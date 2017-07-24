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

import be.helha.degreve.Entities.Livre;
import be.helha.degreve.R;
import be.helha.degreve.UI.LivreUiAdapter;
import be.helha.degreve.async.GetLivres;
import be.helha.degreve.async.Singleton;

public class GetLivresActivity extends AppCompatActivity {

    private static final String TAG = "Simon";
    private Button btnReturn_GetAll;
    private ListView lvGetAll;
    private EditText etGetAll;
    private GetLivres async;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_livres);
        Intent i=getIntent();

        btnReturn_GetAll=(Button) findViewById(R.id.btnReturn_GetAll);
        btnReturn_GetAll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                setResult(Activity.RESULT_CANCELED, intent);
                finish();
            }
        });

        lvGetAll=(ListView) findViewById(R.id.lvGetAll);
        lvGetAll.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LivreUiAdapter uiAdapter = (LivreUiAdapter) lvGetAll.getAdapter();
                Livre selectedBook = uiAdapter.getItem(position);
                Intent intentToDetails = new Intent(getApplicationContext(), GetLivreActivity.class);
                intentToDetails.putExtra("book", selectedBook);
                startActivity(intentToDetails);
            }
        });
        etGetAll= (EditText) findViewById(R.id.etGetAll);
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

        async = new GetLivres(btnReturn_GetAll, lvGetAll, getApplicationContext());
        async.execute();
        updateUi();
    }

    public void updateUi(){
        LivreUiAdapter uiAdapter = new LivreUiAdapter(getApplicationContext(), R.layout.livre_list_item, Singleton.getInstance(getApplicationContext()).getLivres());
        lvGetAll.setAdapter(uiAdapter);
    }
}
