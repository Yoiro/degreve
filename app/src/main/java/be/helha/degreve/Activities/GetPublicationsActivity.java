package be.helha.degreve.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import java.util.List;

import be.helha.degreve.Entities.Livre;
import be.helha.degreve.Entities.Magazine;
import be.helha.degreve.Entities.Publication;
import be.helha.degreve.R;
import be.helha.degreve.UI.PublicationUiAdapter;
import be.helha.degreve.async.GetPublications;
import be.helha.degreve.async.Singleton;

public class GetPublicationsActivity extends AppCompatActivity {

    private static final String TAG = "Simon";
    private Button btnReturn_GetAll;
    private ListView lvGetAll;
    private EditText etGetAll;
    private GetPublications async;
    private boolean isBook;
    private boolean isMag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_publications);
        Intent i=getIntent();

        btnReturn_GetAll=(Button) findViewById(R.id.publicationsReturn);
        btnReturn_GetAll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                setResult(Activity.RESULT_CANCELED, intent);
                finish();
            }
        });

        lvGetAll=(ListView) findViewById(R.id.publicationsLV);
        lvGetAll.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PublicationUiAdapter uiAdapter = (PublicationUiAdapter) lvGetAll.getAdapter();
                Publication selectedBook = uiAdapter.getItem(position);
                Livre livre = isBook(selectedBook);
                if(isBook){
                    Intent intentToDetails = new Intent(getApplicationContext(), GetLivreActivity.class);
                    intentToDetails.putExtra("book", livre);
                    startActivity(intentToDetails);
                } else{
                    Magazine mag = isMagazine(selectedBook);
                    if (isMag){
                        Intent intentToDetails = new Intent(getApplicationContext(), GetMagazineActivity.class);
                        intentToDetails.putExtra("book", mag);
                        System.out.println(mag.toString());
                        startActivity(intentToDetails);
                    }
                }

            }
        });
        etGetAll= (EditText) findViewById(R.id.publicationsET);
        etGetAll.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                async.execute(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        async = new GetPublications(btnReturn_GetAll, lvGetAll, getApplicationContext());
        async.execute();
        updateUi();
    }

    public Livre isBook(Publication livre){
        List<Livre> livreList = Singleton.getInstance(getApplicationContext()).getLivres();
        for(int i = 0; i < livreList.size(); i++){
            if(livre.getTitre().equals(livreList.get(i).getTitre())){
                isBook = true;
                return livreList.get(i);
            }
        }
        return null;
    }

    public Magazine isMagazine(Publication livre){
        List<Magazine> magazineList = Singleton.getInstance(getApplicationContext()).getMagazines();
        for(int i = 0; i < magazineList.size(); i++){
            if(livre.getTitre().equals(magazineList.get(i).getTitre())){
                isMag = true;
                return magazineList.get(i);
            }
        }
        return null;
    }

    public void updateUi(){
        PublicationUiAdapter uiAdapter = new PublicationUiAdapter(getApplicationContext(), R.layout.publication_list_item, Singleton.getInstance(getApplicationContext()).getPublications());
        lvGetAll.setAdapter(uiAdapter);
    }
}
