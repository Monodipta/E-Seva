package com.example.projectproto1.user_catalouge;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.projectproto1.R;
import com.example.projectproto1.track_application;
import com.example.projectproto1.user_catalouge.Fragments.Dash_Fragment;
import com.example.projectproto1.user_catalouge.Fragments.complaint_list_Fragment;
import com.example.projectproto1.user_catalouge.Fragments.helpfback_Fragment;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class user_cat_lg extends AppCompatActivity {

    FloatingActionButton compose_complain;
    ImageView user_img,headerUserImg;
    private final int GALLERY_REQ_CODE = 1000;
    BottomNavigationView nav_bar;
    Dash_Fragment dash_fragment = new Dash_Fragment();
    complaint_list_Fragment complaint_list_fragment = new complaint_list_Fragment();
    helpfback_Fragment helpfeedback_fragment = new helpfback_Fragment();
    static String U_name;
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    TextView USER_NAME,headerUName;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://e-seva-737e3-default-rtdb.firebaseio.com/");
    FirebaseAuth auth;
    FirebaseStorage storage=FirebaseStorage.getInstance();
    StorageReference reference1;
    private static int fragNumber;
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        new AlertDialog.Builder(user_cat_lg.this).setMessage(R.string.logoutmsg)
                .setCancelable(false).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getApplicationContext(), user_log_lay.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.no, null).show();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cat_lg);

        init();

        auth=FirebaseAuth.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nav = (NavigationView) findViewById(R.id.navmenu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.track:
                        Intent intent = new Intent(getApplicationContext(), track_application.class);
                        intent.putExtra("fragNumber",fragNumber);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.edit:
                        Intent intent1=new Intent(getApplicationContext(),EditProfile.class);
                        intent1.putExtra("uname",U_name);
                        intent1.putExtra("fragNumber",fragNumber);
                        startActivity(intent1);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                }
                return true;
            }
        });
        View headerView = nav.getHeaderView(0);
        headerUName = headerView.findViewById(R.id.headerUName);
        headerUserImg = headerView.findViewById(R.id.headerUserImg);

        reference.child("USERS").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String fName=snapshot.child(U_name).child("fullname").getValue(String.class);
                USER_NAME.setText(fName);
                headerUName.setText(fName);
                String image = snapshot.child(U_name).child("image").getValue(String.class);
                Picasso.get()
                        .load(image)
                        .into(user_img);
                Picasso.get()
                        .load(image)
                        .into(headerUserImg);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        Bundle bundle = new Bundle();
        bundle.putString("u_name", U_name);
        dash_fragment.setArguments(bundle);
        complaint_list_fragment.setArguments(bundle);


        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer, dash_fragment).commit();
        fragNumber=0;
        nav_bar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.dashbord:
                        fragNumber=0;
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer, dash_fragment).commit();
                        compose_complain.setVisibility(View.VISIBLE);
                        break;
                    case R.id.complist:
                        fragNumber=1;
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer, complaint_list_fragment).addToBackStack(null).commit();
                        compose_complain.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.helpfeedbck:
                        fragNumber=2;
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer, helpfeedback_fragment).addToBackStack(null).commit();
                        compose_complain.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.logout:
                        new AlertDialog.Builder(user_cat_lg.this).setMessage(R.string.logoutmsg).setCancelable(false).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(getApplicationContext(), user_log_lay.class);
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton(R.string.no, null).show();
                }

                return true;
            }
        });


        compose_complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(user_cat_lg.this, comp_lay.class);
                intent.putExtra("U_name", U_name);
                startActivity(intent);
            }
        });

        user_img.setOnClickListener(view -> ImagePicker.with(user_cat_lg.this)
                .crop()
                .compress(1024)
                .maxResultSize(1080,1080)
                .start());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode== Activity.RESULT_OK){

            if (data!=null){

                user_img.setImageURI(data.getData());
                headerUserImg.setImageURI(data.getData());

                reference1=storage.getReference()
                        .child("image").child(U_name);

                reference1.putFile(data.getData()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                reference.child("USERS").child(U_name).child("image").setValue(uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(user_cat_lg.this, R.string.image_uploaded, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        }
        else {

            Toast.makeText(this, R.string.image_not_select, Toast.LENGTH_SHORT).show();
        }
    }

    private void init() {
        compose_complain = (FloatingActionButton) findViewById(R.id.composecomplaint);
        user_img = (ImageView) findViewById(R.id.userimg);
        nav_bar = (BottomNavigationView) findViewById(R.id.navbar);
        USER_NAME = (TextView) findViewById(R.id.USERNAME);
        headerUName = (TextView) findViewById(R.id.headerUName);
        headerUserImg= (ImageView) findViewById(R.id.headerUserImg);

        U_name = getIntent().getStringExtra("U_name");
    }
}