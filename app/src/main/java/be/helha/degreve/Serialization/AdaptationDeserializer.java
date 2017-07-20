package be.helha.degreve.Serialization;

import com.google.gson.Gson;

import be.helha.degreve.Entities.Adaptation;

/**
 * Created by Alastard on 20/07/2017.
 */

public class AdaptationDeserializer {
    public static Adaptation read(String jsonString){
        return new Gson().fromJson(jsonString, Adaptation.class);
    }

    public static String write(Adaptation livre){
        return new Gson().toJson(livre);
    }
}
