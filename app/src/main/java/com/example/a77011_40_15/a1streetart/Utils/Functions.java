package com.example.a77011_40_15.a1streetart.Utils;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;

import static android.content.Context.LOCATION_SERVICE;

public class Functions {
    public static Bitmap loadFromInternalStorage(String path, String photoName)
    {
        Bitmap bitmap = null;

        try
        {
            File f = new File(path, photoName);
            bitmap = BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (Exception e)
        {
            Log.e("Tag", e.getMessage());
        }
        return bitmap;
    }

    public static String saveToInternalStorage(Bitmap photo, String imageName, String directoryName, Context context)
    {

        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/directoryName
        File directory = cw.getDir(directoryName, Context.MODE_PRIVATE);
        // Create imageDir
        File path = new File(directory, imageName);

        FileOutputStream fileOutputStream = null;
        try
        {
            fileOutputStream = new FileOutputStream(path);
            // Use the compress method on the BitMap object to write image to the OutputStream
            int size = photo.getByteCount();
            int quality = 100;

            if (size > 1000)
            {
                quality = 80;
            }

            photo.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream);

        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                fileOutputStream.close();
            } catch (Exception e)
            {
                Log.e("Tag", e.getMessage());
            }
        }
        return directory.getAbsolutePath();
    }


}
