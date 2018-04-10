package com.example.a77011_40_15.a1streetart.Adapters;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a77011_40_15.a1streetart.Dao.ArticleBll;
import com.example.a77011_40_15.a1streetart.Entities.Article;
import com.example.a77011_40_15.a1streetart.Entities.Articles;
import com.example.a77011_40_15.a1streetart.Fragments.SlideshowFragment;
import com.example.a77011_40_15.a1streetart.R;

class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder>
{
    private Articles liste;

    // on prepare quelques trucs lors du constructeur, dont la lecture des donnees de la BDD
    public CustomAdapter(Articles arrayList, Context context)
    {
        ArticleBll bll = new ArticleBll();
        liste = arrayList;
        liste = bll.getAllArticles(context);// le tableau qui sera lie dans l'adapter avec la fonction onBindViewHolder
    }

    // liaison des donnees du tableau de photos avec les views de chaque holder
    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position)
    {
        Article unePhoto = liste.get(position);
        holder.photo_id = unePhoto.getId();
        String name = unePhoto.getName() + " " + holder.photo_id;

        holder.photoname.setText(name);

        holder.imageV.setImageURI(Uri.parse(unePhoto.getUri()));
        holder.photodesc.setText(unePhoto.getDescription());
        holder.photolatitude = unePhoto.getLatitude();
        holder.photolongitude = unePhoto.getLongitude();

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Activity activity = new Activity();
        Context context = activity.getBaseContext();
        View aHolder = LayoutInflater.from(context).inflate(R.layout.viewholder_slideshow, parent, false);
        return new CustomViewHolder(aHolder);
    }


    @Override
    public int getItemCount()
    {
        return liste.size();
    }
}