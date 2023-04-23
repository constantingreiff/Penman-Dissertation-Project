package com.example.penman.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.penman.R;
import com.example.penman.globals.app_data_stores.AppSession;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordReset extends AppCompatActivity {


    private FirebaseAuth mAuth = AppSession.Session.mAuth;

    private EditText passwordResetField;
    private Button passwordResetBtn;
    private ImageView passResetBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        passwordResetField = findViewById(R.id.passResetField);
        passwordResetBtn = findViewById(R.id.passResetBtn);
        passResetBackBtn = findViewById(R.id.passResetBackBtn);

        passResetBackBtn.setOnClickListener(v -> {
            finish();
        });


        passwordResetBtn.setOnClickListener(v -> {
            String email = passwordResetField.getText().toString();
            forgotPassword(email);

        });


    }

    private void forgotPassword(String email) {

        try {
            mAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Reset link has been sent.", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(this, "An error ocurred, please try again.", Toast.LENGTH_LONG).show();
                }
            });

        } catch (Exception e) {

        }

    }
}

