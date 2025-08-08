package com.example.barcodescanner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreatorPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreatorPageFragment extends Fragment {



    public CreatorPageFragment() {
        // Required empty public constructor
    }

    interface CreatorPageFragmentListener {
        void replaceWithCreatorItemDetailsFragment();
        void replaceWithCreateItemFragment();
        void backFragment();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CreatorPageFragment.
     */
    public static CreatorPageFragment newInstance() {
        CreatorPageFragment fragment = new CreatorPageFragment();
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
        View view = inflater.inflate(R.layout.fragment_creator_page, container, false);

        return view;
    }
}