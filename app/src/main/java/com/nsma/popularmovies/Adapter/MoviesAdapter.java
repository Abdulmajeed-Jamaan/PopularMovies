package com.nsma.popularmovies.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nsma.popularmovies.Models.Movie;
import com.nsma.popularmovies.R;
import com.nsma.popularmovies.Utilities.NetworkUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder>{

    private ArrayList<Movie> mData;
    private Context mContext;
    private ItemClickListener mClickListener;


    public MoviesAdapter(Context mContext ,ArrayList<Movie> mData) {

        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.grid_movie_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        String poster = NetworkUtils.buildUrlImage(mData.get(i).getPosterPath()).toString();
        Picasso.get()
                .load(poster)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(viewHolder.posterImage);


        viewHolder.title.setText(mData.get(i).getTitle());
        viewHolder.popularity.setText(mData.get(i).getPopularity()+"");
        viewHolder.stars.setRating((float)(mData.get(i).getVoteAVG()/2));



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView posterImage;
        TextView title , popularity;
        RatingBar stars ;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            posterImage = itemView.findViewById(R.id.poster_image);

            title = itemView.findViewById(R.id.title);

            popularity = itemView.findViewById(R.id.popularity);

            stars = itemView.findViewById(R.id.rating);
            stars.setNumStars(5);

        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(getAdapterPosition());

        }
    }



    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(int movieIndex);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public void setMoviesArray(ArrayList<Movie> array) {
        this.mData = array;
    }
}
