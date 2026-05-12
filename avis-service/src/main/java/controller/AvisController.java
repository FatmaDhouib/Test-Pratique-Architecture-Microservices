package com.boutique.avisservice.controller;

import com.boutique.avisservice.entity.Avis;
import com.boutique.avisservice.service.AvisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/avis")
@RequiredArgsConstructor
@Tag(name = "Avis", description = "Gestion des avis produits")
public class AvisController {

    private final AvisService avisService;

    @GetMapping("/{produitId}")
    @Operation(summary = "Liste les avis d'un produit")
    public List<Avis> findByProduitId(@PathVariable Long produitId) {
        return avisService.findByProduitId(produitId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Soumet un avis")
    public Avis create(@RequestBody Avis avis) {
        return avisService.save(avis);
    }
}