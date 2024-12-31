package com.example.projectproto1.user_catalouge;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectproto1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration_lay extends AppCompatActivity {

    EditText full_name,hld_no,post_ofc,pin_code,police_stn,ph_no,psswd,email,repsswd;

    String Fullname,Hldno,Postofc,Pincode,Policestn,Phno,Psswd,Email,Repsswd,Emailkey,uid;

    Button submit;

    private DatabaseReference reference=FirebaseDatabase.getInstance().getReferenceFromUrl("https://e-seva-737e3-default-rtdb.firebaseio.com/");

    private  FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_lay);
        init();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Psswd=psswd.getText().toString();
                Repsswd=repsswd.getText().toString();
                Email=email.getText().toString();

                //Emailkey=Email.replace(".","");

                if(!Repsswd.equals(Psswd)){
                    Toast.makeText(Registration_lay.this, R.string.password_didn_t_match, Toast.LENGTH_SHORT).show();
                }
                else{
                    if(!Email.isEmpty()){

                        auth.createUserWithEmailAndPassword(Email,Psswd)
                                        .addOnCompleteListener(Registration_lay.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful())
                                                {
                                                    FirebaseUser user=auth.getCurrentUser();
                                                    uid=user.getUid();
                                                    
                                                    Fullname=full_name.getText().toString();
                                                    Hldno=hld_no.getText().toString();
                                                    Postofc=post_ofc.getText().toString();
                                                    Pincode=pin_code.getText().toString();
                                                    Policestn=police_stn.getText().toString();
                                                    Phno=ph_no.getText().toString();
                                                    Psswd=psswd.getText().toString();
                                                    Repsswd=repsswd.getText().toString();
                                                    Email=email.getText().toString();
                                                    
                                                    reference.child("USERS").child(uid).child("fullname").setValue(Fullname);
                                                    reference.child("USERS").child(uid).child("holdingno").setValue(Hldno);
                                                    reference.child("USERS").child(uid).child("postoffice").setValue(Postofc);
                                                    reference.child("USERS").child(uid).child("pincode").setValue(Pincode);
                                                    reference.child("USERS").child(uid).child("policestation").setValue(Policestn);
                                                    reference.child("USERS").child(uid).child("phoneno").setValue(Phno);
                                                    reference.child("USERS").child(uid).child("password").setValue(Psswd);
                                                    reference.child("USERS").child(uid).child("email").setValue(Email);

                                                    Toast.makeText(Registration_lay.this, R.string.registration_successful, Toast.LENGTH_SHORT).show();
                                                    finish();
                                                }
                                                else 
                                                {
                                                    Toast.makeText(Registration_lay.this, R.string.registration_failed, Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                    }
                    else {
//                        Toast.makeText(Registration_lay.this, R.string.please_provide_an_email, Toast.LENGTH_SHORT).show();
                        email.setError("Required");
                    }
                    
                }
            }
        });
    }

    private void init(){
        
        full_name= (EditText) findViewById(R.id.fullname);
        hld_no= (EditText) findViewById(R.id.hldno);
        post_ofc= (EditText) findViewById(R.id.postofc);
        pin_code= (EditText) findViewById(R.id.pincode);
        police_stn= (EditText) findViewById(R.id.policestn);
        ph_no= (EditText) findViewById(R.id.phnno);
        psswd= (EditText) findViewById(R.id.entpsswd);
        email= (EditText) findViewById(R.id.email);
        repsswd= (EditText) findViewById(R.id.reentpsswd);

        submit= (Button) findViewById(R.id.button);
        auth=FirebaseAuth.getInstance();
    }

}