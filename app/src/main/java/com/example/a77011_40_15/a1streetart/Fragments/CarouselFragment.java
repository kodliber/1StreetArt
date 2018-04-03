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

import com.example.a77011_40_15.a1streetart.Dao.ArticleBll;
import com.example.a77011_40_15.a1streetart.Entities.Article;
import com.example.a77011_40_15.a1streetart.Entities.Articles;
import com.example.a77011_40_15.a1streetart.R;

import java.util.ArrayList;

/**
 * Un "Carroussel"
 * <p>Affiche en ecran Home sous la carte.
 * Son rôle est d'afficher une série d'images cliquable.
 * Le clic sur image dirige l'utilisateur sur le chemin depuis sa position vers le streetart.
 * <p>
 * Il s'agit d'un ViewPager, qui a donc besoin de fragments.
 * <br>
 * NOTE : ce fragment doit rester en V4 si l'on veut l'afficher dans le viewpager. Utilliser les versions "support" du FragmentManager si besoin.
 * <p>
 * Activities that contain this fragment must implement the
 * {@link CarouselFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CarouselFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarouselFragment extends android.support.v4.app.Fragment {
    private OnFragmentInteractionListener mListener;

    ViewPager viewPager;
    MyPageAdapter myPageAdapter;
    // les fragments qui vont défiler
    ArticleFragment first;
    ArticleFragment second;
    ArticleFragment third;
    ArticleFragment fourth;
    public ArrayList<Fragment> fragmentCollection = new ArrayList<>();

    Context context;
    private int NUM_ITEMS = 4;
    private Activity activity;

    public CarouselFragment()
    {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CarouselFragment newInstance()
    {
        CarouselFragment fragment = new CarouselFragment();
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
        View view = inflater.inflate(R.layout.fragment_carousel, container, false);
        //TODO fred generation de fragments de test. A remplacer par un telechargement d'images depuis un serveur
        // generation de fragments en fonction du nombre d'images

/*
        String[] imageNames = {"image1", "image2", "image3", "image4"};
        String[] imageDesc = {"fleurs1\najouté par Bob", "Tigre\npar Julie", "Le tiequar\npar SuperMax", "Soleil\npar Andrea"};
        Double[] imageLatitude = {48.83673193, 48.84283295, 48.84644802, 48.83808778};
        Double[] imageLongitude = {2.35153525, 2.3642382, 2.37968772, 2.39170402 };


        if (imageNames.length > 0)
        {
            for (int i = 0; i < imageNames.length; i++)
            {
                ArticleFragment t = ArticleFragment.newInstance(imageNames[i], imageDesc[i], imageLatitude[i], imageLongitude[i] );
                fragmentCollection.add(t);
            }
//        }*/

        ArticleBll sqliteWorker = new ArticleBll();
        Articles pics = sqliteWorker.getAllArticles(context);

        if (pics != null) {
            for (Article article : pics) {
                ArticleFragment t;
                t = ArticleFragment.newInstance(article.getUri(), article.getDescription(), article.getLatitude(), article.getLongitude(), article.getId());
                fragmentCollection.add(t);
            }
        }
/*        for (int i = 0; i<=pics.size(); i++)
        {
            ArticleFragment t = ArticleFragment.newInstance(pics.get(i).getName(),pics.get(i).getDescription(), pics.get(i).getLatitude(), pics.get(i).getLongitude());
            fragmentCollection.add(t);
        }*/

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        myPageAdapter = new MyPageAdapter(getChildFragmentManager());
        viewPager.setAdapter(myPageAdapter);

        return view;
    }

    private class MyPageAdapter extends FragmentPagerAdapter {
        public MyPageAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            return fragmentCollection.get(position);
        }

        @Override
        public int getCount()
        {
            return fragmentCollection.size();
        }

        // TODO fred : le page title n'est pas actif, chercher pourquoi
        @Override
        public CharSequence getPageTitle(int position)
        {
            String title = "test de titre";
            switch (position) {

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
        if (mListener != null) {
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


}
