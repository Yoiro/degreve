package be.helha.degreve.Entities;

import java.io.Serializable;

/**
 * Created by Alastard on 17/07/2017.
 */
public class Auteur implements Serializable{
    private int id;
    private String nom;

    public Auteur(){}

    public Auteur(int id, String nom) {
        this.id = id;
        this.nom = nom;
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
