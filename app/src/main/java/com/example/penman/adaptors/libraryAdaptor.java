package com.example.penman.adaptors;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.penman.R;
import com.example.penman.globals.general_functions.Intents;
import com.example.penman.holderClasses.LibraryBook;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class libraryAdaptor extends RecyclerView.Adapter<libraryAdaptor.LibraryViewHolder> {

    private ArrayList<LibraryBook> books_list;

    public libraryAdaptor(ArrayList<LibraryBook> books_list) {
        this.books_list = books_list;
    }

    @NonNull
    @Override
    public LibraryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.library_book_view, parent, false);
        return new LibraryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryViewHolder holder, int position) {
        String title = books_list.get(position).getTitle();
        String author = books_list.get(position).getAuthor();
        String edition = books_list.get(position).getEdition();
        String category = books_list.get(position).getCategory();
        Bitmap bitmap = books_list.get(position).getBook_img();
        String document_id = books_list.get(position).getDocument_id();
        holder.title.setText(title);
        holder.author.setText(author);
        holder.edition.setText(edition);
        holder.img.setImageBitmap(bitmap);

        holder.itemView.setOnClickListener(view -> {
            Context context = view.getContext();
            Intent intent = Intents.lBookRIntent(context);
            intent.putExtra("title", title.toString());
            intent.putExtra("author", author.toString());
            intent.putExtra("edition", edition.toString());
            intent.putExtra("category", category.toString());

            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bs);

            intent.putExtra("img", bs.toByteArray());
            intent.putExtra("document_id", document_id);

            startActivity(context, intent, null);
        });

    }

    @Override
    public int getItemCount() {
        return books_list.size();
    }

    public class LibraryViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView author;
        TextView edition;


        ImageView img;

        public LibraryViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.bookTitle);
            author = itemView.findViewById(R.id.bookAuthor);
            edition = itemView.findViewById(R.id.bookEdition);
            img = itemView.findViewById(R.id.lBookListImg);

        }
    }


}
