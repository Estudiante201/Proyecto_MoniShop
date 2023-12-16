package com.example.monishop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monishop.db.DatabaseHelper;
import com.example.monishop.model.Producto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class UpdateProducto extends AppCompatActivity {

    TextView etEditUrlimagen, etEditNombreProducto, etEditPrecio, etEditDescripcion;
    FloatingActionButton btnDetalleRegresar;
    Button btnActualizar, btnEliminar;
    Spinner spnEditCategoria;
    ImageView ivFoto, ivCargarFoto;
    //ImageView imageView;
    int id=0;
    DatabaseHelper databaseHelper;
    Producto producto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_producto);

        etEditUrlimagen=findViewById(R.id.etEditUrlimagen);
        etEditNombreProducto=findViewById(R.id.etEditNombreProducto);
        etEditPrecio=findViewById(R.id.etEditPrecio);
        etEditDescripcion=findViewById(R.id.etEditDescripcion);
        spnEditCategoria=findViewById(R.id.spnEditCategoria);
        btnActualizar=findViewById(R.id.btnActualizar);
        btnEliminar=findViewById(R.id.btnEliminar);
        btnDetalleRegresar=findViewById(R.id.btnActualizarRegresar);
        ivFoto=findViewById(R.id.ivFoto);
        ivCargarFoto=findViewById(R.id.ivCargarFoto);

        databaseHelper=new DatabaseHelper(this);

        Bundle extras=getIntent().getExtras();
        int idProducto=extras.getInt("idProducto");
        String urlimagen=extras.getString("urlimagen");
        String nombreProducto=extras.getString("nombreProducto");
        double precio=extras.getDouble("precio");
        String descripcion=extras.getString("descripcion");
        String categoria=extras.getString("categoria");

        id=idProducto;
        Picasso.get().load(urlimagen).into(ivFoto);
        etEditUrlimagen.setText(urlimagen);
        etEditNombreProducto.setText(nombreProducto);
        etEditPrecio.setText(String.format("%.2f", precio));
        etEditDescripcion.setText(descripcion);

        switch (categoria){
            case "accesorio":
                spnEditCategoria.setSelection(0);
                break;
            case "cuidado personal":
                spnEditCategoria.setSelection(1);
                break;
            case "ropa":
                spnEditCategoria.setSelection(2);
                break;
            case "cuidado de la belleza":
                spnEditCategoria.setSelection(3);
                break;
            case "articulo hogar":
                spnEditCategoria.setSelection(4);
                break;
        }

        ivCargarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlimagen=etEditUrlimagen.getText().toString();

                Picasso.get().load(urlimagen).into(ivFoto);
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idProducto=id;
                String urlimagen=etEditUrlimagen.getText().toString();
                String nombreProducto=etEditNombreProducto.getText().toString();
                double precio=Double.parseDouble(etEditPrecio.getText().toString());
                String descripcion=etEditDescripcion.getText().toString();
                String categoria=spnEditCategoria.getSelectedItem().toString();

                Boolean actualizarProducto=databaseHelper.updateProduct(idProducto, urlimagen, nombreProducto, precio, descripcion, categoria);

                if (actualizarProducto){
                    Toast.makeText(UpdateProducto.this, "Se actualizo", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idProducto=id;

                Boolean eliminarProducto=databaseHelper.deleteProduct(idProducto);

                if (eliminarProducto){
                    Toast.makeText(UpdateProducto.this, "Se han eliminado el producto", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDetalleRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateProducto.this, ListaProducto.class);
                startActivity(intent);
            }
        });

    }
}