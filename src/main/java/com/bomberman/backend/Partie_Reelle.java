package com.bomberman.backend;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Partie_Reelle implements Jeu {

    private static final int LARGEUR = 13;
    private static final int HAUTEUR = 13;
    private static final int MAX_JOUEURS = 4;

    private final String id;
    private final Parametres param = new Parametres();
    private final Etat_Case[][] plateau = new Etat_Case[HAUTEUR][LARGEUR];
    private final Map<String, Joueur> joueurs = new LinkedHashMap<>();
    private boolean started = false;

    public Partie_Reelle(String id) {
        this.id = id;
        initPlateau();
    }

    private void initPlateau() {
        for (int y = 0; y < HAUTEUR; y++) {
            for (int x = 0; x < LARGEUR; x++) {
                plateau[y][x] = Etat_Case.VIDE;
            }
        }
    }

    @Override
    public Parametres getParametres() {
        return param;
    }

    public String getId() {
        return id;
    }

    public int getJoueurCount() {
        return joueurs.size();
    }

    public int getMaxJoueurs() {
        return MAX_JOUEURS;
    }

    public boolean isStarted() {
        return started;
    }

    public List<Joueur> getJoueurs() {
        return new ArrayList<>(joueurs.values());
    }

    public synchronized boolean peutRejoindre() {
        return !started && joueurs.size() < MAX_JOUEURS;
    }

    public synchronized Joueur ajouterJoueur(String pseudo) {
        if (!peutRejoindre()) {
            return null;
        }
        Position position = getSpawnPosition(joueurs.size());
        Joueur joueur = new Joueur(String.valueOf(joueurs.size() + 1), pseudo, position, this);
        joueur.setJeu(this);
        joueurs.put(joueur.getId(), joueur);
        return joueur;
    }

    private Position getSpawnPosition(int index) {
        return switch (index) {
            case 0 -> new Position(1, 1);
            case 1 -> new Position(1, LARGEUR - 2);
            case 2 -> new Position(HAUTEUR - 2, 1);
            default -> new Position(HAUTEUR - 2, LARGEUR - 2);
        };
    }

    public synchronized boolean demarrer() {
        if (joueurs.size() == MAX_JOUEURS && !started) {
            started = true;
            return true;
        }
        return false;
    }

    public String getEtat() {
        if (started) {
            return "EN_COURS";
        }
        if (joueurs.size() == MAX_JOUEURS) {
            return "PRETE";
        }
        return "ATTENTE";
    }

    public Etat_Case[][] getPlateau() {
        return plateau;
    }
}
