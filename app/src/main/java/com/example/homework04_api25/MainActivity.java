package com.example.homework04_api25;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
                                    bundle.putParcelable(KEY_MOVIELIST, movie);
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
                    Toast.makeText(MainActivity.this, "No movies to delete.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        findViewById(R.id.bt_showbyyear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(movieList.size() > 0){
                    Intent sendIntent = new Intent();
                    sendIntent.setAction("com.example.homework04_api25.intent.action.VIEW");
                    sendIntent.addCategory(Intent.CATEGORY_DEFAULT);
                    sendIntent.setType("array/moviebyyear");
                    sendIntent.putParcelableArrayListExtra(KEY_MOVIELIST, movieList);

                    // Verify that the intent will resolve to an activity
                    if (sendIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(sendIntent);
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "No Movies to view.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        findViewById(R.id.bt_showbyrating).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(movieList.size() > 0){
                Intent sendIntent = new Intent();
                sendIntent.setAction("com.example.homework04_api25.intent.action.VIEW");
                sendIntent.addCategory(Intent.CATEGORY_DEFAULT);
                sendIntent.setType("array/moviebyrating");
                sendIntent.putParcelableArrayListExtra(KEY_MOVIELIST, movieList);

                // Verify that the intent will resolve to an activity
                if (sendIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(sendIntent);
                }
                }
                else {
                    Toast.makeText(MainActivity.this, "No Movies to view.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_CODE)
        {
            if ( resultCode == RESULT_OK ){
                Movie movie = data.getExtras().getParcelable(MainActivity.KEY_MOVIELISTRESULT);
                if(movie != null) {
                    //check if it already exists
                    if(movieList.size() == 0){
                        movie.setMovieId(1);
                        movieList.add(movie);
                        movieNames.add(movie.getMovieName());
                    }
                    else {
                        for (Movie mv : movieList) {

                            if(mv.getMovieId() == movie.getMovieId())
                            {
                                Log.d("bagh 2", movie.toString());
                                Log.d("bagh 1", mv.toString());
                                mv.movieName = movie.getMovieName();
                                mv.description = movie.getDescription();
                                mv.genre = movie.getGenre();
                                mv.rating = movie.getRating();
                                mv.year = movie.getYear();
                                mv.imdb = movie.getImdb();
                                Log.d("bagh 3", mv.toString());
                                Toast.makeText(this, "mv genre returned for edit " + movie.genre, Toast.LENGTH_SHORT).show();
                                break;
                            }
                            else
                            {
                                Movie maxId = movieList
                                        .stream()
                                        .max(Comparator.comparing(Movie::getMovieId))
                                        .orElseThrow(NoSuchElementException::new);

                                movie.setMovieId(maxId.movieId + 1);
                                movieList.add(movie);
                                movieNames.add(movie.getMovieName());
                                Toast.makeText(this, "mv genre returned for add " + movie.genre, Toast.LENGTH_SHORT).show();
                                break;
                            }
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
