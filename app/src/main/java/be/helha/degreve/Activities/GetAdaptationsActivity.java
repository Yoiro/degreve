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

import be.helha.degreve.Entities.Adaptation;
import be.helha.degreve.R;
import be.helha.degreve.UI.AdaptationUiAdapter;
import be.helha.degreve.async.GetAdaptations;

public class GetAdaptationsActivity extends AppCompatActivity {
    private static final String TAG = "Simon";
    private Button btnReturn_GetAll;
    private ListView lvGetAll;
    private EditText etGetAll;
    private GetAdaptations async;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_adaptations);
        Intent i=getIntent();

        btnReturn_GetAll=(Button) findViewById(R.id.adaptationsReturn);
        btnReturn_GetAll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                setResult(Activity.RESULT_CANCELED, intent);
                finish();
            }
        });

        lvGetAll=(ListView) findViewById(R.id.adaptationsLV);
        lvGetAll.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AdaptationUiAdapter uiAdapter = (AdaptationUiAdapter) lvGetAll.getAdapter();
                Adaptation selectedBook = uiAdapter.getItem(position);
                Intent intentToDetails = new Intent(getApplicationContext(), GetAdaptationActivity.class);
                intentToDetails.putExtra("id", selectedBook.getId());
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

        async = new GetAdaptations(btnReturn_GetAll, lvGetAll, getApplicationContext());
        async.execute();
    }
}
