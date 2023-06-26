package com.progetto.animeuniverse.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Notification implements Parcelable {
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

    protected Notification(Parcel in) {
        title = in.readString();
        text = in.readString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            checked = in.readBoolean();
        }
    }

    public static final Creator<Notification> CREATOR = new Creator<Notification>() {
        @Override
        public Notification createFromParcel(Parcel in) {
            return new Notification(in);
        }

        @Override
        public Notification[] newArray(int size) {
            return new Notification[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dest.writeBoolean(checked);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
