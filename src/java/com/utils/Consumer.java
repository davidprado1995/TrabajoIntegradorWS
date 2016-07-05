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
                                Gson gson = new Gson();
				Pedido pedidoHecho = gson.fromJson(pedidoJson, Pedido.class);
				Connection con=DBConexion.getConnection();

				//modificar a tabla de pedido
				String sql = "INSERT INTO pedido (costototal, cliente) values(?,?)";
				PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.setFloat(1,pedidoHecho.costoTotal);
				stmt.setString(2,pedidoHecho.cliente);
				stmt.executeUpdate();
				
				
				//Insertar ventas con valor de idpedido
				ResultSet rs = stmt.getGeneratedKeys();
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


