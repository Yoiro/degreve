package be.helha.degreve.Entities;

import java.io.Serializable;

/**
 * Created by Alastard on 17/07/2017.
 */

public class Editeur implements Serializable{
    private Adresse adresse;
    private int id;
    private String nom;

    public Editeur(){}

    public Editeur(Adresse adresse, int id, String nom) {
        this.adresse = adresse;
        this.id = id;
        this.nom = nom;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
