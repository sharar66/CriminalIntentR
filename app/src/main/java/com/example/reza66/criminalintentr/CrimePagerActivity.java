package com.example.reza66.criminalintentr;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.reza66.criminalintentr.models.Crime;
import com.example.reza66.criminalintentr.models.CrimeLab;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity {

    private static final String EXTRA_CRIME_ID = "com.example.reza66.criminalintentr.crime_Id";
    private static final String TAG = "CrimePagerActivity" ;
    private ViewPager mViewPager;
    private List<Crime> mCrimes;

    public static Intent newIntent(Context context,UUID crimeId){
        Intent intent=new Intent(context,CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID,crimeId);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID currentCrimeId= (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        mCrimes= CrimeLab.getInstance(this).getCrimes();

        mViewPager=findViewById(R.id.crime_view_pager);
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Log.d(TAG,"fragment: "+position);
                UUID crimeId=mCrimes.get(position).getId();
                return  CrimeDetailFragment.newInstance(crimeId);
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });
//        int index=CrimeLab.getInstance(this).getIndexOfCrime(currentCrimeId);
//        if(index<0)
//            index=0;
        //mViewPager.setCurrentItem(index);
        //mViewPager.setOffscreenPageLimit(5);
        for(int i =0 ; i < mCrimes.size(); i++){
            if(mCrimes.get(i).getId().equals(currentCrimeId)){
                mViewPager.setCurrentItem(i);
            }
        }
    }
}
