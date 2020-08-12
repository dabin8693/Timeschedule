package org.techtown.timeschedule;

public class categoryList {
    int color;

    public categoryList(int color, String category) {
        this.color = color;
        this.category = category;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    String category;
}
