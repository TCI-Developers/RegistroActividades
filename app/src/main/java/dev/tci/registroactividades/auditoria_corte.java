package dev.tci.registroactividades;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import dev.tci.registroactividades.Modelos.AgendaVisitas;
import dev.tci.registroactividades.Modelos.VerificacionCorte;
import dev.tci.registroactividades.R;
import dev.tci.registroactividades.Singleton.Principal;

public class auditoria_corte extends AppCompatActivity {
    RadioButton rdbtn = null;
    RadioButton rdbtn2 = null;
    RadioGroup ll = null;
    private ArrayList<String> listPreguntas;
    private ArrayList<String> listRespuestas;
    VerificacionCorte vc;
    Principal p = Principal.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auditoria_corte);

        ll = new RadioGroup(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        listPreguntas = new ArrayList<>();
        listRespuestas = new ArrayList<>();
        vc = new VerificacionCorte();

        addRadioButtons(5);
        listarPreguntas();
    }

    public void listarPreguntas(){
        p.databaseReference
                .child("Acopio")
                .child("RV")
                .child("VerificacionCorte")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        try {
                            for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()){
//                                vc = objSnaptshot.getValue(VerificacionCorte.class);
                                Map<String, Object> map = (Map<String, Object>) objSnaptshot.getValue();
                                Log.d("Firebase-Datos", "Value is: " + map);

                                for (String key :  map.keySet()) {
                                    Object value = map.get(key);
                                    if (value instanceof String) {
                                        Log.d("Data", "Valor: " + value);
                                    } else if (value instanceof Class) {
                                        Log.d("Class-Data", "Class: " + ((Class)value).getName());
                                    }
                                }

                                //listPreguntas.add(map.containsValue(vc.getPregunta()));

                                //Log.d("Firebase-Datos-List", "Value is: " + listPreguntas);
                                for (DataSnapshot obj2 : objSnaptshot.getChildren()){
                                    Map<String, Object> map2 = (Map<String, Object>) obj2.getValue();
//                                    vc = obj2.getValue(VerificacionCorte.class);
                                    listRespuestas.add(vc.getRespuestas() );
                                }
                            }
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public void addRadioButtons(int number) {
            for (int i = 0; i < number; i++) {
                rdbtn = new RadioButton(this);
                rdbtn2 = new RadioButton(this);

                rdbtn.setId(View.generateViewId());
                rdbtn.setText("SI");

                rdbtn2.setId(View.generateViewId());
                rdbtn2.setText("NO");

                ll.addView(rdbtn);
                ll.addView(rdbtn2);
                if(i < 1){
                    ((ViewGroup) findViewById(R.id.radiogroup)).addView(ll);
                }
            }

        ll.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                int selectedId = arg0.getChildCount();
                Toast.makeText(getApplicationContext(), String.valueOf(selectedId), Toast.LENGTH_LONG).show();
            }
        });
    }
}