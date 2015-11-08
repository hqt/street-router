package com.fpt.router.web.viewmodel.staff.Notification;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/27/15.
 */
public class NotificationVM {

    private int id;
    private String content;
    private int state;

    public NotificationVM() {

    }

    public NotificationVM(int id, String content, int state) {
        this.id = id;
        this.content = content;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
