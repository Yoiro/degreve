package be.helha.degreve.Entities;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Alastard on 17/07/2017.
 */

public class Magazine extends MotherBook implements Serializable {
    private String periodicite;

    public Magazine(){}

    public Magazine(String periodicite) {
        this.periodicite = periodicite;
    }

    public Magazine(String type, List<Editeur> editeurs, int id, String titre, String periodicite) {
        super(type, editeurs, id, titre);
        this.periodicite = periodicite;
    }

    public String getPeriodicite() {
        return periodicite;
    }

    public void setPeriodicite(String periodicite) {
        this.periodicite = periodicite;
    }

    @Override
    public String toString() {
        return "Magazine{" +
                "periodicite='" + periodicite + '\'' +
                '}';
    }
}
