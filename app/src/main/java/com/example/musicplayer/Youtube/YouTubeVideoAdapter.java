package com.example.musicplayer.Youtube;

        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.example.musicplayer.R;
        import com.example.musicplayer.Utilities;

        import java.util.ArrayList;

/**
 * Created by mokshaDev on 4/1/2015.
 */
public class YouTubeVideoAdapter extends BaseAdapter {

    Context mContext;
    Intent intent;
    LayoutInflater mInflater;
    ArrayList<String> mVideo= new ArrayList<String>();
    ArrayList<String> mTitle= new ArrayList<String>();
    ArrayList<Bitmap> mThumb= new ArrayList<Bitmap>();
    ArrayList<String> mViews= new ArrayList<String>();
    ArrayList<String> mCategory= new ArrayList<String>();
    ArrayList<String> mDuration= new ArrayList<String>();
    ArrayList<String> mID= new ArrayList<String>();

    public YouTubeVideoAdapter(Context context, ArrayList<String> a, ArrayList title, ArrayList thumb, ArrayList views, ArrayList category, ArrayList duration,ArrayList id)  {
        mContext=context;
        mVideo=a;
        mTitle = title;
        mThumb= thumb;
        mViews = views;
        mCategory = category;
        mDuration = duration;
        mID = id;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mVideo.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View arg1, ViewGroup arg2) {
        ViewHolder viewHolder;
        Utilities utilities = new Utilities();
        if(arg1==null)
        {
            arg1=mInflater.inflate(R.layout.video, null);
            viewHolder= new ViewHolder();
            viewHolder.title =(TextView)arg1.findViewById(R.id.title);
            viewHolder.thumbnail =(ImageView)arg1.findViewById(R.id.thumbnail);
            viewHolder.category =(TextView)arg1.findViewById(R.id.category);
            viewHolder.views =(TextView)arg1.findViewById(R.id.views);
            viewHolder.duration =(TextView)arg1.findViewById(R.id.duration);
            viewHolder.id = (TextView)arg1.findViewById(R.id.id);
            arg1.setTag(viewHolder);
        }
        else
        {
            viewHolder= (ViewHolder)arg1.getTag();
        }
        viewHolder.title.setText(mTitle.get(position));
        viewHolder.thumbnail.setImageBitmap(mThumb.get(position));
        viewHolder.category.setText(mCategory.get(position));
        viewHolder.views.setText(mViews.get(position));
        viewHolder.duration.setText(utilities.milliSecondsToTimer(Long.parseLong(mDuration.get(position))));
        viewHolder.id.setText(mID.get(position));
        return arg1;
    }

    static class ViewHolder
    {
        TextView title;
        ImageView thumbnail;
        TextView category;
        TextView views;
        TextView duration;
        TextView id;
    }
}