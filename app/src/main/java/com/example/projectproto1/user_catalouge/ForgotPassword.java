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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText emailET;
    private Button forgetPasswordBtn;
    private FirebaseAuth auth;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        init();

        forgetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=emailET.getText().toString();
                if (email.isEmpty()){
                    emailET.setError("Required");
                }
                else {
                    auth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(ForgotPassword.this, new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(ForgotPassword.this, R.string.password_reset_link_send, Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(),user_log_lay.class));
                                    }
                                    else {
                                        Toast.makeText(ForgotPassword.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
    private void init(){
        emailET= (EditText) findViewById(R.id.forgot_email);
        forgetPasswordBtn= (Button) findViewById(R.id.forget_password);
        auth=FirebaseAuth.getInstance();
    }
}