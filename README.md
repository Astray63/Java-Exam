# Electricity Business - Elijah LASSERRE

Application de gestion de bornes de recharge électrique.

## Fonctionnalités implémentées (core)

### Comptes
- Inscription avec génération d'un code
- Validation du compte par le code
- Connexion / déconnexion

### Bornes & lieux
- Ajouter / modifier un lieu
- Ajouter / modifier une borne
- Supprimer une borne (si aucune réservation future)

### Réservation
- Chercher bornes disponibles pour un créneau
- Créer une réservation
- Accepter / refuser une réservation

### Documents
- Générer un reçu texte (.txt) lors de l'acceptation

### IHM console
- Menu principal clair
- Validation des entrées utilisateur

## Comment utiliser l'application

1. Compilation : `javac -d bin src/**/*.java`
2. Exécution : `java -cp bin ui.Main`

## Structure du projet

- `model` - Entités : `Utilisateur`, `LieuRecharge`, `BorneRecharge`, `Reservation`, énumérations `EtatBorne`, `StatutReservation`
- `interfaces` - Abstractions : `AuthentificationService`, `ReservationService`, `BorneService`, `DocumentService`
- `services` - Implémentations en mémoire
- `ui` - Interface console et classe Main
- `exports` - Dossier où sont générés les reçus
