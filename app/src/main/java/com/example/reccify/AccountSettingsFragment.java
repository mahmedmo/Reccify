package com.example.reccify;

import static com.example.reccify.CurrentUserFile.getCurrentUser;
import static com.example.reccify.GenreList.favouriteGenre;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.Nullable;


public class AccountSettingsFragment extends Fragment {
    TextView mUsername;
    TextView mFavGenre;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    //FRAGMENT SETUP BELOW

    public AccountSettingsFragment() {

    }

    public static AccountSettingsFragment newInstance(String param1, String param2) {
        AccountSettingsFragment fragment = new AccountSettingsFragment();
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
        mFavGenre = view.findViewById(R.id.favGenre);
        mUsername = view.findViewById(R.id.username);
        mFavGenre.setText(favouriteGenre().toString());
        mUsername.setText(getCurrentUser().getUsername());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_account, container, false);
    }
}