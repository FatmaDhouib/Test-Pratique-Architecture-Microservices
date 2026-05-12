package com.boutique.produitsservice.controller;

import com.boutique.produitsservice.entity.Categorie;
import com.boutique.produitsservice.service.CategorieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Catégories", description = "Gestion des catégories")
public class CategorieController {

    private final CategorieService categorieService;

    @GetMapping
    @Operation(summary = "Liste toutes les catégories")
    public List<Categorie> findAll() {
        return categorieService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Détail d'une catégorie")
    public Categorie findById(@PathVariable Long id) {
        return categorieService.findById(id);
    }
}