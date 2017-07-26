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

import be.helha.degreve.Entities.Auteur;
import be.helha.degreve.Entities.Livre;
import be.helha.degreve.R;
import be.helha.degreve.Serialization.LivreDeserializer;
import be.helha.degreve.UI.LivreUiAdapter;
import be.helha.degreve.async.Singleton;

public class GetAuteurActivity extends AppCompatActivity {

    private Auteur auteur;
    private List<Livre> livres = new ArrayList<>();
    private TextView tvNom;
    private TextView nomclasse;
    private ListView lvLivres;
    private Button btnReturn;
    private Button btnMainMenu;

    public void findPublications(){
        String url = "http://54.76.209.52:8080/api-livres/services/api/livres";
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i<response.length(); i++){
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Livre livre = LivreDeserializer.read(obj.toString());
                        for(Auteur a: livre.getAuteurs()){
                            if(a.getId() == auteur.getId()){
                                livres.add(livre);
                                setLivres();
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

    public void setLivres(){
        lvLivres = (ListView) findViewById(R.id.lvlivres);
        lvLivres.setAdapter(new LivreUiAdapter(getApplicationContext(), R.layout.livre_list_item, livres));
        lvLivres.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Livre book = (Livre)lvLivres.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), GetLivreActivity.class);
                intent.putExtra("book", book);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_auteur);
        Intent i = getIntent();
        i.getExtras();
        auteur = (Auteur) i.getSerializableExtra("book");
        tvNom = (TextView) findViewById(R.id.nom);
        nomclasse = (TextView) findViewById(R.id.nomclasse);

        tvNom.setText(auteur.getNom());
        nomclasse.setText(auteur.getClass().getSimpleName());
        btnReturn = (Button) findViewById(R.id.btnReturn_Details);
//        btnDelete = (Button) findViewById(R.id.btnDel_Details);
        btnMainMenu = (Button) findViewById(R.id.btnMainMenu_Details);

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
}
