package com.example.projectproto1.user_catalouge.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.projectproto1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Dash_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class Dash_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Dash_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Dash_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Dash_Fragment newInstance(String param1, String param2) {
        Dash_Fragment fragment = new Dash_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    String U_name;

    String datas;
    TextView name,phonenumber,address,postoffice,pincode,policestation,email;
    ProgressBar userDashProgressbar;
    LinearLayout userData;
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
        // Inflate the layout for this fragment
        if(getArguments()!=null){
            U_name=getArguments().getString("u_name");
        }
        System.out.println(U_name);
        View view=inflater.inflate(R.layout.fragment_dash_, container, false);

        userDashProgressbar= view.findViewById(R.id.user_dash_progressbar);
        userData=view.findViewById(R.id.user_data);
        name= view.findViewById(R.id.name);
        phonenumber=view.findViewById(R.id.phone);
        address=view.findViewById(R.id.address);
        postoffice=view.findViewById(R.id.postoffice);
        pincode=view.findViewById(R.id.pincode);
        policestation=view.findViewById(R.id.policestation);
        email=view.findViewById(R.id.email);

        new NetworkTask().execute();


//        DatabaseReference reference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://e-seva-737e3-default-rtdb.firebaseio.com/");
//        reference.child("USERS").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                //if(snapshot.hasChild(U_name)){
//
//                    name.setText(snapshot.child(U_name).child("fullname").getValue(String.class));
//                    phonenumber.setText(snapshot.child(U_name).child("phoneno").getValue(String.class));
//                    address.setText(snapshot.child(U_name).child("holdingno").getValue(String.class));
//                    postoffice.setText(snapshot.child(U_name).child("postoffice").getValue(String.class));
//                    pincode.setText(snapshot.child(U_name).child("pincode").getValue(String.class));
//                    policestation.setText(snapshot.child(U_name).child("policestation").getValue(String.class));
//                    email.setText(snapshot.child(U_name).child("email").getValue(String.class));
//                }
//            //}
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        return view;
    }

    private class NetworkTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {

            userDashProgressbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            DatabaseReference reference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://e-seva-737e3-default-rtdb.firebaseio.com/");
            reference.child("USERS").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //if(snapshot.hasChild(U_name)){

                    name.setText(snapshot.child(U_name).child("fullname").getValue(String.class));
                    phonenumber.setText(snapshot.child(U_name).child("phoneno").getValue(String.class));
                    address.setText(snapshot.child(U_name).child("holdingno").getValue(String.class));
                    postoffice.setText(snapshot.child(U_name).child("postoffice").getValue(String.class));
                    pincode.setText(snapshot.child(U_name).child("pincode").getValue(String.class));
                    policestation.setText(snapshot.child(U_name).child("policestation").getValue(String.class));
                    email.setText(snapshot.child(U_name).child("email").getValue(String.class));

                    userDashProgressbar.setVisibility(View.GONE);
                    userData.setVisibility(View.VISIBLE);
                }
                //}

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            return null;
        }
    }
}