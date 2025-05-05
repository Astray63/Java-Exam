package services;

import java.util.ArrayList;
import java.util.List;

import interfaces.BorneService;
import interfaces.ReservationService;
import model.BorneRecharge;
import model.EtatBorne;
import model.LieuRecharge;
import model.StatutReservation;

public class BorneServiceImpl implements BorneService {
    
    private List<LieuRecharge> lieux;
    private ReservationService reservationService;
    
    public BorneServiceImpl(ReservationService reservationService) {
        this.lieux = new ArrayList<>();
        this.reservationService = reservationService;
    }
    
    public void setReservationService(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
    
    @Override
    public LieuRecharge ajouterLieu(String nom, String adresse) {
        LieuRecharge nouveauLieu = new LieuRecharge(nom, adresse);
        lieux.add(nouveauLieu);
        return nouveauLieu;
    }
    
    @Override
    public boolean modifierLieu(int id, String nom, String adresse) {
        LieuRecharge lieu = trouverLieuParId(id);
        if (lieu != null) {
            lieu.setNom(nom);
            lieu.setAdresse(adresse);
            return true;
        }
        return false;
    }
    
    @Override
    public BorneRecharge ajouterBorne(int lieuId, double tarifHoraire) {
        LieuRecharge lieu = trouverLieuParId(lieuId);
        if (lieu != null) {
            BorneRecharge nouvelleBorne = new BorneRecharge(tarifHoraire);
            lieu.ajouterBorne(nouvelleBorne);
            return nouvelleBorne;
        }
        return null;
    }
    
    @Override
    public boolean modifierBorne(int borneId, EtatBorne etat, double tarifHoraire) {
        BorneRecharge borne = trouverBorneParId(borneId);
        if (borne != null) {
            borne.setEtat(etat);
            borne.setTarifHoraire(tarifHoraire);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean supprimerBorne(int borneId) {
        if (reservationService.trouverReservationParId(borneId) != null) {
            return false;
        }
        
        for (LieuRecharge lieu : lieux) {
            for (BorneRecharge borne : lieu.getBornes()) {
                if (borne.getId() == borneId) {
                    lieu.retirerBorne(borne);
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public LieuRecharge trouverLieuParId(int lieuId) {
        for (LieuRecharge lieu : lieux) {
            if (lieu.getId() == lieuId) {
                return lieu;
            }
        }
        return null;
    }
    
    @Override
    public BorneRecharge trouverBorneParId(int borneId) {
        for (LieuRecharge lieu : lieux) {
            for (BorneRecharge borne : lieu.getBornes()) {
                if (borne.getId() == borneId) {
                    return borne;
                }
            }
        }
        return null;
    }
    
    @Override
    public List<LieuRecharge> getAllLieux() {
        return new ArrayList<>(lieux);
    }
} 