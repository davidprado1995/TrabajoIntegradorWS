/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webservices;

import com.clases.Producto;
import com.clases.Pedido;
import com.clases.Venta;
import com.google.gson.Gson;
import com.utils.DBConexion;
import com.utils.DemoCliente;
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
        String sql="SELECT * FROM usuariosclientes WHERE idusuariosclientes = ? AND "
                + "contrasena = ?";
         try {
            ps=con.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, clave);
            rs = ps.executeQuery();
            if(rs.next()){  
                 encontrado=true;  
            }
            rs.close();
        } catch (SQLException ex) {                        
        }if(encontrado==false){
                return false;
            }else{
                return true;
            }
        
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "cargarABaseDeDatos")
    public String cargarABaseDeDatos() {
        //TODO write your implementation code here:
        DemoCliente dc = new DemoCliente();
        dc.cargaraBaseDeDatos();
        return "Pedido Cargado";
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "conseguirPedidos")
    public String conseguirPedidos() {
        //TODO write your implementation code here:
        
        List<Pedido> listaPedidos = new ArrayList<Pedido>();
        Connection con=DBConexion.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        String sql="SELECT idpedido, costototal, cliente, fecha FROM pedido";
        try {
            ps=con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){      
                System.out.println("Existe algo");
                Pedido p = new Pedido(rs.getInt(1), rs.getFloat(2), rs.getString(3), rs.getString(4));
                listaPedidos.add(p);
            }
            rs.close();
        } catch (SQLException ex) {                        
        }
        String pedidos=new Gson().toJson(listaPedidos);
        
        return pedidos;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "loguearVendedor")
    public boolean loguearVendedor(@WebParam(name = "usuarioAdm") String usuarioAdm, @WebParam(name = "clave") String clave) {
        Connection con=DBConexion.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        boolean encontrado = false;
        String sql="SELECT * FROM arqsw.usuariosvendedores WHERE idusuarios = ? AND "
                + "contrasena = ?";
         try {
            ps=con.prepareStatement(sql);
            ps.setString(1, usuarioAdm);
            ps.setString(2, clave);
            rs = ps.executeQuery();
            if(rs.next()){  
                 encontrado=true;  
            }
            rs.close();
        } catch (SQLException ex) {                        
        }if(encontrado==false){
                return false;
            }else{
                return true;
            }
        
    
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "conseguirVentas")
    public String conseguirVentas(@WebParam(name = "idpedido") int idpedido) {
        //TODO write your implementation code here:
        List<Venta> listaVentas = new ArrayList<Venta>();
        Connection con=DBConexion.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        String sql="SELECT idventa, cantidad, costo, idpedido, idproducto FROM venta WHERE idpedido = ?";
        try {
            System.out.println("Se mandó la sentencia");
            ps=con.prepareStatement(sql);
            ps.setInt(1, idpedido);
            rs = ps.executeQuery();
            Venta v = null;
            while(rs.next()){
                System.out.println("Corrió la sentencia\n" + rs.getFloat(2) + "\n" + rs.getInt(5));
                int idventa = rs.getInt(1);
                String  sql2="SELECT nombreProducto, precioProducto, idproducto FROM tablaproductos WHERE idproducto=?";
                PreparedStatement ps2=con.prepareStatement(sql2);
                ps2.setInt(1, rs.getInt(5));
                ResultSet rs2= ps2.executeQuery();
                while(rs2.next()){
                    System.out.println("Existe el prducto con id " + rs2.getInt(3));
                Producto prod=new Producto(rs2.getString(1), rs2.getFloat(2), rs2.getInt(3));
                v = new Venta(rs.getInt(1), rs.getFloat(2), prod);
                }
                //se pierde el idventa en el bucle; esto es para que no se pierda
                v.setIdventa(idventa);
                listaVentas.add(v);
            }
            rs.close();
        } catch (SQLException ex) {                        
        }
        String ventas=new Gson().toJson(listaVentas);
        System.out.println(ventas);
        return ventas;
    }
    
    /*public Producto gettearProducto(int idproducto){
        Connection con=DBConexion.getConnection();
        ResultSet rs;
        String  sql2="SELECT nombreProducto, precioProducto, idproducto FROM tablaproductos WHERE idproducto=?";
        PreparedStatement ps2=con.prepareStatement(sql2);
        
                ps2.setInt(1, rs.getInt(5));
                ResultSet rs2= ps2.executeQuery();
                System.out.println("Existe el prducto con id " + rs2.getInt(3));
                Producto prod=new Producto(rs2.getString(1), rs2.getFloat(2), rs2.getInt(3));
    }*/

    /**
     * Web service operation
     */
    @WebMethod(operationName = "mostrarPorCliente")
    public String mostrarPorCliente(@WebParam(name = "idcliente") String idcliente) {
        //TODO write your implementation code here:
        List<Pedido> listaPedidos = new ArrayList<Pedido>();
        Connection con=DBConexion.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        String sql="SELECT idpedido, costototal, cliente, fecha FROM pedido WHERE cliente = ?";
        try {
            ps=con.prepareStatement(sql);
            ps.setString(1, idcliente);
            rs = ps.executeQuery();
            while(rs.next()){      
                System.out.println("Existe algo");
                Pedido p = new Pedido(rs.getInt(1), rs.getFloat(2), rs.getString(3), rs.getString(4));
                listaPedidos.add(p);
            }
            rs.close();
        } catch (SQLException ex) {                        
        }
        String pedidos=new Gson().toJson(listaPedidos);
        
        return pedidos;
    }
    
}
