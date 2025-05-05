package model;

public class BorneRecharge {
    private static int compteur = 0;
    
    private int id;
    private EtatBorne etat;
    private double tarifHoraire;
    
    public BorneRecharge(double tarifHoraire) {
        this.id = ++compteur;
        this.etat = EtatBorne.DISPONIBLE;
        this.tarifHoraire = tarifHoraire;
    }
    
    public int getId() {
        return id;
    }
    
    public EtatBorne getEtat() {
        return etat;
    }
    
    public void setEtat(EtatBorne etat) {
        this.etat = etat;
    }
    
    public double getTarifHoraire() {
        return tarifHoraire;
    }
    
    public void setTarifHoraire(double tarifHoraire) {
        this.tarifHoraire = tarifHoraire;
    }
    
    @Override
    public String toString() {
        return "BorneRecharge [id=" + id + ", etat=" + etat + ", tarifHoraire=" + tarifHoraire + "€/h]";
    }
} 