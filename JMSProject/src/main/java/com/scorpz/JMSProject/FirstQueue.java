package com.scorpz.JMSProject;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class FirstQueue
{  
	public static void main(String[] args)
	{
		InitialContext initialContext = null;
		
		try
		{
			initialContext = new InitialContext();
			ConnectionFactory cf= (ConnectionFactory) initialContext.lookup("ConnectionFactory");
			Connection connection = cf.createConnection();
			Session session = connection.createSession();
			
			Queue queue = (Queue) initialContext.lookup("queue/myQueue");
			MessageProducer producer = session.createProducer(queue);
			
			TextMessage message = session.createTextMessage("Do the dougie");
			System.out.println("Message sent:: "+message);
			
			producer.send(message);
			
			MessageConsumer consumer = session.createConsumer(queue);
			
			connection.start();
			TextMessage receivedMessage = (TextMessage) consumer.receive(5000);
			System.out.println("Message received:: "+receivedMessage.getText());
		}
		catch (NamingException e)
		{
			e.printStackTrace();
		} catch (JMSException e)
		{
			e.printStackTrace();
		}
	}

}
