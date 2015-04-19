package com.example.musicplayer.MusicPlayer;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.musicplayer.R;

import de.hdodenhof.circleimageview.CircleImageView;

/*
 * This is demo code to accompany the Mobiletuts+ series:
 * Android SDK: Creating a Music Player
 * 
 * Sue Smith - February 2014
 */

public class SongAdapter extends BaseAdapter {
	
	//song list and layout
	private ArrayList<Song> songs;
	private LayoutInflater songInf;
	
	//constructor
	public SongAdapter(Context c, ArrayList<Song> theSongs){
		songs=theSongs;
		songInf=LayoutInflater.from(c);
	}

	@Override
	public int getCount() {
		return songs.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//map to song layout
		RelativeLayout songLay = (RelativeLayout)songInf.inflate
				(R.layout.playeritem, parent, false);
		//get title and artist views
		TextView songView = (TextView)songLay.findViewById(R.id.song_title);
		TextView artistView = (TextView)songLay.findViewById(R.id.song_artist);
        CircleImageView c = (CircleImageView)songLay.findViewById(R.id.album_art);
		//get song using position
		Song currSong = songs.get(position);
        if(currSong.getImage()!=null)
        {
            c.setImageBitmap(currSong.getImage());
        }
		//get title and artist strings
		songView.setText(currSong.getTitle());
		artistView.setText(currSong.getArtist());
		//set position as tag
		songLay.setTag(position);
		return songLay;
	}

}
