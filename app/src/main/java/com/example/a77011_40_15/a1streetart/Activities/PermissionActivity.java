package com.example.a77011_40_15.a1streetart.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.a77011_40_15.a1streetart.R;

import java.util.ArrayList;

/**
 * Cette activite a pour role de fixer les permissions pour les versions d'android 6+
 */
public class PermissionActivity extends AppCompatActivity
{
    ArrayList permissions;
    ArrayList<String> permissionRequest;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        permissions = new ArrayList();
        permissionRequest = new ArrayList<>();

        permissions.add(Manifest.permission.CAMERA);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.INTERNET);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_NETWORK_STATE);

        callPermission();
    }

    private void callPermission()
    {

        for (int i = 0; i < permissions.size(); i++)
        {

            String key = permissions.get(i).toString();

            if (ContextCompat.checkSelfPermission(context, key) != PackageManager.PERMISSION_GRANTED)
            {
                permissionRequest.add(key);
            }
        }

        if (permissionRequest.size() > 0)
        {
            String[] permissionString = new String[permissionRequest.size()];

            for (int i = 0; i < permissionRequest.size(); i++)
            {
                permissionString[i] = permissionRequest.get(i);
            }

            ActivityCompat.requestPermissions(PermissionActivity.this, permissionString, 100);
        }
        else
        {
            gotoHome();
        }
    }

    /**
     * ajouter autant de 'requestCode' que necessaire
     */
    @Override
    public void onRequestPermissionsResult(int RequestCode, String permission[], int[] PermissionResult)
    {
        switch (RequestCode)
        {
            case 100:
            {
                //utilisation spécifique en fonction de l'application
                gotoHome();
            }
            break;
        }
    }

    private void gotoHome()
    {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
