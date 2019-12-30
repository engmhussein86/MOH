package com.moh.app.schedule;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.moh.app.DrawerActivity;
import com.moh.app.adapters.ClinicAdapter;
import com.moh.app.adapters.HosAdapter;
import com.moh.app.adapters.HosScheduleAdapter;
import com.moh.app.models.HosScheduleClass;
import com.moh.app.moh.R;
import com.moh.app.utils.AppController;
import com.moh.app.utils.Common;
import com.moh.app.utils.ToolsUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HospitalScheduleFrag extends Fragment {
    Spinner hospital, clinic;
    RecyclerView recyclerView;
    ArrayList<HosScheduleClass> schedule = new ArrayList<>();
    HosScheduleAdapter hosScheduleAdapter;
    ImageView search_btn;
    ProgressDialog pDialog;


    public HospitalScheduleFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hospital_schedule, container, false);

        DrawerActivity.navItemIndex = 17;
        ((DrawerActivity) getActivity()).setToolbarTitle();

        search_btn = (ImageView) view.findViewById(R.id.search_btn);
        hosScheduleAdapter = new HosScheduleAdapter(getContext(), schedule);

        recyclerView = (RecyclerView) view.findViewById(R.id.schedule_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(hosScheduleAdapter);
        hosScheduleAdapter.notifyDataSetChanged();

        hospital = (Spinner) view.findViewById(R.id.hospital);
        clinic = (Spinner) view.findViewById(R.id.clinic);


        Common.GET_ALL_HOSPITAL_PR(new Common.CallBackHash() {
            @Override
            public void onSuccess(ArrayList<HashMap<String, String>> array) {
                Log.e("onSuccess", array.toString());
                HosAdapter hospital_adapter = new HosAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, array);
                hospital.setAdapter(hospital_adapter);
                hospital_adapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(String msg) {
                Log.e("on fail", "456");

            }
        }, getContext());

        hospital.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                HashMap<String, String> hos = (HashMap) hospital.getItemAtPosition(i);
                String H_CODE = hos.get("H_CODE").toString();

                Toast.makeText(getContext(),
                        "MariamOnItemSelectedListener : " + H_CODE,
                        Toast.LENGTH_SHORT).show();

                Common.GET_ALL_CLASS_PR_BY_HOS(new Common.CallBackHash() {
                    @Override
                    public void onSuccess(ArrayList<HashMap<String, String>> array) {
                        Log.e("onSuccess", array.toString());
                        ClinicAdapter clinic_adapter = new ClinicAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, array);
                        clinic.setAdapter(clinic_adapter);
                        clinic_adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFail(String msg) {
                        Log.e("on fail", "456");

                    }
                }, getContext(), H_CODE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();

                HashMap<String, String> hos_selected = (HashMap) hospital.getItemAtPosition(hospital.getSelectedItemPosition());
                String H_CODE = hos_selected.get("H_CODE").toString();

                HashMap<String, String> clinic_selected = (HashMap) clinic.getItemAtPosition(clinic.getSelectedItemPosition());
                String CS_CODE = clinic_selected.get("CS_CODE").toString();


                CLINIC_SCHEDULE_DATA(getContext(), H_CODE, CS_CODE);


            }
        });


        return view;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void clear() {
        schedule.clear();
        hosScheduleAdapter.notifyDataSetChanged();

    }

    ///////////////////////////////////////////////////////////////////////////////////////////


    /////////////////////////////////////////////////////////////////////////

    public void CLINIC_SCHEDULE_DATA(final Context context, final String H_CODE, final String CS_CODE) {
        schedule.clear();

        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("جاري تحميل جدول العيادات الخارجية");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        if (ToolsUtils.isConnectingToInternet(context)) {

            Log.e("Mariam_response", "ddd");


            StringRequest HospitalRequest = new StringRequest(Request.Method.GET, AppController.CLINIC_SCHEDULE_DATA + "&P_HOS_NO=" + H_CODE + "&P_CLASS_NO=" + CS_CODE, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("Mariam_response", response);
                    if (response != null) {
                        try {
                            //JSONObject jobj = new JSONObject(response);
                            JSONArray jArr = new JSONArray(response);

                            Log.e("Mariam_response", "" + jArr.length());
                            if (jArr.length() > 0) {

                                for (int i = 0; i < jArr.length(); i++) {
                                    //Log.e("Error",jArr.getJSONObject(i).getString("LOC_SID").toString());
                                    HashMap<String, String> map = new HashMap<>();

                                    String LOC_NAME_AR = jArr.getJSONObject(i).getString("LOC_NAME_AR");
                                    String FULL_NAME = jArr.getJSONObject(i).getString("FULL_NAME");
                                    String CPT_NAME_AR = jArr.getJSONObject(i).getString("CPT_NAME_AR");

                                    HosScheduleClass schedule_record = new HosScheduleClass(LOC_NAME_AR, FULL_NAME, CPT_NAME_AR);
                                    schedule.add(schedule_record);

                                }
                            }

                            pDialog.dismiss();

                            Log.e("Mariam_size", schedule.size() + "");


                            recyclerView.setAdapter(hosScheduleAdapter);
                            hosScheduleAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Mariam_onErrorResponse", "error : " + error.getMessage());
                    pDialog.dismiss();
                    Toast.makeText(context, "حدث خلل في الاتصال  ", Toast.LENGTH_LONG).show();

                }
            });

            AppController.getInstance().addToRequestQueue(HospitalRequest, 30000);
        } else {

            Toast.makeText(context, "تعذر الاتصال بالانترنت ", Toast.LENGTH_LONG).show();
            pDialog.dismiss();

        }


    }


    ////////////////////////////////////////////////////////////////////////

}
