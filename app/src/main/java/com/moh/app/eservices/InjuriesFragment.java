package com.moh.app.eservices;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.moh.app.DrawerActivity;
import com.moh.app.Fragment_Login;
import com.moh.app.adapters.HosAdapter;
import com.moh.app.adapters.InjuriesAdapter;
import com.moh.app.models.InjuriesClass;
import com.moh.app.moh.R;
import com.moh.app.utils.AppController;
import com.moh.app.utils.ToolsUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import static com.moh.app.DrawerActivity.loader_dialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class InjuriesFragment extends Fragment {

    ArrayList<HashMap<String, String>> hospitals;
    ImageView hos_search_btn;
    Spinner hos_spinner;
    HosAdapter hosAdapter;
    ArrayList<InjuriesClass> Injuries = new ArrayList<>();
    InjuriesAdapter injuriesAdapter;
    RecyclerView recyclerView;
    //    private LoadingDialog loader_dialog;
    ProgressDialog pDialog;
    LinearLayout header;


    public InjuriesFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_injuries, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DrawerActivity.navItemIndex = 12;
        ((DrawerActivity) getActivity()).setToolbarTitle();

        header = (LinearLayout) view.findViewById(R.id.header);
//        loader_dialog = AppController.getLoadingDialog(getContext(), "");
        hospitals = new ArrayList<HashMap<String, String>>();
        hos_search_btn = (ImageView) view.findViewById(R.id.hos_search_btn);

        hosAdapter = new HosAdapter(getContext(), 0, hospitals);
        injuriesAdapter = new InjuriesAdapter(getContext(), Injuries);

        hos_spinner = (Spinner) view.findViewById(R.id.hos_spinner);
        hos_spinner.setAdapter((SpinnerAdapter) hosAdapter);

        recyclerView = (RecyclerView) view.findViewById(R.id.result_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(injuriesAdapter);
        injuriesAdapter.notifyDataSetChanged();

        GET_ALL_HOSPITALS();


        hos_search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, String> hos = (HashMap) hos_spinner.getItemAtPosition(hos_spinner.getSelectedItemPosition());
                int loc = Integer.parseInt(hos.get("H_CODE").toString());

                Log.e("loc", "" + loc);
                HOSPITALS_INJURIES_COUNT(loc);

            }
        });

    }

    //////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void GET_ALL_HOSPITALS() {
        if (ToolsUtils.isConnectingToInternet(getContext())) {

            StringRequest hospital_request = new StringRequest(Request.Method.POST, AppController.HOSPITALS_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("response", response);

                    try {
                        if (response != null) {
                            Log.e("Status", "ssss");

                            final JSONObject jobj = new JSONObject(response);
                            JSONArray jArr = jobj.getJSONArray("result");

                            for (int i = 0; i < jArr.length(); i++) {
                                //Log.e("Error",jArr.getJSONObject(i).getString("LOC_SID").toString());
                                HashMap<String, String> map = new HashMap<>();

                                map.put("H_CODE", jArr.getJSONObject(i).getString("H_CODE"));
                                map.put("H_NAME_AR", jArr.getJSONObject(i).getString("H_NAME_AR"));
                                hospitals.add(map);

                            }


                        } else {

                            Log.e("response", "null");
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    hosAdapter.notifyDataSetChanged();
                    hos_spinner.setSelection(0);


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
                    params.put("username", Fragment_Login.sharedPref.getString("user_name", null));
                    params.put("password", Fragment_Login.sharedPref.getString("user_pass", null));

                    Log.e("params", params.toString());
                    return params;
                }
            };


            AppController.getInstance().addToRequestQueue(hospital_request, 30000);

        } else {

            Toast.makeText(getContext(), "تعذر الاتصال بالانترنت ", Toast.LENGTH_LONG).show();


        }


    }

    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void HOSPITALS_INJURIES_COUNT(final int H_CODE) {
        Injuries.clear();
//        loader_dialog.setMsg("جاري تحميل أعداد الاصابات");
//        loader_dialog.setCanceledOnTouchOutside(true);
//        loader_dialog.showDialog();
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("جاري تحميل أعداد الاصابات");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        if (ToolsUtils.isConnectingToInternet(getContext())) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, AppController.HOSPITALS_INJURIES_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("response", response.toString());
                            try {
                                if (response != null) {
                                    JSONObject jobj = new JSONObject(response);
                                    JSONArray jArr = jobj.getJSONArray("result");

                                    Log.e("Error", jArr.length() + "");

                                    if (jArr.length() > 0) {

                                        for (int i = 0; i < jArr.length(); i++) {
                                            String AK_NAME_AR = jArr.getJSONObject(i).getString("AK_NAME_AR").toString();
                                            int COUNT_RESULT = Integer.parseInt(jArr.getJSONObject(i).getString("COUNT_RESULT"));

                                            InjuriesClass injuries_record = new InjuriesClass(COUNT_RESULT, H_CODE, AK_NAME_AR);
                                            Injuries.add(injuries_record);


                                        }
                                    }
                                    Log.e("size", Injuries.size() + "");


//                                    loader_dialog.hideDialog();
                                    pDialog.dismiss();
                                    header.setVisibility(View.VISIBLE);
                                    recyclerView.setAdapter(injuriesAdapter);
                                    injuriesAdapter.notifyDataSetChanged();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
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
                    params.put("H_CODE", "" + H_CODE);
                    params.put("date_p", ToolsUtils.getCurrentDate());
                    params.put("username", Fragment_Login.sharedPref.getString("user_name", null));
                    params.put("password", Fragment_Login.sharedPref.getString("user_pass", null));
                    Log.e("Error", params.toString());
                    return params;
                }

//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    HashMap<String, String> headers = new HashMap<String, String>();
//                    headers.put("Content-Type", "application/json");
//                    headers.put("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IkNTVDAwMDI1ODAwIiwic3RhdHVzIjoiYWN0aXZlIiwiaWF0IjoxNDc0MzUxOTIzLCJpc3MiOiJDU1QwMDAyNTgwMCJ9.MwoO3QDU7QiIkK4boK9IhiS4Jb4JKMdBYiNbsNA2A50");
//                    Log.e("onResponse",headers.toString());
//                    return headers;
//                    //return super.getHeaders();
//                }


            };


            AppController.getInstance().addToRequestQueue(stringRequest, 30000);

        } else {

            Toast.makeText(getContext(), "تعذر الاتصال بالانترنت ", Toast.LENGTH_LONG).show();
//            loader_dialog.hideDialog();
            pDialog.dismiss();
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////

}
