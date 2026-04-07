package com.bomberman.backend;

public class Joueur {

    String id;
    String pseudo;
    boolean alive;
    Position position;
    int munitions_courante;
    int munitions_max;
    int puissance;
    int vitesse;

    Plateau plateau;

    public Joueur(String id, String pseudo, Position position) {
        this.id = id;
        this.pseudo = pseudo;
        this.alive = true;
        this.position = position;
        this.munitions_max = plateau.getParametres().getMUNITIONS_INIT();
        this.munitions_courante = this.munitions_max;
        this.puissance = plateau.getParametres().getPUISSANCE_INIT();
        this.vitesse = plateau.getParametres().getVITESSE_INIT();
    }


}
