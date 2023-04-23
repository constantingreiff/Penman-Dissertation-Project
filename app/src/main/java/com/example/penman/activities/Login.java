package com.example.penman.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.penman.R;
import com.example.penman.globals.app_data_stores.AppSession;
import com.example.penman.globals.general_functions.Intents;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class Login extends AppCompatActivity {

    private EditText v_email;
    private EditText v_password;
    private Button forgotPass;
    private Button succLoginBtn;
    private FirebaseAuth mAuth = AppSession.Session.mAuth;
    private FirebaseFirestore db = AppSession.Session.db;
    private ImageView loginBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBackBtn = findViewById(R.id.loginBackBtn);
        succLoginBtn = findViewById(R.id.succLoginBtn);
        v_email = findViewById(R.id.loginEmail);
        v_password = findViewById(R.id.loginPassword);
        loginBackBtn = findViewById(R.id.loginBackBtn);
        forgotPass = findViewById(R.id.loginFrPass);


        mAuth.getInstance().signOut();

        forgotPass.setOnClickListener(v -> {
            startActivity(Intents.forgotPasswordIntent(this));
        });

        loginBackBtn.setOnClickListener(v -> finish());

        succLoginBtn.setOnClickListener(v -> {
            String login_email = v_email.getText().toString().trim();
            String password = v_password.getText().toString().trim();

            if (!login_email.isEmpty() && !password.isEmpty()) {

                if (!password.contains(" ")) {
                    Intent ShowcaseBooks = Intents.bookShowcaseIntent(this);

                    processLogin(login_email, password, ShowcaseBooks);
                } else {
                  if (password.contains(" ")) {
                        Toast.makeText(this, "The password cannot contain spaces", Toast.LENGTH_LONG).show();
                    } else if (password.equals("")) {
                        Toast.makeText(this, "Please input a valid password", Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                Toast.makeText(this, "Please fill all the fields!", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void processLogin(String login_email, String password, Intent ShowcaseBooksIntent) {

        if (mAuth.getInstance().getCurrentUser() == null && AppSession.Session.userData.isEmpty()) {

            DocumentReference docRef = db.getInstance().collection("user").document(login_email);

            docRef.get().addOnCompleteListener(find_task -> {
                if (find_task.isSuccessful()) {

                    if (find_task.getResult().get("email") != null) {
                        String email = find_task.getResult().get("email").toString();

                        mAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(login_task -> {
                            if (login_task.isSuccessful()) {

                                db.getInstance().collection("user").document(login_email).get().addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        AppSession.Session.authenticatedUser = mAuth.getInstance().getCurrentUser();
                                        AppSession.Session.userData.putAll(Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getData()));

                                        if (!AppSession.Session.userData.isEmpty() && AppSession.Session.authenticatedUser.getUid() != null) {
                                            //Makes the keyboard go retract automatically if user has been authenticated.
                                            IBinder token = findViewById(android.R.id.content).getWindowToken();
                                            InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                            mgr.hideSoftInputFromWindow(token, 0);
                                            startActivity(ShowcaseBooksIntent);
                                        } else {
                                            Toast.makeText(this, "Something went wrong, please try again", Toast.LENGTH_LONG);
                                        }

                                    } else {
                                        Toast.makeText(this, "Something went wrong, please try again", Toast.LENGTH_LONG);
                                    }
                                });
                            } else {
                                String errorCode = ((FirebaseAuthException) login_task.getException()).getErrorCode();

                                switch (errorCode) {

                                    case "ERROR_INVALID_CUSTOM_TOKEN":
                                        Toast.makeText(this, "The custom token format is incorrect. Please check the documentation.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_CUSTOM_TOKEN_MISMATCH":
                                        Toast.makeText(this, "The custom token corresponds to a different audience.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_INVALID_CREDENTIAL":
                                        Toast.makeText(this, "The supplied auth credential is malformed or has expired.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_INVALID_EMAIL":
                                        Toast.makeText(this, "The email address is badly formatted.", Toast.LENGTH_LONG).show();

                                        break;

                                    case "ERROR_WRONG_PASSWORD":
                                        Toast.makeText(this, "The password is invalid or the user does not have a password.", Toast.LENGTH_LONG).show();

                                        break;

                                    case "ERROR_USER_MISMATCH":
                                        Toast.makeText(this, "The supplied credentials do not correspond to the previously signed in user.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_REQUIRES_RECENT_LOGIN":
                                        Toast.makeText(this, "This operation is sensitive and requires recent authentication. Log in again before retrying this request.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                                        Toast.makeText(this, "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_EMAIL_ALREADY_IN_USE":
                                        Toast.makeText(this, "The email address is already in use by another account.   ", Toast.LENGTH_LONG).show();

                                        break;

                                    case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                                        Toast.makeText(this, "This credential is already associated with a different user account.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_USER_DISABLED":
                                        Toast.makeText(this, "The user account has been disabled by an administrator.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_USER_TOKEN_EXPIRED":
                                        Toast.makeText(this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_USER_NOT_FOUND":
                                        Toast.makeText(this, "There is no user record corresponding to this identifier. The user may have been deleted.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_INVALID_USER_TOKEN":
                                        Toast.makeText(this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_OPERATION_NOT_ALLOWED":
                                        Toast.makeText(this, "This operation is not allowed. You must enable this service in the console.", Toast.LENGTH_LONG).show();
                                        break;

                                    case "ERROR_WEAK_PASSWORD":
                                        Toast.makeText(this, "The given password is invalid.", Toast.LENGTH_LONG).show();

                                        break;

                                }
                            }
                        });

                    } else {
                        Toast.makeText(this, "User doesn't exist!", Toast.LENGTH_LONG).show();
                    }
                }
            });

        } else {
            startActivity(ShowcaseBooksIntent);
        }

    }


}

