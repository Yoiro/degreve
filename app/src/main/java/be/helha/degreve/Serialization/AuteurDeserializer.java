package be.helha.degreve.Serialization;

import com.google.gson.Gson;
import be.helha.degreve.Entities.Auteur;

/**
 * Created by Alastard on 20/07/2017.
 */

public class AuteurDeserializer {
    public static Auteur read(String jsonString){
        return new Gson().fromJson(jsonString, Auteur.class);
    }

    public static String write(Auteur livre){
        return new Gson().toJson(livre);
    }
}
