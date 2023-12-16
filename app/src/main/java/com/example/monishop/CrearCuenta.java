package com.example.monishop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.monishop.db.DatabaseHelper;

public class CrearCuenta extends AppCompatActivity {

    EditText etNombreCompleto, etDNI, etTelefono, etCorreoElectronico, etUsername, etPassword;
    Button btnCrearcuenta;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);

        etNombreCompleto=findViewById(R.id.etNombreCompleto);
        etDNI=findViewById(R.id.etDNI);
        etTelefono=findViewById(R.id.etTelefono);
        etCorreoElectronico=findViewById(R.id.etCorreoElectronico);
        etUsername=findViewById(R.id.etUsername);
        etPassword=findViewById(R.id.etPassword);
        btnCrearcuenta=findViewById(R.id.btnCrearcuenta);

        dbHelper= new DatabaseHelper(this);

        btnCrearcuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombreCompleto=etNombreCompleto.getText().toString();
                String dni=etDNI.getText().toString();
                String telefono=etTelefono.getText().toString();
                String correo=etCorreoElectronico.getText().toString();
                String usuario=etUsername.getText().toString();
                String clave=etPassword.getText().toString();
                if(nombreCompleto.isEmpty() || dni.isEmpty() || telefono.isEmpty() || correo.isEmpty() || usuario.isEmpty() || clave.isEmpty()){
                    Toast.makeText(CrearCuenta.this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
                }else{
                    boolean checkUsuario=dbHelper.checkUsuario(usuario);

                    if(checkUsuario){
                        Toast.makeText(CrearCuenta.this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
                    }else{
                        boolean registrarUsuario=dbHelper.insertDataUsuario(usuario, clave);
                        boolean registrarCliente=dbHelper.insertDataCliente(nombreCompleto, dni, telefono, correo);
                        if(registrarUsuario && registrarCliente){
                            Toast.makeText(CrearCuenta.this, "Registrado Exitosamente", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CrearCuenta.this, MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(CrearCuenta.this, "No se pudo Registrarse", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
}