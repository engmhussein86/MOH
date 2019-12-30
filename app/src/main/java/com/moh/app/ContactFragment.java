package com.moh.app;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.moh.app.moh.R;
import com.moh.app.utils.AppController;
import com.moh.app.utils.ToolsUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {

    //    private LoadingDialog loader_dialog;
    ProgressDialog pDialog;

    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        loader_dialog = AppController.getLoadingDialog(getContext(), "");

        DrawerActivity.navItemIndex = 3;
        ((DrawerActivity) getActivity()).setToolbarTitle();
        ((DrawerActivity) getActivity()).selectNavMenu();

        Typeface font_bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/" + getString(R.string.font_name_bold));

        TextView callTxt = (TextView) view.findViewById(R.id.callTxt);
        TextView sendTxt = (TextView) view.findViewById(R.id.sendTxt);


        final EditText COMPLAINER_NAME = (EditText) view.findViewById(R.id.COMPLAINER_NAME);
        final EditText COMPLAINER_TEL = (EditText) view.findViewById(R.id.COMPLAINER_TEL);
        final EditText RESPONDENT_NAME = (EditText) view.findViewById(R.id.RESPONDENT_NAME);
        final EditText COMPLAINT_SUBJECT = (EditText) view.findViewById(R.id.COMPLAINT_SUBJECT);
        final EditText COMPLAINT = (EditText) view.findViewById(R.id.COMPLAINT);

        callTxt.setTypeface(font_bold);
        sendTxt.setTypeface(font_bold);

        LinearLayout call_btn = (LinearLayout) view.findViewById(R.id.call_btn);

        call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "103")).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ToolsUtils.grantAllUriPermissions(getContext(), intent, Uri.parse("tel:" + "103"));
                getContext().startActivity(intent);
            }
        });

        VideoView videoview = (VideoView) view.findViewById(R.id.videoview);
        MediaController mediaController = new MediaController(getContext());
        mediaController.setAnchorView(videoview);
        videoview.setMediaController(mediaController);
        videoview.setKeepScreenOn(true);
        videoview.setVideoPath("android.resource://" + getActivity().getPackageName() + "/" + R.raw.video_103);
        videoview.start();
        videoview.requestFocus();


        Button addBtn = (Button) view.findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("dd", "aa");

                if (COMPLAINER_TEL.getText().toString().equalsIgnoreCase("") ||
                        RESPONDENT_NAME.getText().toString().equalsIgnoreCase("") ||
                        COMPLAINT_SUBJECT.getText().toString().equalsIgnoreCase("") ||
                        COMPLAINT.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getContext(), "الرجاء ادخال جميع الحقول المطلوبة", Toast.LENGTH_LONG).show();


                } else if (COMPLAINER_TEL.length() < 7) {
                    Toast.makeText(getContext(), "الرجاء ادخال رقم جوال صحيح", Toast.LENGTH_LONG).show();

                } else if (ToolsUtils.isConnectingToInternet(getContext())) {

//                    loader_dialog.setMsg("جاري ارسال الشكوى");
//                    loader_dialog.showDialog();
                    pDialog = new ProgressDialog(getContext());
                    pDialog.setMessage("جاري ارسال الشكوى");
                    pDialog.setIndeterminate(false);
                    pDialog.setCancelable(true);
                    pDialog.show();


                    StringRequest contact_request = new StringRequest(Request.Method.POST, AppController.COMPLAINT_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("response", response);
                            if (response != null) {
                                try {

                                    JSONObject jobj = new JSONObject(response);

                                    if (jobj.getJSONObject("result").getString("msg").toString().equalsIgnoreCase("success")) {
//                                        loader_dialog.hideDialog();

                                        pDialog.dismiss();
                                        Log.e("Status", jobj.getJSONObject("result").getString("msg").toString());
                                        Toast.makeText(getContext(), "تم ارسال الشكوى وسيتم المتابعة من خلال رقم الجوال المدخل", Toast.LENGTH_LONG).show();

                                    } else {
//                                        loader_dialog.hideDialog();

                                        pDialog.dismiss();
                                        Toast.makeText(getContext(), "لم يتم ارسال الشكوى", Toast.LENGTH_LONG).show();

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("onErrorResponse", "error : " + error.getMessage());

//                            loader_dialog.hideDialog();

                            pDialog.dismiss();
                            Toast.makeText(getContext(), "حدث خلل في الاتصال  ", Toast.LENGTH_LONG).show();

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("COMPLAINER_NAME", COMPLAINER_NAME.getText().toString());
                            params.put("COMPLAINER_TEL", COMPLAINER_TEL.getText().toString());
                            params.put("RESPONDENT_NAME", RESPONDENT_NAME.getText().toString());
                            params.put("COMPLAINT_SUBJECT", COMPLAINT_SUBJECT.getText().toString());
                            params.put("COMPLAINT", COMPLAINT.getText().toString());

                            Log.e("params", params.toString());
                            return params;
                        }
                    };

                    AppController.getInstance().addToRequestQueue(contact_request, 30000);


                } else {

//                    loader_dialog.hideDialog();

                    pDialog.dismiss();
                    Toast.makeText(getContext(), "تعذر الاتصال بالانترنت ", Toast.LENGTH_LONG).show();

                }

            }
        });
    }
}
