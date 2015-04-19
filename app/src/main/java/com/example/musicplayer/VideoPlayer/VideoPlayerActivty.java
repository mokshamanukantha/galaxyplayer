package com.example.musicplayer.VideoPlayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.musicplayer.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mokshaDev on 4/7/2015.
 */
public class VideoPlayerActivty extends Activity implements OnItemClickListener{
    private final static String TAG_P="path";
    Cursor cursor;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_player_activty_layout);

        ListView listView = (ListView) this.findViewById(R.id.ListView);

        String[] thumbColumns = { MediaStore.Video.Thumbnails.DATA,
                MediaStore.Video.Thumbnails.VIDEO_ID };

        String[] mediaColumns = { MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA, MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.MIME_TYPE };

        cursor = managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                mediaColumns, null, null, null);

        ArrayList<VideoViewInfo> videoRows = new ArrayList<VideoViewInfo>();

        if (cursor.moveToFirst()) {
            do {

                VideoViewInfo newVVI = new VideoViewInfo();
                int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media._ID));
                Cursor thumbCursor = managedQuery(
                        MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
                        thumbColumns, MediaStore.Video.Thumbnails.VIDEO_ID
                                + "=" + id, null, null);
                if (thumbCursor.moveToFirst()) {
                    String thumb =  thumbCursor.getString(thumbCursor
                            .getColumnIndex(MediaStore.Video.Thumbnails.DATA));
                    newVVI.thumbPath =thumb;
                    Uri imageUri = Uri.parse(thumb);
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                        newVVI.bitmap = bitmap;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Log.v("", newVVI.thumbPath);
                }

                newVVI.filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                newVVI.title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
                Log.v("", newVVI.title);
                newVVI.mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
                Log.v("", newVVI.mimeType);
                videoRows.add(newVVI);
            } while (cursor.moveToNext());
        }
        listView.setAdapter(new VideoGalleryAdapter(this, videoRows));
        listView.setOnItemClickListener(this);
    }

    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        if (cursor.moveToPosition(position)) {
            int fileColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            int mimeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE);
            String videoFilePath = cursor.getString(fileColumn);
            String mimeType = cursor.getString(mimeColumn);
            Intent intent = new Intent(this,VideoViewActivity.class);
            //File newFile = new File(videoFilePath);
            intent.putExtra(TAG_P, videoFilePath);
            startActivity(intent);
        }
    }
}
class VideoViewInfo {
    String filePath;
    String mimeType;
    String thumbPath;
    String title;
    Bitmap bitmap;
}

class VideoGalleryAdapter extends BaseAdapter {
    private Context context;
    private List<VideoViewInfo> videoItems;

    LayoutInflater inflater;

    public VideoGalleryAdapter(Context _context,
                               ArrayList<VideoViewInfo> _items) {
        context = _context;
        videoItems = _items;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return videoItems.size();
    }

    public Object getItem(int position) {
        return videoItems.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View videoRow = inflater.inflate(R.layout.video_item, null);
        ImageView videoThumb = (ImageView) videoRow
                .findViewById(R.id.ImageView);
        Bitmap bmThumbnail;
        if (videoItems.get(position).filePath != null) {
            bmThumbnail=ThumbnailUtils.createVideoThumbnail(videoItems.get(position).filePath,
                    MediaStore.Video.Thumbnails.MICRO_KIND);
            videoThumb.setImageBitmap(bmThumbnail);
        }

        TextView videoTitle = (TextView) videoRow
                .findViewById(R.id.TextView);
        videoTitle.setText(videoItems.get(position).title);

        return videoRow;
    }
}

