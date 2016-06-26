/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;


import org.apache.activemq.*;
import javax.jms.*;

public class DemoServer {
	
	private static ActiveMQConnectionFactory connectionFactory;
	private static Connection connection;
	private static Session session;
	private static Destination destination;
	private static boolean transacted = false;

	public void agregarACola(String jsonPedido){
		try {
			connectionFactory = new ActiveMQConnectionFactory(
			ActiveMQConnection.DEFAULT_USER,
			ActiveMQConnection.DEFAULT_PASSWORD,
			ActiveMQConnection.DEFAULT_BROKER_URL);
			
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(transacted,Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue("ColaPedidos");
			
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			
			
			//Enviamos un mensaje
                        TextMessage message = session.createTextMessage(jsonPedido);			
			//Enviamos un mensaje
			producer.send(message);

		} 
		catch (JMSException e) {
			System.out.print(e);
		}
	}
}

