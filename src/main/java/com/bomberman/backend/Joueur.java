package com.bomberman.backend;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Joueur {

    String id;
    String pseudo;
    boolean alive;
    Position position;
    int munitions_courante;
    int munitions_max;
    int puissance;
    int vitesse;

    @JsonIgnore // Eviter de mettre ici, on doit pas avoir accès au Jeu
    Jeu jeu;

    public Joueur(String id, String pseudo, Position position, Jeu jeu) {
        this.id = id;
        this.pseudo = pseudo;
        this.alive = true;
        this.position = position;
        this.jeu = jeu;

        Parametres param = jeu != null ? jeu.getParametres() : new Parametres();
        this.munitions_max = param.getMUNITIONS_INIT();
        this.munitions_courante = this.munitions_max;
        this.puissance = param.getPUISSANCE_INIT();
        this.vitesse = param.getVITESSE_INIT();
    }

    public Joueur(String id, String pseudo, Position position) {
        this(id, pseudo, position, null);
    }

    public Direction getDeplacement() {
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
