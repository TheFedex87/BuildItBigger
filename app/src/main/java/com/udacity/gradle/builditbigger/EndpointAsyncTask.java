package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.udacity.andjokeslilb.LibMainActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.jokesApi.JokesApi;
import com.udacity.gradle.builditbigger.MainActivityFragment;

import java.io.IOException;

/**
 * Created by feder on 01/05/2018.
 */

//This method as been taken from https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/77e9910911d5412e5efede5fa681ec105a0f02ad/HelloEndpoints#2-connecting-your-android-app-to-the-backend
//as suggested from project specifications
public class EndpointAsyncTask extends AsyncTask<Context, Void, String> {

    private static JokesApi myApiService = null;

    private Context context;

    private Fragment fragment;

    public EndpointAsyncTask(Fragment fragment){
        this.fragment = fragment;
    }

    @Override
    protected String doInBackground(Context... params) {
        if(myApiService == null) {  // Only do this once
            JokesApi.Builder builder = new JokesApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        context = params[0];

        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Intent intent = new Intent(context, LibMainActivity.class);
        intent.putExtra("JOKE", result);

        context.startActivity(intent);

        //Add this handler since after startActivity, loader disappears some instants before
        //new activity is started
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((MainActivityFragment)fragment).showContainer();
            }
        }, 500);

        if (((MainActivity)context).simpleIdlingResource != null) {
            ((MainActivity)context).simpleIdlingResource.setIdleState(true);
        }
    }
}
