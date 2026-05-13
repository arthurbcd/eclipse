# Cahier des charges - Love Sushi 🍣

## 1. Présentation générale du projet
L'objectif est de fournir une application web complète pour un restaurant de sushis. Cette solution doit permettre aux clients de réserver une table en ligne tout en offrant au restaurateur une interface d'administration (**Back-Office**) pour gérer dynamiquement sa carte de sushis et les préférences des clients.

## 2. Contexte et justification
Le restaurant de sushis souhaite se doter d'une plateforme numérique pour répondre à la demande croissante de services en ligne (réservation, consultation de menu à jour). Ce projet centralise la présence en ligne et la gestion opérationnelle du menu et des réservations en une solution unique et efficace.

## 3. Fonctionnalités
Le projet est divisé en deux interfaces principales :

### 3.1. Front-office (Interface Client)
*   **Vitrine du restaurant** : Page de présentation affichant les informations générales de l'établissement.
*   **Consultation de la carte** : Affichage dynamique de la liste des sushis disponibles, récupérée depuis le serveur.
*   **Réservation de table** : Formulaire interactif pour saisir une demande (date, nombre de couverts, nom, contact) avec contrôle de validité des saisies.
*   **Questionnaire** : Lors de la réservation, le client indique ses préférences de sushis (via le moteur de questions/réponses).

### 3.2. Back-office (Interface Restaurant)
*   **Gestion des sushis et du menu** : Outil d'administration permettant d’ajouter, modifier ou supprimer des plats (sushis, boissons).
*   **Gestion des Questions** : Interface pour configurer les questions de préférences posées aux clients.
*   **Consultation des Réponses** : Visualisation des choix et préférences soumis par les clients.

## 4. Exigences spécifiques
*   **Technologies Front** : Page de présentation en HTML5/CSS3. Utilisation de **Bootstrap 5** pour garantir un design **Responsive** (adapté aux mobiles et tablettes).
*   **Persistance des données** : Les données du menu, des questions et des réservations sont stockées de manière persistante (via Repository/Singleton).
*   **API REST** : Fourniture d'une API REST pour la consultation du menu et des réservations, permettant une interopérabilité avec d'autres services ou composants (ex: React).
*   **Environnement d'exécution** : L'application est développée sur l'IDE **Eclipse** et déployée sur un serveur **Apache Tomcat 10** (utilisant la spécification Jakarta EE).

## 5. Livrables
*   **Code Source** : Projet complet structuré pour Eclipse.
*   **Cahier des Charges** : Ce document (`Cahier_des_charges.md`).
*   **Manuel Utilisateur** : Guide d'utilisation des interfaces Front et Back.
*   **Javadoc** : Documentation technique générée à partir du code source Java.
*   **Documentation technique** : Détails sur l'architecture (Servlets, Modèles, API REST).
