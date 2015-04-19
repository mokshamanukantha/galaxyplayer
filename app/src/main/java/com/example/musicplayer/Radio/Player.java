package com.example.musicplayer.Radio;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.musicplayer.R;
import com.example.musicplayer.visualizer.VisualizerView;
import com.example.musicplayer.visualizer.renderer.BarGraphRenderer;
import com.example.musicplayer.visualizer.renderer.LineRenderer;

import java.io.IOException;


public class Player extends ActionBarActivity {


    final String channel_urls[] = {"http://37.130.229.188:8020/;?1428806857419.mp3", "http://37.130.229.188:8025/;?1428770211271.mp3", "http://37.130.229.188:8010/;?1428770335553.mp3", "http://37.130.229.188:8070/;?1428770604970.mp3", "http://192.99.99.195:8010/live.mp3", "http://198.100.148.141:8054/;.mp3", "http://37.130.229.188:8050/;?1428805385029.mp3", "http://198.178.123.8:8404/;?1428805385029.mp3", "http://76.164.217.100:7036/;?1428805385029.mp3", "http://206.190.130.180:8044/;?1428805385029.mp3;", "http://66.55.144.67:8104/;?1428805385029.mp3", "http://76.164.217.100:7024/;?1428805385029.mp3"};
    final String channel_names[] = {"GoldFm 93 93.2", "Shaa FM 90.9 91.1", "Hiru FM 96.1 96.3", "SUN FM 98.9 98.7", "TNL ROX 99.2 101.8", "Heart FM", "Sooryan FM 103.6 103.4", "Kiss FM 96.9", "Y FM 92.7", "Kirula FM", "Neth FM", "Sirasa FM"};
    private String currentChannel;
    private int counter;
    private MediaPlayer mediaPlayer;
    AudioManager audioManager;
    private VisualizerView mVisualizerView;
    private MediaPlayer mSilentPlayer;
    WifiManager.WifiLock wifiLock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        counter = 0;

        ImageButton playButton = (ImageButton) findViewById(R.id.btnplay);
        ImageButton pauseButton = (ImageButton) findViewById(R.id.btnstop);
        ImageButton nextButton = (ImageButton) findViewById(R.id.btnforward);
        ImageButton backButton = (ImageButton) findViewById(R.id.btnback);
        SeekBar seekvolume = (SeekBar) findViewById(R.id.volume);
        final TextView channel = (TextView) findViewById(R.id.channelname);

        Intent intent = getIntent();
        String mychannel = intent.getStringExtra(ChannelCollection.CHANNEL);
        //startActivity(intent);
        currentChannel = channel_names[counter];
        channel.setText(currentChannel);
        //   channelcount.setText(String.valueOf(counter));
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        seekvolume.setMax(maxVolume);
        seekvolume.setProgress(currVolume);

        mediaPlayer = new MediaPlayer();
        mVisualizerView = (VisualizerView) findViewById(R.id.visualizerView);
        mediaPlayer.setWakeMode(this,
                PowerManager.PARTIAL_WAKE_LOCK);
        wifiLock = ((WifiManager) getSystemService(Context.WIFI_SERVICE))
                .createWifiLock(WifiManager.WIFI_MODE_FULL, "mylock");

