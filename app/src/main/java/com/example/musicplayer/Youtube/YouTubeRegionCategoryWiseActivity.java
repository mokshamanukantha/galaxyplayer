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
public class YouTubeRegionCategoryWiseActivity extends Activity implements AdapterView.OnItemClickListener {
    private final String TAG_CATEGORY="Cat";
    private ListView lv;
    ArrayAdapter<String> adapter;
    EditText inputSearch;
    ArrayList<HashMap<String, String>> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube_region_category_layout);
        String categories[] = {"Argentina","Australia",
                "Austria", "Belgium","Brazil",
                "Canada","Chile", "Colombia",
                "Czech Republic", "Egypt",
                "France", "Germany","Great Britain",
                "Hong Kong","Hungary","India","Ireland","Israel","Italy","Japan"};

        lv = (ListView) findViewById(R.id.list_view);
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.product_name, categories);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,YouTubeRegionWiseResultsActivty.class);
        if(position==0)
        {
            intent.putExtra(TAG_CATEGORY,"AR");
            startActivity(intent);
        }
        if(position==1)
        {
            intent.putExtra(TAG_CATEGORY,"AU");
            startActivity(intent);
        }
        if(position==2)
        {
            intent.putExtra(TAG_CATEGORY,"AT");
            startActivity(intent);
        }
        if(position==3)
        {
            intent.putExtra(TAG_CATEGORY,"BE");
            startActivity(intent);
        }
        if(position==4)
        {
            intent.putExtra(TAG_CATEGORY,"BR");
            startActivity(intent);
        }
        if(position==5)
        {
            intent.putExtra(TAG_CATEGORY,"CA");
            startActivity(intent);
        }
        if(position==6)
        {
            intent.putExtra(TAG_CATEGORY,"CL");
            startActivity(intent);
        }
        if(position==7)
        {
            intent.putExtra(TAG_CATEGORY,"CO");
            startActivity(intent);
        }
        if(position==8)
        {
            intent.putExtra(TAG_CATEGORY,"CZ");
            startActivity(intent);
        }
        if(position==9)
        {
            intent.putExtra(TAG_CATEGORY,"EG");
            startActivity(intent);
        }
        if(position==10)
        {
            intent.putExtra(TAG_CATEGORY,"FR");
            startActivity(intent);
        }
        if(position==11)
        {
            intent.putExtra(TAG_CATEGORY,"DE");
            startActivity(intent);
        }
        if(position==12)
        {
            intent.putExtra(TAG_CATEGORY,"GB");
            startActivity(intent);
        }
        if(position==12)
        {
            intent.putExtra(TAG_CATEGORY,"HK");
            startActivity(intent);
        }
        if(position==13)
        {
            intent.putExtra(TAG_CATEGORY,"HU");
            startActivity(intent);
        }
        if(position==13)
        {
            intent.putExtra(TAG_CATEGORY,"IN");
            startActivity(intent);
        }
        if(position==13)
        {
            intent.putExtra(TAG_CATEGORY,"IE");
            startActivity(intent);
        }
        if(position==13)
        {
            intent.putExtra(TAG_CATEGORY,"IL");
            startActivity(intent);
        }
        if(position==13)
        {
            intent.putExtra(TAG_CATEGORY,"IT");
            startActivity(intent);
        }
        if(position==13)
        {
            intent.putExtra(TAG_CATEGORY,"JP");
            startActivity(intent);
        }


    }
}
