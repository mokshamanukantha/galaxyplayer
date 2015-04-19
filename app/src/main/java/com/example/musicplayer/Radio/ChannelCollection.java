package com.example.musicplayer.Radio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.musicplayer.R;


public class ChannelCollection extends ActionBarActivity {

    String choosenChannel;
    public static String CHANNEL = "com.samaraweera.kalana.galaxyradio.CHANNEL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_collection);
        final String channel_names[] = {"GoldFm 93 93.2", "Shaa FM 90.9 91.1", "Hiru FM 96.1 96.3", "SUN FM 98.9 98.7", "TNL ROX 99.2 101.8", "Heart FM", "Sooryan FM 103.6 103.4", "Kiss FM 96.9", "Y FM 92.7", "Kirula FM", "Neth FM", "Sirasa FM"};
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.mylist, channel_names);
        ListView list = (ListView) findViewById(R.id.channellist);
        list.setAdapter(adapter);


        ListView mylist = (ListView) findViewById(R.id.channellist);

        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                TextView textview = (TextView) viewClicked;

                String myItem = textview.getText().toString();
                choosenChannel = myItem;

                navigateRadio(choosenChannel);
            }


        });


    }


    public void navigateRadio(String channel) {


        Intent myintent = new Intent(this, Player.class);
        myintent.putExtra(CHANNEL, channel);

        startActivity(myintent);


    }
}
