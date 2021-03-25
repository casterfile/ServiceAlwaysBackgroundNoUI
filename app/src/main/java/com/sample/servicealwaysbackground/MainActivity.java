//This will start an app without any interface

package com.sample.servicealwaysbackground;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button button;
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("MyActivity", "Service Data Output ");
        startService(new Intent(getApplicationContext(),MyServices.class));
        isStoragePermissionGranted();

        //This will close the app after lunch
//        finish();
//        System.exit(0);

        //Sample Interface with button
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LuanchApp("com.android.chrome");
                startService(new Intent(getApplicationContext(),MyServices.class));
            }
        });
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("MyActivity","Permission is granted");
                return true;
            } else {

                Log.v("MyActivity","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("MyActivity","Permission is granted");
            return true;
        }
    }

}