package be.helha.degreve.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import be.helha.degreve.Entities.Adaptation;
import be.helha.degreve.Entities.Auteur;
import be.helha.degreve.Entities.Editeur;
import be.helha.degreve.Entities.Livre;
import be.helha.degreve.Entities.Magazine;
import be.helha.degreve.Entities.Publication;
import be.helha.degreve.R;
import be.helha.degreve.async.GetLivres;
import be.helha.degreve.async.GetMagazines;
import be.helha.degreve.async.GetPublications;

public class MainActivity extends AppCompatActivity {

    private Button btnGetAllBooks, btnGetAllPubs, btnGetAllEdits, btnGetAllMags, btnGetAllAuthors;

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetAllPubs = (Button) findViewById(R.id.publicationsbtn);
        btnGetAllAuthors = (Button) findViewById(R.id.autorsbtn);
        btnGetAllBooks = (Button) findViewById(R.id.booksbtn);
        btnGetAllEdits = (Button) findViewById(R.id.editorsbtn);
        btnGetAllMags = (Button) findViewById(R.id.magsbtn);

        btnGetAllMags.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Magazine m = new Magazine();
                Intent intent = new Intent(getApplicationContext(), GetMagazinesActivity.class);
                intent.putExtra("Class", m);
                startActivityForResult(intent, 1);
            }
        });

        btnGetAllBooks.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Livre l = new Livre();
                Intent intent = new Intent(getApplicationContext(), GetLivresActivity.class);
                intent.putExtra("Class", l);
                startActivity(intent);
            }
        });

        btnGetAllAuthors.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Auteur a = new Auteur();
                Intent intent = new Intent(getApplicationContext(), GetAuteursActivity.class);
                intent.putExtra("Class", a);
                startActivity(intent);
            }
        });

        btnGetAllPubs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Publication p = new Publication();
                Intent intent = new Intent(getApplicationContext(), GetPublicationsActivity.class);
                intent.putExtra("Class", p);
                startActivity(intent);
            }
        });

        btnGetAllEdits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editeur e = new Editeur();
                Intent intent = new Intent(getApplicationContext(), GetEditeursActivity.class);
                intent.putExtra("Class", e);
                startActivity(intent);
            }
        });

        GetLivres livresAsync = new GetLivres(getApplicationContext());
        livresAsync.fill_Singleton();
        Log.i("Main.ONCREATE", "Livres chargés");
        GetPublications publicationsAsync = new GetPublications(getApplicationContext());
        publicationsAsync.fill_Singleton();
        Log.i("Main.ONCREATE", "Publications chargées");
        GetMagazines magazinesAsync = new GetMagazines(getApplicationContext());
        magazinesAsync.fill_Singleton();
        Log.i("Main.ONCREATE", "Magazines chargés");
    }
}

