package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reservation {
    private static int compteur = 0;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    private int id;
    private Utilisateur utilisateur;
    private BorneRecharge borne;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private StatutReservation statut;
    
    public Reservation(Utilisateur utilisateur, BorneRecharge borne, LocalDateTime dateDebut, LocalDateTime dateFin) {
        this.id = ++compteur;
        this.utilisateur = utilisateur;
        this.borne = borne;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.statut = StatutReservation.EN_ATTENTE;
    }
    
    public int getId() {
        return id;
    }
    
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }
    
    public BorneRecharge getBorne() {
        return borne;
    }
    
    public LocalDateTime getDateDebut() {
        return dateDebut;
    }
    
    public LocalDateTime getDateFin() {
        return dateFin;
    }
    
    public StatutReservation getStatut() {
        return statut;
    }
    
    public void setStatut(StatutReservation statut) {
        this.statut = statut;
    }
    
    public double calculerCout() {
        long heures = java.time.Duration.between(dateDebut, dateFin).toHours();
        return heures * borne.getTarifHoraire();
    }
    
    @Override
    public String toString() {
        return "Réservation [id=" + id + 
               ", utilisateur=" + utilisateur.getEmail() + 
               ", borne=" + borne.getId() + 
               ", début=" + dateDebut.format(formatter) + 
               ", fin=" + dateFin.format(formatter) + 
               ", statut=" + statut + 
               ", coût=" + calculerCout() + "€]";
    }
} 