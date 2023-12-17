package com.farminc.france.farmy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FullFarmyActivity extends AppCompatActivity {


    private Button firstFloorButton;
    private Button secondFloorButton;
    private Button thirdFloorButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_farmy);

        getDataFromAPI();

        firstFloorButton = findViewById(R.id.first_floor_button);
        secondFloorButton = findViewById(R.id.second_floor_button);
        thirdFloorButton = findViewById(R.id.third_floor_button);
        firstFloorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Démarrer FirstFloorActivity
                Intent firstFloorIntent = new Intent(FullFarmyActivity.this, FirstFloorActivity.class);
                startActivity(firstFloorIntent);
                finish();
            }
        });
        secondFloorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Démarrer SecondFloorActivity
                Intent secondFloorIntent = new Intent(FullFarmyActivity.this, SecondFloorActivity.class);
                startActivity(secondFloorIntent);
                finish();
            }
        });
        thirdFloorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Démarrer ThirdFloorActivity
                Intent thirdFloorIntent = new Intent(FullFarmyActivity.this, ThirdFloorActivity.class);
                startActivity(thirdFloorIntent);
                finish();
            }
        });
    }

    private void getDataFromAPI() {
        OkHttpClient client = new OkHttpClient();

        // Créer la requête
        Request request = new Request.Builder()
                .url("http://192.168.1.10/general/")
                .build();

        // Faire la requête de manière asynchrone
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                // Gérer l'échec de la requête
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();

                    try {
                        // Convertir la réponse JSON en objet JSON
                        JSONObject jsonObject = new JSONObject(responseData);

                        // Récupérer les données
                        int nbLevel = jsonObject.getInt("Nb_Level");
                        int waterTank = jsonObject.getInt("Water_Tank");
                        int nutrimentTank = jsonObject.getInt("Nutriment_Tank");
                        int error = jsonObject.getInt("Error");

                        updateUI(nbLevel, waterTank, nutrimentTank, error);

                        // Utiliser les données récupérées
                        Log.d("API_Data", "Nb_Level: " + nbLevel);
                        Log.d("API_Data", "Water_Tank: " + waterTank);
                        Log.d("API_Data", "Nutriment_Tank: " + nutrimentTank);
                        Log.d("API_Data", "Error: " + error);

                        // Vous pouvez également mettre à jour l'interface utilisateur avec ces données
                        // runOnUiThread(() -> updateUI(nbLevel, waterTank, nutrimentTank, error));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Gérer la réponse non réussie
                }
            }
        });
    }

    // Méthode pour mettre à jour l'interface utilisateur avec les données récupérées
    private void updateUI(int nbLevel, int waterTank, int nutrimentTank, int error) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView waterTankText = findViewById(R.id.waterTankText);
                String waterTank_string = String.valueOf(waterTank);
                waterTankText.setText("Niveau d'eau : " + waterTank_string + " L");

                TextView nutrimentTankText = findViewById(R.id.nutrimentTankText);
                String nutrimentTank_string = String.valueOf(nutrimentTank);
                nutrimentTankText.setText("Niveau de nutriments : " + nutrimentTank_string + " mL / L");
            }
        });
    }
}