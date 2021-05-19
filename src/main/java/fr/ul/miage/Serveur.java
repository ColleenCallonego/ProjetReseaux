package fr.ul.miage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

public class Serveur{
    public ServerSocket server;
    public String name;
    public Properties properties;

    public Serveur (String name, Properties p){
        this.name = name;
        this.properties = p;
    }

    public void go(){
        try{
            InetAddress bindAddress = InetAddress.getByName(name);
            server = new ServerSocket(properties.port, 1, bindAddress);
            while (true){
                try {
                    Socket s = server.accept(); // En attente d'une connexion
                    new Client(s, properties); // Lance un nouveau thread pour chaque connexion
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