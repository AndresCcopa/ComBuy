package com.example.raul.combuyappv20.data.Local;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductoLocal {
    @SerializedName("nomproducto")
    @Expose     private String nomproducto;
    @SerializedName("descripcion")
    @Expose     private String descripcion;
    @SerializedName("etiqueta")
    @Expose     private String etiqueta;

    public ProductoLocal(String nomproducto, String descripcion, String etiqueta) {
        this.nomproducto = nomproducto;
        this.descripcion = descripcion;
        this.etiqueta = etiqueta;
    }

    public String getNomproducto() {
        return nomproducto;
    }

    public void setNomproducto(String nomproducto) {
        this.nomproducto = nomproducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }
}
