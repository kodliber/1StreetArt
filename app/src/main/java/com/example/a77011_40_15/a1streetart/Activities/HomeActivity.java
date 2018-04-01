package com.example.a77011_40_15.a1streetart.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.a77011_40_15.a1streetart.Dao.ArticleBll;
import com.example.a77011_40_15.a1streetart.Entities.Article;
import com.example.a77011_40_15.a1streetart.Fragments.ArticleFragment;
import com.example.a77011_40_15.a1streetart.Fragments.DbInspectorFragment;
import com.example.a77011_40_15.a1streetart.Fragments.GoogleMapsFragment;
import com.example.a77011_40_15.a1streetart.R;
import com.example.a77011_40_15.a1streetart.Utils.MapsUtils;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.util.Date;
//import android.support.v4.app.FragmentActivity;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ArticleFragment.OnFragmentInteractionListener {
    Context context;
    FragmentManager fragmentManager = null;
    DbInspectorFragment dbi = null;
    Uri uri;
    ArticleBll b;

    GoogleMapsFragment googlemapFrag;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        context = this;

        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("StreetArt");
        setSupportActionBar(toolbar);

        // le FAB démarre l'application photo
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Prêt à prendre une photo ?", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();

                if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {

                    File file = getMediaFile();
                    uri = Uri.fromFile(file);

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

                } else {
                    Toast.makeText(context, "Pas de camera ?", Toast.LENGTH_LONG).show();
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getFragmentManager();
        googlemapFrag = new GoogleMapsFragment();
        loadFragment(googlemapFrag);

        // acceder aux fragment d'images


//        Fragment carroussel = new ArticleFragment();
//        Fragment carroussel = fragmentManager.findFragmentByTag("carrousselFragment");
/*
        android.support.v4.app.FragmentManager supportFragmentManager = getSupportFragmentManager();
        CarrousselFragment carroussel;
        carroussel = (CarrousselFragment) supportFragmentManager.findFragmentByTag("carrousselFragment");
        android.support.v4.app.Fragment testf = carroussel.fl.get(1);
*/

        //fred quand on clique sur la carte, on passe à l'activité du parcours

    }

    public void loadFragment(Fragment fragment, int cible)
    {
        fragmentManager.beginTransaction()
                .replace(cible, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void loadFragment(Fragment fragment)
    {
        fragmentManager.beginTransaction()
                .replace(R.id.fgtMap, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, SettingsActivity.class);

            startActivity(i);
            return true;
        }

        // ceci est un test pour la fonction walkTo vers New York
        if (id == R.id.action_login) {
            LatLng test = new LatLng(40.7143528, -74.0059731);
            googlemapFrag.walkTo(test, "NY");
            TestPrint();
            return true;
        }

        if (id == R.id.action_exit) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_map) {
            loadFragment(googlemapFrag);

        } else if (id == R.id.nav_dbinspector) {
            // afficher le fragment dbInspector
            if (dbi == null)
                dbi = new DbInspectorFragment();
//            dbi = findViewById();
            loadFragment(dbi);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * implementation de l'interface du fragment Article
     * <br>
     * un clic sur l'imageView declenche un deplacement de la carte en appelant la methode publique walkTo du fragment Map.
     *
     * @param place est un objet contenant des coordonnées GPS
     */
    @Override
    public void showOnMap(LatLng place, String nom)
    {
        googlemapFrag.walkTo(place, nom);
//        Toast.makeText(context, "o", Toast.LENGTH_LONG).show();
    }

    /**
     * Test des methodes par defaut dans les interfaces.
     * En principe disponible avec Java 8 / android SDK 7.
     * Apparemment sans resultat.
     */
    @Override
    public void TestPrint()
    {
        ArticleFragment.OnFragmentInteractionListener.super.TestPrint();
    }

    /**
     * Retour de l'application photo appelée par le <i>floating action button</i>
     * La variable Uri est peuplée dans l'intervalle, elle contient l'endroit où se trouve l'image.
     *
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            storePhotoToDb();
        }
    }

    public void storePhotoToDb()
    {
        Toast.makeText(context, uri.toString(), Toast.LENGTH_LONG).show();
            /*
            FIXMe crash probable si la clef pour l'API google est invalide.
            Possible si l'application change de signature
            Prevoir une verification de la validite de l'acces a l'API
            */
        MapsUtils.LocalizeMe(context); // geolocalisation
        // enregistrer dans la base de donnees
        Article photo = new Article();
//            Date d = new Date();

//        photo.setDate(new Date().getTime());
        photo.setDescription("essai");
        photo.setUri(uri.toString());
        photo.setName("nom");
// pas besoin de la ressource graphique qui n'est la que pour la version temporaire
//        photo.setRes(0);

        b = new ArticleBll();
        b.insertArticle(photo, context);

    }

    /**
     * cette methode est declenchee avant l'ouverture de la camera
     *
     * @return fournit un objet File a l'activite camera
     */
    private File getMediaFile()
    {
        File photostorage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File photofile = new File(photostorage, ("AppStreetArt-" + System.currentTimeMillis()) + ".jpg");

        return photofile;
    }

}
