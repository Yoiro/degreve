package be.helha.degreve.async;

import android.content.Context;
import android.os.AsyncTask;
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
import be.helha.degreve.Entities.Magazine;
import be.helha.degreve.Entities.Publication;
import be.helha.degreve.R;
import be.helha.degreve.Serialization.PublicationDeserializer;
import be.helha.degreve.UI.PublicationUiAdapter;

/**
 * Created by Alastard on 20/07/2017.
 */

public class GetPublications {
    private final String url = "http://54.76.209.52:8080/api-livres/services/api/publications";
    private Button btnReturn;
    private ListView lvT;
    private Context context;
    private List<Publication> publications =new ArrayList<>();


    public GetPublications(Button btn, ListView lv, Context cont) {
        btnReturn = btn;
        lvT=lv;
        context = cont;
    }

    public GetPublications(Context context){
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
                                Publication livre = PublicationDeserializer.read(item);
                                publications.add(livre);
                                Singleton.getInstance(context).setPublications(publications);
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

    /**
     * Cette méthode va communiquer directement avec l'API afin de récupérer la liste de tous les objets de types publications.
     */
    public void execute(){
        AsyncTask<String, Void, List<Publication>> async = new AsyncTask<String, Void, List<Publication>>() {
            @Override
            protected List<Publication> doInBackground(String... params) {
                publications = Singleton.getInstance(context).getPublications();
                return publications;
            }
        };
        async.execute();
    }

    public void execute(CharSequence s){
        List<Publication> filterList = new ArrayList<>();
        for(int i = 0; i < publications.size(); i++){
            Publication item = publications.get(i);
            if(item.getTitre().toLowerCase().contains(s)){
                filterList.add(item);
            }
        }
        PublicationUiAdapter uiAdapter = new PublicationUiAdapter(context, R.layout.publication_list_item, filterList);
        lvT.setAdapter(uiAdapter);
    }

}
