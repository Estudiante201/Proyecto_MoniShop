package com.example.monishop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.monishop.db.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class CreateProducto extends AppCompatActivity {

    ImageView ivFotografia, ivCargarImagen;
    EditText etRegistrarUrlimagen, etRegistrarNombreProducto, etRegistrarPrecio, etRegistrarDescripcion;
    Spinner spnCategoria;
    Button btnAgregar;
    FloatingActionButton btnRegistrarRegresar;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_producto);

        ivFotografia=findViewById(R.id.ivFotografia);
        etRegistrarUrlimagen=findViewById(R.id.etRegistrarUrlimagen);
        etRegistrarNombreProducto=findViewById(R.id.etRegistrarNombreProducto);
        etRegistrarPrecio=findViewById(R.id.etRegistrarPrecio);
        etRegistrarDescripcion=findViewById(R.id.etRegistrarDescripcion);
        spnCategoria=findViewById(R.id.spnCategoria);
        btnAgregar=findViewById(R.id.btnAgregar);
        ivCargarImagen=findViewById(R.id.ivCargarImagen);
        btnRegistrarRegresar=findViewById(R.id.btnRegistrarRegresar);

        dbHelper= new DatabaseHelper(this);

        ivCargarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlimagen=etRegistrarUrlimagen.getText().toString();

                Picasso.get().load(urlimagen).into(ivFotografia);

            }
        });

       btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlimagen=etRegistrarUrlimagen.getText().toString();
                String nombreProducto=etRegistrarNombreProducto.getText().toString();
                double precio=0.0;
                if(!etRegistrarPrecio.getText().toString().isEmpty()){
                    precio=Double.parseDouble(etRegistrarPrecio.getText().toString());
                }
                String descripcion=etRegistrarDescripcion.getText().toString();
                String categoria=spnCategoria.getSelectedItem().toString();

                if(urlimagen.equals("") || nombreProducto.equals("") || precio==0.0 || descripcion.equals("")){
                    Toast.makeText(CreateProducto.this, "Falta completa los campos requeridos", Toast.LENGTH_SHORT).show();
                }else {
                    boolean registrarProducto=dbHelper.insertDataProducto(urlimagen, nombreProducto, precio, descripcion, categoria);

                    if (registrarProducto){
                        Toast.makeText(CreateProducto.this, "Registrado Exitosamente", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnRegistrarRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateProducto.this, ListaProducto.class);
                startActivity(intent);
            }
        });

    }
}