package com.progetto.animeuniverse.model;

import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class NotificationViewModel extends ViewModel {
    //private ArrayList<Notification> notifications = new ArrayList<>();
    private MutableLiveData<List<Notification>> notifications = new MutableLiveData<>();

    public LiveData<List<Notification>> getNotifications() {
        return notifications;
    }
    public void addNotification(Notification notification){
        List<Notification> currentNotifications = notifications.getValue();
        if(currentNotifications == null){
            currentNotifications = new ArrayList<>();
        }
        currentNotifications.add(notification);
        notifications.setValue(currentNotifications);
    }

}
