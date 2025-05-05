package services;

import java.util.ArrayList;
import java.util.List;

import interfaces.AuthentificationService;
import model.Utilisateur;

public class AuthentificationServiceImpl implements AuthentificationService {
    
    private List<Utilisateur> utilisateurs;
    private Utilisateur utilisateurConnecte;
    
    public AuthentificationServiceImpl() {
        this.utilisateurs = new ArrayList<>();
        this.utilisateurConnecte = null;
    }
    
    @Override
    public Utilisateur inscrire(String email, String motDePasse) {
        for (Utilisateur u : utilisateurs) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                return null;
            }
        }
        
        Utilisateur nouvelUtilisateur = new Utilisateur(email, motDePasse);
        utilisateurs.add(nouvelUtilisateur);
        
        return nouvelUtilisateur;
    }
    
    @Override
    public boolean validerInscription(String email, String code) {
        for (Utilisateur u : utilisateurs) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                if (!u.isEstValide() && u.getCodeValidation().equals(code)) {
                    u.validerCompte(code);
                    return true;
                }
                return false;
            }
        }
        return false;
    }
    
    @Override
    public Utilisateur connecter(String email, String motDePasse) {
        for (Utilisateur u : utilisateurs) {
            if (u.getEmail().equalsIgnoreCase(email) && u.getMotDePasse().equals(motDePasse) && u.isEstValide()) {
                this.utilisateurConnecte = u;
                return u;
            }
        }
        return null;
    }
    
    @Override
    public Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }
    
    @Override
    public void deconnecter() {
        this.utilisateurConnecte = null;
    }
} 