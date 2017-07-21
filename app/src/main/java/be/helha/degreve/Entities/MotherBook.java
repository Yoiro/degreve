package be.helha.degreve.Entities;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Alastard on 17/07/2017.
 */

public class MotherBook implements Serializable {
    private String type;
    private List<Editeur> editeurs;
    private int id;
    private String titre;

    public MotherBook(){ }

    public MotherBook(String type, List<Editeur> editeurs, int id, String titre) {
        this.type = type;
        this.editeurs = editeurs;
        this.id = id;
        this.titre = titre;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        return "MotherBook{" +
                "type='" + type + '\'' +
                ", editeurs=" + editeurs +
                ", id=" + id +
                ", titre='" + titre + '\'' +
                '}';
    }
}
