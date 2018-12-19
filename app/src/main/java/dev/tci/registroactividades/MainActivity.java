package dev.tci.registroactividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.*;

import java.io.Serializable;
import java.util.ArrayList;

import dev.tci.registroactividades.Adapter.Recycler;
import dev.tci.registroactividades.Modelos.Huertas;
import dev.tci.registroactividades.Modelos.Registro;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ArrayList<String> listHuertas;
    private ArrayList<String> listProductores;
    private ArrayList<Integer> record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inicializarFirebase();
        ListarHuertas();
    }

    private void ListarHuertas() {
        databaseReference
                .child("Acopio")
                .child("RV")
                .child("UsuariosAcopio")
                .child("32345535466364653")
                .child("agendavisitas")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listHuertas.clear();
                listProductores.clear();
                for(DataSnapshot obj : dataSnapshot.getChildren() ){
                    String r = obj.getKey();
                    listHuertas.add(r);
                    listProductores.add(obj.getValue(Registro.class).getProductor());
                    record.add(obj.getValue(Registro.class).getRecord());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        listHuertas = new ArrayList<String>();
        listProductores= new ArrayList<String>();
        record = new ArrayList<Integer>();
    }

    public void CheckData(View v){
        if(listHuertas.size() > 0){
            Intent intent = new Intent(getApplicationContext(), ListaActividades.class);
            intent.putStringArrayListExtra("Huertas", listHuertas );
            intent.putStringArrayListExtra("Productores", listProductores );
            intent.putIntegerArrayListExtra("record", record );
            startActivity(intent);
            //finish();
        }
    }
}
