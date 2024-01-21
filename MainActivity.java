package com.example.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.core.app.NotificationCompat;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "MyChannel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText keywordEditText = findViewById(R.id.keywordEditText);
        Button searchButton = findViewById(R.id.searchButton);
        Button notifyButton = findViewById(R.id.notifyButton);
        Button clearButton = findViewById(R.id.clearButton);
        Button addButton = findViewById(R.id.addButton);

        final ListView customObjectList = findViewById(R.id.customObjectList);
        final ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        customObjectList.setAdapter(listAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = keywordEditText.getText().toString();
                initiateGoogleSearch(keyword);
            }
        });

        notifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiateNotification();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listAdapter.clear();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String headTitle = "Object";
                String contentOption = "Detailed Content";
                listAdapter.add(headTitle + "\n" + contentOption);
            }
        });
    }

    private void initiateGoogleSearch(String keyword) {
        String searchUrl = "https://www.google.com/search?q=" + Uri.encode(keyword);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(searchUrl));
        startActivity(intent);
    }


    private void initiateNotification() {
        createNotificationChannel();

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Notification Title")
                .setContentText("Notification Content")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Get the NotificationManager
        NotificationManager notificationManager = getSystemService(NotificationManager.class);

        // Display the notification
        notificationManager.notify(1, builder.build());

        // Schedule a handler to dismiss the notification after 10 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Dismiss the notification
                notificationManager.cancel(1);
            }
        }, 10000); // 10000 milliseconds = 10 seconds
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "My Channel";
            String description = "My Channel Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}



