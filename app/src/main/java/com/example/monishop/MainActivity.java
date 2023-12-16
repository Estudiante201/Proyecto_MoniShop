package com.example.monishop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnAcceder, btnRegistrate;
    private static final String PREF_NAME_CARRITO="CarritoPref";
    private static final String PREF_NAME_CLIENTE="ClientePref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAcceder=findViewById(R.id.buttonAcceder);
        btnRegistrate=findViewById(R.id.buttonRegistrate);

        LimpiarCarritoSharedPreferences();
        LimpiarClienteSharedPreferences();

        btnAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, IniciarSesion.class);
                startActivity(intent);
            }
        });

        btnRegistrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CrearCuenta.class);
                startActivity(intent);
            }
        });

    }

    private void LimpiarCarritoSharedPreferences(){
        SharedPreferences sharedPreferences=getSharedPreferences(PREF_NAME_CARRITO, MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    private void LimpiarClienteSharedPreferences(){
        SharedPreferences sharedPreferences=getSharedPreferences(PREF_NAME_CLIENTE, MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}