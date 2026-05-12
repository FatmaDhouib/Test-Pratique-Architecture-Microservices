package com.boutique.produitsservice.service;

import com.boutique.produitsservice.entity.Produit;
import com.boutique.produitsservice.repository.ProduitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProduitServiceTest {

    @Mock
    private ProduitRepository produitRepository;

    @InjectMocks
    private ProduitService produitService;

    private Produit produit;

    @BeforeEach
    void setUp() {
        produit = new Produit();
        produit.setId(1L);
        produit.setNom("Laptop");
        produit.setPrix(1500.0);
        produit.setStock(10);
    }

    @Test
    void testFindAll() {
        when(produitRepository.findAll()).thenReturn(Arrays.asList(produit));
        
        List<Produit> produits = produitService.findAll();
        
        assertNotNull(produits);
        assertEquals(1, produits.size());
        assertEquals("Laptop", produits.get(0).getNom());
        verify(produitRepository, times(1)).findAll();
    }

    @Test
    void testFindById_Success() {
        when(produitRepository.findById(1L)).thenReturn(Optional.of(produit));
        
        Produit found = produitService.findById(1L);
        
        assertNotNull(found);
        assertEquals("Laptop", found.getNom());
        verify(produitRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        when(produitRepository.findById(2L)).thenReturn(Optional.empty());
        
        assertThrows(RuntimeException.class, () -> produitService.findById(2L));
        verify(produitRepository, times(1)).findById(2L);
    }

    @Test
    void testSave() {
        when(produitRepository.save(any(Produit.class))).thenReturn(produit);
        
        Produit saved = produitService.save(produit);
        
        assertNotNull(saved);
        assertEquals("Laptop", saved.getNom());
        verify(produitRepository, times(1)).save(any(Produit.class));
    }
}
