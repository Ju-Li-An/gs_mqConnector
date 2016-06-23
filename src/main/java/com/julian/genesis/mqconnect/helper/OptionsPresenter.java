// SCCSID "@(#) samples/jms/interactive/helper/OptionsPresenter.java, jmscc.samples, k710, k710-007-151026  1.6.2.1 11/10/17 15:53:05"
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ibm.msg.client.wmq.WMQConstants;

/**
 * Present options to the user.
 */
public class OptionsPresenter {

	/**
	 * Logger
	 */
	private static final Logger logger = LogManager.getLogger(OptionsPresenter.class);
	
  /**
   * Context in which current options are presented.
   */
  private MyContext context;

  /**
   * Constructor.
   * 
   * @param ctx Initial Context
   * @param args Argument default
   */
  public OptionsPresenter(MyContext ctx,String[] args) {
      context=ctx;

      Options.SamplesMode.set_Value(Literals.Advanced);
      Options.ConnectionType.set_Value(Literals.WMQ);
      Options.ConnectionMode.set_Value(Literals.Client);
      Options.QueueManager.set_Value(args[0]);
      logger.info("QMANAGER: "+args[0]);
      Options.HostnameWMQ.set_Value(args[1]);
      logger.info("HOSTNAME: "+args[1]);
      Options.PortWMQ.set_Value(args[2]);
      logger.info("PORT: "+args[2]);
      Options.Channel.set_Value(args[3]);
      logger.info("CHANNEL: "+args[3]);
      Options.ListenWMQ.set_Value("queue:///"+args[4]);
      logger.info("LISTEN QUEUE: "+args[4]);
      Options.DestinationWMQ.set_Value("queue:///"+args[5]);
      logger.info("DESTINATION QUEUE: "+args[5]);
      Options.GSUrl.set_Value(args[6]);
      logger.info("GENESIS URL: "+args[6]);
      Options.ProviderVersion.set_Value(Literals.ProviderVersionUnspecified);
      Options.UserID.set_Value("mqm");
      Options.Password.set_Value("mqm");
      Options.ReceiveMode.set_Value(Literals.Async);
      Options.Interval.set_Value("0");
      
      return;
  }

  /**
   * Constructor.
   * 
   * @param ctx Initial Context
   */
  public OptionsPresenter(MyContext ctx) {
    context = ctx;

    System.out.println();
    System.out.println("Enter '?' for help with any question.");
    System.out.println();

    // Simple or advanced mode?
    Options.SamplesMode.Present();

    // Connection type
    Options.ConnectionType.Present();

    // All other options
    if (Options.SamplesMode.Value().equals(Literals.Simple)) {
      if (Options.ConnectionType.Value().equals(Literals.RTT)) {
        // Present minimal RTT options
        PresentRTTOptions();
      }
      else if (Options.ConnectionType.Value().equals(Literals.WMQ)) {
        // Present minimal WMQ options
        PresentWMQOptions();
      }
    }
    else if (Options.SamplesMode.Value().equals(Literals.Advanced)) {
      if (Options.ConnectionType.Value().equals(Literals.RTT)) {
        // Present full RTT options
        PresentFullRTTOptions();
      }
      else if (Options.ConnectionType.Value().equals(Literals.WMQ)) {
        // Present full WMQ options
        PresentFullWMQOptions();
      }
    } // end all other options
    return;
  } // end constructor

  /**
   * Present minimal RTT options.
   */
  private void PresentRTTOptions() {
    Options.HostnameRTT.Present();

    Options.PortRTT.Present();

    Options.DestinationRTT.Present();

    if (InContext(MyContext.Producer)) {
      Options.MessageText.Present();
    }
    return;
  } // end PresentRTTOptions

  /**
   * Present full RTT options.
   */
  private void PresentFullRTTOptions() {
    Options.HostnameRTT.Present();

    Options.PortRTT.Present();

    Options.DestinationRTT.Present();

    Options.ProtocolRTT.Present();

    Options.UserID.Present();

    if (!Options.UserID.IsNull()) {
      Options.Password.Present();
    }

    if (InContext(MyContext.Producer)) {
      Options.MessageType.Present();

      // User defined message text is not applicable if message type is base
      if (!Options.MessageType.Value().equals(Literals.Base)) {
        Options.MessageText.Present();
      }
    }

    if (InContext(MyContext.Consumer)) {
      Options.ReceiveMode.Present();

      Options.Selector.Present();
    }

    if (InContext(MyContext.Consumer)) {
      Options.MulticastModeRTT.Present();
    }

    Options.NumberOfMessages.Present();

    Options.Interval.Present();
    return;
  } // end PresentFullRTTOptions

