package com.example.monishop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.monishop.adapter.CarritoProductoAdapter;
import com.example.monishop.model.CarritoProducto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CarritoCompra extends AppCompatActivity {

    public TextView tvItemTotal, tvTotal;
    Button btnCheckOut;
    FloatingActionButton btnCarritoRegresar;
    RecyclerView recyclerViewCarrito;
    ArrayList<CarritoProducto> elementos;
    SharedPreferences sharedPreferences;

    private static final String PREF_NAME_CARRITO="CarritoPref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito_compra);

        tvItemTotal=findViewById(R.id.tvItemTotal);
        tvTotal=findViewById(R.id.tvTotal);
        btnCheckOut=findViewById(R.id.btnCheckOut);
        btnCarritoRegresar=findViewById(R.id.btnCarritoRegresar);

        sharedPreferences=getSharedPreferences(PREF_NAME_CARRITO, MODE_PRIVATE);

        elementos=new ArrayList<CarritoProducto>();
        elementos=getArrayList();


        recyclerViewCarrito=findViewById(R.id.recyclerViewCarrito);
        recyclerViewCarrito.setHasFixedSize(true);
        recyclerViewCarrito.setLayoutManager(new LinearLayoutManager(this));

        CarritoProductoAdapter carritoProductoAdapter=new CarritoProductoAdapter(elementos,CarritoCompra.this);
        recyclerViewCarrito.setAdapter(carritoProductoAdapter);

        resultadoCarrito();


        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CarritoCompra.this, Delivery.class);
                startActivity(intent);
            }
        });

        btnCarritoRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CarritoCompra.this, Catalogo.class);
                startActivity(intent);
            }
        });

    }

    private ArrayList<CarritoProducto> getArrayList() {
        //ArrayList<Producto> elementos=new ArrayList<>();
        ArrayList<CarritoProducto> elementos=new ArrayList<CarritoProducto>();
        SharedPreferences sharedPreferences=getSharedPreferences(PREF_NAME_CARRITO, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task_list", null);
        Type type = new TypeToken<ArrayList<CarritoProducto>>() {}.getType();
        elementos = gson.fromJson(json, type);

        if (elementos == null) {
            elementos = new ArrayList<>();
        }
        return elementos;
    }


    //lista_producto.add(new CarritoProducto(idProducto, urlimagen1, nombreProducto2, precio3, descripcion4, categoria5, cantidad6));
    private double itemTotal(){
        double suma=0;
        for(int i=0;i<elementos.size();i++){
            suma += elementos.get(i).getPrecio() * elementos.get(i).getCantidad();
        }
        return suma;
    }

    private void resultadoCarrito(){
        double itemTotal=itemTotal();
        double delivery=10;
        double total=itemTotal + delivery;

        tvItemTotal.setText("S/ "+String.format("%.2f", itemTotal));
        tvTotal.setText("S/ " + String.format("%.2f", total));
    }

}