/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 2.0.11
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.baidu.im.outapp.network.hichannel;

public class LoginInfo_T {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected LoginInfo_T(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(LoginInfo_T obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        sessionJNI.delete_LoginInfo_T(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setAuto_login(boolean value) {
    sessionJNI.LoginInfo_T_auto_login_set(swigCPtr, this, value);
  }

  public boolean getAuto_login() {
    return sessionJNI.LoginInfo_T_auto_login_get(swigCPtr, this);
  }

  public void setBackup_ip(String value) {
    sessionJNI.LoginInfo_T_backup_ip_set(swigCPtr, this, value);
  }

  public String getBackup_ip() {
    return sessionJNI.LoginInfo_T_backup_ip_get(swigCPtr, this);
  }

  public void setSign_hash(int value) {
    sessionJNI.LoginInfo_T_sign_hash_set(swigCPtr, this, value);
  }

  public int getSign_hash() {
    return sessionJNI.LoginInfo_T_sign_hash_get(swigCPtr, this);
  }

  public LoginInfo_T() {
    this(sessionJNI.new_LoginInfo_T(), true);
  }

}
