package com.boutique.produitsservice.repository;

import com.boutique.produitsservice.entity.Categorie;
import com.boutique.produitsservice.entity.Produit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ProduitRepositoryTest {

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testFindByCategorieId() {
        // Given
        Categorie categorie = new Categorie();
        categorie.setNom("Electronique");
        entityManager.persist(categorie);

        Produit produit = new Produit();
        produit.setNom("Laptop");
        produit.setPrix(1500.0);
        produit.setStock(10);
        produit.setCategorie(categorie);
        entityManager.persist(produit);
        
        entityManager.flush();

        // When
        List<Produit> produits = produitRepository.findByCategorieId(categorie.getId());

        // Then
        assertNotNull(produits);
        assertEquals(1, produits.size());
        assertEquals("Laptop", produits.get(0).getNom());
    }

    @Test
    void testSaveAndFindById() {
        // Given
        Produit produit = new Produit();
        produit.setNom("Smartphone");
        produit.setPrix(800.0);
        produit.setStock(20);
        
        // When
        Produit saved = produitRepository.save(produit);
        Produit found = produitRepository.findById(saved.getId()).orElse(null);

        // Then
        assertNotNull(found);
        assertEquals("Smartphone", found.getNom());
    }
}
