package com.progetto.animeuniverse.model;

public class Notification {
    private String title;
    private String text;
    private boolean checked;

    public Notification(String title, String text, boolean checked) {
        this.title = title;
        this.text = text;
        this.checked = checked;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
