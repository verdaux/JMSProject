package com.scorpz.JMSProject;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class QueueBrowserDemo
{  
	public static void main(String[] args)
	{
		InitialContext initialContext = null;
		Connection connection = null;
		
		try
		{
			initialContext = new InitialContext();
			ConnectionFactory cf= (ConnectionFactory) initialContext.lookup("ConnectionFactory");
			connection = cf.createConnection();
			Session session = connection.createSession();
			
			Queue queue = (Queue) initialContext.lookup("queue/myQueue");
			MessageProducer producer = session.createProducer(queue);
			
			TextMessage message1 = session.createTextMessage("Do the dougie 1 and browse");
			TextMessage message2 = session.createTextMessage("Do the dougie 2 and browse");
			System.out.println("Message sent :: "+message1+"\n and \n"+message2);
			
			producer.send(message1);
			producer.send(message2);
			
			QueueBrowser browser = session.createBrowser(queue);
			Enumeration messagesEnum = browser.getEnumeration();
			
			while (messagesEnum.hasMoreElements())
			{
				TextMessage eachMessage= (TextMessage)messagesEnum.nextElement();
				System.out.println("browsing:: "+eachMessage.getText());
			}
			
			MessageConsumer consumer = session.createConsumer(queue);
			
			connection.start();
			TextMessage receivedMessage = (TextMessage) consumer.receive(5000);
			System.out.println("Message 1st received:: "+receivedMessage.getText());
			 receivedMessage = (TextMessage) consumer.receive(5000);
			System.out.println("Message received:: "+receivedMessage.getText());
		}
		catch (NamingException e)
		{
			e.printStackTrace();
		} catch (JMSException e)
		{
			e.printStackTrace();
		}
		finally {
			if(initialContext!=null)
			{
				try
				{
					initialContext.close();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			if(connection != null)
			{
				try
				{
					connection.close();
				} catch (Exception e2)
				{
					e2.printStackTrace();
				}
			}
		}
	}

}
