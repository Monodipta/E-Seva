package com.example.projectproto1.admin_catalouge.AdminFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projectproto1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminDashFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminDashFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminDashFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminDashFragment newInstance(String param1, String param2) {
        AdminDashFragment fragment = new AdminDashFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    String U_name;
    TextView name,phonenumber,department,email;

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
        View view=inflater.inflate(R.layout.admin_dash, container, false);

        name= view.findViewById(R.id.adname);
        phonenumber=view.findViewById(R.id.adphone);
        department=view.findViewById(R.id.addepartment);
        email=view.findViewById(R.id.ademail);

        DatabaseReference reference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://e-seva-737e3-default-rtdb.firebaseio.com/");
        reference.child("OFFICER").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                name.setText(snapshot.child(U_name).child("fullname").getValue(String.class));
                phonenumber.setText(snapshot.child(U_name).child("phoneno").getValue(String.class));
                department.setText(snapshot.child(U_name).child("department").getValue(String.class));
                email.setText(snapshot.child(U_name).child("email").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}