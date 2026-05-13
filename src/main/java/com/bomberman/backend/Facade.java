package com.bomberman.backend;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Facade {

    private final Map<String, Partie_Reelle> parties = new HashMap<>();

    @Autowired
    CompteRepository cr;

    @Autowired
    RelationRepository rr;

    /* ---------------------
        GESTION DES COMPTES
    --------------------- */

    @PostMapping("/creerCompte")
    public ResponseEntity<Compte> creerCompte(@RequestParam("pseudo") String pseudo,
            @RequestParam("password") String password) {
        if (pseudo == null || pseudo.isBlank() || password == null || password.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        if (cr.findById(pseudo).isPresent()) {
            throw new RuntimeException("Le pseudo existe déjà");
        }
        Compte compte = new Compte(pseudo, password);
        cr.save(compte);
        return ResponseEntity.ok(compte);
    }

    @PostMapping("/seConnecter")
    public ResponseEntity<Compte> seConnecter(@RequestParam("pseudo") String pseudo,
            @RequestParam("password") String password) {
        if (pseudo == null || pseudo.isBlank() || password == null || password.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        Compte compte = cr.findById(pseudo).orElse(null);
        if (compte == null || !compte.getMotDePasse().equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        compte.isConnected = true;
        return ResponseEntity.ok(compte);
    }

    @PostMapping("/seDeconnecter")
    public ResponseEntity<Void> seDeconnecter(@RequestParam("pseudo") String pseudo) {
        if (pseudo == null || pseudo.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        Compte compte = cr.findById(pseudo).orElse(null);
        if (compte == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        compte.isConnected = false;
        return ResponseEntity.ok().build();
    }

    @PostMapping("/supprimerCompte")
    public ResponseEntity<Void> supprimerCompte(@RequestParam("pseudo") String pseudo, @RequestParam("password") String password) {
        if (pseudo == null || pseudo.isBlank() || password == null || password.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        Compte compte = cr.findById(pseudo).orElse(null);
        if (compte == null || !compte.getMotDePasse().equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        cr.delete(compte);
        return ResponseEntity.ok().build();
    }

    /* ---------------------
        GESTION DES AMIS
    --------------------- */
    
    @PostMapping("/ajouterAmi")
    public ResponseEntity<Void> ajouterAmi(@RequestParam("pseudo") String pseudo, @RequestParam("amiPseudo") String amiPseudo) {
        if (pseudo == null || pseudo.isBlank() || amiPseudo == null || amiPseudo.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        Compte compte = cr.findById(pseudo).orElse(null);
        Compte amiCompte = cr.findById(amiPseudo).orElse(null);
        if (compte == null || amiCompte == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Relation relation;
        if (pseudo.compareTo(amiPseudo) > 0) {
            relation = new Relation(amiPseudo, pseudo);
        } else {
            relation = new Relation(pseudo, amiPseudo);
        }
        rr.save(relation);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/supprimerAmi")
    public ResponseEntity<Void> supprimerAmi(@RequestParam("pseudo") String pseudo, @RequestParam("amiPseudo") String amiPseudo) {
        if (pseudo == null || pseudo.isBlank() || amiPseudo == null || amiPseudo.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        Compte compte = cr.findById(pseudo).orElse(null);
        Compte amiCompte = cr.findById(amiPseudo).orElse(null);
        if (compte == null || amiCompte == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Relation relation;
        if (pseudo.compareTo(amiPseudo) > 0) {
            relation = rr.findByPseudo1AndPseudo2(amiPseudo, pseudo)
                    .orElse(null);
        } else {
            relation = rr.findByPseudo1AndPseudo2(pseudo, amiPseudo)
                    .orElse(null);
        }

        if (relation != null) {
            rr.delete(relation);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/consulterAmis")
    public ResponseEntity<Iterable<Relation>> consulterAmis(@RequestParam("pseudo") String pseudo) {
        if (pseudo == null || pseudo.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        Compte compte = cr.findById(pseudo).orElse(null);
        if (compte == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(rr.findByPseudo1OrPseudo2(pseudo, pseudo));
    }
    /* ---------------------
        GESTION DES PARTIES
    --------------------- */

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
