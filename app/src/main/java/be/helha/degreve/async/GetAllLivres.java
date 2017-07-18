package be.helha.degreve.async;

import android.content.Context;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import be.helha.degreve.Entities.Livre;
import be.helha.degreve.Serialization.LivreDeserializer;

/**
 * Created by Alastard on 17/07/2017.
 */
public class GetAllLivres {
    private final String url = "http://54.76.209.52:8080/api-livres/services/api/livres";
    private Button btnReturn;
    private ListView lvT;
    private Context context;
    private List<Livre> livres=new ArrayList<>();

    public GetAllLivres(Button btn, ListView lv, Context cont) {
        btnReturn = btn;
        lvT=lv;
        context = cont;
    }

    public void execute(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i < response.length(); i++){
                            try {
                                String item = response.getString(i);
                                Livre livre = LivreDeserializer.read(item);
                                livres.add(livre);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        Singleton.getInstance(context).addToRequestQueue(request);
    }
}
