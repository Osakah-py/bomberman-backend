package com.bomberman.backend;

public class Parametres {

    // Plateau
    int HAUTEUR = 13;
    int LARGEUR = 13;
    int MAX_JOUEURS = 4;

    // Joueur
    int PUISSANCE_INIT = 10;
    int VITESSE_INIT = 10;
    int MUNITIONS_INIT = 2;

    public int getHAUTEUR() {
        return HAUTEUR;
    }

    public void setHAUTEUR(int HAUTEUR) {
        this.HAUTEUR = HAUTEUR;
    }

    public int getLARGEUR() {
        return LARGEUR;
    }

    public void setLARGEUR(int LARGEUR) {
        this.LARGEUR = LARGEUR;
    }

    public int getMaxJoueurs() {
        return MAX_JOUEURS;
    }

    public void setMaxJoueurs(int MAX_JOUEURS) {
        this.MAX_JOUEURS = MAX_JOUEURS;
    }

    public int getPUISSANCE_INIT() {
        return PUISSANCE_INIT;
    }

    public void setPUISSANCE_INIT(int PUISSANCE_INIT) {
        this.PUISSANCE_INIT = PUISSANCE_INIT;
    }

    public int getVITESSE_INIT() {
        return VITESSE_INIT;
    }

    public void setVITESSE_INIT(int VITESSE_INIT) {
        this.VITESSE_INIT = VITESSE_INIT;
    }

    public int getMUNITIONS_INIT() {
        return MUNITIONS_INIT;
    }

    public void setMUNITIONS_INIT(int MUNITIONS_INIT) {
        this.MUNITIONS_INIT = MUNITIONS_INIT;
    }
}
