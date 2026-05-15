package com.bomberman.backend;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Partie_Reelle implements Jeu {

    private final String id;
    private final Parametres param = new Parametres();
    private final Etat_Case[][] plateau = new Etat_Case[param.getHAUTEUR()][param.getLARGEUR()];
    private final Etat_Objet[][] objets = new Etat_Objet[param.getHAUTEUR()][param.getLARGEUR()];
    private final Map<String, Joueur> joueurs = new LinkedHashMap<>();
    private boolean started = false;

    public Partie_Reelle(String id) {
        this.id = id;
        initPlateau();
    }

    private void initPlateau() {
        for (int y = 0; y < param.getHAUTEUR(); y++) {
            for (int x = 0; x < param.getLARGEUR(); x++) {
                plateau[y][x] = Etat_Case.VIDE;
                objets[y][x] = Etat_Objet.VIDE;
            }
        }
    }

    /* ---------------------
        GESTION DES PARTIES
    --------------------- */    

    public synchronized boolean peutRejoindre() {
        return !started && joueurs.size() < param.getMaxJoueurs();
    }

    public synchronized Joueur ajouterJoueur(String pseudo) {
        if (!peutRejoindre()) {
            return null;
        }
        Position position = getSpawnPosition(joueurs.size());
        Joueur joueur = new Joueur(pseudo, position, this);
        joueur.setJeu(this);
        joueurs.put(joueur.getPseudo(), joueur);

        // Démarrer automatiquement si le nombre max de joueurs est atteint
        if (joueurs.size() == param.getMaxJoueurs() && !isStarted()) {
            started = true;
        }
        return joueur;
    }

    public synchronized boolean retirerJoueur(String pseudo) {
        joueurs.remove(pseudo);
        return started;
    }

    private Position getSpawnPosition(int index) {
        return switch (index) {
            case 0 -> new Position(1, 1);
            case 1 -> new Position(1, param.getLARGEUR() - 2);
            case 2 -> new Position(param.getHAUTEUR() - 2, 1);
            default -> new Position(param.getHAUTEUR() - 2, param.getLARGEUR() - 2);
        };
    }

    public String getEtat() {
        if (started) {
            return "EN_COURS";
        }
        if (joueurs.size() == param.getMaxJoueurs()) {
            return "PRETE";
        }
        return "ATTENTE";
    }
    
    /* ---------------------
        GESTION IN-GAME
    --------------------- */    

    public synchronized boolean deposerBombe(String pseudo) {
        Joueur joueur = joueurs.get(pseudo);
        if (joueur == null || !started) {
            return false;
        }
        Position pos = joueur.getPosition();
        if (objets[pos.getY()][pos.getX()] == Etat_Objet.VIDE) {
            objets[pos.getY()][pos.getX()] = Etat_Objet.BOMBE;
            // Lance un cooldown de DELAI_BOMBE secondes avant l'explosion
             new Thread(() -> {
                try {
                    Thread.sleep(param.getDELAI_BOMBE() * 1000L);
                    exploserBombe(pos, joueur.getPuissance());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
            return true;
        }
        return false;
    }

    public boolean exploserCase(int x, int y, int puissance) {
        if (x >= 0 && x < param.getLARGEUR() && y >= 0 && y < param.getHAUTEUR()) {

            // Bloc cassable
            if (plateau[y][x] == Etat_Case.BLOC_CASSABLE) {
                plateau[y][x] = Etat_Case.VIDE;
                // Fonction pour générer un objet aléatoire (bonus/malus) avec une certaine probabilité

                // On ne poursuit pas l'explosion au-delà d'un bloc cassable
                return false;
            }

            // Bombe
            if (objets[y][x] == Etat_Objet.BOMBE) {
                objets[y][x] = Etat_Objet.VIDE;
                exploserBombe(new Position(x, y), puissance); // Explosion en chaîne
            }    
            
            // Gestion des joueurs touchés par l'explosion
            for (Joueur joueur : joueurs.values()) {
                if (joueur.getPosition().equals(new Position(x, y))) {
                    joueur.setVivant(false);
                    // Le joueur perd, affichage écran défaite, puis il peut voir le jeu en spectateur ou quitter la partie
                }
            }

            // Gestion du temps d'explosion
            objets[y][x] = Etat_Objet.EXPLOSION;
            new Thread(() -> {
                try {
                    Thread.sleep(param.getDELAI_EXPLOSION());
                    objets[y][x] = Etat_Objet.VIDE;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        } else {
            // Hors limites, on arrête l'explosion dans cette direction
            return false;
        }
        return true;
    }

    public void exploserBombe(Position pos, int puissance) {
        int x = pos.getX();
        int y;

        // Explosion vers le haut
        for (int dy = 1; dy <= puissance; dy++) {
            y = pos.getY() + dy;
            // Le booléen retourné indique si l'explosion peut continuer dans cette direction
            if (!exploserCase(x, y, puissance)) {
                break;
            }
        }

        // Explosion vers le bas
        for (int dy = 1; dy <= puissance; dy++) {
            y = pos.getY() - dy;
            if (!exploserCase(x, y, puissance)) {
                break;
            }
        }

        y = pos.getY();
        // Explosion vers la droite
        for (int dx = 1; dx <= puissance; dx++) {
            x = pos.getX() + dx;
            if (!exploserCase(x, y, puissance)) {
                break;
            }
        }

        // Explosion vers la gauche
        for (int dx = 1; dx <= puissance; dx++) {
            x = pos.getX() - dx;
            if (!exploserCase(x, y, puissance)) {
                break;
            }
        }
    }

    /* ---------------------
        GETTERS ET SETTERS
    --------------------- */
    
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
        return param.getMaxJoueurs();
    }

    public boolean isStarted() {
        return started;
    }

    public List<Joueur> getJoueurs() {
        return new ArrayList<>(joueurs.values());
    }    

    public Etat_Case[][] getPlateau() {
        return plateau;
    }

    public Etat_Objet[][] getObjets() {
        return objets;
    }
}
