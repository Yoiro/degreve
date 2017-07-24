package be.helha.degreve.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import be.helha.degreve.Entities.Adresse;
import be.helha.degreve.Entities.Editeur;
import be.helha.degreve.Entities.Livre;
import be.helha.degreve.Entities.Magazine;
import be.helha.degreve.Entities.Publication;
import be.helha.degreve.R;
import be.helha.degreve.Serialization.PublicationDeserializer;
import be.helha.degreve.UI.PublicationUiAdapter;
import be.helha.degreve.async.Singleton;

public class GetEditeurActivity extends AppCompatActivity {

    private Editeur editeur;
    private List<Publication> publications = new ArrayList<>();

    private Button btnReturn;
    private Button btnMainMenu;
    private TextView tvNom;
    private TextView nomclasse;
    private TextView num;
    private TextView rue;
    private TextView codepostal;
    private TextView ville;

    private ListView lvLivres;

    private boolean isLivre;
    private boolean isMagazine;

    public void findPublications(){
        String url = "http://54.76.209.52:8080/api-livres/services/api/publications";
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i<response.length(); i++){
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Publication publication = PublicationDeserializer.read(obj.toString());
                        for(Editeur e: publication.getEditeurs()){
                            if(e.getId() == editeur.getId()){
                                publications.add(publication);
                                setPublications();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        Singleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    public void setPublications(){
        lvLivres = (ListView) findViewById(R.id.lvlivres);
        PublicationUiAdapter uiAdapter = new PublicationUiAdapter(getApplicationContext(), R.layout.publication_list_item, publications);
        lvLivres.setAdapter(uiAdapter);
        lvLivres.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Publication book = (Publication)lvLivres.getItemAtPosition(position);
                Livre livre = isLivre(book);
                if (isLivre){
                    Intent intent = new Intent(getApplicationContext(), GetLivreActivity.class);
                    intent.putExtra("book", livre);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else{
                    Magazine mag = isMagazine(book);
                    if(isMagazine){
                        Intent intent = new Intent(getApplicationContext(), GetMagazineActivity.class);
                        intent.putExtra("book", mag);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_editeur);
        Intent i = getIntent();
        i.getExtras();
        editeur = (Editeur) i.getSerializableExtra("book");

        tvNom = (TextView) findViewById(R.id.nom);
        nomclasse = (TextView) findViewById(R.id.nomclasse);
        num = (TextView) findViewById(R.id.num);
        rue = (TextView) findViewById(R.id.rue);
        codepostal = (TextView) findViewById(R.id.codepostal);
        ville = (TextView) findViewById(R.id.ville);
        btnReturn = (Button) findViewById(R.id.btnReturn_Details);
        btnMainMenu = (Button) findViewById(R.id.btnMainMenu_Details);

        tvNom.setText(editeur.getNom());
        nomclasse.setText(editeur.getClass().getSimpleName());
        Adresse a = editeur.getAdresse();
        num.setText(a.getNumero());
        rue.setText(a.getRue());
        codepostal.setText(String.format(Locale.getDefault(), "%d", a.getCodePostal()));
        ville.setText(a.getVille());

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                setResult(Activity.RESULT_CANCELED, intent);
                finish();
            }
        });
        btnMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        findPublications();
    }

    public Livre isLivre(Publication p){
        List<Livre> livreList = Singleton.getInstance(getApplicationContext()).getLivres();
        for(int i = 0; i < livreList.size(); i++){
            if(livreList.get(i).getTitre().equals(p.getTitre())) {
                isLivre = true;
                return livreList.get(i);
            }
        }
        return null;
    }

    public Magazine isMagazine(Publication p){
        List<Magazine> magazineList = Singleton.getInstance(getApplicationContext()).getMagazines();
        for(int i = 0; i < magazineList.size(); i++){
            if(magazineList.get(i).getTitre().equals(p.getTitre())){
                isMagazine = true;
                return magazineList.get(i);
            }
        }
        return null;
    }
}
