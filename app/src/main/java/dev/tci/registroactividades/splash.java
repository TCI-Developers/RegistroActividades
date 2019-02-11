package dev.tci.registroactividades;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import dev.tci.registroactividades.Singleton.Principal;

public class splash extends AppCompatActivity {
    String fecha;
    SimpleDateFormat dateFormat;
    Principal p = Principal.getInstance();
    Date date;
    private static final String[] PERMISOS = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int REQUEST_CODE = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        int leer = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE);
        int leer2 = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        int leer3 = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
        int leer4 = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (leer == PackageManager.PERMISSION_DENIED || leer2 == PackageManager.PERMISSION_DENIED || leer3 == PackageManager.PERMISSION_DENIED || leer4 == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(splash.this, PERMISOS, REQUEST_CODE);
        }
        super.onCreate(savedInstanceState);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date = new Date();
        fecha = dateFormat.format(date);

        setContentView(R.layout.content_splash);

        start();
        fullscreen();
        try{
            p.InicializarFirebase();
        }catch (Exception e){

        }
    }
    public void start(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Intent intent = new Intent(splash.this, MainActivity.class);
                    intent.putExtra("FECHA", fecha );
                    startActivity(intent);
                    finish();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        },5000);
    }
    private void fullscreen(){
        int newUI = getWindow().getDecorView().getSystemUiVisibility();
        if(Build.VERSION.SDK_INT >= 14){newUI ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;}
        if(Build.VERSION.SDK_INT >= 16){newUI ^= View.SYSTEM_UI_FLAG_FULLSCREEN;}
        if(Build.VERSION.SDK_INT >= 18){newUI ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;}
        getWindow().getDecorView().setSystemUiVisibility(newUI);
    }
}
