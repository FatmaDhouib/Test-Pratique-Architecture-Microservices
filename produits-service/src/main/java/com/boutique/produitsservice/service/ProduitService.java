package com.boutique.produitsservice.service;

import com.boutique.produitsservice.entity.Produit;
import com.boutique.produitsservice.repository.ProduitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProduitService {

    private final ProduitRepository produitRepository;

    // @Cacheable : la première fois, exécute la méthode et stocke le résultat dans Redis
    // Les fois suivantes, retourne directement depuis Redis (plus rapide !)
    @Cacheable(value = "produits")
    public List<Produit> findAll() {
        return produitRepository.findAll();
    }

    public List<Produit> findByCategorieId(Long categorieId) {
        return produitRepository.findByCategorieId(categorieId);
    }

    public Produit findById(Long id) {
        return produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé : " + id));
    }

    // @CacheEvict : vide le cache quand on crée un nouveau produit
    // (pour que la liste soit mise à jour au prochain appel)
    @CacheEvict(value = "produits", allEntries = true)
    public Produit save(Produit produit) {
        return produitRepository.save(produit);
    }
}