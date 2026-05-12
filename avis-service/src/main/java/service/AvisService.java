package com.boutique.avisservice.service;

import com.boutique.avisservice.client.ProduitClient;
import com.boutique.avisservice.entity.Avis;
import com.boutique.avisservice.repository.AvisRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AvisService {

    private final AvisRepository avisRepository;
    private final ProduitClient produitClient;

    public List<Avis> findByProduitId(Long produitId) {
        return avisRepository.findByProduitId(produitId);
    }

    public Avis save(Avis avis) {
        // Avant d'enregistrer l'avis, vérifier que le produit existe
        try {
            produitClient.getProduitById(avis.getProduitId());
        } catch (FeignException.NotFound e) {
            // Si le produit n'existe pas, on retourne une erreur 404
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Produit non trouvé avec l'id : " + avis.getProduitId());
        }
        return avisRepository.save(avis);
    }
}