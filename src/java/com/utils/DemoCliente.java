package com.utils;
import org.apache.activemq.*;
import javax.jms.*;

public class DemoCliente{
	
	private static ActiveMQConnectionFactory connectionFactory;
	private static Connection connection;
	private static Session session;
	private static Destination destination;

	public void cargaraBaseDeDatos() {

		try {
			connectionFactory = new ActiveMQConnectionFactory(
			ActiveMQConnection.DEFAULT_USER,
			ActiveMQConnection.DEFAULT_PASSWORD,
			ActiveMQConnection.DEFAULT_BROKER_URL);
			connection = connectionFactory.createConnection();
			connection.start();
			
			session = connection.createSession(false,
			Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue("ColaPedidos");
			MessageConsumer consumer = session.createConsumer(destination);
			
			Consumer myConsumer = new Consumer();
			consumer.setMessageListener(myConsumer);
			Thread.sleep(3000);
			session.close();
			connection.close();
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}