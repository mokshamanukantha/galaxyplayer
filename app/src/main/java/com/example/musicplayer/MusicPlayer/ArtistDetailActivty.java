package com.example.musicplayer.MusicPlayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.musicplayer.R;
import com.example.musicplayer.Utilities;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mokshaDev on 4/17/2015.
 */
public class ArtistDetailActivty extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener, View.OnLongClickListener, SeekBar.OnSeekBarChangeListener, AdapterView.OnItemLongClickListener {
    private static final String TAG_ID="id";
    private static final long TAG_NUM=0;
    MusicPlayer mPlayer;
    ListView list;
    long artist_id;
    Utilities utilities;
    long [] songlist;
    SongAdapter songAdt;
    private ArrayList<Song> songList;
    private ArrayList<Song> artistSongList;
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
    CircleImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_detail_layout);
        mPlayer = (MusicPlayer)getApplication();
        songList = new ArrayList<Song>();
        if(!mPlayer.isListNull()) {
            songList = mPlayer.getSongLiss();
        }
        sTitle = (TextView) findViewById(R.id.songTitle);
        bPlay = (ImageButton) findViewById(R.id.btnPlay);
        bForward = (ImageButton) findViewById(R.id.btnForward);
        bBackward = (ImageButton) findViewById(R.id.btnBackward);
        songProgressBar = (SeekBar) findViewById(R.id.songProgressBar);
        songCurrentDurationLabel = (TextView) findViewById(R.id.songCurrentDurationLabel);
        songTotalDurationLabel = (TextView) findViewById(R.id.songTotalDurationLabel);
        list = (ListView) findViewById(R.id.list_view);
        utilities = new Utilities();
        Intent intent = getIntent();
        artist_id = intent.getLongExtra(TAG_ID, TAG_NUM);
        artistSongList = new ArrayList<Song>();

        songlist = Utilities.getSongListForArtist(this, artist_id);
        for(int i=0;i<songlist.length;i++)
        {
            for(int j=0;j<songList.size();j++)
            {
                if(songlist[i]==songList.get(j).getID())
                {
                    artistSongList.add(songList.get(j));
                }
            }
        }
        songAdt = new SongAdapter(this, artistSongList);
        list.setAdapter(songAdt);
        list.setOnItemClickListener(this);
        bPlay.setOnClickListener(this);
        bForward.setOnClickListener(this);
        bBackward.setOnClickListener(this);
        bForward.setOnLongClickListener(this);
        bBackward.setOnLongClickListener(this);
        imageView = (CircleImageView) findViewById(R.id.albumArt);
        imageView.setOnClickListener(this);
        songProgressBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mPlayer.setSong(mPlayer.getSongArrayPos(artistSongList.get(Integer.parseInt(view.getTag().toString())).getID()));
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
                mPlayer.setSong(mPlayer.getSongArrayPos(artistSongList.get(currentSongIndex - 1).getID()));
                mPlayer.playSong();
                currentSongIndex = currentSongIndex - 1;
            } else {
                mPlayer.setSong(mPlayer.getSongArrayPos(artistSongList.get(artistSongList.size() - 1).getID()));
                mPlayer.playSong();
                currentSongIndex = artistSongList.size() - 1;
            }
        }
        if (v.getId() == R.id.btnForward) {
            sTitle.setText("" + mPlayer.getSongTitle());
            currentSongIndex = mPlayer.getCurrentSongIndex();
            if (currentSongIndex < (artistSongList.size() - 1)) {
                mPlayer.setSong(mPlayer.getSongArrayPos(artistSongList.get(currentSongIndex + 1).getID()));
                mPlayer.playSong();
                currentSongIndex = currentSongIndex + 1;
            } else {
                mPlayer.setSong(mPlayer.getSongArrayPos(artistSongList.get(0).getID()));
                mPlayer.playSong();
                currentSongIndex = 0;
            }
        }
        if(v.getId()==R.id.albumArt)
        {
            Intent intent = new Intent(this,PlaySongActivityMusicPlayer.class);
            startActivity(intent);
            overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
        }
    }

    public void updateInfo()
    {
        imageView.setImageBitmap(artistSongList.get(mPlayer.getCurrentSongIndex()).getImage());
        sTitle.setText("" + artistSongList.get(mPlayer.getCurrentSongIndex()).getTitle());
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
            Log.d("total", String.valueOf(totalDuration));
            Log.d("current", String.valueOf(currentDuration));
            songTotalDurationLabel.setText("" + utilities.milliSecondsToTimer(totalDuration));
            songCurrentDurationLabel.setText("" + utilities.milliSecondsToTimer(currentDuration));
            int progress = utilities.getProgressPercentage(currentDuration, totalDuration);
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
        int currentPosition = utilities.progressToTimer(seekBar.getProgress(), totalDuration);
        mPlayer.seek(currentPosition);
        updateProgressBar();

    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }
}
