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
import dev.tci.registroactividades.Modelos.AgendaVisitas;
import dev.tci.registroactividades.Modelos.Huertas;
import dev.tci.registroactividades.Modelos.Registro;
import dev.tci.registroactividades.Singleton.Principal;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> listHuertas;
    private ArrayList<String> listProductores;
    private ArrayList<Integer> record;
    String UID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Init();
        ListarHuertas();
    }

    private void ListarHuertas() {
        Principal p = Principal.getInstance();
        p.InicializarFirebase();
                p.databaseReference
                .child("Acopio")
                .child("RV")
                .child("UsuariosAcopio")
                .child("666")
                .child("agendavisitas")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listHuertas.clear();
                listProductores.clear();
                record.clear();

                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()){
                    AgendaVisitas ag = objSnaptshot.getValue(AgendaVisitas.class);

                    UID = objSnaptshot.getKey();
                    listHuertas.add( ag.getHuerta() );
                    listProductores.add(ag.getProductor());
                    record.add(ag.getRecord());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void Init() {
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
            intent.putExtra("UID", UID );
            startActivity(intent);
            //finish();
        }
    }
}
