package com.baidu.im.frame.utils;

import com.baidu.im.constant.Constant;
import com.baidu.im.frame.inapp.InAppApplication;
import com.baidu.im.outapp.OutAppApplication;

public class LogUtil extends BaseLogUtil {

    public static final String TAG = "LogUtil";

    /**
     * 错误日志，写文件
     * 
     * @param thread
     * @param e
     */
    public static void printError(Throwable e) {
        printError(e, false);
    }

    private static void printError(Throwable e, boolean inAppLog) {
        if (Constant.DEBUG) {
            Thread currentThread = Thread.currentThread();
            String currentThreadInfo = null;
            if (null != currentThread) {
                currentThreadInfo =
                        "UncaughtException, thread: " + currentThread + " name: " + currentThread.getName() + " id: "
                                + currentThread.getId();
            }
            String text = formatMessage(LogLevel.ERROR.getTag(), LogLevel.ERROR.getName(), currentThreadInfo);
            validate(inAppLog);
            if (inAppLog) {
                inAppLogCenter.e(LogLevel.ERROR.getName(), text);
            } else {
                outAppLogCenter.e(LogLevel.ERROR.getName(), text);
            }
        }
    }

    public static void printError(String msg, Throwable e) {
        printError(msg, e, false);
    }

    private static void printError(String msg, Throwable e, boolean inAppLog) {
        if (Constant.DEBUG) {
            String text = formatMessage(LogLevel.ERROR.getTag(), LogLevel.ERROR.getName(), msg);
            validate(inAppLog);
            if (inAppLog) {
                inAppLogCenter.e(LogLevel.ERROR.getName(), text);
            } else {
                outAppLogCenter.e(LogLevel.ERROR.getName(), text);
            }
        }
    }

    public static void printProtocol(String log) {
        printProtocol(log, false);
    }

    private static void printProtocol(String log, boolean inAppLog) {
        if (Constant.DEBUG) {
            String text = formatMessage(LogLevel.PROTOCOL.getTag(), LogLevel.PROTOCOL.getName(), log);
            validate(inAppLog);
            if (inAppLog) {
                // inAppLogCenter.w(LogLevel.PROTOCOL.getName(), text);
            } else {
                outAppLogCenter.protocol(LogLevel.PROTOCOL.getName(), text);
            }
        }
    }

    public static void printMainProcess(String log) {
        printMainProcess(log, false);
    }

    private static void printMainProcess(String log, boolean inAppLog) {
        if (Constant.DEBUG) {
            String text =
                    "u=" + android.os.Process.myUid() + "p=" + android.os.Process.myPid() + " t="
                            + android.os.Process.myTid() + "_" + Thread.currentThread().getName() + "  " + log;
            text = formatMessage(LogLevel.MAINPROGRESS.getTag(), LogLevel.MAINPROGRESS.getName(), text);
            validate(inAppLog);
            if (inAppLog) {
                inAppLogCenter.i(LogLevel.MAINPROGRESS.getName(), text);
            } else {
                outAppLogCenter.i(LogLevel.MAINPROGRESS.getName(), text);
            }
        }
    }

    public static void printMainProcess(String tag, String log) {
        printMainProcess(tag, log, false);
    }

    private static void printMainProcess(String tag, String log, boolean inAppLog) {
        if (Constant.DEBUG) {
            String printTag = Thread.currentThread().getName() + ":" + tag;
            String text = formatMessage(LogLevel.MAINPROGRESS.getTag(), LogLevel.MAINPROGRESS.getName(), log);
            validate(inAppLog);
            if (inAppLog) {
                inAppLogCenter.i(LogLevel.MAINPROGRESS.getName() + ":" + printTag, text);
            } else {
                outAppLogCenter.i(LogLevel.MAINPROGRESS.getName() + ":" + printTag, text);
            }
        }
    }

    public static void printStat(String msg) {
        printStat(msg, false);
    }

