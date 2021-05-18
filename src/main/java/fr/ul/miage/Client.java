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
}
