package com.example.a77011_40_15.a1streetart.Fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a77011_40_15.a1streetart.R;
import com.example.a77011_40_15.a1streetart.Utils.Constantes;
import com.google.android.gms.maps.model.LatLng;

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
public class ArticleFragment extends Fragment {
    private static final String ARG_RESSOURCE = "param1";
    private static final String ARG_NOM = "param2";
    private static final String ARG_LATITUDE = "param3";
    private static final String ARG_LONGITUDE = "param4";
    private static final String ARG_ID = "param5";
    //fred: ajouter et gerer la date

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private double mParam3;
    private double mParam4;
    private int mParam5;

    private OnFragmentInteractionListener mListener;

    Context context;

    public ArticleFragment()
    {
        // Required empty public constructor
    }

    /**
     * Constructeur qui instancie le fragment en lui passant l'image à afficher avec
     *
     * @param ressource   nom réel de la ressource graphique dans les drawable ou URI d'un fichier sur le telephone
     * @param description description de l'image.
     * @param latitude    Latitude recuperee lors de la prise de vue ou fournie par la BDD si l'image vient du serveur
     * @param longitude   Longitude
     * @param id          ID de l'image fournie
     * @return Une nouvelle instance de ArticleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ArticleFragment newInstance(String ressource, String description, double latitude, double longitude, int id)
    {
        ArticleFragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_RESSOURCE, ressource);
        args.putString(ARG_NOM, description);
        args.putDouble(ARG_LATITUDE, latitude);
        args.putDouble(ARG_LONGITUDE, longitude);
        args.putInt(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_RESSOURCE);
            mParam2 = getArguments().getString(ARG_NOM);
            mParam3 = getArguments().getDouble(ARG_LATITUDE);
            mParam4 = getArguments().getDouble(ARG_LONGITUDE);
            mParam5 = getArguments().getInt(ARG_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_article, container, false);

        ImageView imageView;
        imageView = view.findViewById(R.id.ImageView);
        imageView.setMaxHeight(200);
        imageView.setMaxWidth(200);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

        int idRessource = getDrawableResIdByName(mParam1);
        //TODO fred prevoir une ressource par defaut. ici la ressource est trouvee par son Id
        if (idRessource != 0) {
            imageView.setImageResource(idRessource);
        } else if (mParam1 != null) {
            imageView.setImageURI(Uri.parse(mParam1));
        }

        // un clic sur l'imageView declenche un deplacement de la GoogleMap en transmettant les coordonnees
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mParam3 = getArguments().getDouble(ARG_LATITUDE);
                mParam4 = getArguments().getDouble(ARG_LONGITUDE);
                LatLng emplacement = new LatLng(mParam3, mParam4);
                String markerName = String.valueOf(mParam5);

                if (mListener != null)
                    mListener.showOnMap(emplacement, markerName);
                else
                    Log.e(Constantes.MYLOGTAG, "check the mListener");
            }
        });

        TextView t = view.findViewById(R.id.carouselImageTexte);
        t.setText(mParam2);

        TextView tid = view.findViewById(R.id.carouselImageId);
        tid.setText(String.valueOf(mParam5));

        return view;
    }


/*    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri)
    {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

    @TargetApi(23)
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void
    onAttach(Activity activity)
    {
        this.context = activity;
        super.onAttach(activity);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            this.context = activity.getBaseContext();
        }

        if (activity instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) activity;
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
//        void onFragmentInteraction(String toto);
        void showOnMap(LatLng toto, String nom);

        @RequiresApi(api = Build.VERSION_CODES.N)
        default void TestPrint()
        {
//            Toast.makeText(this.getActivity(), Toast.LENGTH_LONG).show();
            Log.i("FRED", "tu es la ?");
        }

    }

    private int getDrawableResIdByName(String resName)
    {
        String pkg = this.context.getPackageName();

        return this.getResources().getIdentifier(resName, "drawable", pkg);
    }
}
