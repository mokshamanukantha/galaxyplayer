package com.example.musicplayer;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by mokshaDev on 4/15/2015.
 */
public class QueryTask {
    public Uri uri;
    public final String[] projection;
    public final String selection;
    public final String[] selectionArgs;
    public String sortOrder;



    public int mode;


    public int type;


    public long data;


    public QueryTask(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        this.uri = uri;
        this.projection = projection;
        this.selection = selection;
        this.selectionArgs = selectionArgs;
        this.sortOrder = sortOrder;
    }


    public Cursor runQuery(ContentResolver resolver)
    {
        return resolver.query(uri, projection, selection, selectionArgs, sortOrder);
    }
}
