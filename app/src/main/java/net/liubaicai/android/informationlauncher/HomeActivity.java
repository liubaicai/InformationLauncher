package net.liubaicai.android.informationlauncher;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import net.liubaicai.android.informationlauncher.tools.EmailEx;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class HomeActivity extends AppCompatActivity {

    Button all_apps_button;
    Button set_wall_button;

    FrameLayout root_layout;

    Timer timer;
    TextView widget_time_text;
    TextView widget_date_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ActionBar bar = getSupportActionBar();
        if (bar!=null){
            bar.hide();
        }

        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this.getApplicationContext());
        Drawable wallpaperDrawable = wallpaperManager.getDrawable();

        root_layout = (FrameLayout)findViewById(R.id.root_layout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            root_layout.setBackground(wallpaperDrawable);
        }
        else {
            root_layout.setBackgroundDrawable(wallpaperDrawable);
        }

        all_apps_button = (Button)findViewById(R.id.all_apps_button);
        all_apps_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),AppListActivity.class);
                startActivity(intent);
            }
        });

        widget_time_text = (TextView)findViewById(R.id.widget_time_text);
        widget_date_text = (TextView)findViewById(R.id.widget_date_text);
        widget_time_text.setText(getWidgetTime());
        widget_date_text.setText(getWidgetDate());
        timer = new Timer();
        timer.schedule(task, 1000, 1000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this.getApplicationContext());
        Drawable wallpaperDrawable = wallpaperManager.getDrawable();

        root_layout = (FrameLayout)findViewById(R.id.root_layout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            root_layout.setBackground(wallpaperDrawable);
        }
        else {
            root_layout.setBackgroundDrawable(wallpaperDrawable);
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        return;
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable(){
                @Override
                public void run() {
                    widget_time_text.setText(getWidgetTime());
                    widget_date_text.setText(getWidgetDate());
                }}
            );
        }
    };

    public static final int WEEKDAYS = 7;

    public static String[] WEEK = {
            "星期天",
            "星期一",
            "星期二",
            "星期三",
            "星期四",
            "星期五",
            "星期六"
    };

    public static String getWidgetTime() {
        DateFormat df = new SimpleDateFormat("HH:mm", Locale.CHINA);
        return df.format(new Date());
    }

    public static String getWidgetDate() {

        DateFormat df = new SimpleDateFormat("MM月dd日", Locale.CHINA);
        return df.format(new Date()) + " " + getWeek();
    }

    public static String getWeek() {
        int dayIndex = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        if (dayIndex < 1 || dayIndex > WEEKDAYS) {
            return null;
        }
        return WEEK[dayIndex - 1];
    }
}
