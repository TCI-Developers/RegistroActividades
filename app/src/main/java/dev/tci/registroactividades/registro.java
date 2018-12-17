package dev.tci.registroactividades;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

public class registro extends AppCompatActivity {

    private GridLayout danoLaytou;
    private EditText huerta, productor, telefono, toneladas_aprox, cal32, cal36, cal40, cal48, cal60, cal70, cal84, cal96, calCAN, calLAC,
            danoRONA, danoROSADO, danoBANO, danoTRIPS, danoQUEMADO, danoCOMEDOR, danoVIRUELA, danoVARICELA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Init();

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
        danoLaytou = findViewById(R.id.gridDano);

    }

    public void CheckRegister(View v){

    }
}
