package com.farminc.france.farmy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.farminc.france.farmy.controller.MainActivity;
import com.farminc.france.farmy.controller.User;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SynchroActivity extends AppCompatActivity {

    private Button mButtonName;
    private Button deleteUserButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchro);

        mButtonName = findViewById(R.id.full_farmy_button);
        deleteUserButton = findViewById(R.id.deleteUserButton);

        deleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Supprimer l'utilisateur
                User write = null;
                writeToJsonFile(write, "user.json");
                // Démarrer InfoUserActivity
                Intent infoUserIntent = new Intent(SynchroActivity.this, InfoUserActivity.class);
                startActivity(infoUserIntent);
                finish();
            }
        });

        // Récupérer le nom de l'utilisateur depuis le fichier JSON
        String userName = getUserNameFromJson();

        // Utiliser le nom de l'utilisateur comme nécessaire
        Log.d("UserName", "User's name is: " + userName);

        TextView nameText = findViewById(R.id.nameText);
        nameText.setText("Bonjour " + userName);

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

    // Méthode pour obtenir le nom de l'utilisateur à partir du fichier JSON
    private String getUserNameFromJson() {
        // Utilisez votre méthode readFromJsonFile pour obtenir l'objet User
        User user = readFromJsonFile("user.json");
        if (user != null) {
            return user.getFirstName(); // Renvoie le nom de l'utilisateur
        } else {
            return "No user found"; // Renvoie une valeur par défaut si aucun utilisateur n'est trouvé
        }
    }

    // Méthode pour la lecture du fichier JSON (assurez-vous de l'adapter à votre code actuel)
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
