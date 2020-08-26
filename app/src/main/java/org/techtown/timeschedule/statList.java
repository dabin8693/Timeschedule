package org.techtown.timeschedule;

public class statList {
    String category;
    int persent;

    public statList(String category, int persent) {
        this.category = category;
        this.persent = persent;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPersent() {
        return persent;
    }

    public void setPersent(int persent) {
        this.persent = persent;
    }
}
