# E-Banking Backend System

## Description

E-Banking Backend System est une application backend développée avec Spring Boot pour la gestion bancaire.  
Elle permet de gérer des clients, des comptes bancaires (courants et épargne) ainsi que des opérations financières sécurisées via une API REST.

---

## Technologies utilisées

| Technologie         | Rôle                                      |
|---------------------|------------------------------------------|
| Spring Boot         | Framework principal backend               |
| Spring MVC          | Couche Web (API REST)                    |
| Spring Data JPA     | Accès et gestion des données             |
| Hibernate           | ORM pour la persistance                  |
| H2 / MySQL          | Base de données                          |
| Spring Security     | Authentification et autorisation         |
| JWT (nimbus-jose)   | Sécurisation des API                     |
| Lombok              | Réduction du code boilerplate            |
| MapStruct           | Mapping Entity ↔ DTO                     |
| Maven               | Gestion des dépendances et build         |

---

## Fonctionnalités

| Module              | Description |
|---------------------|-------------|
| Gestion clients     | Création, modification, suppression, recherche |
| Comptes bancaires   | Comptes courants et comptes épargne |
| Opérations          | Crédit, débit, virement entre comptes |
| Historique          | Consultation paginée des transactions |
| Sécurité            | Authentification JWT et gestion des rôles |

---

## API Endpoints

### Authentification

| Méthode | Endpoint       | Description |
|----------|---------------|-------------|
| POST     | /auth/login   | Authentification et génération du token JWT |
| GET      | /auth/profile | Récupérer le profil utilisateur connecté |

---

###  Clients

| Méthode | Endpoint                | Description | Rôle |
|----------|------------------------|-------------|------|
| GET      | /customers             | Liste des clients | USER |
| GET      | /customers/search      | Recherche client | USER |
| POST     | /customers             | Ajouter client | ADMIN |
| PUT      | /customers/{id}        | Modifier client | ADMIN |
| DELETE   | /customers/{id}        | Supprimer client | ADMIN |

---

###  Comptes & opérations

| Méthode | Endpoint                        | Description |
|----------|--------------------------------|-------------|
| GET      | /accounts                      | Liste des comptes |
| GET      | /accounts/{id}                 | Détails d’un compte |
| GET      | /accounts/{id}/pageOperations  | Historique paginé |
| POST     | /accounts/saveOperation        | Effectuer une opération |

---

##  Sécurité

| Élément     | Description |
|-------------|-------------|
| Type        | Stateless API |
| Auth        | JWT |
| Algorithme  | HMAC-SHA512 |
| Rôles       | ADMIN, USER |

---

##  Comptes de test

| Username | Password | Rôles |
|----------|----------|-------|
| Admin    | 1234     | ADMIN, USER |
| User1    | 1234     | USER |

---

##  Installation

### 1. Cloner le projet
```bash
git clone https://github.com/fatima-aitoulahyan/ebanking-backend.git
