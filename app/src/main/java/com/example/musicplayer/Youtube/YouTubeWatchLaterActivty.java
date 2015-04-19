package com.example.musicplayer.Youtube;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by mokshaDev on 4/7/2015.
 */
public class YouTubeWatchLaterActivty extends Activity implements AdapterView.OnItemClickListener {
    private final String TAG_ID="id";
    private final String TAG_TOKEN="token";
    ListView listView;
    ArrayList<String> msg = new ArrayList<String>();
    ArrayList<String> title = new ArrayList<String>();
    ArrayList<Bitmap> thumb = new ArrayList<Bitmap>();
    ArrayList<String> category = new ArrayList<String>();
    ArrayList<String> views = new ArrayList<String>();
    ArrayList<String> duration = new ArrayList<String>();
    ArrayList<String> id = new ArrayList<String>();
    ProgressDialog pd;
    private String TOKEN ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube_category_results_layout);
        listView = (ListView) findViewById(R.id.list);
        pd = new ProgressDialog(this);
        pd.setTitle("Loading..");
        Intent i = getIntent();
        TOKEN = i.getStringExtra(TAG_TOKEN);
        new TheTask().execute();
        listView.setOnItemClickListener(this);
    }

    public void getData()
    {
        String link = "https://gdata.youtube.com/feeds/api/standardfeeds/most_popular?v=2&alt=jsonc";
        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        if(TOKEN !=null)
        {
            link = "https://gdata.youtube.com/feeds/api/users/default/watch_later?access_token="+ TOKEN+"&v=2&alt=jsonc";
        }
        HttpGet request = new HttpGet(link);
        try
        {
            HttpResponse response = httpclient.execute(request);
            HttpEntity resEntity = response.getEntity();
            String _response= EntityUtils.toString(resEntity); // content will be consume only once

            JSONObject json = new JSONObject(_response);

            JSONArray jsonArray = json.getJSONObject("data").getJSONArray("items");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String title1 = jsonObject.getJSONObject("video").getString("title");
                String category1 = jsonObject.getJSONObject("video").getString("category");
                String views1 = jsonObject.getJSONObject("video").getString("viewCount");
                String duration1 = jsonObject.getJSONObject("video").getString("duration");
                String id1 = jsonObject.getJSONObject("video").getString("id");
                id.add(id1);
                title.add(title1);
                category.add(category1);
                views.add(views1);
                duration.add(duration1);
                String thumbUrl = jsonObject.getJSONObject("video").getJSONObject("thumbnail").getString("sqDefault");
                URL url1 = new URL(thumbUrl);
                Bitmap bmp = BitmapFactory.decodeStream(url1.openConnection().getInputStream());
                thumb.add(bmp);
                String url;
                try {

                    url = jsonObject.getJSONObject("video").getJSONObject("player").getString("default");
                    msg.add(url);
                } catch (JSONException ignore) {
                }
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
        TextView textView =(TextView) view.findViewById(R.id.id);
        String vid = textView.getText().toString();
        Intent intent = new Intent(this,YouTubeVideoPlayerActivty.class);
        intent.putExtra(TAG_ID,vid);
        startActivity(intent);
    }

    class TheTask extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            pd.dismiss();
            YouTubeVideoAdapter you = new YouTubeVideoAdapter(YouTubeWatchLaterActivty.this,msg,title,thumb,views,category,duration,id);
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
