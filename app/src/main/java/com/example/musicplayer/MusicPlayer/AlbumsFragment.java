package com.example.musicplayer.MusicPlayer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.etsy.android.grid.StaggeredGridView;
import com.example.musicplayer.R;
import com.example.musicplayer.Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by mokshaDev on 4/5/2015.
 */
public class AlbumsFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static final String TAG_ID="id";
    MusicPlayer mPlayer;
    private ArrayList<Song> songList;
    List<String> list;
    Utilities utilities;
    long[] albumSongs;
    AlbumGridAdapter albumGridAdapter;
    ArrayList<Album> albumList;
    String[] unique;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album_layout,container,false);
        StaggeredGridView albumView = (StaggeredGridView) view.findViewById(R.id.grid_view);
        mPlayer = (MusicPlayer) getActivity().getApplication();

        songList = new ArrayList<Song>();
        albumList = new ArrayList<Album>();

        utilities =new Utilities();
        if(!mPlayer.isListNull()) {
            songList = mPlayer.getSongLiss();
        }
        String[] album = new String[songList.size()];
        for(int i=0;i<songList.size();i++)
        {
            album[i]= String.valueOf(songList.get(i).getAlbumID());
        }

        unique = new HashSet<String>(Arrays.asList(album)).toArray(new String[0]);
        int[] nSongs = new int[unique.length];
        Bitmap[] nArt = new Bitmap[unique.length];
        String[] albumTitle = new String[unique.length];
        for(int i = 0;i<unique.length;i++)
        {
            albumSongs = Utilities.getSongListForAlbum(getActivity(), Long.parseLong(unique[i]));
            for(int r = 0;r<songList.size();r++)
            {
                if(songList.get(r).getID()==albumSongs[0])
                {
                    nArt[i] = songList.get(r).getImage();
                    albumTitle[i] = songList.get(r).getAlbumTitle();
                }
            }
            nSongs[i]=albumSongs.length;
        }

        for (int n = 0;n<unique.length;n++)
        {
            albumList.add(new Album(nArt[n],nSongs[n],albumTitle[n]));
            //Log.d("album",unique[n]+" title "+ albumTitle[n] +"  no songs "+nSongs[n]+"  bitmap " +nArt[n].toString());
        }
        albumGridAdapter = new AlbumGridAdapter(getActivity().getApplicationContext(),albumList);
        albumView.setAdapter(albumGridAdapter);
        albumView.setOnItemClickListener(this);
        return view;
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        long album_id = Long.parseLong(unique[position]);
        Intent intent = new Intent(getActivity(),AlbumDetailActivity.class);
        intent.putExtra(TAG_ID,album_id);
        startActivity(intent);
    }
}
