package fr.ul.miage;


public class Main {
    public static void main(String[] args){
        //pour stocker la configuration du serveur
        Properties p = new Properties();

        //initialiser le serveur
        Serveur serveur = new Serveur(p.address, p);

        //lancer le serveur
        serveur.go();
    }
}