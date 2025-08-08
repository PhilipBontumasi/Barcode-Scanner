package com.example.barcodescanner;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ItemListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemListFragment extends Fragment {

    FloatingActionButton fltScan;
    ItemListFragmentListener mListener;

    public ItemListFragment() {
        // Required empty public constructor
    }
    interface ItemListFragmentListener {
        void replaceWithItemDetailsFragment();
        void replaceWithScanItemFragment();
        void backFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (ItemListFragmentListener) context;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ItemListFragment.
     */
    public static ItemListFragment newInstance() {
        ItemListFragment fragment = new ItemListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        fltScan = view.findViewById(R.id.floatingScanIL);
        fltScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.replaceWithScanItemFragment();
            }
        });
        return view;
    }
}