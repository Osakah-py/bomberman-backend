package com.bomberman.backend;

public class Proxy implements Jeu {

    Parametres param;
    Etat_Case[][] plateau;

    @Override
    public Parametres getParametres() {
        return param;
    }
}
