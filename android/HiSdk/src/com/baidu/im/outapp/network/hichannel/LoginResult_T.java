/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 2.0.11
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.baidu.im.outapp.network.hichannel;

public class LoginResult_T {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected LoginResult_T(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(LoginResult_T obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        sessionJNI.delete_LoginResult_T(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setAck_code(long value) {
    sessionJNI.LoginResult_T_ack_code_set(swigCPtr, this, value);
  }

  public long getAck_code() {
    return sessionJNI.LoginResult_T_ack_code_get(swigCPtr, this);
  }

  public void setChannelkey(String value) {
    sessionJNI.LoginResult_T_channelkey_set(swigCPtr, this, value);
  }

  public String getChannelkey() {
    return sessionJNI.LoginResult_T_channelkey_get(swigCPtr, this);
  }

  public LoginResult_T() {
    this(sessionJNI.new_LoginResult_T(), true);
  }

}
