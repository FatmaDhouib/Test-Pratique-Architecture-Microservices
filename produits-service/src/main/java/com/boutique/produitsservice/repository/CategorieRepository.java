package com.boutique.produitsservice.repository;

import com.boutique.produitsservice.entity.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    // JpaRepository fournit déjà : findAll(), findById(), save(), delete()...
}