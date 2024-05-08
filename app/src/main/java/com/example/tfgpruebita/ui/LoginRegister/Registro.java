package com.example.tfgpruebita.ui.LoginRegister;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tfgpruebita.MainActivity;
import com.example.tfgpruebita.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {

    private EditText nombreEditText, correoEditText, contrasenaEditText;
    private Button registroButton;

    private Button btnVolver;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        nombreEditText = findViewById(R.id.nombre);
        correoEditText = findViewById(R.id.correo);
        contrasenaEditText = findViewById(R.id.contrasena);
        registroButton = findViewById(R.id.registro);
        btnVolver = findViewById(R.id.btnVolver);

        registroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarUsuario();
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registro.this, Principal.class);
                startActivity(intent);
            }
        });
    }

    private void registrarUsuario() {
        String nombre = nombreEditText.getText().toString().trim();
        String correo = correoEditText.getText().toString().trim();
        String contrasena = contrasenaEditText.getText().toString().trim();
        String contrasenaEncriptada = Encriptar.hashString(contrasena);

        mAuth.createUserWithEmailAndPassword(correo, contrasenaEncriptada)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            guardarUsuarioEnFirestore(nombre, correo, contrasenaEncriptada);
                        } else {
                            Toast.makeText(Registro.this, "Error en el registro: " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void guardarUsuarioEnFirestore(String nombre, String correo, String contrasena) {
        Map<String, Object> usuario = new HashMap<>();
        usuario.put("nombre", nombre);
        usuario.put("correo", correo);
        usuario.put("contrasena", contrasena);

        db.collection("usuario")
                .add(usuario)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Registro.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Registro.this, Login.class));
                            finish();
                        } else {
                            Toast.makeText(Registro.this, "Error al guardar en Firestore: " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

