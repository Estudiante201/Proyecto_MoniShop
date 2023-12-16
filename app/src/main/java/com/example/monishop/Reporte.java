package com.example.monishop;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.monishop.db.DatabaseHelper;
import com.example.monishop.model.Cliente;
import com.example.monishop.model.Factura;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class Reporte extends AppCompatActivity {

    Button btnMostrarCliente, btnMostrarVenta;
    TextView tvListaReporte;
    TableLayout tableLayoutReporte;
    ArrayList<Cliente> elementos;
    ArrayList<Factura> elementosVenta;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte);

        btnMostrarCliente=findViewById(R.id.btnMostrarCliente);
        btnMostrarVenta=findViewById(R.id.btnMostrarVenta);
        tvListaReporte=findViewById(R.id.tvListaReporte);
        tableLayoutReporte=findViewById(R.id.tableLayoutReporte);

        dbHelper= new DatabaseHelper(this);

        elementos=new ArrayList<Cliente>();
        elementos=dbHelper.getAllCliente();

        elementosVenta=new ArrayList<Factura>();
        elementosVenta=dbHelper.getAllFactura();

        btnMostrarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvListaReporte.setText("Lista de Cliente");
                tablaCliente();
            }
        });

        btnMostrarVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvListaReporte.setText("Lista de Venta");
                tablaFactura();
            }
        });
    }

    private void tablaCliente() {
        tableLayoutReporte.removeAllViews();

        TableRow tableRow1 = new TableRow(Reporte.this);
        tableRow1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        TextView tv10 = new TextView(Reporte.this);
        tv10.setText("Nombre Completo");
        tv10.setPadding(10,10,10,10);
        tv10.setTextColor(Color.rgb(58, 77, 185));
        tv10.setTypeface(null, Typeface.BOLD);
        tableRow1.addView(tv10);
        tv10.setGravity(Gravity.CENTER);

        TextView tv20 = new TextView(Reporte.this);
        tv20.setText("DNI");
        tv20.setPadding(10,10,10,10);
        tv20.setTextColor(Color.rgb(58, 77, 185));
        tv20.setTypeface(null, Typeface.BOLD);
        tableRow1.addView(tv20);
        tv20.setGravity(Gravity.CENTER);

        TextView tv30 = new TextView(Reporte.this);
        tv30.setText("Telefono");
        tv30.setPadding(10,10,10,10);
        tv30.setTextColor(Color.rgb(58, 77, 185));
        tv30.setTypeface(null, Typeface.BOLD);
        tableRow1.addView(tv30);
        tv30.setGravity(Gravity.CENTER);

        TextView tv40 = new TextView(Reporte.this);
        tv40.setText("Correo");
        tv40.setPadding(10,10,10,10);
        tv40.setTextColor(Color.rgb(58, 77, 185));
        tv40.setTypeface(null, Typeface.BOLD);
        tableRow1.addView(tv40);
        tv40.setGravity(Gravity.CENTER);

        tableLayoutReporte.addView(tableRow1, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        for (int i = 0; i < elementos.size(); i++) {
            TableRow tableRow = new TableRow(Reporte.this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView tv1 = new TextView(Reporte.this);
            tv1.setText(elementos.get(i).getNombreCompleto());
            tv1.setPadding(10,10,10,10);
            tableRow.addView(tv1);
            tv1.setGravity(Gravity.CENTER);

            TextView tv2 = new TextView(Reporte.this);
            tv2.setText(elementos.get(i).getDni());
            tv2.setPadding(10,10,10,10);
            tableRow.addView(tv2);
            tv2.setGravity(Gravity.CENTER);

            TextView tv3 = new TextView(Reporte.this);
            tv3.setText(elementos.get(i).getTelefono());
            tv3.setPadding(10,10,10,10);
            tableRow.addView(tv3);
            tv3.setGravity(Gravity.CENTER);

            TextView tv4 = new TextView(Reporte.this);
            tv4.setText(elementos.get(i).getCorreo());
            tv4.setPadding(10,10,10,10);
            tableRow.addView(tv4);
            tv4.setGravity(Gravity.CENTER);

            tableLayoutReporte.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        }
    }

    private void tablaFactura() {
        tableLayoutReporte.removeAllViews();

        TableRow tableRow1 = new TableRow(Reporte.this);
        tableRow1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        TextView tv10 = new TextView(Reporte.this);
        tv10.setText("IdFactura");
        tv10.setPadding(10,10,10,10);
        tv10.setTextColor(Color.rgb(58, 77, 185));
        tv10.setTypeface(null, Typeface.BOLD);
        tableRow1.addView(tv10);
        tv10.setGravity(Gravity.CENTER);

        TextView tv20 = new TextView(Reporte.this);
        tv20.setText("Fecha");
        tv20.setPadding(10,10,10,10);
        tv20.setTextColor(Color.rgb(58, 77, 185));
        tv20.setTypeface(null, Typeface.BOLD);
        tableRow1.addView(tv20);
        tv20.setGravity(Gravity.CENTER);

        TextView tv30 = new TextView(Reporte.this);
        tv30.setText("Total");
        tv30.setPadding(10,10,10,10);
        tv30.setTextColor(Color.rgb(58, 77, 185));
        tv30.setTypeface(null, Typeface.BOLD);
        tableRow1.addView(tv30);
        tv30.setGravity(Gravity.CENTER);

        tableLayoutReporte.addView(tableRow1, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        for (int i = 0; i < elementos.size(); i++) {
            TableRow tableRow = new TableRow(Reporte.this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView tv1 = new TextView(Reporte.this);
            tv1.setText(elementosVenta.get(i).getIdFactura()+"");
            tv1.setPadding(10,10,10,10);
            tableRow.addView(tv1);
            tv1.setGravity(Gravity.CENTER);

            TextView tv2 = new TextView(Reporte.this);
            tv2.setText(elementosVenta.get(i).getFecha());
            tv2.setPadding(10,10,10,10);
            tableRow.addView(tv2);
            tv2.setGravity(Gravity.CENTER);

            TextView tv3 = new TextView(Reporte.this);
            tv3.setText("S/ "+elementosVenta.get(i).getTotal());
            tv3.setPadding(10,10,10,10);
            tableRow.addView(tv3);
            tv3.setGravity(Gravity.CENTER);

            tableLayoutReporte.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        }
    }

}