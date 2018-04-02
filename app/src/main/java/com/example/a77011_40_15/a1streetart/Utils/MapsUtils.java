package com.example.a77011_40_15.a1streetart.Utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.Map;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Classe qui comporte des methodes dediee a la gestion de la carte Google.
 */
public abstract class MapsUtils implements LocationListener{
//    public static long MIN_DISTANCE_CHANGE_FOR_UPDATES = 5; // en mètres
//    public static long MIN_TIME_BW_UPDATES = 1000 * 60 * 2;


    /**
     * Cette méthode interroge le GPS pour obtenir la localisation actuelle et l'utiliser pour localiser la photo.
     * Pas besoin d'une carte.
     * TODO Fred: penser a gerer l'absence de connexion GPS
     *
     * @param context
     */
    public static LatLng LocalizeMe(Context context)
    {
        LocationManager locationManager;
        Location location;
        LatLng position = null;
//        GoogleMap mMap = new GoogleMap();
        double latitude = 0;
        double longitude = 0;

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
//            mMap.setMyLocationEnabled(true);

            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

            if (locationManager != null)
            {
                // statut GPS
                boolean checkGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                // statut du network provider
                boolean checkNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if (checkGPS)
                {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                } else if (checkNetwork)
                {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER, 0, 0,  locationListener);
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                } else
                    // en l'absence de GPS ou réseau actif, on tente le PASSIVE_PROVIDER
                {
                    locationManager.requestLocationUpdates(
                            LocationManager.PASSIVE_PROVIDER, 0, 0,  locationListener);
                    location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                }

                if (location != null)
                {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    position = new LatLng(latitude, longitude);
                    Toast.makeText(context, position.toString(),Toast.LENGTH_LONG).show();
                }
                else
                {
                    position = new LatLng(48.859489, 2.320582); //Paris par defaut
                }

            }
        }
        else{
            Log.w(Constantes.MYLOGTAG, "No geolocalization could be performed");
        }

        return position;

    }

    private static LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location)
        {

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
    };
}
