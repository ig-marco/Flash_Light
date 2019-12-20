package com.example.flashlight;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton btnOn;
    static Camera camera=null;
    Camera.Parameters parameters;
    boolean isflash=false;
    boolean isOn=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Flash Light");


        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#ff0266")));

        btnOn=findViewById(R.id.btn_On);


        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH))
        {
           camera= Camera.open();
           parameters=camera.getParameters();
            isflash=true;
        }

        btnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isflash)
                {
                    if (!isOn)
                    {
                        btnOn.setImageResource(R.drawable.image_off);
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        camera.setParameters(parameters);
                        camera.startPreview();
                        isOn=true;
                    }
                    else
                    {
                        btnOn.setImageResource(R.drawable.image);
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                        camera.setParameters(parameters);
                        camera.stopPreview();
                        isOn=false;
                    }
                }
                else{
                    AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Error...");
                    builder.setMessage("Flash not Found");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialogInterface, int i) {
                             dialogInterface.dismiss();
                         }
                     });
                    builder.create().show();
                }
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (camera!=null)
        {
            camera.release();
            camera=null;
        }
    }
}
