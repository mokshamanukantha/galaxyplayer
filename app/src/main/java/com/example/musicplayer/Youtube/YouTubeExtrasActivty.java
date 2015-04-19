package com.example.musicplayer.Youtube;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.musicplayer.LogingMainActivty;
import com.example.musicplayer.R;
import com.gc.materialdesign.views.ButtonRectangle;

/**
 * Created by mokshaDev on 4/8/2015.
 */
public class YouTubeExtrasActivty extends ActionBarActivity implements View.OnClickListener {
    private final String TAG_ACTIVITY = "activity";
    ButtonRectangle btnPlaylist;
    ButtonRectangle btnUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.youtube_extras_layout);
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        this.setSupportActionBar(toolbar);

        toolbar.setTitle("YouTube Player");

        btnPlaylist = (ButtonRectangle) findViewById(R.id.btnYouTubePlaylist);
        btnUpload = (ButtonRectangle) findViewById(R.id.btnYouTubeUpload);

        btnPlaylist.setOnClickListener(this);
        btnUpload.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent ;
        if(v.getId()==R.id.btnYouTubePlaylist)
        {
            intent = new Intent(this,LogingMainActivty.class);
            intent.putExtra(TAG_ACTIVITY,"playlist");
            startActivity(intent);
        }
        if(v.getId()==R.id.btnYouTubeUpload)
        {
            intent = new Intent(this,LogingMainActivty.class);
            intent.putExtra(TAG_ACTIVITY,"upload");
            startActivity(intent);
        }
    }
}
