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
import android.widget.TextView;
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
    RadioGroup grupo = null;
    private ArrayList<Object> listPreguntas;
    private ArrayList<Object> listRespuestas;
    private ArrayList<Object> listRespuestas2;
    VerificacionCorte vc;
    Principal p = Principal.getInstance();
    LinearLayout layout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auditoria_corte);

        ll = new RadioGroup(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        listPreguntas = new ArrayList<>();
        listRespuestas = new ArrayList<>();
        listRespuestas2 = new ArrayList<>();

        layout = findViewById(R.id.content);

        grupo = findViewById(R.id.radiogroup);
        //addRadioButtons(5);
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

                                Map<String, Object> map = (Map<String, Object>) objSnaptshot.getValue();

                                Object value_1 = map.get("respuesta1");
                                Object value_2 = map.get("respuesta2");
                                Object value_3 = map.get("respuesta3");
                                Object value_4 = map.get("respuesta4");
                                Object value_5 = map.get("respuesta5");
                                Object value_6 = map.get("respuesta6");
                                Object value = map.get("pregunta");

                                listPreguntas.add(value);

                                if(value_1 != null){
                                    listPreguntas.add(value_1);
                                }
                                if(value_2 != null){
                                    listPreguntas.add(value_2);
                                }
                                if(value_3 != null){
                                    listPreguntas.add(value_3);
                                }else{
                                    listPreguntas.add("");
                                }
                                if(value_4 != null){
                                    listPreguntas.add(value_4);
                                }else{
                                    listPreguntas.add("");
                                }
                                if(value_5 != null){
                                    listPreguntas.add(value_5);
                                }else{
                                    listPreguntas.add("");
                                }
                                if(value_6 != null){
                                    listPreguntas.add(value_6);
                                    listPreguntas.add("");
                                }else{
                                    listPreguntas.add("");
                                }
                            }
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_LONG).show();
                        }
                        creadLabel();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { }
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

    public void creadLabel(){
        for(int  i = 0 ; i < listPreguntas.size(); i++){
            TextView textView = new TextView(this);
            textView.setText((String) listPreguntas.get(i));

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
            layout.addView(textView);
        }
    }
}