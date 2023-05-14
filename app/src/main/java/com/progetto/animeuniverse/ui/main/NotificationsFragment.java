package com.progetto.animeuniverse.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import androidx.appcompat.widget.Toolbar;
import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.service.notification.NotificationListenerService;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.adapter.NotificationAdapter;
import com.progetto.animeuniverse.model.Notification;
import com.progetto.animeuniverse.model.NotificationViewModel;
import com.progetto.animeuniverse.util.Constants;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class NotificationsFragment extends Fragment {


    private String textTitle = "Titolo prova" + getNotificationId();
    private String textContent = "Prima notifica" + getNotificationId();
    private boolean checkBoxChecked;
    private boolean notificationSent = false;
    private View rootView;

    private int id = 0;

    private RecyclerView recyclerView;
    private NotificationAdapter notificationAdapter;
    private NotificationViewModel notificationViewModel;

    public NotificationsFragment() {
        // Required empty public constructor
    }

    public static NotificationsFragment newInstance() {
        return new NotificationsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createNotificationChannel();
        resetNotificationFlag();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_notifications, container, false);
        recyclerView = rootView.findViewById(R.id.notification_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        notificationAdapter = new NotificationAdapter(new ArrayList<>());
        recyclerView.setAdapter(notificationAdapter);
        notificationViewModel = new ViewModelProvider(requireActivity()).get(NotificationViewModel.class);
        notificationViewModel.getNotifications().observe(getViewLifecycleOwner(), notifications -> {
            notificationAdapter.updateNotifications(notifications);
        });

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        boolean var = shouldSendNotification();
        System.out.println(var);
        if(var){
            Intent intent = new Intent(getContext(), this.getClass());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), Constants.CHANNEL_ID)
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle(textTitle)
                    .setContentText(textContent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            notificationManager.notify(getNotificationId(), builder.build());
            Notification notification = new Notification(textTitle, textContent, checkBoxChecked);
            notificationViewModel.addNotification(notification);
            notificationAdapter.notifyDataSetChanged();

        }
        Button button = (Button) getView().findViewById(R.id.delete_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                List<Notification> myData = ((NotificationAdapter) recyclerView.getAdapter()).getData();
                int count = 0;
                for(int i = 0; i < myData.size(); i++){
                    if(myData.get(i).isChecked()){
                        count++;
                    }
                }
                for(int i = myData.size() - 1; i >= 0; i--){
                    if(myData.get(i).isChecked()){
                        myData.remove(i);
                        recyclerView.getAdapter().notifyItemRemoved(i);
                    }

                }
            }
        });

    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(Constants.CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = (NotificationManager)
                    ContextCompat.getSystemService(getContext(),NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private int getNotificationId() {
        return (int) System.currentTimeMillis();
    }

    private boolean shouldSendNotification() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean isNotNotificationSent = preferences.getBoolean("notification_sent_flag", false);
        if (!isNotNotificationSent) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("notification_sent_flag", true);
            editor.apply();
            return true;
        } else {
            return false;
        }

    }
    private void resetNotificationFlag() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("notification_sent_flag", false);
        editor.apply();
    }


}