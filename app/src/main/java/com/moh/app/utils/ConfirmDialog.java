package com.moh.app.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.moh.app.moh.R;

/**
 * Created by ALMOHANDIS on 7/27/2016.
 */
public class ConfirmDialog extends Dialog {

    private String title, msg;
    private Context context;
    private Button yes_btn, no_btn;

    private TextView title_tv, msg_tv;
    private int status = 0;

    public ConfirmDialog(Context context, String title, String msg) {
        super(context);
        this.context = context;
        this.title = title;
        this.msg = msg;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.confirm_action_dialog);

        title_tv = (TextView) findViewById(R.id.confirm_dialog_title);
        msg_tv = (TextView) findViewById(R.id.confirm_dialog_msg);

        yes_btn = (Button) findViewById(R.id.yes_btn);
        no_btn = (Button) findViewById(R.id.no_btn);

        yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStatus(1);
                hideDialog();
            }
        });

        no_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStatus(0);
                hideDialog();
            }
        });

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int s) {
        status = s;
    }

    public void showDialog() {
        if (!isShowing())
            show();
    }

    public void hideDialog() {
        if (isShowing())
            dismiss();
    }
}
