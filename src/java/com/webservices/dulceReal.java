/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webservices;

import com.clases.Producto;
import com.google.gson.Gson;
import com.utils.DBConexion;
import com.utils.DemoServer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author USER
 */
@WebService(serviceName = "dulceReal")
public class dulceReal {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "conseguirProds")
    public String conseguirProds() {
        //TODO write your implementation code here:
        List<Producto> listaProductos = new ArrayList<Producto>();
        Connection con=DBConexion.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        String sql="SELECT nombreProducto, precioProducto, idproducto FROM tablaproductos";
        try {
            ps=con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){               
                Producto p = new Producto(rs.getString(1), rs.getFloat(2), rs.getInt(3));
                listaProductos.add(p);
            }
            rs.close();
        } catch (SQLException ex) {                        
        }
        String prods=new Gson().toJson(listaProductos);
        return prods;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "cargarALaCola")
    public String cargarALaCola(@WebParam(name = "pedido") String pedido) {
        //TODO write your implementation code here:
        DemoServer ds = new DemoServer();
        ds.agregarACola(pedido);
        return "Mensaje enviado a cola";
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "loguearCliente")
    public boolean loguearCliente(@WebParam(name = "usuario") String usuario, @WebParam(name = "clave") String clave) {
        //TODO write your implementation code here:
        Connection con=DBConexion.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        boolean encontrado = false;
        String sql="SELECT idusuariosclientes, contrasena FROM usuariosclientes";
         try {
            ps=con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next() && encontrado==false){
                if(usuario.equalsIgnoreCase(rs.getString(1)) /*&& clave.equalsIgnoreCase(rs.getString(2))*/){
                 encontrado=true;   
                }
            }
            rs.close();
        } catch (SQLException ex) {                        
        }if(encontrado==false){
                return false;
            }else{
                return true;
            }
        
    }
    
}
