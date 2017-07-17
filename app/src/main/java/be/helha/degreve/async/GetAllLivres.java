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

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import be.helha.degreve.Entities.Livre;

/**
 * Created by Alastard on 17/07/2017.
 */
public class GetAllLivres extends AsyncTask<String, Void, List<Livre>> {
    private String TAG = "Simon";
    private Button btnReturn;
    private final ListView lvT;
    private Context context;
    //private OnTaskCompleted delegate;

    public GetAllLivres(Button btn,ListView lv, T itm, Context cont) {
        btnReturn = btn;
        lvT=lv;
        item = itm;
        context = cont;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        btnReturn.setEnabled(false);
    }

    @Override
    protected List<Livre> doInBackground(String... params) {
        String urlGetAll = "http://52.31.3.254:11080/api-livres/services/api/livres";
        Log.i(TAG, "doInBackground: "+urlGetAll);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlGetAll, null,
                new Response.Listener<JSONArray>(){

                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i < response.length(); i++){
                            try {
                                Livre res = (Livre)response.get(i);
                                lvT.addView(res);
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
                }
        );

        Singleton.getInstance(context).addToRequestQueue(request);

            /*
            //Connect to the API
            URL url = new URL(urlGetAll);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream response = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(response);
            //Reading result
            BufferedReader in = new BufferedReader(reader);
            StringBuilder sb = new StringBuilder();
            String line;
            //Converting to JSon
            boolean test=false;
            while ((line = in.readLine()) != null) {
                sb.append(line);
                test=true;
            }
            String jsonResult = sb.toString();
            JSONArray jsonArray = new JSONArray(jsonResult);
            //Constructing item from JSon
            if (item.getClass().getSimpleName().equals("Editeur")) {
                List<Editeur> items=new ArrayList<>();
                for (int cpt = 0; cpt < jsonArray.length(); cpt++) {
                    JSONObject jsonEditeur = jsonArray.getJSONObject(cpt);
                    JSONObject jsonAdresse = jsonEditeur.getJSONObject("adresse");
                    int jsonCodePostal = jsonAdresse.getInt("codePostal");
                    int jsonIdAdresse = jsonAdresse.getInt("id");
                    String jsonNumAdresse = jsonAdresse.getString("numero");
                    String jsonRueAdresse = jsonAdresse.getString("rue");
                    String jsonVilleAdresse = jsonAdresse.getString("ville");
                    Adresse adresse = new Adresse(jsonCodePostal, jsonIdAdresse, jsonNumAdresse, jsonRueAdresse, jsonVilleAdresse);

                    int jsonIdEditeur = jsonEditeur.getInt("id");
                    String jsonNomEditeur = jsonEditeur.getString("nom");

                    Editeur editeur = new Editeur(adresse, jsonIdEditeur, jsonNomEditeur);
                    items.add(editeur);
                }
                return items;
            }
            else if(item.getClass().getSimpleName().equals("Publication")){
                List<Publication> items=new ArrayList<>();
                List<Editeur> edits=new ArrayList<>();
                for(int cpt=0;cpt<jsonArray.length();cpt++){
                    JSONObject currentPublication=jsonArray.getJSONObject(cpt);
                    JSONArray editeurs=currentPublication.getJSONArray("editeurs");
                    for(int i=0;i<editeurs.length();i++){
                        JSONObject currentEditor=editeurs.getJSONObject(i);
                        JSONObject jsonAdresse = currentEditor.getJSONObject("adresse");
                        int jsonCodePostal = jsonAdresse.getInt("codePostal");
                        int jsonIdAdresse = jsonAdresse.getInt("id");
                        String jsonNumAdresse = jsonAdresse.getString("numero");
                        String jsonRueAdresse = jsonAdresse.getString("rue");
                        String jsonVilleAdresse = jsonAdresse.getString("ville");
                        Adresse adresse = new Adresse(jsonCodePostal, jsonIdAdresse, jsonNumAdresse, jsonRueAdresse, jsonVilleAdresse);
                        int jsonIdEditeur = currentEditor.getInt("id");
                        String jsonNomEditeur = currentEditor.getString("nom");
                        Editeur editeur = new Editeur(adresse, jsonIdEditeur, jsonNomEditeur);
                        edits.add(editeur);
                    }
                    int jsonIdPubli=currentPublication.getInt("id");
                    String jsonTitre="[TITRE]";
                    if(!currentPublication.isNull("titre"))jsonTitre=currentPublication.getString("titre");
                    //jsobjTitre=currentPublication.getJSONObject("titre");
                    //if(!jsobjTitre.isNull("titre")){jsonTitre=currentPublication.getString("titre");}
                    //jsonTitre=currentPublication.getString("titre");
                    Publication pub=new Publication(edits,jsonIdPubli,jsonTitre);
                    items.add(pub);
                }
                return items;
            }
            else if(item.getClass().getSimpleName().equals("Auteur")){
                List<Auteur> items=new ArrayList<>();
                //"id":int, "nom":"string"
                for (int cpt = 0; cpt < jsonArray.length(); cpt++) {
                    JSONObject currentAuthor=jsonArray.getJSONObject(cpt);
                    int id=currentAuthor.getInt("id");
                    String nom=currentAuthor.getString("nom");
                    Auteur auteur=new Auteur(id,nom);
                    items.add(auteur);
                }
                return items;
            }else if(item.getClass().getSimpleName().equals("Livre")){
                //{"type":"livre",
                // "editeurs":
                //  [{"adresse":{"codePostal":9952,"id":33,"numero":"82f","rue":"rue des éditeurs","ville":"ville des éditeurs"},
                //  "id":36,"nom":"cedric_editeur2"}],
                // "id":49,"titre":"francklivre",
                List<Livre>items=new ArrayList<>();
                List<Editeur>editeurs=new ArrayList<>();
                for (int cpt = 0; cpt < jsonArray.length(); cpt++) {
                    JSONObject currentBook=jsonArray.getJSONObject(cpt);
                    String type=currentBook.getString("type");
                    JSONArray jsonEditeurs=currentBook.getJSONArray("editeurs");
                    for(int cptEdit=0;cptEdit<jsonEditeurs.length();cptEdit++){
                        JSONObject currentEdit=jsonEditeurs.getJSONObject(cptEdit);
                        JSONObject jsonAdresse=currentEdit.getJSONObject("adresse");
                        int jsonCodePostal = jsonAdresse.getInt("codePostal");
                        int jsonIdAdresse = jsonAdresse.getInt("id");
                        String jsonNumAdresse = jsonAdresse.getString("numero");
                        String jsonRueAdresse = jsonAdresse.getString("rue");
                        String jsonVilleAdresse = jsonAdresse.getString("ville");
                        Adresse adresse = new Adresse(jsonCodePostal, jsonIdAdresse, jsonNumAdresse, jsonRueAdresse, jsonVilleAdresse);

                        int jsonIdEditeur = currentEdit.getInt("id");
                        String jsonNomEditeur = currentEdit.getString("nom");

                        Editeur editeur = new Editeur(adresse, jsonIdEditeur, jsonNomEditeur);
                        editeurs.add(editeur);
                    }
                    int id=currentBook.getInt("id");
                    String titre=currentBook.getString("titre");
                    List<Adaptation> adaptations=new ArrayList<>();
                    List<Auteur> auteurs=new ArrayList<>();
                    JSONArray jsonAdaptations=currentBook.getJSONArray("adaptations");
                    for(int cptAdapt=0;cptAdapt<jsonAdaptations.length();cptAdapt++){
                        // "adaptations":
                        //  [{"annee":2012,"id":6,"titre":"Les misÃ©rables"}],
                        JSONObject currentAdaptation=jsonAdaptations.getJSONObject(cptAdapt);
                        int annee=currentAdaptation.getInt("annee");
                        int idAdapt=currentAdaptation.getInt("id");
                        String titreAdapt=currentAdaptation.getString("titre");
                        Adaptation adaptation=new Adaptation(annee,idAdapt,titreAdapt);
                        adaptations.add(adaptation);
                    }
                    // "auteurs":
                    //  [{"id":83,"nom":"franck3432"}]
                    // },
                    JSONArray jsonAuteurs=currentBook.getJSONArray("auteurs");
                    for(int cptAuteur=0;cptAuteur<jsonAuteurs.length();cptAuteur++){
                        JSONObject currentAuthor=jsonAuteurs.getJSONObject(cptAuteur);
                        int idAut=currentAuthor.getInt("id");
                        String nom=currentAuthor.getString("nom");
                        Auteur auteur=new Auteur(idAut,nom);
                        auteurs.add(auteur);
                    }
                    Livre livre=new Livre(type,editeurs,id,titre,adaptations,auteurs);
                    items.add(livre);
                }
                return items;
            } else if(item.getClass().getSimpleName().equals("Magazine")){
                //{"type":"magazine",
                // "editeurs":[
                //  {"adresse":
                //      {"codePostal":75328,"id":3,"numero":"5","rue":"Rue Gaston Gallimard","ville":"Paris"},
                //  "id":7,"nom":"Gallimard"}
                // ],
                // "id":81,"titre":"Julien Mag","periodicite":"Mensuelle"}
                List<Magazine> items=new ArrayList<>();
                List<Editeur> editeurs=new ArrayList<>();
                for(int cpt=0;cpt<jsonArray.length();cpt++){
                    JSONObject currentMag=jsonArray.getJSONObject(cpt);
                    String type=currentMag.getString("type");
                    JSONArray currentEdits=currentMag.getJSONArray("editeurs");
                    for(int cptEdit=0;cptEdit<currentEdits.length();cptEdit++){
                        JSONObject currentEdit=currentEdits.getJSONObject(cptEdit);
                        JSONObject jsonAdresse=currentEdit.getJSONObject("adresse");
                        int jsonCodePostal = jsonAdresse.getInt("codePostal");
                        int jsonIdAdresse = jsonAdresse.getInt("id");
                        String jsonNumAdresse = jsonAdresse.getString("numero");
                        String jsonRueAdresse = jsonAdresse.getString("rue");
                        String jsonVilleAdresse = jsonAdresse.getString("ville");
                        Adresse adresse = new Adresse(jsonCodePostal, jsonIdAdresse, jsonNumAdresse, jsonRueAdresse, jsonVilleAdresse);
                        int jsonIdEditeur = currentEdit.getInt("id");
                        String jsonNomEditeur = currentEdit.getString("nom");
                        Editeur editeur = new Editeur(adresse, jsonIdEditeur, jsonNomEditeur);
                        editeurs.add(editeur);
                    }
                    int id=currentMag.getInt("id");
                    String titre=currentMag.getString("titre");
                    String periodicite=currentMag.getString("periodicite");
                    Magazine magazine=new Magazine(type,editeurs,id,titre,periodicite);
                    items.add(magazine);
                }
                return items;*/
        return null;
    }

