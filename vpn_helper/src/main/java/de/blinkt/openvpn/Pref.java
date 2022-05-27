package de.blinkt.openvpn;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Pref {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Long notSaved;

    public Pref(Context context) {
        sharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        notSaved = getAllowedTime() - getUsageTime();
    }

    public Long getNotSavedLeftTime() {
        return notSaved;
    }

    public void setNotSaved(long time) {
        notSaved = (getAllowedTime() - getUsageTime()) - time;
    }

    public void resetNotSaved() {
        notSaved = getAllowedTime() - getUsageTime();
    }

    public Long getLeft() {
        return getAllowedTime() - getUsageTime();
    }

    public Long getUsageTime() {
        String currentDate = new SimpleDateFormat("dd_MM_yyyy", Locale.getDefault()).format(new Date());
        return sharedPreferences.getLong(currentDate + "_usage", 0);
    }

    public void setUsageTime(long time) {
        String currentDate = new SimpleDateFormat("dd_MM_yyyy", Locale.getDefault()).format(new Date());
        editor.putLong(currentDate + "_usage", getUsageTime() + time);
        editor.apply();
    }

    public Long getAllowedTime() {
        String currentDate = new SimpleDateFormat("dd_MM_yyyy", Locale.getDefault()).format(new Date());
        return sharedPreferences.getLong(currentDate + "_allowed", 1800000);
    }

    public void addAllowedTime(long time) {
        String currentDate = new SimpleDateFormat("dd_MM_yyyy", Locale.getDefault()).format(new Date());
        editor.putLong(currentDate + "_allowed", getAllowedTime() + time);
        editor.apply();
    }
}
