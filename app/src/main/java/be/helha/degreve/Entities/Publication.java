package be.helha.degreve.Entities;

import android.content.Context;

import java.io.Serializable;
import java.util.List;


/**
 * Created by Alastard on 17/07/2017.
 */

public class Publication implements Serializable {
    private List<Editeur> editeurs;
    private int id;
    private String titre;

    public Publication(){}

    public Publication(String type, List<Editeur> editeurs, int id, String titre) {
        this.editeurs = editeurs;
        this.id = id;
        this.titre = titre;
    }

    public List<Editeur> getEditeurs() {
        return editeurs;
    }

    public void setEditeurs(List<Editeur> editeurs) {
        this.editeurs = editeurs;
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
        return "Publication{" +
                "editeurs=" + editeurs +
                ", id=" + id +
                ", titre='" + titre + '\'' +
                '}';
    }
}
