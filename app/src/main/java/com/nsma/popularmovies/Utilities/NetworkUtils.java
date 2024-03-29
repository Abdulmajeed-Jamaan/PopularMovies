/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nsma.popularmovies.Utilities;

import android.net.Uri;
import android.util.Log;

import com.nsma.popularmovies.BuildConfig;
import com.nsma.popularmovies.Models.Movie;
import com.nsma.popularmovies.Models.Review;
import com.nsma.popularmovies.Models.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {

    final static String TMDB_BASE_URL =
            "https://api.themoviedb.org/3/movie/";



    final static String PARAM_QUERY = "q";

    final static String PARAM_API_KEY = "api_key";

    // TODO: 15/03/19 YOUR API KEY HERE 
    final static String API_KEY_VALUE = BuildConfig.API_KEY;



    final static String PARAM_INCLUDE_ADULT = "include_adult";
    final static String PARAM_INCLUDE_VIDEO = "include_video";


    final static String PARAM_LANGUAGE = "language";
    final static String ENGLISH = "en-US";


    final static String PARAM_SORT = "sort_by";
    final static String SORTBY_POPULARITY = "popular";
    final static String SORTBY_TOP_RATED = "top_rated";

    final static String ORDER_ASC = ".asc";
    final static String ORDER_DESC = ".desc";

    final static String PARAM_PAGE = "page";


    public static URL buildUrlDiscoverPopularity() {
        Uri builtUri = Uri.parse(TMDB_BASE_URL+SORTBY_POPULARITY).buildUpon()
                .appendQueryParameter(PARAM_API_KEY, API_KEY_VALUE)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildUrlDiscoverTopRated() {
        Uri builtUri = Uri.parse(TMDB_BASE_URL+SORTBY_TOP_RATED).buildUpon()
                .appendQueryParameter(PARAM_API_KEY, API_KEY_VALUE)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    public static URL buildUrlGetTrailers(int videoId) {
        Uri builtUri = Uri.parse(TMDB_BASE_URL+videoId+"/videos").buildUpon()
                .appendQueryParameter(PARAM_API_KEY, API_KEY_VALUE)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d("URL", "buildUrlGetTrailers: "+url.toString());
        return url;
    }


    public static URL buildUrlGetReviews(int videoId) {
        Uri builtUri = Uri.parse(TMDB_BASE_URL+videoId+"/reviews").buildUpon()
                .appendQueryParameter(PARAM_API_KEY, API_KEY_VALUE)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */


    final static String TMDB_BASE_IMAGE_URL =
            "https://image.tmdb.org/t/p/w342";


    public static URL buildUrlImage(String posterPath) {
        Uri builtUri = Uri.parse(TMDB_BASE_IMAGE_URL+posterPath).buildUpon()

                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    public static ArrayList<Movie> parseMovieJSON(String json) throws JSONException {

        JSONObject movieJSON = new JSONObject(json);

        JSONArray results = movieJSON.getJSONArray("results");

        ArrayList<Movie> movieObject = new ArrayList<>();


        for (int i = 0; i < results.length(); i++) {

            movieObject.add(parseObjectJSON(results.get(i).toString()));

        }


        return movieObject;
    }

    public static ArrayList<Trailer> parseTrailersJSON(String json) throws JSONException {

        JSONObject movieJSON = new JSONObject(json);

        JSONArray results = movieJSON.getJSONArray("results");

        ArrayList<Trailer> TrailersObject = new ArrayList<>();


        for (int i = 0; i < results.length(); i++) {

            TrailersObject.add(parseTrailerJSON(results.get(i).toString()));
            Log.d("URL", "buildUrlGetTrailers: "+results.get(i).toString());

        }


        return TrailersObject;
    }




    public static Trailer parseTrailerJSON(String json) throws JSONException {

        JSONObject trailerJSON = new JSONObject(json);

        Trailer trailerObject = new Trailer();

        String id = trailerJSON.getString("id");
        trailerObject.setId(id);

        String key = trailerJSON.getString("key");
        trailerObject.setKey(key);

        String name = trailerJSON.getString("name");
        trailerObject.setName(name);


        return trailerObject;
    }


    public static ArrayList<Review> parseReviewsJSON(String json) throws JSONException {

        JSONObject movieJSON = new JSONObject(json);

        JSONArray results = movieJSON.getJSONArray("results");

        ArrayList<Review> TrailersObject = new ArrayList<>();


        for (int i = 0; i < results.length(); i++) {

            TrailersObject.add(parseReviewJSON(results.get(i).toString()));

        }


        return TrailersObject;
    }




    public static Review parseReviewJSON(String json) throws JSONException {

        JSONObject trailerJSON = new JSONObject(json);

        Review reviewObject = new Review();

        String author = trailerJSON.getString("author");
        reviewObject.setAuthor(author);

        String content = trailerJSON.getString("content");
        reviewObject.setContent(content);




        return reviewObject;
    }


    public static Movie parseObjectJSON(String json) throws JSONException {

        JSONObject movieJSON = new JSONObject(json);

        Movie movieObject = new Movie();

        int id = movieJSON.getInt("id");
        movieObject.setId(id);

        int vote_count = movieJSON.getInt("vote_count");
        movieObject.setVoteCount(vote_count);


        Double vote_average = movieJSON.getDouble("vote_average");
        movieObject.setVoteAVG(vote_average);


        String title = movieJSON.getString("title");
        movieObject.setTitle(title);


        Double popularity = movieJSON.getDouble("popularity");
        movieObject.setPopularity(popularity);


        String poster_path = movieJSON.getString("poster_path");
        movieObject.setPosterPath(poster_path);



        String overview = movieJSON.getString("overview");
        movieObject.setOverview(overview);


        String release_date = movieJSON.getString("release_date");
        movieObject.setRelease_date(release_date);

        return movieObject;
    }



    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}

