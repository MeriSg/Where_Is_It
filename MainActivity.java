package com.meri_sg.where_is_it;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.meri_sg.where_is_it.Fragments.AddItemFragment;
import com.meri_sg.where_is_it.Fragments.FragmentListener;
import com.meri_sg.where_is_it.Fragments.GalleryFragment;
import com.meri_sg.where_is_it.Fragments.ListItemsFragment;

public class MainActivity extends AppCompatActivity implements FragmentListener, NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private Boolean back=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.drawer_open, R.string.drawer_close);

        mDrawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        FragmentManager manager = getFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        AddItemFragment addItemFragment = new AddItemFragment();
        ListItemsFragment listItemsFragment = new ListItemsFragment();

        Intent intent=getIntent();
        back = intent.getBooleanExtra("back",false);

        if (savedInstanceState != null || back ) {
            back=false;
           changeFragment("list");
        } else {
            ft.add(R.id.ContanerMain, addItemFragment, "AddItemFragment").commit();
        }


    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case R.id.item_add_new:
                changeFragment("additem");
                break;

            case R.id.item_list:
                changeFragment("list");
                break;

            case R.id.item_gallery:
                changeFragment("gallery");
                break;

            case R.id.item_reminders:
                changeFragment("reminders");
                break;
        }


        mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }


    //Inflate the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.share).setVisible(false);
        menu.findItem(R.id.clear).setVisible(false);
        return true;
    }//end of onCreateOptionsMenu

    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void changeFragment(String whereTo) {

        FragmentManager manager = getFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ListItemsFragment listItemsFragment = new ListItemsFragment();
        AddItemFragment addItemFragment = new AddItemFragment();
        GalleryFragment galleryFragment = new GalleryFragment();

        if (whereTo.equals("additem")) {
            ft.replace(R.id.ContanerMain, addItemFragment, "AddItemFragment").commit();
        }else if (whereTo.equals("list")) {
            ft.replace(R.id.ContanerMain, listItemsFragment, "listItemsFragment").commit();
        }else if (whereTo.equals("gallery")) {
            ft.replace(R.id.ContanerMain, galleryFragment, "galleryFragment").commit();
        }else if (whereTo.equals("reminders")) {
//            ft.replace(R.id.ContanerMain, remindersFragment, "remindersFragment").commit();
        }



    }
}
