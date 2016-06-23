/*
	Copyright (C) 2016  Julien Le Fur

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
	
*/
package com.julian.genesis.mqconnect;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import com.julian.genesis.mqconnect.helper.Literals;
import com.julian.genesis.mqconnect.helper.Options;

public class JmsFactory {

	/**
	 * Logger
	 */
	private static final Logger logger = LogManager.getLogger(JmsFactory.class);
	/**
	 * Create a connection factory.
	 */
	public static ConnectionFactory MyCreateConnectionFactory() throws JMSException {
		// TODO: Initial context not implemented
		/*
		 * // If an initial context was provided, returned connection factory
		 * already looked up if (InitContext.context != null) { return
		 * InitContext.connectionFactory; }
		 */
		if (Options.ConnectionType.Value().equals(Literals.RTT)) {
			// RTT connection factory
			return MyCreateConnectionFactoryRTT();
		} else if (Options.ConnectionType.Value().equals(Literals.WMQ)) {
			// WMQ connection factory
			return MyCreateConnectionFactoryWMQ();
		} else {
			// Should never come here
			return null;
		}
	} // end MyCreateConnectionFactory

	/**
	 * Create a RTT connection factory and set relevant properties.
	 */
	public static ConnectionFactory MyCreateConnectionFactoryRTT() throws JMSException {
		JmsConnectionFactory cf = null;

		try {
			// Create a connection factory
			JmsFactoryFactory ff = JmsFactoryFactory.getInstance(WMQConstants.RTT_PROVIDER);
			
			cf = ff.createConnectionFactory();

			// Set the properties
			cf.setStringProperty(WMQConstants.RTT_HOST_NAME, Options.HostnameRTT.Value());
			

			cf.setIntProperty(WMQConstants.RTT_PORT, Options.PortRTT.ValueAsNumber());

			cf.setIntProperty(WMQConstants.RTT_CONNECTION_PROTOCOL, Options.ProtocolRTT.ValueAsNumber());

			cf.setIntProperty(WMQConstants.RTT_MULTICAST, Options.MulticastModeRTT.ValueAsNumber());
		} catch (JMSException jmsex) {
			logger.error("Error: Unable to create RTT connection factory.");
			throw jmsex;
		}

		return cf;
	} // end MyCreateConnectionFactoryRTT

	/**
	 * Create a WMQ connection factory and set relevant properties.
	 */
	public static ConnectionFactory MyCreateConnectionFactoryWMQ() throws JMSException {
		JmsConnectionFactory cf = null;
		
		try {
			// Create a connection factory
			JmsFactoryFactory ff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
			
			cf = ff.createConnectionFactory();
			
			// Set the properties
			cf.setStringProperty(WMQConstants.WMQ_PROVIDER_VERSION, Options.ProviderVersion.Value());

			cf.setStringProperty(WMQConstants.WMQ_HOST_NAME, Options.HostnameWMQ.Value());
			
			cf.setIntProperty(WMQConstants.WMQ_PORT, Options.PortWMQ.ValueAsNumber());
			
			cf.setStringProperty(WMQConstants.WMQ_CHANNEL, Options.Channel.Value());
			
			cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, Options.ConnectionMode.ValueAsNumber());

			if (!Options.QueueManager.IsNull()) {
				cf.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, Options.QueueManager.Value());
				
			}

