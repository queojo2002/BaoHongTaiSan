package com.example.baohongtaisan_2.Model;

public class NotificationSendData {
    private NotificationDataBaoHong data;
    private String to;

    public NotificationSendData() {
    }

    public NotificationSendData(NotificationDataBaoHong notificationDataBaoHong, String to) {
        this.data = notificationDataBaoHong;
        this.to = to;
    }

    public NotificationDataBaoHong getNotificationDataBaoHong() {
        return data;
    }

    public void setNotificationDataBaoHong(NotificationDataBaoHong notificationDataBaoHong) {
        this.data = notificationDataBaoHong;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
