package com.example.monishop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.BoringLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.monishop.db.DatabaseHelper;

public class IniciarSesion extends AppCompatActivity {

    private EditText etUsuario, etContrasenia;
    Button btnIniciarSesion;
    SharedPreferences sharedPreferences;
    private static final String PREF_NAME_CLIENTE="ClientePref";
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        etUsuario=findViewById(R.id.etUsuario);
        etContrasenia=findViewById(R.id.etContrasenia);
        btnIniciarSesion=findViewById(R.id.btnIniciarSesion);

        dbHelper= new DatabaseHelper(this);

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario=etUsuario.getText().toString();
                String clave=etContrasenia.getText().toString();

                if(usuario.equals("admin") && clave.equals("1234")){
                    Intent intent = new Intent(IniciarSesion.this, ListaProducto.class);
                    startActivity(intent);
                }else {
                    boolean checkLogin=dbHelper.checkLogin(usuario, clave);

                    if(checkLogin){
                        ObtenerDatosCliente(usuario, clave);
                        Intent intent = new Intent(IniciarSesion.this, Catalogo.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(IniciarSesion.this, "Usuario y/o Contraseña incorrecto", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void ObtenerDatosCliente(String username, String password){
        DatabaseHelper db=new DatabaseHelper(this);

        sharedPreferences=getSharedPreferences(PREF_NAME_CLIENTE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int idUsuario=0;

        Cursor cursorUsuario=null;
        cursorUsuario=db.getReadableDatabase().rawQuery("Select idUsuario from usuarios where username = ? and password = ? LIMIT 1", new String[]{username, password});


        if (cursorUsuario.moveToFirst()){
            do{
                idUsuario=cursorUsuario.getInt(0);
            } while (cursorUsuario.moveToNext());
        }

        cursorUsuario.close();

        Cursor cursorCliente=null;
        cursorCliente=db.getReadableDatabase().rawQuery("Select * from clientes where idUsuario = "+idUsuario, null);


        if (cursorCliente.moveToFirst()){
            do{
                editor.putInt("idCliente", cursorCliente.getInt(0));
                editor.putString("nombreCompleto", cursorCliente.getString(1));
                editor.putString("dni", cursorCliente.getString(2));
                editor.putString("telefono", cursorCliente.getString(3));
                editor.putString("correo", cursorCliente.getString(4));
                editor.putString("idUsuario", cursorCliente.getString(5));
                editor.apply();
            } while (cursorCliente.moveToNext());
        }

        cursorCliente.close();

    }
}