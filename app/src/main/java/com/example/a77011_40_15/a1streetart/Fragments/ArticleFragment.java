package com.example.a77011_40_15.a1streetart.Fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a77011_40_15.a1streetart.R;

/**
 * Affichage d'une image, dans un layout personnalise.
 * Les parametres de la methode newinstance sont utilises.
 * <p>
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ArticleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ArticleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticleFragment extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_RESSOURCE = "param1";
    private static final String ARG_NOM = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    Context context;

    public ArticleFragment()
    {
        // Required empty public constructor
    }

    /**
     * Constructeur qui instancie le fragment en lui passant l'image à afficher avec
     *
     * @param ressource nom réel de la ressource graphique dans les drawable.
     * @param description       titre de l'image.
     * @return Une nouvelle instance de ArticleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ArticleFragment newInstance(String ressource, String description)
    {
        ArticleFragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_RESSOURCE, ressource);
        args.putString(ARG_NOM, description);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_RESSOURCE);
            mParam2 = getArguments().getString(ARG_NOM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_article, container, false);

        ImageView imageView;
        imageView = view.findViewById(R.id.carrousselImageView);
        imageView.setMaxHeight(200);
        imageView.setMaxWidth(200);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

        int idRessource = getDrawableResIdByName(mParam1);
        //TODO prevoir une ressource par defaut
        if (idRessource != 0)
        {
            imageView.setImageResource(idRessource);
        }

        TextView t = view.findViewById(R.id.carrousselImageTexte);
        t.setText(mParam2);

        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri)
    {
        if (mListener != null)
        {
            mListener.onFragmentInteraction(uri);
        }
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity)
    {
        this.context = activity;
        super.onAttach(activity);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        {
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
    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private int getDrawableResIdByName(String resName)
    {
        String pkg = this.context.getPackageName();
        return this.getResources().getIdentifier(resName, "drawable", pkg);
    }
}
