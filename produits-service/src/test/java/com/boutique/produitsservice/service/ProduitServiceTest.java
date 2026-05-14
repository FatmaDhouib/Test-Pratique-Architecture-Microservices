package com.boutique.produitsservice.service;

import com.boutique.produitsservice.entity.Produit;
import com.boutique.produitsservice.repository.ProduitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProduitServiceTest {

    @Mock  // Crée un faux ProduitRepository (ne touche pas la vraie base)
    private ProduitRepository produitRepository;

    @InjectMocks  // Injecte les mocks dans ProduitService
    private ProduitService produitService;

    @Test
    void findAll_shouldReturnAllProduits() {
        // ARRANGE : Prépare les données de test
        Produit p1 = new Produit(1L, "Laptop", 999.0, 10, null);
        Produit p2 = new Produit(2L, "Phone", 499.0, 20, null);
        when(produitRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        // ACT : Appelle la méthode à tester
        List<Produit> result = produitService.findAll();

        // ASSERT : Vérifie le résultat
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getNom()).isEqualTo("Laptop");
        verify(produitRepository, times(1)).findAll();
    }

    @Test
    void findById_whenNotFound_shouldThrowException() {
        // ARRANGE
        when(produitRepository.findById(99L)).thenReturn(Optional.empty());

        // ACT + ASSERT : Vérifie que l'exception est lancée
        assertThrows(RuntimeException.class, () -> produitService.findById(99L));
    }

    @Test
    void save_shouldReturnSavedProduit() {
        // ARRANGE
        Produit produit = new Produit(null, "Tablette", 299.0, 5, null);
        Produit saved = new Produit(1L, "Tablette", 299.0, 5, null);
        when(produitRepository.save(produit)).thenReturn(saved);

        // ACT
        Produit result = produitService.save(produit);

        // ASSERT
        assertThat(result.getId()).isEqualTo(1L);
        verify(produitRepository, times(1)).save(produit);
    }
}