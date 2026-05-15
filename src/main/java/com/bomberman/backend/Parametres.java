package com.bomberman.backend;

public class Parametres {

    // Plateau
    int HAUTEUR = 13;
    int LARGEUR = 13;
    int MAX_JOUEURS = 4;
    int DELAI_BOMBE = 3000; // en ms
    int DELAI_EXPLOSION = 500; // en ms

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

    public int getDELAI_EXPLOSION() {
        return DELAI_EXPLOSION;
    }

    public void setDELAI_EXPLOSION(int DELAI_EXPLOSION) {
        this.DELAI_EXPLOSION = DELAI_EXPLOSION;
    }

    public int getDELAI_BOMBE() {
        return DELAI_BOMBE;
    }

    public void setDELAI_BOMBE(int DELAI_BOMBE) {
        this.DELAI_BOMBE = DELAI_BOMBE;
    }
}
