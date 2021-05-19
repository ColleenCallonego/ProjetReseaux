package fr.ul.miage;

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Client extends Thread{
    public Socket socket;
    public Properties properties;

    /**
     * Constructeur
     * @param s
     * @param p
     */
    public Client(Socket s, Properties p){
        this.socket = s;
        this.properties = p;
        start(); //démarrer le Thread
    }

    /**
     * Méthode qui va être lancé au démarrage du Thread Client
     */
    public void run(){
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream out = new PrintStream(new BufferedOutputStream(socket.getOutputStream()));

            //récupération de la requête
            String s = in.readLine();
            System.out.println(s);

            //récupération de l'host
            String host = in.readLine();
            System.out.println(host);

            if (host.length() >= host.indexOf(":")+2){
                host = host.substring(host.indexOf(":") + 2);
            }
            else{
                host = "";
            }
            getfilename(host, s, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode pour envoyer une réponse au client
     * @param host
     * @param requete
     * @param out
     */
    public void getfilename(String host, String requete, PrintStream out){
        String filename = "";
        StringTokenizer st = new StringTokenizer(requete);
        try {
            if (st.hasMoreElements() && st.nextToken().equalsIgnoreCase("GET") && st.hasMoreElements()) {
                filename += st.nextToken();
            }
            else {
                throw new BadRequestException(); // Bad request
            }

            if ((!host.equals("www.verti.com") && !host.equals("www.dopetrope.com"))) { //si la requête ne fait pas appel à un domaine
                if (filename.endsWith("/")){ //si le filename fini par "/" alors on doit accéder au index.html du chemin
                    filename = properties.repertoireSites + "/" + filename + "index.html";
                }
                else{
                    filename = properties.repertoireSites + "/"  + filename;
                }
            }
            else if (host.equals("www.verti.com")){ //si la requête fait appel au domaine www.verti.com
                if (filename.endsWith("/") || filename.endsWith("@")) { //si la requête à les informations de connexion ou que c'est le premier appel du site
                    if (estProtege(properties.repertoireSites + "/verti/")){
                        Integer pos1 = filename.indexOf("@");
                        Integer pos2 = filename.lastIndexOf("@");
                        if (pos1 == -1){ //si il n'y a pas "@"
                            throw new UnauthorizedException(); // Unauthorized
                        }
                        else{
                            String userPassword = filename.substring(pos1 + 1, pos2);
                            Integer pos3 = userPassword.indexOf(":");
                            String user = userPassword.substring(0, pos3);
                            String mdp = userPassword.substring(pos3 + 1);
                            System.out.println(user + mdp);
                            InputStream f = new FileInputStream(properties.repertoireSites + "/verti/.htpasswd");
                            Scanner scan = new Scanner(f);
                            if (check(scan, user, mdp)){
                                filename = properties.repertoireSites + "/verti/index.html";
                            }
                            else{
                                throw new ForbiddenException(); // Forbidden
                            }
                        }
                    }
                    else{
                        filename = properties.repertoireSites + "/verti/" + filename + "index.html";
                    }
                }
                else {
                    filename = properties.repertoireSites + "/verti/".concat(filename);
                }
            }
            else if (host.equals("www.dopetrope.com")){ //si la requête fait appel au domaine www.verti.com
                if (filename.endsWith("/") || filename.endsWith("@")) {
                    if (estProtege(properties.repertoireSites + "/dopetrope/")){
                        Integer pos1 = filename.indexOf("@");
                        Integer pos2 = filename.lastIndexOf("@");
                        if (pos1 == -1){
                            throw new UnauthorizedException(); // Unauthorized
                        }
                        else{
                            String userPassword = filename.substring(pos1 + 1, pos2);
                            Integer pos3 = userPassword.indexOf(":");
                            String user = userPassword.substring(0, pos3);
                            String mdp = userPassword.substring(pos3 + 1);
                            System.out.println(user + mdp);
                            InputStream f = new FileInputStream(properties.repertoireSites + "/dopetrope/.htpasswd");
                            Scanner scan = new Scanner(f);
                            if (check(scan, user, mdp)){
                                filename = properties.repertoireSites + "/dopetrope/index.html";
                            }
                            else{
                                throw new ForbiddenException(); // Forbidden
                            }
                        }
                    }
                    else{
                        filename = properties.repertoireSites + "/dopetrope/" + filename + "index.html";
                    }
                }
                else {
                    filename = properties.repertoireSites + "/dopetrope/".concat(filename);
                }
            }
            //pour enlever tous les "/" au début du filename
            while (filename.indexOf("/") == 0) {
                filename = filename.substring(1);
            }
            //pour remplacer les "/" par des "\"
            filename = filename.replace('/', File.separator.charAt(0));

            //pour le contenu dynamique
            if (filename.endsWith(".php")){
                //faire un appel pour faire une ligne de commande
                Runtime run = Runtime.getRuntime();
                Process process = run.exec("php " + filename);
                String contenu = getContenu(process.getInputStream());
                out.print("HTTP/1.0 200 OK\r\n" + "Content-type: " + getMimeType(".html") + "\r\n\r\n");
                out.write(contenu.getBytes(StandardCharsets.UTF_8));
                out.close();
            }
            else{
                //envoi de la réponse au client
                InputStream f = new FileInputStream(filename);
                out.print("HTTP/1.0 200 OK\r\n" + "Content-type: " + getMimeType(filename) + "\r\n\r\n");
                byte[] a = new byte[4096];
                int n;
                while ((n = f.read(a)) > 0)
                    out.write(a, 0, n);
                out.close();
            }
        }
        catch (FileNotFoundException e) { //Erreur 404
            out.println("HTTP/1.0 404 Not Found\r\n" + "Content-type: text/html\r\n\r\n"
                    + "<html><head></head><body>" + " Error 404 - Page not found</body></html>\n");
            out.close();
            try {
                socket.close();
            }
            catch (IOException x) {
                System.out.println(x.getMessage());
            }
        }
        catch (BadRequestException e) { //Erreur 400
            out.println("HTTP/1.0 400 Bad Request Not Found\r\n" + "Content-type: text/html\r\n\r\n"
                    + "<html><head></head><body>" + " Error 400 - Bad Request</body></html>\n");
            out.close();
            try {
                socket.close();
            }
            catch (IOException x) {
                System.out.println(x.getMessage());
            }
        }
        catch (UnauthorizedException e) { //Erreur 401
            out.println("HTTP/1.0 401 Unauthorized\r\n" + "Content-type: text/html\r\n\r\n"
                    + "<html><head></head><body>" + " Error 401 - Unauthorized -- Write www[...]/@user:password@ to acces</body></html>\n");
            out.close();
            try {
                socket.close();
            }
            catch (IOException x) {
                System.out.println(x.getMessage());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ForbiddenException e) { //Erreur 403
            out.println("HTTP/1.0 403 Forbidden\r\n" + "Content-type: text/html\r\n\r\n"
                    + "<html><head></head><body>" + " Error 403 - Forbidden -- Write www[...]/@user:password@ to acces</body></html>\n");
            out.close();
            try {
                socket.close();
            }
            catch (IOException x) {
                System.out.println(x.getMessage());
            }
        }
    }

    /**
     * Méthode pour obtenir le mineType d'un fichier
     * @param filename
     * @return
     */
    public String getMimeType(String filename){
        String mimeType = "text/plain";
        if (filename.endsWith(".html") || filename.endsWith(".htm"))
            mimeType = "text/html";
        else if (filename.endsWith(".png"))
            mimeType = "image/png";
        else if (filename.endsWith(".js"))
            mimeType = "application/javascript";
        else if (filename.endsWith(".css"))
            mimeType = "text/css";
        return mimeType;
    }

    /**
     * Méthode pour savoir si un répertoire est protégé ou non
     * @param chemin
     * @return true si il est protégé, false sinon
     */
    public Boolean estProtege(String chemin){
        System.out.println(chemin);
        File folder = new File(chemin);
        for (File f : folder.listFiles()){
            System.out.println(f.getName());
            if (f.getName().equals(".htpasswd")){
                return true;
            }
        }
        return false;
    }

    /**
     * Méthode pour vérifier qu'un mot de passe correspond à un utilisateur
     * @param scan Scanner qui contient le contenu du fichier .htpasswd
     * @param user
     * @param mdp
     * @return true si le mot de passe correspond, false sinon
     */
    public Boolean check(Scanner scan, String user, String mdp){
        try {
            while (scan.hasNext()){
                String s = scan.nextLine();
                Integer pos = s.indexOf(":");
                String FileUser = s.substring(0, pos);
                String FileMdp = s.substring(pos + 1);

                //hashage en md5 du mot de passe fourni par l'utilisateur
                MessageDigest m = MessageDigest.getInstance("MD5");
                m.reset();
                m.update(mdp.getBytes());
                byte[] digest = m.digest();
                BigInteger bigInt = new BigInteger(1,digest);
                String hashtext = bigInt.toString(16);
                while(hashtext.length() < 32 ){
                    hashtext = "0"+hashtext;
                }
                System.out.println(hashtext);
                if (FileUser.equals(user) && FileMdp.equals(hashtext)){
                    return true;
                }
            }
            return false;
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Méthode pour récuperer le contenu d'un fichier
     * @param file
     * @return
     */
    public String getContenu (InputStream file){
        String contenu = "";
        Scanner scan = new Scanner(file);
        while (scan.hasNext()){
            contenu += scan.nextLine();
        }
        return contenu;
    }
}