package dev.tci.registroactividades;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import dev.tci.registroactividades.Adapter.Recycler;
import dev.tci.registroactividades.Adapter.RecyclerUp;
import dev.tci.registroactividades.Modelos.FormatoCalidad;
import dev.tci.registroactividades.Singleton.Principal;

public class actualizar_actividades extends AppCompatActivity {

    private RecyclerUp adapter;
    private RecyclerView recyclerView;
    private ArrayList<FormatoCalidad> actividades;
    Principal p = Principal.getInstance();
    FormatoCalidad ag = null;
    String IMEI = "";
    String UID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_actividades);

        recyclerView = findViewById(R.id.recycler_update);
        IMEI = getIntent().getExtras().getString("IME");
        UID = getIntent().getExtras().getString("UID");
        actividades = new ArrayList<>();

        listarMuestreos(UID, IMEI);

        adapter = new RecyclerUp(actividades, new RecyclerViewClick() {
            @Override
            public void onClick(View v, int position) {
                Toast.makeText(getApplicationContext(), actividades.get(position).getHuerta(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLongClick(View v, int position) {

            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    public void listarMuestreos(String uid, String imei){
                p.databaseReference
                .child("Acopio")
                .child("RV")
                .child("UsuariosAcopio")
                .child(IMEI)
                .child("agendavisitas")
                .child(UID)
                .child("formatocalidad")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()){
                            actividades.add(ag = objSnaptshot.getValue(FormatoCalidad.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), databaseError.getDetails(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
