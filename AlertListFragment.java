package com.meri_sg.where_is_it.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meri_sg.where_is_it.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlertListFragment extends Fragment {


    public AlertListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alert_list, container, false);
    }

}
