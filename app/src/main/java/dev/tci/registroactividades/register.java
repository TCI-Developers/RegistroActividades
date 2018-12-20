package dev.tci.registroactividades;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import dev.tci.registroactividades.FragmentDialog.imageFragment;
import dev.tci.registroactividades.Modelos.Muestro;
import dev.tci.registroactividades.Singleton.Persistencia;
import dev.tci.registroactividades.Singleton.Principal;

public class register extends AppCompatActivity implements imageFragment.OnImageFragmentListener{

    private LinearLayout danoLaytou,lyPhoto;
    private EditText huerta, productor, telefono, toneladas_aprox, cal32, cal36, cal40, cal48, cal60, cal70, cal84, cal96, calCAN, calLAC,
            danoRONA, danoROSADO, danoBANO, danoTRIPS, danoQUEMADO, danoCOMEDOR, danoVIRUELA, danoVARICELA;
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
            Manifest.permission.READ_PHONE_STATE
    };
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Muestreo");
        setSupportActionBar(toolbar);

        Init();
        lyPhoto.setVisibility(View.GONE);
        int record = getIntent().getExtras().getInt("record");
        Toast.makeText(getApplicationContext(), UID, Toast.LENGTH_LONG).show();
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
                //datos de muestro vivitas
                if(!isValidateCalibres()){
                    Toast.makeText(getApplicationContext(), "La suma de los calibres debe de ser 100 \nTu total es de: " + sumaCalibres + "\nFecha: " + hora, Toast.LENGTH_LONG).show();
                }else{
                    Muestro m = new Muestro();
                    if(!cal32.getText().toString().isEmpty()) {m.setCal32(Integer.valueOf(cal32.getText().toString()));}
                    if(!cal36.getText().toString().isEmpty()) {m.setCal36(Integer.valueOf(cal36.getText().toString()));}
                    if(!cal40.getText().toString().isEmpty()) {m.setCal40(Integer.valueOf(cal40.getText().toString()));}
                    if(!cal48.getText().toString().isEmpty()) {m.setCal48(Integer.valueOf(cal48.getText().toString()));}
                    if(!cal60.getText().toString().isEmpty()) {m.setCal60(Integer.valueOf(cal60.getText().toString()));}
                    if(!cal70.getText().toString().isEmpty()) {m.setCal70(Integer.valueOf(cal70.getText().toString()));}
                    if(!cal84.getText().toString().isEmpty()) {m.setCal84(Integer.valueOf(cal84.getText().toString()));}
                    if(!cal96.getText().toString().isEmpty()) {m.setCal96(Integer.valueOf(cal96.getText().toString()));}
                    if(!calCAN.getText().toString().isEmpty()) {m.setCanica(Integer.valueOf(calCAN.getText().toString()));}
                    if(!calLAC.getText().toString().isEmpty()) {m.setLacrado(Integer.valueOf(calLAC.getText().toString()));}

                    Principal p = Principal.getInstance();
                    String imei = getIMEI();
                    p.databaseReference.child("Acopio").child("RV").child("UsuariosAcopio").child(imei).child("agendavisitas").child(UID).child("muestreovisita"+"1").setValue(m);
                    limpiar();
                }
                //datos de cabecera (huerta, productor, tel, etc...)
                if(isValidateCabecera()){

                }
            break;

            case R.id.camera:
                dispatchTakePictureIntent();
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
        imgPhoto = findViewById(R.id.imgPhoto);
        lyPhoto = findViewById(R.id.lyPhoto);
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
        return true;
    }

    public boolean isValidateCabecera(){
        if(huerta.getText().toString().isEmpty()){
            if(productor.getText().toString().isEmpty()){
                if(telefono.getText().toString().isEmpty()){
                    if(toneladas_aprox.getText().toString().isEmpty()){toneladas_aprox.setError("Es requerido");}
                    telefono.setError("Es requerido");}
                productor.setError("Es requerido");}
            huerta.setError("Es requerido");
        }else{
            return true;
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

    static final int REQUEST_TAKE_PHOTO = 1;

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
    Bitmap imageBitmap;
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
    }

}

