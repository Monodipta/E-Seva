package com.example.projectproto1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectproto1.admin_catalouge.admin_log_lay;
import com.example.projectproto1.user_catalouge.user_log_lay;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    Button ad_log,user_log;
    TextView quotes;
    Button languageBtn;

    private int backcount=0,langFlag=0;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        languageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               changeLanguage();
            }
        });
        ad_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isNetworkConnected()){
                    Intent intent1=new Intent(getApplicationContext(), admin_log_lay.class);
                    startActivity(intent1);
                }
                else {
                    Toast.makeText(MainActivity.this, "Please check the internet connection!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        user_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isNetworkConnected()){
                    Intent intent2=new Intent(getApplicationContext(), user_log_lay.class);
                    startActivity(intent2);
                }
                else {
                    Toast.makeText(MainActivity.this, "Please check the internet connection!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void changeLanguage() {

        final String languages[]={"English","বাংলা"};
        AlertDialog.Builder mbuilder=new AlertDialog.Builder(this);
        mbuilder.setTitle(R.string.choose_lang);
        mbuilder.setSingleChoiceItems(languages, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i==0){
                    setlocale("");
                    recreate();
                } else if (i==1) {
                    setlocale("bn");
                    recreate();
                }
            }
        });
        mbuilder.create();
        mbuilder.show();
    }

    private void setlocale( String language) {

        Locale locale=new Locale(language);
        Locale.setDefault(locale);

        Configuration configuration=new Configuration();
        configuration.locale=locale;
        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());

    }

    @Override
    public void onBackPressed() {
        backcount++;
        if(backcount==1) {
            Toast.makeText(this, R.string.press_back_again_to_exit, Toast.LENGTH_SHORT).show();
        }
        else if (backcount==2) {
            finishAffinity();
        }
    }
    private boolean isNetworkConnected(){
        ConnectivityManager connectivityManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    private void init(){
        ad_log= (Button) findViewById(R.id.adlogin);
        user_log= (Button) findViewById(R.id.userlogin);
        quotes= (TextView) findViewById(R.id.qoute);
        languageBtn= (Button) findViewById(R.id.language_btn);
    }

}