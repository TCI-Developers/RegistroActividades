package dev.tci.registroactividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import dev.tci.registroactividades.Adapter.Recycler;
import dev.tci.registroactividades.Modelos.AgendaVisitas;
import dev.tci.registroactividades.Modelos.FormatoCalidad;
import dev.tci.registroactividades.Modelos.Huertas;
import dev.tci.registroactividades.Singleton.Principal;

public class ListaActividades extends AppCompatActivity {
    private ArrayList<Huertas> listadoHuertas;
    private Recycler adapter;
    private RecyclerView recyclerView;
    private Huertas h;
    private ArrayList<String> UID;
    private ArrayList<FormatoCalidad> prueba;
    private String IMEI;
    Principal p = Principal.getInstance();
    FormatoCalidad ag = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_actividades);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        toolbar.setTitle("Actividades");
        setSupportActionBar(toolbar);

        Init();

        ArrayList<String> huertas = getIntent().getExtras().getStringArrayList("Huertas");
        ArrayList<String> productores = getIntent().getExtras().getStringArrayList("Productores");
        final ArrayList<Integer> recordID = getIntent().getExtras().getIntegerArrayList("record");
        UID = getIntent().getExtras().getStringArrayList("UID");
        IMEI = getIntent().getExtras().getString("IMEI");

        for(int i=0; i < huertas.size(); i++ ){
            h = new Huertas();
            h.setNombreHuerta(huertas.get(i));
            h.setNombreProductor(productores.get(i));
            listadoHuertas.add(h);
        }

        adapter = new Recycler(listadoHuertas, new RecyclerViewClick() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), register.class);
                intent.putExtra("record", recordID.get(position));
                intent.putExtra("UID", UID.get(position));
                //Toast.makeText(getApplicationContext(),"UID: "+UID.get(position) + "\nIMEI: " + IMEI, Toast.LENGTH_LONG).show();
                startActivity(intent);
                finish();
            }

            @Override
            public void onLongClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), actualizar_actividades.class);
                intent.putExtra("UID", UID.get(position));
                intent.putExtra("IME", IMEI);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    public void Init(){
        listadoHuertas = new ArrayList<>();
        UID = new ArrayList<String>();
        prueba = new ArrayList<>();
        recyclerView = findViewById(R.id.recylcer_actividades);
    }
}
