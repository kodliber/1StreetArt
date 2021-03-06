package com.example.a77011_40_15.a1streetart.Fragments;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.Fragment;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.a77011_40_15.a1streetart.R;
import com.example.a77011_40_15.a1streetart.Utils.Constantes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.content.Context.LOCATION_SERVICE;


/**
 * ce fragment affiche une Google Map
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GoogleMapsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GoogleMapsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoogleMapsFragment extends Fragment implements OnMapReadyCallback, LocationListener {

    MapView mapView;
    static GoogleMap mMap;
    Context context;
    View v;

    //    private int ZOOM = 20;
    private OnFragmentInteractionListener mListener;
    private LocationManager locationManager;
    private long MIN_DISTANCE_CHANGE_FOR_UPDATES = 5; // en mètres
    private long MIN_TIME_BW_UPDATES = 1000 * 60 * 2;
    double latitude;
    double longitude;
    private Location location;

    public GoogleMapsFragment()
    {
        // Required empty public constructor
    }

    /**
     * "constructeur évolué" : ici on peut passer le zoom, en utilisant les paramètres.
     */
    public static GoogleMapsFragment newInstance()
    {
        GoogleMapsFragment fragment = new GoogleMapsFragment();
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
        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_google_maps, container, false);
        v = inflater.inflate(R.layout.fragment_google_maps, container, false);

        mapView = (MapView) v.findViewById(R.id.mapView);
//        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume(); // important
        mapView.getMapAsync(this);
        return v;
    }

/*    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri)
    {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

    //    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
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

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomGesturesEnabled(true);

        //TODO fred remarque on pourrait utiliser la fonction utilitaire localizeme mais le fragment ne serait pas independant... a etudier

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);

            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

            if (locationManager != null) {

                // status GPS
                boolean checkGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

                // network provider
                boolean checkNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if (checkGPS) {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) this);
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    Toast.makeText(context,"GPS ok" ,Toast.LENGTH_SHORT).show();


                } else if (checkNetwork) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) this);
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    Toast.makeText(context,"Carrier ok" ,Toast.LENGTH_SHORT).show();

                } else {
                    locationManager.requestLocationUpdates(
                            LocationManager.PASSIVE_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) this);
                    location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                    Toast.makeText(context,"Default ok" ,Toast.LENGTH_SHORT).show();

                }

                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    LatLng moi = new LatLng(latitude, longitude);
                    LatLng paris = new LatLng(48.859489, 2.320582);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(paris, Constantes.ZOOM));
                }
                else {
                    Log.e(Constantes.MYLOGTAG, "Localization failed");
                    Toast.makeText(context,R.string.warningNoPosition ,Toast.LENGTH_SHORT).show();
//                    Snackbar.make(v, R.string.warningNoPosition,Snackbar.LENGTH_SHORT);
                }
            }
        }
    }

    @Override
    public void onLocationChanged(Location location)
    {
        mMap.clear();// efface les markers précédents
        LatLng moi = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(moi).title("here"));
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(moi, ZOOM));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(moi, Constantes.ZOOM));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle)
    {

    }

    @Override
    public void onProviderEnabled(String s)
    {

    }

    @Override
    public void onProviderDisabled(String s)
    {

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
//        void onFragmentInteraction(Uri uri);
//        void walkTo();
    }

    public void walkTo(double latitude, double longitude)
    {
        LatLng test = new LatLng(latitude, longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(test, Constantes.ZOOM));
    }

    public void walkTo(LatLng test, String nom)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(test, Constantes.ZOOM));
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(test).title(nom));

    }

}
