package dev.tci.registroactividades.Singleton;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.*;

import java.util.ArrayList;

import dev.tci.registroactividades.Modelos.Registro;

public class Principal extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Principal principal;
    public ArrayList<Registro> listRegistro;

    public Principal() {
        databaseReference.child("RV").child("Actividades").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listRegistro.clear();
                for(DataSnapshot obj : dataSnapshot.getChildren() ){
                    Registro r = obj.getValue(Registro.class);
                    listRegistro.add(r);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public Principal getInstance(){
        if (principal == null ) {
            FirebaseApp.initializeApp(getInstance());
            firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseDatabase.setPersistenceEnabled(true);
            databaseReference = firebaseDatabase.getReference();
        }
        return principal;
    }


}
