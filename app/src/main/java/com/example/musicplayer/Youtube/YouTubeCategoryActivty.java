package com.example.musicplayer.Youtube;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.musicplayer.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mokshaDev on 4/7/2015.
 */
public class YouTubeCategoryActivty extends Activity implements AdapterView.OnItemClickListener {
    private final String TAG_CATEGORY="Cat";
    private ListView lv;
    ArrayAdapter<String> adapter;
    EditText inputSearch;
    ArrayList<HashMap<String, String>> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube_category_layout);
        String categories[] = {"Autos & Vehicles","Education", "Entertainment", "Film & Animation","Comedy", "Howto & Style","Gaming", "Music",
                "News & Politics", "Nonprofits & Activism",
                "On The Web", "Pets & Animals","Science & Technology","Sports","Travel & Events"};

        lv = (ListView) findViewById(R.id.list_view);
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.product_name, categories);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,YouTubeCategoryResultsActivty.class);
        if(position==0)
        {
            intent.putExtra(TAG_CATEGORY,"Autos");
            startActivity(intent);
        }
        if(position==1)
        {
            intent.putExtra(TAG_CATEGORY,"Education");
            startActivity(intent);
        }
        if(position==2)
        {
            intent.putExtra(TAG_CATEGORY,"Entertainment");
            startActivity(intent);
        }
        if(position==3)
        {
            intent.putExtra(TAG_CATEGORY,"Film");
            startActivity(intent);
        }
        if(position==4)
        {
            intent.putExtra(TAG_CATEGORY,"Comedy");
            startActivity(intent);
        }
        if(position==5)
        {
            intent.putExtra(TAG_CATEGORY,"Howto");
            startActivity(intent);
        }
        if(position==6)
        {
            intent.putExtra(TAG_CATEGORY,"Games");
            startActivity(intent);
        }
        if(position==7)
        {
            intent.putExtra(TAG_CATEGORY,"Music");
            startActivity(intent);
        }
        if(position==8)
        {
            intent.putExtra(TAG_CATEGORY,"News");
            startActivity(intent);
        }
        if(position==9)
        {
            intent.putExtra(TAG_CATEGORY,"Nonprofit");
            startActivity(intent);
        }
        if(position==10)
        {
            intent.putExtra(TAG_CATEGORY,"People");
            startActivity(intent);
        }
        if(position==11)
        {
            intent.putExtra(TAG_CATEGORY,"Animals");
            startActivity(intent);
        }
        if(position==12)
        {
            intent.putExtra(TAG_CATEGORY,"Tech");
            startActivity(intent);
        }
        if(position==12)
        {
            intent.putExtra(TAG_CATEGORY,"Sports");
            startActivity(intent);
        }
        if(position==13)
        {
            intent.putExtra(TAG_CATEGORY,"Travel");
            startActivity(intent);
        }

    }
}
