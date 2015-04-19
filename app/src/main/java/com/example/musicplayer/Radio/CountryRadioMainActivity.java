package com.example.musicplayer.Radio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.musicplayer.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mokshaDev on 4/19/2015.
 */
public class CountryRadioMainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {
    private static final String TAG_ID="id";
    ArrayList<String> id = new ArrayList<String>();
    ArrayList<String> name = new ArrayList<String>();
    ArrayList<String> description = new ArrayList<String>();
    ProgressDialog pd;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_radio_main_layout);
        listView = (ListView) findViewById(R.id.list_view);
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        pd = new ProgressDialog(this);
        pd.setTitle("Loading..");
        new TheTask().execute();
        listView.setOnItemClickListener(this);
        if (toolbar != null) {
            toolbar.setTitle("Country Radio Categories");
            setSupportActionBar(toolbar);
        }
    }

    public void getData()
    {
        String link = "http://api.dirble.com/v1/primaryCategories/apikey/11a9e0c2097def32551fad79c24ae895f50a726f";
        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        HttpGet request = new HttpGet(link);
        try
        {
            HttpResponse response = httpclient.execute(request);
            HttpEntity resEntity = response.getEntity();
            String _response= EntityUtils.toString(resEntity); // content will be consume only once

            JSONArray json = new JSONArray(_response);

            JSONArray jsonArray = json;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String title1 = jsonObject.getString("name");
                String category1 = jsonObject.getString("description");
                String id1 = jsonObject.getString("id");

                id.add(id1);
                name.add(title1);
                description.add(category1);
            }
        }
        catch(Exception e1)
        {
            e1.printStackTrace();
        }

        httpclient.getConnectionManager().shutdown();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView txt = (TextView) view.findViewById(R.id.id);
        String rid = txt.getText().toString();
        Intent intent = new Intent(this,CoutryRadioCategoryResultActivty.class);
        intent.putExtra(TAG_ID,rid);
        startActivity(intent);
    }

    class TheTask extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            pd.dismiss();
            CountryRadioAdapter you = new CountryRadioAdapter(CountryRadioMainActivity.this,id,name,description);
            listView.setAdapter(you);
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            getData();
            return null;
        }

    }

}
