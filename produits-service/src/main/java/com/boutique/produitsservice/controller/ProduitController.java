package com.boutique.produitsservice.controller;

import com.boutique.produitsservice.entity.Produit;
import com.boutique.produitsservice.service.ProduitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/produits")
@RequiredArgsConstructor
@Tag(name = "Produits", description = "Gestion des produits")
public class ProduitController {

    private final ProduitService produitService;

    @GetMapping
    @Operation(summary = "Liste tous les produits ou filtre par catégorie")
    public List<Produit> findAll(@RequestParam(required = false) Long categorieId) {
        if (categorieId != null) {
            return produitService.findByCategorieId(categorieId);
        }
        return produitService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Détail d'un produit")
    public Produit findById(@PathVariable Long id) {
        return produitService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crée un nouveau produit")
    public Produit create(@RequestBody Produit produit) {
        return produitService.save(produit);
    }
}