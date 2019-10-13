package com.example.popularfilms;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilmArrayAdapter extends ArrayAdapter<Film> {

    private Map<String, Bitmap> bitmaps = new HashMap<>();
    private Context context;
    private List<Film> films;
    public FilmArrayAdapter(Context context, List<Film> films){
        super (context, -1, films);
        this.context = context;
        this.films = films;
    }

    @Override
    public int getCount() { return films.size(); }

    private class FilmViewHolder{
        ImageView filmImageView;
        TextView filmNameTextView;
        TextView descriptionTextView;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, ViewGroup parent) {
        FilmViewHolder vh = null;
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(
                    R.layout.list_item,
                    parent,
                    false
            );
            vh = new FilmViewHolder();
            vh.filmImageView = convertView.findViewById (R.id.filmImageView);
            vh.filmNameTextView = convertView.findViewById(R.id.filmNameTextView);
            vh.descriptionTextView = convertView.findViewById(R.id.descriptionTextView);
            convertView.setTag(vh);
        }
        else
            vh = (FilmViewHolder) convertView.getTag();

        Film filmesDaVez = films.get(position);


        vh.filmNameTextView.setText(context.getString(
                R.string.film_name,
                filmesDaVez.filmName
                )
        );
        vh.descriptionTextView.setText(context.getString(
                R.string.film_description,
                filmesDaVez.description
                )
        );
        baixarImagem(filmesDaVez, vh.filmImageView);

        return convertView;
    }

    private void baixarImagem(Film filmesDaVez, ImageView filmImageView){
        new Thread( () -> {
            try {
                URL url = new URL(filmesDaVez.posterURL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream is = conn.getInputStream();
                Bitmap figura = BitmapFactory.decodeStream(is);
                ((Activity)context).runOnUiThread( () -> {filmImageView.setImageBitmap(figura);});
            }catch (IOException e){
                e.printStackTrace();
            }
        }).start();
    }
}
