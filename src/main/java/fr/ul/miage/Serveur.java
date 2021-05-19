package fr.ul.miage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Serveur{
    public ServerSocket server;
    public String address;
    public Properties properties;

    /**
     * Constructeur
     * @param address
     * @param p
     */
    public Serveur (String address, Properties p){
        this.address = address;
        this.properties = p;
    }

    /**
     * Méthode pour lancer le ServeurWeb
     * Il va attendre des connexions
     */
    public void go(){
        try{
            InetAddress bindAddress = InetAddress.getByName(address);
            server = new ServerSocket(properties.port, 1, bindAddress);
            while (true){
                try {
                    // En attente d'une connexion
                    Socket s = server.accept();

                    // Lance un nouveau thread pour chaque connexion
                    new Client(s, properties);
                }
                catch (Exception e) {
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