    /*@Override
    protected void onPostExecute(List<?> listView) {
        super.onPostExecute(listView);
        /*if(delegate!=null){
            delegate.onTaskCompleted(shows);
        }*/
    /*    if(item.getClass().getSimpleName().equals("Editeur")) {
            List<Editeur> listViewToEditeur = (List<Editeur>) listView;
            Editor_Ui_Adapter uiAdapter = new Editor_Ui_Adapter(context, R.layout.editor_list_item, listViewToEditeur);
            lvT.setAdapter(uiAdapter);
        }else if(item.getClass().getSimpleName().equals("Publication")){
            List<Publication> listViewToPublication=(List<Publication>) listView;
            Publication_Ui_Adapter uiAdapter=new Publication_Ui_Adapter(context,R.layout.publication_list_item,listViewToPublication);
            lvT.setAdapter(uiAdapter);
        }else if(item.getClass().getSimpleName().equals("Auteur")){
            List<Auteur> listViewToPublication=(List<Auteur>) listView;
            Auteur_Ui_Adapter uiAdapter=new Auteur_Ui_Adapter(context, R.layout.author_list_item,listViewToPublication);
            lvT.setAdapter(uiAdapter);
        }else if(item.getClass().getSimpleName().equals("Livre")){
            List<Livre> listViewToLivre=(List<Livre>)listView;
            Livre_Ui_Adapter uiAdapter=new Livre_Ui_Adapter(context,R.layout.livre_list_item,listViewToLivre);
            lvT.setAdapter(uiAdapter);
        }else if(item.getClass().getSimpleName().equals("Magazine")){
            List<Magazine> listViewToMag=(List<Magazine>)listView;
            Magazine_Ui_Adapter uiAdapter=new Magazine_Ui_Adapter(context,R.layout.magazine_list_item,listViewToMag);
            lvT.setAdapter(uiAdapter);
        }

        btnReturn.setEnabled(true);

    }*/
}