        wifiLock.acquire();

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        for (int i = 0; i < channel_names.length; i++) {
            if (mychannel.equals(channel_names[i])) {
                counter = i;
                currentChannel = channel_names[counter];
                channel.setText(currentChannel);
                //channelcount.setText(String.valueOf(counter));
                if (!mediaPlayer.isPlaying()) {
                    try {
                        mediaPlayer.setDataSource(channel_urls[counter]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        mediaPlayer.prepare(); // might take long! (for buffering, etc)
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mediaPlayer.start();
                }
            }
        }
        seekvolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.reset();
                if (counter < channel_urls.length - 1) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    counter++;
                    currentChannel = channel_names[counter];
                    channel.setText(currentChannel);
                    //channelcount.setText(String.valueOf(counter));
                    if (!mediaPlayer.isPlaying()) {
                        try {
                            mediaPlayer.setDataSource(channel_urls[counter]);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            mediaPlayer.prepare(); // might take long! (for buffering, etc)
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        mediaPlayer.start();
                    }
                } else {
                    //Toast.makeText(Player.this,
                    //"No Channels Found", Toast.LENGTH_LONG).show();
                    counter = 0;
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    currentChannel = channel_names[counter];
                    channel.setText(currentChannel);
                    if (!mediaPlayer.isPlaying()) {
                        try {
                            mediaPlayer.setDataSource(channel_urls[counter]);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            mediaPlayer.prepare(); // might take long! (for buffering, etc)
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mediaPlayer.start();
                    }
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.reset();
                if (counter > 0) {
                    counter--;
                    currentChannel = channel_names[counter];
                    channel.setText(currentChannel);
                    // channelcount.setText(String.valueOf(counter));
                    if (!mediaPlayer.isPlaying()) {
                        try {
                            mediaPlayer.setDataSource(channel_urls[counter]);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            mediaPlayer.prepare(); // might take long! (for buffering, etc)
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mediaPlayer.start();
                    }
                } else {// Toast.makeText(Player.this,
                    // "No Channels Found", Toast.LENGTH_LONG).show();
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    counter = channel_urls.length - 1;
                    currentChannel = channel_names[counter];
                    channel.setText(currentChannel);
                    if (!mediaPlayer.isPlaying()) {
                        try {
                            mediaPlayer.setDataSource(channel_urls[counter]);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            mediaPlayer.prepare(); // might take long! (for buffering, etc)
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mediaPlayer.start();
                    }
                }
            }
        });
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mediaPlayer.isPlaying()) {
                    try {
                        mediaPlayer.setDataSource(channel_urls[counter]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        mediaPlayer.prepare(); // might take long! (for buffering, etc)
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    mediaPlayer.start();

                }
            }


        });
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mediaPlayer.stop();
                mediaPlayer.reset();

            }
        });

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Player.super.onResume();
            mediaPlayer.reset();
            mediaPlayer.stop();
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    private void addLineRenderer()
    {
        Paint linePaint = new Paint();
        linePaint.setStrokeWidth(1f);
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.argb(88, 0, 128, 255));

        Paint lineFlashPaint = new Paint();
        lineFlashPaint.setStrokeWidth(5f);
        lineFlashPaint.setAntiAlias(true);
        lineFlashPaint.setColor(Color.argb(188, 255, 255, 255));
        LineRenderer lineRenderer = new LineRenderer(linePaint, lineFlashPaint, true);
        mVisualizerView.addRenderer(lineRenderer);
    }
    private void addBarGraphRenderers()
    {
        Paint paint = new Paint();
        paint.setStrokeWidth(50f);
        paint.setAntiAlias(true);
        paint.setColor(Color.argb(200, 56, 138, 252));
        BarGraphRenderer barGraphRendererBottom = new BarGraphRenderer(16, paint, false);
        mVisualizerView.addRenderer(barGraphRendererBottom);

        Paint paint2 = new Paint();
        paint2.setStrokeWidth(12f);
        paint2.setAntiAlias(true);
        paint2.setColor(Color.argb(200, 181, 111, 233));
        BarGraphRenderer barGraphRendererTop = new BarGraphRenderer(4, paint2, true);
        mVisualizerView.addRenderer(barGraphRendererTop);
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        init();
    }

    private void init() {
        if(mediaPlayer.isPlaying()) {
            mVisualizerView.link(mediaPlayer);
        }
        addBarGraphRenderers();
    }

    @Override
    protected void onPause()
    {
        cleanUp();
        super.onPause();
    }

    @Override
    protected void onDestroy()
    {
        wifiLock.release();
        cleanUp();
        super.onDestroy();
    }

    private void cleanUp()
    {
        if (mediaPlayer != null)
        {
            mVisualizerView.release();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        if (mSilentPlayer != null)
        {
            mSilentPlayer.release();
            mSilentPlayer = null;
        }
    }

  /*  @Override
    public void  onPause()
    {
        Player.super.onResume();
        if(mediaPlayer.isPlaying())
        {
           // mediaPlayer.pause();
        }


   }
*/
}
