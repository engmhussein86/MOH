package com.moh.app;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.moh.app.moh.R;
import com.moh.app.utils.AppController;
import com.moh.app.utils.ToolsUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import ps.gov.mtit.ssologin.SSOHelper;

import static com.moh.app.utils.ToolsUtils.insertToken;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Login extends Fragment {


    public static SharedPreferences sharedPref;
    public static SharedPreferences.Editor editor;
    //    private LoadingDialog loader_dialog;
    ProgressDialog pDialog;
    String current_token;
    String exist_token;


    public Fragment_Login() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();


        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(getActivity(), new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("newToken", newToken);
                current_token = newToken;

            }
        });


//        SharedPreferences sh = getContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
//
//        String ff = FirebaseInstanceId.getInstance().getToken();
//        current_token = sh.getString(getString(R.string.FCM_TOKEN),ff);


        if (sharedPref.getBoolean("isLogged", false)) {
//            checkToken(getContext(),current_token,sharedPref.getString("USER_ID", null));
            ///////////////////////////////////////////////////////////////////////////////////////////////
            Log.e("Mariam_response", sharedPref.getBoolean("valid_token", false) + "1123");
            if (sharedPref.getBoolean("valid_token", false)) {

                StringRequest insert_token = new StringRequest(Request.Method.POST, AppController.ESERVICES_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Mariam_insert_token", response + "123");

                        try {
                            if (response != null) {
                                final JSONObject jobj = new JSONObject(response);
                                Log.e("Status", jobj.get("isEmp").toString());
                            } else {
                                Log.e("response", "null");
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("onErrorResponse", "error : " + error.getMessage());
                        Toast.makeText(getContext(), "حدث خلل في الاتصال2  ", Toast.LENGTH_LONG).show();

                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("User-ID", sharedPref.getString("USER_ID", null));
                        headers.put("Authorization", sharedPref.getString("Token", null));
                        Log.e("onResponse", headers.toString());
                        return headers;
                        //return super.getHeaders();
                    }
                };
                AppController.getInstance().addToRequestQueue(insert_token, 30000);

            } else {//refresh token
//                StringRequest refresh_token = new StringRequest(Request.Method.POST, AppController.REFRESH_TOKEN_URL, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.e("Mariam_refresh", response);
//
//                        try {
//                            if (response != null) {
//                                final JSONObject jobj = new JSONObject(response);
//                                //Log.e("Status",jobj.get("isEmp").toString());
//                                Log.e("Status", jobj.getInt("status")+"");
//                            } else {
//                                Log.e("response", "null");
//                            }
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("onErrorResponse", "error : " + error.getMessage());
//                        Toast.makeText(getContext(), "حدث خلل في الاتصال1  ", Toast.LENGTH_LONG).show();
//
//                    }
//                }) {
//                    @Override
//                    protected Map<String, String> getParams() {
//                        Map<String, String> params = new HashMap<String, String>();
//                        params.put("client_id", sharedPref.getString("client_id",null));
//                        params.put("client_secret", sharedPref.getString("client_secret",null));
//                        params.put("refresh_token", sharedPref.getString("refreshToken",null));
//
//                        Log.e("params", params.toString());
//                        return params;
//                    }
//                };
//                AppController.getInstance().addToRequestQueue(refresh_token, 30000);

                Log.e("ssss", "login");
                return inflater.inflate(R.layout.fragment_login1, container, false);


            }


            ///////////////////////////////////////////////////////////////////////


//////////////////////////////////////////////////////////////////////////////////////////////////////////

            Fragment_EservicesGrid applicationGrid = new Fragment_EservicesGrid();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.frame, applicationGrid);
            fragmentTransaction.commitAllowingStateLoss();
            return inflater.inflate(R.layout.fragment_eservices_grid, container, false);

        } else {
            Log.e("ssss", "login");
            return inflater.inflate(R.layout.fragment_login1, container, false);
        }


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        loader_dialog = AppController.getLoadingDialog(getContext(), "");

        DrawerActivity.navItemIndex = 4;
        ((DrawerActivity) getActivity()).setToolbarTitle();
        ((DrawerActivity) getActivity()).selectNavMenu();

        if (!sharedPref.getBoolean("isLogged", false)) {

            Typeface font_bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/" + getString(R.string.font_name_bold));
            Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/" + getString(R.string.font_name));

            TextView textView = (TextView) view.findViewById(R.id.textView);
            TextView sign_up = (TextView) view.findViewById(R.id.sign_up);
            Button loginBtn = (Button) view.findViewById(R.id.loginBtn);
            Button sso_btn = (Button) view.findViewById(R.id.sso_btn);
            final EditText user_name = (EditText) view.findViewById(R.id.user_name);
            final EditText user_pass = (EditText) view.findViewById(R.id.user_pass);

            user_name.setTypeface(font);
            user_pass.setTypeface(font);
            sign_up.setTypeface(font);
            loginBtn.setTypeface(font_bold);
            textView.setTypeface(font_bold);

            sign_up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://elogin.gov.ps/new/register"));
                    startActivity(browserIntent);
                }
            });

            sso_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editor.putString("client_id", "APP_MOH");
                    editor.putString("client_secret", "_530210c86901facd67cac2a193e39cc37e1d936799");
                    editor.commit();

                    SSOHelper.doLogin(getActivity(), sharedPref.getString("client_id", null), sharedPref.getString("client_secret", null));
                }
            });

            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("ssss", "login btn");

