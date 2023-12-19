package com.farminc.france.farmy.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.farminc.france.farmy.InfoUserActivity;
import com.farminc.france.farmy.R;
import com.farminc.france.farmy.SynchroActivity;
import com.google.gson.Gson;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConstraintLayout mMainLayout = (ConstraintLayout) findViewById(R.id.main_layout);
        mMainLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                //Lecture du fichier JSON
                User read = readFromJsonFile("user.json");

                if (read == null) {

                    // Démarrer InfoUserActivity
                    Intent infoUserIntent = new Intent(MainActivity.this, InfoUserActivity.class);
                    startActivity(infoUserIntent);
                    finish();
                } else {

                    // Démarrer SynchroActivity
                    Intent synchroIntent = new Intent(MainActivity.this, SynchroActivity.class);
                    startActivity(synchroIntent);
                    finish();

                }

                User readTest = readFromJsonFile("user.json");

                if(readTest == null) {
                }
                else {
                    String name = readTest.getFirstName();
                }

                return false;
            }


        });

    }

    //Fonction pour l'écriture dans un fichier JSON
    private void writeToJsonFile(Object object, String fileName) {
        Gson gson = new Gson();
        String json = gson.toJson(object);

        try {
            FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(json.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Fonction pour la lecture du fichier JSON
    private User readFromJsonFile(String fileName) {
        Gson gson = new Gson();
        String json = "";
        try {
            FileInputStream fis = openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            json = sb.toString();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gson.fromJson(json, User.class);
    }

}

