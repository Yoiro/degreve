package be.helha.degreve.Serialization;

import com.google.gson.Gson;

import java.io.Serializable;

import be.helha.degreve.Entities.Publication;

/**
 * Created by Alastard on 20/07/2017.
 */

public class PublicationDeserializer implements Serializable {

    public static Publication read(String jsonString){
        return new Gson().fromJson(jsonString, Publication.class);
    }

    public static String write(Publication livre){
        return new Gson().toJson(livre);
    }

}
