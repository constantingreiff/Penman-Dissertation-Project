package com.example.penman.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.penman.R;
import com.example.penman.globals.general_functions.Intents;

public class Profile extends AppCompatActivity {


    private Button addBookBtn;
    private ImageView academicProfileBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        addBookBtn = findViewById(R.id.addBookBtn);

        academicProfileBackBtn = findViewById(R.id.academicProfileBackBtn);

        academicProfileBackBtn.setOnClickListener(v -> {
            finish();
        });



        addBookBtn.setOnClickListener(v -> {
            startActivity(Intents.addBookIntent(this));
        });
    }
}