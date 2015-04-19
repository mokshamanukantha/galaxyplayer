package com.example.musicplayer.MusicPlayer;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicplayer.R;
import com.example.musicplayer.Utilities;

import java.util.ArrayList;

/**
 * Created by mokshaDev on 4/6/2015.
 */
public class PlaySongActivityMusicPlayer extends Activity implements View.OnClickListener, View.OnLongClickListener,SeekBar.OnSeekBarChangeListener {
    MusicPlayer mPlayer;
    private TextView sTitle;
    private ImageButton bPlay;
    private ImageButton bForward;
    private ImageButton bBackward;
    private ImageView thumb;
    private SeekBar songProgressBar;
    private TextView songCurrentDurationLabel;
    private TextView songTotalDurationLabel;
    private Utilities utils;
    private boolean isShuffle = false;
    private boolean isRepeat = false;
    private ImageButton btnRepeat;
    private ImageButton btnShuffle;
    private Handler mHandler = new Handler();
    private int seekForwardTime = 5000;
    private int seekBackwardTime = 5000;
    private int currentSongIndex = 0;
    private ArrayList<Song> songList;
    private SeekBar volumeSeekbar = null;
    private AudioManager audioManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_song_layout);
        mPlayer = (MusicPlayer)getApplication();
        songList = new ArrayList<Song>();

        thumb = (ImageView) findViewById(R.id.songThumbnail);
        sTitle = (TextView) findViewById(R.id.songTitle);
        btnRepeat = (ImageButton) findViewById(R.id.btnRepeat);
        btnShuffle = (ImageButton) findViewById(R.id.btnShuffle);
        utils = new Utilities();
        if(!mPlayer.isListNull()) {
            songList = mPlayer.getSongLiss();
            thumb.setImageBitmap(songList.get(mPlayer.getCurrentSongIndex()).getImage());
            sTitle.setText(""+songList.get(mPlayer.getCurrentSongIndex()).getTitle());
        }
        bPlay = (ImageButton) findViewById(R.id.btnPlay);
        bForward = (ImageButton) findViewById(R.id.btnForward);
        bBackward = (ImageButton) findViewById(R.id.btnBackward);
        songProgressBar = (SeekBar) findViewById(R.id.songProgressBar);
        songCurrentDurationLabel = (TextView) findViewById(R.id.songCurrentDurationLabel);
        songTotalDurationLabel = (TextView) findViewById(R.id.songTotalDurationLabel);
        bPlay.setOnClickListener(this);
        bForward.setOnClickListener(this);
        bBackward.setOnClickListener(this);
        bForward.setOnLongClickListener(this);
        bBackward.setOnLongClickListener(this);
        btnRepeat.setOnClickListener(this);
        btnShuffle.setOnClickListener(this);
        if(mPlayer.isPlaying())
        {
            bPlay.setImageResource(R.drawable.ic_pause_white);
        }
        else
            bPlay.setImageResource(R.drawable.ic_play_white);
        songProgressBar.setOnSeekBarChangeListener(PlaySongActivityMusicPlayer.this);
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
            updateInfo();
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
            updateInfo();
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
        if(v.getId()==R.id.btnRepeat)
        {
            if(isRepeat){
                isRepeat = false;
                mPlayer.setRepeatSong(isRepeat);
                Toast.makeText(getApplicationContext(), "Repeat is OFF", Toast.LENGTH_SHORT).show();
                btnRepeat.setImageResource(R.drawable.ic_repeat_one_white);
            }else{
                isRepeat = true;
                mPlayer.setRepeatSong(isRepeat);
                Toast.makeText(getApplicationContext(), "Repeat is ON", Toast.LENGTH_SHORT).show();
                isShuffle = false;
                mPlayer.setShuffelSong(isShuffle);
                btnShuffle.setImageResource(R.drawable.ic_shuffle_white);
            }
        }
        if(v.getId()==R.id.btnShuffle)
        {
            if(isShuffle){
                isShuffle = false;
                mPlayer.setShuffelSong(isShuffle);
                Toast.makeText(getApplicationContext(), "Shuffle is OFF", Toast.LENGTH_SHORT).show();
                btnShuffle.setImageResource(R.drawable.ic_shuffle_white);
            }else{
                isShuffle= true;
                mPlayer.setShuffelSong(isShuffle);
                Toast.makeText(getApplicationContext(), "Shuffle is ON", Toast.LENGTH_SHORT).show();
                isRepeat = false;
                mPlayer.setRepeatSong(isRepeat);
                btnRepeat.setImageResource(R.drawable.ic_repeat_white);
            }
        }
    }

    public void updateInfo()
    {
        thumb.setImageBitmap(songList.get(mPlayer.getCurrentSongIndex()).getImage());
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
            if (totalDuration == currentDuration) {
             updateInfo();
            }
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

}
