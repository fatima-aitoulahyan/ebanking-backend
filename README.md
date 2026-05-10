E-Banking Backend System

Description
E-Banking Backend System est une application backend développée avec Spring Boot pour la gestion bancaire. Elle permet de gérer des clients, des comptes bancaires (courants et épargne) ainsi que des opérations financières sécurisées via une API REST.

Fonctionnalités

Gestion bancaire
- Création et gestion des clients
- Comptes courants et comptes épargne
- Opérations : crédit, débit et virement
- Vérification automatique du solde
- Gestion des découverts pour les comptes courants

Historique
- Historique des transactions
- Pagination des opérations

Sécurité
- Authentification JWT
- Contrôle d’accès basé sur les rôles (ADMIN / USER)
- API sécurisée (stateless)

Stack technique
- Spring Boot 3
- Spring Security + JWT (nimbus-jose-jwt)
- Spring Data JPA / Hibernate
- Base de données H2 ou MySQL
- DTO + MapStruct
- Lombok

Architecture du projet
- entities : modèles JPA
- repositories : accès aux données
- services : logique métier
- web : contrôleurs REST
- dtos : objets de transfert de données
- mappers : conversion Entity / DTO

API Endpoints

Authentification
POST /auth/login : authentification et génération du token JWT
GET /auth/profile : utilisateur connecté

Clients
GET /customers : liste des clients (USER)
GET /customers/search?keyword= : recherche client (USER)
POST /customers : ajouter un client (ADMIN)
PUT /customers/{id} : modifier un client (ADMIN)
DELETE /customers/{id} : supprimer un client (ADMIN)

Comptes et opérations
GET /accounts : liste des comptes
GET /accounts/{id} : détails d’un compte
GET /accounts/{id}/pageOperations : historique paginé
POST /accounts/saveOperation : effectuer une opération (crédit, débit, transfert)

Sécurité
- Application stateless avec JWT
- Algorithme HMAC-SHA512
- Rôles :
  - ADMIN : accès complet
  - USER : accès lecture et opérations

Comptes de test
Admin / 1234 (ADMIN, USER)
User1 / 1234 (USER)

Installation

Cloner le projet :
git clone https://github.com/votre-username/ebanking-backend.git

Configurer la clé JWT :
Dans src/main/resources/application.properties :
jwt.secret=votre_cle_secrete_512_bits_minimum

Lancer le projet :
mvn spring-boot:run

Auteur
Fatima AITOULAHYAN
