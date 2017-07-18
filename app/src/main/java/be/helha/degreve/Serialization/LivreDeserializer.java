package be.helha.degreve.Serialization;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

import be.helha.degreve.Entities.Livre;

/**
 * Created by Alastard on 18/07/2017.
 */

public class LivreDeserializer implements Serializable {

    public static Livre read(String jsonString){
        return new Gson().fromJson(jsonString, Livre.class);
    }

}
