package com.example.musicplayer.MusicPlayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.musicplayer.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mokshaDev on 4/5/2015.
 */
public class ArtistAdapter extends BaseAdapter {


    private ArrayList<Artist> artists;
    private LayoutInflater artistInf;


    public ArtistAdapter(Context c, ArrayList<Artist> theArtists){
        artists =theArtists;
        artistInf =LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return artists.size();
    }

    @Override
    public Object getItem(int arg0) {
        return artists.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = artistInf.inflate(R.layout.artist_item, null);
        }
        TextView songView = (TextView)convertView.findViewById(R.id.song_album);
        TextView artistView = (TextView)convertView.findViewById(R.id.song_artist);
        TextView songNoView = (TextView)convertView.findViewById(R.id.song_num);
        CircleImageView imageView = (CircleImageView)convertView.findViewById(R.id.album_art);

        imageView.setImageBitmap(artists.get(position).getBitmap());
        songView.setText(artists.get(position).getAlbumTitle());
        artistView.setText(artists.get(position).getArtist());
        songNoView.setText(artists.get(position).getNoSongs()+" Songs");
        return convertView;
    }

}
