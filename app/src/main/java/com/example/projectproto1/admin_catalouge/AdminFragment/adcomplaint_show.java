package com.example.projectproto1.admin_catalouge.AdminFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.projectproto1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link adcomplaint_show#newInstance} factory method to
 * create an instance of this fragment.
 */
public class adcomplaint_show extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FloatingActionButton adback;
    private String adcomplaint,adrefid,adtaxno,adtitle,adddept;
    private Spinner status;
    private String Statusstr;
    private Button go;
    DatabaseReference reference=FirebaseDatabase.getInstance().getReferenceFromUrl("https://e-seva-737e3-default-rtdb.firebaseio.com/");

    public adcomplaint_show() {
        // Required empty public constructor
    }
    public adcomplaint_show(String adcomplaint, String adrefid,String adtaxno, String adtitle, String addept) {
        this.adcomplaint=adcomplaint;
        this.adrefid=adrefid;
        this.adtaxno=adtaxno;
        this.adtitle=adtitle;
        this.adddept=addept;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment adcomplaint_show.
     */
    // TODO: Rename and change types and number of parameters
    public static adcomplaint_show newInstance(String param1, String param2) {
        adcomplaint_show fragment = new adcomplaint_show();
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_adcomplaint_show, container, false);

        TextView titleholder=view.findViewById(R.id.adtitleshow);
        TextView refidholder=view.findViewById(R.id.adrefidshow);
        TextView compholder=view.findViewById(R.id.adcompshow);

        adback=view.findViewById(R.id.adback);
        go=view.findViewById(R.id.go);
        status=view.findViewById(R.id.statusdropdown);


        titleholder.setText(adtitle);
        refidholder.setText(adrefid);
        compholder.setText(adcomplaint);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Statusstr=status.getSelectedItem().toString();
                System.out.println(Statusstr);
                reference.child("COMPLAINTS").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        reference.child("COMPLAINTS").child(adddept).child(adrefid).child("status").setValue(Statusstr)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    Toast.makeText(getActivity(), R.string.status_updated, Toast.LENGTH_SHORT).show();
                                                }
                                                else {
                                                    Toast.makeText(getActivity(), R.string.status_update_failed, Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        adback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Back();
            }
        });

        return view;
    }

    private void Back(){


        AppCompatActivity activity=(AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.adframecontainer, new ComplaintListFragment()).addToBackStack(null).commit();

    }
}