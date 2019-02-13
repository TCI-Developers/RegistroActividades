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
    RadioButton rdbtn1,rdbtn2,rdbtn3,rdbtn4,rdbtn5,rdbtn6,rdbtn7,rdbtn8,rdbtn9,rdbtn10,rdbtn11,rdbtn12,rdbtn13,rdbtn14,rdbtn15,rdbtn16,rdbtn17,rdbtn18,rdbtn19,rdbtn20,rdbtn21,rdbtn22 ,rdbtn23
    ,rdbtn24,rdbtn25,rdbtn26,rdbtn27,rdbtn28,rdbtn29,rdbtn30,rdbtn31,rdbtn32,rdbtn33,rdbtn34,rdbtn35,rdbtn36,rdbtn37,rdbtn38,rdbtn39,rdbtn40,rdbtn41,rdbtn42,rdbtn43,rdbtn44,rdbtn45,rdbtn46,rdbtn47
    ,rdbtn48,rdbtn49,rdbtn50,rdbtn51,rdbtn52,rdbtn53,rdbtn54,rdbtn55,rdbtn56,rdbtn57,rdbtn58;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auditoria_corte);
        Init();
    }

    private void Init() {
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
                radiobutton();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void radiobutton(){
        Toast.makeText(getApplicationContext(), ""+rdbtn1.isChecked(), Toast.LENGTH_LONG).show();
    }
}