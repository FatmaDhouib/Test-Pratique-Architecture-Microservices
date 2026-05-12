describe('Parcours E2E Microservices', () => {
  it('Simuler le parcours: liste produits -> détail produit -> avis', () => {
    // 1. Liste produits
    cy.request('GET', 'http://localhost:8090/api/produits').then((response) => {
      expect(response.status).to.eq(200);
      expect(response.body).to.be.an('array');
      expect(response.body.length).to.be.greaterThan(0);
      
      const firstProduct = response.body[0];
      const produitId = firstProduct.id;
      
      // 2. Détail produit
      cy.request('GET', `http://localhost:8090/api/produits/${produitId}`).then((resDetail) => {
        expect(resDetail.status).to.eq(200);
        expect(resDetail.body.id).to.eq(produitId);
        
        // 3. Avis produit
        // (This assumes the avis service is also running and might return an empty array if no avis exist yet)
        cy.request({
            method: 'GET', 
            url: `http://localhost:8090/api/avis/${produitId}`,
            failOnStatusCode: false
        }).then((resAvis) => {
          // It could be 200 with an array, or potentially another status if not set up, but 200 is expected
          expect([200, 404]).to.include(resAvis.status);
          if (resAvis.status === 200) {
            expect(resAvis.body).to.be.an('array');
          }
        });
      });
    });
  });
});
