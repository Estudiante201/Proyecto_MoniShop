package com.example.monishop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.monishop.adapter.CatalogoProductoAdapter;
import com.example.monishop.db.DatabaseHelper;
import com.example.monishop.model.Producto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Catalogo extends AppCompatActivity implements SearchView.OnQueryTextListener {

    //implements SearchView.OnQueryTextListener
    TextView MostrarAccesorio, MostrarCuidadoBelleza, MostrarArticuloHogar, MostrarCuidadoPersonal, MostrarRopa;
    FloatingActionButton btnCerrar;
    ImageView ivCarritoCompra;
    SearchView searchViewBusqueda;
    RecyclerView recyclerViewCatalogoProducto;
    CatalogoProductoAdapter catalogoProductoAdapter;
    ArrayList<Producto> elementos;
    private static final String PREF_NAME_CARRITO="CarritoPref";
    private static final String PREF_NAME_CLIENTE="ClientePref";
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);

        dbHelper= new DatabaseHelper(this);

        MostrarAccesorio=findViewById(R.id.textViewAccesorio);
        MostrarCuidadoBelleza=findViewById(R.id.textViewCuidadoBelleza);
        MostrarArticuloHogar=findViewById(R.id.textViewArticuloHogar);
        MostrarCuidadoPersonal=findViewById(R.id.textViewCuidadoPersonal);
        MostrarRopa=findViewById(R.id.textViewRopa);
        btnCerrar=findViewById(R.id.btnCerrar);
        ivCarritoCompra=findViewById(R.id.ivCarritoCompra);
        searchViewBusqueda=findViewById(R.id.searchViewBusqueda);

        recyclerViewCatalogoProducto=findViewById(R.id.recyclerViewCatalogoProducto);

        recyclerViewCatalogoProducto.setHasFixedSize(true);
        recyclerViewCatalogoProducto.setLayoutManager(new GridLayoutManager(this, 2));

        elementos=new ArrayList<Producto>();
        catalogoProductoAdapter=new CatalogoProductoAdapter(dbHelper.getAllProducto(), this);
        recyclerViewCatalogoProducto.setAdapter(catalogoProductoAdapter);

        searchViewBusqueda.setOnQueryTextListener(this);


        MostrarAccesorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //base datos buscar categoria
                MostrarAccesorio.setTextColor(Color.RED);
                MostrarCuidadoBelleza.setTextColor(Color.DKGRAY);
                MostrarArticuloHogar.setTextColor(Color.DKGRAY);
                MostrarCuidadoPersonal.setTextColor(Color.DKGRAY);
                MostrarRopa.setTextColor(Color.DKGRAY);

                String categoria="accesorio";

                elementos=new ArrayList<Producto>();
                CatalogoProductoAdapter catalogoProductoAdapter=new CatalogoProductoAdapter(dbHelper.getAllProducto("accesorio"), Catalogo.this);
                recyclerViewCatalogoProducto.setAdapter(catalogoProductoAdapter);
            }
        });

        MostrarCuidadoBelleza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MostrarAccesorio.setTextColor(Color.DKGRAY);
                MostrarCuidadoBelleza.setTextColor(Color.RED);
                MostrarArticuloHogar.setTextColor(Color.DKGRAY);
                MostrarCuidadoPersonal.setTextColor(Color.DKGRAY);
                MostrarRopa.setTextColor(Color.DKGRAY);

                String categoria="cuidado de la belleza";

                elementos=new ArrayList<Producto>();
                CatalogoProductoAdapter catalogoProductoAdapter=new CatalogoProductoAdapter(dbHelper.getAllProducto("cuidado de la belleza"), Catalogo.this);
                recyclerViewCatalogoProducto.setAdapter(catalogoProductoAdapter);
            }
        });

        MostrarArticuloHogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MostrarAccesorio.setTextColor(Color.DKGRAY);
                MostrarCuidadoBelleza.setTextColor(Color.DKGRAY);
                MostrarArticuloHogar.setTextColor(Color.RED);
                MostrarCuidadoPersonal.setTextColor(Color.DKGRAY);
                MostrarRopa.setTextColor(Color.DKGRAY);

                String categoria="articulo hogar";

                elementos=new ArrayList<Producto>();
                CatalogoProductoAdapter catalogoProductoAdapter=new CatalogoProductoAdapter(dbHelper.getAllProducto("articulo hogar"), Catalogo.this);
                recyclerViewCatalogoProducto.setAdapter(catalogoProductoAdapter);
            }
        });

        MostrarCuidadoPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MostrarAccesorio.setTextColor(Color.DKGRAY);
                MostrarCuidadoBelleza.setTextColor(Color.DKGRAY);
                MostrarArticuloHogar.setTextColor(Color.DKGRAY);
                MostrarCuidadoPersonal.setTextColor(Color.RED);
                MostrarRopa.setTextColor(Color.DKGRAY);

                String categoria="cuidado personal";

                elementos=new ArrayList<Producto>();
                CatalogoProductoAdapter catalogoProductoAdapter=new CatalogoProductoAdapter(dbHelper.getAllProducto("cuidado personal"), Catalogo.this);
                recyclerViewCatalogoProducto.setAdapter(catalogoProductoAdapter);
            }
        });

        MostrarRopa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MostrarAccesorio.setTextColor(Color.DKGRAY);
                MostrarCuidadoBelleza.setTextColor(Color.DKGRAY);
                MostrarArticuloHogar.setTextColor(Color.DKGRAY);
                MostrarCuidadoPersonal.setTextColor(Color.DKGRAY);
                MostrarRopa.setTextColor(Color.RED);

                String categoria="ropa";

                elementos=new ArrayList<Producto>();
                CatalogoProductoAdapter catalogoProductoAdapter=new CatalogoProductoAdapter(dbHelper.getAllProducto("ropa"), Catalogo.this);
                recyclerViewCatalogoProducto.setAdapter(catalogoProductoAdapter);
            }
        });

        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LimpiarSharedPreferences();

                Intent intent = new Intent(Catalogo.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ivCarritoCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Catalogo.this, CarritoCompra.class);
                startActivity(intent);
            }
        });
    }

    private void LimpiarSharedPreferences(){
        SharedPreferences sharedPreferences1=getSharedPreferences(PREF_NAME_CARRITO, MODE_PRIVATE);
        SharedPreferences.Editor editor1=sharedPreferences1.edit();
        editor1.clear();
        editor1.commit();

        SharedPreferences sharedPreferences2=getSharedPreferences(PREF_NAME_CLIENTE, MODE_PRIVATE);
        SharedPreferences.Editor editor2=sharedPreferences2.edit();
        editor2.clear();
        editor2.commit();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        MostrarAccesorio.setTextColor(Color.DKGRAY);
        MostrarCuidadoBelleza.setTextColor(Color.DKGRAY);
        MostrarArticuloHogar.setTextColor(Color.DKGRAY);
        MostrarCuidadoPersonal.setTextColor(Color.DKGRAY);
        MostrarRopa.setTextColor(Color.DKGRAY);

        elementos=new ArrayList<Producto>();
        catalogoProductoAdapter=new CatalogoProductoAdapter(dbHelper.getAllProducto(), this);
        recyclerViewCatalogoProducto.setAdapter(catalogoProductoAdapter);

        catalogoProductoAdapter.filtrado(s);
        return false;
    }
}