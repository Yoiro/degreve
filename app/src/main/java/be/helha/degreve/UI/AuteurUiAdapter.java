package be.helha.degreve.UI;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import be.helha.degreve.Entities.Auteur;
import be.helha.degreve.R;
import be.helha.degreve.async.Singleton;

/**
 * Created by Alastard on 20/07/2017.
 */

public class AuteurUiAdapter extends ArrayAdapter<Auteur> {
    private Context context;
    private int tuile_layout;
    private List<Auteur> list;
    private final String imageUrl = "http://54.76.209.52:8080/api-livres/services/files/download/";

    public AuteurUiAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Auteur> list) {
        super(context, resource, list);
        this.context=context;
        tuile_layout=resource;
        this.list=list;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(list.size()>0) {
            Auteur current = list.get(position);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View tuile = inflater.inflate(this.tuile_layout, parent, false);
            TextView tvName = (TextView) tuile.findViewById(R.id.auteuritemTV);
            tvName.setText(current.getNom());
            return tuile;
        }
        return new View(context);
    }
}
