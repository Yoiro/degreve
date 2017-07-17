package be.helha.degreve.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import be.helha.degreve.Entities.Auteur;
import be.helha.degreve.Entities.Editeur;
import be.helha.degreve.Entities.Livre;
import be.helha.degreve.Entities.Magazine;
import be.helha.degreve.Entities.Publication;
import be.helha.degreve.R;
import be.helha.degreve.UI.LivreUiAdapter;
import be.helha.degreve.async.GetAllLivres;

public class GetAllActivity extends AppCompatActivity {

    private static final String TAG = "Simon";
    private Object o;
    private Button btnReturn_GetAll;
    private ListView lvGetAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all);
        Intent i=getIntent();
        o=i.getSerializableExtra("Class");
        String classe=o.getClass().getSimpleName();

        lvGetAll=(ListView) findViewById(R.id.lvGetAll);


        if(classe.equals("Livre")){
            GetAllLivres async = new GetAllLivres(btnReturn_GetAll, lvGetAll, getApplicationContext());
            async.execute();
        }

        lvGetAll.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(o.getClass().getSimpleName().equals("Livre")){
                    LivreUiAdapter uiAdapter = (LivreUiAdapter) lvGetAll.getAdapter();
                    Livre selectedBook = uiAdapter.getItem(position);
                    Intent intentToDetails = new Intent(getApplicationContext(), DetailsActivity.class);
                    intentToDetails.putExtra("id", selectedBook.getId());
                    startActivity(intentToDetails);
                }
            }
        });

        btnReturn_GetAll=(Button) findViewById(R.id.btnReturn_GetAll);
        btnReturn_GetAll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });


    }
}
