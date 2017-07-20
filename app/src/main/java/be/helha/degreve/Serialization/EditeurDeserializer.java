package be.helha.degreve.Serialization;

import com.google.gson.Gson;

import be.helha.degreve.Entities.Editeur;

/**
 * Created by Alastard on 20/07/2017.
 */

public class EditeurDeserializer {
    public static Editeur read(String jsonString){
        return new Gson().fromJson(jsonString, Editeur.class);
    }

    public static String write(Editeur livre){
        return new Gson().toJson(livre);
    }
}
