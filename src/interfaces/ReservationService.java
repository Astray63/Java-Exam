package interfaces;

import java.time.LocalDateTime;
import java.util.List;

import model.BorneRecharge;
import model.Reservation;
import model.StatutReservation;
import model.Utilisateur;

public interface ReservationService {
    
    List<BorneRecharge> rechercherBornesDisponibles(LocalDateTime dateDebut, LocalDateTime dateFin);
    
    Reservation creerReservation(Utilisateur utilisateur, int borneId, LocalDateTime dateDebut, LocalDateTime dateFin);
    
    boolean modifierStatutReservation(int reservationId, StatutReservation statut);
    
    List<Reservation> getReservationsUtilisateur(Utilisateur utilisateur);
    
    List<Reservation> getReservationsEnAttente();
    
    Reservation trouverReservationParId(int reservationId);
} 