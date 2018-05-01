package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.udacity.andjokeslilb.LibMainActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.EndpointAsyncTask;
import com.udacity.gradle.builditbigger.R;
import com.udacity.gradle.builditbigger.backend.jokesApi.JokesApi;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    @BindView(R.id.container)
    RelativeLayout containerLayout;

    @BindView(R.id.loader)
    ProgressBar loader;

    @BindView(R.id.joke)
    TextView joke;

    @BindView(R.id.say_a_joke)
    Button sayAJokeButton;

    private Context context;

    private MainActivityFragment thisFragment;

    public MainActivityFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, root);

        thisFragment = this;

        sayAJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loader.setVisibility(View.VISIBLE);
                containerLayout.setVisibility(View.GONE);
                new EndpointAsyncTask(thisFragment).execute(context);
            }
        });

        return root;
    }

    public void showContainer(){
        loader.setVisibility(View.GONE);
        containerLayout.setVisibility(View.VISIBLE);
    }


}


