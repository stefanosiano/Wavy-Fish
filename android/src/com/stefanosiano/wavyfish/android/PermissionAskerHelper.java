package com.stefanosiano.wavyfish.android;

import com.stefanosiano.wavyfish.utilities.Enums;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

/**
 * Created by Stefano on 9/25/2015.
 *
 * Class used to know when the permissions are requested, and to handle what are the permissions requested for each place
 */
public class PermissionAskerHelper {
    private static String SYSTEM_DIALOG = " SystemDialog";


    public static boolean checkPermission(Context context, Enums.EnumPermissions permission) {
        final String[] permissions;
        switch (permission) {
            case shareScore:
                permissions = getReadAndWritePermissions();
                break;
            default:
                permissions = new String[0];
        }
        boolean granted = true;
        for (String p : permissions){
            if (ContextCompat.checkSelfPermission(context, p) != PackageManager.PERMISSION_GRANTED) {
                granted = false;
            }
        }
        return granted;
    }


    public static void askPermission(final Activity activity, Enums.EnumPermissions permission) {
        //THIS IS DONE FOR ANDROID M OR NEWER
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;

        final String[] permissions;
        final int requestCode = permission.getRequestCode();
        String toastMessage = "";
        String dialogMessage = "";
        String permissionGroup = "";
        int[] times;

        switch (permission) {
            case shareScore:
                permissions = getReadAndWritePermissions();
                dialogMessage = activity.getString(R.string.shareScoreStoragePermissionRequest);
                permissionGroup = Manifest.permission_group.STORAGE;
                break;
            default:
                permissions = new String[0];
        }

        //something wrong with permissions
        if(permissions.length == 0) {
            return;
        }

        boolean requestRationale = false;
        for(String p : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, p)) {
                requestRationale = true;
            }
        }

        if(requestRationale) {
            times = saveRequest(activity, permissions, false);
            showAskPermissionDialog(activity, permissions, requestCode, dialogMessage);
        }

        else{
            times = saveRequest(activity, permissions, true);
            if(getMax(times) > 1) {
                permissionGroup = permissionGroup.replace("android.permission-group.", "");
                dialogMessage = dialogMessage + "\n\n" + "1) " + activity.getString(R.string.goToSettings)
                        + "\n" + "2) " + activity.getString(R.string.permissionDialogGuide1)
                        + "\n" + "3) "  + activity.getString(R.string.permissionDialogGuide2, permissionGroup);
                if(!TextUtils.isEmpty(toastMessage))
                    showGoToSettingsDialog(activity, dialogMessage);
            }

            ActivityCompat.requestPermissions(activity, permissions, requestCode);
        }
    }

    private static void showGoToSettingsDialog(final Activity activity, String dialogMessage){
        if(TextUtils.isEmpty(dialogMessage))
            return;

        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle(R.string.permissionRequested);
        dialog.setMessage(dialogMessage);
        dialog.setNegativeButton(R.string.notNow, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setPositiveButton(R.string.goToSettings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                i.addCategory(Intent.CATEGORY_DEFAULT);
                i.setData(Uri.parse("package:com.weddingsnap.android"));
                activity.startActivity(i);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private static void showAskPermissionDialog(final Activity activity, final String[] permissions, final int requestCode, String dialogMessage){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle(R.string.permissionRequested);
        dialog.setMessage(dialogMessage);
        dialog.setNegativeButton(R.string.notNow, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setPositiveButton(R.string.allow, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions(activity, permissions, requestCode);
                dialog.dismiss();
            }
        });
        activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
		        dialog.show();
			}
		});
    }

    private static int getMax(int[] times){
        int max = times[0];
        for(int time : times){
            if(max < time)
                max = time;
        }
        return max;
    }

    //we save the number of times each permission has been requested
    private static int[] saveRequest(Activity activity, String[] permissions, boolean systemDialogShown){
        SharedPreferences prefs = activity.getPreferences(Context.MODE_PRIVATE);
        int[] systemTimes = new int[permissions.length];

        for(int i = 0; i < permissions.length; i++){
            systemTimes[i] = prefs.getInt(permissions[i] + SYSTEM_DIALOG, 0);

            if(systemDialogShown){
                systemTimes[i]++;
                prefs.edit().putInt(permissions[i] + SYSTEM_DIALOG, systemTimes[i]).apply();
            }
        }
        return systemTimes;
    }

    private static String[] getReadAndWritePermissions(){
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            return new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        } else{
            return new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        }
    }
    
    public static void permissionsRequested(Activity activity, Enums.EnumPermissions permissionRequested, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, OnPermissionRequested onPermissionRequested){
        //operation cancelled
        if(permissions.length == 0) {
            //onPermissionRequested.onPermissionCancelled();
            return;
        }

        if(requestCode == permissionRequested.getRequestCode()){
            boolean granted = true;
            for(int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED)
                    granted = false;
            }
            
            if(granted) {
                SharedPreferences prefs = activity.getPreferences(Context.MODE_PRIVATE);
                for(String p : permissions){
                    prefs.edit().putInt(p + SYSTEM_DIALOG, 0).apply();
                }
                onPermissionRequested.onPermissionAccepted();
            }
            else{
                onPermissionRequested.onPermissionDenied();
            }
        }
    }


    public interface OnPermissionRequested{
        //public void onPermissionCancelled();
        public void onPermissionDenied();
        public void onPermissionAccepted();
    }
}
