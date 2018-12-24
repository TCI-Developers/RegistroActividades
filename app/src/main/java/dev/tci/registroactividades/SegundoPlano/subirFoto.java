package dev.tci.registroactividades.SegundoPlano;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import dev.tci.registroactividades.Singleton.Principal;

public class subirFoto extends Service {
    Principal p;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        p.databaseReference.child("Acopio").child("RV").child("seundoPlano").setValue(""+startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "Servicio destruido", Toast.LENGTH_SHORT).show();
    }
}
