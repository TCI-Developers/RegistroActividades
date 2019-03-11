package dev.tci.registroactividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
import dev.tci.registroactividades.FragmentDialog.ayudaFragment;
import dev.tci.registroactividades.Modelos.AgendaVisitas;
import dev.tci.registroactividades.Modelos.FormatoCalidad;
import dev.tci.registroactividades.Modelos.Huertas;
import dev.tci.registroactividades.Singleton.Principal;

public class ListaActividades extends AppCompatActivity {
    private ArrayList<Huertas> listadoHuertas;
    private Recycler adapter;
    private RecyclerView recyclerView;
    private Huertas h;
    private ArrayList<String> UID, ref;
    private ArrayList<FormatoCalidad> prueba;
    private String IMEI, fecha1;
    Principal p = Principal.getInstance();
    FormatoCalidad ag = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_actividades);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        toolbar.setTitle("Actividades");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Init();

        ArrayList<String> huertas = getIntent().getExtras().getStringArrayList("Huertas");
        final ArrayList<Integer> recordID = getIntent().getExtras().getIntegerArrayList("record");
        ArrayList<String> fechas = getIntent().getExtras().getStringArrayList("FECHA");
        ArrayList<String> contactos = getIntent().getExtras().getStringArrayList("Contactos");
        ArrayList<String> telcontactos = getIntent().getExtras().getStringArrayList("TelContactos");
        ArrayList<String> productores = getIntent().getExtras().getStringArrayList("Productores");
        ArrayList<String> telproductores = getIntent().getExtras().getStringArrayList("TelProductores");
        ArrayList<String> HUE = getIntent().getExtras().getStringArrayList("HUE");
        UID = getIntent().getExtras().getStringArrayList("UID");
//        ref = getIntent().getExtras().getStringArrayList("ref");
        IMEI = getIntent().getExtras().getString("IMEI");

        for(int i=0; i < huertas.size(); i++ ){
            h = new Huertas();
            h.setNombreHuerta(huertas.get(i));
            h.setNombreProductor(productores.get(i));
            h.setContacto(contactos.get(i));
            h.setHUE(HUE.get(i));
            h.setTelContacto(telcontactos.get(i));
            h.setTelProductor(telproductores.get(i));
            if(fechas.get(i) == null){h.setFecha("");}
            else{h.setFecha(fechas.get(i));}
            listadoHuertas.add(h);
        }

        adapter = new Recycler(listadoHuertas, new RecyclerViewClick() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), register.class);
                intent.putExtra("record", recordID.get(position));
                intent.putExtra("UID", UID.get(position));
                intent.putExtra("HUE", listadoHuertas.get(position).getHUE());
                intent.putExtra("huerta", listadoHuertas.get(position).getNombreHuerta());
                intent.putExtra("productor", listadoHuertas.get(position).getNombreProductor());
                intent.putExtra("telproductor", listadoHuertas.get(position).getTelProductor());

                intent.putExtra("contacto", listadoHuertas.get(position).getContacto());
                intent.putExtra("telcontacto", listadoHuertas.get(position).getTelContacto());
                startActivity(intent);
                finish();
            }

            @Override
            public void onLongClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), actualizar_actividades.class);
                intent.putExtra("UID", UID.get(position));
                intent.putExtra("IME", IMEI);
                intent.putExtra("FECHA", fecha1);
//                intent.putExtra("ref", ref.get(position));
                startActivity(intent);
                finish();
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    public void Init(){
        listadoHuertas = new ArrayList<>();
        UID = new ArrayList<String>();
        ref = new ArrayList<String>();
        prueba = new ArrayList<>();
        recyclerView = findViewById(R.id.recylcer_actividades);
        fecha1 = getIntent().getExtras().getString("FECHA1");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tutorial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.ayuda:
                ayudaFragment obj = new ayudaFragment();
                obj.setCancelable(false);
                obj.show(getSupportFragmentManager(), "ayuda");
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}
