package be.helha.degreve.Entities;

import java.io.Serializable;

/**
 * Created by Alastard on 17/07/2017.
 */

public class Adresse implements Serializable{
    private int codePostal;
    private int id;
    private String numero;
    private String rue;
    private String ville;

    public Adresse(){}

    public Adresse(int codePostal, int id, String numero, String rue, String ville) {
        this.codePostal = codePostal;
        this.id = id;
        this.numero = numero;
        this.rue = rue;
        this.ville = ville;
    }

    public int getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    @Override
    public String toString() {
        return "Adresse{" +
                "codePostal=" + codePostal +
                ", id=" + id +
                ", numero='" + numero + '\'' +
                ", rue='" + rue + '\'' +
                ", ville='" + ville + '\'' +
                '}';
    }
}
