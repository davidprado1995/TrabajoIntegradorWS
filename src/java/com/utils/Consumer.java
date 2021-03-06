package com.utils;

import com.clases.Pedido;
import com.google.gson.Gson;
import javax.jms.Message;
import javax.jms.*;
import javax.jms.MessageListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Consumer implements MessageListener {
	
	public void onMessage(Message arg0) {

		try {
			if (arg0 instanceof TextMessage) {
				//Codigo para jalar datos de la cola y grabarlo en bd
				String pedidoJson= ((TextMessage)arg0).getText();
				System.out.println("Mensaje Recibido = "+ pedidoJson);
                                
				System.out.println("Wiiii");
                                Gson gson = new Gson();
				Pedido pedidoHecho = gson.fromJson(pedidoJson, Pedido.class);
                                System.out.println(pedidoHecho.costoTotal + "\n" + pedidoHecho.cliente);
				Connection con=DBConexion.getConnection();

				//modificar a tabla de pedido
				String sql = "INSERT INTO pedido (costototal, cliente, fecha) values(?,?,?)";
                                System.out.println("se creó la sentencia");
				PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.setFloat(1,pedidoHecho.costoTotal);
				stmt.setString(2,pedidoHecho.cliente);
                                stmt.setString(3, pedidoHecho.fecha);
                                System.out.println("Se tiene el pedido listo para la base de datos");
				stmt.executeUpdate();
                                System.out.println("Listo!!! se agregó el pedido");
				
				
				//Insertar ventas con valor de idpedido
				ResultSet rs = stmt.getGeneratedKeys();
                                System.out.println("Espero se agreguen las ventas");
				rs.next();
				int auto_id = rs.getInt(1);
				for(int i=0;i<pedidoHecho.ventas.size();i++){
                                    String sql2 = "INSERT INTO venta (cantidad, costo, idpedido, idproducto) values(?,?,?,?)";
                                    PreparedStatement stmt2 = con.prepareStatement(sql2);
                                    stmt2.setInt(1,pedidoHecho.ventas.get(i).cantidad);
                                    stmt2.setFloat(2,pedidoHecho.ventas.get(i).costo);
                                    stmt2.setInt(4,pedidoHecho.ventas.get(i).prod.idProducto);
                                    stmt2.setInt(3,auto_id);
                                    stmt2.executeUpdate();
				}
                                con.commit();
                                rs.close();
				con.close();
			} 
			else if (arg0 instanceof ObjectMessage) {
				System.out.println("Mensaje Recibido : " +((ObjectMessage)arg0).getObject());
			}
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (SQLException ex) {
                Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
}


