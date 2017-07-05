package com.meri_sg.where_is_it.DB;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.util.List;

/**
 * Created by lenovo on 29-Nov-16.
 */

public class ItemsProvider extends ContentProvider {


    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        DbHelper helper=new DbHelper(getContext());
        Cursor c=helper.getReadableDatabase().query(getTableName(uri),projection, selection,selectionArgs,null,null,sortOrder);
        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    protected String getTableName(Uri uri) {
        List<String> pathSegments = uri.getPathSegments();
        return pathSegments.get(0);
    }
}
