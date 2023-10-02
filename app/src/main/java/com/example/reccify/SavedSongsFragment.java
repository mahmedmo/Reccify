package com.example.reccify;

import static com.example.reccify.MainActivity.getMainContext;
import static com.example.reccify.MainActivity.likedSongs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;


public class SavedSongsFragment extends Fragment {
    ArrayList<SavedSongsModel> savedSongsModel = new ArrayList<SavedSongsModel>(0);

    public void setUpSavedSongsModel() {

        for (String s : likedSongs) {
            if(!s.equals(" ")) {
                savedSongsModel.add(new SavedSongsModel(s, R.drawable.ic_listen));
            }
        }
    }

    //FRAGMENT SETUP BELOW

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public SavedSongsFragment() {

    }


    public static SavedSongsFragment newInstance(String param1, String param2) {
        SavedSongsFragment fragment = new SavedSongsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        RecyclerView recyclerView = view.findViewById(R.id.recycleView);
        setUpSavedSongsModel();
        SongRecyclerViewAdapter adapter = new SongRecyclerViewAdapter(getMainContext(), savedSongsModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getMainContext()));


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_saved, container, false);
    }
}