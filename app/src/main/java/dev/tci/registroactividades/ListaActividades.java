package dev.tci.registroactividades;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.tci.registroactividades.Adapter.Recycler;
import dev.tci.registroactividades.Modelos.Huertas;

public class ListaActividades extends AppCompatActivity {
    private ArrayList<Huertas> listadoHuertas;
    private Recycler adapter;
    private RecyclerView recyclerView;
    private Huertas h;
    private ArrayList<String> UID;
    private String IMEI;
    public static String URL_BASE1 = "https://tci-devps.firebaseio.com/Acopio/RV/UsuariosAcopio/";
    public static String URL_BASE2 = "/agendavisitas/H000264161203114658";
    private RequestQueue queue;
    ArrayList<String> concepto;

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
                Toast.makeText(getApplicationContext(),"UID: "+UID.get(position) + "\nIMEI: " + IMEI, Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        subirInformacion();
    }

    public void Init(){
        listadoHuertas = new ArrayList<>();
        UID = new ArrayList<String>();
        recyclerView = findViewById(R.id.recylcer_actividades);
        queue = Volley.newRequestQueue(this);
        concepto = new ArrayList<>();
    }

    public void subirInformacion(){
        for (int i = 0; i < UID.size(); i++){
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, "https://tci-devps.firebaseio.com/Acopio/RV/UsuariosAcopio/"+IMEI+"/agendavisitas/"+UID.get(i)+"/formatocalidad/.json", null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                concepto.add(response.getString("concepto"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        }
                    });

            queue.add(jsonObjectRequest);
        }
    }
}
