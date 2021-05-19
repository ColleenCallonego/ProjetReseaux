package fr.ul.miage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Classe pour stocker la configuration du serveurWeb
 */
public class Properties {
    public String address;
    public Integer port;
    public String repertoireSites;

    public Properties (){
        try {
            FileInputStream file = new FileInputStream(".properties");
            Scanner scan = new Scanner(file);
            String s = "";
            while (scan.hasNext()){
                s = scan.nextLine();
                Integer pos = s.indexOf("=");
                switch (s.substring(0, pos)){
                    case "address":
                        this.address = s.substring(pos + 1);
                        break;
                    case "port":
                        this.port = Integer.decode(s.substring(pos + 1));
                        break;
                    case "repertoireSitesWeb":
                        this.repertoireSites = s.substring(pos + 1);
                        break;
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}