package ui;

import java.time.LocalDateTime;
import java.util.List;

import interfaces.AuthentificationService;
import interfaces.BorneService;
import interfaces.DocumentService;
import interfaces.ReservationService;
import model.BorneRecharge;
import model.EtatBorne;
import model.LieuRecharge;
import model.Reservation;
import model.StatutReservation;
import model.Utilisateur;
import services.AuthentificationServiceImpl;
import services.BorneServiceImpl;
import services.DocumentServiceImpl;
import services.ReservationServiceImpl;

public class Main {
    private static AuthentificationService authService;
    private static BorneService borneService;
    private static ReservationService reservationService;
    private static DocumentService documentService;
    
    private static boolean modeOperateur = false;
    
    public static void main(String[] args) {
        authService = new AuthentificationServiceImpl();
        
        BorneServiceImpl borneServiceImpl = new BorneServiceImpl(null);
        borneService = borneServiceImpl;
        
        reservationService = new ReservationServiceImpl(borneService);
        borneServiceImpl.setReservationService(reservationService);
        
        documentService = new DocumentServiceImpl();
        
        menuPrincipal();
    }
    
    private static void menuPrincipal() {
        while (true) {
            Console.afficherTitre("Electricity Business");
            
            System.out.println("1. S'inscrire");
            System.out.println("2. Valider l'inscription");
            System.out.println("3. Se connecter");
            
            Utilisateur utilisateurConnecte = authService.getUtilisateurConnecte();
            if (utilisateurConnecte != null) {
                System.out.println("4. Rechercher & réserver une borne");
                System.out.println("5. Gérer mes réservations");
                System.out.println("6. Administration (lieux / bornes)");
                
                if (modeOperateur) {
                    System.out.println("7. Gérer les réservations en attente (MODE OPÉRATEUR)");
                    System.out.println("8. Revenir en mode utilisateur");
                } else {
                    System.out.println("7. Passer en mode opérateur");
                }
                
                System.out.println("9. Se déconnecter");
            }
            
            System.out.println("0. Quitter");
            
            int choix = Console.lireEntier("Votre choix:");
            
            switch (choix) {
                case 0:
                    System.out.println("Au revoir !");
                    return;
                case 1:
                    menuInscription();
                    break;
                case 2:
                    menuValidationInscription();
                    break;
                case 3:
                    menuConnexion();
                    break;
                case 4:
                    if (utilisateurConnecte != null) {
                        menuRechercheBorne();
                    } else {
                        System.out.println("Veuillez vous connecter d'abord.");
                    }
                    break;
                case 5:
                    if (utilisateurConnecte != null) {
                        menuGestionReservations();
                    } else {
                        System.out.println("Veuillez vous connecter d'abord.");
                    }
                    break;
                case 6:
                    if (utilisateurConnecte != null) {
                        menuAdministration();
                    } else {
                        System.out.println("Veuillez vous connecter d'abord.");
                    }
                    break;
                case 7:
                    if (utilisateurConnecte != null) {
                        if (modeOperateur) {
                            menuGestionReservationsAttente();
                        } else {
                            modeOperateur = true;
                            System.out.println("Vous êtes maintenant en mode opérateur.");
                        }
                    } else {
                        System.out.println("Veuillez vous connecter d'abord.");
                    }
                    break;
                case 8:
                    if (utilisateurConnecte != null && modeOperateur) {
                        modeOperateur = false;
                        System.out.println("Vous êtes maintenant en mode utilisateur.");
                    } else {
                        System.out.println("Option invalide.");
                    }
                    break;
                case 9:
                    if (utilisateurConnecte != null) {
                        authService.deconnecter();
                        modeOperateur = false;
                        System.out.println("Vous êtes déconnecté.");
                    } else {
                        System.out.println("Option invalide.");
                    }
                    break;
                default:
                    System.out.println("Option invalide.");
            }
        }
    }
    
    private static void menuInscription() {
        Console.afficherTitre("Inscription");
        
        String email = Console.lireChaine("Email:");
        String motDePasse = Console.lireChaine("Mot de passe:");
        
        Utilisateur utilisateur = authService.inscrire(email, motDePasse);
        
        if (utilisateur != null) {
            System.out.println("Inscription réussie ! Votre code de validation est: " + utilisateur.getCodeValidation());
            System.out.println("(Simulé: dans un vrai système, ce code serait envoyé par email)");
        } else {
            System.out.println("Échec de l'inscription. L'email est peut-être déjà utilisé.");
        }
    }
    
    private static void menuValidationInscription() {
        Console.afficherTitre("Validation de l'inscription");
        
        String email = Console.lireChaine("Email:");
        String code = Console.lireChaine("Code de validation:");
        
        boolean resultat = authService.validerInscription(email, code);
        
        if (resultat) {
            System.out.println("Inscription validée avec succès !");
        } else {
            System.out.println("Échec de la validation. Vérifiez votre email et votre code.");
        }
    }
    
