package net.liubaicai.android.informationlauncher;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AppListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);
        ActionBar bar = getSupportActionBar();
        if (bar!=null){
            bar.hide();
        }
    }
}
