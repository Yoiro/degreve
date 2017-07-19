package be.helha.degreve.Serialization;

import com.google.gson.Gson;

import be.helha.degreve.Entities.Magazine;


/**
 * Created by Alastard on 19/07/2017.
 */

public class MagazineDeserializer {
    public static Magazine read(String jsonString){
        return new Gson().fromJson(jsonString, Magazine.class);
    }

    public static String write(Magazine livre){
        return new Gson().toJson(livre);
    }
}
