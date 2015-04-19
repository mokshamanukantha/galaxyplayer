package com.example.musicplayer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.musicplayer.MusicPlayer.MusicPlayerMainActivity;
import com.example.musicplayer.Radio.RadioMainActivty;
import com.example.musicplayer.Radio.SchedulerHome;
import com.example.musicplayer.VideoPlayer.VideoPlayerActivty;
import com.example.musicplayer.Youtube.YouTubePlayerActivity;
import com.gc.materialdesign.views.ButtonRectangle;

/**
 * Created by mokshaDev on 4/7/2015.
 */
public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        this.setSupportActionBar(toolbar);
        final ButtonRectangle btplayer = (ButtonRectangle) findViewById(R.id.btnMusicPlayer);
        final ButtonRectangle btradio = (ButtonRectangle) findViewById(R.id.btnRadioPlayer);
        final ButtonRectangle btvadio = (ButtonRectangle) findViewById(R.id.btnVideoPlayer);
        final ButtonRectangle btYouTubeStreaming = (ButtonRectangle) findViewById(R.id.btnYouTubePlayer);
        final ButtonRectangle btRadioShedule = (ButtonRectangle) findViewById(R.id.btnRadioShedule);

        final Intent VideoplayerIntent = new Intent(this,VideoPlayerActivty.class);
        final Intent MusicplayerIntent = new Intent(this,MusicPlayerMainActivity.class);
        final Intent RadioIntent = new Intent(this,RadioMainActivty.class);
        final Intent youtubeIntent = new Intent(this,YouTubePlayerActivity.class);
        final Intent radioshedule = new Intent(this,SchedulerHome.class);


        btvadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(VideoplayerIntent);
            }
        });

        btradio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(RadioIntent);
            }
        });

        btplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MusicplayerIntent);
            }
        });

        btYouTubeStreaming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(youtubeIntent);
            }
        });

        btRadioShedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(radioshedule);
            }
        });

        if (toolbar != null) {
            toolbar.setTitle("Galaxy Player");
            setSupportActionBar(toolbar);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}
