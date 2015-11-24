package com.stefanosiano.wavyfish.android;

import java.util.ArrayList;
import java.util.List;

import com.stefanosiano.wavyfish.game.AnalyticsEnums.ShareType;
import com.stefanosiano.wavyfish.game.ShareCallback;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

public class ItemSharer {
	private Activity activity;
	private ProgressDialog progressBar;
	
	public ItemSharer(Activity activity){
		this.activity = activity;
		
		progressBar = new ProgressDialog(activity);
		progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressBar.setMessage("Loading...");
	}
	
	public void showProgressBar(){
		if(progressBar == null){
			progressBar = new ProgressDialog(activity);
			progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressBar.setMessage("Loading...");
		}
		if(!progressBar.isShowing())
			progressBar.show();
	}
	
	public void hideProgressBar(){
        if(progressBar != null && progressBar.isShowing())
        	progressBar.dismiss();
	}
	
	public void shareImage(String title, String text, Uri uri, ShareCallback callback){
		showProgressBar();
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
		shareIntent.putExtra(Intent.EXTRA_TEXT, text);
		shareIntent.setType("image/*");
		showDialog(shareIntent, title, callback, ShareType.score);
	}
	
	public void shareText(String title, String text, ShareCallback callback){
		showProgressBar();
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_TEXT, text);
		shareIntent.setType("text/plain");
		showDialog(shareIntent, title, callback, ShareType.link);
	}
	
	
	private void showDialog(final Intent shareIntent, String title, final ShareCallback callback, final ShareType type){

        final Dialog dialog = new Dialog(activity, R.style.GdxTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.share_dialog_layout);
        ((TextView) dialog.findViewById(R.id.title_listApp_dialog)).setText(title);

        GridView gridView = (GridView) dialog.findViewById(R.id.grid_listApp_dialog);
        final ShareDialogAdapter dialogAdapter = new ShareDialogAdapter(activity);
        gridView.setAdapter(dialogAdapter);

        final List<ResolveInfo> resInfoList = activity.getPackageManager().queryIntentActivities(shareIntent, PackageManager.MATCH_DEFAULT_ONLY);
        final ArrayList<ShareOption> shareList = new ArrayList<ShareOption>();

        for(ResolveInfo resolveInfo : resInfoList)
            shareList.add(ShareOption.getShareOptions(resolveInfo, activity));
        
        dialogAdapter.setNewElements(shareList);
        
        gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShareOption shareOption = shareList.get(position);
                String packageName = shareOption.getPackagename();

                if(callback != null){
                    String sendPackage = packageName;
                    sendPackage = sendPackage.replace("com.", "");
                    sendPackage = sendPackage.replace(".android", "");
                	callback.onShareClicked(sendPackage, type);
                }
                
                shareIntent.setClassName(shareOption.getPackagename(), shareOption.getAppNameDetail());
                activity.startActivity(shareIntent);
                dialog.dismiss();
			}
        });
        dialog.show();
		hideProgressBar();
    }
}
