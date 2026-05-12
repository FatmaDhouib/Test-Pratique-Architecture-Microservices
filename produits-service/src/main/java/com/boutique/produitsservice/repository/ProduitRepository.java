package com.boutique.produitsservice.repository;

import com.boutique.produitsservice.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
    // Spring génère automatiquement la requête SQL à partir du nom de la méthode !
    List<Produit> findByCategorieId(Long categorieId);
}