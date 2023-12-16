package com.example.monishop;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monishop.model.CarritoProducto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class PagoExitoso extends AppCompatActivity {

    TextView tvFacturaCliente, tvFacturaFechaCompra, tvFacturaSubTotal, tvFacturaTotal;
    TableLayout tableLayoutFactura;
    Button btnDescargarComprobante, btnVolverInicio;
    ArrayList<CarritoProducto> elementos;

    String nombreCompleto=null;
    String fechaCompra=null;
    private static final String PREF_NAME_CARRITO="CarritoPref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago_exitoso);

        btnDescargarComprobante=findViewById(R.id.btnDescargarComprobante);
        btnVolverInicio=findViewById(R.id.btnVolverInicio);
        tvFacturaCliente=findViewById(R.id.tvFacturaCliente);
        tvFacturaFechaCompra=findViewById(R.id.tvFacturaFechaCompra);
        tableLayoutFactura=findViewById(R.id.tableLayoutFactura);
        tvFacturaSubTotal=findViewById(R.id.tvFacturaSubTotal);
        tvFacturaTotal=findViewById(R.id.tvFacturaTotal);

        elementos=new ArrayList<CarritoProducto>();
        elementos=getArrayList();

        Bundle extras=getIntent().getExtras();
        //String nombreCompleto=extras.getString("nombreCompleto");
        //String fechaCompra=extras.getString("fecha");

        nombreCompleto=extras.getString("nombreCompleto");
        fechaCompra=extras.getString("fecha");


        tvFacturaCliente.setText(nombreCompleto);
        tvFacturaFechaCompra.setText(fechaCompra);
        tablaFactura();

        tvFacturaSubTotal.setText("S/ "+String.format("%.2f", subtotal()));
        tvFacturaTotal.setText("S/ "+String.format("%.2f", (subtotal()+10)));

        LimpiarSharedPreferences();

        askPermissions();

        btnDescargarComprobante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generarPdf();
            }
        });

        btnVolverInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PagoExitoso.this, Catalogo.class);
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

    private void tablaFactura(){

        for (int i=0;i<elementos.size();i++) {
            TableRow tableRow=new TableRow(PagoExitoso.this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView tv1=new TextView(PagoExitoso.this);
            tv1.setText(elementos.get(i).getNombreProducto());
            tableRow.addView(tv1);
            tv1.setGravity(Gravity.CENTER);

            TextView tv2=new TextView(PagoExitoso.this);
            tv2.setText(String.valueOf(elementos.get(i).getCantidad()));
            tableRow.addView(tv2);
            tv2.setGravity(Gravity.CENTER);

            TextView tv3=new TextView(PagoExitoso.this);
            tv3.setText("S/ " + elementos.get(i).getPrecio());
            tableRow.addView(tv3);
            tv3.setGravity(Gravity.CENTER);

            tableLayoutFactura.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        }


    }

    private double subtotal(){

        double suma=0.0;

        for (int i=0;i<elementos.size();i++) {
            suma += elementos.get(i).getCantidad() *elementos.get(i).getPrecio();
        }
        return suma;
    }

    private void LimpiarSharedPreferences(){
        SharedPreferences sharedPreferences2=getSharedPreferences(PREF_NAME_CARRITO, MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences2.edit();
        editor.clear();
        editor.commit();
    }

    private void askPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 200);
    }

    private void generarPdf() {

        Paint paint = new Paint();
        TextPaint descripcion = new TextPaint();

        Bitmap bitmap, bitmapEscala;

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1080, 1920, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();


        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.monishop1);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 473, 200, false);
        canvas.drawBitmap(bitmapEscala, 300, 20, paint);

        String text = "Hello, World";
        float x = 500;
        float y = 900;

        //cliente
        paint.setColor(Color.rgb(142, 68, 173));
        paint.setFakeBoldText(true);
        paint.setTextSize(35);
        canvas.drawText("Cliente: ", 50, 270, paint);
        //document.finishPage(page);


        paint.setColor(Color.BLACK);
        paint.setFakeBoldText(false);
        paint.setTextSize(35);
        canvas.drawText(nombreCompleto, 180, 270, paint);

        //fecha de compra
        paint.setColor(Color.rgb(142, 68, 173));
        paint.setFakeBoldText(true);
        paint.setTextSize(35);
        canvas.drawText("Fecha Compra: ", 50, 320, paint);


        paint.setColor(Color.BLACK);
        paint.setFakeBoldText(false);
        paint.setTextSize(35);
        canvas.drawText(fechaCompra, 310, 320, paint);

        paint.setColor(Color.BLACK);
        paint.setFakeBoldText(false);
        paint.setTextSize(35);

        descripcion.setTextSize(32);

        int ya = 400;

        double subtotal=0.0;

        for (int i=0;i<elementos.size();i++) {
            double precioporCantidad =elementos.get(i).getCantidad()*elementos.get(i).getPrecio();

            String obtenerDato=elementos.get(i).getNombreProducto()+ "   (cant: "+elementos.get(i).getCantidad()+") x (precio: "
                    +String.format("%.2f", elementos.get(i).getPrecio())+" = S/ "+String.format("%.2f", precioporCantidad);
            canvas.drawText(obtenerDato, 50, ya, descripcion);
            ya += 70;
            subtotal +=elementos.get(i).getCantidad()*elementos.get(i).getPrecio();

        }

        paint.setColor(Color.BLACK);
        paint.setFakeBoldText(false);
        paint.setTextSize(35);
        canvas.drawText("__________________________", 500, ya, paint);

        ya +=50;

        paint.setColor(Color.BLACK);
        paint.setFakeBoldText(true);
        paint.setTextSize(35);
        canvas.drawText("Subtotal: S/ "+String.format("%.2f", subtotal), 680, ya, paint);

        ya +=50;

        paint.setColor(Color.BLACK);
        paint.setFakeBoldText(false);
        paint.setTextSize(35);
        canvas.drawText("Delivery: S/ 10", 680, ya, paint);

        ya +=50;

        canvas.drawText("__________________________", 500, ya, paint);
        ya +=50;

        paint.setColor(Color.BLACK);
        paint.setFakeBoldText(false);
        paint.setTextSize(35);
        canvas.drawText("Tota: S/ "+String.format("%.2f", (subtotal+10)), 680, ya, paint);

        ya +=50;


        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.codigo_qr);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap, 300, 300, false);
        canvas.drawBitmap(bitmapEscala, 350, ya, paint);




        document.finishPage(page);

        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String fileName = "MoniShop.pdf";
        File file = new File(downloadsDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            document.writeTo(fos);
            document.close();
            fos.close();
            Toast.makeText(this, "Written Successfully!!!", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            Log.d("mylog", "Error while writing " + e.toString());
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}