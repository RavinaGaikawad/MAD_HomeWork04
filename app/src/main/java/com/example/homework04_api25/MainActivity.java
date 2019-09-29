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
                Log.d("bagh", "Inside edit button"+ Arrays.toString(movieList.toArray()));
                Log.d("bagh","Movie List size" + movieList.size());
                Log.d("bagh","Movie List first name" + movieList.get(0).movieName);

                movienames = new String[movieNames.size()];
                movienames = movieNames.toArray(movienames);

                if(movieList.size() > 0)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Pick a movie")
                            .setItems(movienames, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    Toast.makeText(MainActivity.this, movienames[i], Toast.LENGTH_SHORT).show();

                                    Movie movie =  new Movie();
                                    movie = movieList.get(i);

                                    Intent editmovieintent = new Intent(MainActivity.this, EditMovieActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable(KEY_MOVIELIST, movie);
                                    editmovieintent.putExtra(KEY_MOVIELIST, bundle);
                                    startActivityForResult(editmovieintent, REQ_CODE);
                                    //new EditMovieActivity.EditMovieAsyncTask(movie).execute();

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
        if(requestCode == REQ_CODE)
        {
            if ( resultCode == RESULT_OK ){
                //Log.d("bagh", "on activity result return");
                Movie movie= (Movie) data.getExtras().getSerializable(MainActivity.KEY_MOVIELISTRESULT);

                //check if it already exists
                if(movieList.size() == 0){
                    Log.d("bagh" , "first movie id: ");
                    movie.movieId = 1;
                    movieList.add(movie);
                    movieNames.add(movie.movieName);
                }
                else {
                    /*for(Movie carnet : movieList) {
                        if(carnet.getMovieId().equals(movie.movieId)) {
                            ;
                        }
                    }*/
//                    if(movieList.contains(movie.movieId)){
//                        //
//
//                    }
//                    else{
//                        //
//
//                    }
                    // then
                    Movie maxId = movieList
                            .stream()
                            .max(Comparator.comparing(Movie::getMovieId))
                            .orElseThrow(NoSuchElementException::new);

                    Toast.makeText(this, "max id" + maxId.movieId, Toast.LENGTH_SHORT).show();
                    Log.d("bagh" , "max movie id: "+maxId.movieId);

                    movie.movieId = maxId.movieId + 1;
                    movieList.add(movie);
                    movieNames.add(movie.movieName);
                }



                Log.d("bagh", Arrays.toString(movieList.toArray()));
                Log.d("bagh", "result of added movie name" + movieNames.get(0));

            }
            else if(resultCode == RESULT_CANCELED){
                Toast.makeText(MainActivity.this, "Some Error Ocurred. (Main Activity)", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
