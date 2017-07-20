package be.helha.degreve.UI;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import be.helha.degreve.Entities.Editeur;
import be.helha.degreve.R;

/**
 * Created by Alastard on 20/07/2017.
 */

public class EditeurUiAdapter extends ArrayAdapter<Editeur> {
    private Context context;
    private int tuile_layout;
    private List<Editeur> list;

    public EditeurUiAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Editeur> list) {
        super(context, resource, list);
        this.context=context;
        tuile_layout=resource;
        this.list=list;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Editeur current = list.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View tuile = inflater.inflate(this.tuile_layout, parent, false);
        TextView tvName = (TextView) tuile.findViewById(R.id.editeuritemTV);
        tvName.setText(current.getNom());
        return tuile;
    }
}
