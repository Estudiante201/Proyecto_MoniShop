package com.example.monishop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.monishop.db.DatabaseHelper;
import com.example.monishop.model.CarritoProducto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Tarjeta extends AppCompatActivity {

    EditText etNombre, etTarjetaNumero, etTarjetaFecha, etTarjetaaCVV;
    Button btnPagar;
    ArrayList<CarritoProducto> elementos;
    private static final String PREF_NAME_CARRITO="CarritoPref";
    private static final String PREF_NAME_CLIENTE="ClientePref";
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjeta);

        etNombre=findViewById(R.id.etNombre);
        etTarjetaNumero=findViewById(R.id.etTarjetaNumero);
        etTarjetaFecha=findViewById(R.id.etTarjetaFecha);
        etTarjetaaCVV=findViewById(R.id.etTarjetaaCVV);
        btnPagar=findViewById(R.id.btnPagar);

        dbHelper= new DatabaseHelper(this);

        elementos=new ArrayList<CarritoProducto>();
        elementos=getArrayList();

        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tarjetaNombre=etNombre.getText().toString();
                String tarjetaNumero=etTarjetaNumero.getText().toString();
                String tarjetaFecha=etTarjetaFecha.getText().toString();
                String tarjetaaCVV=etTarjetaaCVV.getText().toString();

                //datos delivery
                Bundle extras=getIntent().getExtras();
                String departamento=extras.getString("departamento");
                String distrito=extras.getString("distrito");
                String direccion=extras.getString("direccion");
                String fechaEntrega=extras.getString("fechaEntrega");
                String horaEntrega=extras.getString("horaEntrega");

                //idCliente
                SharedPreferences sharedPreferences1=getSharedPreferences(PREF_NAME_CLIENTE, MODE_PRIVATE);
                int idCliente=sharedPreferences1.getInt("idCliente", 0);
                String nombreCompleto=sharedPreferences1.getString("nombreCompleto", null);

                //datos factura
                String fecha=fechaActual();
                double total=total();

                if(tarjetaNombre.isEmpty() || tarjetaNumero.isEmpty() || tarjetaFecha.isEmpty() || tarjetaaCVV.isEmpty()){
                    Toast.makeText(Tarjeta.this, "Falta completa los campos requeridos", Toast.LENGTH_SHORT).show();
                }else {
                    boolean registrarDelivery=dbHelper.insertDataDelivery(departamento, distrito, direccion, fechaEntrega, horaEntrega);
                    boolean registrarFactura=dbHelper.insertDataFactura(fecha, total, idCliente);

                    if(registrarDelivery && registrarFactura){
                        registrarDetalleFactura();
                        //envia datos para obtener la visualizacion factura
                        Intent intent=new Intent(Tarjeta.this, PagoExitoso.class);
                        intent.putExtra("nombreCompleto", nombreCompleto);
                        intent.putExtra("fecha", fecha);

                        startActivity(intent);
                    }
                }
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

    public String fechaActual(){
        Date fecha1=new Date();
        SimpleDateFormat f1=new SimpleDateFormat("dd-MM-yyyy");
        return f1.format(fecha1);
    }

    private double total(){
        double itemTotal=0;
        for(int i=0;i<elementos.size();i++){
            itemTotal += elementos.get(i).getPrecio() * elementos.get(i).getCantidad();
        }
        return itemTotal+10;
    }

    private void registrarDetalleFactura(){
        int idFactura=dbHelper.getIdFactura();

        for (int i=0;i<elementos.size();i++) {

            int idProducto=elementos.get(i).getIdProducto();
            String urlimagen=elementos.get(i).getUrlimagen();
            String nombreProducto=elementos.get(i).getNombreProducto();
            double precioVenta=elementos.get(i).getPrecio();
            String descripcion=elementos.get(i).getDescripcion();
            String categoria=elementos.get(i).getCategoria();
            int cantidad=elementos.get(i).getCantidad();

            dbHelper.insertDataDetalleFactura(cantidad, precioVenta, idFactura, idProducto);
        }
    }

}