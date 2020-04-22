package com.example.permissionutil;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.pateldeepdev.android_permission.PermissionHandler;
import com.pateldeepdev.android_permission.Permissions;

import java.util.ArrayList;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Permissions.check(this,Manifest.permission.CALL_PHONE)){
            Toast.makeText(MainActivity.this, "Phone granted.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this, "Phone denied", Toast.LENGTH_SHORT).show();
        }
    }

    public void requestPhone(View view) {
        Permissions.request(this, Manifest.permission.CALL_PHONE, null, new PermissionHandler() {
            @Override
            public void onGranted() {
                Toast.makeText(MainActivity.this, "Phone granted.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void requestCameraAndStorage(View view) {
        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        Permissions.request(this, permissions, null, null, new PermissionHandler() {
            @Override
            public void onGranted() {
                Toast.makeText(MainActivity.this, "Camera+Storage granted.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void requestLocation(View view) {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
        String rationale = "Please provide location permission so that you can ...";
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Info")
                .setSettingsDialogTitle("Warning");

        Permissions.request(this, permissions, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {
                Toast.makeText(MainActivity.this, "Location granted.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "Location denied.", Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void openSettings(View view) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", getPackageName(), null));
        startActivity(intent);
    }
}
