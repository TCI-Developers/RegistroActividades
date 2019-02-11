package dev.tci.registroactividades;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import dev.tci.registroactividades.FragmentDialog.imageFragment;
import dev.tci.registroactividades.Modelos.FormatoCalidad;
import dev.tci.registroactividades.Singleton.Principal;

import static dev.tci.registroactividades.MainActivity.connected;
import static dev.tci.registroactividades.MainActivity.imgRUTA;

public class register extends AppCompatActivity implements imageFragment.OnImageFragmentListener {

    private LinearLayout danoLaytou, lyPhoto;
    private EditText huerta, productor, contacto, contacTele ,telefono, toneladas_aprox, cal32, cal36, cal40, cal48, cal60, cal70, cal84, cal96, calCAN, calLAC,
            danoRONA, danoROSADO, danoBANO, danoTRIPS, danoQUEMADO, danoCOMEDOR, danoVIRUELA, danoVARICELA, NoCuadrillas, concepto, superficie;
    private ImageView imgPhoto;
    private int sumaCalibres = 0;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    Date date = new Date();
    String fecha = dateFormat.format(date);
    String UID;
    String hora = java.text.DateFormat.getTimeInstance().format(Calendar.getInstance().getTime());
    TelephonyManager mTelephony;
    String myIMEI = "";
    private Spinner spnMun, spnCONCEPT, spnComedo, spnFloracion, spnTipo;
    Principal p;
    String imei;
    static final int REQUEST_TAKE_PHOTO = 1;
    String namePhoto = fecha + "-" + hora + "-RV.jpg";
    LocationManager manager;
    private double lati = 0;
    private double longi  = 0;
    FormatoCalidad f = new FormatoCalidad();
    ProgressDialog dialog;
    private String identificador;
    int sumaDano = 0;
    String record;
    private static final String[] PERMISOS = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int REQUEST_CODE = 1;
    private String downloadImageUrl;
    UploadTask uploadTask = null;
    ProgressBar bar;
    ObjectAnimator anim;
    TextView progres;
    private CheckBox chekBanio;
    AlertDialog alert = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Muestreo");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Init();

        lyPhoto.setVisibility(View.GONE);
        record = getIntent().getExtras().getString("record");

