package com.example.madgassignment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_navigation_menu);

        ImageView homeIcon = findViewById(R.id.nav_home);
        ImageView searchIcon = findViewById(R.id.nav_search);
        ImageView notificationIcon = findViewById(R.id.nav_notification);
        ImageView settingsIcon = findViewById(R.id.nav_settings);

        String activePage = getIntent().getStringExtra("active_page");

        if ("home".equals(activePage)) {
            homeIcon.setSelected(true);
        } else if ("search".equals(activePage)) {
            searchIcon.setSelected(true);
        } else if ("notification".equals(activePage)) {
            notificationIcon.setSelected(true);
        } else if ("settings".equals(activePage)) {
            settingsIcon.setSelected(true);
        }

        // Home
        homeIcon.setOnClickListener(v -> navigateTo(HomePageActivity.class, homeIcon, searchIcon, notificationIcon, settingsIcon));

        // Search
        searchIcon.setOnClickListener(v -> navigateTo(SearchActivity.class, homeIcon, searchIcon, notificationIcon, settingsIcon));

        // Notifications
        notificationIcon.setOnClickListener(v -> navigateTo(NotificationsActivity.class, homeIcon, searchIcon, notificationIcon, settingsIcon));

        // Settings
        settingsIcon.setOnClickListener(v -> navigateTo(SettingsActivity.class, homeIcon, searchIcon, notificationIcon, settingsIcon));
    }

    // Update the icons
    private void navigateTo(Class<?> activityClass, ImageView... icons) {
        for (ImageView icon : icons) {
            icon.setSelected(false); // Reset all icons to unselected
        }

        // Start the new activity
        startActivity(new Intent(this, activityClass));
        finish();
    }

    // Method to update the active page in the bottom navigation
    public void setActivePage(String activePage) {
        ImageView homeIcon = findViewById(R.id.nav_home);
        ImageView searchIcon = findViewById(R.id.nav_search);
        ImageView notificationIcon = findViewById(R.id.nav_notification);
        ImageView settingsIcon = findViewById(R.id.nav_settings);

        homeIcon.setSelected("home".equals(activePage));
        searchIcon.setSelected("search".equals(activePage));
        notificationIcon.setSelected("notification".equals(activePage));
        settingsIcon.setSelected("settings".equals(activePage));
    }
}
