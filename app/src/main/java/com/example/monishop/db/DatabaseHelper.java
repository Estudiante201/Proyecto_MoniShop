package com.example.monishop.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.monishop.model.Cliente;
import com.example.monishop.model.Factura;
import com.example.monishop.model.Producto;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Moni_Shop.db";
    private static final int DATABASE_VERSION = 1;
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS usuarios (idUsuario INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT);");
        db.execSQL("CREATE TABLE IF NOT EXISTS clientes (idCliente INTEGER PRIMARY KEY AUTOINCREMENT, nombreCompleto TEXT, dni TEXT, telefono TEXT, correo TEXT, idUsuario INTEGER);");
        db.execSQL("CREATE TABLE IF NOT EXISTS productos (idProducto INTEGER PRIMARY KEY AUTOINCREMENT, urlimagen TEXT, nombreProducto TEXT, precio REAL, descripcion TEXT, categoria TEXT);");
        db.execSQL("CREATE TABLE IF NOT EXISTS delivery (idDelivery INTEGER PRIMARY KEY AUTOINCREMENT, departamento TEXT, distrito TEXT, direccion TEXT, fechaEntrega TEXT, horaEntrega TEXT);");
        db.execSQL("CREATE TABLE IF NOT EXISTS factura (idFactura INTEGER PRIMARY KEY AUTOINCREMENT, fecha TEXT, total REAL, idCliente INTEGER, idDelivery INTEGER);");
        db.execSQL("CREATE TABLE IF NOT EXISTS detallefactura (idDetallefactura INTEGER PRIMARY KEY AUTOINCREMENT, cantidad INTEGER, precioVenta REAL, idFactura INTEGER, idProducto INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    //INSERT TABLA USUARIO
    public Boolean insertDataUsuario(String username, String password){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);

        long result=db.insert("usuarios", null, contentValues);

        if(result==-1){
            return false;
        }else {
            return true;
        }
    }

    //INSERT TABLA CLIENTE
    public Boolean insertDataCliente(String nombreCompleto, String dni, String telefono, String correo){
        SQLiteDatabase db=this.getWritableDatabase();

        int idUsuario=0;
        Cursor cursorUsuario=null;

        cursorUsuario=db.rawQuery("SELECT * FROM usuarios ORDER BY idUsuario DESC LIMIT 1", null );

        if (cursorUsuario.moveToFirst()){
            do{
                idUsuario=cursorUsuario.getInt(0);
            } while (cursorUsuario.moveToNext());
        }

        cursorUsuario.close();

        ContentValues contentValues = new ContentValues();
        contentValues.put("nombreCompleto", nombreCompleto);
        contentValues.put("dni", dni);
        contentValues.put("telefono", telefono);
        contentValues.put("correo", correo);
        contentValues.put("idUsuario", idUsuario);

        long result=db.insert("clientes", null, contentValues);

        if(result==-1){
            return false;
        }else {
            return true;
        }
    }

    public Boolean checkUsuario(String username){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("Select * from usuarios where username = ?", new String[]{username});

        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }

    public Boolean checkLogin(String username, String password){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("Select * from usuarios where username = ? and password = ?", new String[]{username, password});

        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }

    }

    //INSERT TABLA PRODUCTO
    public Boolean insertDataProducto(String urlimagen, String nombreProducto, double precio, String descripcion, String categoria){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("urlimagen", urlimagen);
        contentValues.put("nombreProducto", nombreProducto);
        contentValues.put("precio", precio);
        contentValues.put("descripcion", descripcion);
        contentValues.put("categoria", categoria);

        long result=db.insert("productos", null, contentValues);

        if(result==-1){
            return false;
        }else {
            return true;
        }
    }

    public ArrayList<Producto> getAllProducto() {
        SQLiteDatabase db=this.getWritableDatabase();

        ArrayList<Producto> listaProducto=new ArrayList<>();
        Producto producto=null;
        Cursor cursorProducto=null;

        cursorProducto=db.rawQuery("SELECT * FROM productos", null);

        if (cursorProducto.moveToFirst()){
            do{
                producto=new Producto();
                producto.setIdProducto(cursorProducto.getInt(0));
                producto.setUrlimagen(cursorProducto.getString(1));
                producto.setNombreProducto(cursorProducto.getString(2));
                producto.setPrecio(cursorProducto.getDouble(3));
                producto.setDescripcion(cursorProducto.getString(4));
                producto.setCategoria(cursorProducto.getString(5));
                listaProducto.add(producto);
            } while (cursorProducto.moveToNext());
        }

        cursorProducto.close();
        return listaProducto;
    }

    //base de datos mostrar por categoria del producto
    public ArrayList<Producto> getAllProducto(String categoria) {
        SQLiteDatabase db=this.getWritableDatabase();

        ArrayList<Producto> listaProducto=new ArrayList<>();
        Producto producto=null;
        Cursor cursorProducto=null;

        cursorProducto=db.rawQuery("SELECT * FROM productos WHERE categoria ='"+categoria+"'", null );

        if (cursorProducto.moveToFirst()){
            do{
                producto=new Producto();
                producto.setIdProducto(cursorProducto.getInt(0));
                producto.setUrlimagen(cursorProducto.getString(1));
                producto.setNombreProducto(cursorProducto.getString(2));
                producto.setPrecio(cursorProducto.getDouble(3));
                producto.setDescripcion(cursorProducto.getString(4));
                producto.setCategoria(cursorProducto.getString(5));
                listaProducto.add(producto);
            } while (cursorProducto.moveToNext());
        }

        cursorProducto.close();
        return listaProducto;
    }

    public Boolean updateProduct(int idProducto, String urlimagen, String nombreProducto, double precio, String descripcion, String categoria) {
        SQLiteDatabase db=this.getWritableDatabase();

        try{
            db.execSQL("UPDATE productos SET urlimagen = '"+urlimagen+"' , nombreProducto = '"+nombreProducto+"' , precio = "+precio+
                    " , descripcion = '"+descripcion+"' , categoria = '"+categoria+"' WHERE idProducto = "+idProducto);
            return true;
        } catch (Exception ex){
            return false;
        }
    }

    public Boolean deleteProduct(int idProducto) {
        SQLiteDatabase db=this.getWritableDatabase();

        try{
            db.execSQL("DELETE FROM productos WHERE idProducto = "+idProducto);
            return true;
        } catch (Exception ex){
            return false;
        }
    }

    public Boolean deleteAllProduct() {
        SQLiteDatabase db=this.getWritableDatabase();

        try{
            db.execSQL("DELETE FROM productos");
            return true;
        } catch (Exception ex){
            return false;
        }
    }


    //INSERT TABLA DELIVERY
    public Boolean insertDataDelivery(String departamento, String distrito, String direccion, String fechaEntrega, String horaEntrega){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("departamento", departamento);
        contentValues.put("distrito", distrito);
        contentValues.put("direccion", direccion);
        contentValues.put("fechaEntrega", fechaEntrega);
        contentValues.put("horaEntrega", horaEntrega);

        long result=db.insert("delivery", null, contentValues);

        if(result==-1){
            return false;
        }else {
            return true;
        }
    }

    //INSERT TABLA FACTURA
    public Boolean insertDataFactura(String fecha, double total, int idCliente){
        SQLiteDatabase db=this.getWritableDatabase();

        int idDelivery=0;

        Cursor cursorDelivery=null;
        cursorDelivery=db.rawQuery("SELECT * FROM delivery ORDER BY idDelivery DESC LIMIT 1", null );

        if (cursorDelivery.moveToFirst()){
            do{
                idDelivery=cursorDelivery.getInt(0);
            } while (cursorDelivery.moveToNext());
        }

        cursorDelivery.close();

        ContentValues contentValues = new ContentValues();
        contentValues.put("fecha", fecha);
        contentValues.put("total", total);
        contentValues.put("idCliente", idCliente);
        contentValues.put("idDelivery", idDelivery);

        long result=db.insert("factura", null, contentValues);

        if(result==-1){
            return false;
        }else {
            return true;
        }
    }

    //INSERT TABLA DETALLEFACTURA
    public Boolean insertDataDetalleFactura(int cantidad, double precioVenta, int idFactura, int idProducto){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("cantidad", cantidad);
        contentValues.put("precioVenta", precioVenta);
        contentValues.put("idFactura", idFactura);
        contentValues.put("idProducto", idProducto);

        long result=db.insert("detalleFactura", null, contentValues);

        if(result==-1){
            return false;
        }else {
            return true;
        }
    }

    //obtener ultimo idFactura
    public int getIdFactura() {
        SQLiteDatabase db=this.getWritableDatabase();

        int a=0;
        Cursor cursorFactura=null;

        cursorFactura=db.rawQuery("SELECT * FROM factura ORDER BY idFactura DESC LIMIT 1", null );

        if (cursorFactura.moveToFirst()){
            do{
                a=cursorFactura.getInt(0);
            } while (cursorFactura.moveToNext());
        }

        cursorFactura.close();
        return a;
    }

    //obtener la lista de los clientes
    public ArrayList<Cliente> getAllCliente() {
        SQLiteDatabase db=this.getWritableDatabase();

        ArrayList<Cliente> listaCliente=new ArrayList<>();
        Cliente cliente=null;
        Cursor cursorCliente=null;

        cursorCliente=db.rawQuery("SELECT * FROM clientes", null);

        if (cursorCliente.moveToFirst()){
            do{
                cliente=new Cliente();
                cliente.setIdCliente(cursorCliente.getInt(0));
                cliente.setNombreCompleto(cursorCliente.getString(1));
                cliente.setDni(cursorCliente.getString(2));
                cliente.setTelefono(cursorCliente.getString(3));
                cliente.setCorreo(cursorCliente.getString(4));
                cliente.setIdUsuario(cursorCliente.getInt(5));
                listaCliente.add(cliente);
            } while (cursorCliente.moveToNext());
        }

        cursorCliente.close();
        return listaCliente;
    }

    //obtener la factura
    public ArrayList<Factura> getAllFactura() {
        SQLiteDatabase db=this.getWritableDatabase();

        ArrayList<Factura> listaFactura=new ArrayList<>();
        Factura factura=null;
        Cursor cursorFactura=null;

        cursorFactura=db.rawQuery("SELECT * FROM factura", null);

        if (cursorFactura.moveToFirst()){
            do{
                factura=new Factura();
                factura.setIdFactura(cursorFactura.getInt(0));
                factura.setFecha(cursorFactura.getString(1));
                factura.setTotal(cursorFactura.getDouble(2));
                factura.setIdCliente(cursorFactura.getInt(3));
                factura.setIdDelivery(cursorFactura.getInt(4));
                listaFactura.add(factura);
            } while (cursorFactura.moveToNext());
        }

        cursorFactura.close();
        return listaFactura;
    }

}
