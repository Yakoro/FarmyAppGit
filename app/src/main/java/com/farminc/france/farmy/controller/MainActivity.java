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

        //Supprimer l'utilisateur
        //User write = null;
        //writeToJsonFile(write, "user.json");

        ConstraintLayout mMainLayout = (ConstraintLayout) findViewById(R.id.main_layout);
        mMainLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Log.d("Test", "Click");

                //Lecture du fichier JSON
                User read = readFromJsonFile("user.json");

                if (read == null) {
                    Log.d("Test", "Null");

                    // Démarrer InfoUserActivity
                    Intent infoUserIntent = new Intent(MainActivity.this, InfoUserActivity.class);
                    startActivity(infoUserIntent);
                    finish();
                } else {
                    Log.d("Test", "Non null");

                    // Démarrer SynchroActivity
                    Intent synchroIntent = new Intent(MainActivity.this, SynchroActivity.class);
                    startActivity(synchroIntent);
                    finish();

                }


                User readTest = readFromJsonFile("user.json");

                if(readTest == null) {
                    Log.d("Test", "Null");
                }
                else {
                    Log.d("Test", "Non null");
                    String name = readTest.getFirstName();
                    Log.d("Test", name);
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

class User {
    private String _name;

    public User(String name) {
        this._name = name;
    }
    public String getFirstName() {
        return _name;
    }

    public void setFirstName(String name) {
        _name = name;
    }
}