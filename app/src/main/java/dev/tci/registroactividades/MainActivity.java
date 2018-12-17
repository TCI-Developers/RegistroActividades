package dev.tci.registroactividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.*;

import java.util.List;

import dev.tci.registroactividades.Singleton.Principal;

public class MainActivity extends AppCompatActivity {
    Principal p = new Principal().getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void ChekcData(View v){
        if (p.listRegistro.size() > 0) {
            Intent intent = new Intent();
            intent.putExtra("Record", p.listRegistro.get(0).getRecord_id());
            startActivity(intent);
        }
    }

}
