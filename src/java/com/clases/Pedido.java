/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clases;

import java.util.List;

/**
 *
 * @author USER
 */

//Esta clase no se usa en este webservice, Morzan usala para el web app del cliente.
public class Pedido {
    public int idpedido;
    public float costoTotal=0.0f;
    public String cliente;
    public List<Venta> ventas;
    public String fecha;


    public Pedido(String cliente, List<Venta> ventas, String fecha) {
        this.cliente = cliente;
        this.ventas = ventas;
        this.fecha = fecha;
        for (int i = 0; i < ventas.size(); i++) {
            costoTotal=costoTotal+ventas.get(i).costo;
        }
        
    }
    
    public Pedido(){
            
        }
    
    public Pedido(int idpedido, float costototal, String cliente, String fecha){
        this.idpedido=idpedido;
        this.costoTotal=costototal;
        this.cliente=cliente;
        this.fecha=fecha;
    }
    
    

    public int getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(int idpedido) {
        this.idpedido = idpedido;
    }

    public float getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(float costoTotal) {
        this.costoTotal = costoTotal;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public List<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    
    
}