-- Insérer 3 catégories
INSERT INTO categories (nom) VALUES ('Électronique') ON CONFLICT DO NOTHING;
INSERT INTO categories (nom) VALUES ('Vêtements') ON CONFLICT DO NOTHING;
INSERT INTO categories (nom) VALUES ('Alimentation') ON CONFLICT DO NOTHING;

-- Insérer 5 produits
INSERT INTO produits (nom, prix, stock, categorie_id) VALUES ('Smartphone Samsung', 699.99, 50, 1) ON CONFLICT DO NOTHING;
INSERT INTO produits (nom, prix, stock, categorie_id) VALUES ('Laptop Dell', 1299.99, 20, 1) ON CONFLICT DO NOTHING;
INSERT INTO produits (nom, prix, stock, categorie_id) VALUES ('T-shirt Nike', 29.99, 100, 2) ON CONFLICT DO NOTHING;
INSERT INTO produits (nom, prix, stock, categorie_id) VALUES ('Jean Levi s', 89.99, 75, 2) ON CONFLICT DO NOTHING;
INSERT INTO produits (nom, prix, stock, categorie_id) VALUES ('Chocolat Lindt', 4.99, 200, 3) ON CONFLICT DO NOTHING;