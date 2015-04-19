package com.example.musicplayer.MusicPlayer;

import android.graphics.Bitmap;

/**
 * Created by mokshaDev on 4/5/2015.
 */
public class Artist {

    private String albumTitle;
    private String artist;
    private Bitmap bitmap;
    private int noSongs;

    public Artist(String Title, String Artist,Bitmap bm,int nSongs){
        albumTitle=Title;
        artist=Artist;
        bitmap = bm;
        noSongs = nSongs;
    }
    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getNoSongs() {
        return noSongs;
    }

    public void setNoSongs(int noSongs) {
        this.noSongs = noSongs;
    }



}
