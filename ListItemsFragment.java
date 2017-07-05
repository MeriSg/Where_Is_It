package com.meri_sg.where_is_it.Fragments;


import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.meri_sg.where_is_it.DB.Contract;
import com.meri_sg.where_is_it.DB.DbCommands;
import com.meri_sg.where_is_it.DetailsItemActivity;
import com.meri_sg.where_is_it.R;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListItemsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private MyAdapter adapter;
    private RecyclerView recyclerView;

    public ListItemsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_items, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        adapter = new MyAdapter(null);
        recyclerView.setAdapter(adapter);
        getLoaderManager().initLoader(1,null,this);

        EditText serch= (EditText)v.findViewById(R.id.serchET);
        serch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DbCommands commands=new DbCommands(getActivity());
                Cursor c=commands.serchItem(s);
                adapter.swapCursor(c);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }


    // 1> Create our Adapter class ==> generic type is a ViewHolder, a wrapper for each item
    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

        // 8> Create the data structure
        private Cursor dataCursor;

        // 9> create constructor that gets a new ArrayList
        public MyAdapter(Cursor cursor) {
            this.dataCursor = cursor;
        }

        public Cursor swapCursor(Cursor cursor) {
            if (dataCursor == cursor) {
                return null;
            }
            Cursor oldCursor = dataCursor;
            this.dataCursor = cursor;
            if (cursor != null) {
                this.notifyDataSetChanged();
            }
            return oldCursor;
        }

        // 11> what to do when creating a new MyHolder - inflate the item layout and pass it to a new holder
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View v = inflater.inflate(R.layout.single_list_item, parent, false);
            return new MyViewHolder(v);
        }

        // 12> how to bind(show) the data for each item in the list
        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {

            dataCursor.moveToPosition(position);
            String namefromData = dataCursor.getString(dataCursor.getColumnIndex(Contract.SavedItem.ITEMNAME));
            String imgFromData = dataCursor.getString(dataCursor.getColumnIndex(Contract.SavedItem.IMG));
            String placeDesFromData = dataCursor.getString(dataCursor.getColumnIndex(Contract.SavedItem.PLACEDESCRIPTION));

            final int itemid = dataCursor.getInt(dataCursor.getColumnIndex(Contract.SavedItem.ID));

            if (imgFromData.equals("uri")){
                holder.itemImg.setImageResource(R.drawable.ic_action_add_a_photo);
            }else {
                Picasso.with(getActivity()).load(imgFromData).resize(270,200).into(holder.itemImg);
            }
            holder.itmName.setText(namefromData);
            holder.placeDes.setText(placeDesFromData);

            holder.itmName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(),DetailsItemActivity.class);
                    intent.putExtra("detailId",itemid);
                    startActivity(intent);
                }
            });

            holder.itemImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(),DetailsItemActivity.class);
                    intent.putExtra("detailId",itemid);
                    startActivity(intent);
                }
            });

            holder.placeDes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(),DetailsItemActivity.class);
                    intent.putExtra("detailId",itemid);
                    startActivity(intent);
                }
            });


        }

        // 10> return the size of the ArrayList
        @Override
        public int getItemCount() {

          return (dataCursor == null) ? 0 : dataCursor.getCount();
        }

        // 2> Create the ViewHolder class - and the layout resource file
        public class MyViewHolder extends RecyclerView.ViewHolder{

            // 5> add the view variables
            private TextView itmName, placeDes;
            private ImageView itemImg;

            // 3> Add constructor
            public MyViewHolder(View itemView) {
                super(itemView);

                // 6> find the views and connect to the variables
                itmName = (TextView) itemView.findViewById(R.id.itemNameTv);
                itemImg = (ImageView) itemView.findViewById(R.id.itemIMGList);
                placeDes = (TextView) itemView.findViewById(R.id.placedescTv);


            }


        }
    }/////


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), Contract.SavedItem.CONTENT_URI,null, null ,null ,Contract.SavedItem.ITEMNAME+" ASC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//        data.setNotificationUri(getActivity().getContentResolver(),Contract.SavedItem.CONTENT_URI);
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }


}
