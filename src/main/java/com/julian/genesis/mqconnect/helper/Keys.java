// SCCSID "@(#) samples/jms/interactive/helper/Keys.java, jmscc.samples, k710, k710-007-151026  1.6.2.1 11/10/17 15:52:40"
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

/**
 * A set of keys that define options for the sample applications.
 */
final class Keys {

  // Private constructor.
  private Keys() {
    // Empty
    return;
  }

  /**
   * Pattern
   */
  public static final Keys GSPattern = new Keys();
  
  /**
   * Genesis URL
   */
  public static final Keys GSUrl = new Keys();
  
  /**
   * Samples Mode
   */
  public static final Keys SamplesMode = new Keys();

  /**
   * Connection Type
   */
  public static final Keys ConnectionType = new Keys();

  /**
   * Hostname for RTT
   */
  public static final Keys HostnameRTT = new Keys();

  /**
   * Port for RTT
   */
  public static final Keys PortRTT = new Keys();

  /**
   * Protocol for RTT
   */
  public static final Keys ProtocolRTT = new Keys();

  /**
   * Hostname for WMQ
   */
  public static final Keys HostnameWMQ = new Keys();

  /**
   * Port for WMQ
   */
  public static final Keys PortWMQ = new Keys();

  /**
   * Channel
   */
  public static final Keys Channel = new Keys();

  /**
   * Connection Mode
   */
  public static final Keys ConnectionMode = new Keys();

  /**
   * Queue Manager
   */
  public static final Keys QueueManager = new Keys();

  /**
   * Provider Version
   */
  public static final Keys ProviderVersion = new Keys();

  /**
   * Broker Version
   */
  public static final Keys BrokerVersion = new Keys();

  /**
   * Broker Publish Queue
   */
  public static final Keys BrokerPublishQueue = new Keys();

  /**
   * Destination for RTT
   */
  public static final Keys DestinationRTT = new Keys();

  /**
   * Destination for WMQ
   */
  public static final Keys DestinationWMQ = new Keys();

  /**
   * Listening for WMQ
   */
  public static final Keys ListenWMQ = new Keys();
  
  /**
   * UserID
   */
  public static final Keys UserID = new Keys();

  /**
   * Password
   */
  public static final Keys Password = new Keys();

  /**
   * Number of Messages
   */
  public static final Keys NumberOfMessages = new Keys();

  /**
   * Receive Mode
   */
  public static final Keys ReceiveMode = new Keys();

  /**
   * Delivery Mode for WMQ
   */
  public static final Keys DeliveryModeWMQ = new Keys();

  /**
   * Multicast Mode for RTT
   */
  public static final Keys MulticastModeRTT = new Keys();

  /**
   * Selector
   */
  public static final Keys Selector = new Keys();

  /**
   * Message Text
   */
  public static final Keys MessageText = new Keys();

  /**
   * Message Type
   */
  public static final Keys MessageType = new Keys();

  /**
   * Interval
   */
  public static final Keys Interval = new Keys();

  /**
   * Initial Context URI
   */
  public static final Keys InitialContextURI = new Keys();

  /**
   * Initial Context Factory Name
   */
  public static final Keys ICConnFactName = new Keys();

  /**
   * Initial Context Destination Name
   */
  public static final Keys ICDestName = new Keys();
}
