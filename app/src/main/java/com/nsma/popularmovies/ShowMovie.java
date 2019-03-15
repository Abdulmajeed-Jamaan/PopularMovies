package com.nsma.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nsma.popularmovies.Models.Movie;
import com.nsma.popularmovies.Utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShowMovie extends AppCompatActivity {

ImageView poster ;
TextView title , popularity , realeseDate, overReview ;
RatingBar mRatingBar;

    Movie movie ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_movie);


        poster = findViewById(R.id.poster_image);
        title = findViewById(R.id.title);
        popularity = findViewById(R.id.popularity);
        realeseDate = findViewById(R.id.release_date);
        overReview = findViewById(R.id.over_review);
        mRatingBar = findViewById(R.id.rating);



        if (getIntent().hasExtra(MainActivity.MOVIE)) {

            movie = (Movie) getIntent().getSerializableExtra(MainActivity.MOVIE);
        }else{
            finish();

        }



        setTitle(movie.getTitle());


        String link = NetworkUtils.buildUrlImage(movie.getPosterPath()).toString();
        Picasso.get()
                .load(link)
                .into(poster);

        title.setText(movie.getTitle());
        mRatingBar.setRating((float)(movie.getVoteAVG()/2));
        popularity.setText(movie.getPopularity()+"");
        overReview.setText(movie.getOverview());
        realeseDate.setText(movie.getRelease_date());

    }






}
