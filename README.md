# Projet Boutique — Architecture Microservices Spring Boot 4

## Présentation

Ce projet est une mini plateforme e-commerce développée avec une architecture microservices.

Le système est composé de :

- produits-service
- avis-service
- Eureka Server
- API Gateway
- PostgreSQL
- Redis
- Application mobile Flutter
- Docker Compose

L'objectif du projet est de démontrer :

- l'architecture microservices,
- la communication inter-services,
- le service discovery,
- le cache Redis,
- le routage via API Gateway,
- les tests backend,
- le déploiement Docker.

---

# Architecture du Projet

```text
/projet-boutique/
├── produits-service/
├── avis-service/
├── eureka-server/
├── api-gateway/
├── mobile-app/
├── docker-compose.yml
└── README.md
```

---

# Technologies Utilisées

## Backend

- Java 25
- Spring Boot 4
- Spring Data JPA
- Spring Cloud Gateway
- Spring Cloud Eureka
- Spring OpenFeign
- PostgreSQL
- Redis
- Maven
- Docker
- Docker Compose
- Swagger OpenAPI

## Mobile

- Flutter
- Dart
- HTTP package

## Tests

- JUnit 5
- Mockito
- H2 Database
- Cypress

---

# Architecture Microservices

## produits-service

Port : 8091

Responsabilités :

- gestion des produits,
- gestion des catégories,
- cache Redis,
- exposition des APIs REST.

Endpoints :

| Méthode | Endpoint | Description |
|---|---|---|
| GET | /api/produits | Liste des produits |
| GET | /api/produits/{id} | Détail produit |
| GET | /api/produits?categorieId=1 | Produits par catégorie |
| POST | /api/produits | Création produit |
| GET | /api/categories | Liste catégories |
| GET | /api/categories/{id} | Détail catégorie |

---

## avis-service

Port : 8092

Responsabilités :

- gestion des avis,
- validation produit via Feign Client,
- exposition APIs REST.

Endpoints :

| Méthode | Endpoint | Description |
|---|---|---|
| GET | /api/avis/{produitId} | Avis du produit |
| POST | /api/avis | Ajouter un avis |

---

## Eureka Server

Port : 8761

Responsabilités :

- découverte des services,
- enregistrement automatique des microservices.

Dashboard Eureka :

```text
http://localhost:8761
```

---

## API Gateway

Port : 8090

Responsabilités :

- point d'entrée unique,
- routage des requêtes,
- communication centralisée.

Routes configurées :

