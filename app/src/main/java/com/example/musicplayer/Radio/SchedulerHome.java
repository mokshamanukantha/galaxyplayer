package com.example.musicplayer.Radio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.musicplayer.R;


public class SchedulerHome extends ActionBarActivity {
public static String CHANNEL="com.samaraweera.kalana.radio.CHANNEL";
    public static String choosenChannel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler_home);
        Intent intent =getIntent();
       String[] myChannels={"Hiru FM","Gold FM","Sri FM","Sirasa FM","Yes FM","Y FM"};


        ArrayAdapter adapter=new ArrayAdapter(this,R.layout.mylist,myChannels);
        ListView list=(ListView)findViewById(R.id.channels);
        list.setAdapter(adapter);


        ListView mylist = (ListView) findViewById(R.id.channels);
        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id)
            {
                TextView textview = (TextView) viewClicked;

                String myItem = textview.getText().toString();
                choosenChannel=myItem;

                navigateDaySelect(choosenChannel);
            }


        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scheduler_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void navigateDaySelect(String channelName)
    {
        Intent myintent=new Intent(this,DaySelect.class);
        myintent.putExtra(CHANNEL,channelName);
        startActivity(myintent);


    }









}