    private static void printStat(String msg, boolean inAppLog) {
        if (Constant.DEBUG) {
            validate(inAppLog);
            if (inAppLog) {
                inAppLogCenter.stat("STAT", msg);
            } else {
                outAppLogCenter.stat("STAT", msg);
            }
        }
    }

    public static void printIm(String msg) {
        printIm(msg, false);
    }

    private static void printIm(String msg, boolean inAppLog) {
        if (Constant.DEBUG) {
            validate(inAppLog);
            if (inAppLog) {
                // inAppLogCenter.stat("STAT", msg);
            } else {
                outAppLogCenter.im("IM", msg);
            }
        }
    }

    public static void printIm(String tag, String msg) {
        printIm(tag, msg, false);
    }

    private static void printIm(String tag, String msg, boolean inAppLog) {
        if (Constant.DEBUG) {
            validate(inAppLog);
            if (inAppLog) {
                // inAppLogCenter.stat("IM" + tag, msg);
            } else {
                outAppLogCenter.im("IM:" + tag, msg);
            }
        }
    }

    // public static void printImE(String msg) {
    // printImE(msg, null, false);
    // }

    public static void printImE(String msg, Throwable tr) {
        printImE(msg, tr, false);
    }

    private static void printImE(String msg, Throwable tr, boolean inAppLog) {
        if (Constant.DEBUG) {
            validate(inAppLog);
            if (inAppLog) {
                // inAppLogCenter.stat("STAT", msg);
            } else {
                outAppLogCenter.im("IM", msg, tr);
            }
        }
    }

    public static void printImE(String tag, String msg, Throwable tr) {
        printImE(tag, msg, tr, false);
    }

    private static void printImE(String tag, String msg, Throwable tr, boolean inAppLog) {
        if (Constant.DEBUG) {
            validate(inAppLog);
            if (inAppLog) {
                // inAppLogCenter.stat("STAT", msg);
            } else {
                outAppLogCenter.im("IM:" + tag, msg, tr);
            }
        }
    }

    public static void printMsg(String msg) {
        printMsg(msg, false);
    }

    private static void printMsg(String msg, boolean inAppLog) {
        if (Constant.DEBUG) {
            validate(inAppLog);
            if (inAppLog) {
                // inAppLogCenter.stat("STAT", msg);
            } else {
                outAppLogCenter.msg("MSG", msg);
            }
        }
    }

    public static void printDebug(String tag, String msg) {
        printDebug(tag, msg, false);
    }

    private static void printDebug(String tag, String msg, boolean inAppLog) {
        if (Constant.DEBUG) {
            String text = formatMessage(LogLevel.DEBUG.getTag(), LogLevel.DEBUG.getName(), tag + ", " + msg);
            validate(inAppLog);
            if (inAppLog) {
                inAppLogCenter.d(LogLevel.DEBUG.getName(), text);
            } else {
                outAppLogCenter.d(LogLevel.DEBUG.getName(), text);
            }
        }
    }

    /**
     * UserInterface for the user of sdk
     * 
     * @param TAG
     * @param log
     */
    public static void e(String TAG, String log) {
        e(TAG, log, null);
    }

    /**
     * UserInterface for the user of sdk
     */
    public static void e(String TAG, Throwable e) {
        e(TAG, "", e);
    }

    public static void e(String TAG, String log, Throwable e) {
        e(TAG, log, e, false);
    }

    private static void e(String tag, String msg, Throwable t, boolean inAppLog) {
        if (Constant.DEBUG) {
            String text = formatMessage(LogLevel.ERROR.getTag(), tag, msg);
            validate(inAppLog);
            if (inAppLog) {
                inAppLogCenter.e(tag, text, t);
            } else {
                outAppLogCenter.e(tag, text, t);
            }
        }
    }

    public static void w(String tag, String msg) {
        w(tag, msg, false);
    }

    private static void w(String tag, String msg, boolean inAppLog) {
        if (Constant.DEBUG) {
            String text = formatMessage(LogLevel.WARN.getTag(), tag, msg);
            validate(inAppLog);
            if (inAppLog) {
                inAppLogCenter.w(tag, text);
            } else {
                outAppLogCenter.w(tag, text);
            }
        }
    }

