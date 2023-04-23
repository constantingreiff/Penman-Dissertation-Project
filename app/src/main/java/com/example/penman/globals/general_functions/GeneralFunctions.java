package com.example.penman.globals.general_functions;

import android.text.TextUtils;
import android.util.Patterns;

public class GeneralFunctions {
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}