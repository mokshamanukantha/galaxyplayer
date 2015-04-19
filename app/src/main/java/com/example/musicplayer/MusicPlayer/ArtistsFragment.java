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
import android.widget.ListView;

import com.example.musicplayer.R;
import com.example.musicplayer.Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by mokshaDev on 4/5/2015.
 */
public class ArtistsFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static final String TAG_ID="id";
    private ArrayList<Artist> artistList;
    private ListView artistView;
    MusicPlayer mPlayer;
    private ArrayList<Song> songList;
    List<String> list;
    String[] unique;
    Utilities utilities;
    long[] artistSongs;
    ArrayList<Artist> artistlist;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artis_layout,container,false);
        artistView = (ListView) view.findViewById(R.id.song_list);
        artistList = new ArrayList<Artist>();
        songList = new ArrayList<Song>();
        artistlist = new ArrayList<Artist>();
        utilities =new Utilities();
        mPlayer = (MusicPlayer) getActivity().getApplication();
        if(!mPlayer.isListNull()) {
            songList = mPlayer.getSongLiss();
        }
        String[] artist = new String[songList.size()];
        for(int i=0;i<songList.size();i++)
        {
            artist[i]= String.valueOf(songList.get(i).getArtistID());
        }

        unique = new HashSet<String>(Arrays.asList(artist)).toArray(new String[0]);

        int[] nSongs = new int[unique.length];
        Bitmap[] nArt = new Bitmap[unique.length];
        String[] albumTitle = new String[unique.length];
        String[] artistN = new String[unique.length];
        for(int i = 0;i<unique.length;i++)
        {
            artistSongs = Utilities.getSongListForArtist(getActivity(), Long.parseLong(unique[i]));
            for(int r = 0;r<songList.size();r++)
            {
                if(songList.get(r).getID()== artistSongs[0])
                {
                    nArt[i] = songList.get(r).getImage();
                    albumTitle[i] = songList.get(r).getAlbumTitle();
                    artistN[i] = songList.get(r).getArtist();
                }
            }
            nSongs[i]= artistSongs.length;
        }
        for (int n = 0;n<unique.length;n++)
        {
            artistlist.add(new Artist(albumTitle[n], artistN[n], nArt[n], nSongs[n]));
//            Log.d("album", unique[n] + " Artist " + artistN[n] + " title " + albumTitle[n] + "  no songs " + nSongs[n] + "  bitmap " + nArt[n].toString());
        }
        ArtistAdapter songAdt = new ArtistAdapter(getActivity().getApplicationContext(), artistlist);
        artistView.setAdapter(songAdt);
        artistView.setOnItemClickListener(this);
        return view;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        long artist_id = Long.parseLong(unique[position]);
        Intent intent = new Intent(getActivity(),ArtistDetailActivty.class);
        intent.putExtra(TAG_ID,artist_id);
        startActivity(intent);
    }
}
