package dev.tci.registroactividades;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import dev.tci.registroactividades.Modelos.Muestro;
import dev.tci.registroactividades.Singleton.Persistencia;
import dev.tci.registroactividades.Singleton.Principal;

public class register extends AppCompatActivity {

    private LinearLayout danoLaytou;
    private EditText huerta, productor, telefono, toneladas_aprox, cal32, cal36, cal40, cal48, cal60, cal70, cal84, cal96, calCAN, calLAC,
            danoRONA, danoROSADO, danoBANO, danoTRIPS, danoQUEMADO, danoCOMEDOR, danoVIRUELA, danoVARICELA;
    private int sumaCalibres = 0;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    Date date = new Date();
    String fecha = dateFormat.format(date);
    String UID;
    String hora = java.text.DateFormat.getTimeInstance().format(Calendar.getInstance().getTime());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Muestreo");
        setSupportActionBar(toolbar);

        Init();
        int record = getIntent().getExtras().getInt("record");

        calLAC.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count > 0){
                    if( Integer.valueOf(s.toString()) > 0){
                        danoLaytou.setVisibility(View.VISIBLE);
                    }
                }else{
                    danoLaytou.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.check:
                if(!isValidateCalibres()){
                    Toast.makeText(getApplicationContext(), "La suma de los calibres debe de ser 100 \nTu total es de: " + sumaCalibres + "\nFecha: " + hora, Toast.LENGTH_LONG).show();
                }else{
                    Muestro m = new Muestro();
                    m.setCal32(Integer.valueOf(cal32.getText().toString()));
                    m.setCal36(Integer.valueOf(cal36.getText().toString()));
                    m.setCal40(Integer.valueOf(cal40.getText().toString()));
                    m.setCal48(Integer.valueOf(cal48.getText().toString()));
                    m.setCal60(Integer.valueOf(cal60.getText().toString()));
                    m.setCal70(Integer.valueOf(cal70.getText().toString()));
                    m.setCal84(Integer.valueOf(cal84.getText().toString()));
                    m.setCal96(Integer.valueOf(cal96.getText().toString()));
                    m.setCanica(Integer.valueOf(calCAN.getText().toString()));
                    m.setLacrado(Integer.valueOf(calLAC.getText().toString()));

                    Principal p = Principal.getInstance();
                    p.databaseReference.child("Acopio").child("RV").child("UsuariosAcopio").child("666").child("agendavisitas").child("H000264161203112031").child("muestreovisita").setValue(m);

                }
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void Init(){
        huerta = findViewById(R.id.txtHuerta);
        productor = findViewById(R.id.txtProductor);
        telefono = findViewById(R.id.txtTelefono);
        toneladas_aprox = findViewById(R.id.txtToneladas);
        cal32 = findViewById(R.id.txtCal32);
        cal36 = findViewById(R.id.txtCal36);
        cal40 = findViewById(R.id.txtCal40);
        cal48 = findViewById(R.id.txtCal48);
        cal60 = findViewById(R.id.txtCal60);
        cal70 = findViewById(R.id.txtCal70);
        cal84 = findViewById(R.id.txtCal84);
        cal96 = findViewById(R.id.txtCal96);
        calCAN = findViewById(R.id.txtCalCAN);
        calLAC = findViewById(R.id.txtCalLAC);
        danoRONA = findViewById(R.id.txtDanoRona);
        danoROSADO = findViewById(R.id.txtDanoRosado);
        danoBANO = findViewById(R.id.txtDanoBano);
        danoTRIPS = findViewById(R.id.txtDanoTrips);
        danoQUEMADO = findViewById(R.id.txtDanoQuemado);
        danoCOMEDOR = findViewById(R.id.txtDanoComedor);
        danoVIRUELA = findViewById(R.id.txtDanoViruela);
        danoVARICELA = findViewById(R.id.txtDanoVaricela);
        danoLaytou = findViewById(R.id.layout_danos);
        UID = getIntent().getExtras().getString("UID");
    }

    public void guardar(){

    }

    public boolean isValidateCalibres(){
        sumaCalibres = 0;
        if(!cal32.getText().toString().isEmpty()){sumaCalibres += Integer.valueOf(cal32.getText().toString());}

        if(!cal36.getText().toString().isEmpty()){sumaCalibres += Integer.valueOf(cal36.getText().toString());}

        if(!cal40.getText().toString().isEmpty()){sumaCalibres += Integer.valueOf(cal40.getText().toString());}

        if(!cal48.getText().toString().isEmpty()){sumaCalibres += Integer.valueOf(cal48.getText().toString());}

        if(!cal60.getText().toString().isEmpty()){sumaCalibres += Integer.valueOf(cal60.getText().toString());}

        if(!cal70.getText().toString().isEmpty()){sumaCalibres += Integer.valueOf(cal70.getText().toString());}

        if(!cal84.getText().toString().isEmpty()){sumaCalibres += Integer.valueOf(cal84.getText().toString());}

        if(!cal96.getText().toString().isEmpty()){sumaCalibres += Integer.valueOf(cal96.getText().toString());}

        if(!calCAN.getText().toString().isEmpty()){sumaCalibres += Integer.valueOf(calCAN.getText().toString());}

        if(!calLAC.getText().toString().isEmpty()){sumaCalibres += Integer.valueOf(calLAC.getText().toString());}

        if(sumaCalibres != 100){
            return false;
        }
        isValidateCabecera();
        return true;
    }

    public void isValidateCabecera(){
        if(huerta.getText().toString().isEmpty()){huerta.setError("Es requerido");}

        if(productor.getText().toString().isEmpty()){productor.setError("Es requerido");}

        if(telefono.getText().toString().isEmpty()){telefono.setError("Es requerido");}

        if(toneladas_aprox.getText().toString().isEmpty()){toneladas_aprox.setError("Es requerido");}

    }


}
