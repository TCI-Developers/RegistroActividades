package dev.tci.registroactividades;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import dev.tci.registroactividades.Modelos.AgendaVisitas;
import dev.tci.registroactividades.Modelos.VerificacionCorte;
import dev.tci.registroactividades.R;
import dev.tci.registroactividades.Singleton.Principal;

public class auditoria_corte extends AppCompatActivity {
    RadioButton rdbtn1,rdbtn2,rdbtn3,rdbtn4,rdbtn5,rdbtn6,rdbtn7,rdbtn8,rdbtn9,rdbtn10,rdbtn11,rdbtn12,rdbtn13,rdbtn14,rdbtn15,rdbtn16,rdbtn17,rdbtn18,rdbtn19,rdbtn20,rdbtn21,rdbtn22 ,rdbtn23
    ,rdbtn24,rdbtn25,rdbtn26,rdbtn27,rdbtn28,rdbtn29,rdbtn30,rdbtn31,rdbtn32,rdbtn33,rdbtn34,rdbtn35,rdbtn36,rdbtn37,rdbtn38,rdbtn39,rdbtn40,rdbtn41,rdbtn42,rdbtn43,rdbtn44,rdbtn45,rdbtn46,rdbtn47
    ,rdbtn48,rdbtn49,rdbtn50,rdbtn51,rdbtn52,rdbtn53,rdbtn54,rdbtn55,rdbtn56,rdbtn57,rdbtn58;
    Principal p = Principal.getInstance();
    RadioGroup radioG1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auditoria_corte);
        Init();
    }

    private void Init() {
        radioG1 = findViewById(R.id.rdg1);
        rdbtn1   = findViewById(R.id.rdb1);
        rdbtn2   = findViewById(R.id.rdb2);
        rdbtn3   = findViewById(R.id.rdb3);
        rdbtn4   = findViewById(R.id.rdb4);
        rdbtn5   = findViewById(R.id.rdb5);
        rdbtn6   = findViewById(R.id.rdb6);
        rdbtn7   = findViewById(R.id.rdb7);
        rdbtn8   = findViewById(R.id.rdb8);
        rdbtn9   = findViewById(R.id.rdb9);
        rdbtn10  = findViewById(R.id.rdb10);
        rdbtn11  = findViewById(R.id.rdb11);
        rdbtn12  = findViewById(R.id.rdb12);
        rdbtn13  = findViewById(R.id.rdb13);
        rdbtn14  = findViewById(R.id.rdb14);
        rdbtn15  = findViewById(R.id.rdb15);
        rdbtn16  = findViewById(R.id.rdb16);
        rdbtn17  = findViewById(R.id.rdb17);
        rdbtn18  = findViewById(R.id.rdb18);
        rdbtn19  = findViewById(R.id.rdb19);
        rdbtn20  = findViewById(R.id.rdb20);
        rdbtn21  = findViewById(R.id.rdb21);
        rdbtn22  = findViewById(R.id.rdb22);
        rdbtn23  = findViewById(R.id.rdb23);
        rdbtn24  = findViewById(R.id.rdb24);
        rdbtn25  = findViewById(R.id.rdb25);
        rdbtn26  = findViewById(R.id.rdb26);
        rdbtn27  = findViewById(R.id.rdb27);
        rdbtn28  = findViewById(R.id.rdb28);
        rdbtn29  = findViewById(R.id.rdb29);
        rdbtn30  = findViewById(R.id.rdb30);
        rdbtn31  = findViewById(R.id.rdb31);
        rdbtn32  = findViewById(R.id.rdb32);
        rdbtn33  = findViewById(R.id.rdb33);
        rdbtn34  = findViewById(R.id.rdb34);
        rdbtn35  = findViewById(R.id.rdb35);
        rdbtn36  = findViewById(R.id.rdb36);
        rdbtn37  = findViewById(R.id.rdb37);
        rdbtn38  = findViewById(R.id.rdb38);
        rdbtn39  = findViewById(R.id.rdb39);
        rdbtn40  = findViewById(R.id.rdb40);
        rdbtn41  = findViewById(R.id.rdb41);
        rdbtn42  = findViewById(R.id.rdb42);
        rdbtn43  = findViewById(R.id.rdb43);
        rdbtn44  = findViewById(R.id.rdb44);
        rdbtn45  = findViewById(R.id.rdb45);
        rdbtn46  = findViewById(R.id.rdb46);
        rdbtn47  = findViewById(R.id.rdb47);
        rdbtn48  = findViewById(R.id.rdb48);
        rdbtn49  = findViewById(R.id.rdb49);
        rdbtn50  = findViewById(R.id.rdb50);
        rdbtn51  = findViewById(R.id.rdb51);
        rdbtn52  = findViewById(R.id.rdb52);
        rdbtn53  = findViewById(R.id.rdb53);
        rdbtn54  = findViewById(R.id.rdb54);
        rdbtn55  = findViewById(R.id.rdb55);
        rdbtn56  = findViewById(R.id.rdb56);
        rdbtn57  = findViewById(R.id.rdb57);
        rdbtn58  = findViewById(R.id.rdb58);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_verificacion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.check_verificacion:
                saveData();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveData(){
        Map<String, Object> map = new TreeMap<>();
        rdbtn1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    rdbtn1.setText("SI");
                }else{

                }
            }
        });
        if(rdbtn1.isChecked()){
            map.put("01","YES");
            rdbtn1.setText("SI");
        }else{ map.put("01","NO");rdbtn1.setText("NO"); }

        if(rdbtn2.isChecked()){
            map.put("02","YES");
        }else{ map.put("02","NO"); }

        if(rdbtn3.isChecked()){
            map.put("03","YES");
        }else{ map.put("03","NO"); }

        if(rdbtn4.isChecked()){
            map.put("04","YES");
        }else{ map.put("04","NO"); }

        if(rdbtn5.isChecked()){
            map.put("05","YES");
        }else{ map.put("05","NO"); }

        if(rdbtn6.isChecked()){
            map.put("06","YES");
        }else{ map.put("06","NO"); }

        if(rdbtn7.isChecked()){
            map.put("07","YES");
        }else{ map.put("07","NO"); }

        if(rdbtn8.isChecked()){
            map.put("08","YES");
        }else{ map.put("08","NO"); }

        if(rdbtn9.isChecked()){
            map.put("09","YES");
        }else{ map.put("09","NO"); }

        if(rdbtn10.isChecked()){
            map.put("10","YES");
        }else{ map.put("10","NO"); }

        if(rdbtn11.isChecked()){
            map.put("11","YES");
        }else{ map.put("11","NO"); }

        if(rdbtn12.isChecked()){
            map.put("12","YES");
        }else{ map.put("12","NO"); }

        if(rdbtn13.isChecked()){
            map.put("13","YES");
        }else{ map.put("13","NO"); }

        if(rdbtn14.isChecked()){
            map.put("14","YES");
        }else{ map.put("14","NO"); }

        if(rdbtn15.isChecked()){
            map.put("15","YES");
        }else{ map.put("15","NO"); }

        if(rdbtn16.isChecked()){
            map.put("16","YES");
        }else{ map.put("17","NO"); }

        if(rdbtn18.isChecked()){
            map.put("18","YES");
        }else{ map.put("18","NO"); }

        if(rdbtn19.isChecked()){
            map.put("19","YES");
        }else{ map.put("19","NO"); }

        if(rdbtn20.isChecked()){
            map.put("20","YES");
        }else{ map.put("20","NO"); }

        if(rdbtn21.isChecked()){
            map.put("21","YES");
        }else{ map.put("21","NO"); }

        if(rdbtn22.isChecked()){
            map.put("22","YES");
        }else{ map.put("22","NO"); }

        if(rdbtn23.isChecked()){
            map.put("23","YES");
        }else{ map.put("23","NO"); }

        if(rdbtn24.isChecked()){
            map.put("24","YES");
        }else{ map.put("24","NO"); }

        if(rdbtn25.isChecked()){
            map.put("25","YES");
        }else{ map.put("25","NO"); }

        if(rdbtn26.isChecked()){
            map.put("26","YES");
        }else{ map.put("26","NO"); }

        if(rdbtn27.isChecked()){
            map.put("27","YES");
        }else{ map.put("27","NO"); }

        if(rdbtn28.isChecked()){
            map.put("28","YES");
        }else{ map.put("28","NO"); }

        if(rdbtn29.isChecked()){
            map.put("29","YES");
        }else{ map.put("29","NO"); }

        if(rdbtn30.isChecked()){
            map.put("30","YES");
        }else{ map.put("30","NO"); }

        if(rdbtn31.isChecked()){
            map.put("31","YES");
        }else{ map.put("31","NO"); }

        if(rdbtn32.isChecked()){
            map.put("32","YES");
        }else{ map.put("32","NO"); }

        if(rdbtn33.isChecked()){
            map.put("33","YES");
        }else{ map.put("33","NO"); }

        if(rdbtn34.isChecked()){
            map.put("34","YES");
        }else{ map.put("34","NO"); }

        if(rdbtn35.isChecked()){
            map.put("35","YES");
        }else{ map.put("35","NO"); }

        if(rdbtn36.isChecked()){
            map.put("36","YES");
        }else{ map.put("36","NO"); }

        if(rdbtn37.isChecked()){
            map.put("37","YES");
        }else{ map.put("37","NO"); }

        if(rdbtn38.isChecked()){
            map.put("38","YES");
        }else{ map.put("38","NO"); }

        if(rdbtn39.isChecked()){
            map.put("39","YES");
        }else{ map.put("39","NO"); }

        if(rdbtn40.isChecked()){
            map.put("40","YES");
        }else{ map.put("40","NO"); }

        if(rdbtn41.isChecked()){
            map.put("41","YES");
        }else{ map.put("41","NO"); }

        if(rdbtn42.isChecked()){
            map.put("42","YES");
        }else{ map.put("42","NO"); }

        if(rdbtn43.isChecked()){
            map.put("43","YES");
        }else{ map.put("43","NO"); }

        if(rdbtn44.isChecked()){
            map.put("44","YES");
        }else{ map.put("44","NO"); }

        if(rdbtn45.isChecked()){
            map.put("45","YES");
        }else{ map.put("45","NO"); }

        if(rdbtn46.isChecked()){
            map.put("46","YES");
        }else{ map.put("46","NO"); }

        if(rdbtn47.isChecked()){
            map.put("47","YES");
        }else{ map.put("47","NO"); }

        if(rdbtn48.isChecked()){
            map.put("48","YES");
        }else{ map.put("48","NO"); }

        if(rdbtn49.isChecked()){
            map.put("49","YES");
        }else{ map.put("49","NO"); }

        if(rdbtn50.isChecked()){
            map.put("50","YES");
        }else{ map.put("50","NO"); }

        if(rdbtn51.isChecked()){
            map.put("51","YES");
        }else{ map.put("51","NO"); }

        if(rdbtn52.isChecked()){
            map.put("52","YES");
        }else{ map.put("52","NO"); }

        p.databaseReference
        .child("Acopio")
        .child("RV")
        .child("VerificacionCorte")
        .setValue(map);
    }

    public void loadData(){
        final ArrayList<String> list = new ArrayList<>();
        p.databaseReference
        .child("Acopio")
        .child("RV")
        .child("VerificacionCorte")
        .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();

                for (String key : map.keySet()) {
                    Object value = map.get(key);
                    list.add((String) value);
                }
                Toast.makeText(getApplicationContext(), list.size()+"", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getDetails(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }
}