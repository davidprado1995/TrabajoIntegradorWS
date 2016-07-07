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

    public Venta(int cantidad, float costo, Producto prod, int idventa) {
        this.cantidad = cantidad;
        this.costo = costo;
        this.prod = prod;
        this.idventa = idventa;
    }
    
    

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public Producto getProd() {
        return prod;
    }

    public void setProd(Producto prod) {
        this.prod = prod;
    }

    public int getIdventa() {
        return idventa;
    }

    public void setIdventa(int idventa) {
        this.idventa = idventa;
    }
    
    
        
}