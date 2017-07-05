package com.meri_sg.where_is_it;

import android.Manifest;
import android.app.AlertDialog;
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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.meri_sg.where_is_it.DB.Contract;
import com.meri_sg.where_is_it.DB.DbCommands;
import com.meri_sg.where_is_it.DB.SavedItemObj;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailsItemActivity extends AppCompatActivity {

    private EditText itemNameD, placeDescripD, newCommentsD;
    private ImageView itemImgD;
    private String itemname, imgUri, imgtime, placeDesc, alerton, alerttime, comments ;
    private double lat,lng ;
    private int itemID, pos;
    private Cursor itemDetail;
    private Toolbar toolbar;
    private Button change, cancel;
    private DbCommands commands;
    private boolean isPicture;
    private Uri mImageUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_item);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        itemImgD=(ImageView)findViewById(R.id.itemImgD);
        itemNameD=(EditText)findViewById(R.id.itemNameEtD);
        placeDescripD=(EditText)findViewById(R.id.placeEtD);
        newCommentsD=(EditText)findViewById(R.id.commentsEtD);

        change=(Button) findViewById(R.id.changeBtn);
        cancel=(Button) findViewById(R.id.cancelBtn);

        final Intent intent= getIntent();
        itemID = intent.getIntExtra("detailId",-1);

        commands=new DbCommands(this);

        //////////////////////
        if (itemID!=-1){
            itemDetail = commands.getItemDetail();
            if (itemDetail.getCount()>0){
                while (itemDetail.moveToNext()){
                    if (itemDetail.getInt(itemDetail.getColumnIndex(Contract.SavedItem.ID))==itemID){
                        pos = itemDetail.getPosition();
                        getDetails();
                    }
                }
            }
        }///////////////////


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (0==0){//to-do: check if somthing change
                    AlertDialog.Builder builder=new AlertDialog.Builder(DetailsItemActivity.this);
                    builder.setTitle(R.string.dont_save_changes);
                    builder.setPositiveButton(R.string.dont_save, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onBackPressed();
                        }
                    });
                    //handle No answer
                    builder.setNegativeButton(R.string.wate, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Toast.makeText(MainActivity.this, R.string.your_data_save, Toast.LENGTH_SHORT).show();
                        }
                    });
                    //show the alert "change picture" to the user
                    builder.show();
                }else{
                    onBackPressed();
                }


            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lat=0;
                lng=0;
                itemname = itemNameD.getText().toString();
                if (imgUri==null)
                    imgUri = "uri";
                imgtime = "1/5/15 4646";
                placeDesc = placeDescripD.getText().toString();
                alerton = "off";
                alerttime = "date";
                comments = newCommentsD.getText().toString();

                commands.updateItem(new SavedItemObj(itemname,imgUri,placeDesc,lat,lng,imgtime,alerton,alerttime,comments),itemID);
                Intent intent1=new Intent(DetailsItemActivity.this, MainActivity.class);
                intent1.putExtra("back",true);
                startActivity(intent1);
            }
        });



        itemImgD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPicture){
                    AlertDialog.Builder builder=new AlertDialog.Builder(DetailsItemActivity.this);
                    builder.setTitle(R.string.change_picture);
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
                    //show the alert "change picture" to the user
                    builder.show();
                }else {
                    // storage permishion check!
                    if (ActivityCompat.checkSelfPermission(DetailsItemActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(DetailsItemActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE} ,1);
                    }else
                        takePicture();

                }
            }
        });



    }



    public void getDetails (){
        itemDetail.moveToPosition(pos);

        imgUri=itemDetail.getString(itemDetail.getColumnIndex(Contract.SavedItem.IMG));
        itemname=itemDetail.getString(itemDetail.getColumnIndex(Contract.SavedItem.ITEMNAME));
        placeDesc=itemDetail.getString(itemDetail.getColumnIndex(Contract.SavedItem.PLACEDESCRIPTION));
        comments=itemDetail.getString(itemDetail.getColumnIndex(Contract.SavedItem.COMMENTS));
        if (imgUri.equals("uri")){
            isPicture=false;
        }else {
//            Toast.makeText(DetailsItemActivity.this, imgUri, Toast.LENGTH_SHORT).show();
            isPicture=true;
            Picasso.with(this).load(imgUri).resize(270,200).into(itemImgD);
        }

        itemNameD.setText(itemname);
        placeDescripD.setText(placeDesc);
        newCommentsD.setText(comments);
    }

    private void takePicture (){
        File pictureFileDir = new File(Environment.getExternalStorageDirectory(),"WhereItIs");
        pictureFileDir.mkdirs();
        String timeStamp=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File imgFile=new File(pictureFileDir,"img_"+timeStamp+".jpg");
        mImageUri= Uri.fromFile(imgFile);

        Intent imgIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imgIntent.putExtra(MediaStore.EXTRA_OUTPUT,mImageUri);
        startActivityForResult(imgIntent,42);

        isPicture=true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.getContentResolver().notifyChange(mImageUri,null);
        ContentResolver cr= this.getContentResolver();
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(cr, mImageUri);
            imgUri = mImageUri.toString();
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            Bitmap bMapScaled=Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
            itemImgD.setImageBitmap(bMapScaled);
        } catch (IOException e) {
//            Toast.makeText(this,R.string.no_new_picture, Toast.LENGTH_SHORT).show();
        }
    }


        //Inflate the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }//end of onCreateOptionsMenu

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.share){
            Intent intent=new Intent(android.content.Intent.ACTION_SEND);
//            intent.setType("text/plain");
            intent.setType("image/*");

            intent.putExtra(Intent.EXTRA_SUBJECT,"Here is what i whant:");
            intent.putExtra(Intent.EXTRA_TEXT," "+itemname+" "+placeDesc+" ");
            intent.putExtra(Intent.EXTRA_STREAM,Uri.parse(imgUri));
            startActivity(Intent.createChooser(intent,"how do you like to shere?"));
        }else if(item.getItemId()==R.id.clear){

            AlertDialog.Builder builder=new AlertDialog.Builder(DetailsItemActivity.this);
            builder.setTitle(R.string.delete);
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    commands.deleteOneItem(itemID);
                    Intent intent1=new Intent(DetailsItemActivity.this, MainActivity.class);
                    intent1.putExtra("back",true);
                    startActivity(intent1);
                }
            });
            //handle No answer
            builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Toast.makeText(MainActivity.this, R.string.your_data_save, Toast.LENGTH_SHORT).show();
                }
            });
            //show the alert "change picture" to the user
            builder.show();
        }
        return super.onOptionsItemSelected(item);
    }





}
