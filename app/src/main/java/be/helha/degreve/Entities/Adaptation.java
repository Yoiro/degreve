package be.helha.degreve.Entities;

import java.io.Serializable;

/**
 * Created by Alastard on 17/07/2017.
 */

public class Adaptation implements Serializable{
    private int annee;
    private int id;
    private String titre;

    public Adaptation(){}
    public Adaptation(int annee, int id, String titre) {
        this.annee = annee;
        this.id = id;
        this.titre = titre;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    @Override
    public String toString() {
        return "Adaptation{" +
                "annee=" + annee +
                ", id=" + id +
                ", titre='" + titre + '\'' +
                '}';
    }
}
