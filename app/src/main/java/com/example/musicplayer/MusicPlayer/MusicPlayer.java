package com.example.musicplayer.MusicPlayer;

import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.example.musicplayer.Utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by mokshaDev on 4/5/2015.
 */
public class MusicPlayer extends Application implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {
    private MediaPlayer player;
    private ArrayList<Song> songs=new ArrayList<Song>();
    private int songPosn;

    private boolean isRepeat=false;
    private boolean isShuffle = false;
    private int seekForwardTime = 5000;
    private int seekBackwardTime = 5000;
    private int currentSongIndex = 0;

    private static final int GET_ALBUM_ART = 3;
    private static final int ALBUM_ART_DECODED = 4;

    private Worker mAlbumArtWorker;
    private MusicPlayer.AlbumArtHandler mAlbumArtHandler;

    private Utilities utils;
    SongAdapter songAdt;

    public void onCreate(){
        Log.e("MUSIC SERVICE", "Error initializing player");
        songPosn=0;
        player = new MediaPlayer();
        initMusicPlayer();
        utils = new Utilities();
        setList();
        songAdt = new SongAdapter(getApplicationContext(), songs);
        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        mAlbumArtWorker = new Worker("album art worker");
        mAlbumArtHandler = new MusicPlayer.AlbumArtHandler(mAlbumArtWorker.getLooper());
        updateList();
    }

