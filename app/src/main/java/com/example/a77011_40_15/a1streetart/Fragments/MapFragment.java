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
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a77011_40_15.a1streetart.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.content.Context.LOCATION_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Context context;
    MapView mMapView;
    GoogleMap mMap;
    GoogleMap carte;

    private long MIN_DISTANCE_CHANGE_FOR_UPDATES = 5; // en mètres
    private long MIN_TIME_BW_UPDATES = 1000 * 60 * 2;

    //    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    Location location;
    private double latitude;
    private double longitude;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

//        context = this;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // affichage de Google Maps
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        View view = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                carte = mMap;

                // For showing a move to my location button
//                carte.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map
                LatLng paris = new LatLng(48, 2);
                carte.addMarker(new MarkerOptions().position(paris).title("Marker Title").snippet("Marker Description"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(paris).zoom(12).build();
                carte.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            this.context = activity.getBaseContext();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

//    public void onMapReady(GoogleMap googleMap) {
    @Override
    public void onMapReady(GoogleMap map) {
        this.mMap = map;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomGesturesEnabled(true);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);

            LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

            if (locationManager != null) {
                // status GPS
                boolean checkGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

                // network provider
                boolean checkNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if (checkGPS) {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) context);
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                } else if (checkNetwork) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) context);
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                } else {
                    locationManager.requestLocationUpdates(
                            LocationManager.PASSIVE_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) context);
                    location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                }

/*
                if (locationManager != null) {
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        LatLng user = new LatLng(latitude, longitude);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user, 15));
                    }
                } else {
                    latitude = 48.45654;
                    longitude = 2.3333;
                    LatLng paris = new LatLng(latitude, longitude);
                    mMap.addMarker(new MarkerOptions().position(paris).title("Paris"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(paris, 20));
                }
*/


            }
        }
        latitude = 48.45654;
        longitude = 2.3333;
        LatLng paris = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(paris).title("Paris"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(paris, 20));


//            googleMap.setMyLocationEnabled(true);// indicateur pour la position de l'utilisateur
/*
        LatLng sydney = new LatLng(48.45654, 2.3333);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 20));
        */
    }

//    @Override
//    public void onLocationChanged(Location location) {
//        mMap.clear();// efface les markers précédents
//        LatLng moi = new LatLng(location.getLatitude(), location.getLongitude());
//        mMap.addMarker(new MarkerOptions().position(moi).title("here"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(moi, 10));
//    }
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
