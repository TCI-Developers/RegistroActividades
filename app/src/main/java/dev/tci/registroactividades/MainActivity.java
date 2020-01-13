package dev.tci.registroactividades;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.*;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import dev.tci.registroactividades.Modelos.AgendaVisitas;
import dev.tci.registroactividades.Modelos.FormatoCalidad;
import dev.tci.registroactividades.QuickBase.ParseXmlData;
import dev.tci.registroactividades.QuickBase.Results;
import dev.tci.registroactividades.Singleton.Principal;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> listHuertas, listProductores,record, UID, ref, ref2, namePhoto,  fechaArray, contactos, telContacto, telProductor, HUE;
    private CardView cardView;
    private String myIMEI = "";
    private String fecha;
    private Date date, date2;
    private SimpleDateFormat dateFormat;
    private static final String[] PERMISOS = {
            Manifest.permission.READ_PHONE_STATE
    };
    private static final int REQUEST_CODE = 1;
    private TelephonyManager mTelephony;
    private String token = "cwxu6exdambasvd2cp6wvbgwfuqy";
    Principal p = Principal.getInstance();
    private FormatoCalidad f;
    private ArrayList<FormatoCalidad> datosF;
    private ImageButton btnubir;
    public static ArrayList<String> imgRUTA;
    private String downloadImageUrl;
    private UploadTask uploadTask = null;
    private ProgressBar bar;
    public static boolean connected;
    private TextView progres, pendientes;
    private SwipeRefreshLayout refreshLayout;
    private AgendaVisitas ag;
    private ProgressDialog dialog;

    @Override
    protected void onStart() {
        super.onStart();
        loaddatosQuick();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Init();
        ListarHuertas();
        validaInternet();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loaddatosQuick();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });

        btnubir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connected){
                    dialog = new ProgressDialog(v.getContext());
                    dialog.setTitle("Subiendo datos");
                    dialog.setMessage("Espere unos segundos por favor");
                    dialog.setCancelable(false);
                    dialog.show();
                    for(int j=0; j<UID.size(); j++){
                            for(int i = 0 ; i < ref.size(); i++){
                                subirFotoFirebase(i, j);
                        }
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "No tienes internet, verifica tu conexión", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void ListarHuertas() {
                p.databaseReference
                .child("Acopio")
                .child("RV")
                .child("UsuariosAcopio")
                .child(getIMEI())
                .child("agendavisitas")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listHuertas.clear();
                listProductores.clear();
                record.clear();
                UID.clear();
                contactos.clear();
                HUE.clear();
                telContacto.clear();
                telProductor.clear();
                try {
                    date = dateFormat.parse(fecha);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()){
                    try{
                        ag = objSnaptshot.getValue(AgendaVisitas.class);
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Error en la lectura de huertas: " + e.toString(), Toast.LENGTH_LONG).show();
                    }
                    if(objSnaptshot.getKey().equals("no programada")){
                        UID.add(objSnaptshot.getKey());
                        p.databaseReference
                                .child("Acopio")
                                .child("RV")
                                .child("UsuariosAcopio")
                                .child(getIMEI())
                                .child("agendavisitas")
                                .child("no programada")
                                .child("formatocalidad")
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()){
                                            f = objSnaptshot.getValue(FormatoCalidad.class);
                                            if(f.getSubido() < 1){
                                                record.add("0");
                                                listHuertas.add( "No programada" );
                                                listProductores.add("");
                                                contactos.add("");
                                                fechaArray.add(ag.getFecha());
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                    }else{
                        try {
                            date2 = dateFormat.parse(ag.getFecha());
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "No se ecuentra la fecha o no tiene el formato correcto en alguna actividad", Toast.LENGTH_LONG).show();
                        }
                        if(date.equals(date2) || date.before(date2) ){
                            UID.add(objSnaptshot.getKey());
                            listHuertas.add( ag.getHuerta() );
                            record.add(ag.getRecord());
                            fechaArray.add(ag.getFecha());

                            contactos.add(ag.getContacto());
                            telContacto.add(ag.getTelContacto());
                            listProductores.add(ag.getProductor());
                            telProductor.add(ag.getTelProductor());

                            HUE.add(ag.getHUE());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getDetails(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void Init() {
        listHuertas = new ArrayList<String>();
        listProductores= new ArrayList<String>();
        record = new ArrayList<String>();
        UID = new ArrayList<String>();
        HUE = new ArrayList<String>();
        telContacto = new ArrayList<String>();
        telProductor = new ArrayList<String>();
        datosF = new ArrayList<>();
        btnubir = findViewById(R.id.imageButton);
        imgRUTA = new ArrayList<>();
        ref = new ArrayList<>();
        ref2 = new ArrayList<>();
        namePhoto = new ArrayList<>();
        bar = findViewById(R.id.progSubida2);
        cardView = findViewById(R.id.cardSubir);
        progres = findViewById(R.id.txtProgress2);
        pendientes = findViewById(R.id.txtPendiente);
        fechaArray = new ArrayList<String>();
        fecha = getIntent().getExtras().getString("FECHA");
        date = new Date();
        date2 = new Date();
        dateFormat = new SimpleDateFormat("yy-MM-dd", Locale.getDefault());
        refreshLayout = findViewById(R.id.swipeLoad);
        contactos = new ArrayList<>();
    }

    public void CheckData(View v){
//        ListarHuertas();
        if(listHuertas.size() > 0){
            Intent intent = new Intent(getApplicationContext(), ListaActividades.class);
            intent.putStringArrayListExtra("Huertas", listHuertas );
            intent.putStringArrayListExtra("record", record );
            intent.putStringArrayListExtra("UID", UID );
            intent.putStringArrayListExtra("Contactos", contactos );
            intent.putStringArrayListExtra("TelContactos", telContacto );
            intent.putStringArrayListExtra("Productores", listProductores );
            intent.putStringArrayListExtra("TelProductores", telProductor );
            intent.putStringArrayListExtra("HUE", HUE );
//            intent.putStringArrayListExtra("ref", ref2 );
            intent.putStringArrayListExtra("FECHA", fechaArray );
            intent.putExtra("FECHA1", fecha );
            intent.putExtra("IMEI", myIMEI );
            startActivity(intent);
            //finish();
        }else{
            Snackbar.make(v, "No tienes actividades.", Snackbar.LENGTH_LONG).show();
        }
    }

    public void CheckData2(View v){
            Intent intent = new Intent(getApplicationContext(), register.class);
            intent.putStringArrayListExtra("Huertas", listHuertas );
            intent.putStringArrayListExtra("Productores", listProductores );
            intent.putStringArrayListExtra("record", null );
            intent.putExtra("UID", "no programada" );
            intent.putExtra("IMEI", myIMEI );
            intent.putStringArrayListExtra("Contactos", contactos );
            startActivity(intent);
    }

    public String getIMEI(){
        int leer = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (leer == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, PERMISOS, REQUEST_CODE);
        }else{
            mTelephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephony.getDeviceId() != null){
                myIMEI = mTelephony.getDeviceId();
            }
        }
        return myIMEI;
    }

    public void subirQuick(int pos){
        String  RegistroQ = "";
            RegistroQ = "https://aortizdemontellanoarevalo.quickbase.com/db/bnhn2ewij" +
            "?a=API_AddRecord"+
            "&_fid_112="+datosF.get(pos).getHuerta()+ //Huerta Nombre
            "&_fid_169="+datosF.get(pos).getHUE()+ //HUE
            "&_fid_113="+datosF.get(pos).getProductor() + "|" + datosF.get(pos).getContacto() +//productor
            "&_fid_114=" +datosF.get(pos).getTelefono() + "|" + datosF.get(pos).getContacTele()+//telefono
            "&_fid_115=" +datosF.get(pos).getTon_prox()+//toneladas aprox
            "&_fid_116=" +datosF.get(pos).getMunicipio()+//municipio
            "&_fid_18=" +datosF.get(pos).getCal32()+//Calibre 32
            "&_fid_19=" +datosF.get(pos).getCal36()+//Calibre 36
            "&_fid_20=" +datosF.get(pos).getCal40()+//Calibre 40
            "&_fid_21=" +datosF.get(pos).getCal48()+//Calibre 48
            "&_fid_22=" +datosF.get(pos).getCal60()+//Calibre 60
            "&_fid_105=" +datosF.get(pos).getCal70()+//Calibre 70
            "&_fid_106=" +datosF.get(pos).getCal84()+//Calibre 84
            "&_fid_107=" +datosF.get(pos).getCal96()+//Calibre 96
            "&_fid_24=" +datosF.get(pos).getCanica()+//Canica (CAN)
            "&_fid_23=" +datosF.get(pos).getLacrado()+//lacrado(LAC)
            "&_fid_117=" +datosF.get(pos).getRona()+//Roña
            "&_fid_118=" +datosF.get(pos).getRosado()+//Rosadocom.tci.consultoria.registroactividades
            "&_fid_119=" +datosF.get(pos).getBano()+//Baño
            "&_fid_120=" +datosF.get(pos).getTrips()+//Trips
            "&_fid_121=" +datosF.get(pos).getQuemado()+//Quemado
            "&_fid_122=" +datosF.get(pos).getComedor()+//Comedor
            "&_fid_123=" +datosF.get(pos).getViruela()+//Viruela
            "&_fid_124=" +datosF.get(pos).getVaricela()+//Varicela
            "&_fid_108=" +getIMEI()+//imei
            "&_fid_109=" +datosF.get(pos).getRecord()+//record
            "&_fid_110=" +datosF.get(pos).getNcuadrillas()+//numero cuadrillas
            "&_fid_111=" +datosF.get(pos).getConcepto()+//concepto bitacora
            "&_fid_7=" +datosF.get(pos).getCampoBitacora()+//campobitacora*******************************************************************
            "&_fid_87=" +URLEncoder.encode(datosF.get(pos).getUrl()) +//ruta de la imagen
            "&_fid_81=" +datosF.get(pos).getLatitud() +", "+ datosF.get(pos).getLongitud() +//latitud,longitud
            "&_fid_6=" +datosF.get(pos).getFecha()+" "+datosF.get(pos).getHora()+//fecha,hora
            "&_fid_152=" +datosF.get(pos).getFloracion()+//floracion
            "&_fid_151=" +datosF.get(pos).getTipoHuerta()+//nacional, organico, exportacion
            "&_fid_157=" +datosF.get(pos).getSuperficie()+//superficie
            "&usertoken=b2c6pu_fwjc_cbciv3q3szxj8k8ycyfx4rkk6";
            //"&ticket="  +"9_bqm6nmf84_b2c6pu_fwjc_a_-b_q9b76fd4tdzzdwuh72bbj34bfacry5x8rezw8z3diqzkwhcqhjk8z_c8asn26"+
            //"&apptoken=" + token;
            try{
                new CargarDatos().execute(RegistroQ.replace(" ", "%20"));
                Toast.makeText(getApplicationContext(), "Se subio la informacion correctamente", Toast.LENGTH_LONG).show();
            } catch (Exception e){
                Toast.makeText(this, "Error de conexión", Toast.LENGTH_SHORT).show();
        }
    }

    public void loaddatosQuick(){
        for(int i=0; i<UID.size(); i++){
            datosF.clear();
            imgRUTA.clear();
            namePhoto.clear();
            ref.clear();
            ref2.clear();

            try{
                f = new FormatoCalidad();
                p.databaseReference
                        .child("Acopio")
                        .child("RV")
                        .child("UsuariosAcopio")
                        .child(getIMEI())
                        .child("agendavisitas")
                        .child(UID.get(i))
                        .child("formatocalidad")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()){
                                    f = objSnaptshot.getValue(FormatoCalidad.class);
                                    ref2.add(objSnaptshot.getKey());
                                    if(f.getSubido() < 1){
                                        ref.add(objSnaptshot.getKey());
                                        datosF.add(f);
                                        imgRUTA.add(f.getBaseurl());
                                        namePhoto.add(f.getFecha() + "-" + f.getHora() + "-RV.jpg");
                                        cardView.setVisibility(View.VISIBLE);
                                    }
                                }
                                pendientes.setText(imgRUTA.size()+"");
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(), databaseError.getDetails(), Toast.LENGTH_LONG).show();
                            }
                        });
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "Error cargar datos quick: " + e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    class CargarDatos extends AsyncTask<String, Void, String> {

        @Override
            protected String doInBackground(String... urls) {
                try {
                    while (true) {
                        return Results.downloadUrl(urls[0]);
                    }

                } catch (IOException e) {
                    cancel(true);
                    return e.getCause().toString();
                }
            }

            @Override
            protected void onPostExecute(String result) {

            String resultado = ParseXmlData.ParseXmlData(result);

            /*Si la variable resultado es distinto a null entonces es por que quickBase
            nos envio una respuesta que xml con mensaje de exito o de algun error generado en la consulta*/
                if (resultado != null) {
                    //Si hay error en la carga de datos en quickBase, los datos los mandamos a Hostinge
                    if (resultado.equals("No error")) {
                        Log.d("Mensaje del Servidor", resultado);
                        try {
                            dialog.dismiss();
                            cardView.setVisibility(View.GONE);
                        } catch (Exception e) {
//                            Toast.makeText(MainActivity.this, "Error al subir", Toast.LENGTH_SHORT).show();
                            System.out.println("error al subir: " + e.getMessage());
                        }
                    } else {
                        Log.d("Error de consulta", resultado);
                        dialog.dismiss();
                        cardView.setVisibility(View.GONE);
                    }
                } else {
                    /**En caso que respuesta sea null es por que fue error de http como los son;
                     * 404,500,403 etc*/
                    Log.d("Error del Servidor ", result);
                    dialog.dismiss();
                    cardView.setVisibility(View.GONE);
                }
            }
        }

    public void subirFotoFirebase(final int pos, final int posUID) {
            StorageReference path = p.storageRef.child("RV/"+namePhoto.get(pos));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Matrix matrix = new Matrix();
            matrix.postRotate(90.0f);
            byte[] data = new byte[0];
            try{
                Bitmap imageBitmap = BitmapFactory.decodeFile(imgRUTA.get(pos));
                Bitmap rotatedBitmap = Bitmap.createBitmap(imageBitmap, 0, 0, imageBitmap.getWidth(), imageBitmap.getHeight(), matrix, true);
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                data = baos.toByteArray();
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "La fotografia ya no se encuentra en el celular", Toast.LENGTH_LONG).show();
            }

            uploadTask = path.putBytes(data);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //Toast.makeText(MainActivity.this,"Img guardada en Storage",Toast.LENGTH_SHORT).show();
                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                        {
                            if (!task.isSuccessful())
                            {
                                Toast.makeText(MainActivity.this,"Error en obtener ur1:"+task.getException().toString(),Toast.LENGTH_SHORT).show();
                                cardView.setEnabled(true);
                                throw task.getException();
                            }else{
                                downloadImageUrl = p.storageRef.child("RV/"+namePhoto.get(pos)).getDownloadUrl().toString();
                            }
                            return p.storageRef.child("RV/"+namePhoto.get(pos)).getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task)
                        {
                            if (task.isSuccessful())
                            {
                                downloadImageUrl = task.getResult().toString();
                                // Toast.makeText(MainActivity.this, "obtenimos la url de firebase correctamente", Toast.LENGTH_SHORT).show();
                                datosF.get(pos).setUrl(downloadImageUrl);
                                final HashMap<String, Object> productMap = new HashMap<>();

                                productMap.put("url", downloadImageUrl);
                                productMap.put("subido", 2);

                                p.databaseReference.
                                        child("Acopio")
                                        .child("RV")
                                        .child("UsuariosAcopio")
                                        .child(getIMEI())
                                        .child("agendavisitas")
                                        .child(UID.get(posUID))
                                        .child("formatocalidad")
                                        .child(ref.get(pos)).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.getValue() != null ){
                                            //Toast.makeText(getApplicationContext(), "Existe awebo", Toast.LENGTH_LONG).show();
                                            p.databaseReference
                                                    .child("Acopio")
                                                    .child("RV")
                                                    .child("UsuariosAcopio")
                                                    .child(getIMEI())
                                                    .child("agendavisitas")
                                                    .child(UID.get(posUID))
                                                    .child("formatocalidad")
                                                    .child(ref.get(pos))
                                                    .updateChildren(productMap);
                                            subirQuick(pos);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        dialog.dismiss();
                                        Toast.makeText(getApplicationContext(),"Error al leer firebase:\n" + databaseError, Toast.LENGTH_LONG).show();
                                    }
                                });


//                                bar.setVisibility(View.GONE);
//                                bar.setProgress(0);
//                                progres.setVisibility(View.GONE);
                            }else{
                                Toast.makeText(MainActivity.this,"Error en obtener url2: "+task.getException().toString(),Toast.LENGTH_SHORT).show();
                                cardView.setEnabled(true);
                            }
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Error: " + e, Toast.LENGTH_SHORT).show();
                    Log.e("Error: ", e.toString());
                    cardView.setEnabled(true);
                    dialog.dismiss();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
//                    bar.setVisibility(View.VISIBLE);
//                    bar.setProgress((int) progress);
//                    progres.setVisibility(View.VISIBLE);
//
//                    DecimalFormat format = new DecimalFormat("#.00");
////                    progres.setText(format.format(progress)  + " %");
//                    if(progress != 0){
//                        dialog.setProgress(Integer.valueOf(format.format(progress)  + " %"));
//                    }
                }
            });
    }

    public void validaInternet(){
        DatabaseReference connectedRef = p.firebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                connected = snapshot.getValue(Boolean.class);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error internet:" + error, Toast.LENGTH_LONG).show();
            }
        });
    }
}
