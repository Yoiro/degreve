package be.helha.degreve.async;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import be.helha.degreve.Activities.MainActivity;
import be.helha.degreve.Entities.Livre;

/**
 * Created by Alastard on 17/07/2017.
 */
public class GetAllLivres {
    private final String url = "http://54.76.209.52:8080/api-livres/services/api/livres";
    private Button btnReturn;
    private ListView lvT;
    private Context context;

    public GetAllLivres(Button btn, ListView lv, Context cont) {
        btnReturn = btn;
        lvT=lv;
        context = cont;

//        btnReturn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    public void execute(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i < response.length(); i++){
                            try {
                                Livre listItem = (Livre)response.get(i);
                                System.out.println(listItem);
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
