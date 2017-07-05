package com.meri_sg.where_is_it.Fragments;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.meri_sg.where_is_it.DB.Contract;
import com.meri_sg.where_is_it.DB.DbCommands;
import com.meri_sg.where_is_it.DB.DbHelper;
import com.meri_sg.where_is_it.DB.SavedItemObj;
import com.meri_sg.where_is_it.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddItemFragment extends Fragment {

    private EditText itemName, placeDescrip, newComments;
    private ImageView itemImg;
    private boolean isPicture;
    private Button addb;
    private String itemname, imgUri, imgtime, placedescription, alerton, alerttime, comments ;
    private double lat,lng ;
    public FragmentListener myActivity;
    private Uri mImageUri;
    private Dialog placesDialog;
    private DbCommands commands;


    //attach activity to the fragment
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            myActivity = (FragmentListener) activity;
        } catch (ClassCastException e) {
            Log.d("err", "activity must implement FragmentListener");
        }
    }//end of onAttach

    public AddItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_add_item, container, false);

        commands = new DbCommands(getActivity());


        itemName = (EditText)v.findViewById(R.id.itemNameET);
        placeDescrip = (EditText)v.findViewById(R.id.placeDescripET);
        newComments = (EditText)v.findViewById(R.id.commentsET);
        itemImg = (ImageView) v.findViewById(R.id.itemIMG);
        addb = (Button)v.findViewById(R.id.addBtn);



        //dialog....
        placesDialog=new Dialog(getActivity());
        placesDialog.setContentView(R.layout.places_dialog);
        setPlacesDialog ();

        placeDescrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placesDialog.show();
            }
        });


        //new item picture
        itemImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPicture){
                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                    builder.setTitle(R.string.take_anuther_picture);
                    builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            takePicture();
                        }
                    });
                    //handle No answer
                    builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Toast.makeText(MainActivity.this, R.string.your_data_save, Toast.LENGTH_SHORT).show();
                        }
                    });
                    //show the alert to the user
                    builder.show();
                    //alert take anuther picture?
                }else {
                    // storage permishion check!
                    if (ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE} ,1);
                    }else
                        takePicture();

                }
            }
        });


        addb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lat=0;
                lng=0;
                itemname = itemName.getText().toString();
                if (imgUri==null)
                    imgUri = "uri";
                imgtime = "1/5/15 4646";
                placedescription = placeDescrip.getText().toString();
                alerton = "off";
                alerttime = "date";
                comments = newComments.getText().toString();

                commands.addNewItem(new SavedItemObj(itemname,imgUri,placedescription,lat,lng,imgtime,alerton,alerttime,comments));
                myActivity.changeFragment("list");
            }
        });


        return v;
    }


    ListView roomList, insideList;
    SimpleCursorAdapter adapteri, adapterp;
    EditText roomEt, insideEt;

    public void setPlacesDialog (){

        roomList=(ListView)placesDialog.findViewById(R.id.roomListLV);
        insideList=(ListView)placesDialog.findViewById(R.id.insideListLV);

        DbHelper helper = new DbHelper(getActivity());

        Cursor cursorp= helper.getReadableDatabase().rawQuery("SELECT *"+"FROM "+ Contract.places.TABLE_NAME
                +" WHERE "+Contract.places.PLACENAME+"='kitchen'",null);
        if (cursorp.getCount()==0) {
            commands.addnewplace("kitchen");
            commands.addnewplace("Living room");
            commands.addnewplace("Bedroom");
            commands.addnewplace("Bathroom");
            commands.addnewplace("Washroom");
            commands.addnewplace("Office");
            commands.addnewplace("Car");
        }

        Cursor cursor1=commands.getAllPlaes();

        adapterp = new SimpleCursorAdapter(getActivity(),android.R.layout.simple_list_item_1,cursor1,
                new String[] {Contract.places.PLACENAME},new int[] { android.R.id.text1 });

        roomList.setAdapter(adapterp);

        roomEt=(EditText)placesDialog.findViewById(R.id.roomET);
//        roomEt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });




        Cursor cursori= helper.getReadableDatabase().rawQuery("SELECT *"+"FROM "+ Contract.inside.TABLE_NAME
                +" WHERE "+Contract.inside.INSIDENAME+"='Box'",null);
        if (cursori.getCount()==0){
            commands.addnewinside("Box");
            commands.addnewinside("Drawer");
            commands.addnewinside("Locker");
            commands.addnewinside("Closet");
            commands.addnewinside("Shelf");
            commands.addnewinside("Cell");
        }

        Cursor cursor2=commands.getAllInside();

            adapteri = new SimpleCursorAdapter(getActivity(),android.R.layout.simple_list_item_1,
                cursor2,new String[] {Contract.inside.INSIDENAME},
                new int[] { android.R.id.text1 });


        insideList.setAdapter(adapteri);

        insideEt=(EditText)placesDialog.findViewById(R.id.insideET);
//        insideEt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });



        roomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor=(Cursor) adapterp.getItem(position);
                roomEt.setText(cursor.getString(cursor.getColumnIndex(Contract.places.PLACENAME)));
            }
        });

        insideList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor=(Cursor) adapteri.getItem(position);
                insideEt.setText(cursor.getString(cursor.getColumnIndex(Contract.inside.INSIDENAME)));
            }
        });


        //add new/saved places
        Button addRoom= (Button)placesDialog.findViewById(R.id.addRoomBtn);
        addRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DbHelper helper = new DbHelper(getActivity());

                String room= roomEt.getText().toString();

                Cursor cursor1= helper.getReadableDatabase().rawQuery("SELECT *"+"FROM "+ Contract.places.TABLE_NAME
                        +" WHERE "+Contract.places.PLACENAME+"='"+room+"'",null)  ;
                if (cursor1.getCount()==0){
                    commands.addnewplace(room);
                }

                String inside= insideEt.getText().toString();
                Cursor cursor2= helper.getReadableDatabase().rawQuery("SELECT *"+"FROM "+ Contract.inside.TABLE_NAME
                +" WHERE "+Contract.inside.INSIDENAME+"='"+inside+"'",null)  ;
                if (cursor2.getCount()==0){
                    commands.addnewinside(inside);
                }

                placeDescrip.setText(roomEt.getText()+" in the "+ insideEt.getText());
                placesDialog.dismiss();
            }
        });
    }




    private void takePicture (){
        File pictureFileDir = new File(Environment.getExternalStorageDirectory(),"WhereItIs");
        pictureFileDir.mkdirs();
        String timeStamp=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File imgFile=new File(pictureFileDir,"img_"+timeStamp+".jpg");
        mImageUri=Uri.fromFile(imgFile);

        Intent imgIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imgIntent.putExtra(MediaStore.EXTRA_OUTPUT,mImageUri);
        startActivityForResult(imgIntent,42);

        isPicture=true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getActivity().getContentResolver().notifyChange(mImageUri,null);
        ContentResolver cr= getActivity().getContentResolver();
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(cr, mImageUri);
            imgUri = mImageUri.toString();
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            Bitmap bMapScaled=Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
            itemImg.setImageBitmap(bMapScaled);
        } catch (IOException e) {
//            Toast.makeText(this,R.string.no_new_picture, Toast.LENGTH_SHORT).show();
        }
    }


}
