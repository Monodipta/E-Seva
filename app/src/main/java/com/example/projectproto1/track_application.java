package com.example.projectproto1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectproto1.user_catalouge.Fragments.Dash_Fragment;
import com.example.projectproto1.user_catalouge.Fragments.complaint_list_Fragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class track_application extends AppCompatActivity {

    EditText refId;
    Spinner trackDropdown;
    Button track;
    ImageView showStatus;
    String refIdstr;
    String trackDropdownStr;
    private int fragNumber;
    private Dash_Fragment dash_fragment=new Dash_Fragment();
    private complaint_list_Fragment complaintListFragment=new complaint_list_Fragment();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    DatabaseReference reference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://e-seva-737e3-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_application);
        init();


        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refIdstr=refId.getText().toString();
                trackDropdownStr=trackDropdown.getSelectedItem().toString();

                if(refIdstr.isEmpty()||trackDropdownStr.equalsIgnoreCase("Department")){
                    refId.setError("Required");
                    if(trackDropdownStr.equalsIgnoreCase("department")){
                        Toast.makeText(track_application.this, "Please select the department properly", Toast.LENGTH_SHORT).show();
                    }
                }
                else {

                    reference.child("COMPLAINTS").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //showStatus.setVisibility(View.VISIBLE);
                            String sts=snapshot.child(trackDropdownStr).child(refIdstr).child("status").getValue(String.class);
                            System.out.println(sts);
                            if(sts.isEmpty()){
                                Toast.makeText(track_application.this, "Pleases fill the details properly!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                if(sts.equalsIgnoreCase("Not Viewed")){
                                    showStatus.setImageResource(R.drawable.not_viewed);
                                } else if (sts.equalsIgnoreCase("In Progreess")) {
                                    showStatus.setImageResource(R.drawable.in_progress);
                                } else if (sts.equalsIgnoreCase("Completed")) {
                                    showStatus.setImageResource(R.drawable.complete);
                                }
                                else if (sts.equalsIgnoreCase("Rejected")){
                                    showStatus.setImageResource(R.drawable.rejected);
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
    private void init(){
        refId= (EditText) findViewById(R.id.ref_id);
        trackDropdown= (Spinner) findViewById(R.id.track_dropdown);
        track= (Button) findViewById(R.id.track);
        showStatus= (ImageView) findViewById(R.id.show_status);

        fragNumber=getIntent().getIntExtra("fragNumber",0);
    }
}