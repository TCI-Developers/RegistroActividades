package dev.tci.registroactividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import dev.tci.registroactividades.Adapter.Recycler;
import dev.tci.registroactividades.Modelos.Huertas;

public class ListaActividades extends AppCompatActivity {
    private ArrayList<Huertas> listadoHuertas;
    private Recycler adapter;
    private RecyclerView recyclerView;
    private Huertas h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_actividades);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        toolbar.setTitle("Actividades");
        setSupportActionBar(toolbar);

        Init();

        ArrayList<String> valor = getIntent().getExtras().getStringArrayList("Huertas");
        for(int i=0; i < valor.size(); i++ ){
            h = new Huertas();
            h.setNombreHuerta(valor.get(i));
            listadoHuertas.add(h);
        }

        adapter = new Recycler(listadoHuertas, new RecyclerViewClick() {
            @Override
            public void onClick(View v, int position) {

            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    public void Init(){
        listadoHuertas = new ArrayList<>();
        recyclerView = findViewById(R.id.recylcer_actividades);
    }

}
