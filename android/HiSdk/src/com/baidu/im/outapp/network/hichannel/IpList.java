/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 2.0.11
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.baidu.im.outapp.network.hichannel;

public class IpList {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected IpList(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(IpList obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        sessionJNI.delete_IpList(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setIp1(String value) {
    sessionJNI.IpList_ip1_set(swigCPtr, this, value);
  }

  public String getIp1() {
    return sessionJNI.IpList_ip1_get(swigCPtr, this);
  }

  public void setIp2(String value) {
    sessionJNI.IpList_ip2_set(swigCPtr, this, value);
  }

  public String getIp2() {
    return sessionJNI.IpList_ip2_get(swigCPtr, this);
  }

  public void setIp3(String value) {
    sessionJNI.IpList_ip3_set(swigCPtr, this, value);
  }

  public String getIp3() {
    return sessionJNI.IpList_ip3_get(swigCPtr, this);
  }

  public void setIp4(String value) {
    sessionJNI.IpList_ip4_set(swigCPtr, this, value);
  }

  public String getIp4() {
    return sessionJNI.IpList_ip4_get(swigCPtr, this);
  }

  public void setIp5(String value) {
    sessionJNI.IpList_ip5_set(swigCPtr, this, value);
  }

  public String getIp5() {
    return sessionJNI.IpList_ip5_get(swigCPtr, this);
  }

  public IpList() {
    this(sessionJNI.new_IpList(), true);
  }

}
