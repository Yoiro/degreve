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

import be.helha.degreve.Entities.Editeur;
import be.helha.degreve.Entities.Magazine;
import be.helha.degreve.R;
import be.helha.degreve.UI.EditeurUiAdapter;
import be.helha.degreve.async.Singleton;

public class GetMagazineActivity extends AppCompatActivity {

    private Button btnReturn;
    private Button btnMainMenu;
    private TextView tvTitre;
    private TextView nomclasse;
    private TextView periodicite;
    private TextView editionsTitle;
    private ListView lvEditions;

    private Magazine livre;
    private final String baseUrl = "http://54.76.209.52:8080/api-livres/services/api/magazines/";
    private final String imgUrl = "http://54.76.209.52:8080/api-livres/services/files/download/";

    public void setEditeurs(){
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

    public void find() {
        String urlWithId = baseUrl + livre.getTitre();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlWithId, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        tvTitre.setText(livre.getTitre());
                        editionsTitle.setText("EDITEURS");
                        periodicite.setText(livre.getPeriodicite());
                        loadImage(livre.getId());
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

    public void loadImage(int id){

        ImageRequest imgR = new ImageRequest(imgUrl + livre.getId(), new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                NetworkImageView imageView = (NetworkImageView) findViewById(R.id.getmagazineImage);
                imageView.setImageBitmap(response);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_magazine);
        Bundle extras=getIntent().getExtras();
        livre = (Magazine)extras.getSerializable("book");
        find();
        tvTitre=(TextView)findViewById(R.id.tvTitre_Details);
        periodicite =(TextView)findViewById(R.id.periodicite);
        nomclasse=(TextView)findViewById(R.id.nomclasse);
        editionsTitle = (TextView) findViewById(R.id.editionstitle);
        lvEditions = (ListView) findViewById(R.id.editionsLV);
        btnMainMenu=(Button)findViewById(R.id.btnMainMenu_Details);
        btnReturn = (Button) findViewById(R.id.btnReturn_Details);

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

        setEditeurs();

    }
}
