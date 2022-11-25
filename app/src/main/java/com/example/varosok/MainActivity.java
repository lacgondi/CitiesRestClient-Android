package com.example.varosok;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String URL = "https://retoolapi.dev/If7jC8/data";

    private Button add;
    private Button list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                MainActivity.this.startActivity(intent);
                finish();
            }
        });
    }

    private void init() {
        add = findViewById(R.id.buttonAdd);
        list = findViewById(R.id.buttonList);
    }
}