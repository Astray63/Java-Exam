package interfaces;

import java.util.List;

import model.BorneRecharge;
import model.EtatBorne;
import model.LieuRecharge;

public interface BorneService {
    
    LieuRecharge ajouterLieu(String nom, String adresse);
    
    boolean modifierLieu(int id, String nom, String adresse);
    
    BorneRecharge ajouterBorne(int lieuId, double tarifHoraire);
    
    boolean modifierBorne(int borneId, EtatBorne etat, double tarifHoraire);
    
    boolean supprimerBorne(int borneId);
    
    LieuRecharge trouverLieuParId(int lieuId);
    
    BorneRecharge trouverBorneParId(int borneId);
    
    List<LieuRecharge> getAllLieux();
} 