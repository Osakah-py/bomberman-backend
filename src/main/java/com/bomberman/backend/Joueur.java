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

    Jeu jeu;

    public Joueur(String id, String pseudo, Position position) {
        this.id = id;
        this.pseudo = pseudo;
        this.alive = true;
        this.position = position;
        this.munitions_max = jeu.getParametres().getMUNITIONS_INIT();
        this.munitions_courante = this.munitions_max;
        this.puissance = jeu.getParametres().getPUISSANCE_INIT();
        this.vitesse = jeu.getParametres().getVITESSE_INIT();
    }

    public Direction getDeplacement(){
        return Direction.NONE;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getMunitions_courante() {
        return munitions_courante;
    }

    public void setMunitions_courante(int munitions_courante) {
        this.munitions_courante = munitions_courante;
    }

    public int getMunitions_max() {
        return munitions_max;
    }

    public void setMunitions_max(int munitions_max) {
        this.munitions_max = munitions_max;
    }

    public int getPuissance() {
        return puissance;
    }

    public void setPuissance(int puissance) {
        this.puissance = puissance;
    }

    public int getVitesse() {
        return vitesse;
    }

    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
    }

    public Jeu getJeu() {
        return jeu;
    }

    public void setJeu(Jeu jeu) {
        this.jeu = jeu;
    }
}
