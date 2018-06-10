package data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import esati.adhurim.musicmanagementapp.DetailedAlbumActivity;
import esati.adhurim.musicmanagementapp.MainActivity;
import esati.adhurim.musicmanagementapp.R;
import lastfm.models.Album;
import utility.AppController;

/**
 * Created by Adhu on 6/6/2018.
 */

public class CustomAlbumsListViewAdapter extends ArrayAdapter<Album> {

    private LayoutInflater layoutInflater;
    private ArrayList<Album> albums; //all albums of searched artist
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private Activity context;
    private int layoutResourceId;
    private Context MainActivityContext = MainActivity.getContextOfApplication();


    public CustomAlbumsListViewAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<Album> objects) {
        super(context, resource, objects);
        albums = objects;
        this.context = context;
        layoutResourceId = resource;
    }

    @Override
    public int getCount() {
        return albums.size();
    }

    @Nullable
    @Override
    public Album getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getPosition(@Nullable Album item) {
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

        if(row == null) {
            layoutInflater = LayoutInflater.from(context);
            row = layoutInflater.inflate(layoutResourceId, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.albumImage = (NetworkImageView) row.findViewById(R.id.artistImage);
            viewHolder.albumTitle = (TextView) row.findViewById(R.id.albumTitle);
            viewHolder.albumArtist = (TextView) row.findViewById(R.id.albumArtist);
            viewHolder.playCount = (TextView) row.findViewById(R.id.playCount);

            row.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) row.getTag();
        }

        viewHolder.album = albums.get(position);

        viewHolder.albumImage.setImageUrl(viewHolder.album.getAlbumImage(), imageLoader);
        viewHolder.albumTitle.setText(viewHolder.album.getAlbumTitle());
        viewHolder.albumArtist.setText(viewHolder.album.getAlbumArtist());
        viewHolder.playCount.setText(viewHolder.album.getPlayCount());

        final Button saveButton = (Button) row.findViewById(R.id.saveButton);
        final Button removeButton = (Button) row.findViewById(R.id.removeButton);
        saveButton.setEnabled(true);
        removeButton.setEnabled(false);

        //loadAlbums();

        Log.v("Adhurim", "FavoriteAlbum size: " + MainActivity.favoriteAlbums.size());

        if(saveButton.isEnabled()) {
            for(int c = 0; c < MainActivity.favoriteAlbums.size(); c++) {
                if(MainActivity.favoriteAlbums.get(c).getAlbumTitle().equals(viewHolder.album.getAlbumTitle())) {
                    Log.v("Adhurim", MainActivity.favoriteAlbums.get(c).getAlbumTitle() + " = " + viewHolder.album.getAlbumTitle());
                    Log.v("Adhurim", viewHolder.album.getAlbumTitle() + " is favorited, Save button should be disabled");
                    saveButton.setEnabled(false);
                    removeButton.setEnabled(true);
                    notifyDataSetChanged();
                }
            }
        }

        final ViewHolder finalViewHolder2 = viewHolder;
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeButton.setEnabled(false);
                saveButton.setEnabled(true);
                int index = 0;
                for(int c = 0; c < MainActivity.favoriteAlbums.size(); c++) {
                    if(MainActivity.favoriteAlbums.get(c).getAlbumTitle().equals(finalViewHolder2.album.getAlbumTitle())) {
                        index = c;
                        Log.v("Adhurim", "FOUND INDEX: " + c);
                    }
                }
                Log.v("Adhurim", "Index of " + finalViewHolder2.album.getAlbumTitle() + " is " + index);
                MainActivity.favoriteAlbums.remove(index);
                saveAlbums();
                notifyDataSetChanged();
            }
        });


        final ViewHolder finalViewHolder1 = viewHolder;
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveButton.setEnabled(false);
                removeButton.setEnabled(true);
                finalViewHolder1.album.setFavorite(true);
                MainActivity.favoriteAlbums.add(finalViewHolder1.album);
                saveAlbums();
                Log.v("Adhurim", "Album " + finalViewHolder1.album.getAlbumTitle() + " is now Favorite set to"
                        + finalViewHolder1.album.isFavorite() + "\nSaved album with title: " + albums.get(position).getAlbumTitle() + ". Size now is: "
                        + MainActivity.favoriteAlbums.size());
                //notifyDataSetChanged();
            }
        });

        final ViewHolder finalViewHolder = viewHolder;
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailedAlbumActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("albumObj", finalViewHolder.album);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        return row;
    }

    public void saveAlbums() {
        SharedPreferences sharedPreferences = MainActivityContext.getSharedPreferences("sharedprefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(MainActivity.favoriteAlbums);
        editor.putString("favoriteAlbums", json);
        editor.apply();
    }

    public void loadAlbums() {
        SharedPreferences sharedPreferences = MainActivityContext.getSharedPreferences("sharedprefs", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("favoriteAlbums", null);
        Type type = new TypeToken<ArrayList<Album>>() {}.getType();
        MainActivity.favoriteAlbums = gson.fromJson(json, type);

        if(MainActivity.favoriteAlbums == null) {
            MainActivity.favoriteAlbums = new ArrayList<>();
        }

    }

    public class ViewHolder {
        Album album;
        NetworkImageView albumImage;
        TextView albumTitle;
        TextView albumArtist;
        TextView playCount;
    }
}
