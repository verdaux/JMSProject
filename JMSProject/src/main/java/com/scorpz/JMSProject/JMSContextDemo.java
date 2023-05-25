package com.scorpz.JMSProject;

import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class JMSContextDemo
{

	public static void main(String[] args) throws Exception
	{
		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/myQueue");
		
		try(
				ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext()
			)
		{
			jmsContext.createProducer().send(queue, "New way to connect to JMS");
			String msgreceiveBody = jmsContext.createConsumer(queue).receiveBody(String.class);
			
			System.out.println(msgreceiveBody);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
