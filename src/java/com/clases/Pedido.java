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
    public float costoTotal=0.0f;
    public String cliente;
    public List<Venta> ventas;


    public Pedido(String cliente, List<Venta> ventas) {
        this.cliente = cliente;
        this.ventas = ventas;
        for (int i = 0; i < ventas.size(); i++) {
            costoTotal=costoTotal+ventas.get(i).costo;
        }
        
    }
    
    public Pedido(){
            
        }
    
    
    
}