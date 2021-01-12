package com.mad.emottionanalysis.activity;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.mad.emottionanalysis.R;
import com.mad.emottionanalysis.view.MainActivityFragment;


public class MainActivity extends AppCompatActivity {
    private static final String[] PERMISSION_EXTERNAL_STORAGE = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int REQUEST_EXTERNAL_STORAGE = 100;
    public static final int TAKE_PHOTO = 1;
    public static final int SELECTED_PHOTO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyStoragePermissions(this);
        setContentView(R.layout.activity_main);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                // this will start the Fragment
                MainActivityFragment mainActivityFragment;
                mainActivityFragment=MainActivityFragment.newInstance();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.add(R.id.main_activity_frame, mainActivityFragment);
                transaction.commit();
            }
        });
    }

    /**
     * Dynamic Authority check method
     *
     * @param activity current activity
     */
    private void verifyStoragePermissions(Activity activity) {
        int permissionWrite = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionWrite != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSION_EXTERNAL_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

}
