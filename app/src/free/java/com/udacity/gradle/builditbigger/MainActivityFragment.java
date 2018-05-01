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
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
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

    @BindView(R.id.adView)
    AdView mAdView;

    private Context context;

    private MainActivityFragment thisFragment;

    private InterstitialAd mInterstitialAd;

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

        //AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        thisFragment = this;

        sayAJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*JokesProvider jokesProvider = new JokesProvider();

                Intent intent = new Intent(context, LibMainActivity.class);
                intent.putExtra("JOKE", jokesProvider.getJoke());

                startActivity(intent);*/
                loader.setVisibility(View.VISIBLE);
                containerLayout.setVisibility(View.GONE);
                if (mInterstitialAd.isLoaded())
                    mInterstitialAd.show();

                if (((MainActivity)context).simpleIdlingResource != null) {
                    ((MainActivity)context).simpleIdlingResource.setIdleState(false);
                }

                //Delay task call in order to show an Interstitial ad
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new EndpointAsyncTask(thisFragment).execute(context);
                    }
                }, 3000);

            }
        });

        //((MainActivity)context).getIdlingResource();

        return root;
    }


    public void showContainer(){
        loader.setVisibility(View.GONE);
        containerLayout.setVisibility(View.VISIBLE);
    }


}


