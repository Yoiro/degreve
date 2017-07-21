package be.helha.degreve.Entities;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Alastard on 17/07/2017.
 */
public class Livre extends MotherBook implements Serializable{
    private List<Adaptation> adaptations;
    private List<Auteur> auteurs;

    public Livre(){}
    public Livre(String type, List<Editeur> editeurs, int id, String titre, List<Adaptation> adaptations, List<Auteur> auteurs) {
        super(type, editeurs, id, titre);
        this.adaptations = adaptations;
        this.auteurs = auteurs;
    }

    public List<Adaptation> getAdaptations() {
        return adaptations;
    }

    public void setAdaptations(List<Adaptation> adaptations) {
        this.adaptations = adaptations;
    }

    public List<Auteur> getAuteurs() {
        return auteurs;
    }

    public void setAuteurs(List<Auteur> auteurs) {
        this.auteurs = auteurs;
    }

    @Override
    public String toString() {
        return "Livre{" +
                "adaptations=" + adaptations +
                ", auteurs=" + auteurs +
                '}';
    }
}
