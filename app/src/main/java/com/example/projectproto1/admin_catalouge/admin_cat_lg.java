package com.example.projectproto1.admin_catalouge;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectproto1.R;
import com.example.projectproto1.admin_catalouge.AdminFragment.AdminDashFragment;
import com.example.projectproto1.admin_catalouge.AdminFragment.ComplaintListFragment;
import com.example.projectproto1.admin_catalouge.AdminFragment.HelpFeedbackFragment;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class admin_cat_lg extends AppCompatActivity {

    AdminDashFragment adminDashFragment=new AdminDashFragment();
    ComplaintListFragment complaintListFragment=new ComplaintListFragment();
    HelpFeedbackFragment helpFeedbackFragment=new HelpFeedbackFragment();

    BottomNavigationView nav_bar;
    String U_name,DEPT;
    DatabaseReference reference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://e-seva-737e3-default-rtdb.firebaseio.com/");
    ImageView adminImg;
    TextView Admin_name;
    FirebaseStorage storage=FirebaseStorage.getInstance();
    StorageReference reference1;

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(admin_cat_lg.this).setMessage(R.string.logoutmsg).setCancelable(false).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent=new Intent(getApplicationContext(), admin_log_lay.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.no,null).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cat_lg);

        init();

        reference.child("OFFICER").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Admin_name.setText(snapshot.child(U_name).child("fullname").getValue(String.class));
                String image = snapshot.child(U_name).child("image").getValue(String.class);
                Picasso.get()
                        .load(image)
                        .into(adminImg);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Bundle bundle=new Bundle();
        bundle.putString("u_name",U_name);
        adminDashFragment.setArguments(bundle);

        Bundle bundle1=new Bundle();
        bundle1.putString("dept",DEPT);
        complaintListFragment.setArguments(bundle1);

        getSupportFragmentManager().beginTransaction().replace(R.id.adframecontainer,adminDashFragment).commit();
        nav_bar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()){

                    case R.id.dashbord:
                        getSupportFragmentManager().beginTransaction().replace(R.id.adframecontainer,adminDashFragment).commit();
                        break;
                    case R.id.complist:
                        getSupportFragmentManager().beginTransaction().replace(R.id.adframecontainer,complaintListFragment).commit();
                        break;
                    case R.id.helpfeedbck:
                        getSupportFragmentManager().beginTransaction().replace(R.id.adframecontainer,helpFeedbackFragment).commit();
                        break;
                    case R.id.logout:
                        new AlertDialog.Builder(admin_cat_lg.this).setMessage(R.string.logoutmsg).setCancelable(false).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent=new Intent(getApplicationContext(), admin_log_lay.class);
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton(R.string.no,null).show();
                }

                return true;
            }
        });

        adminImg.setOnClickListener(view -> ImagePicker.with(admin_cat_lg.this)
                .crop()
                .compress(1024)
                .maxResultSize(1080,1080)
                .start());
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode== Activity.RESULT_OK){

            if (data!=null){

                adminImg.setImageURI(data.getData());

                reference1=storage.getReference()
                        .child("adminimage").child(U_name);

                reference1.putFile(data.getData()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                reference.child("OFFICER").child(U_name).child("image").setValue(uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(admin_cat_lg.this, R.string.image_uploaded, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        }
        else {

            Toast.makeText(admin_cat_lg.this, R.string.image_not_selected, Toast.LENGTH_SHORT).show();
        }
    }

    private void init(){

        nav_bar= (BottomNavigationView) findViewById(R.id.navbar);
        Admin_name= (TextView) findViewById(R.id.adminname);
        adminImg= (ImageView) findViewById(R.id.adminimg);

        U_name=getIntent().getStringExtra("U_name");
        DEPT=getIntent().getStringExtra("dept");
        System.out.println(U_name);
    }
}