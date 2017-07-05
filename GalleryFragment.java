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
public class GalleryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private GalleryAdapter galleryAdapter;
    private RecyclerView galleryrecyclerView;

    public GalleryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_gallery, container, false);

        galleryrecyclerView = (RecyclerView) v.findViewById(R.id.recyclerGallery);
        galleryrecyclerView.setHasFixedSize(true);
        galleryrecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        galleryAdapter = new GalleryAdapter(null);
        galleryrecyclerView.setAdapter(galleryAdapter);
        getLoaderManager().initLoader(1,null,this);

        EditText serch= (EditText)v.findViewById(R.id.serchETG);
        serch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DbCommands commands=new DbCommands(getActivity());
                Cursor c=commands.serchItem(s);
                galleryAdapter.SwapCursor(c);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }


    public class GalleryAdapter extends RecyclerView.Adapter<GalleryViewHolder>{


        private Cursor galleryDataCursor;

        public GalleryAdapter(Cursor cursor){
            this.galleryDataCursor = cursor;
        }

        public Cursor SwapCursor (Cursor cursor){
            if (galleryDataCursor == cursor){
                return null;
            }
            Cursor oldCursor = galleryDataCursor;
            this.galleryDataCursor = cursor;
            if (cursor !=null){
                this.notifyDataSetChanged();
            }
            return oldCursor;
        }


        @Override
        public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View v = inflater.inflate(R.layout.single_gallery_item, parent, false);
            return new GalleryViewHolder(v);
        }

        @Override
        public void onBindViewHolder(GalleryViewHolder holder, final int position) {
            galleryDataCursor.moveToPosition(position);
            String itemNameFromData = galleryDataCursor.getString(galleryDataCursor.getColumnIndex(Contract.SavedItem.ITEMNAME));
            String imgFromData = galleryDataCursor.getString(galleryDataCursor.getColumnIndex(Contract.SavedItem.IMG));
            final int itemid = galleryDataCursor.getInt(galleryDataCursor.getColumnIndex(Contract.SavedItem.ID));

            if (imgFromData.equals("uri")){
                //holder.galleryItemImg.setImageResource(R.drawable.ic_action_add_a_photo);
            }else {
                Picasso.with(getActivity()).load(imgFromData).resize(270,200).into(holder.galleryItemImg);
            }
            holder.galleryItem.setText(itemNameFromData);

            holder.galleryItemImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(),DetailsItemActivity.class);
                    intent.putExtra("detailId",itemid);
                    startActivity(intent);
                }
            });

            holder.galleryItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(),DetailsItemActivity.class);
                    intent.putExtra("detailId",itemid);
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return (galleryDataCursor == null) ? 0 : galleryDataCursor.getCount();
        }
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder{

        private TextView galleryItem;
        private ImageView galleryItemImg;

        public GalleryViewHolder (View view){
            super (view);


            galleryItem = (TextView) view.findViewById(R.id.galleryItemNameTv);
            galleryItemImg = (ImageView) view.findViewById(R.id.galleryItemIMG);



        }

    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), Contract.SavedItem.CONTENT_URI,null, null ,null ,Contract.SavedItem.IMGTIME+" ASC");

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        galleryAdapter.SwapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
