package be.helha.degreve.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import be.helha.degreve.Entities.Livre;
import be.helha.degreve.R;
import be.helha.degreve.UI.LivreUiAdapter;

/**
 * Created by Alastard on 17/07/2017.
 */
public class GetAllLivresAT extends AsyncTask<String, Void, List<Livre>> {
    private String TAG = "Simon";
    private Button btnReturn;
    private ListView lvT;
    private Context context;
    private List<Livre> livres=new ArrayList<>();
    //private OnTaskCompleted delegate;

    public GetAllLivresAT(Button btn,ListView lv, Context cont) {
        btnReturn = btn;
        lvT=lv;
        context = cont;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        btnReturn.setEnabled(false);
    }

    @Override
    protected void onPostExecute(List<Livre> livres) {
        super.onPostExecute(livres);
        /*if(delegate!=null){
            delegate.onTaskCompleted(shows);
        }*/
        LivreUiAdapter uiAdapter=new LivreUiAdapter(context, R.layout.livre_list_item,livres);
        lvT.setAdapter(uiAdapter);
        btnReturn.setEnabled(true);
    }

    @Override
    protected List<Livre> doInBackground(String... params) {
        String urlGetAll = "http://54.76.209.52:8080/api-livres/services/api/livres";
        Log.i(TAG, "doInBackground: " + urlGetAll);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlGetAll, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                Livre livre = (Livre) response.get(i);
                                addLivre(livre);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );

        Singleton.getInstance(context).addToRequestQueue(request);
        return livres;
    }

    private void addLivre(Livre livre){
        livres.add(livre);
    }

}
