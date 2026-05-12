describe('Parcours utilisateur Boutique', () => {

    it('Récupère la liste des produits', () => {
        cy.request('GET', 'http://localhost:8090/api/produits')
            .then((response) => {
                expect(response.status).to.eq(200);
                expect(response.body).to.be.an('array');
                expect(response.body.length).to.be.greaterThan(0);
            });
    });

    it('Récupère le détail d\'un produit', () => {
        cy.request('GET', 'http://localhost:8090/api/produits/1')
            .then((response) => {
                expect(response.status).to.eq(200);
                expect(response.body).to.have.property('id', 1);
                expect(response.body).to.have.property('nom');
            });
    });

    it('Récupère les avis d\'un produit', () => {
        cy.request('GET', 'http://localhost:8090/api/avis/1')
            .then((response) => {
                expect(response.status).to.eq(200);
                expect(response.body).to.be.an('array');
            });
    });

    it('Parcours complet : catégorie → produit → avis', () => {
        // 1. Récupère les catégories
        cy.request('GET', 'http://localhost:8090/api/categories')
            .then((catResponse) => {
                expect(catResponse.status).to.eq(200);
                const categorieId = catResponse.body[0].id;

                // 2. Récupère les produits de la première catégorie
                return cy.request(`http://localhost:8090/api/produits?categorieId=${categorieId}`);
            })
            .then((prodResponse) => {
                expect(prodResponse.status).to.eq(200);
                expect(prodResponse.body.length).to.be.greaterThan(0);
                const produitId = prodResponse.body[0].id;

                // 3. Récupère les avis du premier produit
                return cy.request(`http://localhost:8090/api/avis/${produitId}`);
            })
            .then((avisResponse) => {
                expect(avisResponse.status).to.eq(200);
            });
    });
});