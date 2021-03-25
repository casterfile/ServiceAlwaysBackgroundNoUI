//This will start an app without any interface

package com.sample.servicealwaysbackground;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button button;
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("MyActivity", "Service Data Output ");
        startService(new Intent(getApplicationContext(),MyServices.class));

        //This will close the app after lunch
        finish();
        System.exit(0);

        //Sample Interface with button
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LuanchApp("com.android.chrome");

            }
        });
    }

}