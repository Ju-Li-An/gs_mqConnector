//SCCSID "@(#) samples/jms/interactive/helper/Options.java, jmscc.samples, k710, k710-007-151026  1.5.2.1 11/10/17 15:52:57"
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

import java.util.Hashtable;

import com.ibm.msg.client.wmq.WMQConstants;

/**
 * Provides functionality for user option(s).
 */
public class Options extends BaseOptions {

	// Declare all known user options

	// Common

	/**
	 * GeneSiS URL
	 */
	public static Options ListenWMQ;
	
	/**
	 * GeneSiS URL
	 */
	public static Options GSUrl;

	/**
	 * Simple or Advanced mode
	 */
	public static Options SamplesMode;

	/**
	 * Connection Type
	 */
	public static Options ConnectionType;

	/**
	 * User ID
	 */
	public static Options UserID;

	/**
	 * Password
	 */
	public static Options Password;

	/**
	 * Number of messages to send/receive
	 */
	public static Options NumberOfMessages;

	/**
	 * Receive mode
	 */
	public static Options ReceiveMode;

	/**
	 * Selector expression
	 */
	public static Options Selector;

	/**
	 * Message text
	 */
	public static Options MessageText;

	/**
	 * Message type
	 */
	public static Options MessageType;

	/**
	 * Interval between messages
	 */
	public static Options Interval;

	/**
	 * Initial context URI
	 */
	public static Options InitialContextURI;

	/**
	 * File name to write initial context to
	 */
	public static Options ICFileName;

	/**
	 * Connection factory object name in initial context
	 */
	public static Options ICConnFactName;

	/**
	 * Destination object name in initial context
	 */
	public static Options ICDestName;

	// RTT specific

	/**
	 * Host name for RTT
	 */
	public static Options HostnameRTT;

	/**
	 * Port for RTT
	 */
	public static Options PortRTT;

	/**
	 * Protocol for RTT
	 */
	public static Options ProtocolRTT;

	/**
	 * Destination for RTT
	 */
	public static Options DestinationRTT;

	/**
	 * Multicast mode for RTT
	 */
	public static Options MulticastModeRTT;

	// WMQ specific

	/**
	 * Host name for WMQ
	 */
	public static Options HostnameWMQ;

	/**
	 * Port for WMQ
	 */
	public static Options PortWMQ;

	/**
	 * Channel for WMQ
	 */
	public static Options Channel;

	/**
	 * Connection mode for WMQ
	 */
	public static Options ConnectionMode;

	/**
	 * Queue manager for WMQ
	 */
	public static Options QueueManager;

	/**
	 * Provider version for WMQ
	 */
	public static Options ProviderVersion;

	/**
	 * Broker version for WMQ
	 */
	public static Options BrokerVersion;

	/**
	 * Broker publish queue for WMQ
	 */
	public static Options BrokerPublishQueue;

	/**
	 * Destination for WMQ
	 */
	public static Options DestinationWMQ;

	/**
	 * Delivery mode for WMQ
	 */
	public static Options DeliveryModeWMQ;
	
	/**
	 * Method (correlationID pattern, messageId pattern)
	 */
	public static Options GSPattern;

