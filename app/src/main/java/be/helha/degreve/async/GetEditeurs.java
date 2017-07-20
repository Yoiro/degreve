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

import be.helha.degreve.Entities.Editeur;
import be.helha.degreve.R;
import be.helha.degreve.Serialization.EditeurDeserializer;
import be.helha.degreve.UI.EditeurUiAdapter;

/**
 * Created by Alastard on 20/07/2017.
 */

public class GetEditeurs {
    private final String url = "http://54.76.209.52:8080/api-livres/services/api/editeurs";
    private Button btnReturn;
    private ListView lvT;
    private Context context;
    private List<Editeur> list =new ArrayList<>();

    public GetEditeurs(Button btn, ListView lv, Context cont) {
        btnReturn = btn;
        lvT=lv;
        context = cont;
    }

    /**
     * Cette méthode va communiquer directement avec l'API afin de récupérer la liste de tous les objets de types list.
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
                                Editeur livre = EditeurDeserializer.read(item);
                                list.add(livre);
                                System.out.println(list);
                                EditeurUiAdapter uiAdapter = new EditeurUiAdapter(context, R.layout.editeur_list_item, list);
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
        List<Editeur> filterList = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            Editeur item = list.get(i);
            if(item.getNom().toLowerCase().contains(s)){
                filterList.add(item);
            }
        }
        EditeurUiAdapter uiAdapter = new EditeurUiAdapter(context, R.layout.editeur_list_item, filterList);
        lvT.setAdapter(uiAdapter);
    }
}
