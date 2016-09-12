// SCCSID "@(#) samples/jms/interactive/helper/JmsApp.java, jmscc.samples, k710, k710-007-151026  1.6.2.1 11/10/17 15:52:34"
/*
 * <N_OCO_COPYRIGHT>
 * Licensed Materials - Property of IBM
 * 
 * 5724-H72, 5655-R36, 5724-L26, 5655-L82     
 * 
 * (c) Copyright IBM Corp. 2008, 2009 All Rights Reserved.
 * 
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with
 * IBM Corp.
 * <NOC_COPYRIGHT>
 */
package com.julian.genesis.mqconnect.helper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * An abstract class that can be extended to provide consumer/producer
 * functionality.
 */
public abstract class JmsApp {

	/**
	 * Logger
	 */
	private static final Logger logger = LogManager.getLogger(JmsApp.class);
	
	/**
	 * Constructor.
	 * 
	 * @param args
	 *            User specified list of arguments
	 * @param ctx
	 *            Specifies the context for options
	 */
	protected JmsApp(String[] args, MyContext ctx) {

		// Request options from the user
		if (args.length > 0)
			new OptionsPresenter(ctx, args);
		else
			new OptionsPresenter(ctx);

		// Write out user responses to a file in current directory
		Writer writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(
					Options.ConnectionType.Value().toLowerCase() + "-" + ctx.toString().toLowerCase() + ".rsp"));

			writer.write(BaseOptions.UserResponses());
		} catch (IOException ioex) {
			logger.error("Error: Unable to write a response file.");
			logger.error(ioex);
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException ioex) {
				logger.error("Error: Unable to close the response file.");
				logger.error(ioex);
			}
		}
		return;
	}

	protected static String getGSHost() {
		String val = "";
		try {
			val = new URL(Options.GSUrl.Value()).getHost();
		} catch (MalformedURLException e) {
			logger.error(e);
		} 
		return val;
	}

	protected static int getGSPort() {
		int port=0;
		try {
			port = new URL(Options.GSUrl.Value()).getPort();
		} catch (MalformedURLException e) {
			logger.error(e);
		} 
		return port;
	}
	
	protected static String getGSUri() {
		String val = "";
		try {
			val = new URL(Options.GSUrl.Value()).getPath();
		} catch (MalformedURLException e) {
			logger.error(e);
		} 
		return val;
	}

	protected static String getGSPattern() {
		return Options.GSPattern.Value();
	}
	

} // end JMSApp
