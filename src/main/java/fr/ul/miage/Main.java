package fr.ul.miage;

public class Main {
    public static void main(String[] args){
        Serveur serveur = new Serveur("127.0.0.2");
        serveur.go();
    }
}
