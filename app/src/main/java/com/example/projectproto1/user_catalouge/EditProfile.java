package com.example.projectproto1.user_catalouge;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectproto1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfile extends AppCompatActivity {

    EditText fullNameEdit,phoneNoEdit,hldNoEdit,postOfcEdit,pinCodeEdit,policeStnEdit;
    Button editProfileBtn;
    String uName,fullNameStr,phoneNoStr,hldNoStr,postOfcStr,pinCodeStr,policeStnStr;
    DatabaseReference reference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://e-seva-737e3-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        init();
        stringMake();

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //takeInput();
                reference.child("USERS").child(uName).child("fullname").setValue(fullNameEdit.getText().toString());
                reference.child("USERS").child(uName).child("phoneno").setValue(phoneNoEdit.getText().toString());
                reference.child("USERS").child(uName).child("holdingno").setValue(hldNoEdit.getText().toString());
                reference.child("USERS").child(uName).child("postoffice").setValue(postOfcEdit.getText().toString());
                reference.child("USERS").child(uName).child("pincode").setValue(pinCodeEdit.getText().toString());
                reference.child("USERS").child(uName).child("policestation").setValue(policeStnEdit.getText().toString());

                Toast.makeText(EditProfile.this, R.string.profile_edit_successful, Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getApplicationContext(), user_cat_lg.class));
            }
        });
    }
    private void init(){
        fullNameEdit= (EditText) findViewById(R.id.fullname_edit);
        phoneNoEdit= (EditText) findViewById(R.id.phoneno_edit);
        hldNoEdit= (EditText) findViewById(R.id.hldno_edit);
        postOfcEdit= (EditText) findViewById(R.id.postofc_edit);
        pinCodeEdit= (EditText) findViewById(R.id.pincode_edit);
        policeStnEdit= (EditText) findViewById(R.id.policestn_edit);
        editProfileBtn= (Button) findViewById(R.id.edit_button);

        uName=getIntent().getStringExtra("uname");
    }
    private void stringMake(){
        reference.child("USERS").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fullNameStr=snapshot.child(uName).child("fullname").getValue(String.class);
                phoneNoStr=snapshot.child(uName).child("phoneno").getValue(String.class);
                hldNoStr=snapshot.child(uName).child("holdingno").getValue(String.class);
                postOfcStr=snapshot.child(uName).child("postoffice").getValue(String.class);
                pinCodeStr=snapshot.child(uName).child("pincode").getValue(String.class);
                policeStnStr=snapshot.child(uName).child("policestation").getValue(String.class);

                settext();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void settext(){
        fullNameEdit.setText(fullNameStr);
        phoneNoEdit.setText(phoneNoStr);
        hldNoEdit.setText(hldNoStr);
        postOfcEdit.setText(postOfcStr);
        pinCodeEdit.setText(pinCodeStr);
        policeStnEdit.setText(policeStnStr);
    }
}