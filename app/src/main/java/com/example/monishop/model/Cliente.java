package com.example.monishop.model;

public class Cliente {
    private int idCliente;
    private String nombreCompleto;
    private String dni;
    private String telefono;
    private String correo;
    private int idUsuario;

    public Cliente() {
    }

    public Cliente(int idCliente, String nombreCompleto, String dni, String telefono, String correo, int idUsuario) {
        this.idCliente = idCliente;
        this.nombreCompleto = nombreCompleto;
        this.dni = dni;
        this.telefono = telefono;
        this.correo = correo;
        this.idUsuario = idUsuario;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
