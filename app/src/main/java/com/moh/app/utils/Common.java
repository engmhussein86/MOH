package com.moh.app.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.moh.app.Fragment_Login;
import com.moh.app.models.HinsUser;
import com.moh.app.models.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Common {


    public static void HinsData(final CallBack onCallBack, final Context context) {
        final ArrayList<HinsUser> hinsUsers = new ArrayList<>();


        if (ToolsUtils.isConnectingToInternet(context)) {

            StringRequest HinsRequest = new StringRequest(Request.Method.POST, AppController.HINS_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("response", response);
                    if (response != null) {
                        try {

                            Users users = new Gson().fromJson(response, Users.class);
                            Log.e("Error", "users" + users.getResult().size());


                            JSONObject jobj = new JSONObject(response);
                            JSONArray jArr = jobj.getJSONArray("result");

                            Log.e("response", "" + jArr.length());
                            hinsUsers.clear();
                            if (jArr.length() > 0) {

                                for (int i = 0; i < jArr.length(); i++) {
                                    String User_id, relation_desc, DEP_NAME;
                                    int IsDep;
                                    Log.e("DEP_ID_NO", jArr.getJSONObject(i).getString("DEP_ID_NO").toString());

                                    if (jArr.getJSONObject(i).getString("DEP_ID_NO").toString().trim().equalsIgnoreCase("0")) {
                                        User_id = jArr.getJSONObject(i).getString("ID_NO").toString();
                                        IsDep = 1;//صاحب التأمين
                                    } else {
                                        User_id = jArr.getJSONObject(i).getString("DEP_ID_NO").toString();
                                        IsDep = 2;//مرافق
                                    }

                                    if (jArr.getJSONObject(i).getString("DEP_NAME").toString().trim().equalsIgnoreCase("-")) {
                                        DEP_NAME = jArr.getJSONObject(i).getString("FULL_NAME_AR").toString();
                                    } else {
                                        DEP_NAME = jArr.getJSONObject(i).getString("DEP_NAME").toString();
                                    }


                                    if (jArr.getJSONObject(i).getString("REL_TYPE_DESC").toString().trim().equalsIgnoreCase("-")) {
                                        relation_desc = "صاحب التأمين";
                                    } else {
                                        relation_desc = jArr.getJSONObject(i).getString("REL_TYPE_DESC").toString();
                                    }


                                    HinsUser hinsUser_record = new HinsUser(User_id, DEP_NAME, relation_desc, IsDep);
                                    hinsUsers.add(hinsUser_record);


                                }
                            }
                            Log.e("size", hinsUsers.size() + "");
                            onCallBack.onSuccess(hinsUsers);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            onCallBack.onFail(e.toString());
                        }

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("onErrorResponse", "error : " + error.getMessage());
//                    loader_dialog.hideDialog();
                    Toast.makeText(context, "حدث خلل في الاتصال  ", Toast.LENGTH_LONG).show();

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("User-ID", Fragment_Login.sharedPref.getString("USER_ID", null));
                    headers.put("Authorization", Fragment_Login.sharedPref.getString("Token", null));
                    Log.e("onResponse", headers.toString());
                    return headers;
                    //return super.getHeaders();
                }
            };

            AppController.getInstance().addToRequestQueue(HinsRequest, 30000);
        } else {

            Toast.makeText(context, "تعذر الاتصال بالانترنت ", Toast.LENGTH_LONG).show();
//            loader_dialog.hideDialog();

        }

        // return hinsUsers;


    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    public static void GET_ALL_HOSPITAL_PR(final CallBackHash onCallBack, final Context context) {
        final ArrayList<HashMap<String, String>> hospitals = new ArrayList<>();

        HashMap<String, String> map = new HashMap<>();

        map.put("H_CODE", "0");
        map.put("H_NAME_AR", "اختر مستشفى");
        hospitals.add(map);


        if (ToolsUtils.isConnectingToInternet(context)) {

            StringRequest HospitalRequest = new StringRequest(Request.Method.GET, AppController.GET_ALL_HOSPITAL_PR, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("response", response);
                    if (response != null) {
                        try {
                            //JSONObject jobj = new JSONObject(response);
                            JSONArray jArr = new JSONArray(response);

                            Log.e("response", "" + jArr.length());
                            if (jArr.length() > 0) {

                                for (int i = 0; i < jArr.length(); i++) {
                                    //Log.e("Error",jArr.getJSONObject(i).getString("LOC_SID").toString());
                                    HashMap<String, String> map = new HashMap<>();

                                    map.put("H_CODE", jArr.getJSONObject(i).getString("H_CODE"));
                                    map.put("H_NAME_AR", jArr.getJSONObject(i).getString("H_NAME_AR"));
                                    hospitals.add(map);


                                }
                            }
                            Log.e("size", hospitals.size() + "");
                            onCallBack.onSuccess(hospitals);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            onCallBack.onFail(e.toString());
                        }

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("onErrorResponse", "error : " + error.getMessage());
//                    loader_dialog.hideDialog();
                    Toast.makeText(context, "حدث خلل في الاتصال  ", Toast.LENGTH_LONG).show();

                }
            }) {

            };

            AppController.getInstance().addToRequestQueue(HospitalRequest, 30000);
        } else {

            Toast.makeText(context, "تعذر الاتصال بالانترنت ", Toast.LENGTH_LONG).show();
//            loader_dialog.hideDialog();

        }

        // return hinsUsers;


    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
    public static void GET_ALL_CLASS_PR_BY_HOS(final CallBackHash onCallBack, final Context context, String H_CODE) {
        final ArrayList<HashMap<String, String>> clinic = new ArrayList<>();

        HashMap<String, String> map = new HashMap<>();

        map.put("CS_CODE", "0");
        map.put("CS_NAME_AR", "اختر عيادة");
        clinic.add(map);


        if (ToolsUtils.isConnectingToInternet(context)) {

            StringRequest HospitalRequest = new StringRequest(Request.Method.GET, AppController.GET_ALL_CLASS_PR_BY_HOS + H_CODE, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("response", response);
                    if (response != null) {
                        try {
                            //JSONObject jobj = new JSONObject(response);
                            JSONArray jArr = new JSONArray(response);

                            Log.e("response", "" + jArr.length());
                            if (jArr.length() > 0) {

                                for (int i = 0; i < jArr.length(); i++) {
                                    //Log.e("Error",jArr.getJSONObject(i).getString("LOC_SID").toString());
                                    HashMap<String, String> map = new HashMap<>();

                                    map.put("CS_CODE", jArr.getJSONObject(i).getString("CS_CODE"));
                                    map.put("CS_NAME_AR", jArr.getJSONObject(i).getString("CS_NAME_AR"));
                                    clinic.add(map);


                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            onCallBack.onFail(e.toString());
                        }

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("onErrorResponse", "error : " + error.getMessage());
//                    loader_dialog.hideDialog();
                    Toast.makeText(context, "حدث خلل في الاتصال  ", Toast.LENGTH_LONG).show();

                }
            }) {

            };

            AppController.getInstance().addToRequestQueue(HospitalRequest, 30000);
        } else {

            Toast.makeText(context, "تعذر الاتصال بالانترنت ", Toast.LENGTH_LONG).show();
//            loader_dialog.hideDialog();

        }

        Log.e("size", clinic.size() + "");
        onCallBack.onSuccess(clinic);


    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////

    public interface CallBackHash {
        void onSuccess(ArrayList<HashMap<String, String>> array);

        void onFail(String msg);
    }
}


