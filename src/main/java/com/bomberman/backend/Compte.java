package com.bomberman.backend;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Compte {
    
    @Id
    private String pseudo; // Unique, donc clef
    private String motDePasse;
    public boolean isConnected = false;

    public Compte() {
    }

    public Compte(String pseudo, String motDePasse) {
        this.pseudo = pseudo;
        this.motDePasse = motDePasse;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}
