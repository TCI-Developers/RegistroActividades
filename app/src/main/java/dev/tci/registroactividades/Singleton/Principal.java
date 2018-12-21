package dev.tci.registroactividades.Singleton;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Principal extends AppCompatActivity {

    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;
    private static Principal principal;
    StorageReference storageRef;
    private Principal() {

    }

    public static Principal getInstance(){
        if (principal == null ) {
            principal = new Principal();
        }
        return principal;
    }

    public void InicializarFirebase(){
            FirebaseApp.initializeApp(getInstance());
            firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseDatabase.setPersistenceEnabled(true);
            databaseReference = firebaseDatabase.getReference();
            storageRef = FirebaseStorage.getInstance().getReference();
    }

}
