package interfaces;

import model.Utilisateur;

public interface AuthentificationService {
    
    Utilisateur inscrire(String email, String motDePasse);
    
    boolean validerInscription(String email, String code);
    
    Utilisateur connecter(String email, String motDePasse);
    
    Utilisateur getUtilisateurConnecte();
    
    void deconnecter();
} 