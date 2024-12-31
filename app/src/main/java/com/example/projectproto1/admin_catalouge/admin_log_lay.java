package com.example.projectproto1.admin_catalouge;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectproto1.MainActivity;
import com.example.projectproto1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class admin_log_lay extends AppCompatActivity {

    TextView forgot_passwrd,Admin_name;
    Button log_in;
    EditText u_name,passwd;
    Spinner Dept;
    String U_name,Passwd,dept;
    DatabaseReference reference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://e-seva-737e3-default-rtdb.firebaseio.com/");

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent backintent=new Intent(getApplicationContext(), MainActivity.class);
        startActivity(backintent);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_log_lay);

        init();

        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                U_name=u_name.getText().toString();
                Passwd=passwd.getText().toString();
                //U_name=Uname.replace(".","");
                System.out.println(U_name);
                dept=Dept.getSelectedItem().toString();
                
                if(!U_name.isEmpty() && !Passwd.isEmpty()){

                    reference.child("OFFICER").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(U_name)){
                                final String getpasswd=snapshot.child(U_name).child("password").getValue(String.class);
                                final String getdept=snapshot.child(U_name).child("department").getValue(String.class);
                                if(getpasswd.equals(Passwd)){
                                    if(getdept.equals(dept)){
                                        Toast.makeText(admin_log_lay.this, R.string.successfully_loggedin, Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(admin_log_lay.this, admin_cat_lg.class);
                                        intent.putExtra("U_name",U_name);
                                        intent.putExtra("dept",dept);
                                        startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(admin_log_lay.this, R.string.select_department_name_properly, Toast.LENGTH_SHORT).show();
                                    }

                                }
                                else {
                                    Toast.makeText(admin_log_lay.this, R.string.wrong_password, Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(admin_log_lay.this, R.string.user_doesn_t_exist, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                else {
//                    Toast.makeText(admin_log_lay.this, R.string.please_provide_all_the_details, Toast.LENGTH_SHORT).show();
                    u_name.setError("Required");
                    passwd.setError("Required");
                }
            }
        });

        forgot_passwrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(admin_log_lay.this).setTitle(R.string.warning).setIcon(R.drawable.baseline_warning_24)
                        .setMessage(R.string.DBAcontact)
                        .setCancelable(false).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .setNegativeButton(" ",null).show();
            }
        });
    }

    private void init(){
        u_name= (EditText) findViewById(R.id.adminuname);
        passwd= (EditText) findViewById(R.id.adminpass);
        log_in= (Button) findViewById(R.id.login);
        forgot_passwrd= (TextView) findViewById(R.id.frogotpasswrdbutton);
        Dept= (Spinner) findViewById(R.id.dropdown);
    }
}