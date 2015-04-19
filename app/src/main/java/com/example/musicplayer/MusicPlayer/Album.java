package com.example.musicplayer.MusicPlayer;

import android.graphics.Bitmap;

/**
 * Created by mokshaDev on 4/17/2015.
 */
public class Album {

    Bitmap albumArt;
    int noSongs;
    String Artist;
    String albumTitle;

    public Album(Bitmap art,int no,String title)
    {
        albumArt = art;
        noSongs = no;
        albumTitle = title;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }
    public Bitmap getAlbumArt() {
        return albumArt;
    }

    public void setAlbumArt(Bitmap albumArt) {
        this.albumArt = albumArt;
    }

    public int getNoSongs() {
        return noSongs;
    }

    public void setNoSongs(int noSongs) {
        this.noSongs = noSongs;
    }

    public String getArtist() {
        return Artist;
    }

    public void setArtist(String artist) {
        Artist = artist;
    }

}
