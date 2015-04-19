package com.example.musicplayer.VideoPlayer;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.musicplayer.R;

/**
 * Created by mokshaDev on 4/18/2015.
 */
public class VideoViewActivity extends Activity {
    private final static String TAG_P="path";
    VideoView video;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_player);
        video = (VideoView) findViewById(R.id.video_view);
        MediaController controller = new MediaController(this);
        video.setMediaController(controller);
        Intent i = getIntent();
        String path = i.getStringExtra(TAG_P);
        video.setVideoPath(path);
        video.start();
    }

    protected void onPause() {
        super.onPause();
        video.suspend();
    }


    @Override
    protected void onResume() {
        super.onResume();
        video.resume();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
