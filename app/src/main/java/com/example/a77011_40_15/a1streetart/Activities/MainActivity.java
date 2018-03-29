package com.example.a77011_40_15.a1streetart.Activities;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.a77011_40_15.a1streetart.R;

/**
 * Sur l'activite d'accueil, on dirige soit vers les permissions ou bien l'application
 * Concretement nous vérifions la version de l'OS et si c'est une v6+ demander l'autorisation pour les permissions
 */
public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = null;

        if (Build.VERSION.SDK_INT >= 23)
        {
            intent = new Intent(this, PermissionActivity.class);
        }
        else
        {
            intent = new Intent(this, HomeActivity.class);
        }



        startActivity(intent);
        finish();

    }
}
