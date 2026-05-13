package com.bomberman.backend;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RelationRepository extends JpaRepository<Relation, Long> {

    // Trouver toutes les relations impliquant un pseudo donné
    Iterable<Relation> findByPseudo1OrPseudo2(String pseudo1, String pseudo2);

    Optional<Relation> findByPseudo1AndPseudo2(String amiPseudo, String pseudo);
}
