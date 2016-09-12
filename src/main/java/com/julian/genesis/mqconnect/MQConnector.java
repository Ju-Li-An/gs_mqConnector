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
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.julian.genesis.mqconnect.helper.JmsApp;
import com.julian.genesis.mqconnect.helper.MyContext;

public class MQConnector extends JmsApp {

	private static boolean isSigterm=false;
	
	/**
	 * Logger
	 */
	private static final Logger logger = LogManager.getLogger(MQConnector.class);
	
	public MQConnector(String[] args) {
		super(args, MyContext.Consumer);
		return;
	}
	
	public void setSigterm(boolean signal) {
		isSigterm = signal;
	}

	/**
	 * The main entry point for the application.
	 * 
	 * @param args
	 *            cmd line arguments
	 */
	public static void main(String[] args) {
		// Check if user needs help
		if ((args.length > 0) && (args[0].endsWith("?") || args[0].toLowerCase().endsWith("help"))) {
			DisplayHelp();
			return;
		}

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			
			@Override
			public void run() {
				logger.debug("STOP");
				isSigterm=true;
			}
		}));
		
		try {
			MQConnector mqConnector = new MQConnector(args);
			mqConnector.ReceiveMessages();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.fatal(ex);
			logger.fatal("MQConnector execution FAILED!");
			System.exit(-1);
		}

		// Read an input before termination
		/*System.out.print("Press return key to continue...");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			reader.read();
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}*/
		System.exit(0);
		return;
	} // end Main

	/**
	 * Display help to the user.
	 */
	private static void DisplayHelp() {
		System.out.println();
		System.out.println("Usage: Consumes messages from a topic/queue");
		System.out.println();
		System.out.println("  MQConnector [ < response_file ]");
		System.out.println();
		System.out.println("  MQConnector [ QMANAGER HOSTNAME PORT CHANNEL LISTEN_QUEUE RESP_QUEUE GENESIS_URL ]");
		return;
	}

	/**
	 * Receive the message(s).
	 */
	private void ReceiveMessages() throws Exception {
		// Variables
		Connection connection = null;
		Session session = null;
		MessageConsumer consumer = null;

		ConnectionFactory connectionFactory = JmsFactory.MyCreateConnectionFactory();

		logger.info("Connection Factory created.");

		connection = JmsFactory.MyCreateConnection(connectionFactory);

		logger.info("Connection created.");

		session = JmsFactory.MyCreateSession(connection);

		logger.info("Session created.");

		Destination destination = JmsFactory.MyCreateListenDestination(session);

		logger.info("Listening Queue created: " + destination.toString());

		consumer = JmsFactory.MyCreateConsumer(session, destination);

		logger.info("Consumer created.");

		logger.info("Waiting for messages...");
		
		MessageProcessor processor = new MessageProcessor(getGSHost(),getGSPort(),getGSUri(),getGSPattern());
		processor.setConnection(connection);
		
		consumer.setMessageListener(processor);
		
		connection.start();
		
		boolean boucle = true;
		
		while( boucle ) {
			Thread.sleep(1000);
			if( isSigterm ) {
				boucle = false;
			} 
		}
	}

}
