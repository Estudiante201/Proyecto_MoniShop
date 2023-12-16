package com.example.monishop.model;

public class Factura {
    private int idFactura;
    private String fecha;
    private double total;
    private int idCliente;
    private int idDelivery;

    public Factura() {
    }

    public Factura(int idFactura, String fecha, double total, int idCliente, int idDelivery) {
        this.idFactura = idFactura;
        this.fecha = fecha;
        this.total = total;
        this.idCliente = idCliente;
        this.idDelivery = idDelivery;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdDelivery() {
        return idDelivery;
    }

    public void setIdDelivery(int idDelivery) {
        this.idDelivery = idDelivery;
    }
}
