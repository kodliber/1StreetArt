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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a77011_40_15.a1streetart.Dao.ArticleBll;
import com.example.a77011_40_15.a1streetart.Entities.Article;
import com.example.a77011_40_15.a1streetart.Entities.Articles;
import com.example.a77011_40_15.a1streetart.R;
import com.example.a77011_40_15.a1streetart.Utils.Constantes;

import java.util.Iterator;

/**
 * Fragment d'inspection de la base de donnees SQLite.
 * Il liste les photos enregistrees dans la BDD SQLite du telephone
 * <p>Lors des operations de modifcation de la BDD, il faut penser a mettre a jour la collection egalement !!</p>
 * <p>
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DbInspectorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DbInspectorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DbInspectorFragment extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Context context;
    private Activity activity = null;
    private Articles lesPhotos = null;

    private RecyclerView theRecyclerView = null;
    private CustomAdapter myAdapter;


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
        View dbinspectorView = inflater.inflate(R.layout.fragment_db_inspector, container, false);

        // preparer le recyclerview et lui fournir l'adaptateur
        theRecyclerView = dbinspectorView.findViewById(R.id.recyclerviewinspector);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        theRecyclerView.setLayoutManager(mLayoutManager);
        myAdapter = new CustomAdapter(lesPhotos, context);
        theRecyclerView.setAdapter(myAdapter);

        return dbinspectorView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    /*public void onButtonPressed(Uri uri)
    {
        if (mListener != null)
        {
//            mListener.onFragmentInteraction(uri);
//            mListener.sendRefresh(uri);
        }
    }
*/
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
//            mListener = (OnFragmentInteractionListener) this.context;
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
//        void onFragmentInteraction(Uri uri);

        void sendRefresh (Uri uri);

        void sendDataSetChanged();
    }

    //TODO fred  ne pas oublier les donnees qui manquent
    class CustomViewHolder extends RecyclerView.ViewHolder
    {
        public TextView photouri, photoid, photodesc, photoname, photolatitude, photolongitude, photodate;

        public CustomViewHolder(View view)
        {
            super(view);
            photoname = view.findViewById(R.id.dbname);
            photodesc = view.findViewById(R.id.dbdesc);
            photoid = view.findViewById(R.id.dbid);
            photouri = view.findViewById(R.id.dburi);
            photolatitude = view.findViewById(R.id.dblatitude);
            photolongitude = view.findViewById(R.id.dblongitude);
            photodate = view.findViewById(R.id.dbdate);
        }
    }

    // lancement de Google Photos
    private void PhotoStartGooglePhotos()
    {
        PackageManager pm = context.getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage("com.google.android.apps.photos");
        startActivity(intent);
    }

    // lancement du picker de medium
    private void PhotoStartPicker(View view)
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivity(intent);
    }

    public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder>
    {
        private Articles liste;

        // charger le layout du holder
        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            final View holder = LayoutInflater.from(context).inflate(R.layout.viewholder_dbinspector, parent, false);
            Log.i(Constantes.MYLOGTAG, "creation de viewholder");

            Button btnPick = holder.findViewById(R.id.btnIntentPick);
            btnPick.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    PhotoStartPicker(view);
                }
            });

            Button btnPhotos = holder.findViewById(R.id.btnIntentPhotos);
            btnPhotos.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    PhotoStartGooglePhotos();
                }
            });

            Button btnSuppress = holder.findViewById(R.id.btnSuppress);
            btnSuppress.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    int thePicId;
                    TextView t = holder.findViewById(R.id.dbid);

                    thePicId = Integer.valueOf(t.getText().toString());
                    PhotoSuppressFromDb(thePicId, context);

                    // mettre a jour la collection avec un iterator
                    Iterator<Article> iter = liste.iterator();
                    while (iter.hasNext())
                    {
                        if (iter.next().getId() == thePicId)
                        {
                            iter.remove();
//                            Log.i(Constantes.MYLOGTAG, "thePic id =" + thePicId + " was removed");
                        }
                    }

                    myAdapter.notifyItemRemoved(thePicId); // ne provoque pas un rafraichissement du recyclerView
                    myAdapter.notifyDataSetChanged();

                    // TODO FRED maintenant que la liste est à jour, il faut lancer le refresh du slideshow
                    // utilisation de l'interface pour avertir l'activity qui va relayer au slideshow
                    if (mListener != null)
                        mListener.sendDataSetChanged();
                    else
                    {
                        Log.e(Constantes.MYLOGTAG, "revoir le fonctionnement du mListener dans le fragment dbinspector");
                    }
                }
            });

            return new CustomViewHolder(holder);
        }


        // cette methode va peupler les donnees du holder avec celles du tableau de photos
        @Override
        public void onBindViewHolder(CustomViewHolder holder, int position)
        {
            Article unePhoto = liste.get(position);
            String comboIdName = unePhoto.getId() + "-" + unePhoto.getName();
            holder.photoname.setText(comboIdName);
            holder.photodesc.setText(unePhoto.getDescription());
            holder.photolatitude.setText(String.valueOf(unePhoto.getLatitude() + " " + String.valueOf(unePhoto.getLongitude())));
//            holder.photolongitude.setText(String.valueOf(unePhoto.getLongitude()));
            holder.photouri.setText(unePhoto.getUri());
            holder.photodate.setText(unePhoto.getDate());
            holder.photoid.setText(String.valueOf(unePhoto.getId()));

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

    /**
     * Suppression des donnees de l'image dans la BDD SQLite (pas du fichier physique !!)
     */
    private void PhotoSuppressFromDb(int picId, Context context)
    {
        Toast.makeText(context, "id = " + picId, Toast.LENGTH_SHORT).show();
        ArticleBll bll = new ArticleBll();
        bll.deleteArticle(picId, context);
        // Mettre a jour la collection !
//            liste.remove(picId);

//            myAdapter.notifyItemRemoved(picId);

    }

}
