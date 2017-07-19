package be.helha.degreve.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import be.helha.degreve.R;

public class DetailsActivity extends AppCompatActivity {

    private Object item;
    private Button btnReturn;
    private Button btnMainMenu;
    private TextView tvTitre;
    private TextView tvType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Bundle extras=getIntent().getExtras();
        item=extras.getSerializable("Class");
        int index=extras.getInt("id");

        tvTitre=(TextView)findViewById(R.id.tvTitre_Details);
        tvType=(TextView)findViewById(R.id.tvType_Details);

        btnReturn = (Button) findViewById(R.id.btnReturn_Details);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),GetLivresActivity.class);
                setResult(Activity.RESULT_CANCELED, intent);
                finish();
            }
        });

        btnMainMenu=(Button)findViewById(R.id.btnMainMenu_Details);
        btnMainMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
