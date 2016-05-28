/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 2.0.11
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.baidu.im.outapp.network.hichannel;

public class HiCoreSession {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected HiCoreSession(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(HiCoreSession obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        sessionJNI.delete_HiCoreSession(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public HiCoreSession() {
    this(sessionJNI.new_HiCoreSession(), true);
  }

  public boolean initSession() {
    return sessionJNI.HiCoreSession_initSession(swigCPtr, this);
  }

  public boolean deinitSession() {
    return sessionJNI.HiCoreSession_deinitSession(swigCPtr, this);
  }

  public void set_notify_callback(IEvtCallback cb) {
    sessionJNI.HiCoreSession_set_notify_callback(swigCPtr, this, IEvtCallback.getCPtr(cb), cb);
  }

  public int connect() {
    return sessionJNI.HiCoreSession_connect(swigCPtr, this);
  }

  public int disconnect(boolean atonce) {
    return sessionJNI.HiCoreSession_disconnect(swigCPtr, this, atonce);
  }

  public boolean postMessage(byte[] data, int len, int seq) {
    return sessionJNI.HiCoreSession_postMessage(swigCPtr, this, data, len, seq);
  }

  public void networkChanged(NetworkChange_T arg0) {
    sessionJNI.HiCoreSession_networkChanged(swigCPtr, this, arg0.swigValue());
  }

  public void sendKeepAlive() {
    sessionJNI.HiCoreSession_sendKeepAlive(swigCPtr, this);
  }

  public void dumpSelf() {
    sessionJNI.HiCoreSession_dumpSelf(swigCPtr, this);
  }

}