package com.example.vaibhav.moviebhandaar.Data;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vaibhav.moviebhandaar.Activites.MovieDetailActivity;
import com.example.vaibhav.moviebhandaar.Model.Movie;
import com.example.vaibhav.moviebhandaar.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by vaibhav on 05-04-2018.
 */

public class MovieRecyclerviewAdapter extends RecyclerView.Adapter<MovieRecyclerviewAdapter.ViewHolder> {
    private Context context;
    private List<Movie> movieList;

    public MovieRecyclerviewAdapter(Context context, List<Movie> movies){
        this.context=context;
        movieList=movies;

    }

    @Override
    public MovieRecyclerviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_row,parent,false);

        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(MovieRecyclerviewAdapter.ViewHolder holder, int position) {
        Movie movie=movieList.get(position);
        String posterLink= movie.getPoster();

        holder.title.setText(movie.getTitle());
        holder.type.setText(movie.getMovieType());

        Picasso.with(context)
                .load(posterLink)
                .placeholder(android.R.drawable.ic_btn_speak_now)
                .into(holder.poster);

        holder.year.setText(movie.getYear());


    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title,year,type;
        ImageView poster;


        public ViewHolder(View itemView, final Context ctx) {
            super(itemView);
            context=ctx;
            title=(TextView)itemView.findViewById(R.id.movieTitleID);
            year=(TextView)itemView.findViewById(R.id.movieReleaseID);
            poster=(ImageView)itemView.findViewById(R.id.movieImageID);
            type=(TextView)itemView.findViewById(R.id.movieCategoryID);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //take user to next page ,description wala
                    Movie movie=movieList.get(getAdapterPosition());
                    Intent intent=new Intent(context, MovieDetailActivity.class);
                    intent.putExtra("movie",movie);
                    ctx.startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }
}
