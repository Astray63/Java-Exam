package services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import interfaces.DocumentService;
import model.Reservation;
import model.StatutReservation;

public class DocumentServiceImpl implements DocumentService {
    
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final String EXPORT_DIR = "exports";
    
    public DocumentServiceImpl() {
        File dir = new File(EXPORT_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    
    @Override
    public boolean genererRecu(Reservation reservation) {
        if (reservation == null || reservation.getStatut() != StatutReservation.ACCEPTEE) {
            return false;
        }
        
        String nomFichier = String.format("%s/recu_%d.txt", EXPORT_DIR, reservation.getId());
        
        try (FileWriter writer = new FileWriter(nomFichier)) {
            writer.write("===============================================\n");
            writer.write("            ELECTRICITY BUSINESS               \n");
            writer.write("===============================================\n\n");
            
            writer.write("REÇU DE RÉSERVATION\n\n");
            
            writer.write(String.format("Numéro de réservation: %d\n", reservation.getId()));
            writer.write(String.format("Client: %s\n", reservation.getUtilisateur().getEmail()));
            writer.write(String.format("Borne: %d\n", reservation.getBorne().getId()));
            writer.write(String.format("Tarif horaire: %.2f €/h\n", reservation.getBorne().getTarifHoraire()));
            writer.write("\n");
            
            writer.write(String.format("Date de début: %s\n", reservation.getDateDebut().format(formatter)));
            writer.write(String.format("Date de fin: %s\n", reservation.getDateFin().format(formatter)));
            writer.write("\n");
            
            writer.write(String.format("Durée: %d heure(s)\n", 
                    java.time.Duration.between(reservation.getDateDebut(), reservation.getDateFin()).toHours()));
            writer.write(String.format("Montant total: %.2f €\n", reservation.calculerCout()));
            writer.write("\n");
            
            writer.write("Merci de votre confiance !\n");
            writer.write("===============================================\n");
            
            return true;
        } catch (IOException e) {
            System.err.println("Erreur lors de la génération du reçu: " + e.getMessage());
            return false;
        }
    }
} 