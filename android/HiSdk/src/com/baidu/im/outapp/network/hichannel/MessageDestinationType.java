/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 2.0.11
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.baidu.im.outapp.network.hichannel;

public final class MessageDestinationType {
  public final static MessageDestinationType Destination_DEFAULT = new MessageDestinationType("Destination_DEFAULT", 0);
  public final static MessageDestinationType Destination_ILLEGAL = new MessageDestinationType("Destination_ILLEGAL");

  public final int swigValue() {
    return swigValue;
  }

  public String toString() {
    return swigName;
  }

  public static MessageDestinationType swigToEnum(int swigValue) {
    if (swigValue < swigValues.length && swigValue >= 0 && swigValues[swigValue].swigValue == swigValue)
      return swigValues[swigValue];
    for (int i = 0; i < swigValues.length; i++)
      if (swigValues[i].swigValue == swigValue)
        return swigValues[i];
    throw new IllegalArgumentException("No enum " + MessageDestinationType.class + " with value " + swigValue);
  }

  private MessageDestinationType(String swigName) {
    this.swigName = swigName;
    this.swigValue = swigNext++;
  }

  private MessageDestinationType(String swigName, int swigValue) {
    this.swigName = swigName;
    this.swigValue = swigValue;
    swigNext = swigValue+1;
  }

  private MessageDestinationType(String swigName, MessageDestinationType swigEnum) {
    this.swigName = swigName;
    this.swigValue = swigEnum.swigValue;
    swigNext = this.swigValue+1;
  }

  private static MessageDestinationType[] swigValues = { Destination_DEFAULT, Destination_ILLEGAL };
  private static int swigNext = 0;
  private final int swigValue;
  private final String swigName;
}

