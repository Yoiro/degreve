package be.helha.degreve.Serialization;

import com.google.gson.Gson;

import java.io.Serializable;

import be.helha.degreve.Entities.Livre;

/**
 * Created by Alastard on 18/07/2017.
 */

public class LivreDeserializer implements Serializable {

    public static Livre read(String jsonString){
        return new Gson().fromJson(jsonString, Livre.class);
    }

}
