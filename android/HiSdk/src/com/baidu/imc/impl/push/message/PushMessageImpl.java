package com.baidu.imc.impl.push.message;

import com.baidu.imc.message.PushMessage;

public class PushMessageImpl implements PushMessage {
    private String message = null;
    private NotificationImpl notification = null;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public NotificationImpl getNotification() {
        return notification;
    }

    public void setNotification(NotificationImpl notification) {
        this.notification = notification;
    }

    public static class NotificationImpl implements Notification {
        private String alert;
        private String title;
        private String extras;

        @Override
        public String getAlert() {
            return alert;
        }

        public void setAlert(String alert) {
            this.alert = alert;
        }

        @Override
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public String getExtras() {
            return extras;
        }

        public void setExtras(String extras) {
            this.extras = extras;
        }

    }
}
