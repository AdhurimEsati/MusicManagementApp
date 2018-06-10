package data;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import esati.adhurim.musicmanagementapp.R;
import esati.adhurim.musicmanagementapp.TopAlbums;
import lastfm.models.Artist;
import utility.AppController;

/**
 * Created by Adhu on 6/10/2018.
 */

public class CustomArtistListViewAdapter extends ArrayAdapter<Artist>{

    private LayoutInflater layoutInflater;
    private ArrayList<Artist> artists; //all tracks of opened album
    private Activity context;
    private int layoutResourceId;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();


    public CustomArtistListViewAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<Artist> objects) {
        super(context, resource, objects);
        this.context = context;
        layoutResourceId = resource;
        artists = objects;
    }


    @Override
    public int getCount() { return artists.size(); }

    @Nullable
    @Override
    public Artist getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getPosition(@Nullable Artist item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }



    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        ViewHolder viewHolder = null;

        if (row == null) {
            layoutInflater = LayoutInflater.from(context);
            row = layoutInflater.inflate(layoutResourceId, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.artistName = (TextView)row.findViewById(R.id.artistName);
            viewHolder.artistImage = (NetworkImageView) row.findViewById(R.id.artistImage);

            row.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) row.getTag();
        }

        viewHolder.artist = artists.get(position);
        viewHolder.artistName.setText(viewHolder.artist.getName());
        viewHolder.artistImage.setImageUrl(viewHolder.artist.getImageUrl(), imageLoader);


        final ViewHolder finalViewHolder = viewHolder;
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        final ViewHolder finalViewHolder1 = viewHolder;
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TopAlbums.class);
                intent.putExtra("SEARCHED_ARTIST", finalViewHolder1.artist.getName());
                context.startActivity(intent);
            }
        });

        return row;
    }

    public class ViewHolder {
        Artist artist;
        TextView artistName;
        NetworkImageView artistImage;
    }

}
