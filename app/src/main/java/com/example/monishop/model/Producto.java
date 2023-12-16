package com.example.monishop.model;

public class Producto {
    private int idProducto;
    private String urlimagen;
    private String nombreProducto;
    private double precio;
    private String descripcion;
    private String categoria;

    public Producto() {
    }

    public Producto(int idProducto, String urlimagen, String nombreProducto, double precio, String descripcion, String categoria) {
        this.idProducto = idProducto;
        this.urlimagen = urlimagen;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.descripcion = descripcion;
        this.categoria = categoria;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getUrlimagen() {
        return urlimagen;
    }

    public void setUrlimagen(String urlimagen) {
        this.urlimagen = urlimagen;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
