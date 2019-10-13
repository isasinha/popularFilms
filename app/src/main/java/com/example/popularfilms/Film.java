package com.example.popularfilms;

public class Film {

    public final String filmName;
    public final String description;
    public final String posterURL;

    public Film (String filmName, String description, String posterName){
        this.filmName = filmName;
        this.description = description;
        this.posterURL = "https://image.tmdb.org/t/p/w500/" + posterName;
    }
}
