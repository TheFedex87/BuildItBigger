package com.example.udacity.andjokeslilb;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;

/**
 * Created by feder on 28/04/2018.
 */

public class LibJokeFragment extends Fragment {
    View root;

    public LibJokeFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_lib_joke, container, false);

        return root ;
    }

    public void setJoke(String joke){
        ((TextView)root.findViewById(R.id.joke)).setText(joke);
    }
}
