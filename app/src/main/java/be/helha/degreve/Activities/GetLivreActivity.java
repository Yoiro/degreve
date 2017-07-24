package be.helha.degreve.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONObject;


import be.helha.degreve.Entities.Auteur;
import be.helha.degreve.Entities.Editeur;
import be.helha.degreve.Entities.Livre;
import be.helha.degreve.R;
import be.helha.degreve.UI.AdaptationUiAdapter;
import be.helha.degreve.UI.AuteurUiAdapter;
import be.helha.degreve.UI.EditeurUiAdapter;
import be.helha.degreve.async.Singleton;

public class GetLivreActivity extends AppCompatActivity {

    private final String baseUrl = "http://54.76.209.52:8080/api-livres/services/api/livres/";
    private final String imgUrl = "http://54.76.209.52:11080/api-livres/services/files/download/";
    private Livre livre;

    private TextView tvTitre;
    private NetworkImageView getlivreImage;
    private TextView adaptationTitle;
    private TextView nomclasse;
    private ListView lvAdaptations;
    private TextView auteurTitle;
    private ListView lvAuteur;
    private TextView editionsTitle;
    private ListView lvEditions;
    private Button btnReturn;
    private Button btnMainMenu;

    public void find() {
        String urlWithId = baseUrl + livre.getTitre();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlWithId, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        tvTitre.setText(livre.getTitre());
                        loadImage();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );
        Singleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    public void loadImage(){

        ImageRequest imgR = new ImageRequest(imgUrl + livre.getId(), new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                getlivreImage.setImageBitmap(response);
                //getlivreImage.setImageUrl(imgUrl+""+livre.getId(), Singleton.getInstance(getApplicationContext()).getImageLoader());
            }
        }, R.dimen.detailWidth, R.dimen.detailHeight, ImageView.ScaleType.CENTER, Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );
        Singleton.getInstance(getApplicationContext()).addToRequestQueue(imgR);
    }

    public void setEditions(){
        if(livre.getEditeurs()!=null && livre.getEditeurs().size()>0){
            editionsTitle.setText("EDITEURS");
            lvEditions = (ListView) findViewById(R.id.editionsLV);
            lvEditions.setAdapter(new EditeurUiAdapter(getApplicationContext(), R.layout.editeur_list_item, livre.getEditeurs()));
            lvEditions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Editeur e = (Editeur) lvEditions.getItemAtPosition(position);
                    Intent intent = new Intent(getApplicationContext(), GetEditeurActivity.class);
                    intent.putExtra("book", e);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        } else editionsTitle.setText("");
    }

    public void setAuteurs(){
        if(livre.getAuteurs()!=null && livre.getAuteurs().size()>0){
            auteurTitle.setText("AUTEURS");
            lvAuteur = (ListView) findViewById(R.id.auteursLV);
            lvAuteur.setAdapter(new AuteurUiAdapter(getApplicationContext(), R.layout.auteur_list_item, livre.getAuteurs()));
            lvAuteur.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Auteur a = (Auteur) lvAuteur.getItemAtPosition(position);
                    Intent intent = new Intent(getApplicationContext(), GetAuteurActivity.class);
                    intent.putExtra("book", a);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        } else auteurTitle.setText("");
    }

    public void setAdaptations(){
        if(livre.getAdaptations()!=null && livre.getAdaptations().size()>0){
            adaptationTitle.setText("ADAPTATIONS");
            lvAdaptations = (ListView) findViewById(R.id.lvadaptations);
            lvAdaptations.setAdapter(new AdaptationUiAdapter(getApplicationContext(), R.layout.adaptation_list_item, livre.getAdaptations()));
        } else adaptationTitle.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_livre);
        Bundle extras=getIntent().getExtras();
        livre = (Livre)extras.getSerializable("book");

        tvTitre=(TextView)findViewById(R.id.tvTitre_Details);
        nomclasse=(TextView)findViewById(R.id.nomclasse);
        adaptationTitle = (TextView) findViewById(R.id.adaptationtitle);
        editionsTitle = (TextView) findViewById(R.id.editionstitle);
        auteurTitle = (TextView) findViewById(R.id.auteurtitle);
        btnMainMenu=(Button)findViewById(R.id.btnMainMenu_Details);
        btnReturn = (Button) findViewById(R.id.btnReturn_Details);
        getlivreImage = (NetworkImageView) findViewById(R.id.getlivreImage);

        tvTitre.setText(livre.getTitre());
        nomclasse.setText(livre.getClass().getSimpleName());

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),GetLivresActivity.class);
                setResult(Activity.RESULT_CANCELED, intent);
                finish();
            }
        });

        btnMainMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        setEditions();
        setAuteurs();
        setAdaptations();
        find();
    }
}
