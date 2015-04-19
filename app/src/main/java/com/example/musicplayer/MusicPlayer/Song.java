package com.example.musicplayer.MusicPlayer;

/*
 * This is demo code to accompany the Mobiletuts+ series:
 * Android SDK: Creating a Music Player
 * 
 * Sue Smith - February 2014
 */

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class Song {
	
	private long id;
	private String title;
	private String artist;
    private long aid;
    private String albumTitle;
    private long artistID;
    private Bitmap image;
	
	public Song(long songID, String songTitle, String songArtist,long Id,Bitmap bitmap,String alTitle,long artisID){
		id=songID;
		title=songTitle;
		artist=songArtist;
        aid = Id;
        image = bitmap;
        albumTitle = alTitle;
        artistID = artisID;
	}

    public Song(long songID, String songTitle, String songArtist){
        id=songID;
        title=songTitle;
        artist=songArtist;
    }

	
	public long getID(){return id;}
	public String getTitle(){return title;}
	public String getArtist(){return artist;}
    public long getAlbumID(){return aid;}
    public Bitmap getImage(){return image;}
    public String getAlbumTitle(){return albumTitle;}
    public long getArtistID(){return artistID;}
}
