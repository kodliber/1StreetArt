package com.example.a77011_40_15.a1streetart.Fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.a77011_40_15.a1streetart.Dao.ArticleBll;
import com.example.a77011_40_15.a1streetart.Entities.Article;
import com.example.a77011_40_15.a1streetart.Entities.Articles;
import com.example.a77011_40_15.a1streetart.R;

import java.util.ArrayList;

/**
 * Fragment d'inspection de la base de donnees SQLite.
 * Il liste les photos enregistrees dans la BDD SQLite du telephone
 * <p>
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DbInspectorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DbInspectorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DbInspectorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView theRecyclerView = null;

    private OnFragmentInteractionListener mListener;
    private Context context;
    private Activity activity = null;
    private Articles lesPhotos = null;

    public DbInspectorFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DbInspectorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DbInspectorFragment newInstance(String param1, String param2)
    {
        DbInspectorFragment fragment = new DbInspectorFragment();
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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View dbinspectorView = inflater.inflate(R.layout.fragment_db_inspector, container, false);
        // preparer la listview

//        ArticleBll bll = new ArticleBll();// lire les donnees depuis la BDD SQLite
//        lesPhotos = bll.getAllArticles(context); // le tableau qui sera lie dans l'adapter avec la fonction onBindViewHolder

        // preparer l'adaptateur qui va utiliser les donnees

        // preparer le viewholder personnalise : inflater son layout et lier les objets
        //TODO fred  ne pas oublier les donnees qui manquent
/*        class customViewHolder extends RecyclerView.ViewHolder {
            public TextView photouri, photoid, photodesc, photoname;

            public customViewHolder(View view)
            {
                super(view);
                photoname = view.findViewById(R.id.dbname);
                photodesc = view.findViewById(R.id.dbdesc);
                photoid = view.findViewById(R.id.dbid);
                photouri = view.findViewById(R.id.dburi);
            }
        }*/

        // preparer l'adaptater qui utilise les holders personnalises
/*
        RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
            // charger le layout du holder
            @Override
            public customViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
            {
                View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_dbinspector, parent, false);
                return new customViewHolder(holder);
            }

            // cette methode va peupler les donnees du holder avec celles du tableau de photos
            @Override
            public void onBindViewHolder(ViewHolder holder, int position)
            {
                Article unePhoto = lesPhotos.get(position);
                ((customViewHolder)holder).photodesc.setText(unePhoto.getDescription());
                ((customViewHolder)holder).photoname.setText(unePhoto.getName());
                ((customViewHolder)holder).photoid.setText(unePhoto.getName());
                ((customViewHolder)holder).photouri.setText(unePhoto.getUri());
            }

            @Override
            public int getItemCount()
            {
                return lesPhotos.size();
            }
        };
*/

        // preparer le recyclerview et lui fournir l'adaptateur
        theRecyclerView = dbinspectorView.findViewById(R.id.recyclerviewinspector);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        theRecyclerView.setLayoutManager(mLayoutManager);

        CustomAdapter customAdapter = new CustomAdapter(lesPhotos, context);
        theRecyclerView.setAdapter(customAdapter);

        return dbinspectorView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri)
    {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onAttach(Activity activity)
    {
        this.activity = activity;
        super.onAttach(activity);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            this.context = activity.getBaseContext();
        }
    }


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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    //TODO fred  ne pas oublier les donnees qui manquent
    class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView photouri, photoid, photodesc, photoname;

        public CustomViewHolder(View view)
        {
            super(view);
            photoname = view.findViewById(R.id.dbname);
            photodesc = view.findViewById(R.id.dbdesc);
            photoid = view.findViewById(R.id.dbid);
            photouri = view.findViewById(R.id.dburi);
        }
    }

    private void startPhotos()
    {
        PackageManager pm = context.getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage("com.google.android.apps.photos");
        startActivity(intent);
    }

    private void startGallery(View view)
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivity(intent);
    }

    class CustomAdapter extends RecyclerView.Adapter {

        private Articles liste;

        // charger le layout du holder
        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View holder = LayoutInflater.from(context).inflate(R.layout.viewholder_dbinspector, parent, false);
            // evenement bouton : lancer com.google.android.apps.photos

            Button btnPhotos = holder.findViewById(R.id.btnIntentPhotos);
            Button button3 = holder.findViewById(R.id.button3);
            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    startPhotos();
                }
            });

            btnPhotos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivity(intent);
                }
            });

            return new CustomViewHolder(holder);
        }

        // cette methode va peupler les donnees du holder avec celles du tableau de photos
        @Override
        public void onBindViewHolder(ViewHolder holder, int position)
        {
            Article unePhoto = liste.get(position);
            String title = unePhoto.getId() + unePhoto.getName();
            ((CustomViewHolder) holder).photodesc.setText(unePhoto.getDescription());
            ((CustomViewHolder) holder).photoname.setText(title);
//            ((CustomViewHolder)holder).photoname.setText(unePhoto.getName());
//            ((CustomViewHolder)holder).photoid.setText(String.valueOf(unePhoto.getId()));
            ((CustomViewHolder) holder).photouri.setText(unePhoto.getUri());
        }

        @Override
        public int getItemCount()
        {
            return liste.size();
        }

        private CustomAdapter(Articles arrayList, Context context)
        {
            ArticleBll bll = new ArticleBll();
            liste = arrayList;
            liste = bll.getAllArticles(context);// le tableau qui sera lie dans l'adapter avec la fonction onBindViewHolder
        }

    }

}