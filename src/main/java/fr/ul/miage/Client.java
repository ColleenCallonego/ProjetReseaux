package fr.ul.miage;

import java.io.*;
import java.net.Socket;

public class Client extends Thread{
    public Socket socket;

    public Client(Socket s){
        this.socket = s;
        start();
    }

    public void run(){
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream out = new PrintStream(new BufferedOutputStream(socket.getOutputStream()));

            //récupération de la requête
            String s = in.readLine();
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getfilename(String requete){
        String filename = "sites/";
        StringTokenizer st = new StringTokenizer(requete);
        try{
            if (st.hasMoreElements() && st.nextToken().equalsIgnoreCase("GET") && st.hasMoreElements()){
                filename += st.nextToken();
            }
            else{
                throw new FileNotFoundException(); // Bad request
            }
            //si le filename fini par "/" alors on doit accès au index.html du chemin
            if (filename.endsWith("/")){
                filename += "index.html";
            }
            //pour enlever tous les "/" au début du filename
            while(filename.indexOf("/") == 0){
                filename = filename.substring(1);
            }
            //pour remplacer les "/" par des "\"
            filename = filename.replace('/', File.separator.charAt(0));
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return filename;
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
}
