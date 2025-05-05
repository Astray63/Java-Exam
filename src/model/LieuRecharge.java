package model;

import java.util.ArrayList;
import java.util.List;

public class LieuRecharge {
    private static int compteur = 0;
    
    private int id;
    private String nom;
    private String adresse;
    private List<BorneRecharge> bornes;
    
    public LieuRecharge(String nom, String adresse) {
        this.id = ++compteur;
        this.nom = nom;
        this.adresse = adresse;
        this.bornes = new ArrayList<>();
    }
    
    public int getId() {
        return id;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getAdresse() {
        return adresse;
    }
    
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    
    public List<BorneRecharge> getBornes() {
        return bornes;
    }
    
    public void ajouterBorne(BorneRecharge borne) {
        bornes.add(borne);
    }
    
    public void retirerBorne(BorneRecharge borne) {
        bornes.remove(borne);
    }
    
    @Override
    public String toString() {
        return "LieuRecharge [id=" + id + ", nom=" + nom + ", adresse=" + adresse + ", nombre de bornes=" + bornes.size() + "]";
    }
} 