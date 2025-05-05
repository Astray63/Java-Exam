package services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import interfaces.BorneService;
import interfaces.ReservationService;
import model.BorneRecharge;
import model.EtatBorne;
import model.Reservation;
import model.StatutReservation;
import model.Utilisateur;

public class ReservationServiceImpl implements ReservationService {
    
    private List<Reservation> reservations;
    private BorneService borneService;
    
    public ReservationServiceImpl(BorneService borneService) {
        this.reservations = new ArrayList<>();
        this.borneService = borneService;
    }
    
    @Override
    public List<BorneRecharge> rechercherBornesDisponibles(LocalDateTime dateDebut, LocalDateTime dateFin) {
        List<BorneRecharge> bornesDisponibles = new ArrayList<>();
        
        for (var lieu : borneService.getAllLieux()) {
            for (var borne : lieu.getBornes()) {
                if (borne.getEtat() == EtatBorne.DISPONIBLE && estBorneDisponiblePourCreneau(borne, dateDebut, dateFin)) {
                    bornesDisponibles.add(borne);
                }
            }
        }
        
        return bornesDisponibles;
    }
    
    private boolean estBorneDisponiblePourCreneau(BorneRecharge borne, LocalDateTime dateDebut, LocalDateTime dateFin) {
        for (Reservation reservation : reservations) {
            if (reservation.getBorne().getId() == borne.getId() && 
                reservation.getStatut() != StatutReservation.REFUSEE && 
                reservation.getStatut() != StatutReservation.ANNULEE) {
                
                if (!(dateFin.isBefore(reservation.getDateDebut()) || dateDebut.isAfter(reservation.getDateFin()))) {
                    return false;
                }
            }
        }
        return true;
    }
    
    @Override
    public Reservation creerReservation(Utilisateur utilisateur, int borneId, LocalDateTime dateDebut, LocalDateTime dateFin) {
        BorneRecharge borne = borneService.trouverBorneParId(borneId);
        if (borne == null || borne.getEtat() != EtatBorne.DISPONIBLE || !estBorneDisponiblePourCreneau(borne, dateDebut, dateFin)) {
            return null;
        }
        
        Reservation reservation = new Reservation(utilisateur, borne, dateDebut, dateFin);
        reservations.add(reservation);
        return reservation;
    }
    
    @Override
    public boolean modifierStatutReservation(int reservationId, StatutReservation statut) {
        Reservation reservation = trouverReservationParId(reservationId);
        if (reservation != null) {
            reservation.setStatut(statut);
            return true;
        }
        return false;
    }
    
    @Override
    public List<Reservation> getReservationsUtilisateur(Utilisateur utilisateur) {
        return reservations.stream()
                .filter(r -> r.getUtilisateur().getId() == utilisateur.getId())
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Reservation> getReservationsEnAttente() {
        return reservations.stream()
                .filter(r -> r.getStatut() == StatutReservation.EN_ATTENTE)
                .collect(Collectors.toList());
    }
    
    @Override
    public Reservation trouverReservationParId(int reservationId) {
        for (Reservation reservation : reservations) {
            if (reservation.getId() == reservationId) {
                return reservation;
            }
        }
        return null;
    }
} 