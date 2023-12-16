package com.example.monishop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monishop.R;
import com.example.monishop.UpdateProducto;
import com.example.monishop.model.Producto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolver> {
    private ArrayList<Producto> product_list;
    private Context context;

    public ProductoAdapter(ArrayList<Producto> product_list, Context context) {
        this.product_list = product_list;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductoAdapter.ViewHolver onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row_product, parent, false);
        return new ViewHolver(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoAdapter.ViewHolver holder, int position) {
        //llenando datos para autocompleta la lista recyclerview
        Picasso.get().load(product_list.get(position).getUrlimagen()).into(holder.imagenProducto);

        holder.nombreProducto.setText(product_list.get(position).getNombreProducto());
        holder.precio.setText(String.format("%.2f", product_list.get(position).getPrecio()));
    }

    @Override
    public int getItemCount() {
        return product_list.size();
    }

    public class ViewHolver extends RecyclerView.ViewHolder {
        TextView nombreProducto, precio;
        ImageView imagenProducto;
        public ViewHolver(@NonNull View itemView) {
            super(itemView);

            nombreProducto=itemView.findViewById(R.id.tvCatalogoNombreProducto);
            precio=itemView.findViewById(R.id.tvCatalogoPrecio);
            imagenProducto=itemView.findViewById(R.id.ivCatalogoFoto);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context=view.getContext();
                    Intent intent=new Intent(context, UpdateProducto.class);
                    intent.putExtra("idProducto", product_list.get(getAdapterPosition()).getIdProducto());
                    intent.putExtra("urlimagen", product_list.get(getAdapterPosition()).getUrlimagen());
                    intent.putExtra("nombreProducto", product_list.get(getAdapterPosition()).getNombreProducto());
                    intent.putExtra("precio", product_list.get(getAdapterPosition()).getPrecio());
                    intent.putExtra("descripcion", product_list.get(getAdapterPosition()).getDescripcion());
                    intent.putExtra("categoria", product_list.get(getAdapterPosition()).getCategoria());
                    context.startActivity(intent);
                }
            });
        }
    }
}
