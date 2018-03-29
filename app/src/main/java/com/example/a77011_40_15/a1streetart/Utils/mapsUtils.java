package com.example.a77011_40_15.a1streetart.Utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import java.util.Map;

import static android.content.Context.LOCATION_SERVICE;

public class mapsUtils {
    public long MIN_DISTANCE_CHANGE_FOR_UPDATES = 5; // en mètres
    public long MIN_TIME_BW_UPDATES = 1000 * 60 * 2;

    public void LocalizeMe(Context context, Map)
    {
        LocationManager locationManager;
        Location location;

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            mMap.setMyLocationEnabled(true);

            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

            if (locationManager != null)
            {

                // status GPS
                boolean checkGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

                // network provider
                boolean checkNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if (checkGPS)
                {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) this);
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                } else if (checkNetwork)
                {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) this);
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                } else
                {
                    locationManager.requestLocationUpdates(
                            LocationManager.PASSIVE_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) this);
                    location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                }

                if (locationManager != null)
                {
                    if (location != null)
                    {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        LatLng moi = new LatLng(latitude, longitude);
                        LatLng paris = new LatLng(48.859489, 2.320582);
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(paris, Constantes.ZOOM));
                    }
                }

            }
        }

    }
}