	/**
	 * Static constructor.
	 */
	static {
		// Create new hashtables
		inputMessage = new Hashtable();
		helpMessage = new Hashtable();
		defaultValue = new Hashtable();
		domainRestriction = new Hashtable();
		isValid = new Hashtable();
		nameToValue = new Hashtable();

		// Input messages

		inputMessage.put(Keys.SamplesMode, "Desired mode to run this sample application");
		inputMessage.put(Keys.ConnectionType, "Enter connection type");
		inputMessage.put(Keys.HostnameRTT, "Enter hostname");
		inputMessage.put(Keys.PortRTT, "Enter port");
		inputMessage.put(Keys.ProtocolRTT, "Enter connection protocol");
		inputMessage.put(Keys.HostnameWMQ, "Enter hostname");
		inputMessage.put(Keys.PortWMQ, "Enter port");
		inputMessage.put(Keys.Channel, "Enter channel");
		inputMessage.put(Keys.ConnectionMode, "Enter connection mode");
		inputMessage.put(Keys.QueueManager, "Enter queue manager");
		inputMessage.put(Keys.ProviderVersion, "Enter provider version");
		inputMessage.put(Keys.BrokerVersion, "Enter broker version");
		inputMessage.put(Keys.BrokerPublishQueue, "Enter publish queue");
		inputMessage.put(Keys.DestinationRTT, "Enter destination topic");
		inputMessage.put(Keys.DestinationWMQ, "Enter destination URI");
		inputMessage.put(Keys.ListenWMQ, "Enter listen URI");
		inputMessage.put(Keys.UserID, "Enter user");
		inputMessage.put(Keys.Password, "Enter password");
		inputMessage.put(Keys.NumberOfMessages, "Enter number of messages");
		inputMessage.put(Keys.ReceiveMode, "Enter receive mode");
		inputMessage.put(Keys.DeliveryModeWMQ, "Enter delivery mode");
		inputMessage.put(Keys.MulticastModeRTT, "Enter multicast mode");
		inputMessage.put(Keys.Selector, "Enter SQL selector statement");
		inputMessage.put(Keys.MessageType, "Enter message type");
		inputMessage.put(Keys.MessageText, "Your message");
		inputMessage.put(Keys.Interval, "Enter interval between messages in seconds");
		inputMessage.put(Keys.InitialContextURI, "Enter administered object directory");
		inputMessage.put(Keys.ICConnFactName, "Enter connection factory name for initial context");
		inputMessage.put(Keys.ICDestName, "Enter destination name for initial context");
		inputMessage.put(Keys.GSUrl, "Enter destination URL to GeneSiS");
		inputMessage.put(Keys.GSPattern, "Mode [C]orrelationId or [M]essageId");

		// end Input messages

		// Help messages

		helpMessage.put(Keys.GSUrl,
				new String[] { "The Genesis URL service" });
		
		// Ensure that each line is no longer than 80 characters for clearer
		// user interface
		helpMessage.put(Keys.SamplesMode,
				new String[] { "The desired mode to run this sample application. Choose 'simple' if you",
						"prefer to specify minimal configuration settings and would like default",
						"values for most options. Choose 'advanced' if you prefer finer control",
						"and would like to specify value(s) of your own choice." });

		helpMessage.put(Keys.ConnectionType, new String[] { "The connection type for messaging service:",
				"WebSphere MQ (WMQ)", "or WebSphere Business Integration Brokers using RTT transport (RTT)." });

		helpMessage.put(Keys.HostnameRTT, new String[] {
				"The name of the computer on the network providing messaging service", "or its IPv4 address." });

		helpMessage.put(Keys.PortRTT, new String[] { "The port number for messaging service using desired transport.",
				"Valid range is 0 to 2^16-1 (both inclusive)." });

		helpMessage.put(Keys.ProtocolRTT, new String[] { "The communication protocol to be used." });

		helpMessage.put(Keys.HostnameWMQ, new String[] {
				"The name of the computer on the network providing messaging service", "or its IPv4 address." });

		helpMessage.put(Keys.PortWMQ, new String[] { "The port number for messaging service using desired transport.",
				"Valid range is 0 to 2^16-1 (both inclusive)." });

		helpMessage.put(Keys.Channel,
				new String[] { "The channel to use. A WebSphere MQ channel is a logical communication",
						"link between a WebSphere MQ client and a WebSphere MQ queue manager",
						"(MQI channels), or between two queue managers (message channels)." });

		helpMessage.put(Keys.ConnectionMode,
				new String[] { "The connection mode to be used. Please refer to the readme or",
						"documentation for the statement of enviroment, for e.g. supported",
						"versions and pre-requisites required." });

		helpMessage.put(Keys.QueueManager, new String[] { "The WMQ queue manager to connect to." });

		helpMessage.put(Keys.ProviderVersion,
				new String[] { "The provider version describes the Version, release, modification level",
						"and fix pack of the queue manager to which this client is intended to connect.",
						"See documentation for further details.", });

		helpMessage.put(Keys.BrokerVersion,
				new String[] { "The broker version describes which type of broker is being used for publish/",
						"subscribe messaging. The value broker implies the use of the WMQ publish/",
						"subscribe broker. The value integrator implies the use of the WMQ Integrator",
						"broker, i.e. WBI Message Broker or WBI Event Broker." });

		helpMessage.put(Keys.BrokerPublishQueue,
				new String[] { "The name of the input queue for publishing messages." });

		helpMessage.put(Keys.DestinationRTT, new String[] {
				"A topic for publish/subscribe messaging. Destination may or may not", "have the topic:// prefix." });

		helpMessage.put(Keys.DestinationWMQ,
				new String[] { "A queue for point-to-point messaging or topic for publish/subscribe",
						"messaging. Destination without either queue:// or topic:// prefix",
						"is assumed to be a queue." });
		
		helpMessage.put(Keys.ListenWMQ,
				new String[] { "A queue for point-to-point messaging or topic for publish/subscribe",
						"messaging. Destination without either queue:// or topic:// prefix",
						"is assumed to be a queue." });

		helpMessage.put(Keys.UserID, new String[] { "The user id, if security is enabled." });

		helpMessage.put(Keys.Password, new String[] { "The password for user id, if security is enabled." });

		helpMessage.put(Keys.NumberOfMessages,
				new String[] { "The number of messages to send or receive.",
						"Valid values are finite numbers or 'infinite'.",
						"Valid range for finite numbers is 0 to 2^32-1 (both inclusive)." });

		helpMessage.put(Keys.ReceiveMode,
				new String[] { "Synchronously or asynchronously receive message(s).",
						"Synchronous mode will poll the server for messages",
						"Asynchronous will receive messages as they become available" });

		helpMessage.put(Keys.DeliveryModeWMQ,
				new String[] { "The delivery mode for the messages.",
						"Persistent messages are guaranteed to be delivered exactly once.",
						"Non-persistent messages are guaranteed to be delivered at most once." });

		helpMessage.put(Keys.MulticastModeRTT,
				new String[] { "Selects the IP transmission modes you will allow the server to use when",
						"receiving messages (if available).",
						"Note: Multicast messaging requires additional configuration on the server." });

		helpMessage.put(Keys.Selector, new String[] { "Selector expression that must be matched for messages passed to",
				"this Consumer by any Destination to which the Consumer attaches." });

		helpMessage.put(Keys.MessageText,
				new String[] { "Your message. If no message text is provided, then the application alternates",
						"between two pre-defined messages." });

		helpMessage.put(Keys.MessageType, new String[] { "The type of message(s) to send or receive." });

		helpMessage.put(Keys.Interval, new String[] { "The interval in seconds between sending or receiving messages.",
				"Valid range is 0 to 2^32-1 (both inclusive)." });

		helpMessage.put(Keys.InitialContextURI,
				new String[] { "An initial context is used to access administered objects.",
						"Valid initial context prefixes are file://, http://, ",
						"LDAP:// and cosnaming://. If no prefix is provided, ", "then file:// is assumed." });

		helpMessage.put(Keys.ICConnFactName,
				new String[] { "The name for connection factory object in the initial context." });

		helpMessage.put(Keys.ICDestName, new String[] { "The name for destination object in the initial context." });
		
		helpMessage.put(Keys.ICDestName, new String[] { "The name for destination object in the initial context." });
		
		helpMessage.put(Keys.GSPattern, new String[] { "Mode [C]orrelationId or [M]essageId" });

		// end Help messages

		// Default values

		defaultValue.put(Keys.SamplesMode, Literals.Simple);
		defaultValue.put(Keys.ConnectionType, Literals.WMQ);
		defaultValue.put(Keys.HostnameRTT, Literals.LocalHost);
		defaultValue.put(Keys.PortRTT, new Integer(1506));
		defaultValue.put(Keys.ProtocolRTT, Literals.Tcp);
		defaultValue.put(Keys.HostnameWMQ, Literals.LocalHost);
		defaultValue.put(Keys.PortWMQ, new Integer(1414));
		defaultValue.put(Keys.Channel, Literals.ServerConnectionChannel);
		defaultValue.put(Keys.ConnectionMode, Literals.Client);
		defaultValue.put(Keys.ProviderVersion, Literals.ProviderVersionUnspecified);
		defaultValue.put(Keys.BrokerVersion, Literals.Broker);
		defaultValue.put(Keys.DestinationRTT, Literals.Dest);
		defaultValue.put(Keys.DestinationWMQ, Literals.DestQueue);
		defaultValue.put(Keys.ListenWMQ, Literals.DestQueue);
		defaultValue.put(Keys.NumberOfMessages, Literals.Infinite);
		defaultValue.put(Keys.ReceiveMode, Literals.Async);
		defaultValue.put(Keys.DeliveryModeWMQ, Literals.NonPersistent);
		defaultValue.put(Keys.MulticastModeRTT, Literals.Disabled);
		defaultValue.put(Keys.MessageType, Literals.Text);
		defaultValue.put(Keys.Interval, new Integer(1));
		defaultValue.put(Keys.ICConnFactName, Literals.SampleCF);
		defaultValue.put(Keys.ICDestName, Literals.SampleDest);
		defaultValue.put(Keys.GSUrl, Literals.GSUrlDefault);
		defaultValue.put(Keys.GSPattern, Literals.GSPatternDefault);

		// end Default values

		// Domain restriction

		domainRestriction.put(Keys.SamplesMode, new String[] { Literals.Simple, Literals.Advanced });

		domainRestriction.put(Keys.ConnectionType, new String[] { Literals.RTT, Literals.WMQ });

		domainRestriction.put(Keys.ProtocolRTT, new String[] { Literals.Tcp, Literals.Http });

		domainRestriction.put(Keys.ConnectionMode, new String[] { Literals.Client, Literals.Bindings });

		domainRestriction.put(Keys.ProviderVersion, new String[] { Literals.ProviderVersionUnspecified,
				Literals.ProviderVersion6, Literals.ProviderVersion7 });

		domainRestriction.put(Keys.BrokerVersion, new String[] { Literals.Broker, Literals.Integrator });

		domainRestriction.put(Keys.ReceiveMode, new String[] { Literals.Sync, Literals.Async });

		domainRestriction.put(Keys.DeliveryModeWMQ,
				new String[] { Literals.AsApp, Literals.AsDest, Literals.NonPersistent, Literals.Persistent });

		domainRestriction.put(Keys.MulticastModeRTT,
				new String[] { Literals.Disabled, Literals.Enabled, Literals.NonReliable, Literals.Reliable });

		domainRestriction.put(Keys.MessageType, new String[] { Literals.Base, Literals.Bytes, Literals.Map,
				Literals.Object, Literals.Stream, Literals.Text });
		// end Domain restriction

		// Validity checks

		// A non-exhaustive list of checks. More checks can be added if needed.
		isValid.put(Keys.HostnameRTT, new IsOneWord());
		isValid.put(Keys.PortRTT, new IsValidPortNumber());
		isValid.put(Keys.HostnameWMQ, new IsOneWord());
		isValid.put(Keys.PortWMQ, new IsValidPortNumber());
		isValid.put(Keys.Channel, new IsOneWord());
		isValid.put(Keys.QueueManager, new IsOneWord());
		isValid.put(Keys.BrokerPublishQueue, new IsOneWord());
		isValid.put(Keys.DestinationRTT, new IsValidRTTDestination());
		isValid.put(Keys.DestinationWMQ, new IsValidDestination());
		isValid.put(Keys.ListenWMQ, new IsValidDestination());
		isValid.put(Keys.UserID, new IsOneWord());
		isValid.put(Keys.Password, new IsOneWord());
		isValid.put(Keys.NumberOfMessages, new IsValidNumberOfMessages());
		isValid.put(Keys.Interval, new IsGreaterThanOrEqualZero());
		isValid.put(Keys.InitialContextURI, new IsValidInitialContextURI());
		isValid.put(Keys.ICConnFactName, new IsValidICConnFactName());
		isValid.put(Keys.ICDestName, new IsValidICDestName());

		// end Validity checks

		// Name to value conversion tables

		nameToValue.put(Keys.ProtocolRTT, new Hashtable());
		nameToValue.put(Keys.ConnectionMode, new Hashtable());
		nameToValue.put(Keys.BrokerVersion, new Hashtable());
		nameToValue.put(Keys.DeliveryModeWMQ, new Hashtable());
		nameToValue.put(Keys.MulticastModeRTT, new Hashtable());

		((Hashtable) nameToValue.get(Keys.ProtocolRTT)).put(Literals.Tcp, new Integer(WMQConstants.RTT_CP_TCP));
		((Hashtable) nameToValue.get(Keys.ProtocolRTT)).put(Literals.Http, new Integer(WMQConstants.RTT_CP_HTTP));

		((Hashtable) nameToValue.get(Keys.ConnectionMode)).put(Literals.Client,
				new Integer(WMQConstants.WMQ_CM_CLIENT));
		((Hashtable) nameToValue.get(Keys.ConnectionMode)).put(Literals.Bindings,
				new Integer(WMQConstants.WMQ_CM_BINDINGS));

		((Hashtable) nameToValue.get(Keys.BrokerVersion)).put(Literals.Broker, new Integer(WMQConstants.WMQ_BROKER_V1));
		((Hashtable) nameToValue.get(Keys.BrokerVersion)).put(Literals.Integrator,
				new Integer(WMQConstants.WMQ_BROKER_V2));

		((Hashtable) nameToValue.get(Keys.DeliveryModeWMQ)).put(Literals.AsApp,
				new Integer(WMQConstants.DELIVERY_AS_APP));
		((Hashtable) nameToValue.get(Keys.DeliveryModeWMQ)).put(Literals.AsDest,
				new Integer(WMQConstants.DELIVERY_AS_DEST));
		((Hashtable) nameToValue.get(Keys.DeliveryModeWMQ)).put(Literals.NonPersistent,
				new Integer(WMQConstants.DELIVERY_NOT_PERSISTENT));
		((Hashtable) nameToValue.get(Keys.DeliveryModeWMQ)).put(Literals.Persistent,
				new Integer(WMQConstants.DELIVERY_PERSISTENT));

		((Hashtable) nameToValue.get(Keys.MulticastModeRTT)).put(Literals.Disabled,
				new Integer(WMQConstants.RTT_MULTICAST_DISABLED));
		((Hashtable) nameToValue.get(Keys.MulticastModeRTT)).put(Literals.Enabled,
				new Integer(WMQConstants.RTT_MULTICAST_ENABLED));
		((Hashtable) nameToValue.get(Keys.MulticastModeRTT)).put(Literals.NonReliable,
				new Integer(WMQConstants.RTT_MULTICAST_NOT_RELIABLE));
		((Hashtable) nameToValue.get(Keys.MulticastModeRTT)).put(Literals.Reliable,
				new Integer(WMQConstants.RTT_MULTICAST_RELIABLE));
		// todo: multicast as CF?

		// end Name to value conversion tables

		// And create user options...
		SamplesMode = new Options(Keys.SamplesMode);
		ConnectionType = new Options(Keys.ConnectionType);
		HostnameRTT = new Options(Keys.HostnameRTT);
		PortRTT = new Options(Keys.PortRTT);
		ProtocolRTT = new Options(Keys.ProtocolRTT);
		HostnameWMQ = new Options(Keys.HostnameWMQ);
		PortWMQ = new Options(Keys.PortWMQ);
		Channel = new Options(Keys.Channel);
		ConnectionMode = new Options(Keys.ConnectionMode);
		QueueManager = new Options(Keys.QueueManager);
		ProviderVersion = new Options(Keys.ProviderVersion);
		BrokerVersion = new Options(Keys.BrokerVersion);
		BrokerPublishQueue = new Options(Keys.BrokerPublishQueue);
		DestinationRTT = new Options(Keys.DestinationRTT);
		DestinationWMQ = new Options(Keys.DestinationWMQ);
		ListenWMQ = new Options(Keys.ListenWMQ);
		UserID = new Options(Keys.UserID);
		Password = new Options(Keys.Password);
		NumberOfMessages = new Options(Keys.NumberOfMessages);
		ReceiveMode = new Options(Keys.ReceiveMode);
		DeliveryModeWMQ = new Options(Keys.DeliveryModeWMQ);
		MulticastModeRTT = new Options(Keys.MulticastModeRTT);
		Selector = new Options(Keys.Selector);
		MessageText = new Options(Keys.MessageText);
		MessageType = new Options(Keys.MessageType);
		Interval = new Options(Keys.Interval);
		InitialContextURI = new Options(Keys.InitialContextURI);
		ICConnFactName = new Options(Keys.ICConnFactName);
		ICDestName = new Options(Keys.ICDestName);
		GSUrl = new Options(Keys.GSUrl);
		GSPattern = new Options(Keys.GSPattern);

	} // end static constructor

	/**
	 * Private constructor, to block instantiation of options elsewhere.
	 * 
	 * @param key
	 */
	private Options(Keys key) {
		super(key);
		return;
	}

} // end Options
