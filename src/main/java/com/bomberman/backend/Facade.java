package com.bomberman.backend;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Facade {

    private final Map<String, Partie_Reelle> parties = new HashMap<>();

    @PostMapping("/creerPartie")
    public ResponseEntity<Partie_Reelle> creerPartie(@RequestParam("id") String id) {
        if (id == null || id.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        synchronized (parties) {
            if (parties.containsKey(id)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            Partie_Reelle partie = new Partie_Reelle(id);
            parties.put(id, partie);
            return ResponseEntity.ok(partie);
        }
    }

    @PostMapping("/rejoindrePartie")
    public ResponseEntity<Joueur> rejoindrePartie(@RequestParam("partieId") String partieId,
            @RequestParam("pseudo") String pseudo) {
        if (partieId == null || partieId.isBlank() || pseudo == null || pseudo.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        Partie_Reelle partie = parties.get(partieId);
        if (partie == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Joueur joueur = partie.ajouterJoueur(pseudo);
        if (joueur == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(joueur);
    }

    @GetMapping("/partieEtat")
    public ResponseEntity<Partie_Reelle> getEtatPartie(@RequestParam("partieId") String partieId) {
        Partie_Reelle partie = parties.get(partieId);
        if (partie == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(partie);
    }

    @PostMapping("/demarrerPartie")
    public ResponseEntity<Partie_Reelle> demarrerPartie(@RequestParam("partieId") String partieId) {
        Partie_Reelle partie = parties.get(partieId);
        if (partie == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (!partie.demarrer()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(partie);
    }
}
