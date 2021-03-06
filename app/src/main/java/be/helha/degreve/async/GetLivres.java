package be.helha.degreve.async;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import be.helha.degreve.Entities.Livre;
import be.helha.degreve.R;
import be.helha.degreve.Serialization.LivreDeserializer;
import be.helha.degreve.UI.LivreUiAdapter;

/**
 * Created by Alastard on 17/07/2017.
 */
public class GetLivres {
    private final String url = "http://54.76.209.52:8080/api-livres/services/api/livres";
    private Button btnReturn;
    private ListView lvT;
    private Context context;
    private List<Livre> livres=new ArrayList<>();

    public GetLivres(Button btn, ListView lv, Context cont) {
        btnReturn = btn;
        lvT=lv;
        context = cont;
    }

    public GetLivres(Context context) {
        this.context = context;
    }

    public void fill_Singleton(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>(){
                    //Ici, nous récupérons la réponse à la requête
                    @Override
                    public void onResponse(JSONArray response) {
                        //Lorsque nous recevons une réponse (sous forme d'Array Json),
                        //nous allons itérer sur chaque élément de celle-ci afin de rajouter chaque élément à notre liste de Livre.
                        for(int i = 0; i < response.length(); i++){
                            try {
                                String item = response.getString(i);
                                Livre livre = LivreDeserializer.read(item);
                                livres.add(livre);
                                Singleton.getInstance(context).setLivres(livres);
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
        //Maintenant que nous avons défini le comportement de la requête en cas de réponse et en cas d'erreur, nous l'ajoutons à la queue de requêtes (Volley)
        Singleton.getInstance(context).addToRequestQueue(request);
    }

    public void execute(){
        AsyncTask<String, Void, List<Livre>> async = new AsyncTask<String, Void, List<Livre>>() {
            @Override
            protected List<Livre> doInBackground(String... params) {
                livres = Singleton.getInstance(context).getLivres();
                return livres;
            }
        };
        async.execute();
    }

    public void execute(CharSequence s){
        List<Livre> filterList = new ArrayList<>();
        for(int i=0; i < livres.size(); i++){
            Livre item = livres.get(i);
            if(item.getTitre().toLowerCase().contains(s)){
                filterList.add(item);
            }
        }
        LivreUiAdapter uiAdapter = new LivreUiAdapter(context, R.layout.livre_list_item, filterList);
        lvT.setAdapter(uiAdapter);
    }

    public void updateUi(){
        LivreUiAdapter uiAdapter = new LivreUiAdapter(context, R.layout.livre_list_item, livres);
        lvT.setAdapter(uiAdapter);
    }
}
