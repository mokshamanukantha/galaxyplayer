package com.example.musicplayer.MusicPlayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musicplayer.R;

import java.util.ArrayList;

/**
 * Created by mokshaDev on 4/17/2015.
 */
public class AlbumGridAdapter extends BaseAdapter {

    ArrayList<Album> albums;
    LayoutInflater inflater;
    Context  mContext;

    public AlbumGridAdapter(Context c, ArrayList<Album> album)
    {
        albums = album;
        inflater = LayoutInflater.from(c);
        mContext = c;
    }

    @Override
    public int getCount() {
        return albums.size();
    }

    @Override
    public Object getItem(int position) {
        return albums.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_list, null);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.album_grid);
        TextView textView = (TextView) convertView.findViewById(R.id.album_title);
        TextView textView1 = (TextView) convertView.findViewById(R.id.album_songs);

        //Bitmap b = albums.get(position).getAlbumArt();
        if(!(albums.get(position).getAlbumArt()==null)) {
            imageView.setImageBitmap(Bitmap.createScaledBitmap(albums.get(position).getAlbumArt(), 200, 200, false));
        }
        textView.setText("" + albums.get(position).getAlbumTitle());
        textView1.setText(albums.get(position).getNoSongs()+" Songs");
        return convertView;
    }
}
