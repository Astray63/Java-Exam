package ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Console {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    
    public static void afficherTitre(String titre) {
        System.out.println("\n=== " + titre + " ===");
    }
    
    public static String lireChaine(String message) {
        System.out.print(message + " ");
        return scanner.nextLine().trim();
    }
    
    public static int lireEntier(String message) {
        while (true) {
            try {
                System.out.print(message + " ");
                int valeur = Integer.parseInt(scanner.nextLine().trim());
                return valeur;
            } catch (NumberFormatException e) {
                System.out.println("Erreur: Veuillez entrer un nombre entier valide.");
            }
        }
    }
    
    public static double lireDouble(String message) {
        while (true) {
            try {
                System.out.print(message + " ");
                double valeur = Double.parseDouble(scanner.nextLine().trim());
                return valeur;
            } catch (NumberFormatException e) {
                System.out.println("Erreur: Veuillez entrer un nombre décimal valide.");
            }
        }
    }
    
    public static LocalDate lireDate(String message) {
        while (true) {
            try {
                System.out.print(message + " (format: jj/mm/aaaa): ");
                String input = scanner.nextLine().trim();
                return LocalDate.parse(input, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Erreur: Format de date invalide. Utilisez le format jj/mm/aaaa.");
            }
        }
    }
    
    public static LocalTime lireHeure(String message) {
        while (true) {
            try {
                System.out.print(message + " (format: hh:mm): ");
                String input = scanner.nextLine().trim();
                return LocalTime.parse(input, timeFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Erreur: Format d'heure invalide. Utilisez le format hh:mm.");
            }
        }
    }
    
    public static LocalDateTime lireDateTime(String message) {
        LocalDate date = lireDate(message + " - Date");
        LocalTime time = lireHeure(message + " - Heure");
        return LocalDateTime.of(date, time);
    }
    
    public static boolean confirmer(String message) {
        System.out.print(message + " (o/n): ");
        String reponse = scanner.nextLine().trim().toLowerCase();
        return reponse.equals("o") || reponse.equals("oui");
    }
} 