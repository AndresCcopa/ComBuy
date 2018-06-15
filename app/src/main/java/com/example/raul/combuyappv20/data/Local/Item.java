package com.example.raul.combuyappv20.data.Local;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("precio")
    @Expose     private double precio;
    @SerializedName("stock")
    @Expose     private int stock;
    @SerializedName("idlocalnegocio")
    @Expose     private Local idlocalnegocio;
    @SerializedName("idproductolocal")
    @Expose     private ProductoLocal idproductolocal;

    public Item(double precio, int stock, Local idlocalnegocio, ProductoLocal idproductolocal) {
        this.precio = precio;
        this.stock = stock;
        this.idlocalnegocio = idlocalnegocio;
        this.idproductolocal = idproductolocal;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Local getIdlocalnegocio() {
        return idlocalnegocio;
    }

    public void setIdlocalnegocio(Local idlocalnegocio) {
        this.idlocalnegocio = idlocalnegocio;
    }

    public ProductoLocal getIdproductolocal() {
        return idproductolocal;
    }

    public void setIdproductolocal(ProductoLocal idproductolocal) {
        this.idproductolocal = idproductolocal;
    }
}
