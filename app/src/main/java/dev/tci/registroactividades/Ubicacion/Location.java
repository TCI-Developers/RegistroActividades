package dev.tci.registroactividades.Ubicacion;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class Location  implements LocationListener {
    private Context ctx ;
    private LocationManager locationManager;
    private String proveedor;
    private boolean networkOn;

    @SuppressLint("MissingPermission")
    public Location(Context ctx) {
        this.ctx = ctx;
        locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        proveedor  = LocationManager.NETWORK_PROVIDER;
        networkOn = locationManager.isProviderEnabled(proveedor);
        locationManager.requestLocationUpdates(proveedor,10000,10,this);
        getLocation();
    }

    private void getLocation()
    {
        if(networkOn)
        {
            @SuppressLint("MissingPermission") android.location.Location lc = locationManager.getLastKnownLocation(proveedor);
            if (lc !=null)
            {
                StringBuilder builder = new StringBuilder();
                builder
                        .append("Altitud :").append(lc.getAltitude())
                        .append("Longitud : ").append(lc.getLongitude());
                Toast.makeText(ctx,builder.toString(),Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        Log.d("TOMAS","CAMBIO");
        getLocation();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        networkOn = locationManager.isProviderEnabled(proveedor);
    }

    @Override
    public void onProviderDisabled(String provider) {
        networkOn = locationManager.isProviderEnabled(proveedor);
    }
}