| Route | Destination |
|---|---|
| /api/produits/** | produits-service |
| /api/categories/** | produits-service |
| /api/avis/** | avis-service |

---

# Architecture Technique

```text
                    Mobile App
                         |
                         v
                 API Gateway :8090
                         |
        ---------------------------------
        |                               |
        v                               v
 produits-service                 avis-service
      :8091                           :8092
        |                               |
        v                               v
 PostgreSQL Produits             PostgreSQL Avis
        |
        v
      Redis

                 Eureka Server
                      :8761
```

---

# Configuration des Ports

| Service | Port |
|---|---|
| API Gateway | 8090 |
| produits-service | 8091 |
| avis-service | 8092 |
| Eureka Server | 8761 |
| PostgreSQL Produits | 5433 |
| PostgreSQL Avis | 5434 |
| Redis | 6379 |

---

# Prérequis

Avant de lancer le projet, installer :

- Java 25
- Maven 3.9+
- Docker Desktop
- Flutter SDK
- Git
- Node.js (pour Cypress)

Vérifier les installations :

```bash
java -version
mvn -version
docker --version
flutter --version
node -v
```

---

# Cloner le Projet

```bash
git clone https://github.com/USERNAME/projet-boutique.git

cd projet-boutique
```

---

# Lancement avec Docker Compose

## Construire les services

À la racine du projet :

```bash
docker compose build
```

## Démarrer tous les services

```bash
docker compose up -d
```

## Vérifier les conteneurs

```bash
docker ps
```

---

# Ordre de Démarrage

Le système démarre dans cet ordre :

1. PostgreSQL
2. Redis
3. Eureka Server
4. produits-service
5. avis-service
6. API Gateway

---

# Vérification des Services

## Eureka

```text
http://localhost:8761
```

Les services visibles doivent être :

- PRODUITS-SERVICE
- AVIS-SERVICE
- API-GATEWAY

---

## Swagger produits-service

```text
http://localhost:8091/swagger-ui.html
```

---

## Swagger avis-service

```text
http://localhost:8092/swagger-ui.html
```

---

# Données Initiales

Le fichier data.sql ajoute automatiquement :

## Catégories

- Electronique
- Livres
- Vetements

## Produits

- Laptop
- Smartphone
- Java Book
- T-Shirt
- Casque Audio

---

# Exemple de Requêtes API

## Liste des catégories

```http
GET http://localhost:8090/api/categories
```

---

## Liste des produits

```http
GET http://localhost:8090/api/produits
```

---

## Produits par catégorie

```http
GET http://localhost:8090/api/produits?categorieId=1
```

---

## Ajouter un produit

```http
POST http://localhost:8090/api/produits
Content-Type: application/json
```

Body :

```json
{
  "nom": "MacBook Pro",
  "prix": 4500,
  "stock": 8,
  "categorie": {
    "id": 1
  }
}
```

---

## Ajouter un avis

```http
POST http://localhost:8090/api/avis
Content-Type: application/json
```

Body :

```json
{
  "produitId": 1,
  "auteur": "Fatma",
  "commentaire": "Excellent produit",
  "note": 5
}
```

---

# Redis Cache

Le cache Redis est utilisé dans produits-service.

Objectif :

- accélérer les lectures,
- éviter les accès répétés à PostgreSQL.

Annotations utilisées :

```java
@Cacheable
@CacheEvict
```

---

# Communication Interservices

avis-service utilise OpenFeign pour appeler produits-service.

Flux :

```text
avis-service
    |
Feign Client
    |
produits-service
```

Avant d'ajouter un avis :

- le produit est vérifié,
- si le produit n'existe pas → HTTP 404.

---

# Application Mobile Flutter

L'application mobile permet :

1. afficher les catégories,
2. afficher les produits d'une catégorie,
3. afficher les avis d'un produit.

Toutes les requêtes passent par API Gateway.

Exemple :

```text
http://IP_MACHINE:8090/api/categories
```

---

# Tests

## Tests Unitaires

Frameworks :

- JUnit 5
- Mockito

Commande :

```bash
mvn test
```

---

## Tests d'Intégration

Utilise :

- @DataJpaTest
- H2 Database

Objectif :

- tester repositories,
- tester persistance JPA.

---

## Tests E2E

Outil : Cypress

Scénario :

```text
Liste Produits
    -> Détail Produit
    -> Avis Produit
```

Lancer Cypress :

```bash
npx cypress open
```

---

# Gestion Git

## Branches

### version1

Contient :

- backend complet,
- docker compose,
- microservices.

### version2

Ajoute :

- mobile app,
- tests,
- cypress.

---

# Build Manuel des Services

## produits-service

```bash
cd produits-service
mvn clean package
```

---

## avis-service

```bash
cd avis-service
mvn clean package
```

---

## api-gateway

```bash
cd api-gateway
mvn clean package
```

---

## eureka-server

```bash
cd eureka-server
mvn clean package
```

---

# Commandes Docker Utiles

## Arrêter les services

```bash
docker compose down
```

---

## Voir les logs

```bash
docker compose logs -f
```

---

## Redémarrer un service

```bash
docker compose restart produits-service
```

---

# Problèmes Fréquents

## Port déjà utilisé

Erreur :

```text
Port already allocated
```

Solution :

- arrêter le processus utilisant le port,
- modifier le port dans docker-compose.yml.

---

## Eureka ne détecte pas les services

Vérifier :

- eureka-server démarré,
- configuration spring.application.name,
- dépendance eureka-client.

---

## Erreur PostgreSQL

Vérifier :

- conteneur PostgreSQL actif,
- username/password,
- URL JDBC.

---

# Auteur

Projet réalisé dans le cadre de l'examen pratique :

Architecture Microservices — Spring Boot 4 — Docker — Flutter