//                    loader_dialog.setMsg("جاري تسجيل الدخول");
//                    loader_dialog.setCanceledOnTouchOutside(true);
//                    loader_dialog.showDialog();
                    pDialog = new ProgressDialog(getContext());
                    pDialog.setMessage("جاري تسجيل الدخول");
                    pDialog.setIndeterminate(false);
                    pDialog.setCancelable(true);
                    pDialog.show();


                    if (ToolsUtils.isConnectingToInternet(getContext())) {

//                        loader_dialog.setMsg("جاري تسجيل الدخول");
//                        loader_dialog.setCanceledOnTouchOutside(true);
//                        loader_dialog.showDialog();

                        StringRequest ss = new StringRequest(Request.Method.POST, AppController.ESERVICES_URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.e("response", response);

                                try {
                                    if (response != null) {
                                        final JSONObject jobj = new JSONObject(response);
                                        //Log.e("Status",jobj.get("isEmp").toString());
                                        Log.e("Status", jobj.getInt("status") + "");


//                                        loader_dialog.hideDialog();
                                        pDialog.dismiss();
                                        if (jobj.getInt("status") == 200) {
                                            Log.e("Status", jobj.getString("token"));
                                            Toast.makeText(getContext(), "تم الدخول بنجاح", Toast.LENGTH_LONG).show();

                                            final String id = jobj.getString("id");

                                            insertToken(getContext(), current_token, id);

                                            editor.putBoolean("isLogged", true);
                                            editor.putString("USER_ID", jobj.getString("id"));
                                            editor.putString("Token", jobj.getString("token"));
                                            editor.putString("USER_FULL_NAME", jobj.getString("name"));
                                            editor.putString("isEmp", jobj.get("isEmp").toString());
                                            editor.putString("user_name", user_name.getText().toString());
                                            editor.putString("user_pass", user_pass.getText().toString());

                                            editor.commit();

                                            if (sharedPref.getBoolean("isLogged", false)) {

                                                Fragment_EservicesGrid applicationGrid = new Fragment_EservicesGrid();
                                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                                                        android.R.anim.fade_out);
                                                fragmentTransaction.replace(R.id.frame, applicationGrid);
                                                fragmentTransaction.commitAllowingStateLoss();
                                            }

                                        } else {
//                                            loader_dialog.hideDialog();
                                            pDialog.dismiss();
                                            Toast.makeText(getContext(), "خطأ في اسم المستخدم أو كلمة المرور", Toast.LENGTH_LONG).show();

                                            editor.putBoolean("isLogged", false);
                                            editor.commit();

                                        }


                                    } else {
//                                        loader_dialog.hideDialog();
                                        pDialog.dismiss();
                                        Log.e("response", "null");
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("onErrorResponse", "error : " + error.getMessage());
//                                loader_dialog.hideDialog();
                                pDialog.dismiss();
                                Toast.makeText(getContext(), "حدث خلل في الاتصال  ", Toast.LENGTH_LONG).show();

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("username", user_name.getText().toString());
                                params.put("password", user_pass.getText().toString());

                                Log.e("params", params.toString());
                                return params;
                            }
                        };


                        AppController.getInstance().addToRequestQueue(ss, 30000);

                    } else {

                        Toast.makeText(getContext(), "تعذر الاتصال بالانترنت ", Toast.LENGTH_LONG).show();
//                        loader_dialog.hideDialog();
                        pDialog.dismiss();

                    }


                }
            });


        }
    }

    /////////////////////////////////////////////////////////////////////////////////////

}
