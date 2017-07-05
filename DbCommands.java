package com.meri_sg.where_is_it.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * Created by lenovo on 29-Nov-16.
 */

public class DbCommands {

    private Context context;
    private DbHelper helper;

    public DbCommands(Context context) {
        this.context = context;
    }

    //add place object to DB
    public void addNewItem(SavedItemObj savedItemObj) {

        helper = new DbHelper(context);
        ContentValues cv = new ContentValues();

        cv.put(Contract.SavedItem.ITEMNAME, savedItemObj.getItemname());
        cv.put(Contract.SavedItem.IMG, savedItemObj.getImg());
        cv.put(Contract.SavedItem.IMGTIME, savedItemObj.getImgtime());
        cv.put(Contract.SavedItem.PLACEDESCRIPTION, savedItemObj.getPlacedescription());
        cv.put(Contract.SavedItem.LAT, savedItemObj.getLat());
        cv.put(Contract.SavedItem.LNG, savedItemObj.getLng());
        cv.put(Contract.SavedItem.ALERTON, savedItemObj.getAlerton());
        cv.put(Contract.SavedItem.ALERTTIME, savedItemObj.getAlerttime());
        cv.put(Contract.SavedItem.COMMENTS, savedItemObj.getComments());

        helper.getWritableDatabase().insert(Contract.SavedItem.TABLE_NAME, null, cv);

        //update provider that something changed
        context.getContentResolver().notifyChange(Contract.SavedItem.CONTENT_URI, null);

        helper.close();

    }//end of addItem

    //add place object to DB
    public void updateItem(SavedItemObj savedItemObj, int id) {

        helper = new DbHelper(context);
        ContentValues cv = new ContentValues();

        cv.put(Contract.SavedItem.ITEMNAME, savedItemObj.getItemname());
        cv.put(Contract.SavedItem.IMG, savedItemObj.getImg());
        cv.put(Contract.SavedItem.IMGTIME, savedItemObj.getImgtime());
        cv.put(Contract.SavedItem.PLACEDESCRIPTION, savedItemObj.getPlacedescription());
        cv.put(Contract.SavedItem.LAT, savedItemObj.getLat());
        cv.put(Contract.SavedItem.LNG, savedItemObj.getLng());
        cv.put(Contract.SavedItem.ALERTON, savedItemObj.getAlerton());
        cv.put(Contract.SavedItem.ALERTTIME, savedItemObj.getAlerttime());
        cv.put(Contract.SavedItem.COMMENTS, savedItemObj.getComments());

        helper.getWritableDatabase().update(Contract.SavedItem.TABLE_NAME,cv ,Contract.SavedItem.ID+
                        "="+id,null );

        //update provider that something changed
        context.getContentResolver().notifyChange(Contract.SavedItem.CONTENT_URI, null);

        helper.close();

    }//end of addItem



    //Delete item from DB
    public void deleteOneItem(int id) {

        helper = new DbHelper(context);
        helper.getWritableDatabase().delete(Contract.SavedItem.TABLE_NAME,Contract.SavedItem.ID+
                "="+id ,null);
        //update provider that something changed
        context.getContentResolver().notifyChange(Contract.SavedItem.CONTENT_URI, null);

        helper.close();

    }//end of deleteOneItem


    //Order items by serch
    public Cursor serchItem (CharSequence sequence ) {

        helper = new DbHelper(context);
        Cursor myOrderCursor= helper.getReadableDatabase().rawQuery("SELECT * FROM "+Contract.SavedItem.TABLE_NAME+" WHERE "+
                Contract.SavedItem.ITEMNAME+ " LIKE '"+sequence+"%'", null);

       return myOrderCursor;

    }//end of serchItem


    public Cursor getItemDetail (){
        helper = new DbHelper(context);
        Cursor itemDetail = helper.getReadableDatabase().rawQuery("SELECT * FROM "+Contract.SavedItem.TABLE_NAME, null);

        return itemDetail;
    }


    public void addnewplace (String place){

        helper = new DbHelper(context);
        ContentValues cv = new ContentValues();
        cv.put(Contract.places.PLACENAME, place);
        helper.getWritableDatabase().insert(Contract.places.TABLE_NAME, null, cv);
        //update provider that something changed
        context.getContentResolver().notifyChange(Contract.places.CONTENT_URI, null);

        helper.close();
    }

    public Cursor getAllPlaes (){
        helper = new DbHelper(context);
        Cursor allPlaes = helper.getReadableDatabase().rawQuery("SELECT * FROM "+Contract.places.TABLE_NAME, null);
        return allPlaes;
    }

    public void addnewinside (String inside){
        helper= new DbHelper(context);
        ContentValues cv= new ContentValues();
        cv.put(Contract.inside.INSIDENAME, inside);
        helper.getWritableDatabase().insert(Contract.inside.TABLE_NAME, null, cv);
        //update provider that something changed
        context.getContentResolver().notifyChange(Contract.inside.CONTENT_URI, null);

        helper.close();
    }

    public Cursor getAllInside (){
        helper = new DbHelper(context);
        Cursor allInside = helper.getReadableDatabase().rawQuery("SELECT * FROM "+Contract.inside.TABLE_NAME, null);
        return allInside;
    }

}
