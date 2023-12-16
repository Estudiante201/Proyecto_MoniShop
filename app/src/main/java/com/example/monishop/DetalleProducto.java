package com.example.monishop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monishop.model.CarritoProducto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DetalleProducto extends AppCompatActivity {

    TextView tvDetalleNombreProducto, tvDetallePrecio, tvDetalleDescripcion, tvCantidad;
    Button btnDisminuir, btnAumentar, btnAddCart;
    ImageView ivDetalleFoto;
    FloatingActionButton btnDetalleProductoRegresar;
    SharedPreferences sharedPreferences;
    private static final String PREF_NAME_CARRITO="CarritoPref";
    private ArrayList<CarritoProducto> lista_producto;
    int idProducto;
    int cantidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);

        tvDetalleNombreProducto=findViewById(R.id.tvDetalleNombreProducto);
        tvDetallePrecio=findViewById(R.id.tvDetallePrecio);
        tvDetalleDescripcion=findViewById(R.id.tvDetalleDescripcion);
        btnAddCart=findViewById(R.id.btnDetalleAddCart);
        ivDetalleFoto=findViewById(R.id.ivDetalleFoto);
        tvCantidad=findViewById(R.id.tvDetalleCantidad);
        btnDisminuir=findViewById(R.id.btnDisminuir);
        btnAumentar=findViewById(R.id.btnAumentar);
        btnDetalleProductoRegresar=findViewById(R.id.btnDetalleProductoRegresar);

        sharedPreferences=getSharedPreferences(PREF_NAME_CARRITO, MODE_PRIVATE);

        lista_producto=getArrayList();


        Bundle extras=getIntent().getExtras();
        idProducto=extras.getInt("idProducto");
        String urlimagen=extras.getString("urlimagen");
        String nombreProducto=extras.getString("nombreProducto");
        double precio=extras.getDouble("precio");
        String descripcion=extras.getString("descripcion");
        String categoria=extras.getString("categoria");

        Picasso.get().load(urlimagen).into(ivDetalleFoto);
        tvDetalleNombreProducto.setText(nombreProducto);
        tvDetallePrecio.setText(String.format("%.2f", precio));
        tvDetalleDescripcion.setText(descripcion);

        btnAumentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cantidad=Integer.parseInt(tvCantidad.getText().toString());
                cantidad++;
                tvCantidad.setText(String.valueOf(cantidad));
            }
        });

        btnDisminuir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cantidad=Integer.parseInt(tvCantidad.getText().toString());
                if (cantidad > 1){
                    cantidad--;
                }
                tvCantidad.setText(String.valueOf(cantidad));
            }
        });

        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cantidad=Integer.parseInt(tvCantidad.getText().toString());

                boolean mismoproducto=actualizaMismoProductoCarrito();

                if(mismoproducto==false){
                    lista_producto.add(new CarritoProducto(idProducto, urlimagen, nombreProducto, precio, descripcion, categoria, cantidad));

                    saveData(lista_producto);
                }

                Toast.makeText(DetalleProducto.this, "Se agrego el producto al carrito", Toast.LENGTH_SHORT).show();
            }
        });

        btnDetalleProductoRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetalleProducto.this, Catalogo.class);
                startActivity(intent);
            }
        });
    }

    private void saveData(ArrayList<CarritoProducto> taskList) {
            SharedPreferences sharedPreferences=getSharedPreferences(PREF_NAME_CARRITO, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(taskList);
            editor.putString("task_list", json);
            editor.apply();
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

    private Boolean actualizaMismoProductoCarrito(){
        boolean resultado=false;

        if (lista_producto.size()==0){
            resultado=false;

        }else {
            for (int i=0;i<lista_producto.size();i++) {
                if(idProducto==lista_producto.get(i).getIdProducto()){
                    int cantidadNueva=lista_producto.get(i).getCantidad() + cantidad;
                    lista_producto.get(i).setCantidad(cantidadNueva);

                    LimpiarSharedPreferences();

                    SharedPreferences sharedPreferences=getSharedPreferences(PREF_NAME_CARRITO, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(lista_producto);
                    editor.putString("task_list", json);
                    editor.apply();

                    resultado=true;
                }
            }
        }

        return resultado;
    }

    private void LimpiarSharedPreferences(){
        SharedPreferences sharedPreferences2=getSharedPreferences(PREF_NAME_CARRITO, MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences2.edit();
        editor.clear();
        editor.commit();
    }

}