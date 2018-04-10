package com.example.a77011_40_15.a1streetart.Adapters;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a77011_40_15.a1streetart.Fragments.SlideshowFragment;
import com.example.a77011_40_15.a1streetart.R;
import com.example.a77011_40_15.a1streetart.Utils.Constantes;
import com.google.android.gms.maps.model.LatLng;

public class CustomViewHolder extends RecyclerView.ViewHolder
{
    public TextView photouri, photodesc, photoname, photodate, photoid;
    public double photolatitude, photolongitude;
    public ImageView imageV;
    public int photo_id;

    public CustomViewHolder(View view)
    {
        super(view);
        photoname = view.findViewById(R.id.picturename);
        photodesc = view.findViewById(R.id.picturedescription);
        imageV = view.findViewById(R.id.imageView);
        photolatitude = 0; // fred peut-etre pas besoin d'initialiser
        photolongitude = 0;
//            photoid = view.findViewById(R.id.dbid);
//            photouri = view.findViewById(R.id.dburi);
//            photolatitude = view.findViewById(R.id.dblatitude);
//            photolongitude = view.findViewById(R.id.dblongitude);
//            photodate = view.findViewById(R.id.dbdate);

        imageV.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //appeler la methode d'interface

                LatLng emplacement = new LatLng(photolatitude, photolongitude);// il me semble que ce genre de code en javascript ne donnerait pas le meme resultat ! A moins de faire une closure
                String markerName = photoname.getText().toString();


                Activity activity = new Activity();
                Context context = activity.getBaseContext();

                FragmentManager fragmentManager = activity.getFragmentManager();

                SlideshowFragment slideshowFragment = (SlideshowFragment) fragmentManager.findFragmentById(R.id.carrousselFragment);

                if (slideshowFragment.mListener != null)
                    slideshowFragment.mListener.showOnMap(emplacement, markerName);
                else{
                    Log.e(Constantes.MYLOGTAG, "Revoir le mListener du fragment Slideshow");
                }
            }
        });

    }
}
