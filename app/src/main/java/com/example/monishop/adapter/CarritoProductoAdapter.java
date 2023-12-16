package com.example.monishop.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monishop.CarritoCompra;
import com.example.monishop.R;
import com.example.monishop.model.CarritoProducto;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CarritoProductoAdapter extends RecyclerView.Adapter<CarritoProductoAdapter.ViewHolver> {
    private ArrayList<CarritoProducto> product_list;
    private Context context;

    public CarritoProductoAdapter(ArrayList<CarritoProducto> product_list, Context context) {
        this.product_list = product_list;
        this.context = context;
    }

    @NonNull
    @Override
    public CarritoProductoAdapter.ViewHolver onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row_carrito, parent, false);
        return new ViewHolver(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarritoProductoAdapter.ViewHolver holder, int position) {
        //llenando datos para autocompleta la lista recyclerview
        Picasso.get().load(product_list.get(position).getUrlimagen()).into(holder.imagenProducto);

        holder.nombreProducto.setText(product_list.get(position).getNombreProducto());
        holder.precio.setText(String.format("%.2f", product_list.get(position).getPrecio()));
        holder.cantidad.setText(String.valueOf(product_list.get(position).getCantidad()));
        double montoObtener= product_list.get(position).getPrecio() * product_list.get(position).getCantidad();
        holder.monto.setText(String.format("%.2f", montoObtener));

        holder.amumentaCantidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cant=product_list.get(position).getCantidad();
                cant++;
                product_list.get(position).setCantidad(cant);

                actualizarCarrito();

                Intent intent = new Intent(context, CarritoCompra.class);
                context.startActivity(intent);
            }
        });

        holder.disminuirCantidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cant=product_list.get(position).getCantidad();
                cant--;
                product_list.get(position).setCantidad(cant);

                if (product_list.get(position).getCantidad()==0){
                    product_list.remove(position);
                }

                actualizarCarrito();

                Intent intent = new Intent(context, CarritoCompra.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return product_list.size();
    }

    public class ViewHolver extends RecyclerView.ViewHolder {
        TextView nombreProducto, precio, cantidad, monto;
        ImageView imagenProducto;
        Button amumentaCantidad, disminuirCantidad;
        public ViewHolver(@NonNull View itemView) {
            super(itemView);

            nombreProducto=itemView.findViewById(R.id.tvCarritoNombreProducto);
            precio=itemView.findViewById(R.id.tvCarritoPrecio);
            cantidad=itemView.findViewById(R.id.tvCarritoCantidad);
            monto=itemView.findViewById(R.id.tvCarritoMonto);
            imagenProducto=itemView.findViewById(R.id.ivCarritoFoto);
            amumentaCantidad=itemView.findViewById(R.id.btnAum);
            disminuirCantidad=itemView.findViewById(R.id.btnDism);
        }
    }

    private Boolean actualizarCarrito(){

        LimpiarSharedPreferences();

        SharedPreferences sharedPreferences=context.getSharedPreferences("CarritoPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(product_list);
        editor.putString("task_list", json);
        editor.apply();

        return true;
    }

    private void LimpiarSharedPreferences(){
        SharedPreferences sharedPreferences2= context.getSharedPreferences("CarritoPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences2.edit();
        editor.clear();
        editor.commit();
    }
}