    public void initMusicPlayer() {
        player.setWakeMode(getApplicationContext(),
                PowerManager.PARTIAL_WAKE_LOCK);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);

    }

    public SongAdapter getSongsAdapter()
    {
        return songAdt;
    }

    public void setList(){
        getSongList();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    public void playSong(){

        try{
            player.reset();
            System.out.println(songs);
            System.out.println(songs.get(songPosn));
            Song playSong = songs.get(songPosn);
            long currSong = playSong.getID();
            Uri trackUri = ContentUris.withAppendedId(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    currSong);
            player.setDataSource(getApplicationContext(), trackUri);
        }
        catch(Exception e){
            Log.e("MUSIC SERVICE", "Error setting data source", e);
        }
        try {
            player.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Song> getSongLiss()
    {
        if(!songs.isEmpty()) {
            return songs;
        }
        return null;
    }

    public boolean isListNull()
    {
        return songs == null;
    }

    public boolean isListImageNull()
    {
        return songs.get(0).getImage() == null;
    }

    public void setSong(int songIndex){
        songPosn=songIndex;
    }

    public boolean isPlaying()
    {
        return player.isPlaying();
    }

    public void pauseSong()
    {
        player.pause();
    }

    public void restartSong()
    {
        player.start();
    }

    public String status()
    {
        if(player != null)
        {
            return "sdf";
        }
        else
            return null;
    }


    public long getDuration()
    {
        return player.getDuration();
    }

    public long getCurrentPosition()
    {
        return player.getCurrentPosition();
    }

    public void seek(int currentPosition)
    {
        player.seekTo(currentPosition);
    }

    public MediaPlayer getPlayer()
    {
        return player;
    }

    public int getCurrentSongIndex()
    {
        System.out.println(songPosn);
        return songPosn;
    }

    public String getSongTitle()
    {
        Song song = songs.get(songPosn);
        return song.getTitle();
    }

    public void onCompletion(MediaPlayer arg0)
    {

        if(isRepeat){
            setSong(songPosn);
            playSong();
        } else if(isShuffle){
            // shuffle is on - play a random song
            Random rand = new Random();
            songPosn = rand.nextInt((songs.size() - 1) - 0 + 1) + 0;
            setSong(songPosn);
            playSong();
        } else{
            // no repeat or shuffle ON - play next song
            if(songPosn < (songs.size() - 1)){
                setSong(songPosn + 1);
                playSong();
                songPosn = songPosn + 1;
            }else{
                // play first song
                setSong(0);
                playSong();
                songPosn = 0;
            }
        }
    }

    public void setRepeatSong(boolean re)
    {
        isRepeat = re;
    }

    public void setShuffelSong(boolean sh)
    {
        isShuffle = sh;
    }

    public void moveForward()
    {
        int currentPosition = (int) getCurrentPosition();
        if(currentPosition + seekForwardTime <= getDuration()){
            seek(currentPosition + seekForwardTime);
        }else{
            seek((int)getDuration());
        }
    }

    public void moveBackward()
    {
        int currentPosition = (int) getCurrentPosition();
        if(currentPosition - seekBackwardTime >= 0){
            seek(currentPosition - seekBackwardTime);
        }else{
            seek(0);
        }
    }

    public void moveNext()
    {
        currentSongIndex = getCurrentSongIndex();
        if(currentSongIndex < (songs.size() - 1)){
            setSong(currentSongIndex + 1);
            playSong();
            currentSongIndex = currentSongIndex + 1;
        }else{
            setSong(0);
            playSong();
            currentSongIndex = 0;
        }
    }
    public void movePrevious()
    {
        currentSongIndex = getCurrentSongIndex();
        if(currentSongIndex > 0){
            setSong(currentSongIndex - 1);
            playSong();
            currentSongIndex = currentSongIndex - 1;
        }else{
            // play last song
            setSong(songs.size() - 1);
            playSong();
            currentSongIndex = songs.size() - 1;
        }
    }

    public void updateList() {
        Song s = songs.get(0);
        mAlbumArtHandler.removeMessages(GET_ALBUM_ART);
        mAlbumArtHandler.obtainMessage(GET_ALBUM_ART, new AlbumSongIdWrapper(s.getAlbumID(), s.getID())).sendToTarget();
    }

    private void getSongList() {
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = null;
        String sortOrder = null;
        String selectionMimeType = MediaStore.Files.FileColumns.MIME_TYPE + "=?";
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension("mp3");
        String[] selectionArgsMp3 = new String[]{mimeType};
        Cursor musicCursor = musicResolver.query(musicUri, projection, selectionMimeType, selectionArgsMp3, sortOrder);
        if (musicCursor != null && musicCursor.moveToFirst()) {
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            int album_id = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.ALBUM_ID);
            int album_title =  musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.ALBUM);
            int artisit_id =  musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.ARTIST_ID);
            do {
                long thisId = musicCursor.getLong(idColumn);
                long albumID = musicCursor.getLong(album_id);
                long artistID = musicCursor.getLong(artisit_id);
                String albumTitle = musicCursor.getString(album_title);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                songs.add(new Song(thisId, thisTitle, thisArtist,albumID,null,albumTitle,artistID));
            }
            while (musicCursor.moveToNext());
        }
    }

    private static class AlbumSongIdWrapper {
        public long albumid;
        public long songid;

        AlbumSongIdWrapper(long aid, long sid) {
            albumid = aid;
            songid = sid;
        }
    }

    public class AlbumArtHandler extends Handler {
        private long mAlbumId = -1;

        public AlbumArtHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.d("ALBUM_ART_DECODED", "In the Handler");
            long albumid = ((AlbumSongIdWrapper) msg.obj).albumid;
            long songid = ((AlbumSongIdWrapper) msg.obj).songid;
            if (msg.what == GET_ALBUM_ART && (mAlbumId != albumid || albumid < 0)) {
                // while decoding the new image, show the default album art
                Message numsg = mmHandler.obtainMessage(ALBUM_ART_DECODED, null);
                mmHandler.removeMessages(ALBUM_ART_DECODED);
                mmHandler.sendMessageDelayed(numsg, 300);
                // Don't allow default artwork here, because we want to fall back to song-specific
                // album art if we can't find anything for the album.
                Bitmap bm = Utilities.getArtwork(getApplicationContext(), songid, albumid, false);
                if (bm == null) {
                    bm = Utilities.getArtwork(getApplicationContext(), songid, -1);
                }
                if (bm != null) {
                    numsg = mmHandler.obtainMessage(ALBUM_ART_DECODED, bm);
                    mmHandler.removeMessages(ALBUM_ART_DECODED);
                    mmHandler.sendMessage(numsg);
                }
            }
        }
    }

    private static class Worker implements Runnable {
        private final Object mLock = new Object();
        private Looper mLooper;

        /**
         * Creates a worker thread with the given name. The thread
         * then runs a {@link android.os.Looper}.
         *
         * @param name A name for the new thread
         */
        Worker(String name) {
            Thread t = new Thread(null, this, name);
            t.setPriority(Thread.MIN_PRIORITY);
            t.start();
            synchronized (mLock) {
                while (mLooper == null) {
                    try {
                        mLock.wait();
                    } catch (InterruptedException ex) {
                    }
                }
            }
        }

        public Looper getLooper() {
            return mLooper;
        }

        public void run() {
            synchronized (mLock) {
                Looper.prepare();
                mLooper = Looper.myLooper();
                mLock.notifyAll();
            }
            Looper.loop();
        }

        public void quit() {
            mLooper.quit();
        }
    }

    private Bitmap album_art;
    int position = 0;

    private Handler mmHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case ALBUM_ART_DECODED:

                    int size = songs.size();
                    Song s = songs.get(position);
                    album_art = (Bitmap) msg.obj;
                    songs.set(position,new Song(s.getID(),s.getTitle(),s.getArtist(),s.getAlbumID(),album_art,s.getAlbumTitle(),s.getArtistID()));
                    position++;
                    Log.d("ALBUM_ART_DECODED", msg.toString());
                    if (position < size) {
                        mAlbumArtHandler.removeMessages(GET_ALBUM_ART);
                        mAlbumArtHandler.obtainMessage(GET_ALBUM_ART, new AlbumSongIdWrapper(s.getAlbumID(), s.getID())).sendToTarget();
                        songAdt.notifyDataSetChanged();
                    }
                    break;
                default:
                    break;
            }
        }
    };


    public int getSongArrayPos(long id)
    { int position =0;
        for(int i=0;i<songs.size();i++)
        {
          if(songs.get(i).getID()==id)
          {
              position=i;
              break;
          }
        }
        return position;
    }
}
