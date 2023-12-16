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

import com.example.monishop.DetalleProducto;
import com.example.monishop.R;
import com.example.monishop.model.Producto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CatalogoProductoAdapter extends RecyclerView.Adapter<CatalogoProductoAdapter.ViewHolver> {
    private ArrayList<Producto> product_list;
    private Context context;
    private ArrayList<Producto> listaOriginal;

    public CatalogoProductoAdapter(ArrayList<Producto> product_list, Context context) {
        this.product_list = product_list;
        this.context = context;
        listaOriginal=new ArrayList<>();
        listaOriginal.addAll(product_list);
    }

    @NonNull
    @Override
    public CatalogoProductoAdapter.ViewHolver onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row_product, parent, false);
        return new ViewHolver(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogoProductoAdapter.ViewHolver holder, int position) {
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
                    Intent intent=new Intent(context, DetalleProducto.class);
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

    public void filtrado(final String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            product_list.clear();
            product_list.addAll(listaOriginal);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Producto> collecion = product_list.stream()
                        .filter(i -> i.getNombreProducto().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                product_list.clear();
                product_list.addAll(collecion);
            } else {
                for (Producto c : listaOriginal) {
                    if (c.getNombreProducto().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        product_list.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
}
