package com.example.projectproto1.admin_catalouge.AdminFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.projectproto1.R;
import com.example.projectproto1.admin_catalouge.AdminFragment.adrcv.adcompAdapter;
import com.example.projectproto1.admin_catalouge.AdminFragment.adrcv.admodel;
import com.example.projectproto1.user_catalouge.Fragments.rcv.model;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ComplaintListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    static String Dept;
    adcompAdapter adadapter;
    RecyclerView adRecview;

    DatabaseReference reference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://e-seva-737e3-default-rtdb.firebaseio.com/");

    public ComplaintListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ComplaintListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ComplaintListFragment newInstance(String param1, String param2) {
        ComplaintListFragment fragment = new ComplaintListFragment();
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
            Dept=getArguments().getString("dept");
        }
        View view = inflater.inflate(R.layout.complaint_list_fragment, container, false);

        adRecview=(RecyclerView)view.findViewById(R.id.adrecview);
        adRecview.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<admodel> options =
                new FirebaseRecyclerOptions.Builder<admodel>()
                        .setQuery(reference.child("COMPLAINTS").child(Dept), admodel.class)
                        .build();
        adadapter=new adcompAdapter(options);
        adRecview.setAdapter(adadapter);



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adadapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adadapter.stopListening();
    }
}