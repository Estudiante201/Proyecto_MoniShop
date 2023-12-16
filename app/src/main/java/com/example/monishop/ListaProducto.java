package com.example.monishop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.monishop.adapter.ProductoAdapter;
import com.example.monishop.db.DatabaseHelper;
import com.example.monishop.model.Producto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListaProducto extends AppCompatActivity {

    FloatingActionButton btnAdd, btnCliente;
    Button btnDeleteAll;
    ImageView ivCerrarSesion;
    RecyclerView recyclerViewProducto;
    ArrayList<Producto> elementos;
    DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_producto);

        btnAdd=findViewById(R.id.btnAdd);
        btnCliente=findViewById(R.id.btnCliente);
        btnDeleteAll=findViewById(R.id.btnDeleteAll);
        ivCerrarSesion=findViewById(R.id.ivCerrarSesion);

        dbHelper= new DatabaseHelper(this);

        recyclerViewProducto=findViewById(R.id.recyclerViewCatalogo);
        recyclerViewProducto.setHasFixedSize(true);
        recyclerViewProducto.setLayoutManager(new GridLayoutManager(this, 2));

        elementos=new ArrayList<Producto>();
        ProductoAdapter productAdapter=new ProductoAdapter(dbHelper.getAllProducto(), this);
        recyclerViewProducto.setAdapter(productAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaProducto.this, CreateProducto.class);
                startActivity(intent);
            }
        });

        btnCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaProducto.this, Reporte.class);
                startActivity(intent);
            }
        });

        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean eliminarAllProducto=dbHelper.deleteAllProduct();

                if (eliminarAllProducto){
                    Toast.makeText(ListaProducto.this, "Se han eliminado todo", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ListaProducto.this, ListaProducto.class);
                    startActivity(intent);
                }
            }
        });

        ivCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaProducto.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}