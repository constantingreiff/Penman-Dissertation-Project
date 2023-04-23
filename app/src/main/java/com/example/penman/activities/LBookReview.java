package com.example.penman.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.penman.globals.app_data_stores.AppSession;
import com.example.penman.holderClasses.Review;
import com.example.penman.R;
import com.example.penman.adaptors.lreview_adaptor;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LBookReview extends AppCompatActivity {

    ArrayList<Review> reviews_list;
    private FirebaseFirestore db = AppSession.Session.db;
    private RecyclerView recyclerView;
    private TextView title;
    private TextView author;
    private TextView edition;

    private Button reviewBtn;

    private Button readBookBtn;
    private TextView addReviewText;
    private String document_id;
    private ImageView bookIMG;
    private ImageView lBookBackBtn;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lbook_review);

        recyclerView = findViewById(R.id.lRevRecyclerView);
        reviewBtn = findViewById(R.id.lReviewBtn);
        readBookBtn = findViewById(R.id.lReadBookBtn);

        addReviewText = findViewById(R.id.lReviewField);

        title = findViewById(R.id.lBookTitle);
        author = findViewById(R.id.lBookAuthor);
        edition = findViewById(R.id.lBookEdition);

        bookIMG = findViewById(R.id.lBookPageImg);

        title.setText(getIntent().getStringExtra("title"));
        author.setText(getIntent().getStringExtra("author"));
        edition.setText(getIntent().getStringExtra("edition"));

        reviews_list = new ArrayList<>();

        Bitmap b = BitmapFactory.decodeByteArray(
                getIntent().getByteArrayExtra("img"),0,getIntent().getByteArrayExtra("img").length);

        bookIMG.setImageBitmap(b);
        document_id = getIntent().getStringExtra("document_id");
        lBookBackBtn = findViewById(R.id.lBookBackBtn);

        lBookBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        document_id = getIntent().getStringExtra("document_id");

        String first_name = AppSession.Session.userData.get("first_name").toString();
        String surname = AppSession.Session.userData.get("surname").toString();
        String user = first_name + " " + surname;


        db.getInstance().collection("showcase_book").document(document_id).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                List<Map<Object, String>> reviews;
                reviews = (List<Map<Object, String>>) task.getResult().get("Reviews");

                for (int i = 0; i < reviews.size(); i++) {

                    String name = reviews.get(i).get("name");
                    String comment = reviews.get(i).get("comment");
                    reviews_list.add(new Review(name, comment));
                }

                setAdapter();

            }
        });

        setAdapter();

        reviewBtn.setOnClickListener(v -> {

            addReview(user);
        });


    }

    //Adds review to database, document_id is passed from previous intent in order to identify the selected database document.
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void addReview(String user) {

        String comment = addReviewText.getText().toString();

        if ((comment.length() > 20)) {
            reviews_list.add(new Review(user, comment));

            db.getInstance().collection("showcase_book").document(document_id).update("Reviews", reviews_list).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    setAdapter();
                    addReviewText.setText("");
                    Toast.makeText(this, "Your review has been sucessfully submited", Toast.LENGTH_LONG).show();
                    IBinder token = findViewById(android.R.id.content).getWindowToken();
                    InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(token, 0);
                } else {
                    reviews_list.remove(reviews_list.size() - 1);
                }
            });
        } else {
            Toast.makeText(this, "Minimum review size is 50 characters", Toast.LENGTH_LONG).show();
        }

    }

    //Sets the adapter and recyclerView to display the queried list.
    private void setAdapter() {
        lreview_adaptor adapter = new lreview_adaptor(new ArrayList(reviews_list));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        System.out.println(adapter.getItemCount());
    }

}
