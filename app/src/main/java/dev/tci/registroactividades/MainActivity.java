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
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import java.util.ArrayList;
import dev.tci.registroactividades.Modelos.AgendaVisitas;
import dev.tci.registroactividades.Modelos.FormatoCalidad;
import dev.tci.registroactividades.QuickBase.ParseXmlData;
import dev.tci.registroactividades.QuickBase.Results;
import dev.tci.registroactividades.Singleton.Principal;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> listHuertas;
    private ArrayList<String> listProductores;
    private ArrayList<String> record;
    private ArrayList<String> UID;
    private ArrayList<String> ref;
    private ArrayList<String> namePhoto;
    String myIMEI = "";
    private static final String[] PERMISOS = {
            Manifest.permission.READ_PHONE_STATE
    };
    private static final int REQUEST_CODE = 1;
    TelephonyManager mTelephony;
    String token = "bp9chqje532g5cz95empciw4qnu";
    Principal p = Principal.getInstance();
    FormatoCalidad f;
    private ArrayList<FormatoCalidad> datosF;
    Button btnubir;
    ProgressDialog dialog;
    private ImageView imgPhoto;
    public static ArrayList<String> imgRUTA;
    private String downloadImageUrl;
    UploadTask uploadTask = null;
    boolean ban = false;
    ProgressBar bar;
    public static boolean connected;

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

        btnubir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connected){
                    for(int j=0; j<UID.size(); j++){
                        for(int i = 0 ; i < imgRUTA.size(); i++){
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

                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()){
                    AgendaVisitas ag = objSnaptshot.getValue(AgendaVisitas.class);

                    UID.add(objSnaptshot.getKey());
                    listHuertas.add( ag.getHuerta() );
                    listProductores.add(ag.getProductor());
                    record.add(ag.getRecord());
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
        datosF = new ArrayList<>();
        btnubir = findViewById(R.id.button2);
        dialog = new ProgressDialog(MainActivity.this);
        imgRUTA = new ArrayList<>();
        ref = new ArrayList<>();
        namePhoto = new ArrayList<>();
        bar = findViewById(R.id.progSubida2);
    }

    public void CheckData(View v){
        ListarHuertas();
        if(listHuertas.size() > 0){
            Intent intent = new Intent(getApplicationContext(), ListaActividades.class);
            intent.putStringArrayListExtra("Huertas", listHuertas );
            intent.putStringArrayListExtra("Productores", listProductores );
            intent.putStringArrayListExtra("record", record );
            intent.putStringArrayListExtra("UID", UID );
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

    public void subirQuick(){
        String  RegistroQ = "";
        for(int i = 0; i < datosF.size() ; i++){
            RegistroQ = "https://aortizdemontellanoarevalo.quickbase.com/db/bnhn2ewij" +
            "?a=API_AddRecord"+
            "&_fid_112="+"Bonanza"+ //Huerta Nombre
            "&_fid_113="+datosF.get(i).getProductor() +//productor
            "&_fid_114=" +datosF.get(i).getTelefono()+//telefono
            "&_fid_115=" +datosF.get(i).getTon_prox()+//toneladas aprox
            "&_fid_116=" +datosF.get(i).getMunicipio()+//municipio
            "&_fid_18=" +datosF.get(i).getCal32()+//Calibre 32
            "&_fid_19=" +datosF.get(i).getCal36()+//Calibre 36
            "&_fid_20=" +datosF.get(i).getCal40()+//Calibre 40
            "&_fid_21=" +datosF.get(i).getCal48()+//Calibre 48
            "&_fid_22=" +datosF.get(i).getCal60()+//Calibre 60
            "&_fid_105=" +datosF.get(i).getCal70()+//Calibre 70
            "&_fid_106=" +datosF.get(i).getCal84()+//Calibre 84
            "&_fid_107=" +datosF.get(i).getCal96()+//Calibre 96
            "&_fid_24=" +datosF.get(i).getCanica()+//Canica (CAN)
            "&_fid_23=" +datosF.get(i).getLacrado()+//lacrado(LAC)
            "&_fid_117=" +datosF.get(i).getRona()+//Roña
            "&_fid_118=" +datosF.get(i).getRosado()+//Rosado
            "&_fid_119=" +datosF.get(i).getBano()+//Baño
            "&_fid_120=" +datosF.get(i).getTrips()+//Trips
            "&_fid_121=" +datosF.get(i).getQuemado()+//Quemado
            "&_fid_122=" +datosF.get(i).getComedor()+//Comedor
            "&_fid_123=" +datosF.get(i).getViruela()+//Viruela
            "&_fid_124=" +datosF.get(i).getVaricela()+//Varicela
            "&_fid_108=" +getIMEI()+//imei
            "&_fid_109=" +datosF.get(i).getProductor()+//record
            "&_fid_110=" +datosF.get(i).getNcuadrillas()+//numero cuadrillas
            "&_fid_111=" +datosF.get(i).getConcepto()+//concepto bitacora
            "&_fid_7=" +datosF.get(i).getPositionMun()+//campobitacora*******************************************************************
            "&_fid_87=" +datosF.get(i).getUrl()+//ruta de la imagen
            "&_fid_81=" +datosF.get(i).getLatitud() +", "+ datosF.get(i).getLongitud() +//latitud,longitud
            "&_fid_6=" +datosF.get(i).getFecha()+", "+datosF.get(i).getHora()+//fecha,hora
            "&ticket="  +"9_bpqnx8hh8_b2c6pu_fwjc_a_-b_di9hv2qb4t5jbp9jhvu3thpdfdt49mr8dugqz499kgcecg5vb3m_bwg8928"+
            "&apptoken=" + token;
            try{
                new CargarDatos().execute(RegistroQ.replace(" ", "%20"));
                //Toast.makeText(getApplicationContext(), "Se subio la informacion correctamente", Toast.LENGTH_LONG).show();
            } catch (Exception e){
                Toast.makeText(this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void loaddatosQuick(){
        for(int i=0; i<UID.size(); i++){
            datosF.clear();
            imgRUTA.clear();
            namePhoto.clear();
            ref.clear();
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
                                ref.add(objSnaptshot.getKey());
                                f = objSnaptshot.getValue(FormatoCalidad.class);
                                if(f.getStatus() < 1){
                                    datosF.add(f);
                                    imgRUTA.add(f.getBaseurl());
                                    namePhoto.add(f.getFecha() + "-" + f.getHora() + "-RV.jpg");
                                    btnubir.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getApplicationContext(), databaseError.getDetails(), Toast.LENGTH_LONG).show();
                        }
                    });
            }
    }
///////////////////////////carga de datos a quickbase
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

                    //Si hay error en la carga de datos en quickBase, los datos los mandamos a Hostinger
                    if (resultado.equals("No error")) {
                        Log.d("Mensaje del Servidor", resultado);
                        dialog.hide();
                        try {

                        } catch (Exception e) {
//                            Toast.makeText(MainActivity.this, "Error al subir", Toast.LENGTH_SHORT).show();
                            System.out.println("error al subir: " + e.getMessage());
                        }
                    } else {
                        Log.d("Error de consulta", resultado);

                    }
                } else {
                    /**En caso que respuesta sea null es por que fue error de http como los son;
                     * 404,500,403 etc*/
                    Log.d("Error del Servidor ", result);
                    dialog.hide();
                }
            }
        }

    public void subirFotoFirebase(final int pos, final int posUID) {
        StorageReference path = p.storageRef.child("Imagenes/RV/"+namePhoto.get(pos));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Matrix matrix = new Matrix();
        matrix.postRotate(90.0f);
        Bitmap imageBitmap = BitmapFactory.decodeFile(imgRUTA.get(pos));
        Bitmap rotatedBitmap = Bitmap.createBitmap(imageBitmap, 0, 0, imageBitmap.getWidth(), imageBitmap.getHeight(), matrix, true);
        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] data = baos.toByteArray();

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
                                throw task.getException();
                            }else{
                                downloadImageUrl = p.storageRef.child("Imagenes/RV/"+namePhoto.get(pos)).getDownloadUrl().toString();
                            }
                            return p.storageRef.child("Imagenes/RV/"+namePhoto.get(pos)).getDownloadUrl();
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
                                datosF.get(pos).setStatus(1);
                                p.databaseReference
                                .child("Acopio")
                                .child("RV")
                                .child("UsuariosAcopio")
                                .child(getIMEI())
                                .child("agendavisitas")
                                .child(UID.get(posUID))
                                .child("formatocalidad")
                                .child(ref.get(pos))
                                .setValue(datosF.get(pos));

                                Toast.makeText(MainActivity.this, "Todos tus datos se subieron exitosamente.", Toast.LENGTH_SHORT).show();

                                bar.setVisibility(View.GONE);
                                bar.setProgress(0);
                                btnubir.setVisibility(View.GONE);
                                //subirQuick();
                            }else{
                                Toast.makeText(MainActivity.this,"Error en obtener url2: "+task.getException().toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Error: " + e, Toast.LENGTH_SHORT).show();
                    Log.e("Error: ", e.toString());
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    bar.setVisibility(View.VISIBLE);
                    bar.setProgress((int) progress);
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
