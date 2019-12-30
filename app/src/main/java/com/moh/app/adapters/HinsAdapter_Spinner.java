package com.moh.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import com.moh.app.models.HinsUser;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Hussein on 02/04/2016.
 */
public class HinsAdapter_Spinner extends ArrayAdapter<HinsUser> {

    ArrayList<HinsUser> hinsUsers;
    Context context;

    public HinsAdapter_Spinner(Context context, int resource, List<HinsUser> hinsUsers) {
        super(context, resource, hinsUsers);

        this.context = context;
        this.hinsUsers = (ArrayList) hinsUsers;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HinsUser hinsUser = hinsUsers.get(position);
        View view = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);

        CheckedTextView FULL_NAME_AR = (CheckedTextView) view.findViewById(android.R.id.text1);
        //TextView title = (TextView)view.findViewById(R.id.hos_title);

        FULL_NAME_AR.setText(hinsUser.getFULL_NAME_AR());

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}
