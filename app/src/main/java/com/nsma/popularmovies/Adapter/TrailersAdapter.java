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
import com.nsma.popularmovies.Models.Trailer;
import com.nsma.popularmovies.R;
import com.nsma.popularmovies.Utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.ViewHolder>{

    private ArrayList<Trailer> mData;
    private Context mContext;
    private ItemClickListener mClickListener;


    public TrailersAdapter(Context mContext , ArrayList<Trailer> mData) {

        this.mContext = mContext;
        this.mData = mData;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_trailers_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {




        viewHolder.title.setText(mData.get(i).getName());




    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title ;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);


            title = itemView.findViewById(R.id.label_trailer);


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

    public void setMoviesArray(ArrayList<Trailer> array) {
        this.mData = array;
    }
}
