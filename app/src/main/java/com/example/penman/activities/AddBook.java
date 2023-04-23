package com.example.penman.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.penman.R;
import com.example.penman.globals.app_data_stores.AppSession;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AddBook extends AppCompatActivity {


    private EditText book_title;
    private EditText book_author;
    private EditText book_edition;
    private EditText book_category;

    private EditText book_description;
    private Button book_submit;
    private Button book_img_chose_btn;

    private Uri filePath;

    private ImageView book_img;

    private FirebaseFirestore db = AppSession.Session.db;
    private FirebaseStorage storageRef = FirebaseStorage.getInstance();


    int SELECT_PICTURE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        book_title = findViewById(R.id.addBookTitle);
        book_author = findViewById(R.id.addBookAuthor);
        book_edition = findViewById(R.id.addBookEdition);
        book_category = findViewById(R.id.addBookCategory);
        book_description = findViewById(R.id.addBookDescription);
        book_img = findViewById(R.id.addBookImg);
        book_img_chose_btn = findViewById(R.id.addBookImgBtn);
        book_submit = findViewById(R.id.addBookSubmitBtn);


        book_img_chose_btn.setOnClickListener(v -> {
            imageChooser();
        });


        book_submit.setOnClickListener(v -> {
            submitBook();
        });

    }


    public void submitBook(){

        String title = book_title.getText().toString();
        String author = book_author.getText().toString();
        String description = book_description.getText().toString();
        String edition = book_edition.getText().toString();
        String category = book_category.getText().toString();

        String author_email = AppSession.Session.userData.get("email").toString();

        String book_photo_name = UUID.randomUUID().toString();

        Map book = new HashMap<>();

        book.put("title", title);
        book.put("author", author);
        book.put("edition", edition);
        book.put("category", category);
        book.put("description", description);
        book.put("author_email", author_email);
        book.put("photo_name", book_photo_name);
        book.put("Reviews", new ArrayList<Map<Object, String>>());

        //Sets the adapter and recyclerView to display the queried list.
        db.getInstance().collection("showcase_book").add(book).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                uploadImage(book_photo_name);

                IBinder token = findViewById(android.R.id.content).getWindowToken();
                InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(token, 0);
                Toast.makeText(this, "The book has been added successfully", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(this, "Something went wrong, please try again!", Toast.LENGTH_LONG).show();
            }
        });

    }



    // this function is triggered when
    // the Select Image Button is clicked
    public void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }


    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                filePath = data.getData();

                if (null != filePath) {
                    // update the preview image in the layout

                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore
                                .Images
                                .Media
                                .getBitmap(
                                        getContentResolver(),
                                        filePath);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    book_img.setImageBitmap(bitmap);


                }
            }
        }
    }

    // UploadImage method
    private void uploadImage(String book_photo_name) {
        if (filePath != null) {

            // Defining the child of storageReference
            StorageReference ref
                    = storageRef.getReference()
                    .child(
                            "images/"
                                    + book_photo_name);

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath);




        }


    }
}