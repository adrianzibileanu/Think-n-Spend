package com.example.thinknspend;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

//import android.Manifest;
import android.Manifest;
import android.app.ActionBar;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.thinknspend.ui.main.MainFragment;
import com.example.thinknspend.ui.main.NewSpending;
import com.example.thinknspend.ui.main.UIExtras.AlertDetail;

import java.io.File;
import java.io.IOException;

import static com.example.thinknspend.ui.main.MainFragment.fun_balance;



public class MainActivity extends AppCompatActivity {

    private static final int REQUESTED_PERMISSION=1232;
    private String channel_name = "funNotif";
    private String channel_description = "Fun balance notification";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);






        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance(), "mainFragment")
                    .commitNow();
            setTitle("Home");
         //   getSupportActionBar().setDisplayShowHomeEnabled(true);
        //    getSupportActionBar().setLogo(R.mipmap.ic_launcher_round);
         //   getSupportActionBar().setDisplayUseLogoEnabled(true);
        }


    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

}

