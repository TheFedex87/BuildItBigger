package com.example.udacity.andjokeslilb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LibMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lib_main);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("JOKE")){
            String joke = intent.getStringExtra("JOKE");

            ((LibJokeFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_joke)).setJoke(joke);
        }
    }
}
