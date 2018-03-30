package com.example.a77011_40_15.a1streetart.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.a77011_40_15.a1streetart.R;
import com.example.a77011_40_15.a1streetart.Utils.Constantes;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // zoom

        TextView defaultzoom;
                defaultzoom = findViewById(R.id.defaultzoom);
//                int test = Constantes.ZOOM;
        defaultzoom.setText(String.valueOf(Constantes.ZOOM));
        defaultzoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
//                Constantes.ZOOM = defaultzoom.getText();
            }
        });
    }
}
