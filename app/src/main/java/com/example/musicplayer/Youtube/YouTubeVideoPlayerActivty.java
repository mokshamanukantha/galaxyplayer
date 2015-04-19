package com.example.musicplayer.Youtube;

import android.content.Intent;
import android.os.Bundle;

import com.example.musicplayer.DeveloperKey;
import com.example.musicplayer.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Created by mokshaDev on 4/7/2015.
 */
public class YouTubeVideoPlayerActivty extends YouTubeFailureRecoveryActivity implements YouTubePlayer.OnInitializedListener {

    private final String TAG_ID="id";
    private String videoID="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube_video_player_layout);
        Intent intent= getIntent();
        videoID = intent.getStringExtra(TAG_ID);
        YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(DeveloperKey.DEVELOPER_KEY,this);



    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo(videoID);
            player.setFullscreen(true);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }
}
