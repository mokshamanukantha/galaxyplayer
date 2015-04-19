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
 * Created by mokshaDev on 4/7/2015.
 */
public class YouTubePlayerActivity extends ActionBarActivity implements View.OnClickListener {
    private final String TAG_ACTIVITY = "activity";
    ButtonRectangle btnCategory;
    ButtonRectangle btnRegion;
    ButtonRectangle btnRelated;
    ButtonRectangle btnFavorites;
    ButtonRectangle btnWhatchLater;
    ButtonRectangle btnExtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.you_tube_player_activty_layout);
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        this.setSupportActionBar(toolbar);

        toolbar.setTitle("YouTube Player");

        btnCategory = (ButtonRectangle) findViewById(R.id.btnYouTubeCategories);
        btnRegion= (ButtonRectangle) findViewById(R.id.btnYouTubeRegion);
        btnRelated= (ButtonRectangle) findViewById(R.id.btnYouTubeRelated);
        btnFavorites= (ButtonRectangle) findViewById(R.id.btnYouTubeFavorites);
        btnWhatchLater = (ButtonRectangle) findViewById(R.id.btnYouTubeWhatchLater);
        btnExtras = (ButtonRectangle) findViewById(R.id.btnYouTubeExtras);

        btnCategory.setOnClickListener(this);
        btnRegion.setOnClickListener(this);
        btnRelated.setOnClickListener(this);
        btnFavorites.setOnClickListener(this);
        btnWhatchLater.setOnClickListener(this);
        btnExtras.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        Intent intent ;
        if(v.getId()==R.id.btnYouTubeCategories)
        {
            intent = new Intent(this,YouTubeCategoryActivty.class);
            startActivity(intent);
        }
        if(v.getId()==R.id.btnYouTubeRegion)
        {
            intent = new Intent(this,YouTubeRegionCategoryWiseActivity.class);
            startActivity(intent);
        }
        if(v.getId()==R.id.btnYouTubeRelated)
        {
            intent = new Intent(this,YouTubeRelatedActivty.class);
            startActivity(intent);
        }
        if(v.getId()==R.id.btnYouTubeFavorites)
        {
            intent = new Intent(this,LogingMainActivty.class);
            intent.putExtra(TAG_ACTIVITY,"favorites");
            startActivity(intent);
        }
        if(v.getId()==R.id.btnYouTubeWhatchLater)
        {
            intent = new Intent(this,LogingMainActivty.class);
            intent.putExtra(TAG_ACTIVITY,"watchlater");
            startActivity(intent);
        }
        if(v.getId()==R.id.btnYouTubeExtras)
        {
            intent = new Intent(this,YouTubeExtrasActivty.class);
            startActivity(intent);
        }
    }
}