    private static void menuConnexion() {
        Console.afficherTitre("Connexion");
        
        String email = Console.lireChaine("Email:");
        String motDePasse = Console.lireChaine("Mot de passe:");
        
        Utilisateur utilisateur = authService.connecter(email, motDePasse);
        
        if (utilisateur != null) {
            System.out.println("Connexion réussie ! Bienvenue, " + utilisateur.getEmail());
        } else {
            System.out.println("Échec de la connexion. Vérifiez vos identifiants ou validez votre compte.");
        }
    }
    
    private static void menuRechercheBorne() {
        Console.afficherTitre("Recherche de borne");
        
        LocalDateTime dateDebut = Console.lireDateTime("Date de début");
        LocalDateTime dateFin = Console.lireDateTime("Date de fin");
        
        List<BorneRecharge> bornesDisponibles = reservationService.rechercherBornesDisponibles(dateDebut, dateFin);
        
        if (bornesDisponibles.isEmpty()) {
            System.out.println("Aucune borne disponible pour ce créneau.");
            return;
        }
        
        System.out.println("\nBornes disponibles:");
        for (int i = 0; i < bornesDisponibles.size(); i++) {
            BorneRecharge borne = bornesDisponibles.get(i);
            for (LieuRecharge lieu : borneService.getAllLieux()) {
                if (lieu.getBornes().contains(borne)) {
                    System.out.printf("%d. Borne #%d - %s - %s - %.2f€/h\n", 
                            i + 1, borne.getId(), lieu.getNom(), lieu.getAdresse(), borne.getTarifHoraire());
                    break;
                }
            }
        }
        
        int choix = Console.lireEntier("Choisissez une borne (0 pour annuler):");
        
        if (choix > 0 && choix <= bornesDisponibles.size()) {
            BorneRecharge borneChoisie = bornesDisponibles.get(choix - 1);
            
            if (Console.confirmer("Voulez-vous réserver cette borne ?")) {
                Reservation reservation = reservationService.creerReservation(
                        authService.getUtilisateurConnecte(), 
                        borneChoisie.getId(), 
                        dateDebut, 
                        dateFin);
                
                if (reservation != null) {
                    System.out.println("Réservation créée avec succès !");
                    System.out.println("Statut: EN ATTENTE - Un opérateur examinera votre demande.");
                } else {
                    System.out.println("Échec de la réservation. La borne n'est plus disponible.");
                }
            }
        }
    }
    
    private static void menuGestionReservations() {
        Console.afficherTitre("Mes Réservations");
        
        Utilisateur utilisateur = authService.getUtilisateurConnecte();
        List<Reservation> reservations = reservationService.getReservationsUtilisateur(utilisateur);
        
        if (reservations.isEmpty()) {
            System.out.println("Vous n'avez aucune réservation.");
            return;
        }
        
        System.out.println("Vos réservations:");
        for (int i = 0; i < reservations.size(); i++) {
            Reservation reservation = reservations.get(i);
            System.out.printf("%d. %s\n", i + 1, reservation);
        }
    }
    
    private static void menuAdministration() {
        while (true) {
            Console.afficherTitre("Administration");
            
            System.out.println("1. Ajouter un lieu");
            System.out.println("2. Modifier un lieu");
            System.out.println("3. Ajouter une borne");
            System.out.println("4. Modifier une borne");
            System.out.println("5. Supprimer une borne");
            System.out.println("0. Retour au menu principal");
            
            int choix = Console.lireEntier("Votre choix:");
            
            switch (choix) {
                case 0:
                    return;
                case 1:
                    menuAjouterLieu();
                    break;
                case 2:
                    menuModifierLieu();
                    break;
                case 3:
                    menuAjouterBorne();
                    break;
                case 4:
                    menuModifierBorne();
                    break;
                case 5:
                    menuSupprimerBorne();
                    break;
                default:
                    System.out.println("Option invalide.");
            }
        }
    }
    
    private static void menuAjouterLieu() {
        Console.afficherTitre("Ajouter un lieu");
        
        String nom = Console.lireChaine("Nom du lieu:");
        String adresse = Console.lireChaine("Adresse:");
        
        LieuRecharge lieu = borneService.ajouterLieu(nom, adresse);
        System.out.println("Lieu ajouté avec succès ! ID: " + lieu.getId());
    }
    
    private static void menuModifierLieu() {
        Console.afficherTitre("Modifier un lieu");
        
        List<LieuRecharge> lieux = borneService.getAllLieux();
        
        if (lieux.isEmpty()) {
            System.out.println("Aucun lieu disponible.");
            return;
        }
        
        System.out.println("Lieux disponibles:");
        for (LieuRecharge lieu : lieux) {
            System.out.println(lieu);
        }
        
        int id = Console.lireEntier("ID du lieu à modifier:");
        String nom = Console.lireChaine("Nouveau nom (laissez vide pour ne pas modifier):");
        String adresse = Console.lireChaine("Nouvelle adresse (laissez vide pour ne pas modifier):");
        
        LieuRecharge lieu = borneService.trouverLieuParId(id);
        
        if (lieu != null) {
            if (!nom.isEmpty()) {
                lieu.setNom(nom);
            }
            if (!adresse.isEmpty()) {
                lieu.setAdresse(adresse);
            }
            System.out.println("Lieu modifié avec succès !");
        } else {
            System.out.println("Lieu non trouvé.");
        }
    }
    
