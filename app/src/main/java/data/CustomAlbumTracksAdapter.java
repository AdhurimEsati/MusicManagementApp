package data;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import esati.adhurim.musicmanagementapp.R;
import lastfm.models.Track;

/**
 * Created by Adhu on 6/10/2018.
 */

public class CustomAlbumTracksAdapter extends ArrayAdapter<Track>{

    private LayoutInflater layoutInflater;
    private ArrayList<Track> tracks; //all tracks of opened album
    private Activity context;
    private int layoutResourceId;


    public CustomAlbumTracksAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<Track> objects) {
        super(context, resource, objects);
        this.context = context;
        layoutResourceId = resource;
        tracks = objects;
    }


    @Override
    public int getCount() { return tracks.size(); }

    @Nullable
    @Override
    public Track getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getPosition(@Nullable Track item) {
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
            viewHolder.trackName = (TextView)row.findViewById(R.id.trackName);
            viewHolder.duration = (TextView)row.findViewById(R.id.duration);

            row.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) row.getTag();
        }

        viewHolder.track = tracks.get(position);
        viewHolder.trackName.setText(viewHolder.track.getArtist() + " - " + viewHolder.track.getName());
        double durationSeconds = Double.parseDouble(viewHolder.track.getDuration());
        double durationMinutes = durationSeconds / 60;
        long min = (long) durationMinutes;
        double sec = durationMinutes - min;
        double finalSec = sec * 60;

        viewHolder.duration.setText("Duration: " + min + ":" + (int)finalSec);

        final ViewHolder finalViewHolder = viewHolder;
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalViewHolder.track.getUrl()));
                context.startActivity(browserIntent);
            }
        });

        return row;
    }




    public class ViewHolder {
        Track track;
        TextView trackName;
        TextView duration;
    }

}
