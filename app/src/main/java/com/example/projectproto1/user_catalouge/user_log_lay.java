package com.example.projectproto1.user_catalouge;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectproto1.MainActivity;
import com.example.projectproto1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class user_log_lay extends AppCompatActivity {

    TextView reg_button,forgotPasswordBtn;
    EditText u_name,u_pass;
    String U_name,U_passwd;
    FirebaseAuth auth;

    Button log_in;
    DatabaseReference reference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://e-seva-737e3-default-rtdb.firebaseio.com/");

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent backintent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(backintent);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_log_lay);
        init();

        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                U_name=u_name.getText().toString();
                U_passwd=u_pass.getText().toString();


                if(!U_name.isEmpty() && !U_passwd.isEmpty()){

                    auth.signInWithEmailAndPassword(U_name, U_passwd)
                            .addOnCompleteListener(user_log_lay.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = auth.getCurrentUser();
                                        String uid = user.getUid();
                                        reference.child("USERS").child(uid).child("password").setValue(U_passwd);
                                        Toast.makeText(user_log_lay.this, R.string.successfully_logged_in, Toast.LENGTH_SHORT).show();
                                        Intent intent2 = new Intent(user_log_lay.this, user_cat_lg.class);
                                        intent2.putExtra("U_name", uid);
                                        startActivity(intent2);
                                    } else {
                                        Toast.makeText(user_log_lay.this, getString(R.string.login_failed) + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else {
                    u_name.setError("Required");
                    u_pass.setError("Required");
                }
            }
        });
        forgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),ForgotPassword.class);
                startActivity(intent);
            }
        });
        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(user_log_lay.this, Registration_lay.class);
                startActivity(intent);
            }
        });
    }
    private void init(){
        reg_button= (TextView) findViewById(R.id.regbutton);
        u_name= (EditText) findViewById(R.id.usuname);
        u_pass= (EditText) findViewById(R.id.uspass);
        log_in= (Button) findViewById(R.id.login);
        forgotPasswordBtn= (TextView) findViewById(R.id.frogotpasswrdbutton);

        auth=FirebaseAuth.getInstance();
    }
}