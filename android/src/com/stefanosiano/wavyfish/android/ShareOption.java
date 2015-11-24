package com.stefanosiano.wavyfish.android;

import android.content.Context;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;


public class ShareOption {
    private String packagename;
    private String appName;
    private Drawable appIcon;
    private String appNameDetail;

    public String getAppNameDetail() {
        return appNameDetail;
    }

    public void setAppNameDetail(String appNameDetail) {
        this.appNameDetail = appNameDetail;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    public static ShareOption getShareOptions(ResolveInfo resolveInfo, Context context) {
        ShareOption options = new ShareOption();
        options.setAppName((String) resolveInfo.loadLabel(context.getPackageManager()));
        options.setAppIcon(resolveInfo.loadIcon(context.getPackageManager()));
        options.setPackagename(resolveInfo.activityInfo.applicationInfo.packageName);
        options.setAppNameDetail(resolveInfo.activityInfo.name);
        return options;
    }

}
