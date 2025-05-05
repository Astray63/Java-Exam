package model;

public class Utilisateur {
    private static int compteur = 0;
    
    private int id;
    private String email;
    private String motDePasse;
    private String codeValidation;
    private boolean estValide;
    
    public Utilisateur(String email, String motDePasse) {
        this.id = ++compteur;
        this.email = email;
        this.motDePasse = motDePasse;
        this.codeValidation = genererCode();
        this.estValide = false;
    }
    
    private String genererCode() {
        return String.format("%06d", (int) (Math.random() * 1000000));
    }
    
    public int getId() {
        return id;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getMotDePasse() {
        return motDePasse;
    }
    
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
    
    public String getCodeValidation() {
        return codeValidation;
    }
    
    public boolean isEstValide() {
        return estValide;
    }
    
    public void validerCompte(String code) {
        if (code != null && code.equals(codeValidation)) {
            this.estValide = true;
        }
    }
    
    @Override
    public String toString() {
        return "Utilisateur [id=" + id + ", email=" + email + ", estValide=" + estValide + "]";
    }
} 