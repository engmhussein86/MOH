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
import com.moh.app.adapters.CalendarAdapter;
import com.moh.app.models.ShiftClass;
import com.moh.app.moh.R;
import com.moh.app.utils.AppController;
import com.moh.app.utils.ExpandableHeightGridView;
import com.moh.app.utils.ToolsUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShiftFragment extends Fragment {

    //    private LoadingDialog loader_dialog;
    ProgressDialog pDialog;
    TextView USER_FULL_NAME, USER_NO;
    Spinner month_dropdown, year_dropdown;
    ArrayList<ShiftClass> ShiftArray = new ArrayList<>();
    ImageView search_btn;

    ExpandableHeightGridView gridview;
    private CalendarAdapter cal_adapter;


    public ShiftFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shift, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DrawerActivity.navItemIndex = 11;
        ((DrawerActivity) getActivity()).setToolbarTitle();

//        loader_dialog = AppController.getLoadingDialog(getContext(), "");
        USER_FULL_NAME = (TextView) view.findViewById(R.id.USER_FULL_NAME);
        USER_NO = (TextView) view.findViewById(R.id.USER_NO);

        USER_FULL_NAME.setText(Fragment_Login.sharedPref.getString("USER_FULL_NAME", null));


        int month = Calendar.getInstance().get(Calendar.MONTH);
        Log.e("Error", "" + (month + 1));
        int year = Calendar.getInstance().get(Calendar.YEAR);
        Log.e("Error", "" + year);

        gridview = (ExpandableHeightGridView) view.findViewById(R.id.gv_calendar);

        month_dropdown = (Spinner) view.findViewById(R.id.month);
        Integer[] month_items = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        ArrayAdapter<Integer> month_adapter = new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_spinner_dropdown_item, month_items);
        month_dropdown.setAdapter(month_adapter);
        month_dropdown.setSelection(month);

        year_dropdown = (Spinner) view.findViewById(R.id.year);
        Integer[] year_items = new Integer[]{2016, 2017, 2018, 2019, 2020, 2021};
        ArrayAdapter<Integer> year_adapter = new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_spinner_dropdown_item, year_items);
        year_dropdown.setAdapter(year_adapter);

        int spinnerPosition = year_adapter.getPosition(year);
        Log.e("spinnerPosition", "" + spinnerPosition);
        year_dropdown.setSelection(spinnerPosition);

        getShifts();

        search_btn = (ImageView) view.findViewById(R.id.search_btn);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
                getShifts();

            }
        });


    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void clear() {
        ShiftArray.clear();
        cal_adapter.notifyDataSetChanged();

    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    public void getShifts() {

        final int selected_month = Integer.parseInt(month_dropdown.getItemAtPosition(month_dropdown.getSelectedItemPosition()).toString());
        final int selected_year = Integer.parseInt(year_dropdown.getItemAtPosition(year_dropdown.getSelectedItemPosition()).toString());

        gridview.setExpanded(true);

        cal_adapter = new CalendarAdapter(getContext(), selected_month, selected_year, ShiftArray);
        gridview.setAdapter(cal_adapter);

//        loader_dialog.setMsg("جاري تحميل الورديات");
//        loader_dialog.setCanceledOnTouchOutside(true);
//        loader_dialog.showDialog();
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("جاري تحميل الورديات");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        if (ToolsUtils.isConnectingToInternet(getContext())) {

            StringRequest shiftRequest = new StringRequest(Request.Method.POST, AppController.SHIFT_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("response", response);
                    if (response != null) {
                        try {
                            JSONObject jobj = new JSONObject(response);
                            JSONArray jArr = jobj.getJSONArray("result");

                            if (jArr.length() > 0) {

                                USER_NO.setText(jArr.getJSONObject(0).getString("TB_EMPBASICINFO_NO").toString(), null);

                                for (int i = 0; i < jArr.length(); i++) {
                                    //Log.e("TB_EMPBASICINFO_NO", jArr.getJSONObject(i).getString("TB_EMPBASICINFO_NO").toString());

                                    String TB_EMPBASICINFO_NO = jArr.getJSONObject(i).getString("TB_EMPBASICINFO_NO").toString();
                                    String EMPSHIFT_SHIFTDATE = jArr.getJSONObject(i).getString("TB_EMPSHIFT_SHIFTDATE").toString();
                                    String SHIFT_NAME = jArr.getJSONObject(i).getString("TB_SHIFT_NAME").toString();
                                    String SHIFT_START = jArr.getJSONObject(i).getString("TB_SHIFT_START").toString();
                                    String SHIFT_END = jArr.getJSONObject(i).getString("TB_SHIFT_END").toString();
                                    String NOTE = jArr.getJSONObject(i).getString("NOTE").toString();
                                    String SHIFT_TYPE_NAME = jArr.getJSONObject(i).getString("TB_SHIFT_TYPE_NAME").toString();
                                    int SHIFT_NO = jArr.getJSONObject(i).getInt("TB_SHIFT_NO");
                                    int SHIFT_TYPE_FK = jArr.getJSONObject(i).getInt("SHIFT_TYPE_FK");


                                    ShiftClass shift_record = new ShiftClass(TB_EMPBASICINFO_NO, EMPSHIFT_SHIFTDATE, SHIFT_NAME, SHIFT_START, SHIFT_END, NOTE, SHIFT_TYPE_NAME, SHIFT_NO, SHIFT_TYPE_FK);
                                    ShiftArray.add(shift_record);

                                }
                            }
//                            loader_dialog.hideDialog();

                            pDialog.dismiss();

                            Log.e("size", ShiftArray.size() + "");


                            gridview.setAdapter(cal_adapter);
                            cal_adapter.notifyDataSetChanged();


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
                    params.put("month", "" + selected_month);
                    params.put("year", "" + selected_year);

                    Log.e("params", params.toString());
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

            AppController.getInstance().addToRequestQueue(shiftRequest, 30000);
        } else {

            Toast.makeText(getContext(), "تعذر الاتصال بالانترنت ", Toast.LENGTH_LONG).show();
//            loader_dialog.hideDialog();

            pDialog.dismiss();

        }

    }


}
