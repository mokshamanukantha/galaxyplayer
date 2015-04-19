package com.example.musicplayer.MusicPlayer;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.musicplayer.R;
import com.gc.materialdesign.views.ButtonRectangle;

/**
 * Created by mokshaDev on 4/5/2015.
 */
public class PlaylistsFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    private static final String TAG_ID="id";
    ListView listView;
    ArrayAdapter<String> adapter;
    String[] playlistID;
    ButtonRectangle cPlaylist;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist_layout,container,false);
        listView = (ListView) view.findViewById(R.id.play_list);
        cPlaylist = (ButtonRectangle) view.findViewById(R.id.createPlaylist);
        String[] proj = {"*"};
        String id = "";
        Uri tempPlaylistURI = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;
        Cursor playListCursor= getActivity().managedQuery(tempPlaylistURI, proj,id, null, null);
        if(playListCursor == null){
            System.out.println("Not having any Playlist on phone --------------");
        }
        System.gc();
        String playListName = null;
        String playID = null;
        String[] playlists = new String[playListCursor.getCount()];
        playlistID = new String[playListCursor.getCount()];

        for(int i = 0; i <playListCursor.getCount() ; i++)
        {
            playListCursor.moveToPosition(i);
            playListName = playListCursor.getString(playListCursor.getColumnIndex("name"));
            playID = playListCursor.getString(0);
            System.out.println("> " + i + "  : " + playListName );
            System.out.println("> " + i + "  : " + playID );
            playlists[i]=playListName;
            playlistID[i] = playID;
        }

        adapter = new ArrayAdapter<String>(getActivity(), R.layout.play_list_item, R.id.play_list_title, playlists);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        cPlaylist.setOnClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        long playlist_id = Long.parseLong(playlistID[position]);
        Intent intent = new Intent(getActivity(),PlaylistResultActivity.class);
        intent.putExtra(TAG_ID,playlist_id);
        startActivity(intent);

    }


    @Override
    public void onClick(View v) {

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setTitle("Title");
        alert.setMessage("New Playlist Name");

        final EditText input = new EditText(getActivity());
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                ContentResolver c= getActivity().getContentResolver();
                createPlaylist(c, value);
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        alert.show();
        adapter.notifyDataSetChanged();

    }

    public long createPlaylist(ContentResolver resolver, String name)
    {
        long id = getPlaylist(resolver, name);

        if (id == -1) {
            // We need to create a new playlist.
            ContentValues values = new ContentValues(1);
            values.put(MediaStore.Audio.Playlists.NAME, name);
            Uri uri = resolver.insert(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI, values);
            id = Long.parseLong(uri.getLastPathSegment());
        } else {
            // We are overwriting an existing playlist. Clear existing songs.
            Uri uri = MediaStore.Audio.Playlists.Members.getContentUri("external", id);
            resolver.delete(uri, null, null);
        }
        adapter.notifyDataSetChanged();
        return id;
    }

    public long getPlaylist(ContentResolver resolver, String name)
    {
        long id = -1;

        Cursor cursor = resolver.query(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Playlists._ID},
                MediaStore.Audio.Playlists.NAME + "=?",
                new String[]{name}, null);

        if (cursor != null) {
            if (cursor.moveToNext())
                id = cursor.getLong(0);
            cursor.close();
        }

        return id;
    }
}
