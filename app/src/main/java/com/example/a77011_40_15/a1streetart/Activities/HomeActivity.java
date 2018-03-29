package com.example.a77011_40_15.a1streetart.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
//import android.support.v4.app.FragmentActivity;

import com.example.a77011_40_15.a1streetart.Fragments.ArticleFragment;
import com.example.a77011_40_15.a1streetart.Fragments.ArticlesFragment;
import com.example.a77011_40_15.a1streetart.Fragments.CarrousselFragment;
import com.example.a77011_40_15.a1streetart.Fragments.GoogleMapsFragment;
import com.example.a77011_40_15.a1streetart.Fragments.ViewpagerFragment;
import com.example.a77011_40_15.a1streetart.R;
import com.example.a77011_40_15.a1streetart.Utils.Constantes;
import com.example.a77011_40_15.a1streetart.Utils.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ArticleFragment.OnFragmentInteractionListener {
    FragmentManager fragmentManager = null;
    Context context;
//    Fragment googlemapFrag;
    GoogleMapsFragment googlemapFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        context = this;

        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("StreetArt");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Prêt à prendre une photo ?", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
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
        //TODO quand on clique sur la carte, on passe à l'activité du parcours

//        Fragment artFrag = new ViewpagerFragment();
        //TODO charger les articles dans le fragment frtArticles
//        loadFragment(artFrag, R.id.fgtArticles);
        //TODO quand on clique sur les aricles on passe à la l'activité lecture d'articles

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


        if (id == R.id.action_login) {
            LatLng test = new LatLng(40.7143528, -74.0059731);
            googlemapFrag.walkTo(test, "NY");
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

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * ici c'est l'interface du fragment d'image qui déplace la map
     * @param toto
     */
    @Override
    public void ShowOnMap(LatLng toto, String nom)
    {
        googlemapFrag.walkTo(toto, nom);
//        Toast.makeText(context, "o", Toast.LENGTH_LONG).show();
    }
}
