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

//Esta clase no se usa en este webservice, Morzan usala para el web app del cliente.
public class Venta {
    
    public int cantidad;
    public float costo;
    public Producto prod;
    public int idventa;

    public Venta(int cantidad, Producto prod) {
        this.cantidad = cantidad;
        this.costo = cantidad*prod.precioProducto;
        this.prod = prod;
        
    }  
    
    public Venta(){
        
    }

    public Venta(int cantidad, float costo, Producto prod) {
        this.cantidad = cantidad;
        this.costo = costo;
        this.prod = prod;
    }
    
    
        
}