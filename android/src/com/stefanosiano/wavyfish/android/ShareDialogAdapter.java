package com.stefanosiano.wavyfish.android;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ShareDialogAdapter extends ArrayAdapter<Object> {
    private final Activity activity;
    private ArrayList<ShareOption> shareOptionsList;

    public ShareDialogAdapter(Activity activity) {
        super(activity.getApplicationContext(), 0);
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflator = activity.getLayoutInflater();
            convertView = inflator.inflate(R.layout.share_dialog_item, null);
        }

        ((TextView) convertView.findViewById(R.id.appname_share_dialog_item)).setText(shareOptionsList.get(position).getAppName());
        ((ImageView) convertView.findViewById(R.id.image_share_dialog_item)).setImageDrawable(shareOptionsList.get(position).getAppIcon());

        return convertView;
    }

    public void setNewElements(ArrayList<ShareOption> newList) {
        shareOptionsList = newList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return shareOptionsList == null ? 0 : shareOptionsList.size();
    }

    @Override
    public ShareOption getItem(int position) {
        return shareOptionsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
