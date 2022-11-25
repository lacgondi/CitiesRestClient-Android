package com.example.varosok;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class ListActivity extends AppCompatActivity {
    private Button backButton;
    private TextView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        init();
        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(ListActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        try {
            list.setText(loadPeopleFromServer());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init(){
        backButton = findViewById(R.id.buttonBack);
        list = findViewById(R.id.listOut);
    }

    private String loadPeopleFromServer() throws IOException {
        Response response = RequestHandler.get(MainActivity.URL);
        return response.getContent();
    }
}
