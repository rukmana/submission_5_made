package com.example.moviecatalogue.setting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.moviecatalogue.BaseActivity;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.setting.reminder.DailyReminder;
import com.example.moviecatalogue.setting.reminder.ReleaseReminder;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    TextView tvSetting;
    Switch mSwichDaily, mSwichRelease;
    ReleaseReminder releaseReminder;
    DailyReminder dailyReminder;
    SharedPreferences sharedPreferences = null;
    public static final String NAME = "sharedpref_name";
    public static final String KEY_DAILY = "key_daily";
    public static final String KEY_RELEASE = "key_release";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        tvSetting = findViewById(R.id.tv_language_setting);
        mSwichDaily = findViewById(R.id.swc_daily_reminder);
        mSwichRelease = findViewById(R.id.swc_release_today_reminder);
        tvSetting.setOnClickListener(this);

        releaseReminder = new ReleaseReminder();
        dailyReminder = new DailyReminder();

        sharedPreferences = getSharedPreferences(NAME, MODE_PRIVATE);

        if (sharedPreferences.getString(KEY_DAILY, null) != null) {
            mSwichDaily.setChecked(true);
        } else {
            mSwichDaily.setChecked(false);
        }
        if (sharedPreferences.getString(KEY_RELEASE, null) != null) {
            mSwichRelease.setChecked(true);
        } else {
            mSwichRelease.setChecked(false);
        }

        mSwichRelease.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    releaseReminder.setAlarmRelease(SettingActivity.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_RELEASE, "Release");
                    editor.apply();
                } else {
                    releaseReminder.cancelAlarmRelease(SettingActivity.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove(KEY_RELEASE);
                    editor.apply();
                }
            }
        });

        mSwichDaily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    dailyReminder.setAlarmDaily(SettingActivity.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_DAILY, "Daily");
                    editor.apply();
                } else {
                    dailyReminder.cancelAlarmDaily(SettingActivity.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove(KEY_DAILY);
                    editor.apply();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_language_setting:
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
                break;
        }
    }
}
