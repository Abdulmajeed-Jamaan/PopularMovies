package com.nsma.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.widget.Toast;

import com.nsma.popularmovies.Adapter.MoviesAdapter;
import com.nsma.popularmovies.Models.Movie;
import com.nsma.popularmovies.Utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.ItemClickListener{



    final static String MOVIE = "movie";

    RecyclerView mRecyclerView;
    MoviesAdapter moviesAdapter;
    ArrayList<Movie> mData ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler);

        mData= new ArrayList<>();

        if (getIntent().hasExtra(SplashScreen.MOVIES)) {

            mData = (ArrayList<Movie>) getIntent().getSerializableExtra(SplashScreen.MOVIES);

        }else{
            finish();
        }




        moviesAdapter = new MoviesAdapter(this,mData);
        moviesAdapter.setClickListener(this);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));

        mRecyclerView.setAdapter(moviesAdapter);





    }


    public void sortByPopularity(){

        mData.clear();
        moviesAdapter.notifyDataSetChanged();
        setTitle(R.string.most_popular);

        URL request = NetworkUtils.buildUrlDiscoverPopularity();
        new TMDBQueryTask().execute(request);

    }

    public void sortByTopRated(){

        mData.clear();
        moviesAdapter.notifyDataSetChanged();
        setTitle(R.string.top_rated);



        URL request = NetworkUtils.buildUrlDiscoverTopRated();
        new TMDBQueryTask().execute(request);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // COMPLETED (9) Within onCreateOptionsMenu, use getMenuInflater().inflate to inflate the menu
        getMenuInflater().inflate(R.menu.main_menu, menu);
        // COMPLETED (10) Return true to display your menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.most_popular:
                sortByPopularity();
                break;

            case R.id.top_rated:
                sortByTopRated();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int movieIndex) {

        Intent mIntent = new Intent(MainActivity.this,ShowMovie.class);
        mIntent.putExtra(MOVIE,mData.get(movieIndex));
        startActivity(mIntent);

    }


    public class TMDBQueryTask extends AsyncTask<URL, Void, String> {

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

            ArrayList<Movie> movies = new ArrayList<>();
            if (theMovieDBResult != null && !theMovieDBResult.equals("")) {

                try {
                    movies = NetworkUtils.parseMovieJSON(theMovieDBResult);



                } catch (JSONException e) {
                    e.printStackTrace();
                }


                mData = movies;
                moviesAdapter.setMoviesArray(mData);
                moviesAdapter.notifyDataSetChanged();

            }else{
                Toast.makeText(MainActivity.this,"Some Error Happen",Toast.LENGTH_LONG).show();
            }
        }
    }
}
