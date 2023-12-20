package com.farminc.france.farmy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.farminc.france.farmy.R;
import com.farminc.france.farmy.controller.MainActivity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;

public class InfoUserActivity extends AppCompatActivity {

    private EditText mEditTextName;
    private Button mButtonName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_user);

        mEditTextName = findViewById(R.id.info_edittext_name);
        mButtonName = findViewById(R.id.info_button_name);

        mButtonName.setEnabled(false);

        //On regarde quand du texte est tapé
        mEditTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            //Méthode appelée quand du texte est tapé
            public void afterTextChanged(Editable editable) {
                //mButtonName.setEnabled(true);
                mButtonName.setEnabled(!editable.toString().isEmpty());

            }
        });

        //Si le bouton est appuyé

        mButtonName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Récupération du nom tapé dans le EditText
                String nom_user = mEditTextName.getText().toString();
                Log.d("Test", nom_user);

                UserInfo user = new UserInfo(nom_user);
                writeToJsonFile(user, "user.json");

                Intent mainIntent = new Intent(InfoUserActivity.this, MainActivity.class);
                //startActivity(mainIntent);
                //finish();
                //Envoi dans l'activité SynchroActivity
                Intent synchroActivityIntent = new Intent(InfoUserActivity.this, SynchroActivity.class);
                startActivity(synchroActivityIntent);
                finish();
            }
        });

    }


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


}


class UserInfo {
    private String _name;

    public UserInfo(String name) {
        this._name = name;
    }
    public String getFirstName() {
        return _name;
    }
}