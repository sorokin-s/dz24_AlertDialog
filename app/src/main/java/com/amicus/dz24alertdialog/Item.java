package com.amicus.dz24alertdialog;

public class Item {
    private int imageResId;

    public boolean isChoice() {
        return choice;
    }

    public void setChoice(boolean choice) {
        this.choice = choice;
    }

    private String text1;
    private boolean choice;
    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    private String text2;


    public Item(int id, String text1, String text2) {
        this.choice=false;
        this.text1 = text1;
        this.text2 = text2;
        this.imageResId = id;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int idImage) {
        this.imageResId = idImage;
    }
}
