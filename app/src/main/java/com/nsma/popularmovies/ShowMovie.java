package com.nsma.popularmovies;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nsma.popularmovies.Adapter.ReviewsAdapter;
import com.nsma.popularmovies.Adapter.TrailersAdapter;
import com.nsma.popularmovies.Database.AppDatabase;
import com.nsma.popularmovies.Models.Movie;
import com.nsma.popularmovies.Models.Review;
import com.nsma.popularmovies.Models.Trailer;
import com.nsma.popularmovies.Utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ShowMovie extends AppCompatActivity implements TrailersAdapter.ItemClickListener {

    private ImageView poster ;
    private TextView title , popularity , realeseDate, overReview ;
    private RatingBar mRatingBar;
    private ImageView btnFav ;

    private RecyclerView trailers,reviews;
    private TrailersAdapter mTrailAdapter;
    private ReviewsAdapter mReviewAdapter;

    private ArrayList<Trailer> mTrailers;
    private ArrayList<Review> mReviews;

    private Movie movie ;
    private AppDatabase mDB;
    private boolean isItFav = false;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);

    }

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
        btnFav = findViewById(R.id.btn_fav);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDB = AppDatabase.getInstance(this);


        if (getIntent().hasExtra(MainActivity.MOVIE)) {

            movie = (Movie) getIntent().getSerializableExtra(MainActivity.MOVIE);


        }else{
            finish();

        }



        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
               Movie mMovie = mDB.taskDao().getMovie(movie.getId());

                if (mMovie != null) {
                    isItFav = true;
                    btnFav.setImageResource(R.drawable.ic_star_filled);
                }
            }
        });

        String link = NetworkUtils.buildUrlImage(movie.getPosterPath()).toString();
        Picasso.get()
                .load(link)
                .into(poster);

        title.setText(movie.getTitle());
        mRatingBar.setRating((float)(movie.getVoteAVG()/2));
        popularity.setText(movie.getPopularity()+"");
        overReview.setText(movie.getOverview());
        realeseDate.setText(movie.getRelease_date());

        trailers = findViewById(R.id.tailer_recycler);
        reviews = findViewById(R.id.review_recycler);

        trailers.setLayoutManager(new LinearLayoutManager(this));
        reviews.setLayoutManager(new LinearLayoutManager(this));



        mTrailers = new ArrayList<>();
        mTrailAdapter = new TrailersAdapter(this,mTrailers);
        trailers.setAdapter(mTrailAdapter);

        mReviews = new ArrayList<>();
        mReviewAdapter = new ReviewsAdapter(this,mReviews);
        reviews.setAdapter(mReviewAdapter);


        mTrailAdapter.setClickListener(this);


        URL requestTrailers = NetworkUtils.buildUrlGetTrailers(movie.getId());
        new TMDBQueryTaskTrails().execute(requestTrailers);

        URL requestReviews = NetworkUtils.buildUrlGetReviews(movie.getId());
        new TMDBQueryTaskReviews().execute(requestReviews);




    }

    @Override
    public void onItemClick(int movieIndex) {


        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + mTrailers.get(movieIndex).getKey()));
        startActivity(intent);

    }

    public void addTofav(View view) {

        if (isItFav) {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDB.taskDao().deleteMovie(movie);
                }
            });
            btnFav.setImageResource(R.drawable.ic_star_empty);
            Toast.makeText(this,"Removed to favorites",Toast.LENGTH_LONG).show();

        }else{
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDB.taskDao().insertMovie(movie);
                }
            });
            btnFav.setImageResource(R.drawable.ic_star_filled);
            Toast.makeText(this,"Added from favorites",Toast.LENGTH_LONG).show();

        }

    }


    public class TMDBQueryTaskReviews extends AsyncTask<URL, Void, String> {

        // COMPLETED (2) Override the doInBackground method to perform the query. Return the results. (Hint: You've already written the code to perform the query)
        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String theMovieDBResult = null;
            try {
                theMovieDBResult = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return theMovieDBResult;
        }

        // COMPLETED (3) Override onPostExecute to display the results in the TextView
        @Override
        protected void onPostExecute(String theMovieDBResult) {

            ArrayList<Review> movies = new ArrayList<>();
            if (theMovieDBResult != null && !theMovieDBResult.equals("")) {

                try {
                    movies = NetworkUtils.parseReviewsJSON(theMovieDBResult);



                } catch (JSONException e) {
                    e.printStackTrace();

                }


                mReviews = movies;

                mReviewAdapter.setMoviesArray(mReviews);
                mReviewAdapter.notifyDataSetChanged();

            }else{
                Toast.makeText(ShowMovie.this,"Some Error Happen",Toast.LENGTH_LONG).show();
            }
        }
    }


    public class TMDBQueryTaskTrails extends AsyncTask<URL, Void, String> {

        // COMPLETED (2) Override the doInBackground method to perform the query. Return the results. (Hint: You've already written the code to perform the query)
        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String theMovieDBResult = null;
            try {
                theMovieDBResult = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return theMovieDBResult;
        }

        // COMPLETED (3) Override onPostExecute to display the results in the TextView
        @Override
        protected void onPostExecute(String theMovieDBResult) {

            ArrayList<Trailer> movies = new ArrayList<>();
            if (theMovieDBResult != null && !theMovieDBResult.equals("")) {

                try {
                    movies = NetworkUtils.parseTrailersJSON(theMovieDBResult);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mTrailers = movies;

                mTrailAdapter.setMoviesArray(mTrailers);
                mTrailAdapter.notifyDataSetChanged();

            }else{
                Toast.makeText(ShowMovie.this,"Some Error Happen",Toast.LENGTH_LONG).show();
            }
        }
    }



}
