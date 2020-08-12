package org.techtown.timeschedule;

public class monthList {
    String body;
    String category;
    int color;
    String time;

    public monthList(String body, String category, int color, String time) {
        this.body = body;
        this.category = category;
        this.color = color;
        this.time = time;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
