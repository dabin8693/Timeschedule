package org.techtown.timeschedule;

public class dayList {
    String body;
    int type;
    int color;

    public dayList(String body, int type, int color) {
        this.body = body;
        this.type = type;
        this.color = color;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
