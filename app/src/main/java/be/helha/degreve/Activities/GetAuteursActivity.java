package be.helha.degreve.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import be.helha.degreve.Entities.Auteur;
import be.helha.degreve.R;
import be.helha.degreve.UI.AuteurUiAdapter;
import be.helha.degreve.async.GetAuteurs;

public class GetAuteursActivity extends AppCompatActivity {

    private static final String TAG = "Simon";
    private Button btnReturn_GetAll;
    private ListView lvGetAll;
    private EditText etGetAll;
    private GetAuteurs async;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_auteurs);
        Intent i=getIntent();

        btnReturn_GetAll=(Button) findViewById(R.id.auteursReturn);
        lvGetAll=(ListView) findViewById(R.id.auteursLV);
        etGetAll= (EditText) findViewById(R.id.etGetAll);

        btnReturn_GetAll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                setResult(Activity.RESULT_CANCELED, intent);
                finish();
            }
        });

        lvGetAll.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AuteurUiAdapter uiAdapter = (AuteurUiAdapter) lvGetAll.getAdapter();
                Auteur selectedBook = uiAdapter.getItem(position);
                Intent intentToDetails = new Intent(getApplicationContext(), GetAdaptationActivity.class);
                intentToDetails.putExtra("id", selectedBook.getId());
                startActivity(intentToDetails);
            }
        });

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

        async = new GetAuteurs(btnReturn_GetAll, lvGetAll, getApplicationContext());
        async.execute();
    }
}
