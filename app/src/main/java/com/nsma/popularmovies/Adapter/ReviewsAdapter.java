package com.nsma.popularmovies.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nsma.popularmovies.Models.Movie;
import com.nsma.popularmovies.Models.Review;
import com.nsma.popularmovies.R;
import com.nsma.popularmovies.Utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder>{

    private ArrayList<Review> mData;
    private Context mContext;


    public ReviewsAdapter(Context mContext , ArrayList<Review> mData) {

        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_review_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {




        viewHolder.author.setText(mData.get(i).getAuthor());
        //viewHolder.rating.setText(mData.get(i).getRating());
        viewHolder.content.setText(mData.get(i).getContent());



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView author ,content;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);



            author = itemView.findViewById(R.id.author_label);
            content = itemView.findViewById(R.id.content_label);



        }

    }





    public void setMoviesArray(ArrayList<Review> array) {
        this.mData = array;
    }
}
