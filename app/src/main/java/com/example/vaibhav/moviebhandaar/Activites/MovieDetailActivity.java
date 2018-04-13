package com.example.vaibhav.moviebhandaar.Activites;

import android.app.DownloadManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.vaibhav.moviebhandaar.Model.Movie;
import com.example.vaibhav.moviebhandaar.R;
import com.example.vaibhav.moviebhandaar.Util.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieDetailActivity extends AppCompatActivity {
    private Movie movie;
    private TextView movieTitle,movieYear,director,category,actors,rating,writers,plot,boxOffice;
    private ImageView movieImage;
    private RequestQueue queue;
    private String movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        queue= Volley.newRequestQueue(this);
        movie=(Movie)getIntent().getSerializableExtra("movie");
        movieId=movie.getImdbId();
        setUpUI();
        getMovieDetails(movieId);
    }
    private void setUpUI() {

        movieTitle = (TextView) findViewById(R.id.movietitleidDets);
        movieImage = (ImageView) findViewById(R.id.movieimageIDDet);
        movieYear = (TextView) findViewById(R.id.moviereleaseIDDets);
        director = (TextView) findViewById(R.id.directedByDet);
        category = (TextView) findViewById(R.id.movieCatIDDet);
        rating = (TextView) findViewById(R.id.movieRatingIDDet);
        writers = (TextView) findViewById(R.id.writersDet);
        plot = (TextView) findViewById(R.id.plotDet);
        boxOffice = (TextView) findViewById(R.id.boxOfficeDet);
        actors = (TextView) findViewById(R.id.actorsDet);
    }
    private void getMovieDetails(String id) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Constants.URL + id+Constants.API_KEY, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    if (response.has("Ratings")) {
                        JSONArray ratings = response.getJSONArray("Ratings");

                        String source = null;
                        String value = null;
                        if (ratings.length() > 0) {

                            JSONObject mRatings = ratings.getJSONObject(ratings.length() - 1);
                            source = mRatings.getString("Source");
                            value = mRatings.getString("Value");
                            rating.setText(source + " : " + value);

                        }else {
                            rating.setText("Ratings: N/A");
                        }

                        movieTitle.setText(response.getString("Title"));
                        movieYear.setText("Released: " + response.getString("Released"));
                        director.setText("Director: " + response.getString("Director"));
                        writers.setText("Writers: " + response.getString("Writer"));
                        plot.setText("Plot: " + response.getString("Plot"));
                        actors.setText("Actors: " + response.getString("Actors"));

                        Picasso.with(getApplicationContext())
                                .load(response.getString("Poster"))
                                .into(movieImage);

                        boxOffice.setText("Box Office: " + response.getString("BoxOffice"));

                    }



                }catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error:", error.getMessage());

            }
        });
        queue.add(jsonObjectRequest);

    }

}
