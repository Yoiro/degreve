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

import be.helha.degreve.Entities.Magazine;
import be.helha.degreve.R;
import be.helha.degreve.async.Singleton;

/**
 * Created by Alastard on 19/07/2017.
 */

public class MagazineUiAdapter extends ArrayAdapter<Magazine>{
    private Context context;
    private int tuile_layout;
    private List<Magazine> list;
    private final String imageUrl = "http://54.76.209.52:8080/api-livres/services/files/download/";

    public MagazineUiAdapter(Context context, int resource, List<Magazine> list){
        super(context,resource,list);
        this.context=context;
        tuile_layout=resource;
        this.list=list;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Magazine current=list.get(position);
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View tuile=inflater.inflate(this.tuile_layout,parent, false);
        NetworkImageView networkImageView = (NetworkImageView) tuile.findViewById(R.id.magitemIMG);
        networkImageView.setMaxHeight(R.dimen.thumbHeight);
        networkImageView.setMaxWidth(R.dimen.thumbWidth);
        networkImageView.setImageUrl(imageUrl+current.getId(), Singleton.getInstance(context).getImageLoader());

        TextView tvName=(TextView) tuile.findViewById(R.id.magitemTV);
        tvName.setText(current.getTitre());
        return tuile;
    }
}