    public static void i(String tag, String msg) {
        i(tag, msg, false);
    }

    private static void i(String tag, String msg, boolean inAppLog) {
        if (Constant.DEBUG) {
            String text = formatMessage(LogLevel.INFO.getTag(), tag, msg);
            validate(inAppLog);
            if (inAppLog) {
                inAppLogCenter.i(tag, text);
            } else {
                outAppLogCenter.i(tag, text);
            }
        }
    }

    public static void d(String tag, String msg) {
        d(tag, msg, false);
    }

    private static void d(String tag, String msg, boolean inAppLog) {
        if (Constant.DEBUG) {
            String text = formatMessage(LogLevel.DEBUG.getTag(), tag, msg);
            validate(inAppLog);
            if (inAppLog) {
                inAppLogCenter.d(tag, text);
            } else {
                outAppLogCenter.d(tag, text);
            }
        }
    }

    public static void setFileLogLevel(LogLevel level) {
        setFileLogLevel(level, false);
    }

    private static void setFileLogLevel(LogLevel level, boolean inAppLog) {
        validate(inAppLog);
        if (inAppLog) {
            inAppLogCenter.setFileLogLevel(level);
        } else {
            outAppLogCenter.setFileLogLevel(level);
        }
    }

    public static void changeFileLogLevel(LogLevel level, int duration, String url) {
        changeFileLogLevel(level, duration, url, false);
    }

    private static void changeFileLogLevel(LogLevel level, int duration, String url, boolean inAppLog) {
        validate(inAppLog);
        if (inAppLog) {
            inAppLogCenter.changeFileLogLevel(level, duration, url);
        } else {
            outAppLogCenter.changeFileLogLevel(level, duration, url);
        }
    }

    public static void setLogcatLevel(LogLevel level) {
        setLogcatLevel(level, false);
    }

    private static void setLogcatLevel(LogLevel level, boolean inAppLog) {
        validate(inAppLog);
        if (inAppLog) {
            inAppLogCenter.setLogcatLevel(level);
        } else {
            outAppLogCenter.setLogcatLevel(level);
        }
    }

    public static void changeLogcatLevel(LogLevel level, int duration) {
        changeLogcatLevel(level, duration, false);
    }

    private static void changeLogcatLevel(LogLevel level, int duration, boolean inAppLog) {
        validate(inAppLog);
        if (inAppLog) {
            inAppLogCenter.changeLogcatLogLevel(level, duration);
        } else {
            outAppLogCenter.changeLogcatLogLevel(level, duration);
        }
    }

    private static boolean inAppLogCenterInitialized = false;
    private static boolean outAppLogCenterInitialized = false;

    // private static SDKInAppLogCenter inAppLogCenter = new SDKInAppLogCenter(LogLevel.DEBUG, LogLevel.DEBUG);
    private static SDKInAppLogCenter inAppLogCenter = null;
    private static SDKOutAppLogCenter outAppLogCenter = new SDKOutAppLogCenter(LogLevel.DEBUG, LogLevel.DEBUG);

    private static void validate(boolean inAppLog) {
        if (inAppLog) {
            if (!inAppLogCenterInitialized) {
                // initialize InAppLogCenter
                if (null != InAppApplication.getInstance().getContext()) {
                    inAppLogCenter.initialize(InAppApplication.getInstance().getContext());
                    inAppLogCenterInitialized = true;
                }
            }
        } else {
            if (!outAppLogCenterInitialized) {
                // initialize OutAppLogCenter
                if (null != InAppApplication.getInstance().getContext()) {
                    outAppLogCenter.initialize(InAppApplication.getInstance().getContext());
                    outAppLogCenterInitialized = true;
                }
                if (null != OutAppApplication.getInstance().getContext()) {
                    outAppLogCenter.initialize(OutAppApplication.getInstance().getContext());
                    outAppLogCenterInitialized = true;
                }
            }
        }
    }

}
