package com.meri_sg.where_is_it.DB;

import android.net.Uri;

/**
 * Created by lenovo on 29-Nov-16.
 */

public class Contract {

    public final static String AUTHORITY ="com.meri_sg.where_is_it.DB.saveditem";


    public static class SavedItem {

        public final static String TABLE_NAME = "saveditem";

        public final static Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static String ID = "_id";
        public static String ITEMNAME = "itemname";
        public static String IMG = "img";
        public static String PLACEDESCRIPTION = "placedescription";
        public static String LAT = "lat";
        public static String LNG = "lng";
        public static String IMGTIME = "imgtime";
        public static String ALERTON = "alerton";
        public static String ALERTTIME = "alerttime";
        public static String COMMENTS = "comments";
    }


    public static class places {

        public final static String TABLE_NAME = "places";

        public final static Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static String ID = "_id";
        public static String PLACENAME = "placename";

    }

    public static class inside {

        public final static String TABLE_NAME = "inside";

        public final static Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static String ID = "_id";
        public static String INSIDENAME = "insidename";

    }


}
