package com.example.homework04_api25;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class MainActivity extends AppCompatActivity {
    public int REQ_CODE = 1;
    public static String KEY_MOVIELIST = "MovieList";
    public static String KEY_MOVIELISTRESULT = "MovieListResult";
    public ArrayList<Movie> movieList = new ArrayList<>();
    public ArrayList<String> movieNames = new ArrayList<>();
    String[] movienames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("My Favorite Movies");

        findViewById(R.id.bt_addmovie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code for add movie
                Intent addmovieintent = new Intent(MainActivity.this, AddMovieActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(KEY_MOVIELIST, movieList);
                startActivityForResult(addmovieintent, REQ_CODE);
            }
        });

        findViewById(R.id.bt_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code for edit movie
                movienames = new String[movieNames.size()];
                movienames = movieNames.toArray(movienames);

                if(movieList.size() > 0)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Pick a movie")
                            .setItems(movienames, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    Movie movie = movieList.get(i);

                                    Intent editmovieintent = new Intent(MainActivity.this, EditMovieActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable(KEY_MOVIELIST, movie);
                                    editmovieintent.putExtra(KEY_MOVIELIST, bundle);
                                    startActivityForResult(editmovieintent, REQ_CODE);
                                }
                            });

                    final AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "No movies to edit.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        findViewById(R.id.bt_deletemovie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code for delete movie
                movienames = new String[movieNames.size()];
                movienames = movieNames.toArray(movienames);

                if(movieList.size() > 0)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Pick a movie")
                            .setItems(movienames, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    movieList.remove(i);
                                    Toast.makeText(MainActivity.this, movienames[i]+"  movie deleted successfully.", Toast.LENGTH_SHORT).show();
                                }
                            });

                    final AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "No movies to edit.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        findViewById(R.id.bt_showbyyear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code for view movie by year
            }
        });

        findViewById(R.id.bt_showbyrating).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code for view movie by rating
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        boolean moviePresent = false;
        if(requestCode == REQ_CODE)
        {
            if ( resultCode == RESULT_OK ){
                Movie movie= (Movie) data.getExtras().getSerializable(MainActivity.KEY_MOVIELISTRESULT);

                //check if it already exists
                if(movieList.size() == 0){
                    movie.movieId = 1;
                    movieList.add(movie);
                    movieNames.add(movie.movieName);
                }
                else {
                    for (Movie mv : movieList) {
                        if(mv.movieId == movie.movieId)
                        {
                            Log.d("bagh", movie.toString());
                            Log.d("bagh", mv.toString());
                            mv.movieName = movie.movieName;
                            mv.description = movie.description;
                            mv.genre = movie.genre;
                            mv.rating = movie.rating;
                            mv.year = movie.year;
                            mv.imdb = movie.imdb;
                            Log.d("bagh", mv.toString());
                            Toast.makeText(this, "mv genre returned for edit "+movie.genre, Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Movie maxId = movieList
                                    .stream()
                                    .max(Comparator.comparing(Movie::getMovieId))
                                    .orElseThrow(NoSuchElementException::new);

                            movie.movieId = maxId.movieId + 1;
                            movieList.add(movie);
                            movieNames.add(movie.movieName);
                            Toast.makeText(this, "mv genre returned for add "+movie.genre, Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            }
            else if(resultCode == RESULT_CANCELED){
                Toast.makeText(MainActivity.this, "Some Error Ocurred. (Main Activity)", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
