package fr.ul.miage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Serveur{
    public ServerSocket server;
    public String name;

    public Serveur (String name){
        this.name = name;
    }

    public void go(){
        try{
            InetAddress bindAddress = InetAddress.getByName(name);
            server = new ServerSocket(80, 1, bindAddress);
            while (true){
                try {
                    Socket s = server.accept(); // En attente d'une connexion
                    new Client(s); // Lance un nouveau thread pour chaque connexion
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
