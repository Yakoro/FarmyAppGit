package com.farminc.france.farmy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.farminc.france.farmy.controller.MainActivity;


public class SynchroActivity extends AppCompatActivity {

    private Button mButtonName;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchro);

        mButtonName = findViewById(R.id.full_farmy_button);
        mButtonName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Démarrer FullFarmyActivity
                Intent fullFarmyIntent = new Intent(SynchroActivity.this, FullFarmyActivity.class);
                startActivity(fullFarmyIntent);
                finish();
            }
        });

    }
}