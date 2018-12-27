package dev.tci.registroactividades;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
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
    int posT = 0;
    boolean ban = false;
    private ArrayList<String> UIDActividades;
    private EditText huertaUP, productorUP, telefonoUP, toneladasUP;
    private Spinner municipioUP;
    FormatoCalidad f = new FormatoCalidad();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_actividades);

        Init();

        listarMuestreos(UID, IMEI);

        adapter = new RecyclerUp(actividades, new RecyclerViewClick() {
            @Override
            public void onClick(View v, int position) {
                huertaUP.setText(actividades.get(position).getHuerta());
                productorUP.setText(actividades.get(position).getProductor());
                telefonoUP.setText(String.valueOf(actividades.get(position).getTelefono()));
                toneladasUP.setText(String.valueOf(actividades.get(position).getTon_prox()));
                municipioUP.setSelection(actividades.get(position).getPositionMun());
                llenadoClase(position);
                posT = position;
                ban = true;
            }

            @Override
            public void onLongClick(View v, int position) {

            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manu_actualizar_actividades, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.check:
                if(isValidateCabecera()){
                    if(ban){
                        guardarDatos();
                    }else{
                        Toast.makeText(getApplicationContext(), "Debe de haber seleccionado algun registro para modificar", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Init() {
        IMEI = getIntent().getExtras().getString("IME");
        UID = getIntent().getExtras().getString("UID");

        huertaUP = findViewById(R.id.txtHuertaUp);
        productorUP = findViewById(R.id.txtProductorUp);
        telefonoUP = findViewById(R.id.txtTelefonoUp);
        toneladasUP = findViewById(R.id.txtToneladasUp);
        municipioUP = findViewById(R.id.spnMunicipioUp);

        recyclerView = findViewById(R.id.recycler_update);
        actividades = new ArrayList<>();
        UIDActividades = new ArrayList<>();
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
                            UIDActividades.add(objSnaptshot.getKey());
                            actividades.add(ag = objSnaptshot.getValue(FormatoCalidad.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), databaseError.getDetails(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void guardarDatos(){
        f.setHuerta(huertaUP.getText().toString());
        f.setProductor(productorUP.getText().toString());
        f.setTelefono(Long.valueOf(telefonoUP.getText().toString()));
        f.setTon_prox(Long.valueOf(toneladasUP.getText().toString()));
        f.setMunicipio(municipioUP.getSelectedItem().toString());
        f.setPositionMun(municipioUP.getSelectedItemPosition());

        p.databaseReference
        .child("Acopio")
        .child("RV")
        .child("UsuariosAcopio")
        .child(IMEI)
        .child("agendavisitas")
        .child(UID)
        .child("formatocalidad")
        .child(UIDActividades.get(posT))
        .setValue(f);
        finish();
        Toast.makeText(getApplicationContext(), "Se modificó el registro correctamente", Toast.LENGTH_LONG).show();
    }

    private void llenadoClase(int pos){
        f.setBano(actividades.get(pos).getBano());
        f.setComedor(actividades.get(pos).getComedor());
        f.setNcuadrillas(actividades.get(pos).getNcuadrillas());
        f.setCal32(actividades.get(pos).getCal32());
        f.setCal36(actividades.get(pos).getCal36());
        f.setCal40(actividades.get(pos).getCal40());
        f.setCal48(actividades.get(pos).getCal48());
        f.setCal60(actividades.get(pos).getCal60());
        f.setCal70(actividades.get(pos).getCal70());
        f.setCal84(actividades.get(pos).getCal84());
        f.setCal96(actividades.get(pos).getCal96());
        f.setCanica(actividades.get(pos).getCanica());
        f.setLacrado(actividades.get(pos).getLacrado());
        f.setRona(actividades.get(pos).getRona());
        f.setRosado(actividades.get(pos).getRosado());
        f.setTrips(actividades.get(pos).getTrips());
        f.setQuemado(actividades.get(pos).getQuemado());
        f.setViruela(actividades.get(pos).getViruela());
        f.setVaricela(actividades.get(pos).getVaricela());
        f.setHora(actividades.get(pos).getHora());
        f.setFecha(actividades.get(pos).getFecha());
        f.setLatitud(actividades.get(pos).getLatitud());
        f.setLongitud(actividades.get(pos).getLongitud());
        f.setUrl(actividades.get(pos).getUrl());
        f.setConcepto(actividades.get(pos).getConcepto());
        f.setCampoBitacora(actividades.get(pos).getCampoBitacora());
    }

    public boolean isValidateCabecera(){
        if(huertaUP.getText().toString().isEmpty()){
            huertaUP.setError("Es requerido");
        }else{
            if(productorUP.getText().toString().isEmpty()){
                productorUP.setError("Es requerido");
            }else{
                if(telefonoUP.getText().toString().isEmpty()){
                    telefonoUP.setError("Es requerido");
                }
                else{
                    if(toneladasUP.getText().toString().isEmpty()){
                        toneladasUP.setError("Es requerido");
                    }else{
                        if(municipioUP.getSelectedItemPosition() < 1){
                            Toast.makeText(getApplicationContext(), "Selecciona un municipio por favor.", Toast.LENGTH_LONG).show();
                        }else{
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
