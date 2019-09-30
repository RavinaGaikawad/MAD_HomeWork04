package com.example.homework04_api25;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    String movieName;
    String description;
    String genre;
    int rating;
    int year;
    String imdb;
    int movieId;

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getImdb() {
        return imdb;
    }

    public void setImdb(String imdb) {
        this.imdb = imdb;
    }

    public Movie(String movieName, String description, String genre, int rating, int year, String imdb, int movieId) {
        this.movieName = movieName;
        this.description = description;
        this.genre = genre;
        this.rating = rating;
        this.year = year;
        this.imdb = imdb;
        this.movieId = movieId;
    }

    public Movie(){

    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieName='" + movieName + '\'' +
                ", description='" + description + '\'' +
                ", genre='" + genre + '\'' +
                ", rating=" + rating +
                ", year=" + year +
                ", imdb='" + imdb + '\'' +
                ", movieId=" + movieId +
                '}';
    }

    @Override
    public int describeContents() {
        return movieId;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(movieId);
        dest.writeInt(year);
        dest.writeInt(rating);
        dest.writeString(movieName);
        dest.writeString(description);
        dest.writeString(genre);
        dest.writeString(imdb);
    }

    //constructor used for parcel
    public Movie(Parcel parcel){
        this.movieId = parcel.readInt();
        this.year = parcel.readInt();
        this.rating = parcel.readInt();
        this.movieName = parcel.readString();
        this.description = parcel.readString();
        this.genre = parcel.readString();
        this.imdb = parcel.readString();
    }

    //creator - used when un-parceling our parcle (creating the object)
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){

        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[0] ;
        }
    };

/*
    @Override
    public int compare(Movie movie1, Movie movie2) {
        //return movie1.getMovieId().(movie2.getMovieId());
        movie1.getMovieId().compareTo()
        return 0;
    }
*/
}
