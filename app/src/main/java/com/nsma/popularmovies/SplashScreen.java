package com.nsma.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nsma.popularmovies.Models.Movie;
import com.nsma.popularmovies.Utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class SplashScreen extends AppCompatActivity {

    final static String MOVIES = "movies";
    RelativeLayout mRelativeLayout ;
    Animation fade;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mRelativeLayout = findViewById(R.id.container);

        fade = AnimationUtils.loadAnimation(this,R.anim.fade);


        mRelativeLayout.setAnimation(fade);


        URL request = NetworkUtils.buildUrlDiscoverPopularity();
        new TMDBQueryTask().execute(request);






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

                final ArrayList<Movie> finalMovies = movies;
                fade.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                        Intent mIntent = new Intent(SplashScreen.this,MainActivity.class);
                        mIntent.putExtra(MOVIES,finalMovies);
                        startActivity(mIntent);
                        finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });






            }else{
                Toast.makeText(SplashScreen.this,"Some Error Happen",Toast.LENGTH_LONG).show();
            }
        }
    }
}
