package com.example.varosok;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

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
            addCity();
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

    private void addCity() {
        String nameContent = name.getText().toString();
        String countryContent = country.getText().toString();
        String populationContent = population.getText().toString();

        boolean valid = validation();

        if (valid){
            Toast.makeText(this,
                    "Minden mezőt ki kell tölteni", Toast.LENGTH_SHORT).show();
            return;
        }else{
            Toast.makeText(this,
                    "Város hozzáadva", Toast.LENGTH_SHORT).show();
        }

        int populationToInt = Integer.parseInt(populationContent);
        City city = new City(0,nameContent,countryContent,populationToInt);
        Gson jsonConverter = new Gson();
        RequestTask task = new RequestTask(MainActivity.URL, "POST",
                jsonConverter.toJson(city));
        task.execute();
    }

    private boolean validation() {
        if (name.getText().toString().isEmpty() ||
                country.getText().toString().isEmpty() || population.getText().toString().isEmpty())
            return true;
        else
            return false;
    }

    private class RequestTask extends AsyncTask<Void, Void, Response> {
        private String requestUrl;
        private String requestMethod;
        private String requestBody;

        public RequestTask(String requestUrl) {
            this.requestUrl = requestUrl;
            this.requestMethod = "GET";
        }

        public RequestTask(String requestUrl, String requestMethod) {
            this.requestUrl = requestUrl;
            this.requestMethod = requestMethod;
        }

        public RequestTask(String requestUrl, String requestMethod, String requestBody) {
            this.requestUrl = requestUrl;
            this.requestMethod = requestMethod;
            this.requestBody = requestBody;
        }

        @Override
        protected Response doInBackground(Void... voids) {
            Response response = null;
            try {
                switch (requestMethod) {
                    case "GET":
                        response = RequestHandler.get(MainActivity.URL);
                        break;
                    case "POST":
                        response = RequestHandler.post(requestUrl, requestBody);
                        break;
                    case "PUT":
                        response = RequestHandler.put(requestUrl, requestBody);
                        break;
                    case "DELETE":
                        response = RequestHandler.delete(requestUrl);
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

    }
}
