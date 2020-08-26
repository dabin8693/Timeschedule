package org.techtown.timeschedule;

public class phonephoneList {
    private String phone;
    private int type;

    public phonephoneList(String phone, int type) {
        this.phone = phone;
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