  /**
   * Present minimal WMQ options.
   */
  private void PresentWMQOptions() {
    Options.ConnectionMode.Present();

    Options.QueueManager.Present();

    if ((Options.ConnectionMode.ValueAsNumber() == WMQConstants.WMQ_CM_CLIENT)) {
      Options.HostnameWMQ.Present();
    }

    Options.ListenWMQ.Present();
    
    Options.DestinationWMQ.Present();

    Options.ProviderVersion.Present();

    if (!Options.DestinationWMQ.IsNull() && Options.DestinationWMQ.Value().startsWith("topic://")) {

      // Broker version is only applicable for ProviderVersion unspecified or v6
      if (Options.ProviderVersion.Value().equals(Literals.ProviderVersionUnspecified)
          || Options.ProviderVersion.Value().equals(Literals.ProviderVersion6)) {
        Options.BrokerVersion.Present();

        if ((Options.BrokerVersion.ValueAsNumber() == WMQConstants.WMQ_BROKER_V2)
            && (InContext(MyContext.Producer))) {
          // Must get broker publish queue name from the user
          Options.BrokerPublishQueue.Present(true);
        }
      }
    }

    Options.GSUrl.Present();
    
    if (InContext(MyContext.Producer)) {
      Options.MessageText.Present();
    }
    return;
  } // end PresentWMQOptions

  /**
   * Present full WMQ options.
   */
  private void PresentFullWMQOptions() {
    Options.ConnectionMode.Present();

    Options.QueueManager.Present();

    if ((Options.ConnectionMode.ValueAsNumber() == WMQConstants.WMQ_CM_CLIENT)) {
      Options.HostnameWMQ.Present();

      Options.PortWMQ.Present();

      Options.Channel.Present();
    }

    Options.ListenWMQ.Present();
    
    Options.DestinationWMQ.Present();

    Options.ProviderVersion.Present();

    if (!Options.DestinationWMQ.IsNull() && Options.DestinationWMQ.Value().startsWith("topic://")) {

      // Broker version is only applicable for ProviderVersion unspecified or v6
      if (Options.ProviderVersion.Value().equals(Literals.ProviderVersionUnspecified)
          || Options.ProviderVersion.Value().equals(Literals.ProviderVersion6)) {
        Options.BrokerVersion.Present();

        if ((Options.BrokerVersion.ValueAsNumber() == WMQConstants.WMQ_BROKER_V2)
            && (InContext(MyContext.Producer))) {
          // Must get broker publish queue name from the user
          Options.BrokerPublishQueue.Present(true);
        }
      }
    }

    Options.GSUrl.Present();
    
    Options.UserID.Present();

    if (!Options.UserID.IsNull()) {
      Options.Password.Present();
    }

    if (InContext(MyContext.Producer)) {
      Options.DeliveryModeWMQ.Present();
    }

    if (InContext(MyContext.Producer)) {
      Options.MessageType.Present();

      // User defined message text is not applicable if message type is base
      if (!Options.MessageType.Value().equals(Literals.Base)) {
        Options.MessageText.Present();
      }
    }

    if (InContext(MyContext.Consumer)) {
      Options.ReceiveMode.Present();

      Options.Selector.Present();
    }

    // Don't ask for the number of messages for WMQ async consumers as we cannot fully control the
    // number of messages received in this mode
    if (InContext(MyContext.Producer) || !(Options.ReceiveMode.Value().equals(Literals.Async))) {
      Options.NumberOfMessages.Present();
    }

    Options.Interval.Present();
    return;
  } // end PresentFullWMQOptions

  /**
   * Whether the argument context is same as current context.
   */
  private boolean InContext(MyContext x) {
    return (context == x);
  }

} // end OptionsPresenter

