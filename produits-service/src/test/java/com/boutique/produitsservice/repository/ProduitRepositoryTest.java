package com.boutique.produitsservice.repository;

import com.boutique.produitsservice.entity.Categorie;
import com.boutique.produitsservice.entity.Produit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest  // Lance uniquement la couche JPA avec une base H2 en mémoire
class ProduitRepositoryTest {

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    @Test
    void findByCategorieId_shouldReturnCorrectProduits() {
        // ARRANGE : Crée une catégorie et des produits en base H2
        Categorie cat = categorieRepository.save(new Categorie(null, "Électronique"));
        produitRepository.save(new Produit(null, "Laptop", 999.0, 10, cat));
        produitRepository.save(new Produit(null, "Phone", 499.0, 20, cat));

        // ACT
        List<Produit> result = produitRepository.findByCategorieId(cat.getId());

        // ASSERT
        assertThat(result).hasSize(2);
    }
}