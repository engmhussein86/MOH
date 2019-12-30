package com.moh.app;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.moh.app.adapters.EservicesAdapter;
import com.moh.app.moh.R;
import com.moh.app.utils.AppController;
import com.moh.app.utils.ToolsUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.moh.app.Fragment_Login.sharedPref;
import static com.moh.app.utils.ToolsUtils.checkToken;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_EservicesGrid extends Fragment {
    GridView gv;
    List<String> Titles = new ArrayList<String>();
    List Icons = new ArrayList<>();
    String HolidayNotify;
    String fcm_token;
    private Activity mAct;
    private String current_token;

    public Fragment_EservicesGrid() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_eservices_grid, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DrawerActivity.navItemIndex = 5;
        ((DrawerActivity) getActivity()).setToolbarTitle();

        gv = (GridView) view.findViewById(R.id.grid_view);

        SharedPreferences sh = getContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(getActivity(), new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("newToken", newToken);
                fcm_token = newToken;

            }
        });


        current_token = sh.getString(getString(R.string.FCM_TOKEN), fcm_token);

        checkToken(getContext(), current_token, sharedPref.getString("USER_ID", null));


        // public applications
        Titles.add("التأمين الصحي");
        Titles.add("الالتزامات المالية");
        Titles.add("مواعيد العمليات");
        Titles.add("مواعيد العيادات الخارجية");
//        Titles.add("التحاليل المخبرية");

        Icons.add(R.drawable.ic_import_contacts_black_24dp);
        Icons.add(R.drawable.money_bag_blue);
        Icons.add(R.drawable.calendar_blue);
        Icons.add(R.drawable.calendar_blue);
//        Icons.add(R.drawable.calendar_blue);


        //MOH EMP applications
        Log.e("Mariam_isEmp", sharedPref.getString("isEmp", "0"));
        if (sharedPref.getString("isEmp", "0").equalsIgnoreCase("1")) {

            Titles.add("الدوام");
            Icons.add(R.drawable.ic_perm_contact_calendar_black_24dp);

            Titles.add("الاجازة الالكترونية");
            Icons.add(R.drawable.calendar_blue);

            Titles.add("الورديات");
            Icons.add(R.drawable.calendar_blue);

            Titles.add("بروتوكول الولادة");
            Icons.add(R.drawable.baby_icon_blue);

        }

        if (ToolsUtils.isConnectingToInternet(getContext())) {

            StringRequest permission_request = new StringRequest(Request.Method.POST, AppController.PERMISSION_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("response", response);

                    try {
                        if (response != null) {
                            Log.e("Status", "ssss");

                            final JSONObject jobj = new JSONObject(response);

                            JSONArray obj = jobj.getJSONArray("PRIVS");
                            JSONArray menu = obj.getJSONObject(0).getJSONArray("menu");
                            Log.e("error", menu.getJSONObject(0).getString("MENU_NAME"));

                            for (int i = 0; i < menu.length(); i++) {

                                Titles.add(menu.getJSONObject(i).getString("MENU_NAME"));
                                Icons.add(R.drawable.ic_android_black_24dp);
                            }

                            String[] activityTitles = new String[Titles.size()];
                            Titles.toArray(activityTitles);

                            int[] Application_Icons = new int[Icons.size()];
                            Application_Icons = toIntArray(Icons);


//        Bundle bundle = this.getArguments();
//        if (bundle != null) {
//            HolidayNotify = bundle.getString("HolidayNotify", null);
//        }
//
//        Log.e("HolidayNotify",HolidayNotify);


                            gv.setAdapter(new EservicesAdapter(getActivity(), getContext(), activityTitles, Application_Icons, HolidayNotify));


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

                    Toast.makeText(getContext(), "حدث خلل في الاتصال  ", Toast.LENGTH_LONG).show();

                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id", sharedPref.getString("USER_ID", null));
                    Log.e("params", params.toString());
                    return params;
                }
            };


            AppController.getInstance().addToRequestQueue(permission_request, 30000);

        } else {

            Toast.makeText(getContext(), "تعذر الاتصال بالانترنت ", Toast.LENGTH_LONG).show();


        }

        Log.e("error", Titles.toString() + "");

        String[] activityTitles = new String[Titles.size()];
        Titles.toArray(activityTitles);

        int[] Application_Icons = new int[Icons.size()];
        Application_Icons = toIntArray(Icons);

//        Bundle bundle = this.getArguments();
//        if (bundle != null) {
//            HolidayNotify = bundle.getString("HolidayNotify", null);
//        }
//
//        Log.e("HolidayNotify",HolidayNotify);


        gv = (GridView) view.findViewById(R.id.grid_view);

        gv.setAdapter(new EservicesAdapter(getActivity(), getContext(), activityTitles, Application_Icons, HolidayNotify));

    }

    /////////////////////////////////////////////////////////
    int[] toIntArray(List<Integer> list) {
        int[] ret = new int[list.size()];
        int i = 0;
        for (Integer e : list)
            ret[i++] = e.intValue();
        return ret;
    }

}
