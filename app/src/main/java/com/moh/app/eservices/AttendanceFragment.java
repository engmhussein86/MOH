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
import com.moh.app.adapters.AttendanceAdapter;
import com.moh.app.models.AttendanceClass;
import com.moh.app.moh.R;
import com.moh.app.utils.AppController;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttendanceFragment extends Fragment {

    //    private LoadingDialog loader_dialog;
    RecyclerView recyclerView;
    TextView USER_FULL_NAME, USER_NO;
    Spinner month_dropdown, year_dropdown;
    ArrayList<AttendanceClass> AttendanceArray = new ArrayList<>();
    AttendanceAdapter attnAdapter;
    ImageView search_btn;
    ProgressDialog pDialog;


    public AttendanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attendance, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DrawerActivity.navItemIndex = 8;
        ((DrawerActivity) getActivity()).setToolbarTitle();


//        loader_dialog = AppController.getLoadingDialog(getContext(), "");
        USER_FULL_NAME = (TextView) view.findViewById(R.id.USER_FULL_NAME);
        USER_NO = (TextView) view.findViewById(R.id.USER_NO);

        attnAdapter = new AttendanceAdapter(getContext(), AttendanceArray);

        recyclerView = (RecyclerView) view.findViewById(R.id.attendance_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(attnAdapter);
        attnAdapter.notifyDataSetChanged();

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


        getAttendance();

        search_btn = (ImageView) view.findViewById(R.id.search_btn);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
                getAttendance();

            }
        });

    }

    /////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void clear() {
        AttendanceArray.clear();
        attnAdapter.notifyDataSetChanged();

    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    public void getAttendance() {

        final int selected_month = Integer.parseInt(month_dropdown.getItemAtPosition(month_dropdown.getSelectedItemPosition()).toString());
        final int selected_year = Integer.parseInt(year_dropdown.getItemAtPosition(year_dropdown.getSelectedItemPosition()).toString());

        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("جاري تحميل الدوام");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        if (ToolsUtils.isConnectingToInternet(getContext())) {

            StringRequest attnRequest = new StringRequest(Request.Method.POST, AppController.ATTENDANCE_URL, new Response.Listener<String>() {
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
                                    Log.e("TB_EMPBASICINFO_NO", jArr.getJSONObject(i).getString("TB_EMPBASICINFO_NO").toString());

                                    String TB_EMPBASICINFO_NO = jArr.getJSONObject(i).getString("TB_EMPBASICINFO_NO").toString();
                                    String ATTENDDAT = jArr.getJSONObject(i).getString("TB_EMPATTENDANCEINFO_ATTENDDAT").toString();
                                    String ATTENDTIM = jArr.getJSONObject(i).getString("ATTENDTIM").toString();
                                    String ARRTIME = jArr.getJSONObject(i).getString("ARRTIME").toString();
                                    String ARRIDATE = jArr.getJSONObject(i).getString("TB_EMPATTENDANCEINFO_ARRIDATE").toString();
                                    String ATTDAY = jArr.getJSONObject(i).getString("ATTDAY").toString();
                                    String ATTENDSTA = jArr.getJSONObject(i).getString("TB_EMPATTENDANCEINFO_ATTENDSTA").toString();
                                    int ATTENDTYP = jArr.getJSONObject(i).getInt("TB_EMPATTENDANCEINFO_ATTENDTYP");


                                    AttendanceClass attn_record = new AttendanceClass(TB_EMPBASICINFO_NO, ATTENDDAT, ATTENDTIM, ARRTIME, ARRIDATE, ATTDAY, ATTENDSTA, ATTENDTYP);
                                    AttendanceArray.add(attn_record);

                                }
                            }
//                            loader_dialog.hideDialog();
                            pDialog.dismiss();

                            Log.e("size", AttendanceArray.size() + "");


                            recyclerView.setAdapter(attnAdapter);
                            attnAdapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("onErrorResponse", "error : " + error.getMessage());
                    pDialog.dismiss();
//                    loader_dialog.hideDialog();
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

            AppController.getInstance().addToRequestQueue(attnRequest, 30000);
        } else {

            Toast.makeText(getContext(), "تعذر الاتصال بالانترنت ", Toast.LENGTH_LONG).show();
            pDialog.dismiss();
//            loader_dialog.hideDialog();

        }

    }


}