        chekBanio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    danoBANO.setVisibility(View.VISIBLE);
                    danoBANO.setHint("Baño");
                }else{
                    danoBANO.setVisibility(View.INVISIBLE);
                    danoBANO.setHint("");
                }
            }
        });

        //Toast.makeText(getApplicationContext(), UID, Toast.LENGTH_LONG).show();
        calLAC.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    if (Integer.valueOf(s.toString()) > 0) {
                        danoLaytou.setVisibility(View.VISIBLE);
                    }
                } else {
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

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.check:
                dialog = new ProgressDialog(register.this);
                dialog.setTitle("Espere!");
                dialog.setMessage("Subiendo información...");

                //dialog.setCancelable(false);
                Mi_hubicacion();
                if (isValidateCabecera()) {
                    //datos de muestro vicitas
                    if (!isValidateCalibres()) {
                        Toast.makeText(getApplicationContext(), "La suma de los calibres debe de ser 100 \nTu total es de: " + sumaCalibres, Toast.LENGTH_LONG).show();
                    } else {
                        if (calLAC.getText().toString().isEmpty()) calLAC.setText("0");
                        if (Integer.valueOf(calLAC.getText().toString()) > 0) {
                            if (!isValidateDano()) {
                                Toast.makeText(getApplicationContext(), "La suma de los daños debe de ser "+calLAC.getText().toString()+"\nTu total es de: " + sumaDano, Toast.LENGTH_LONG).show();
                            } else {
                                if (isValidateCuadrillas()) {
                                    if (lyPhoto.getVisibility() == View.GONE) {
                                        Toast.makeText(register.this, "Foto requerida!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        guardarDatos();
                                    }
                                }
                            }
                        } else {
                            if (isValidateCuadrillas()) {
                                if (lyPhoto.getVisibility() == View.GONE) {
                                    Toast.makeText(register.this, "Foto requerida!", Toast.LENGTH_SHORT).show();
                                } else {
                                    guardarDatos();
                                }
                            }
                        }
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
        try{
            //Formato calidad
            if (!danoBANO.getText().toString().isEmpty()) {
                f.setBano(Integer.valueOf(danoBANO.getText().toString()));
            }

            if (!NoCuadrillas.getText().toString().isEmpty()) {
                f.setNcuadrillas(Integer.valueOf(NoCuadrillas.getText().toString()));
            }

            f.setHuerta(huerta.getText().toString());
            f.setProductor(productor.getText().toString());
            f.setTelefono(Long.valueOf(telefono.getText().toString()));

            if(!contacto.getText().toString().isEmpty()){
                f.setContacto(contacto.getText().toString());
            }else{
                f.setContacto("N/A");
            }

            if(!contacTele.getText().toString().isEmpty()){
                f.setContacTele(Long.valueOf(contacTele.getText().toString()));
            }else{
                f.setContacTele(0);
            }

            f.setTon_prox(Long.valueOf(toneladas_aprox.getText().toString()));
            f.setMunicipio(spnMun.getSelectedItem().toString());

            if (!cal32.getText().toString().isEmpty()) {
                f.setCal32(Integer.valueOf(cal32.getText().toString()));
            }
            if (!cal36.getText().toString().isEmpty()) {
                f.setCal36(Integer.valueOf(cal36.getText().toString()));
            }
            if (!cal40.getText().toString().isEmpty()) {
                f.setCal40(Integer.valueOf(cal40.getText().toString()));
            }
            if (!cal48.getText().toString().isEmpty()) {
                f.setCal48(Integer.valueOf(cal48.getText().toString()));
            }
            if (!cal60.getText().toString().isEmpty()) {
                f.setCal60(Integer.valueOf(cal60.getText().toString()));
            }
            if (!cal70.getText().toString().isEmpty()) {
                f.setCal70(Integer.valueOf(cal70.getText().toString()));
            }
            if (!cal84.getText().toString().isEmpty()) {
                f.setCal84(Integer.valueOf(cal84.getText().toString()));
            }
            if (!cal96.getText().toString().isEmpty()) {
                f.setCal96(Integer.valueOf(cal96.getText().toString()));
            }
            if (!calCAN.getText().toString().isEmpty()) {
                f.setCanica(Integer.valueOf(calCAN.getText().toString()));
            }
            if (!calLAC.getText().toString().isEmpty()) {
                f.setLacrado(Integer.valueOf(calLAC.getText().toString()));
            }

            if (!danoRONA.getText().toString().isEmpty()) {
                f.setRona(Integer.valueOf(danoRONA.getText().toString()));
            }
            if (!danoROSADO.getText().toString().isEmpty()) {
                f.setRosado(Integer.valueOf(danoROSADO.getText().toString()));
            }
            if (!danoTRIPS.getText().toString().isEmpty()) {
                f.setTrips(Integer.valueOf(danoTRIPS.getText().toString()));
            }
            if (!danoQUEMADO.getText().toString().isEmpty()) {
                f.setQuemado(Integer.valueOf(danoQUEMADO.getText().toString()));
            }
            if (!danoVIRUELA.getText().toString().isEmpty()) {
                f.setViruela(Integer.valueOf(danoVIRUELA.getText().toString()));
            }
            if (!danoVARICELA.getText().toString().isEmpty()) {
                f.setVaricela(Integer.valueOf(danoVARICELA.getText().toString()));
            }

            f.setHora(hora);
            f.setFecha(fecha);

            f.setLatitud(lati);
            f.setLongitud(longi);
            f.setUrl("");
            f.setBaseurl(mCurrentPhotoPath);
            f.setPositionMun(spnMun.getSelectedItemPosition());
            f.setNoComedor(spnComedo.getSelectedItemPosition());
            f.setNotipoHuerta(spnTipo.getSelectedItemPosition());
            f.setNofloracion(spnFloracion.getSelectedItemPosition());
            f.setCheckBanio(chekBanio.isChecked());

            f.setComedor(spnComedo.getSelectedItem().toString());
            f.setConcepto("");
            f.setCampoBitacora(concepto.getText().toString());
            f.setRecord(record);
            f.setStatus(0);
            f.setSubido(0);

            f.setFloracion(spnFloracion.getSelectedItem().toString());
            f.setTipoHuerta(spnTipo.getSelectedItem().toString());
            f.setSuperficie(Integer.valueOf(superficie.getText().toString()));

            p.databaseReference
            .child("Acopio")
            .child("RV")
            .child("UsuariosAcopio")
            .child(imei)
            .child("agendavisitas")
            .child(UID)
            .child("formatocalidad")
            .child(identificador)
            .setValue(f);

            if(connected){
                subirFirebase();
            }else{
                Toast.makeText(register.this,"No tienes internet, pero tus datos se han guardado localmente",Toast.LENGTH_LONG).show();
                finish();
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error al guardar la visita:\n"+e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void Init() {
        huerta = findViewById(R.id.txtHuerta);
        productor = findViewById(R.id.txtProductor);
        contacto = findViewById(R.id.txtContacto);
        contacTele = findViewById(R.id.txtTelefonoContacto);
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
        identificador = UUID.randomUUID().toString();
        imgRUTA = new ArrayList<>();
        bar = findViewById(R.id.progSubida);
        progres = findViewById(R.id.txtProgress);
        chekBanio = findViewById(R.id.checkBox);
        spnComedo = findViewById(R.id.spnComedor);
        spnFloracion = findViewById(R.id.spnFloracion);
        spnTipo = findViewById(R.id.spnTipo);
        superficie = findViewById(R.id.txtSuperficie);
    }

    public boolean isValidateCalibres() {
        sumaCalibres = 0;
        if (!cal32.getText().toString().isEmpty()) {
            sumaCalibres += Integer.valueOf(cal32.getText().toString());
        }

        if (!cal36.getText().toString().isEmpty()) {
            sumaCalibres += Integer.valueOf(cal36.getText().toString());
        }

        if (!cal40.getText().toString().isEmpty()) {
            sumaCalibres += Integer.valueOf(cal40.getText().toString());
        }

        if (!cal48.getText().toString().isEmpty()) {
            sumaCalibres += Integer.valueOf(cal48.getText().toString());
        }

        if (!cal60.getText().toString().isEmpty()) {
            sumaCalibres += Integer.valueOf(cal60.getText().toString());
        }

        if (!cal70.getText().toString().isEmpty()) {
            sumaCalibres += Integer.valueOf(cal70.getText().toString());
        }

        if (!cal84.getText().toString().isEmpty()) {
            sumaCalibres += Integer.valueOf(cal84.getText().toString());
        }

        if (!cal96.getText().toString().isEmpty()) {
            sumaCalibres += Integer.valueOf(cal96.getText().toString());
        }

        if (!calCAN.getText().toString().isEmpty()) {
            sumaCalibres += Integer.valueOf(calCAN.getText().toString());
        }

        if (!calLAC.getText().toString().isEmpty()) {
            sumaCalibres += Integer.valueOf(calLAC.getText().toString());
        }

        if (sumaCalibres != 100) {
            return false;
        }
        return true;
    }

    public boolean isValidateDano() {
        sumaDano = 0;
        if (!danoRONA.getText().toString().isEmpty()) {
            sumaDano += Integer.valueOf(danoRONA.getText().toString());
        }

        if (!danoROSADO.getText().toString().isEmpty()) {
            sumaDano += Integer.valueOf(danoROSADO.getText().toString());
        }

        if (!danoTRIPS.getText().toString().isEmpty()) {
            sumaDano += Integer.valueOf(danoTRIPS.getText().toString());
        }

        if (!danoQUEMADO.getText().toString().isEmpty()) {
            sumaDano += Integer.valueOf(danoQUEMADO.getText().toString());
        }

        if (!danoVIRUELA.getText().toString().isEmpty()) {
            sumaDano += Integer.valueOf(danoVIRUELA.getText().toString());
        }

        if (!danoVARICELA.getText().toString().isEmpty()) {
            sumaDano += Integer.valueOf(danoVARICELA.getText().toString());
        }

        if (sumaDano != Integer.valueOf(calLAC.getText().toString())) {
            return false;
        }
        return true;
    }

    public boolean isValidateCabecera() {
        if (huerta.getText().toString().isEmpty()) {
            huerta.setError("Es requerido");
        } else {
            if (productor.getText().toString().isEmpty()) {
                productor.setError("Es requerido");
            } else {
                if (telefono.getText().toString().isEmpty()) {
                    telefono.setError("Es requerido");
                } else {
                    if (toneladas_aprox.getText().toString().isEmpty()) {
                        toneladas_aprox.setError("Es requerido");
                    } else {
                        if (superficie.getText().toString().isEmpty()) {
                            superficie.setError("Es requerido");
                        } else {
                            if (spnMun.getSelectedItemPosition() < 1) {
                                Toast.makeText(getApplicationContext(), "Selecciona un municipio por favor.", Toast.LENGTH_LONG).show();
                            } else {
                                if (spnComedo.getSelectedItemPosition() < 1) {
                                    Toast.makeText(getApplicationContext(), "Selecciona una opción de comedor por favor.", Toast.LENGTH_LONG).show();
                                }else{
                                    if (spnTipo.getSelectedItemPosition() < 1) {
                                        Toast.makeText(getApplicationContext(), "Selecciona si es Exportación, Nacional u Organico por favor.", Toast.LENGTH_LONG).show();
                                    }else{
                                        if (spnFloracion.getSelectedItemPosition() < 1) {
                                            Toast.makeText(getApplicationContext(), "Selecciona la Floración por favor.", Toast.LENGTH_LONG).show();
                                        }else{
                                            if (chekBanio.isChecked()) {
                                                if(danoBANO.getText().toString().isEmpty()){
                                                    danoBANO.setError("Es requerido");
                                                }else{
                                                    return true;
                                                }
                                            }else{
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isValidateCuadrillas() {
        if (!NoCuadrillas.getText().toString().isEmpty()) {
            if (!concepto.getText().toString().isEmpty()) {
                return true;
            }else{
                concepto.setError("Es requerido");
            }
        } else {
            NoCuadrillas.setError("Es requerido");
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
        int leer4 = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (leer4 == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(register.this, PERMISOS, REQUEST_CODE);
        }
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
                Uri photoURI = FileProvider.getUriForFile(this, "com.tci.consultoria.registroactividades", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            try {
                lyPhoto.setVisibility(View.VISIBLE);
                mostrarPhoto();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getIMEI() {
        int leer = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE);
        int leer2 = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        int leer3 = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
        if (leer == PackageManager.PERMISSION_DENIED || leer2 == PackageManager.PERMISSION_DENIED || leer3 == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(register.this, PERMISOS, REQUEST_CODE);
        }
        mTelephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (mTelephony.getDeviceId() != null) {
            myIMEI = mTelephony.getDeviceId();
        }
        return myIMEI;
    }

    @Override
    public void onImageListener() {

    }

    public void viewImagen(View view) {
        imageFragment obj = new imageFragment();
        obj.show(getSupportFragmentManager(), "register");
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
        int leer = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE);
        int leer2 = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        int leer3 = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
        if (leer == PackageManager.PERMISSION_DENIED || leer2 == PackageManager.PERMISSION_DENIED || leer3 == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(register.this, PERMISOS, REQUEST_CODE);
        }
        try{
            manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                AlertNoGps();
            }else{
                Location local = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                actualizar(local);
                manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 20 * 1000, 10, locationListener);
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Error en el GPS:\n"+ e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void AlertNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder .setTitle("GPS")
                .setMessage("El sistema GPS esta desactivado, para registrar tus actividades es necesario activarlo." +
                        " Por favor pulsa el botón rojo (Activar) para activarlo.")
                .setCancelable(false)
                .setPositiveButton("Activar", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                    }
                });
        alert = builder.create();
        alert.show();
        alert.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.RED);
    }

    private void actualizar(Location local) {
        if (local != null) {
            lati = local.getLatitude();
            longi = local.getLongitude();
            //Toast.makeText(getApplicationContext(), "Latitud: " + lati + "Longitud: " + longi, Toast.LENGTH_LONG).show();
        }
    }

    public void subirFirebase() {
        StorageReference path = p.storageRef.child("RV/"+namePhoto);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Matrix matrix = new Matrix();
        matrix.postRotate(90.0f);
        Bitmap imageBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
        Bitmap rotatedBitmap = Bitmap.createBitmap(imageBitmap, 0, 0, imageBitmap.getWidth(), imageBitmap.getHeight(), matrix, true);
        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] data = baos.toByteArray();

        uploadTask = path.putBytes(data);
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                bar.setVisibility(View.VISIBLE);
                progres.setVisibility(View.VISIBLE);

                bar.setProgress((int) progress);

                DecimalFormat format = new DecimalFormat("#.00");
                progres.setText(format.format(progress)  + " %");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Toast.makeText(register.this,"Img guardada en Storage",Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }
                        downloadImageUrl = p.storageRef.child("RV/"+namePhoto).getDownloadUrl().toString();
                        return p.storageRef.child("RV/"+namePhoto).getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if (task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();

                            f.setUrl(downloadImageUrl);
                            f.setStatus(0);
                            p.databaseReference
                            .child("Acopio")
                            .child("RV")
                            .child("UsuariosAcopio")
                            .child(imei)
                            .child("agendavisitas")
                            .child(UID)
                            .child("formatocalidad")
                            .child(identificador)
                            .setValue(f);

//                            Toast.makeText(register.this, "Datos subidos exitosamente", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(register.this, "Error en subir a firebase: " + e, Toast.LENGTH_LONG).show();
                Log.e("Error: ", e.toString());
            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                Toast.makeText(register.this, "En el cancelable", Toast.LENGTH_LONG).show();
            }
        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(register.this, "En el pausable", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void mostrarPhoto() throws IOException {
        Glide.with(register.this)
                .load(mCurrentPhotoPath)
                .into(imgPhoto);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Mi_hubicacion();
    }
}