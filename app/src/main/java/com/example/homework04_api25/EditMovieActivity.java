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

public class EditMovieActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText et_name;
    EditText et_description;
    Spinner sp_genrelist;
    SeekBar sb_rating;
    EditText et_year;
    EditText et_imdb;
    int progress = 0;
    List<String> genresList;
    String genreSelected;
    String movieName;
    String description;
    int year;
    String imdb;
    String genre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);

        setTitle("Edit Movie");
        et_name = findViewById(R.id.et_name);
        et_description = findViewById(R.id.et_description);
        sp_genrelist = findViewById(R.id.sp_genrelist);
        sb_rating = findViewById(R.id.sb_rating);
        et_year = findViewById(R.id.et_year);
        et_imdb = findViewById(R.id.et_imdb);
        sb_rating.setMax(5);

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

        //Code for spinner for genre
        Spinner spinner = findViewById(R.id.sp_genrelist);
        //spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genresList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        final Bundle extrasFromMain = getIntent().getExtras().getBundle(MainActivity.KEY_MOVIELIST);

        Movie moviebundle = (Movie) extrasFromMain.getSerializable(MainActivity.KEY_MOVIELIST);

        Log.d("bagh" , "movie bundle "+moviebundle.toString());
        //set data for edit
        et_name.setText(moviebundle.movieName);
        et_description.setText(moviebundle.description);
        et_year.setText(""+ moviebundle.year);
        et_imdb.setText(""+moviebundle.imdb);
        sb_rating.setProgress(moviebundle.rating);
        progress = moviebundle.rating;
        genre = moviebundle.genre;

        Log.d("bagh","genre: "+moviebundle.genre);

        int spinnerPosition = dataAdapter.getPosition(genre);
        spinner.setSelection(spinnerPosition);

        //Code for spinner for genre
        spinner.setOnItemSelectedListener(this);

        //seekbar code
        sb_rating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                Log.d("bagh", "Progress bar: " +progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        findViewById(R.id.bt_addmovie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //add edit code
                Log.d("bagh" , "Inside edit button");

                movieName = et_name.getText().toString();
                description = et_description.getText().toString();
                year = Integer.parseInt(et_year.getText().toString());
                imdb = et_imdb.getText().toString();

                Movie movie = new Movie();
                movie.movieId = moviebundle.movieId;
                movie.movieName = movieName;
                movie.description = description;
                movie.genre = genreSelected;
                movie.rating = progress;
                movie.year = year;
                movie.imdb = imdb;

                Log.d("bagh", movie.toString());

                Intent intent = new Intent();
                intent.putExtra(MainActivity.KEY_MOVIELISTRESULT, movie);
                setResult(RESULT_OK, intent);
                Toast.makeText(EditMovieActivity.this, "returning ADD movie", Toast.LENGTH_SHORT).show();
                finish();
            }
        });



    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        genreSelected = adapterView.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
