package dev.tci.registroactividades;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import dev.tci.registroactividades.FragmentDialog.imageFragment;
import dev.tci.registroactividades.Modelos.FormatoCalidad;
import dev.tci.registroactividades.Singleton.Principal;

public class register extends AppCompatActivity implements imageFragment.OnImageFragmentListener{

    private LinearLayout danoLaytou,lyPhoto;
    private EditText huerta, productor, telefono, toneladas_aprox, cal32, cal36, cal40, cal48, cal60, cal70, cal84, cal96, calCAN, calLAC,
            danoRONA, danoROSADO, danoBANO, danoTRIPS, danoQUEMADO, danoCOMEDOR, danoVIRUELA, danoVARICELA, NoCuadrillas, concepto;
    private ImageView imgPhoto;
    private int sumaCalibres = 0;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    Date date = new Date();
    String fecha = dateFormat.format(date);
    String UID;
    String hora = java.text.DateFormat.getTimeInstance().format(Calendar.getInstance().getTime());
    TelephonyManager mTelephony;
    String myIMEI = "";
    private static final String[] PERMISOS = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private static final int REQUEST_CODE = 1;
    Bitmap imageBitmap;
    private Spinner spnMun, spnCONCEPT;
    Principal p;
    String imei;
    static final int REQUEST_TAKE_PHOTO = 1;
    String namePhoto = fecha+"-"+hora+"-RV.jpg";
    LocationManager manager;
    private double lati;
    private double longi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int leer = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        int leer2 = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int leer3 = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (leer == PackageManager.PERMISSION_DENIED || leer2 == PackageManager.PERMISSION_DENIED || leer3 == PackageManager.PERMISSION_DENIED ) {
            ActivityCompat.requestPermissions(this, PERMISOS, REQUEST_CODE);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Muestreo");
        setSupportActionBar(toolbar);

        Init();

        lyPhoto.setVisibility(View.GONE);
        int record = getIntent().getExtras().getInt("record");

        //Toast.makeText(getApplicationContext(), UID, Toast.LENGTH_LONG).show();
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
                    danoLaytou.setVisibility(View.GONE);
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
                Mi_hubicacion();
                //subirFotoFirebase();
                //obtenerURLImg();
                if( isValidateCabecera()){
                    //datos de muestro vicitas
                    if(!isValidateCalibres()){
                        Toast.makeText(getApplicationContext(), "La suma de los calibres debe de ser 100 \nTu total es de: " + sumaCalibres, Toast.LENGTH_LONG).show();
                    }else{
                        guardarDatos();
                    }
                }
            break;

            case R.id.camera:
                dispatchTakePictureIntent();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void guardarDatos() {
//*********************************************************************************************************************************************************************************************************************************************************
        //Formato calidad
        FormatoCalidad f = new FormatoCalidad();
        if(!danoBANO.getText().toString().isEmpty()) {f.setBano(Integer.valueOf(danoBANO.getText().toString()));}
        if(!danoCOMEDOR.getText().toString().isEmpty()) {f.setComedor(danoCOMEDOR.getText().toString());}
        if(!NoCuadrillas.getText().toString().isEmpty()) {f.setNcuadrillas(Integer.valueOf(NoCuadrillas.getText().toString()));}

        f.setHuerta(huerta.getText().toString());
        f.setProductor(productor.getText().toString());
        f.setTelefono(Long.valueOf(telefono.getText().toString()));
        f.setTon_prox(Long.valueOf(toneladas_aprox.getText().toString()));
        f.setMunicipio(spnMun.getSelectedItem().toString());

        if(!cal32.getText().toString().isEmpty()) {f.setCal32(Integer.valueOf(cal32.getText().toString()));}
        if(!cal36.getText().toString().isEmpty()) {f.setCal36(Integer.valueOf(cal36.getText().toString()));}
        if(!cal40.getText().toString().isEmpty()) {f.setCal40(Integer.valueOf(cal40.getText().toString()));}
        if(!cal48.getText().toString().isEmpty()) {f.setCal48(Integer.valueOf(cal48.getText().toString()));}
        if(!cal60.getText().toString().isEmpty()) {f.setCal60(Integer.valueOf(cal60.getText().toString()));}
        if(!cal70.getText().toString().isEmpty()) {f.setCal70(Integer.valueOf(cal70.getText().toString()));}
        if(!cal84.getText().toString().isEmpty()) {f.setCal84(Integer.valueOf(cal84.getText().toString()));}
        if(!cal96.getText().toString().isEmpty()) {f.setCal96(Integer.valueOf(cal96.getText().toString()));}
        if(!calCAN.getText().toString().isEmpty()) {f.setCanica(Integer.valueOf(calCAN.getText().toString()));}
        if(!calLAC.getText().toString().isEmpty()) {f.setLacrado(Integer.valueOf(calLAC.getText().toString()));}

        if(!danoRONA.getText().toString().isEmpty()) {f.setRona(Integer.valueOf(danoRONA.getText().toString()));}
        if(!danoROSADO.getText().toString().isEmpty()) {f.setRosado(Integer.valueOf(danoROSADO.getText().toString()));}
        if(!danoTRIPS.getText().toString().isEmpty()) {f.setTrips(Integer.valueOf(danoTRIPS.getText().toString()));}
        if(!danoQUEMADO.getText().toString().isEmpty()) {f.setQuemado(Integer.valueOf(danoQUEMADO.getText().toString()));}
        if(!danoVIRUELA.getText().toString().isEmpty()) {f.setViruela(Integer.valueOf(danoVIRUELA.getText().toString()));}
        if(!danoVARICELA.getText().toString().isEmpty()) {f.setVaricela(Integer.valueOf(danoVARICELA.getText().toString()));}

        f.setHora(hora);
        f.setFecha(fecha);

        f.setLatitud(lati);
        f.setLongitud(longi);
        f.setUrl("");

        f.setConcepto(spnCONCEPT.getSelectedItem().toString());

        p.databaseReference.child("Acopio").child("RV").child("UsuariosAcopio").child(imei).child("agendavisitas").child(UID).child("formatocalidad").setValue(f);
////*******************************************************************************************************************************************************************************************************************************************************
        //subirFotoFirebase();
        limpiar();
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
        imgPhoto = findViewById(R.id.imgPhoto);
        lyPhoto = findViewById(R.id.lyPhoto);
        spnMun = findViewById(R.id.spnMunicipio);
        NoCuadrillas = findViewById(R.id.txtNoCuadrillas);
        concepto = findViewById(R.id.txtConcepto);
        spnCONCEPT = findViewById(R.id.spnPLAG);
        p = Principal.getInstance();
        imei = getIMEI();
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
        return true;
    }

    public boolean isValidateCabecera(){
        if(huerta.getText().toString().isEmpty()){
            huerta.setError("Es requerido");
        }else{
            if(productor.getText().toString().isEmpty()){
                productor.setError("Es requerido");
            }else{
                if(telefono.getText().toString().isEmpty()){
                    telefono.setError("Es requerido");
                }
                else{
                    if(toneladas_aprox.getText().toString().isEmpty()){
                        toneladas_aprox.setError("Es requerido");
                    }else{
                        if(spnMun.getSelectedItemPosition() < 1){
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

    public static String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "dev.tci.registroactividades",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Matrix matrix = new Matrix();
            matrix.postRotate(90.0f);
            lyPhoto.setVisibility(View.VISIBLE);
            imageBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
            Bitmap rotatedBitmap = Bitmap.createBitmap(imageBitmap, 0, 0, imageBitmap.getWidth(), imageBitmap.getHeight(), matrix, true);
            imgPhoto.setImageBitmap(rotatedBitmap);
        }
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

    @Override
    public void onImageListener() {

    }

    public void viewImagen(View view){
        imageFragment obj = new imageFragment();
        obj.show(getSupportFragmentManager(),"register");
    }

    public void subirFotoFirebase(){
        StorageReference path = p.storageRef.child("Imagenes/RV/"+namePhoto);
        imgPhoto.setDrawingCacheEnabled(true);
        imgPhoto.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imgPhoto.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = path.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(register.this,"Error IMG: "+exception,Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(register.this,"Img guardada en Storage",Toast.LENGTH_SHORT).show();
                obtenerURLImg();
            }
        });
    }

    public void obtenerURLImg(){
        p.storageRef.child("Imagenes/RV/"+namePhoto).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Toast.makeText(register.this,"URL: "+uri,Toast.LENGTH_SHORT).show();
                p.databaseReference.child("Acopio").child("RV").child("UsuariosAcopio").child(imei).child("agendavisitas")
                        .child(UID).setValue(uri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(register.this,"No se encontro IMG",Toast.LENGTH_SHORT).show();
            }
        });
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            actualizar(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private void Mi_hubicacion() {
        manager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        Location local = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizar(local);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
    }

    private void actualizar(Location local) {
        if (local != null) {
            lati = local.getLatitude();
            longi = local.getLongitude();
            //Toast.makeText(getApplicationContext(), "Latitud: " + lati + "Longitud: " + longi, Toast.LENGTH_LONG).show();
        }
    }

    public void limpiar(){
        huerta.setText("");
        productor.setText("");
        telefono.setText("");
        toneladas_aprox.setText("");
        cal32.setText("");
        cal36.setText("");
        cal40.setText("");
        cal48.setText("");
        cal60.setText("");
        cal70.setText("");
        cal84.setText("");
        cal96.setText("");
        calCAN.setText("");
        calLAC.setText("");
        danoRONA.setText("");
        danoROSADO.setText("");
        danoBANO.setText("");
        danoTRIPS.setText("");
        danoQUEMADO.setText("");
        danoCOMEDOR.setText("");
        danoVIRUELA.setText("");
        danoVARICELA.setText("");
        spnMun.setSelection(0);
        NoCuadrillas.setText("");
        concepto.setText("");
        spnCONCEPT.setSelection(0);
    }
}

