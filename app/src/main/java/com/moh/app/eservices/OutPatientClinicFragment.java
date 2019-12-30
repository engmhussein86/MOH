package com.moh.app.eservices;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.moh.app.DrawerActivity;
import com.moh.app.Fragment_Login;
import com.moh.app.adapters.HinsAdapter_Spinner;
import com.moh.app.adapters.OutPatientClinicAdapter;
import com.moh.app.models.HinsUser;
import com.moh.app.models.OutPatientClinicClass;
import com.moh.app.moh.R;
import com.moh.app.utils.AppController;
import com.moh.app.utils.CallBack;
import com.moh.app.utils.Common;
import com.moh.app.utils.ToolsUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class OutPatientClinicFragment extends Fragment {

    //    private LoadingDialog loader_dialog;
    RecyclerView recyclerView;
    TextView USER_FULL_NAME;
    ImageView operation_search_btn;
    ArrayList<OutPatientClinicClass> OutPatientClinicArray = new ArrayList<>();
    OutPatientClinicAdapter outPatientClinicAdapter;
    Spinner hins_spinner;
    HinsAdapter_Spinner hinsAdapter_spinner;

    ProgressDialog pDialog;


    public OutPatientClinicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_out_patient_clinic, container, false);

        DrawerActivity.navItemIndex = 15;
        ((DrawerActivity) getActivity()).setToolbarTitle();


//        loader_dialog = AppController.getLoadingDialog(getContext(), "");
        USER_FULL_NAME = (TextView) view.findViewById(R.id.USER_FULL_NAME);

        operation_search_btn = (ImageView) view.findViewById(R.id.outClinic_search_btn);


        outPatientClinicAdapter = new OutPatientClinicAdapter(getContext(), OutPatientClinicArray);

        recyclerView = (RecyclerView) view.findViewById(R.id.outClinic_list);
        hins_spinner = (Spinner) view.findViewById(R.id.hins_spinner);

        Common.HinsData(new CallBack() {
            @Override
            public void onSuccess(ArrayList<HinsUser> hinsArray) {
                Log.e("onSuccess", hinsArray.toString());
                hinsAdapter_spinner = new HinsAdapter_Spinner(getContext(), 0, hinsArray);
                hins_spinner.setAdapter(hinsAdapter_spinner);
                hinsAdapter_spinner.notifyDataSetChanged();
            }

            @Override
            public void onFail(String msg) {
                Log.e("on fail", "456");

            }
        }, getContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setReverseLayout(true);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(outPatientClinicAdapter);
        outPatientClinicAdapter.notifyDataSetChanged();

        USER_FULL_NAME.setText(Fragment_Login.sharedPref.getString("USER_FULL_NAME", null));

        getVisits(Fragment_Login.sharedPref.getString("USER_ID", null));


        operation_search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();

                HinsUser hinsUser_record = (HinsUser) hins_spinner.getItemAtPosition(hins_spinner.getSelectedItemPosition());
                String ID_NO = hinsUser_record.getID_NO();


                getVisits(ID_NO);


            }
        });


        return view;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void clear() {
        OutPatientClinicArray.clear();
        outPatientClinicAdapter.notifyDataSetChanged();

    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    public void getVisits(final String ID_NO) {
        OutPatientClinicArray.clear();

        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("جاري تحميل مواعيد العيادات الخارجية");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();


//        loader_dialog.setMsg("جاري تحميل مواعيد العيادات الخارجية");
//        loader_dialog.setCanceledOnTouchOutside(true);
//        loader_dialog.showDialog();

        if (ToolsUtils.isConnectingToInternet(getContext())) {

            StringRequest OperationRequest = new StringRequest(Request.Method.POST, AppController.OUTPATIENT_CLINIC_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("response", response);
                    if (response != null) {
                        try {

                            JSONObject jobj = new JSONObject(response);
                            JSONArray jArr = jobj.getJSONArray("result");

                            Log.e("error", jArr.length() + "");

                            if (jArr.length() > 0) {

                                for (int i = 0; i < jArr.length(); i++) {
                                    JSONObject e = jArr.getJSONObject(i);
                                    Log.e("error", e.getString("MR_FULL_NAME_AR").toString());

                                    String MRP_ID = e.getString("MRP_ID").toString();
                                    Log.e("error", e.getString("MRP_ID").toString());

                                    String VISIT_TIME = e.getString("VISIT_TIME").toString();
                                    Log.e("error", e.getString("VISIT_TIME").toString());

                                    String MRP_DOB = e.getString("MRP_DOB").toString();
                                    Log.e("error", e.getString("MRP_DOB").toString());

                                    String MR_FULL_NAME_AR = e.getString("MR_FULL_NAME_AR").toString();
                                    Log.e("error", e.getString("MR_FULL_NAME_AR").toString());

                                    String H_NAME_AR = e.getString("H_NAME_AR").toString();
                                    Log.e("error", e.getString("H_NAME_AR").toString());

                                    String LOC_NAME_AR = e.getString("LOC_NAME_AR").toString();
                                    Log.e("error", e.getString("LOC_NAME_AR").toString());

                                    String VISITTP_NAME_AR = e.getString("VISITTP_NAME_AR").toString();
                                    Log.e("error", e.getString("VISITTP_NAME_AR").toString());

                                    String DOC_FULL_NAME_AR = e.getString("DOC_FULL_NAME_AR").toString();
                                    Log.e("error", e.getString("DOC_FULL_NAME_AR").toString());

                                    OutPatientClinicClass opc_record = new OutPatientClinicClass(MRP_ID, MR_FULL_NAME_AR, MRP_DOB, VISIT_TIME, H_NAME_AR, LOC_NAME_AR, VISITTP_NAME_AR, DOC_FULL_NAME_AR);

                                    OutPatientClinicArray.add(opc_record);

                                }
                            }
//                            loader_dialog.hideDialog();
                            pDialog.dismiss();
                            Log.e("size", OutPatientClinicArray.size() + "");


                            recyclerView.setAdapter(outPatientClinicAdapter);
                            outPatientClinicAdapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("onErrorResponse", "error : " + error.getMessage());
//                    loader_dialog.hideDialog();
                    pDialog.dismiss();
                    Toast.makeText(getContext(), "حدث خلل في الاتصال  ", Toast.LENGTH_LONG).show();

                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
//                    params.put("mrp_id", "433279759");
                    params.put("mrp_id", ID_NO);
                    Log.e("Error", params.toString());
                    return params;
                }

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

            AppController.getInstance().addToRequestQueue(OperationRequest, 300000000);
        } else {

            Toast.makeText(getContext(), "تعذر الاتصال بالانترنت ", Toast.LENGTH_LONG).show();
//            loader_dialog.hideDialog();
            pDialog.dismiss();

        }

    }


}
