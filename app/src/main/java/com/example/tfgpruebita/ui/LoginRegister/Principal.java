package com.example.tfgpruebita.ui.LoginRegister;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfgpruebita.R;

public class Principal extends AppCompatActivity {

        private Button btnRegistro;
        private Button btnLogin;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.principal);

                btnRegistro = findViewById(R.id.button2);
                btnLogin = findViewById(R.id.button3);

                btnRegistro.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Intent intent = new Intent(Principal.this, Registro.class);
                                startActivity(intent);
                        }
                });

                btnLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Intent intent = new Intent(Principal.this, Login.class);
                                startActivity(intent);
                        }
               });
}
}