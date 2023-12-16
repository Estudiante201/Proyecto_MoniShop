package com.example.monishop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Delivery extends AppCompatActivity {

    EditText etDepartametno, etDistrito, etDireccion, etFecha, etHora;
    Button btnContinuarCompra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        btnContinuarCompra=findViewById(R.id.btnContinuarCompra);
        etDepartametno=findViewById(R.id.etDepartametno);
        etDistrito=findViewById(R.id.etDistrito);
        etDireccion=findViewById(R.id.etDireccion);
        etFecha=findViewById(R.id.etFecha);
        etHora=findViewById(R.id.etHora);

        btnContinuarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String departamento=etDepartametno.getText().toString();
                String distrito=etDistrito.getText().toString();
                String direccion=etDireccion.getText().toString();
                String fechaEntrega=etFecha.getText().toString();
                String horaEntrega=etHora.getText().toString();

                if(departamento.isEmpty() || distrito.isEmpty() || direccion.isEmpty() || fechaEntrega.isEmpty() || horaEntrega.isEmpty()){
                    Toast.makeText(Delivery.this, "Falta completa los campos requeridos", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent=new Intent(Delivery.this, Tarjeta.class);
                    intent.putExtra("departamento", departamento);
                    intent.putExtra("distrito", distrito);
                    intent.putExtra("direccion", direccion);
                    intent.putExtra("fechaEntrega", fechaEntrega);
                    intent.putExtra("horaEntrega", horaEntrega);
                    startActivity(intent);
                }

            }
        });
    }
}