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

import be.helha.degreve.Entities.Adaptation;
import be.helha.degreve.R;
import be.helha.degreve.Serialization.AdaptationDeserializer;
import be.helha.degreve.UI.AdaptationUiAdapter;

/**
 * Created by Alastard on 20/07/2017.
 */

public class GetAdaptations {
    private final String url = "http://54.76.209.52:8080/api-livres/services/api/adaptations";
    private Button btnReturn;
    private ListView lvT;
    private Context context;
    private List<Adaptation> livres=new ArrayList<>();

    public GetAdaptations(Button btn, ListView lv, Context cont) {
        btnReturn = btn;
        lvT=lv;
        context = cont;
    }

    /**
     * Cette méthode va communiquer directement avec l'API afin de récupérer la liste de tous les objets de types livres.
     */
    public void execute(){
        //Utilisation de Volley afin de créer une requête JSon
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
                                Adaptation livre = AdaptationDeserializer.read(item);
                                livres.add(livre);
                                System.out.println(livres);
                                AdaptationUiAdapter uiAdapter = new AdaptationUiAdapter(context, R.layout.adaptation_list_item, livres);
                                lvT.setAdapter(uiAdapter);
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

    public void execute(CharSequence s){
        List<Adaptation> filterList = new ArrayList<>();
        for(int i=0; i < livres.size(); i++){
            Adaptation item = livres.get(i);
            if(item.getTitre().toLowerCase().contains(s)){
                filterList.add(item);
            }
        }
        AdaptationUiAdapter uiAdapter = new AdaptationUiAdapter(context, R.layout.adaptation_list_item, filterList);
        lvT.setAdapter(uiAdapter);
    }
}
