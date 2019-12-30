package com.moh.app.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.TextView;

import com.moh.app.moh.R;
import com.wang.avi.AVLoadingIndicatorView;

//import pl.droidsonroids.gif.GifTextView;

/**
 * Created by ALMOHANDIS on 7/27/2016.
 */
public class LoadingDialog extends Dialog {

    String msg;
    AVLoadingIndicatorView imgLoad;
    //GifTextView img;
    private Context context;
    private TextView msg_tv;

    public LoadingDialog(Context context, String msg) {
        super(context);
        this.context = context;
        this.msg = msg;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.loading_dialog);
        setCancelable(false);

        msg_tv = (TextView) findViewById(R.id.loading_message);
        msg_tv.setText(msg);
        //img = (GifTextView)findViewById(R.id.loading_icon);
        imgLoad = (AVLoadingIndicatorView) findViewById(R.id.loading_icon);

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
    }

    public void setContext(Context con) {
        this.context = con;
    }

    public void setIcon(int icon) {
        //img.setBackgroundResource(icon);
    }

    public void setMsg(String m) {
        msg = m;
        msg_tv.setText(msg);
    }

    public void showDialog() {
        if (!isShowing())
            imgLoad.show();
        show();
    }

    public void hideDialog() {
        if (isShowing())
            //imgLoad.h();

            dismiss();
    }

}