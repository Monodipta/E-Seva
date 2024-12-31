package com.example.projectproto1.user_catalouge.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectproto1.R;
import com.example.projectproto1.user_catalouge.Fragments.rcv.compAdapter;
import com.example.projectproto1.user_catalouge.Fragments.rcv.model;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class complaint_list_Fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static String U_name;
    RecyclerView recview;
    compAdapter adapter;
    ProgressBar recviewPrgressbar;
    DatabaseReference reference=FirebaseDatabase.getInstance().getReferenceFromUrl("https://e-seva-737e3-default-rtdb.firebaseio.com/");


    private String mParam1;
    private String mParam2;

    public complaint_list_Fragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static complaint_list_Fragment newInstance(String param1, String param2) {
        complaint_list_Fragment fragment = new complaint_list_Fragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(getArguments()!=null){
            U_name=getArguments().getString("u_name");
        }
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_complaint_list_, container, false);

        recviewPrgressbar=view.findViewById(R.id.recview_progress);
        recview=(RecyclerView)view.findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));
        new NetworkTask().execute();

//        FirebaseRecyclerOptions<model> options =
//                new FirebaseRecyclerOptions.Builder<model>()
//                        .setQuery(reference.child("COMPLAINTSUSER").child(U_name), model.class)
//                        .build();
//        adapter=new compAdapter(options);
//        recview.setAdapter(adapter);
//        adapter.notifyDataSetChanged();

        return view;
    }

    private class NetworkTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
//            super.onPreExecute();
            recviewPrgressbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            FirebaseRecyclerOptions<model> options =
                    new FirebaseRecyclerOptions.Builder<model>()
                            .setQuery(reference.child("COMPLAINTSUSER").child(U_name), model.class)
                            .build();
            adapter=new compAdapter(options);

            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {

            recviewPrgressbar.setVisibility(View.GONE);

            recview.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            adapter.startListening();
        }
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        adapter.startListening();
//    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        adapter.startListening();
//    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}