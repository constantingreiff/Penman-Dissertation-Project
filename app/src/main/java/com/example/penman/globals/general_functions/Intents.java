package com.example.penman.globals.general_functions;


import android.content.Context;
import android.content.Intent;

import com.example.penman.activities.Login;
import com.example.penman.activities.RegisterSuccessful;

public class Intents {

    public static Intent registerIntent(Context context) {
        return new Intent(context, com.example.penman.activities.Register.class);
    }

    public static Intent registerSuccIntent(Context context) {
        return new Intent(context, RegisterSuccessful.class);
    }

    public static Intent loginIntent(Context context) {
        return new Intent(context, Login.class);
    }

    public static Intent mainMenuIntent(Context context) {
        return new Intent(context, com.example.penman.activities.MainActivity.class);
    }

    public static Intent forgotPasswordIntent(Context context) {
        return new Intent(context, com.example.penman.activities.PasswordReset.class);

    }

    public static Intent bookShowcaseIntent(Context context) {
        return new Intent(context, com.example.penman.activities.ShowcaseBooks.class);
    }

    public static Intent addBookIntent(Context context) {
        return new Intent(context, com.example.penman.activities.AddBook.class);
    }

    public static Intent lBookRIntent(Context context) {
        return new Intent(context, com.example.penman.activities.LBookReview.class);
    }

    public static Intent profileIntent(Context context){
        return new Intent(context, com.example.penman.activities.Profile.class);
    }
}
