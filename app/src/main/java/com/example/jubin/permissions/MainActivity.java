package com.example.jubin.permissions;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.audiofx.BassBoost;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    //only write permission needed no need of read
    private static final int REQUEST_WRITE = 125;
    private static final int REQUEST_READ = 226;
    private static final int REQUEST_ALL = 336;


    private static final int TXT_WRITE = 1;
    private static final int TXT_READ = 2;

    private PermissionUtil permissionUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        permissionUtil = new PermissionUtil(this);

        // check3();
        Button btn1 = (Button) findViewById(R.id.write);
        Button btn2 = (Button) findViewById(R.id.read);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check1();
            }
        });

    }



    private void check1() {

        if (checkPermission(TXT_READ) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                showPermissionExplained(TXT_READ);
            } else if (!permissionUtil.checkPermission("read")) {
                requestPermision(TXT_READ);
                permissionUtil.updatePermission("read");
            } else {
                Toast.makeText(this, "Allow storage permission", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                this.startActivity(intent);
            }
        } else {
            Toast.makeText(this, "Read permission granted", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(intent);
        }
    }

    private void check() {
        if (checkPermission(TXT_WRITE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                showPermissionExplained(TXT_WRITE);
            } else if (!permissionUtil.checkPermission("write")) {
                requestPermision(TXT_WRITE);
                permissionUtil.updatePermission("write");
            } else {
                Toast.makeText(this, "Allow storage permission", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                this.startActivity(intent);
            }
        } else {
            Toast.makeText(this, "Storage permission granted", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Read permission granted", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(intent);
        }

    }

    //check permission is granted or not

    private int checkPermission(int permission) {
        int status = PackageManager.PERMISSION_DENIED;

        switch (permission) {
            case TXT_WRITE:
                status = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;
            case TXT_READ:
                status = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
                break;

        }
        return status;
    }

    private void requestPermision(int permission) {
        switch (permission) {
            case TXT_WRITE:
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE);
                break;
            case TXT_READ:
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ);
                break;

        }


    }

    public void GroupPermission(ArrayList<String> permission)

    {
        String[] permissionList=new String[permission.size()];
        permission.toArray(permissionList);
        ActivityCompat.requestPermissions(MainActivity.this,permissionList,REQUEST_ALL);


    }


    private void showPermissionExplained(final int permission)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);

        if(permission==TXT_WRITE)
        {
            builder.setMessage("App need to access your device sdcard");
            builder.setTitle("Storage permission");
        }
        if(permission==TXT_READ)
        {
            builder.setMessage("App need to access your device sdcard");
            builder.setTitle("Read permission");
        }

        builder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(permission==TXT_WRITE)
                    requestPermision(TXT_WRITE);

                else
                if(permission==TXT_READ)
                    requestPermision(TXT_READ);

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

}
