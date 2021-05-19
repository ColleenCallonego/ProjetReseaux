package fr.ul.miage;

import java.util.Properties;

public class Main {
    public static void main(String[] args){
        Properties p = new Properties();
        Serveur serveur = new Serveur(p.address, p);
        serveur.go();
    }
}