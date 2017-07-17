package be.helha.degreve.async;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;

/**
 * Created by Alastard on 17/07/2017.
 */

public class GetAllLivres {
    private final String url = "http://54.76.209.52:8080/api-livres/services/api/livres";
    private Context context;

    public GetAllLivres(Context context) {
        this.context = context;
    }

    public void execute(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println(response);
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
