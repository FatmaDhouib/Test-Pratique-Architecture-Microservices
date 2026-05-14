package com.boutique.avisservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// "produits-service" = le nom du service dans Eureka
@FeignClient(name = "produits-service")
public interface ProduitClient {

    // Quand on appelle cette méthode, Feign fait un GET http://produits-service/api/produits/{id}
    @GetMapping("/api/produits/{id}")
    Object getProduitById(@PathVariable("id") Long id);
}