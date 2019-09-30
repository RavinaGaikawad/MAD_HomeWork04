package com.example.homework04_api25;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.example.homework04_api25.MainActivity.KEY_MOVIELIST;
import static com.example.homework04_api25.R.*;

public class ShowListByYear extends AppCompatActivity {

    TextView tv_title;
    TextView tv_static_mby;
    TextView tv_description;
    TextView tv_genre;
    TextView tv_rating;
    TextView tv_year;
    TextView tv_imdb;
    ImageView iv_first;
    ImageView iv_last;
    ImageView iv_previous;
    ImageView iv_next;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_show_list_by_year);

        tv_title = findViewById(id.tv_title);
        tv_description = findViewById(id.tv_description);
        tv_genre = findViewById(id.tv_genre);
        tv_rating = findViewById(id.tv_rating);
        tv_year = findViewById(id.tv_year);
        tv_imdb = findViewById(id.tv_imdb);
        iv_first = findViewById(id.iv_first);
        iv_last = findViewById(id.iv_last);
        iv_next = findViewById(id.iv_next);
        iv_previous = findViewById(id.iv_previous);
        tv_static_mby = findViewById(id.tv_static_mby);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if ("com.example.homework04_api25.intent.action.VIEW".equals(action) && type != null) {
            if ("array/moviebyyear".equals(type)) {
                handleMovieByYear(intent); // Handle View for Movies By Year
            } else if ("array/moviebyrating".equals(type)) {
                handleMovieByRating(intent); // Handle single image being sent
            }
        }
    }

    void handleMovieByYear(Intent intent) {
        setTitle("Movies By Year");
        ArrayList<Movie> sharedText = intent.getParcelableArrayListExtra(KEY_MOVIELIST);
        Collections.sort(sharedText,new YearComp());
        // TODO: Sort List by Year
        if (sharedText != null) {
            count = 0;
            tv_title.setText(sharedText.get(0).getMovieName());
            tv_description.setText(sharedText.get(0).getDescription());
            tv_genre.setText(sharedText.get(0).getGenre());
            tv_rating.setText(String.valueOf(sharedText.get(0).getRating()));
            tv_year.setText(String.valueOf(sharedText.get(0).getYear()));
            tv_imdb.setText(sharedText.get(0).getImdb());

            iv_first.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count = 0;
                    tv_title.setText(sharedText.get(0).getMovieName());
                    tv_description.setText(sharedText.get(0).getDescription());
                    tv_genre.setText(sharedText.get(0).getGenre());
                    tv_rating.setText(String.valueOf(sharedText.get(0).getRating()));
                    tv_year.setText(String.valueOf(sharedText.get(0).getYear()));
                    tv_imdb.setText(sharedText.get(0).getImdb());
                }
            });


            iv_last.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count = sharedText.size() - 1;
                    tv_title.setText(sharedText.get(count).getMovieName());
                    tv_description.setText(sharedText.get(count).getDescription());
                    tv_genre.setText(sharedText.get(count).getGenre());
                    tv_rating.setText(String.valueOf(sharedText.get(count).getRating()));
                    tv_year.setText(String.valueOf(sharedText.get(count).getYear()));
                    tv_imdb.setText(sharedText.get(count).getImdb());
                }
            });

            iv_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(count + 1 == sharedText.size()){
                        Toast.makeText(ShowListByYear.this, "Last Movie Reached", Toast.LENGTH_SHORT);
                    } else {
                        count = count + 1;
                        tv_title.setText(sharedText.get(count).getMovieName());
                        tv_description.setText(sharedText.get(count).getDescription());
                        tv_genre.setText(sharedText.get(count).getGenre());
                        tv_rating.setText(String.valueOf(sharedText.get(count).getRating()));
                        tv_year.setText(String.valueOf(sharedText.get(count).getYear()));
                        tv_imdb.setText(sharedText.get(count).getImdb());
                    }
                }
            });

            iv_previous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(count - 1 < 0){
                        Toast.makeText(ShowListByYear.this, "This is the First Movie", Toast.LENGTH_SHORT);
                    } else {
                        count = count - 1;
                        tv_title.setText(sharedText.get(count).getMovieName());
                        tv_description.setText(sharedText.get(count).getDescription());
                        tv_genre.setText(sharedText.get(count).getGenre());
                        tv_rating.setText(String.valueOf(sharedText.get(count).getRating()));
                        tv_year.setText(String.valueOf(sharedText.get(count).getYear()));
                        tv_imdb.setText(sharedText.get(count).getImdb());
                    }
                }
            });

        }
    }

    void handleMovieByRating(Intent intent) {ArrayList<Movie> sharedText = intent.getParcelableArrayListExtra(KEY_MOVIELIST);
        setTitle("Movies By Rating");
        ArrayList<Movie> RatingMovie = intent.getParcelableArrayListExtra(KEY_MOVIELIST);
        Collections.sort(RatingMovie,new RatingComp());

        tv_static_mby.setText(string.movies_by_rating);
        // TODO: Sort List by Year
        if (sharedText != null) {
            count = 0;
            tv_title.setText(RatingMovie.get(0).getMovieName());
            tv_description.setText(RatingMovie.get(0).getDescription());
            tv_genre.setText(RatingMovie.get(0).getGenre());
            tv_rating.setText(String.valueOf(RatingMovie.get(0).getRating()));
            tv_year.setText(String.valueOf(RatingMovie.get(0).getYear()));
            tv_imdb.setText(RatingMovie.get(0).getImdb());

            iv_first.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count = 0;
                    tv_title.setText(RatingMovie.get(0).getMovieName());
                    tv_description.setText(RatingMovie.get(0).getDescription());
                    tv_genre.setText(RatingMovie.get(0).getGenre());
                    tv_rating.setText(String.valueOf(RatingMovie.get(0).getRating()));
                    tv_year.setText(String.valueOf(RatingMovie.get(0).getYear()));
                    tv_imdb.setText(RatingMovie.get(0).getImdb());
                }
            });


            iv_last.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count = RatingMovie.size() - 1;
                    tv_title.setText(RatingMovie.get(count).getMovieName());
                    tv_description.setText(RatingMovie.get(count).getDescription());
                    tv_genre.setText(RatingMovie.get(count).getGenre());
                    tv_rating.setText(String.valueOf(RatingMovie.get(count).getRating()));
                    tv_year.setText(String.valueOf(RatingMovie.get(count).getYear()));
                    tv_imdb.setText(RatingMovie.get(count).getImdb());
                }
            });

            iv_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(count + 1 == RatingMovie.size()){
                        Toast.makeText(ShowListByYear.this, "Last Movie Reached", Toast.LENGTH_SHORT);
                    } else {
                        count = count + 1;
                        tv_title.setText(RatingMovie.get(count).getMovieName());
                        tv_description.setText(RatingMovie.get(count).getDescription());
                        tv_genre.setText(RatingMovie.get(count).getGenre());
                        tv_rating.setText(String.valueOf(RatingMovie.get(count).getRating()));
                        tv_year.setText(String.valueOf(RatingMovie.get(count).getYear()));
                        tv_imdb.setText(RatingMovie.get(count).getImdb());
                    }
                }
            });

            iv_previous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(count - 1 < 0){
                        Toast.makeText(ShowListByYear.this, "This is the First Movie", Toast.LENGTH_SHORT);
                    } else {
                        count = count - 1;
                        tv_title.setText(RatingMovie.get(count).getMovieName());
                        tv_description.setText(RatingMovie.get(count).getDescription());
                        tv_genre.setText(RatingMovie.get(count).getGenre());
                        tv_rating.setText(String.valueOf(RatingMovie.get(count).getRating()));
                        tv_year.setText(String.valueOf(RatingMovie.get(count).getYear()));
                        tv_imdb.setText(RatingMovie.get(count).getImdb());
                    }
                }
            });

        }
    }

    class RatingComp implements Comparator<Movie>{
        @Override
        public int compare(Movie e1, Movie e2) {
            if(e1.getRating() > e2.getRating()){
                return 1;
            } else {
                return -1;
            }
        }
    }

    class YearComp implements Comparator<Movie>{
        @Override
        public int compare(Movie e1, Movie e2) {
            if(e1.getYear() < e2.getYear()){
                return 1;
            } else {
                return -1;
            }
        }
    }
}
