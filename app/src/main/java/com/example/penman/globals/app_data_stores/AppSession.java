package com.example.penman.globals.app_data_stores;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AppSession {

    public static class Session {
        public static FirebaseUser authenticatedUser;
        public static Map<String, Object> userData = new HashMap<>();
        public static FirebaseAuth mAuth;
        public static FirebaseFirestore db;
    }
}
