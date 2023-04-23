package com.example.penman.adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.penman.R;
import com.example.penman.holderClasses.Review;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class lreview_adaptor extends RecyclerView.Adapter<lreview_adaptor.ReviewViewHolder> {

    private ArrayList<Review> review_list;

    public lreview_adaptor(ArrayList<Review> review_list) {
        this.review_list = review_list;
    }

    @NotNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.library_review_view, parent, false);

        return new ReviewViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ReviewViewHolder holder, int position) {
        String name = review_list.get(position).getName();
        String comment = review_list.get(position).getComment();

        holder.name.setText(name);
        holder.comment.setText(comment);


    }

    @Override
    public int getItemCount() {
        return review_list.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView comment;


        public ReviewViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.lViewName);
            this.comment = itemView.findViewById(R.id.lViewText);

        }
    }


}
