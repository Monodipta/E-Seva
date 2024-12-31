package com.example.projectproto1.user_catalouge.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.projectproto1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;


public class complaint_show extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FloatingActionButton back,saveAsPdf;

    String complaint,refid,taxno,title;
    public complaint_show() {
        // Required empty public constructor
    }

    public complaint_show(String complaint, String refid, String taxno, String title) {

        this.complaint=complaint;
        this.refid=refid;
        this.taxno=taxno;
        this.title=title;
    }

    // TODO: Rename and change types and number of parameters
    public static complaint_show newInstance(String param1, String param2) {
        complaint_show fragment = new complaint_show();
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
        View view=inflater.inflate(R.layout.fragment_complaint_show, container, false);

        TextView titleholder=view.findViewById(R.id.titleshow);
        TextView refidholder=view.findViewById(R.id.refidshow);
        TextView compholder=view.findViewById(R.id.compshow);

        back=view.findViewById(R.id.back);
        saveAsPdf=view.findViewById(R.id.save_as_pdf);

        titleholder.setText(title);
        refidholder.setText(refid);
        compholder.setText(complaint);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Back();
            }
        });
        saveAsPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text=title+"\n\n"+"Referenceid: "+refid+"\n\n"+"Tax No.: "+taxno+"\n\n"+complaint;
                convertToPdf(text);
            }
        });


        return view;
    }

    private void Back(){
        AppCompatActivity activity=(AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,new complaint_list_Fragment()).addToBackStack(null).commit();
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

            String filePath = folder.getAbsolutePath() + "/"+refid+"myFile.pdf";
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            Paragraph paragraph = new Paragraph(text);
            document.add(paragraph);

            document.close();

            Toast.makeText(getContext(), "PDF created successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}