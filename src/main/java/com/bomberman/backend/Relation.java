package com.bomberman.backend;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Relation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pseudo1;
    private String pseudo2;

    public Relation(String pseudo1, String pseudo2) {
        this.pseudo1 = pseudo1;
        this.pseudo2 = pseudo2;
    }

    public Long getId() {
        return id;
    }
    
    public String getPseudo1() {
        return pseudo1;
    }

    public String getPseudo2() {
        return pseudo2;
    }

    public void setPseudo1(String pseudo1) {
        this.pseudo1 = pseudo1;
    }

    public void setPseudo2(String pseudo2) {
        this.pseudo2 = pseudo2;
    }
}
