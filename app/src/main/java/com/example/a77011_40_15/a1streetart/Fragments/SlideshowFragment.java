package com.example.a77011_40_15.a1streetart.Fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a77011_40_15.a1streetart.Dao.ArticleBll;
import com.example.a77011_40_15.a1streetart.Entities.Article;
import com.example.a77011_40_15.a1streetart.Entities.Articles;
import com.example.a77011_40_15.a1streetart.R;
import com.example.a77011_40_15.a1streetart.Utils.Constantes;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * SlideShowFragment remplace Carousel dont la gestion de fragment a la volée semblait buggée.
 * Ici on utilisera un recyclerView, dont les éléments affichent l'image et ses informations
 * L'avantage du Viewpager est qu'il affiche les vues en entier alors que le recyclerview peut afficher des "bouts" de vues.
 * <p>
 * Activities that contain this fragment must implement the
 * {@link SlideshowFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SlideshowFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SlideshowFragment extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context context;
    private Activity activity;
    private Articles manyPictures;
    CustomAdapter aCustomAdapter;

    public OnFragmentInteractionListener mListener;

    public SlideshowFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SlideshowFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SlideshowFragment newInstance(String param1, String param2)
    {
        SlideshowFragment fragment = new SlideshowFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_slideshow, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerV);
        LinearLayoutManager llm = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(llm);

        aCustomAdapter = new CustomAdapter(manyPictures, context);
        recyclerView.setAdapter(aCustomAdapter);


        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();

/*        ArticleBll sqliteWorker = new ArticleBll();
        Articles pics;
        pics = sqliteWorker.getAllArticles(context);//lecture depuis la BDD

        if (pics != null)
        {
            Toast.makeText(context, "onresume", Toast.LENGTH_SHORT).show();
            for (Article article : pics)
            {
                ArticleFragment newFragment;
                newFragment = ArticleFragment.newInstance(article.getUri(), article.getDescription(), article.getLatitude(), article.getLongitude(), article.getId());
                fragmentCollection.add(newFragment);
            }
        }*/

//        getFragmentManager().beginTransaction().detach(this).attach(this).commit(); // tenter de rafraîchir le fragment

    }

    // TODO: Rename method, update argument and hook method into UI event
/*    public void onButtonPressed(Uri uri)
    {
        if (mListener != null)
        {
            mListener.onFragmentInteraction(uri);
        }
    }*/

    @TargetApi(23)
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
        if (context instanceof OnFragmentInteractionListener)
        {
            mListener = (OnFragmentInteractionListener) context;
        } else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }


    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity)
    {
        this.activity = activity;
        super.onAttach(activity);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        {
            this.context = activity.getBaseContext();
            mListener = (OnFragmentInteractionListener) activity;
            Log.e (Constantes.MYLOGTAG, "Le Listener est ok pour Android 5");
        }

    }

    /*@Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener)
        {
            mListener = (OnFragmentInteractionListener) context;
        } else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void showOnMap(LatLng l, String s);
        void refresh();
    }

    /**
     * @photouri URI vers le fichier local
     * @photoid sera concaténé avec le nom de l'image par exemple
     */
    private class CustomViewHolder extends RecyclerView.ViewHolder
    {
        private TextView photouri, photodesc, photoname, photodate, photoid;
        private double photolatitude, photolongitude;
        private ImageView imageV;
        private int photo_id;

        public CustomViewHolder(View view)
        {
            super(view);

            // TODO voir comment on peut "mettre en cache" les références aux ressources, puisque cette méthode sera appelée x fois selon le nombre de viewholders à créer
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

                    if (mListener != null)
                        mListener.showOnMap(emplacement, markerName);
                    else{
                        Log.e(Constantes.MYLOGTAG, "Revoir le mListener du fragment Slideshow");
                    }
                }
            });

        }
    }

    private class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder>
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
            View aHolder = LayoutInflater.from(context).inflate(R.layout.viewholder_slideshow, parent, false);
            return new CustomViewHolder(aHolder);
        }


        @Override
        public int getItemCount()
        {
            return liste.size();
        }
    }

    // signaler qu'on a reçu la notification que le dataset a été rafraichi
    //TODO Fred il faut mettre à jour ce dataset lorsqu'il y a une suppression, car le fragment n'est pas rechargé quand on quitte le fragment dbinspector !!!
    //soit le recharger artificiellement, soit gérer la supression qui a été effectuée depuis dbinspector.
    public void reloadDataSet(){
        Toast.makeText(context, "reload reçu", Toast.LENGTH_SHORT).show();
//        aCustomAdapter.notifyItemRemoved();
        aCustomAdapter.notifyDataSetChanged();
    }
}
