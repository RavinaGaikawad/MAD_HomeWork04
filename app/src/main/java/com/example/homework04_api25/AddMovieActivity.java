package com.example.homework04_api25;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class AddMovieActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText et_name;
    EditText et_description;
    Spinner sp_genrelist;
    SeekBar sb_rating;
    EditText et_year;
    EditText et_imdb;
    ArrayList<Movie> movieList;
    int progress = 0;
    List<String> genresList;
    String genreSelected;
    String movieName;
    String description;
    int year;
    String imdb;
    boolean isErrorPresent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        setTitle("Add Movie");
        et_name = findViewById(R.id.et_name);
        et_description = findViewById(R.id.et_description);
        sp_genrelist = findViewById(R.id.sp_genrelist);
        sb_rating = findViewById(R.id.sb_rating);
        et_year = findViewById(R.id.et_year);
        et_imdb = findViewById(R.id.et_imdb);
        sb_rating.setMax(5);

        // SeekBar
        sb_rating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                Log.d("bagh", "Progress Bar: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        //Code for spinner for genre
        Spinner spinner = findViewById(R.id.sp_genrelist);
        spinner.setOnItemSelectedListener(this);

        //Spinner dropdown elements
        genresList = new ArrayList<String>();
        genresList.add(0, "Action");
        genresList.add(1, "Animation");
        genresList.add(2, "Comedy");
        genresList.add(3, "Documentary");
        genresList.add(4,"Family");
        genresList.add(5, "Horror");
        genresList.add(6, "Crime");
        genresList.add(7, "Others");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genresList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


        //logic on button click
        findViewById(R.id.bt_addmovie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //add movie code
                Log.d("bagh" , "Inside add button");

                movieName = et_name.getText().toString();
                description = et_description.getText().toString();
                year = Integer.parseInt(et_year.getText().toString());
                imdb = et_imdb.getText().toString();

                if(movieName.length() > 50) {
                    et_name.setError("Movie name should not exceed 50 characters.");
                    Toast.makeText(AddMovieActivity.this, "Movie name should not exceed 50 characters.", Toast.LENGTH_SHORT).show();
                    isErrorPresent = true;
                }
                else {
                    isErrorPresent = false;
                }

                if(description.length() > 1000 || String.valueOf(year).length() > 4 || movieName.length() > 50
                        || movieName.length() == 0 || description.length() == 0 || String.valueOf(year).length() == 0 || imdb.length() == 0 || genreSelected == "select") {
                    isErrorPresent = true;

                    if(movieName.length() == 0){
                        et_name.setError("Please provide a movie name.");
                        Toast.makeText(AddMovieActivity.this, "Please provide a movie name.", Toast.LENGTH_SHORT).show();
                    }

                    if(description.length() == 0){
                        et_description.setError("Please provide a movie description.");
                        Toast.makeText(AddMovieActivity.this, "Please provide a movie description.", Toast.LENGTH_SHORT).show();
                    }

                    if(imdb.length() == 0){
                        et_imdb.setError("Please provide a movie imdb.");
                        Toast.makeText(AddMovieActivity.this, "Please provide a movie imdb.", Toast.LENGTH_SHORT).show();
                    }

                    if(genreSelected == "select"){
                        Toast.makeText(AddMovieActivity.this, "Please provide a movie genre.", Toast.LENGTH_SHORT).show();
                    }

                    if(String.valueOf(year).length() == 0){
                        et_name.setError("Please provide a movie year.");
                        Toast.makeText(AddMovieActivity.this, "Please provide a movie year.", Toast.LENGTH_SHORT).show();
                    }

                    if(movieName.length() > 50) {
                        et_name.setError("Movie name should not exceed 50 characters.");
                        Toast.makeText(AddMovieActivity.this, "Movie name should not exceed 50 characters.", Toast.LENGTH_SHORT).show();
                    }

                    if(description.length() > 1000) {
                        et_description.setError("Movie description should not exceed 1000 characters.");
                        Toast.makeText(AddMovieActivity.this, "Movie description should not exceed 1000 characters.", Toast.LENGTH_SHORT).show();
                    }

                    if(String.valueOf(year).length() > 4)
                    {
                        et_year.setError("Invalid year");
                        Toast.makeText(AddMovieActivity.this, "Invalid year.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    isErrorPresent = false;
                }


                if(!isErrorPresent){
                    Movie movie = new Movie();
                    movie.setMovieName(movieName);
                    movie.setDescription(description);
                    movie.setGenre(genreSelected);
                    movie.setRating(progress);
                    movie.setYear(year);
                    movie.setImdb(imdb);

                    Log.d("bagh", movie.toString());

                    Intent intent = new Intent();
                    intent.putExtra(MainActivity.KEY_MOVIELISTRESULT, movie);
                    setResult(RESULT_OK, intent);
                    Toast.makeText(AddMovieActivity.this, "Returning ADD movie", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        genreSelected = adapterView.getItemAtPosition(position).toString();
        Log.d("bagh", "Drop down: " +genreSelected);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
