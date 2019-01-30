package com.example.reza66.criminalintentr;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reza66.criminalintentr.models.Crime;
import com.example.reza66.criminalintentr.models.CrimeLab;

import java.util.List;

import static android.os.Build.VERSION_CODES.N;


/**
 * A simple {@link Fragment} subclass.
 */
public class CrimeListFragment extends Fragment {

    public static final String SUBTITLE_VISIBLE = "subtitleVisible";

    private RecyclerView mRecyclerView;
    private CrimeAdapter mCrimeAdapter;
    private boolean mIsSubtitleVisible;
    private int posi;



    public CrimeListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null)
            mIsSubtitleVisible=savedInstanceState.getBoolean(SUBTITLE_VISIBLE);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_crime_list, container, false);

        mRecyclerView=view.findViewById(R.id.crimes_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return  view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
        updateSubtitle();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list,menu);

        MenuItem item=menu.findItem(R.id.show_subtitle);
        item.setTitle(mIsSubtitleVisible ? R.string.hide_subtitle :R.string.show_subtitle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()){
           case R.id.new_crime:
               Crime crime=new Crime();
               CrimeLab.getInstance(getActivity()).addCrime(crime);
               Intent intent=CrimePagerActivity.newIntent(getActivity(),crime.getId());
               startActivity(intent);
               return true;
           case R.id.show_subtitle:
               mIsSubtitleVisible=!mIsSubtitleVisible;
               getActivity().invalidateOptionsMenu();
               updateSubtitle();
               return true;
           default:
               return super.onOptionsItemSelected(item);
       }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(SUBTITLE_VISIBLE,mIsSubtitleVisible);
    }

    private void updateSubtitle() {
        int crimeCount= CrimeLab.getInstance(getActivity()).getCrimes().size();
//        String subtitle=getString(R.string.subtitle_format,crimeCount);

        String subTitle =
                getResources().getQuantityString(R.plurals.subtitle_plurals, crimeCount, crimeCount);

        if (!mIsSubtitleVisible)
            subTitle=null;
        AppCompatActivity activity= (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subTitle);
    }

    private void updateUI() {
        CrimeLab crimeLab=CrimeLab.getInstance(getActivity());
        List<Crime> crimes=crimeLab.getCrimes();
        if(mCrimeAdapter==null){
           mCrimeAdapter=new CrimeAdapter(crimes);
            mRecyclerView.setAdapter(mCrimeAdapter);
        } else {
            mCrimeAdapter.setCrimes(crimes);
            mCrimeAdapter.notifyDataSetChanged();
            //mCrimeAdapter.notifyItemChanged(posi);
        }
    }

    private class CrimeHolder extends RecyclerView.ViewHolder{
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImageView;

        private Crime mCrime;

        public CrimeHolder(@NonNull final View itemView) {
            super(itemView);
            mTitleTextView=itemView.findViewById(R.id.list_item_crime_title);
            mDateTextView = itemView.findViewById(R.id.list_item_crime_date);
            mSolvedImageView = itemView.findViewById((R.id.crime_solved));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(getActivity(),mCrime.getTitle()+"cliked",Toast.LENGTH_LONG).show();
                    posi=CrimeHolder.this.getAdapterPosition();
                    Intent intent=CrimePagerActivity.newIntent(getActivity(),mCrime.getId());
                    startActivity(intent);
                }
            });
        }
        public void bind(Crime crime){
            mCrime=crime;
            mTitleTextView.setText(crime.getTitle());
            mDateTextView.setText(crime.getDate().toString());
            mSolvedImageView.setVisibility(crime.isSolved()==true ? View.VISIBLE:View.GONE);
        }
    }
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{
        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        public void setCrimes(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @NonNull
        @Override
        public CrimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater= LayoutInflater.from(getActivity());
            View view=inflater.inflate(R.layout.list_item_crime,parent,false);
            CrimeHolder crimeHolder=new CrimeHolder(view);

            return crimeHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull CrimeHolder holder, int position) {
            Crime crime=mCrimes.get(position);
            holder.bind(crime);

        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }

}
