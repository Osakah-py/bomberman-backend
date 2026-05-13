package com.bomberman.backend;

public interface Plateau {

    Parametres parametres = new Parametres();
    public Etat_Case[][] plateau = new Etat_Case[parametres.getLARGEUR()][parametres.getHAUTEUR()];

    public Etat_Case[][] getPlateau();

}
