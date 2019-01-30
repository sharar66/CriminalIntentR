package com.example.reza66.criminalintentr;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.NoCopySpan;

/**
 * Created by www.NooR26.com on 12/17/2018.
 */

public abstract  class SingleFragmentActivity extends AppCompatActivity{
    public abstract Fragment createFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fragmentManager =getSupportFragmentManager();

        if (fragmentManager.findFragmentById(R.id.fragment_container)==null)
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, createFragment())
                    .commit();
    }


}
