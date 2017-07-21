package be.helha.degreve.UI;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import be.helha.degreve.Entities.Publication;
import be.helha.degreve.R;
import be.helha.degreve.async.Singleton;

/**
 * Created by Alastard on 20/07/2017.
 */

public class PublicationUiAdapter extends ArrayAdapter<Publication> {
    private Context context;
    private int tuile_layout;
    private List<Publication> list;
    private final String imageUrl = "http://54.76.209.52:8080/api-livres/services/files/download/";

    public PublicationUiAdapter(Context context, int resource, List<Publication> list){
        super(context,resource,list);
        this.context=context;
        tuile_layout=resource;
        this.list=list;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Publication current=list.get(position);
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View tuile=inflater.inflate(this.tuile_layout,parent, false);
        NetworkImageView networkImageView = (NetworkImageView) tuile.findViewById(R.id.publicationitemIMG);
        try {
            networkImageView.setImageUrl(imageUrl + current.getId(), Singleton.getInstance(context).getImageLoader());
        } finally {
            TextView tvName=(TextView) tuile.findViewById(R.id.publicationitemTV);
            tvName.setText(current.getTitre());
        }
        return tuile;
    }
}
