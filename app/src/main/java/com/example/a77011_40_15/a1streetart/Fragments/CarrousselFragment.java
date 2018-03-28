package com.example.a77011_40_15.a1streetart.Fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a77011_40_15.a1streetart.Entities.Article;
import com.example.a77011_40_15.a1streetart.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Un "Carroussel"
 * Affiche en ecran Home sous la carte.
 * Son rôle est d'afficher une série d'images cliquable.
 * Le clic sur image dirige l'utilisateur sur le chemin depuis sa position vers le streetart.
 * <p>
 * Il s'agit d'un viewpager, qui a donc besoin de fragments.
 * <p>
 * NOTE : ce carroussel doit rester en V4 si l'on veut l'afficher dans le viewpager.
 * <p>
 * Activities that contain this fragment must implement the
 * {@link CarrousselFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CarrousselFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarrousselFragment extends Fragment
{
    private OnFragmentInteractionListener mListener;

    ViewPager viewPager;
    MyPageAdapter myPageAdapter;
    // les fragments qui vont défiler
    ArticleFragment first;
    ArticleFragment second;
    ArticleFragment third;
    ArticleFragment fourth;
    ArrayList<Fragment> fl = new ArrayList<>();

    Context context;
    private int NUM_ITEMS = 4;
    private Activity activity;

    public CarrousselFragment()
    {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CarrousselFragment newInstance()
    {
        CarrousselFragment fragment = new CarrousselFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_carroussel, container, false);

        //TODO generation de fragments de test. A remplacer par un telechargement d'images depuis un serveur

        String[] imageNames = {"image1", "image2", "image3", "image4"};
        String[] imageDesc = {"fleurs1\najouté par Bob", "Tigre\npar Julie", "Le tiequar\npar SuperMax", "Soleil\npar Andrea"};

//        first =  new ArticleFragment();
//        second = new ArticleFragment();
//        third = new ArticleFragment();
//        fourth = new ArticleFragment();

        // generation de fragments en fonction du nombre d'images
        if (imageNames.length > 0)
        {
            for (int i = 0; i < imageNames.length; i++)
            {
                ArticleFragment t = ArticleFragment.newInstance(imageNames[i], imageDesc[i]);
                fl.add(t);
            }
        }
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        myPageAdapter = new MyPageAdapter(getChildFragmentManager());
        viewPager.setAdapter(myPageAdapter);

        return v;
    }

    private class MyPageAdapter extends FragmentPagerAdapter
    {
        public MyPageAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
//            switch (position) {
//                case 0:
//                    return first;
//                case 1:
//                    return second;
//                case 2:
//                    return third;
//                case 3:
//                    return fourth;
//            }
//            return null;
            return fl.get(position);
        }

        @Override
        public int getCount()
        {
            return NUM_ITEMS;
        }


        @Override
        public CharSequence getPageTitle(int position)
        {
            String title = "test de titre";
            switch (position)
            {

                case 0:
                    title = "1";
                    break;

                case 1:
                    title = "2";
                    break;

                case 2:
                    title = "3";
                    break;

                case 3:
                    title = "4";
                    break;
            }
            return title;
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri)
    {
        if (mListener != null)
        {
            mListener.onFragmentInteraction(uri);
        }
    }

        //ceci fait planter
/*        @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/
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
        this.activity = activity;
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


}