    private static void menuAjouterBorne() {
        Console.afficherTitre("Ajouter une borne");
        
        List<LieuRecharge> lieux = borneService.getAllLieux();
        
        if (lieux.isEmpty()) {
            System.out.println("Aucun lieu disponible. Créez d'abord un lieu.");
            return;
        }
        
        System.out.println("Lieux disponibles:");
        for (LieuRecharge lieu : lieux) {
            System.out.println(lieu);
        }
        
        int lieuId = Console.lireEntier("ID du lieu où ajouter la borne:");
        double tarifHoraire = Console.lireDouble("Tarif horaire (€):");
        
        BorneRecharge borne = borneService.ajouterBorne(lieuId, tarifHoraire);
        
        if (borne != null) {
            System.out.println("Borne ajoutée avec succès ! ID: " + borne.getId());
        } else {
            System.out.println("Échec de l'ajout. Lieu introuvable.");
        }
    }
    
    private static void menuModifierBorne() {
        Console.afficherTitre("Modifier une borne");
        
        int borneId = Console.lireEntier("ID de la borne à modifier:");
        BorneRecharge borne = borneService.trouverBorneParId(borneId);
        
        if (borne == null) {
            System.out.println("Borne non trouvée.");
            return;
        }
        
        System.out.println("Borne actuelle: " + borne);
        
        System.out.println("États disponibles:");
        System.out.println("1. DISPONIBLE");
        System.out.println("2. HORS_SERVICE");
        System.out.println("3. EN_MAINTENANCE");
        int etatChoix = Console.lireEntier("Choisissez le nouvel état (0 pour ne pas modifier):");
        
        EtatBorne nouvelEtat = borne.getEtat();
        if (etatChoix >= 1 && etatChoix <= 3) {
            switch (etatChoix) {
                case 1:
                    nouvelEtat = EtatBorne.DISPONIBLE;
                    break;
                case 2:
                    nouvelEtat = EtatBorne.HORS_SERVICE;
                    break;
                case 3:
                    nouvelEtat = EtatBorne.EN_MAINTENANCE;
                    break;
            }
        }
        
        double tarifHoraire = Console.lireDouble("Nouveau tarif horaire (€) (actuel: " + borne.getTarifHoraire() + "):");
        
        boolean resultat = borneService.modifierBorne(borneId, nouvelEtat, tarifHoraire);
        
        if (resultat) {
            System.out.println("Borne modifiée avec succès !");
        } else {
            System.out.println("Échec de la modification.");
        }
    }
    
    private static void menuSupprimerBorne() {
        Console.afficherTitre("Supprimer une borne");
        
        int borneId = Console.lireEntier("ID de la borne à supprimer:");
        
        if (borneService.trouverBorneParId(borneId) == null) {
            System.out.println("Borne non trouvée.");
            return;
        }
        
        boolean resultat = borneService.supprimerBorne(borneId);
        
        if (resultat) {
            System.out.println("Borne supprimée avec succès !");
        } else {
            System.out.println("Échec de la suppression. La borne a peut-être des réservations futures.");
        }
    }
    
    private static void menuGestionReservationsAttente() {
        Console.afficherTitre("Gestion des réservations en attente");
        
        List<Reservation> reservationsEnAttente = reservationService.getReservationsEnAttente();
        
        if (reservationsEnAttente.isEmpty()) {
            System.out.println("Aucune réservation en attente.");
            return;
        }
        
        System.out.println("Réservations en attente:");
        for (int i = 0; i < reservationsEnAttente.size(); i++) {
            Reservation reservation = reservationsEnAttente.get(i);
            System.out.printf("%d. %s\n", i + 1, reservation);
        }
        
        int choix = Console.lireEntier("Choisissez une réservation à traiter (0 pour annuler):");
        
        if (choix > 0 && choix <= reservationsEnAttente.size()) {
            Reservation reservation = reservationsEnAttente.get(choix - 1);
            
            System.out.println("\nDétails de la réservation:");
            System.out.println(reservation);
            
            System.out.println("\n1. Accepter");
            System.out.println("2. Refuser");
            System.out.println("0. Annuler");
            
            int action = Console.lireEntier("Votre action:");
            
            switch (action) {
                case 1:
                    if (reservationService.modifierStatutReservation(reservation.getId(), StatutReservation.ACCEPTEE)) {
                        System.out.println("Réservation acceptée !");
                        documentService.genererRecu(reservation);
                        System.out.println("Reçu généré dans le dossier 'exports'.");
                    } else {
                        System.out.println("Échec de l'acceptation.");
                    }
                    break;
                case 2:
                    if (reservationService.modifierStatutReservation(reservation.getId(), StatutReservation.REFUSEE)) {
                        System.out.println("Réservation refusée.");
                    } else {
                        System.out.println("Échec du refus.");
                    }
                    break;
                default:
                    System.out.println("Action annulée.");
            }
        }
    }
} 