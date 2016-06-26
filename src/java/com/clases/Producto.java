/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clases;

/**
 *
 * @author USER
 */
public class Producto {
    
    public String nombreProducto;
    public float precioProducto;
    public int idProducto;

    public Producto(String nombreProducto, float precioProducto, int idProducto) {
        this.nombreProducto = nombreProducto;
        this.precioProducto = precioProducto;
        this.idProducto = idProducto;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombreProducto + " Precio: " + precioProducto + " Id: " + idProducto;
    }
    
    

   
    
}
