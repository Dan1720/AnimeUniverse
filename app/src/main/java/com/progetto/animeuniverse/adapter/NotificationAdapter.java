package com.progetto.animeuniverse.adapter;


import android.content.Intent;
import android.service.notification.StatusBarNotification;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.model.Notification;
import com.progetto.animeuniverse.model.NotificationViewModel;
import com.progetto.animeuniverse.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    private List<Notification> notificationList;
    private LayoutInflater layoutInflater;
    private NotificationViewModel notificationViewModel;

    /*public NotificationAdapter(NotificationViewModel viewModel){
        notificationViewModel = viewModel;
        notificationList = notificationViewModel.getNotifications();
    }*/
    public NotificationAdapter(List<Notification> notifications){
        notificationList = notifications;
    }
    public List<Notification> getData(){
        return notificationList;
    }
    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_item, parent, false);
        return new NotificationViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position){
        Notification notification = notificationList.get(position);
        holder.titleTextView.setText(notification.getTitle());
        holder.textTextView.setText(notification.getText());
        holder.checkBox.setChecked(notification.isChecked());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                notification.setChecked(isChecked);
            }
        });
    }
    @Override
    public int getItemCount(){
        return notificationList.size();
    }




    public static class NotificationViewHolder extends RecyclerView.ViewHolder{
        public TextView titleTextView;
        public TextView textTextView;
        public CheckBox checkBox;
        public NotificationViewHolder(@NonNull View itemView){
            super(itemView);
            titleTextView = itemView.findViewById(R.id.notification_title);
            textTextView = itemView.findViewById(R.id.notification_text);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
    public void updateNotifications(List<Notification> notifications) {
        this.notificationList = notifications != null ? notifications : new ArrayList<>();
        notifyDataSetChanged();
    }
}