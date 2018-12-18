package dev.tci.registroactividades;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.*;

import java.util.ArrayList;

import dev.tci.registroactividades.Modelos.Registro;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String fecha;
    private ArrayList<Object> listRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listRegistro = new ArrayList<Object>();
        inicializarFirebase();
    }

    private void ListarDatos() {
        databaseReference.child("Acopio").child("Bonanza").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listRegistro.clear();
                for(DataSnapshot obj : dataSnapshot.getChildren() ){
                    Object r = obj.getValue(Registro.class);
                    listRegistro.add(r);
                }
                Toast.makeText(MainActivity.this, listRegistro.size()+"", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
        Toast.makeText(MainActivity.this, databaseReference.toString(), Toast.LENGTH_LONG).show();
    }

    public void CheckData(View v){
        ListarDatos();
    }
}
