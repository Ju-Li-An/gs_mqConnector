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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.DefaultHttpClientIODispatch;
import org.apache.http.impl.nio.pool.BasicNIOConnPool;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.nio.protocol.BasicAsyncRequestProducer;
import org.apache.http.nio.protocol.BasicAsyncResponseConsumer;
import org.apache.http.nio.protocol.HttpAsyncRequestExecutor;
import org.apache.http.nio.protocol.HttpAsyncRequester;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOEventDispatch;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpProcessorBuilder;
import org.apache.http.protocol.RequestConnControl;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestExpectContinue;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessageProcessor implements MessageListener {

	private HttpProcessor httpproc;

	private BasicNIOConnPool pool;

	private final HttpHost httpHost;

	private HttpAsyncRequester requester;

	private String gsService;

	private Connection connection;
	
	/**
	 * Logger
	 */
	private static final Logger logger = LogManager.getLogger(MessageProcessor.class);

	public MessageProcessor(String hostname, int port, String gsService) throws Exception {

		this.gsService = gsService;

		httpproc = HttpProcessorBuilder.create().add(new RequestContent()).add(new RequestTargetHost())
				.add(new RequestConnControl()).add(new RequestUserAgent("Test/1.1"))
				.add(new RequestExpectContinue(true)).build();

		HttpAsyncRequestExecutor protocolHandler = new HttpAsyncRequestExecutor();

		final IOEventDispatch ioEventDispatch = new DefaultHttpClientIODispatch(protocolHandler,
				ConnectionConfig.DEFAULT);

		final ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor();

		pool = new BasicNIOConnPool(ioReactor, ConnectionConfig.DEFAULT);
		pool.setDefaultMaxPerRoute(50);
		pool.setMaxTotal(50);

		Thread t = new Thread(new Runnable() {

			public void run() {
				try {
					// Ready to go!
					ioReactor.execute(ioEventDispatch);
				} catch (InterruptedIOException ex) {
					logger.error("Interrupted");
				} catch (IOException e) {
					logger.error("I/O error: " + e.getMessage());
				}
				System.out.println("Shutdown");
			}
		});

		t.start();

		httpHost = new HttpHost(hostname, port, "http");

		requester = new HttpAsyncRequester(httpproc);
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	private void sendToGenesis(TextMessage message) {
		HttpEntity body = null;
		String msgId=null;
		try {
			msgId=message.getJMSMessageID();
			logger.debug("NOUVEAU MESSAGE : "+msgId );
			logger.debug(message);
			logger.debug(pool.getTotalStats().toString());

			body = new StringEntity(message.getText());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		BasicHttpEntityEnclosingRequest request = new BasicHttpEntityEnclosingRequest("POST", gsService);

		request.setEntity(body);

		HttpCoreContext coreContext = HttpCoreContext.create();
	
		final long start = new Date().getTime();
		final String messageId = msgId;
		requester.execute(new BasicAsyncRequestProducer(httpHost, request), 
				new BasicAsyncResponseConsumer(), 
				pool,
				coreContext, 
				new FutureCallback<HttpResponse>() {

					public void completed(final HttpResponse response) {
						logger.info(messageId + " - STAT Genesis CALL: "+(new Date().getTime()-start)+" ms");
						logger.debug(pool.getTotalStats().toString());
						logger.debug(httpHost + "->" + response.getStatusLine());
						try {
							postResponse(response.getEntity().getContent(),new Date().getTime(),messageId);
						} catch (UnsupportedOperationException | IOException e) {
							e.printStackTrace();
						} 
					}

					public void failed(final Exception ex) {

						logger.error(httpHost + "->" + ex);
					}

					public void cancelled() {

						logger.error(httpHost + " cancelled");
					}

				});
	}

	public void postResponse(InputStream response,long start,String messageId) throws IOException {
		StringBuilder inputStringBuilder = new StringBuilder();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response));

		String content = bufferedReader.readLine();
		while (content != null) {
			inputStringBuilder.append(content);
			content = bufferedReader.readLine();
		}

		logger.debug(httpHost + "->" + inputStringBuilder.toString());

		Session session = null;
		Destination destination;
		MessageProducer producer = null;
		try {
			session = JmsFactory.MyCreateSession(connection);

			destination = JmsFactory.MyCreateDestination(session);

			producer = JmsFactory.MyCreateProducer(session, destination);

			Message sendMsg = session.createTextMessage(inputStringBuilder.toString());

			producer.send(sendMsg);
			logger.info(messageId + " - STAT PUT MQ: "+(new Date().getTime()-start)+" ms");

		} catch (JMSException e) {
			JmsFactory.processJMSException(e);
		} finally {
			if (producer != null) {
				try {
					producer.close();
				} catch (JMSException jmsex) {
					logger.error("Producer could not be closed.");
					JmsFactory.processJMSException(jmsex);
				}
			}

			if (session != null) {
				try {
					session.close();
				} catch (JMSException jmsex) {
					logger.error("Session could not be closed.");
					JmsFactory.processJMSException(jmsex);
				}
			}
		}

	}

	@Override
	public void onMessage(Message msg) {

		TextMessage message = (TextMessage) msg;

		this.sendToGenesis(message);
	}

}
