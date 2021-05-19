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

    public Client(Socket s, Properties p){
        this.socket = s;
        this.properties = p;
        start();
    }

    public void run(){
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream out = new PrintStream(new BufferedOutputStream(socket.getOutputStream()));

            //récupération de la requête
            String s = in.readLine();
            System.out.println(s);
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

    public void getfilename(String host, String requete, PrintStream out){
        String filename = "";
        StringTokenizer st = new StringTokenizer(requete);
        try {
            if (st.hasMoreElements() && st.nextToken().equalsIgnoreCase("GET") && st.hasMoreElements()) {
                filename += st.nextToken();
            } else {
                throw new BadRequestException(); // Bad request
            }
            //si le filename fini par "/" alors on doit accès au index.html du chemin
            if ((!host.equals("www.verti.com") && !host.equals("www.dopetrope.com"))) {
                if (filename.endsWith("/")){
                    filename = properties.repertoireSites + "/" + filename + "index.html";
                }
                else{
                    filename = properties.repertoireSites + "/"  + filename;
                }
            }
            else if (host.equals("www.verti.com")){
                if (filename.endsWith("/") || filename.endsWith("@")) {
                    if (estProtege(properties.repertoireSites + "/verti/")){
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
                            InputStream f = new FileInputStream(properties.repertoireSites + "/verti/.htpasswd");
                            Scanner scan = new Scanner(f);
                            if (check(scan, user, mdp)){
                                filename = properties.repertoireSites + "/verti/index.html";
                            }
                            else{
                                throw new UnauthorizedException(); // Unauthorized
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
            else if (host.equals("www.dopetrope.com")){
                if (filename.endsWith("/")) {
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
                                throw new UnauthorizedException(); // Unauthorized
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
            System.out.println(filename);
            if (filename.endsWith(".php")){
                Runtime run = Runtime.getRuntime();
                Process process = run.exec("php " + filename);
                String contenu = getContenu(process.getInputStream());
                out.print("HTTP/1.0 200 OK\r\n" + "Content-type: " + getMimeType(".html") + "\r\n\r\n");
                out.write(contenu.getBytes(StandardCharsets.UTF_8));
                out.close();
            }
            else{
                InputStream f = new FileInputStream(filename);
                out.print("HTTP/1.0 200 OK\r\n" + "Content-type: " + getMimeType(filename) + "\r\n\r\n");
                byte[] a = new byte[4096];
                int n;
                while ((n = f.read(a)) > 0)
                    out.write(a, 0, n);
                out.close();
            }
        } catch (FileNotFoundException e) {
            out.println("HTTP/1.0 404 Not Found\r\n" + "Content-type: text/html\r\n\r\n"
                    + "<html><head></head><body>" + " Error 404 - Page not found</body></html>\n");
            out.close();
            try {
                socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (BadRequestException e) {
            out.println("HTTP/1.0 400 Bad Request Not Found\r\n" + "Content-type: text/html\r\n\r\n"
                    + "<html><head></head><body>" + " Error 400 - Bad Request</body></html>\n");
            out.close();
            try {
                socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (UnauthorizedException e) {
            out.println("HTTP/1.0 401 Unauthorized\r\n" + "Content-type: text/html\r\n\r\n"
                    + "<html><head></head><body>" + " Error 401 - Unauthorized -- Write www[...]/@user:password@ to acces</body></html>\n");
            out.close();
            try {
                socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public Boolean check(Scanner scan, String user, String mdp){
        while (scan.hasNext()){
            String s = scan.nextLine();
            Integer pos = s.indexOf(":");
            String FileUser = s.substring(0, pos);
            String FileMdp = s.substring(pos + 1);
            MessageDigest m = null;
            try {
                m = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
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

    public String getContenu (InputStream file){
        String contenu = "";
        Scanner scan = new Scanner(file);
        while (scan.hasNext()){
            contenu += scan.nextLine();
        }
        return contenu;
    }
}