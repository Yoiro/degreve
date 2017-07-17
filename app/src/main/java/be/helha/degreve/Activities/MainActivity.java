package be.helha.degreve;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import be.helha.degreve.Entities.Editeur;
import be.helha.degreve.Entities.Livre;
import be.helha.degreve.Entities.Auteur;
import be.helha.degreve.Entities.Magazine;
import be.helha.degreve.Entities.Publication;

public class MainActivity extends AppCompatActivity {

    private Button btnGetAllBooks, btnGetAllPubs, btnGetAllEdits, btnGetAllMags, btnGetAllAuthors;
    private Object selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ok
        btnGetAllPubs=(Button)findViewById(R.id.publicationsbtn);
        //ok
        btnGetAllAuthors=(Button)findViewById(R.id.autorsbtn);
        //ok
        btnGetAllBooks=(Button)findViewById(R.id.booksbtn);
        //ok
        btnGetAllEdits=(Button)findViewById(R.id.editorsbtn);
        btnGetAllMags=(Button)findViewById(R.id.magsbtn);

        btnGetAllMags.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Magazine m=new Magazine();
                Intent intent=new Intent(getApplicationContext(),GetAllActivity.class);
                intent.putExtra("Class",m);
                startActivity(intent);
            }
        });

        btnGetAllBooks.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Livre l=new Livre();
                Intent intent=new Intent(getApplicationContext(),GetAllActivity.class);
                intent.putExtra("Class",l);
                startActivity(intent);
            }
        });

        btnGetAllAuthors.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Auteur a=new Auteur();
                Intent intent=new Intent(getApplicationContext(),GetAllActivity.class);
                intent.putExtra("Class",a);
                startActivity(intent);
            }
        });

        btnGetAllPubs.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Publication p = new Publication();
                Intent intent = new Intent(getApplicationContext(), GetAllActivity.class);
                intent.putExtra("Class", p);
                startActivity(intent);
            }
        });

        btnGetAllEdits.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Editeur e=new Editeur();
                Intent intent=new Intent(getApplicationContext(),GetAllActivity.class);
                intent.putExtra("Class",e);
                startActivity(intent);
            }
        });
}
