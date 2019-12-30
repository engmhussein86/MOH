package com.moh.app.eservices;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import com.moh.app.adapters.LabOrderAdapter;
import com.moh.app.models.HinsUser;
import com.moh.app.models.LabOrderClass;
import com.moh.app.moh.R;
import com.moh.app.utils.AppController;
import com.moh.app.utils.CallBack;
import com.moh.app.utils.Common;
import com.moh.app.utils.ToolsUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class LabOrderFragment extends Fragment {
    RecyclerView recyclerView;
    TextView USER_FULL_NAME;
    ImageView lab_search_btn;
    ArrayList<LabOrderClass> labOrderArray = new ArrayList<>();
    LabOrderAdapter labOrderAdapter;
    Spinner hins_spinner;
    HinsAdapter_Spinner hinsAdapter_spinner;
    Spinner month_dropdown, year_dropdown;

    ProgressDialog pDialog;


    public LabOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lab_order, container, false);

        DrawerActivity.navItemIndex = 16;
        ((DrawerActivity) getActivity()).setToolbarTitle();

        USER_FULL_NAME = (TextView) view.findViewById(R.id.USER_FULL_NAME);

        lab_search_btn = (ImageView) view.findViewById(R.id.lab_search_btn);

        labOrderAdapter = new LabOrderAdapter(getContext(), labOrderArray);

        recyclerView = (RecyclerView) view.findViewById(R.id.laborder_list);
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
        recyclerView.setAdapter(labOrderAdapter);
        labOrderAdapter.notifyDataSetChanged();

        USER_FULL_NAME.setText(Fragment_Login.sharedPref.getString("USER_FULL_NAME", null));

        int month = Calendar.getInstance().get(Calendar.MONTH);
        Log.e("Error", "" + (month + 1));
        int year = Calendar.getInstance().get(Calendar.YEAR);
        Log.e("Error", "" + year);


        month_dropdown = (Spinner) view.findViewById(R.id.month);
        Integer[] month_items = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        ArrayAdapter<Integer> month_adapter = new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_spinner_dropdown_item, month_items);
        month_dropdown.setAdapter(month_adapter);
        month_dropdown.setSelection(month);

        year_dropdown = (Spinner) view.findViewById(R.id.year);
        Integer[] year_items = new Integer[]{year - 2, year - 1, year};
        ArrayAdapter<Integer> year_adapter = new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_spinner_dropdown_item, year_items);
        year_dropdown.setAdapter(year_adapter);

        int spinnerPosition = year_adapter.getPosition(year);
        Log.e("spinnerPosition", "" + spinnerPosition);
        year_dropdown.setSelection(spinnerPosition);

        final int selected_month = Integer.parseInt(month_dropdown.getItemAtPosition(month_dropdown.getSelectedItemPosition()).toString());
        final int selected_year = Integer.parseInt(year_dropdown.getItemAtPosition(year_dropdown.getSelectedItemPosition()).toString());

        try {
            getLabOreder("801269960", 4, 2018);
//            getLabOreder(Fragment_Login.sharedPref.getString("USER_ID", null), selected_month,selected_year);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        lab_search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();

                HinsUser hinsUser_record = (HinsUser) hins_spinner.getItemAtPosition(hins_spinner.getSelectedItemPosition());
                String ID_NO = hinsUser_record.getID_NO();


                final int selected_month = Integer.parseInt(month_dropdown.getItemAtPosition(month_dropdown.getSelectedItemPosition()).toString());
                final int selected_year = Integer.parseInt(year_dropdown.getItemAtPosition(year_dropdown.getSelectedItemPosition()).toString());

                try {
                    getLabOreder(ID_NO, selected_month, selected_year);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        });

        return view;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void clear() {
        labOrderArray.clear();
        labOrderAdapter.notifyDataSetChanged();

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    public void getLabOreder(final String ID_NO, int month, int year) throws ParseException {
        labOrderArray.clear();

        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("جاري تحميل التحاليل المخبرية..");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date fdate = sdf.parse("1/" + month + "/" + year);
        final String first_date = sdf.format(fdate);


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fdate);

        Log.e("MFirst Day Of Month : ", "" + calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Log.e("MLast Day of Month  : ", "" + calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        Date ldate = sdf.parse(calendar.getActualMaximum(Calendar.DAY_OF_MONTH) + "/" + month + "/" + year);
        final String last_date = sdf.format(ldate);
        Log.e("Mariam_first Day:", "" + first_date);
        Log.e("Mariam_Last Day:", last_date);


        if (ToolsUtils.isConnectingToInternet(getContext())) {

            StringRequest OperationRequest = new StringRequest(Request.Method.POST, AppController.LABORDER_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("Mariam_response", response);
                    if (response != null) {
                        try {

                            JSONObject jobj = new JSONObject(response);
                            JSONArray jArr = jobj.getJSONArray("result");

                            Log.e("error", jArr.length() + "");

                            if (jArr.length() > 0) {

                                for (int i = 0; i < jArr.length(); i++) {
                                    JSONObject e = jArr.getJSONObject(i);

                                    String ORDER_REQUEST_DATE = e.getString("ORDER_REQUEST_DATE").toString();
                                    Log.e("error", e.getString("ORDER_REQUEST_DATE").toString());

                                    String CATEGORY_NAME_AR = e.getString("CATEGORY_NAME_AR").toString();
                                    Log.e("error", e.getString("CATEGORY_NAME_AR").toString());

                                    String ORDER_STATUS_NAME_AR = e.getString("ORDER_STATUS_NAME_AR").toString();
                                    Log.e("error", e.getString("ORDER_STATUS_NAME_AR").toString());

                                    String H_NAME_AR = e.getString("H_NAME_AR").toString();
                                    Log.e("error", e.getString("H_NAME_AR").toString());

                                    int ORDER_CD = Integer.parseInt(e.getString("ORDER_CD").toString());

                                    int D_DETAILS_CD = Integer.parseInt(e.getString("D_DETAILS_CD").toString());

                                    LabOrderClass lo_record = new LabOrderClass(ORDER_REQUEST_DATE, CATEGORY_NAME_AR, ORDER_STATUS_NAME_AR, H_NAME_AR, ORDER_CD, D_DETAILS_CD);

                                    labOrderArray.add(lo_record);

                                }
                            }
//                            loader_dialog.hideDialog();
                            pDialog.dismiss();
                            Log.e("size", labOrderArray.size() + "");


                            recyclerView.setAdapter(labOrderAdapter);
                            labOrderAdapter.notifyDataSetChanged();


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
                    params.put("from_date", first_date);
                    params.put("to_date", last_date);

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
