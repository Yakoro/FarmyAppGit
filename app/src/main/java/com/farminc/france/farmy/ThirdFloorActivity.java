package com.farminc.france.farmy;

import androidx.appcompat.app.AppCompatActivity;

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


public class ThirdFloorActivity extends AppCompatActivity {
    private Button backButton;
    int temperature_needed = 21;
    int moisure_needed = 20;
    int luminosity_needed = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_floor);

        // Appel à la méthode pour récupérer les données de l'API
        getDataFromAPI();

        backButton = findViewById(R.id.back);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Démarrer FullFarmyActivity
                Intent fullFarmyIntent = new Intent(ThirdFloorActivity.this, FullFarmyActivity.class);
                startActivity(fullFarmyIntent);
                finish();
            }
        });
    }

    private void getDataFromAPI() {
        OkHttpClient client = new OkHttpClient();

        // Créer la requête
        Request request = new Request.Builder()
                .url("http://192.168.1.10/etage_2/")
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
                        int temperature = jsonObject.getInt("Temperature");
                        int moisure = jsonObject.getInt("Moisure");
                        int luminosity = jsonObject.getInt("Luminosity");

                        updateUI(temperature, moisure, luminosity);

                        // Utiliser les données récupérées
                        Log.d("API_Data", "Temperature : " + temperature);
                        Log.d("API_Data", "Moisure : " + moisure);
                        Log.d("API_Data", "Luminosity : " + luminosity);

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
    private void updateUI(int temperature, int moisure, int luminosity) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView tempText = findViewById(R.id.tempText);
                String temperature_string = String.valueOf(temperature);
                tempText.setText("Température : " + temperature_string + " °C  |  Requise : " + temperature_needed + " °C");

                TextView moisureText = findViewById(R.id.moisureText);
                String moisure_string = String.valueOf(moisure);
                moisureText.setText("Humidité : " + moisure_string + " %  |  Requise : " + moisure_needed + " %");

                TextView lumiText = findViewById(R.id.lumiText);
                String luminosity_string = String.valueOf(luminosity);
                lumiText.setText("Luminosity : " + luminosity_string + " lux  |  Requise : " + luminosity_needed + " lux");
            }
        });
    }

}