			// Broker version is only applicable for ProviderVersion unspecified
			// or v6
			if (Options.ProviderVersion.Value().equals(Literals.ProviderVersionUnspecified)
					|| Options.ProviderVersion.Value().equals(Literals.ProviderVersion6)) {
				cf.setIntProperty(WMQConstants.WMQ_BROKER_VERSION, Options.BrokerVersion.ValueAsNumber());

				if (Options.BrokerVersion.Value().equals(Literals.Integrator)) {
					cf.setStringProperty(WMQConstants.WMQ_BROKER_PUBQ, Options.BrokerPublishQueue.Value());
				}
			}

		} catch (JMSException jmsex) {
			logger.error("Error: Unable to create WMQ connection factory.");
			throw jmsex;
		}

		return cf;
	} // end MyCreateConnectionFactoryWMQ

	/**
	 * Create a connection with relevant values.
	 * 
	 * @param cf
	 *            The connection factory to use
	 */
	public static Connection MyCreateConnection(ConnectionFactory cf) throws JMSException {
		Connection connection = null;
		try {
			// Create a connection
			if (!Options.UserID.IsNull() && !Options.Password.IsNull()) {
				connection = cf.createConnection(Options.UserID.Value(), Options.Password.Value());
			} else {
				connection = cf.createConnection();
			}
		} catch (JMSException jmsex) {
			logger.error("Error: Unable to create connection using the connection factory.");
			throw jmsex;
		}
		return connection;
	} // end MyCreateConnection

	/**
	 * Create a session with relevant values.
	 * 
	 * @param connection
	 *            The connection to use
	 */
	public static Session MyCreateSession(Connection connection) throws JMSException {
		Session session = null;

		try {
			// Create a session
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		} catch (JMSException jmsex) {
			logger.error("Error: Unable to create a session using the connection.");
			throw jmsex;
		}

		return session;
	} // end MyCreateSession

	/**
	 * Create a destination with relevant values.
	 * 
	 * @param session
	 *            The session to use
	 */
	public static Destination MyCreateDestination(Session session) throws JMSException {
		/*
		 * // If an initial context was provided, returned destination already
		 * looked up if (InitContext.context != null) { return
		 * InitContext.destination; }
		 */
		Destination destination = null;

		try {
			if (Options.ConnectionType.Value().equals(Literals.RTT)) {
				// RTT destination
				destination = session.createTopic(Options.DestinationRTT.Value()); // Create
																					// a
																					// topic
			} else if (Options.ConnectionType.Value().equals(Literals.WMQ)) {
				// WMQ destination
				// Create a topic if the destination name starts with topic://.
				// Otherwise, default to
				// creating a queue.
				destination = (Options.DestinationWMQ.Value().startsWith("topic://"))
						? (Destination) session.createTopic(Options.DestinationWMQ.Value())
						: (Destination) session.createQueue(Options.DestinationWMQ.Value());
			} else {
				// Should never come here
				return null;
			}
		} catch (JMSException jmsex) {
			logger.error("Error: Unable to create the destination using the session.");
			throw jmsex;
		}

		return destination;
	} // end MyCreateDestination
	
	
	public static Destination MyCreateListenDestination(Session session) throws JMSException {
		/*
		 * // If an initial context was provided, returned destination already
		 * looked up if (InitContext.context != null) { return
		 * InitContext.destination; }
		 */
		Destination destination = null;

		try {
			if (Options.ConnectionType.Value().equals(Literals.RTT)) {
				// RTT destination
				destination = session.createTopic(Options.DestinationRTT.Value()); // Create
																					// a
																					// topic
			} else if (Options.ConnectionType.Value().equals(Literals.WMQ)) {
				// WMQ destination
				// Create a topic if the destination name starts with topic://.
				// Otherwise, default to
				// creating a queue.
				destination = (Options.ListenWMQ.Value().startsWith("topic://"))
						? (Destination) session.createTopic(Options.ListenWMQ.Value())
						: (Destination) session.createQueue(Options.ListenWMQ.Value());
			} else {
				// Should never come here
				return null;
			}
		} catch (JMSException jmsex) {
			logger.error("Error: Unable to create the destination using the session.");
			throw jmsex;
		}

		return destination;
	} // end MyCreateDestination
	

	/**
	 * Create a producer and set relevant properties.
	 * 
	 * @param session
	 *            The session to use
	 * @param destination
	 *            The destination to use
	 */
	public static MessageProducer MyCreateProducer(Session session, Destination destination) throws JMSException {
		MessageProducer producer = null;

		try {
			producer = session.createProducer(destination);
			producer.setDeliveryMode(Options.DeliveryModeWMQ.ValueAsNumber());
		} catch (JMSException jmsex) {
			logger.error("Error: Unable to create a producer using the session and destination.");
			throw jmsex;
		}

		return producer;
	} // end MyCreateProducer

	/**
	 * Create a consumer and set relevant properties.
	 * 
	 * @param session
	 *            The session to use
	 * @param destination
	 *            The destination to use
	 */
	public static MessageConsumer MyCreateConsumer(Session session, Destination destination) throws JMSException {
		MessageConsumer consumer = null;

		try {
			consumer = (Options.Selector.IsNull()) ? session.createConsumer(destination)
					: session.createConsumer(destination, Options.Selector.Value());
		} catch (JMSException jmsex) {
			logger.error("Error: Unable to create a consumer using the session and destination.");
			throw jmsex;
		}

		return consumer;
	} // end MyCreateConsumer

	/**
	 * Process a JMSException and any associated inner exceptions.
	 * 
	 * @param jmsex
	 */
	public static void processJMSException(JMSException jmsex) {
		System.out.println(jmsex);
		Throwable innerException = jmsex.getLinkedException();
		if (innerException != null) {
			logger.error("Inner exception(s):");
		}
		while (innerException != null) {
			logger.error(innerException);
			innerException = innerException.getCause();
		}
		logger.info("MQConnector execution FAILED!");
		System.exit(-1);
		return;
	}
}
