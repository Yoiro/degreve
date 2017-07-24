package be.helha.degreve.UI;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import be.helha.degreve.Entities.Livre;
import be.helha.degreve.R;
import be.helha.degreve.async.Singleton;

/**
 * Created by Alastard on 17/07/2017.
 */

public class LivreUiAdapter extends ArrayAdapter<Livre> {

    private Context context;
    private int tuile_layout;
    private List<Livre> list;
    private final String imageUrl = "http://52.31.3.254:11080/api-livres/services/files/download/";

    public LivreUiAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Livre> list){
        super(context,resource,list);
        this.context=context;
        tuile_layout=resource;
        this.list=list;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Livre current=list.get(position);
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View tuile=inflater.inflate(this.tuile_layout,parent, false);
        NetworkImageView networkImageView = (NetworkImageView) tuile.findViewById(R.id.lvGetAll_imgThumbLivre);
        networkImageView.setImageUrl(imageUrl+current.getId(), Singleton.getInstance(context).getImageLoader());
        TextView tvName=(TextView) tuile.findViewById(R.id.lvGetAll_nameLivre);
        tvName.setText(current.getTitre());
        return tuile;
    }
}
