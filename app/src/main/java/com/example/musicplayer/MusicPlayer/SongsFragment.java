package com.example.musicplayer.MusicPlayer;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicplayer.R;
import com.example.musicplayer.Utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mokshaDev on 4/5/2015.
 */
public class SongsFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener, View.OnLongClickListener, SeekBar.OnSeekBarChangeListener, AdapterView.OnItemLongClickListener {
    MusicPlayer mPlayer;
    private ArrayList<Song> songList;
    private ListView songView;
    private TextView sTitle;
    private ImageButton bPlay;
    private ImageButton bForward;
    private ImageButton bBackward;
    private Handler mHandler = new Handler();
    private Utilities utils;
    private SeekBar songProgressBar;
    private TextView songCurrentDurationLabel;
    private TextView songTotalDurationLabel;
    private int seekForwardTime = 5000;
    private int seekBackwardTime = 5000;
    private int currentSongIndex = 0;

    String[] playlistID;
    String[] playlists;
    int songID;
    private BitmapDrawable dalbum_art;
    SongAdapter songAdt;
    CircleImageView imageView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song_layout, container, false);
        mPlayer = (MusicPlayer) getActivity().getApplication();
        songView = (ListView) view.findViewById(R.id.song_list);
        songList = new ArrayList<Song>();
        sTitle = (TextView) view.findViewById(R.id.songTitle);
        bPlay = (ImageButton) view.findViewById(R.id.btnPlay);
        bForward = (ImageButton) view.findViewById(R.id.btnForward);
        bBackward = (ImageButton) view.findViewById(R.id.btnBackward);
        songProgressBar = (SeekBar) view.findViewById(R.id.songProgressBar);
        songCurrentDurationLabel = (TextView) view.findViewById(R.id.songCurrentDurationLabel);
        songTotalDurationLabel = (TextView) view.findViewById(R.id.songTotalDurationLabel);
        songList = mPlayer.getSongLiss();
        dalbum_art = (BitmapDrawable) getResources().getDrawable(R.drawable.song);
        utils = new Utilities();
        Collections.sort(songList, new Comparator<Song>() {
            public int compare(Song a, Song b) {
                return a.getTitle().compareTo(b.getTitle());
            }
        });
        registerForContextMenu(songView);
        songView.setAdapter(mPlayer.getSongsAdapter());
        songView.setOnItemClickListener(this);
        songView.setOnItemLongClickListener(this);
        bPlay.setOnClickListener(this);
        bForward.setOnClickListener(this);
        bBackward.setOnClickListener(this);
        bForward.setOnLongClickListener(this);
        bBackward.setOnLongClickListener(this);
        imageView = (CircleImageView) view.findViewById(R.id.albumArt);
        imageView.setOnClickListener(this);
        if(mPlayer.isPlaying())
        {
            bPlay.setImageResource(R.drawable.ic_pause_white);
        }
        else
            bPlay.setImageResource(R.drawable.ic_play_white);
        songProgressBar.setOnSeekBarChangeListener(this);
        updateInfo();
        getPlayLists();
        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mPlayer.setSong(Integer.parseInt(view.getTag().toString()));
        mPlayer.playSong();
        sTitle.setText("" + mPlayer.getSongTitle());
        bPlay.setImageResource(R.drawable.ic_pause_white);
        songProgressBar.setProgress(0);
        songProgressBar.setMax(100);
        updateProgressBar();
        updateInfo();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnPlay) {
            songProgressBar.setProgress(0);
            songProgressBar.setMax(100);
            updateProgressBar();
            if (mPlayer.isPlaying()) {
                if (mPlayer.status() != null) {
                    mPlayer.pauseSong();
                    bPlay.setImageResource(R.drawable.ic_play_white);
                }
            } else {
                if (mPlayer.status() != null) {
                    mPlayer.restartSong();
                    bPlay.setImageResource(R.drawable.ic_pause_white);
                }
            }
        }
        if (v.getId() == R.id.btnBackward) {
            sTitle.setText("" + mPlayer.getSongTitle());
            currentSongIndex = mPlayer.getCurrentSongIndex();
            if (currentSongIndex > 0) {
                mPlayer.setSong(currentSongIndex - 1);
                mPlayer.playSong();
                currentSongIndex = currentSongIndex - 1;
            } else {
                mPlayer.setSong(songList.size() - 1);
                mPlayer.playSong();
                currentSongIndex = songList.size() - 1;
            }
        }
        if (v.getId() == R.id.btnForward) {
            sTitle.setText("" + mPlayer.getSongTitle());
            currentSongIndex = mPlayer.getCurrentSongIndex();
            if (currentSongIndex < (songList.size() - 1)) {
                mPlayer.setSong(currentSongIndex + 1);
                mPlayer.playSong();
                currentSongIndex = currentSongIndex + 1;
            } else {
                mPlayer.setSong(0);
                mPlayer.playSong();
                currentSongIndex = 0;
            }
        }
        if(v.getId()==R.id.albumArt)
        {
            Intent intent = new Intent(getActivity(),PlaySongActivityMusicPlayer.class);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
        }
    }

    public void updateInfo()
    {
        imageView.setImageBitmap(songList.get(mPlayer.getCurrentSongIndex()).getImage());
        sTitle.setText("" + songList.get(mPlayer.getCurrentSongIndex()).getTitle());
    }

    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == R.id.btnBackward) {
            sTitle.setText("" + mPlayer.getSongTitle());
            int currentPosition = (int) mPlayer.getCurrentPosition();
            if (currentPosition - seekBackwardTime >= 0) {
                mPlayer.seek(currentPosition - seekBackwardTime);
            } else {
                mPlayer.seek(0);
            }
        }
        if (v.getId() == R.id.btnForward) {
            sTitle.setText("" + mPlayer.getSongTitle());
            int currentPosition = (int) mPlayer.getCurrentPosition();
            if (currentPosition + seekForwardTime <= mPlayer.getDuration()) {
                mPlayer.seek(currentPosition + seekForwardTime);
            } else {
                mPlayer.seek((int) mPlayer.getDuration());
            }
        }
        return false;
    }

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = mPlayer.getDuration();
            long currentDuration = mPlayer.getCurrentPosition();
            songTotalDurationLabel.setText("" + utils.milliSecondsToTimer(totalDuration));
            songCurrentDurationLabel.setText("" + utils.milliSecondsToTimer(currentDuration));
            int progress = utils.getProgressPercentage(currentDuration, totalDuration);
            songProgressBar.setProgress(progress);
            mHandler.postDelayed(this, 100);
            //if (totalDuration == currentDuration) {
                   // updateInfo();
            //}
        }
    };

    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
        int totalDuration = (int) mPlayer.getDuration();
        int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);
        mPlayer.seek(currentPosition);
        updateProgressBar();

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        songID = (int) mPlayer.getSongLiss().get(position).getID();
        return false;
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(v.getId()==R.id.song_list) {
            menu.setHeaderTitle("Menu");
            menu.add(0, v.getId(), 0, "Add to Playlist");
            menu.add(0, v.getId(), 0, "Set as");
            menu.add(0, v.getId(), 0, "Delete");
        }
        else
        {
            menu.setHeaderTitle("Playlists");
            menu.add(0, v.getId(), 0, "new");
        }
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals("Add to Playlist")){
            Toast.makeText(getActivity(), "Action 1 invoked", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                    getActivity());
            builderSingle.setIcon(R.drawable.ic_launcher);
            builderSingle.setTitle("Select One Name:-");
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    getActivity(),
                    android.R.layout.select_dialog_singlechoice);
            for(int i=0;i<playlists.length;i++) {
                arrayAdapter.add(playlists[i]);
            }
            builderSingle.setNegativeButton("cancel",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            builderSingle.setAdapter(arrayAdapter,
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final int dPosition = which;
                            String strName = arrayAdapter.getItem(which);
                            AlertDialog.Builder builderInner = new AlertDialog.Builder(
                                    getActivity());
                            builderInner.setMessage(strName);
                            builderInner.setTitle("Your Selected Item is");
                            builderInner.setPositiveButton("Ok",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which) {
                                            Log.d("songID", String.valueOf(songID));
                                            Log.d("position", String.valueOf(dPosition));
                                            Log.d("playlistid",playlistID[dPosition]);
                                            addToPlaylist(getActivity().getApplicationContext().getContentResolver(), songID, Integer.parseInt(playlistID[dPosition]));
                                            dialog.dismiss();
                                        }
                                    });
                            builderInner.show();
                        }
                    });
            builderSingle.show();
        } else if (item.getTitle().equals("Set as")) {
            Toast.makeText(getActivity(), "Action 2 invoked", Toast.LENGTH_SHORT).show();
        } else if (item.getTitle().equals("Delete")) {
            Toast.makeText(getActivity(), "Action 3 invoked", Toast.LENGTH_SHORT).show();
        } else {
            return false;
        }
        return true;
    }


    public static void addToPlaylist(ContentResolver resolver, int audioId,int YOUR_PLAYLIST_ID) {
        Log.d("playlistid", String.valueOf(YOUR_PLAYLIST_ID));
        String[] cols = new String[] {
                "count(*)"
        };
        Uri uri = MediaStore.Audio.Playlists.Members.getContentUri("external", YOUR_PLAYLIST_ID);
        Cursor cur = resolver.query(uri, cols, null, null, null);
        cur.moveToFirst();
        final int base = cur.getInt(0);
        cur.close();
        ContentValues values = new ContentValues();
        values.put(MediaStore.Audio.Playlists.Members.PLAY_ORDER, Integer.valueOf(base + audioId));
        values.put(MediaStore.Audio.Playlists.Members.AUDIO_ID, audioId);
        resolver.insert(uri, values);
    }

    public static void removeFromPlaylist(ContentResolver resolver, int audioId,int YOUR_PLAYLIST_ID) {
        Log.v("made it to add", "" + audioId);
        String[] cols = new String[] {
                "count(*)"
        };
        Uri uri = MediaStore.Audio.Playlists.Members.getContentUri("external", YOUR_PLAYLIST_ID);
        Cursor cur = resolver.query(uri, cols, null, null, null);
        cur.moveToFirst();
        final int base = cur.getInt(0);
        cur.close();
        ContentValues values = new ContentValues();

        resolver.delete(uri, MediaStore.Audio.Playlists.Members.AUDIO_ID + " = " + audioId, null);
    }

    public void getPlayLists()
    {
        String[] proj = {"*"};
        String id = "";
        Uri tempPlaylistURI = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;
        Cursor playListCursor= getActivity().managedQuery(tempPlaylistURI, proj,id, null, null);
        if(playListCursor == null){
            System.out.println("Not having any Playlist on phone --------------");
        }
        System.gc();
        String playListName = null;
        String playID = null;
        playlists = new String[playListCursor.getCount()];
        playlistID = new String[playListCursor.getCount()];

        for(int i = 0; i <playListCursor.getCount() ; i++)
        {
            playListCursor.moveToPosition(i);
            playListName = playListCursor.getString(playListCursor.getColumnIndex("name"));
            playID = playListCursor.getString(0);
            System.out.println("> " + i + "  : " + playListName );
            System.out.println("> " + i + "  : " + playID );
            playlists[i]=playListName;
            playlistID[i] = playID;
        }
    }
}


