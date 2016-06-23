//SCCSID "@(#) samples/jms/interactive/helper/MyContext.java, jmscc.samples, k710, k710-007-151026  1.6.2.1 11/10/17 15:52:52"
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
 * The context in which options are presented.
 */
public final class MyContext {

  private String id;

  /**
   * Private constructor.
   * 
   * @param String for name
   */
  private MyContext(String name) {
    id = name;
    return;
  }

  /**
   * Overridden toString()
   * 
   * @return ID
   */
  public String toString() {
    return id;
  }

  /**
   * For SampleProducer application
   */
  public final static MyContext Producer = new MyContext("Producer");

  /**
   * For SampleConsumer application
   */
  public final static MyContext Consumer = new MyContext("Consumer");
} // end Context

