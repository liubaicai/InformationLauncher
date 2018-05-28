package net.liubaicai.android.informationlauncher.applist;

import java.util.ArrayList;

/**
 * Created by mac on 2017/11/22.
 */
public class StaticData {
    public static ArrayList<AppModel> getApps() {
        return apps;
    }

    public static void setApps(ArrayList<AppModel> apps) {
        StaticData.apps = apps;
    }

    public static ArrayList<AppModel> apps;
}
