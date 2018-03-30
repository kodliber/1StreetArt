package com.example.a77011_40_15.a1streetart.Utils;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import java.security.cert.Extension;

/**
 * Un ensemble de fonctions utilitaires
 * Created by 77011-40-15 on 27/03/2018.
 */

public class Utils extends Activity {


    public static void alert(String msg, View view) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    public static void quicktoast(Context c, String s){
        Toast.makeText(c, s, Toast.LENGTH_SHORT).show();
    }

/*    private int getDrawableResIdByName(String resName)
    {
        String pkg = this.context.getPackageName();
        return this.getResources().getIdentifier(resName, "drawable", pkg);
    }*/
}
