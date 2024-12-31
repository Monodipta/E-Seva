package com.example.projectproto1.user_catalouge;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectproto1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;


public class comp_lay extends AppCompatActivity {

    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;
    private FloatingActionButton expand,download_file,send_complaint,speech_btn;
    private EditText complaint,title,taxno;
    private Spinner departments;
    private boolean clicked=false;
    private Animation rotateOpen;
    private Animation rotateClose;
    private Animation fromBottom;
    private Animation toBottom;
    private String U_name,timestamp,comp_ref_id,mob,full_name,Comp,Ttl,Taxnon,Dept,dateString;
    DatabaseReference reference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://e-seva-737e3-default-rtdb.firebaseio.com/");

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Intent intent=new Intent(getApplicationContext(),user_cat_lg.class);
        //startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comp_lay);
        init();
        gettime();
        getdate();
        getmob();
        comp_ref_id=timestamp;



        expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onExpandButtonClicked();
            }
        });
        speech_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpeakNow(view);
            }
        });

        send_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                push();

            }
        });

        download_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text=title.getText().toString()+"\n\n"+"Referenceid: "+(mob+dateString+comp_ref_id).replace(":","").replace(" ","").replace("-","").toUpperCase()+"\n\n"+"Tax No.: "+taxno.getText().toString()+"\n\n"+complaint.getText().toString();
                convertToPdf(text);
            }
        });

    }
    private void SpeakNow(View view){

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,getString(R.string.strat_speaking));

        try {

            startActivityForResult(intent,REQUEST_CODE_SPEECH_INPUT);
        }
        catch(Exception e){

            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    private void push(){
        Comp=complaint.getText().toString();
        Dept=departments.getSelectedItem().toString();
        Ttl=title.getText().toString();
        Taxnon=taxno.getText().toString();
        comp_ref_id=(mob+dateString+comp_ref_id).replace(":","");
        comp_ref_id=comp_ref_id.replace(" ","");
        comp_ref_id=comp_ref_id.replace("-","");
        comp_ref_id=comp_ref_id.toUpperCase();


        if(!Comp.isEmpty() && !Dept.equals("Department") && !Ttl.isEmpty() && !Taxnon.isEmpty() ){
            reference.child("COMPLAINTS").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    reference.child("COMPLAINTS").child(Dept).child(comp_ref_id).child("fullname").setValue(full_name);
                    reference.child("COMPLAINTS").child(Dept).child(comp_ref_id).child("phoneno").setValue(mob);
                    reference.child("COMPLAINTS").child(Dept).child(comp_ref_id).child("refid").setValue(comp_ref_id);
                    reference.child("COMPLAINTS").child(Dept).child(comp_ref_id).child("title").setValue(Ttl);
                    reference.child("COMPLAINTS").child(Dept).child(comp_ref_id).child("taxno").setValue(Taxnon);
                    reference.child("COMPLAINTS").child(Dept).child(comp_ref_id).child("complaint").setValue(Comp);
                    reference.child("COMPLAINTS").child(Dept).child(comp_ref_id).child("date").setValue(dateString);
                    reference.child("COMPLAINTS").child(Dept).child(comp_ref_id).child("status").setValue("Not Viewed");
                    reference.child("COMPLAINTS").child(Dept).child(comp_ref_id).child("department").setValue(Dept);

                    reference.child("COMPLAINTSUSER").child(U_name).child(comp_ref_id).child("title").setValue(Ttl);
                    reference.child("COMPLAINTSUSER").child(U_name).child(comp_ref_id).child("complaint").setValue(Comp);
                    reference.child("COMPLAINTSUSER").child(U_name).child(comp_ref_id).child("taxno").setValue(Taxnon);
                    reference.child("COMPLAINTSUSER").child(U_name).child(comp_ref_id).child("refid").setValue(comp_ref_id);
                    reference.child("COMPLAINTSUSER").child(U_name).child(comp_ref_id).child("date").setValue(dateString);

                    reference.child("USERS").child(U_name).child("refid").setValue(comp_ref_id);

                    Toast.makeText(comp_lay.this, R.string.complaint_submitted, Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else {
            Toast.makeText(comp_lay.this, R.string.please_provide_all_the_details, Toast.LENGTH_SHORT).show();
            comp_ref_id=timestamp;
        }
    }
    private void convertToPdf(String text) {
        Document document = new Document();

        try {
            File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

            // Create the "MyPDFs" directory within the "Downloads" directory
            File folder = new File(downloadsDir, "MyPDFs");
            if (!folder.exists()) {
                folder.mkdirs();
            }

            String filePath = folder.getAbsolutePath() + "/"+(mob+dateString+comp_ref_id).replace(":","").replace(" ","").replace("-","").toUpperCase()+"myFile.pdf";
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            Paragraph paragraph = new Paragraph(text);
            document.add(paragraph);

            document.close();

            Toast.makeText(this, "PDF created successfully", Toast.LENGTH_SHORT).show();
            push();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       switch(requestCode){

           case REQUEST_CODE_SPEECH_INPUT:{
               if(resultCode==RESULT_OK && null!=data){
                   ArrayList<String> result= data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                   String exsistingtext = complaint.getText().toString();
                   complaint.setText(exsistingtext+" "+result.get(0));
               }
               break;
           }
       }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void gettime(){
        ZoneId kolkataZoneId = ZoneId.of("Asia/Kolkata");
        LocalTime kolkataTime = LocalTime.now(kolkataZoneId);
        String formattedTime = kolkataTime.format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
        timestamp=formattedTime;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getdate(){
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dateString = currentDate.format(formatter);
    }

    private void getmob()
    {
        reference.child("USERS").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mob=snapshot.child(U_name).child("phoneno").getValue(String.class);
                full_name=snapshot.child(U_name).child("fullname").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void init()
    {
        complaint= (EditText) findViewById(R.id.complaint);
        title= (EditText) findViewById(R.id.comp_title);
        taxno= (EditText) findViewById(R.id.tax_no);
        departments= (Spinner) findViewById(R.id.dropdown);

        U_name=getIntent().getStringExtra("U_name");

        expand= (FloatingActionButton) findViewById(R.id.expand);
        download_file= (FloatingActionButton) findViewById(R.id.downloadfile);
        send_complaint= (FloatingActionButton) findViewById(R.id.sendcomplaint);

        rotateOpen= AnimationUtils.loadAnimation(this,R.anim.rotate_open_anim);
        rotateClose= AnimationUtils.loadAnimation(this,R.anim.rotate_close_anim);
        fromBottom= AnimationUtils.loadAnimation(this,R.anim.from_bottom_anim);
        toBottom= AnimationUtils.loadAnimation(this,R.anim.to_bottom_anim);
        speech_btn= (FloatingActionButton) findViewById(R.id.speechbtn);
    }
    private void onExpandButtonClicked()
    {
        setVisibility(clicked);
        setAnimation(clicked);
        if(!clicked){
            clicked=true;
        }
        else {
            clicked=false;
        }
    }
    private void setVisibility(boolean clicked){
        if(!clicked){
            download_file.show();
            send_complaint.show();
        }
        else {
            download_file.hide();
            send_complaint.hide();
        }
    }
    private void setAnimation(boolean clicked){
        if(!clicked){
            download_file.startAnimation(fromBottom);
            send_complaint.startAnimation(fromBottom);
            speech_btn.startAnimation(fromBottom);
            expand.startAnimation(rotateOpen);
        }
        else{
            download_file.startAnimation(toBottom);
            send_complaint.startAnimation(toBottom);
            speech_btn.startAnimation(toBottom);
            expand.startAnimation(rotateClose);
        }
    }
}