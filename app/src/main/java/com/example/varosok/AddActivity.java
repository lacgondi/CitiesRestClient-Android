package com.example.varosok;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

public class AddActivity extends AppCompatActivity {
    private EditText name;
    private EditText country;
    private EditText population;
    private Button insert;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        init();
        insert.setOnClickListener(view -> {
            String nameContent =String.valueOf(name.getText());
            String countryContent =String.valueOf(country.getText());
            int populationContent =Integer.parseInt(String.valueOf(population.getText()));
            if(nameContent.isEmpty()){
                Toast.makeText(AddActivity.this, "Töltsd ki a név mezőt",
                        Toast.LENGTH_SHORT).show();
            }
            if(countryContent.isEmpty()){
                Toast.makeText(AddActivity.this, "Töltsd ki az ország mezőt",
                        Toast.LENGTH_SHORT).show();
            }
            City city = new City(0,nameContent,countryContent,populationContent);
            Gson converter = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            String json = converter.toJson(city);
            Response response = null;
            try {
                response = RequestHandler.post(MainActivity.URL, json);
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert response != null;
            if (response.getResponseCode() == 201) {
                Toast.makeText(AddActivity.this, "Város hozzáadva",
                        Toast.LENGTH_SHORT).show();
            } else {
                String content = response.getContent();
                Toast.makeText(AddActivity.this, content,
                        Toast.LENGTH_SHORT).show();
            }
        });
        back.setOnClickListener(view -> {
            Intent intent = new Intent(AddActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void init(){
        name = findViewById(R.id.addName);
        country = findViewById(R.id.addCountry);
        population = findViewById(R.id.addPopulation);
        insert = findViewById(R.id.buttonInsert);
        back = findViewById(R.id.buttonBackFromInsert);
    }
}
