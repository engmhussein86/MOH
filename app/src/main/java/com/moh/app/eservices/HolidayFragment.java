package com.moh.app.eservices;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.moh.app.DrawerActivity;
import com.moh.app.Fragment_Login;
import com.moh.app.adapters.BackHolidayAdapter;
import com.moh.app.adapters.ViewHolidayAdapter;
import com.moh.app.adapters.WaitHolidayAdapter;
import com.moh.app.models.HolidayClass;
import com.moh.app.moh.R;
import com.moh.app.utils.AppController;
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
import java.util.Locale;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HolidayFragment extends Fragment {

    //    private LoadingDialog loader_dialog;
    ProgressDialog pDialog;
    TextView USER_FULL_NAME;
    //////////////////////////////////////////////////////////////////////////////////////
    LinearLayout request_linear, waiting_linear, view_linear, back_linear;
    TextView request_tab, waiting_tab, view_tab, back_tab;
    /////////////////////////////////////////////////////////////////////////////////////////
    RecyclerView recyclerView;
    RecyclerView viewHoliday_recyclerView;
    RecyclerView backHoliday_recyclerView;
    ArrayList<HolidayClass> waitHolidayArray = new ArrayList<>();
    ArrayList<HolidayClass> viewHolidayArray = new ArrayList<>();
    ArrayList<HolidayClass> backHolidayArray = new ArrayList<>();
    WaitHolidayAdapter waitAdapter;
    ViewHolidayAdapter viewHolidayAdapter;
    BackHolidayAdapter backHolidayAdapter;
    String HNO, TNO, EM;
    private EditText EMPHOLIDAYS_STARTDATE, EMPHOLIDAYS_ENDDATE, EMPHOLIDAYS_HCOUNT, FROMDATE, TODATE;
    private CheckBox FRIDAY, SATURDAY;
    private Button addBtn;
    private ImageView search_btn;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;


    /////////////////////////////////////////////////////////////////////////////////////////


    public HolidayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_holiday, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DrawerActivity.navItemIndex = 10;
        ((DrawerActivity) getActivity()).setToolbarTitle();

        request_tab = (TextView) view.findViewById(R.id.request_tab);
        waiting_tab = (TextView) view.findViewById(R.id.waiting_tab);
        view_tab = (TextView) view.findViewById(R.id.view_tab);
        back_tab = (TextView) view.findViewById(R.id.back_tab);

        request_linear = (LinearLayout) view.findViewById(R.id.request_linear);
        waiting_linear = (LinearLayout) view.findViewById(R.id.waiting_linear);
        view_linear = (LinearLayout) view.findViewById(R.id.view_linear);
        back_linear = (LinearLayout) view.findViewById(R.id.back_linear);


        recyclerView = (RecyclerView) view.findViewById(R.id.wait_holiday_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewHoliday_recyclerView = (RecyclerView) view.findViewById(R.id.view_holiday_list);
        viewHoliday_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        backHoliday_recyclerView = (RecyclerView) view.findViewById(R.id.back_holiday_list);
        backHoliday_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        loader_dialog = AppController.getLoadingDialog(getContext(), "");
        USER_FULL_NAME = (TextView) view.findViewById(R.id.USER_FULL_NAME);
        USER_FULL_NAME.setText(Fragment_Login.sharedPref.getString("USER_FULL_NAME", null));

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        EMPHOLIDAYS_HCOUNT = (EditText) view.findViewById(R.id.EMPHOLIDAYS_HCOUNT);
        EMPHOLIDAYS_STARTDATE = (EditText) view.findViewById(R.id.EMPHOLIDAYS_STARTDATE);
        EMPHOLIDAYS_ENDDATE = (EditText) view.findViewById(R.id.EMPHOLIDAYS_ENDDATE);
        FROMDATE = (EditText) view.findViewById(R.id.FROMDATE);
        TODATE = (EditText) view.findViewById(R.id.TODATE);

        EMPHOLIDAYS_STARTDATE.setInputType(InputType.TYPE_NULL);
        EMPHOLIDAYS_STARTDATE.requestFocus();

        FRIDAY = (CheckBox) view.findViewById(R.id.FRIDAY);
        SATURDAY = (CheckBox) view.findViewById(R.id.SATURDAY);

        addBtn = (Button) view.findViewById(R.id.addBtn);
        search_btn = (ImageView) view.findViewById(R.id.search_btn);

        FloatingActionButton floatingActionButton =
                (FloatingActionButton) view.findViewById(R.id.info_fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holiday_count_dialog(getContext(), Fragment_Login.sharedPref.getString("USER_ID", null));
            }
        });


        holiday_request();
        /////////////////////////////////////////////////////////////////////////////////////////////
        request_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request_linear.setVisibility(View.VISIBLE);
                waiting_linear.setVisibility(View.GONE);
                view_linear.setVisibility(View.GONE);
                back_linear.setVisibility(View.GONE);

                request_tab.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                waiting_tab.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.divider));
                view_tab.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.divider));
                back_tab.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.divider));

                request_tab.setTextColor(ContextCompat.getColor(getContext(), R.color.icons));
                waiting_tab.setTextColor(ContextCompat.getColor(getContext(), R.color.primary_text));
                view_tab.setTextColor(ContextCompat.getColor(getContext(), R.color.primary_text));
                back_tab.setTextColor(ContextCompat.getColor(getContext(), R.color.primary_text));

                holiday_request();
            }
        });
        //////////////////////////////////////////////////////////////////////////////////////////////
        waiting_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request_linear.setVisibility(View.GONE);
                waiting_linear.setVisibility(View.VISIBLE);
                view_linear.setVisibility(View.GONE);
                back_linear.setVisibility(View.GONE);

                request_tab.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.divider));
                waiting_tab.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                view_tab.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.divider));
                back_tab.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.divider));

                request_tab.setTextColor(ContextCompat.getColor(getContext(), R.color.primary_text));
                waiting_tab.setTextColor(ContextCompat.getColor(getContext(), R.color.icons));
                view_tab.setTextColor(ContextCompat.getColor(getContext(), R.color.primary_text));
                back_tab.setTextColor(ContextCompat.getColor(getContext(), R.color.primary_text));

                viewWaitHoliday();


            }
        });
        //////////////////////////////////////////////////////////////////////////////////////////////////
        view_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request_linear.setVisibility(View.GONE);
                waiting_linear.setVisibility(View.GONE);
                view_linear.setVisibility(View.VISIBLE);
                back_linear.setVisibility(View.GONE);

                request_tab.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.divider));
                waiting_tab.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.divider));
                view_tab.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                back_tab.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.divider));

                request_tab.setTextColor(ContextCompat.getColor(getContext(), R.color.primary_text));
                waiting_tab.setTextColor(ContextCompat.getColor(getContext(), R.color.primary_text));
                view_tab.setTextColor(ContextCompat.getColor(getContext(), R.color.icons));
                back_tab.setTextColor(ContextCompat.getColor(getContext(), R.color.primary_text));

                viewHoliday();

            }
        });

        //////////////////////////////////////////////////////////////////////////////////////////////////
        back_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request_linear.setVisibility(View.GONE);
                waiting_linear.setVisibility(View.GONE);
                view_linear.setVisibility(View.GONE);
                back_linear.setVisibility(View.VISIBLE);

                request_tab.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.divider));
                waiting_tab.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.divider));
                view_tab.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.divider));
                back_tab.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));

                request_tab.setTextColor(ContextCompat.getColor(getContext(), R.color.primary_text));
                waiting_tab.setTextColor(ContextCompat.getColor(getContext(), R.color.primary_text));
                view_tab.setTextColor(ContextCompat.getColor(getContext(), R.color.primary_text));
                back_tab.setTextColor(ContextCompat.getColor(getContext(), R.color.icons));

                viewBackHoliday();

            }
        });


    }

    public void holiday_count_dialog(final Context context, final String user_id) {
        final String[] HNO = new String[1];
        final String[] TNO = new String[1];
        final String[] EM = new String[1];


        if (ToolsUtils.isConnectingToInternet(context)) {


            StringRequest holidayCountRequest = new StringRequest(Request.Method.POST, AppController.EMP_HOLIDAYS_COUNT_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("response", response);
                    if (response != null) {
                        try {
                            JSONObject jobj = new JSONObject(response);
                            JSONArray jArr = jobj.getJSONArray("result");

                            if (jArr.length() > 0) {

                                for (int i = 0; i < jArr.length(); i++) {
                                    Log.e("TB_EMPBASICINFO_NO", jArr.getJSONObject(i).getString("CNO"));
                                    if (Integer.parseInt(jArr.getJSONObject(i).getString("CNO")) == 1) {
                                        HNO[0] = jArr.getJSONObject(i).getString("TB_EMPHOLIDAYSCOUNT_HNO");
                                        TNO[0] = jArr.getJSONObject(i).getString("TB_EMPHOLIDAYSCOUNT_TNO");
                                    }

                                    if (Integer.parseInt(jArr.getJSONObject(i).getString("CNO")) == 2) {
                                        EM[0] = jArr.getJSONObject(i).getString("TB_EMPHOLIDAYSCOUNT_HNO");
                                    }


                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        new MaterialAlertDialogBuilder(context)
                                .setTitle(context.getResources().getString(R.string.holiday_count))
                                .setMessage(Html.fromHtml("<b>" + context.getResources().getString(R.string.holiday_count_message, HNO[0], TNO[0], EM[0]) + "</b>"))
                                .setPositiveButton(context.getResources().getString(R.string.ok), null)
                                .show();

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("onErrorResponse", "error : " + error.getMessage());
                    Toast.makeText(context, "حدث خلل في الاتصال  ", Toast.LENGTH_LONG).show();

                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("year", "" + Calendar.getInstance().get(Calendar.YEAR));
                    params.put("user_id", "" + user_id);
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

            AppController.getInstance().addToRequestQueue(holidayCountRequest, 30000);
        } else {

            Toast.makeText(context, "تعذر الاتصال بالانترنت ", Toast.LENGTH_LONG).show();
            pDialog.dismiss();
//            loader_dialog.hideDialog();

        }


    }

//////////////////////////////////////////////////////////////////////////////////////////////////

    private void holiday_request() {

        FRIDAY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FRIDAY.isChecked()) {
                    SATURDAY.setEnabled(true);

                } else {
                    SATURDAY.setChecked(false);
                    SATURDAY.setEnabled(false);

                }

                if (EMPHOLIDAYS_STARTDATE.getText().toString().equalsIgnoreCase("") || EMPHOLIDAYS_HCOUNT.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getContext(), "الرجاء ادخال مدة الاجازة وتاريخ بداية الاجازة", Toast.LENGTH_LONG).show();

                } else {

                    Calendar newDate = Calendar.getInstance();
                    try {
                        Date date = dateFormatter.parse(EMPHOLIDAYS_STARTDATE.getText().toString());
                        newDate.setTime(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    calculateEndDate(newDate);
                }
            }
        });

        SATURDAY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EMPHOLIDAYS_STARTDATE.getText().toString().equalsIgnoreCase("") || EMPHOLIDAYS_HCOUNT.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getContext(), "الرجاء ادخال مدة الاجازة وتاريخ بداية الاجازة", Toast.LENGTH_LONG).show();

                } else {
                    Calendar newDate = Calendar.getInstance();
                    try {
                        Date date = dateFormatter.parse(EMPHOLIDAYS_STARTDATE.getText().toString());
                        newDate.setTime(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    calculateEndDate(newDate);
                }

            }
        });

        setDateTimeField();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (EMPHOLIDAYS_STARTDATE.getText().toString().equalsIgnoreCase("") || EMPHOLIDAYS_HCOUNT.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getContext(), "الرجاء ادخال جميع الحقول", Toast.LENGTH_LONG).show();

                } else if (Integer.parseInt(EMPHOLIDAYS_HCOUNT.getText().toString()) > 65) {
                    Toast.makeText(getContext(), "لا يمكن ادخال عدد الايام اكبر من 65", Toast.LENGTH_LONG).show();

                } else {

                    if (ToolsUtils.isConnectingToInternet(getContext())) {

                        StringRequest ss = new StringRequest(Request.Method.POST, AppController.INSERT_HOLIDAY_URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                try {

                                    if (response != null) {
                                        Log.e("response", response);
                                        JSONObject jobj = new JSONObject(response);

                                        Log.e("Status", jobj.getString("msg").toString());

                                        Toast.makeText(getContext(), jobj.getString("msg").toString(), Toast.LENGTH_LONG).show();

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
                                params.put("EMPHOLIDAYS_HCOUNT", EMPHOLIDAYS_HCOUNT.getText().toString());
                                params.put("EMPHOLIDAYS_STARTDATE", EMPHOLIDAYS_STARTDATE.getText().toString());
                                params.put("EMPHOLIDAYS_ENDDATE", EMPHOLIDAYS_ENDDATE.getText().toString());
                                params.put("FRIDAY", (FRIDAY.isChecked() ? 1 : 0) + "");
                                params.put("SATURDAY", (SATURDAY.isChecked() ? 1 : 0) + "");

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


                        AppController.getInstance().addToRequestQueue(ss, 30000);

                    } else {

                        Toast.makeText(getContext(), "تعذر الاتصال بالانترنت ", Toast.LENGTH_LONG).show();

                    }
                }


            }
        });

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private void setDateTimeField() {
        EMPHOLIDAYS_STARTDATE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (EMPHOLIDAYS_HCOUNT.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getContext(), "الرجاء ادخال مدة الاجازة ", Toast.LENGTH_LONG).show();

                } else {

                    if (v == EMPHOLIDAYS_STARTDATE) {
                        fromDatePickerDialog.show();

                    }
                }
            }
        });


        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(getContext(), AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                EMPHOLIDAYS_STARTDATE.setText(dateFormatter.format(newDate.getTime()));
                calculateEndDate(newDate);


            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void calculateEndDate(Calendar c) {
        Log.e("error", "end date");


        int HCOUNT = Integer.parseInt(EMPHOLIDAYS_HCOUNT.getText().toString());
        c.add(Calendar.DAY_OF_YEAR, HCOUNT);

        boolean friday = c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY;
        if (friday) {
            if (FRIDAY.isChecked() && SATURDAY.isChecked()) {
                c.add(Calendar.DAY_OF_YEAR, 2);
            } else if (FRIDAY.isChecked() && !SATURDAY.isChecked()) {
                c.add(Calendar.DAY_OF_YEAR, 1);
            }
        }

        EMPHOLIDAYS_ENDDATE.setText(dateFormatter.format(c.getTime()));
        EMPHOLIDAYS_ENDDATE.setEnabled(false);

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void viewWaitHoliday() {
        waitHolidayArray.clear();

        waitAdapter = new WaitHolidayAdapter(getContext(), waitHolidayArray);


        recyclerView.setAdapter(waitAdapter);
        waitAdapter.notifyDataSetChanged();

//    loader_dialog.setMsg("جاري تحميل الطلبات في الانتظار ..");
//    loader_dialog.setCanceledOnTouchOutside(true);
//    loader_dialog.showDialog();

        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("جاري تحميل الطلبات في الانتظار ..");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        if (ToolsUtils.isConnectingToInternet(getContext())) {

            StringRequest wait_holiday_request = new StringRequest(Request.Method.POST, AppController.WAIT_HOLIDAY_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("response", response);
                    if (response != null) {
                        try {
                            JSONObject jobj = new JSONObject(response);
                            JSONArray jArr = jobj.getJSONArray("result");

                            for (int i = 0; i < jArr.length(); i++) {
                                Log.e("TB_EMPBASICINFO_NO", jArr.getJSONObject(i).getString("TB_EMPBASICINFO_NO").toString());

                                Log.e("FULL_NAME_AR", jArr.getJSONObject(i).getString("TB_EMPBASICINFO_AFNAME").toString());
                                Log.e("TB__STARTDATE", jArr.getJSONObject(i).getString("TB_EMPHOLIDAYS_STARTDATE").toString());
                                Log.e("TB_EMPHOLIDAYS_HCOUNT", "" + jArr.getJSONObject(i).getInt("TB_EMPHOLIDAYS_HCOUNT"));

                                int TB_EMPHOLIDAYS_NO = jArr.getJSONObject(i).getInt("TB_EMPHOLIDAYS_NO");
                                String TB_EMPBASICINFO_NO = jArr.getJSONObject(i).getString("TB_EMPBASICINFO_NO").toString();
                                String FULL_NAME_AR = jArr.getJSONObject(i).getString("TB_EMPBASICINFO_AFNAME").toString() + " " + jArr.getJSONObject(i).getString("TB_EMPBASICINFO_ASNAME").toString() + " " + jArr.getJSONObject(i).getString("TB_EMPBASICINFO_ALNAME").toString();
                                int TB_EMPHOLIDAYS_HCOUNT = jArr.getJSONObject(i).getInt("TB_EMPHOLIDAYS_HCOUNT");
                                String TB_EMPHOLIDAYS_STARTDATE = jArr.getJSONObject(i).getString("TB_EMPHOLIDAYS_STARTDATE").toString();
                                String TB_EMPHOLIDAYS_ENDDATE = jArr.getJSONObject(i).getString("TB_EMPHOLIDAYS_ENDDATE").toString();

                                HolidayClass wait_holiday_record = new HolidayClass(TB_EMPHOLIDAYS_NO, TB_EMPBASICINFO_NO, FULL_NAME_AR, TB_EMPHOLIDAYS_HCOUNT, TB_EMPHOLIDAYS_STARTDATE, TB_EMPHOLIDAYS_ENDDATE, null, null);
                                wait_holiday_record.setTB_EMPBASICINFO_ID(jArr.getJSONObject(i).getString("TB_EMPBASICINFO_ID"));

                                waitHolidayArray.add(wait_holiday_record);

                            }
                            Log.e("size", waitHolidayArray.toString());

//                        loader_dialog.hideDialog();
                            pDialog.dismiss();

                            recyclerView.setAdapter(waitAdapter);
                            waitAdapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("onErrorResponse", "error : " + error.getMessage());
//                loader_dialog.hideDialog();
                    pDialog.dismiss();
                    Toast.makeText(getContext(), "حدث خلل في الاتصال  ", Toast.LENGTH_LONG).show();

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

            AppController.getInstance().addToRequestQueue(wait_holiday_request, 30000);


        } else {

            Toast.makeText(getContext(), "تعذر الاتصال بالانترنت ", Toast.LENGTH_LONG).show();
//        loader_dialog.hideDialog();
            pDialog.dismiss();

        }


    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void viewHoliday() {

        final Calendar calendar = Calendar.getInstance();
        //calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date MonthFirstDay = calendar.getTime();
        Log.e("nextMonthFirstDay", "" + dateFormatter.format(MonthFirstDay.getTime()));
        FROMDATE.setText(dateFormatter.format(MonthFirstDay.getTime()));
        FROMDATE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setDateFeild(FROMDATE);
            }
        });


        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date MonthLastDay = calendar.getTime();
        Log.e("nextMonthLastDay", "" + dateFormatter.format(MonthLastDay.getTime()));
        TODATE.setText(dateFormatter.format(MonthLastDay.getTime()));
        TODATE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setDateFeild(TODATE);
            }
        });

        viewHoliday_volley(FROMDATE.getText().toString(), TODATE.getText().toString());

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHoliday_volley(FROMDATE.getText().toString(), TODATE.getText().toString());
            }
        });


    }

    //////////////////////////////////////////////////////////////////////////////////
    private void setDateFeild(final EditText editText) {
        //Log.e("editText",""+editText.getTransitionName());
        fromDatePickerDialog.show();

        final Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(getContext(), AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                Log.e("newDate.getTime()", "" + newDate.getTime());

                editText.setText(dateFormatter.format(newDate.getTime()));

                //EMPHOLIDAYS_STARTDATE.setText(dateFormatter.format(newDate.getTime()));
                //calculateEndDate(newDate);


            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


    }

    /////////////////////////////////////////////////////////////////////////////////
    private void viewHoliday_volley(final String from, final String to) {
        viewHolidayArray.clear();
        viewHolidayAdapter = new ViewHolidayAdapter(getContext(), viewHolidayArray);

        viewHoliday_recyclerView.setAdapter(viewHolidayAdapter);
        viewHolidayAdapter.notifyDataSetChanged();

//        loader_dialog.setMsg("جاري عرض الاجازات ..");
//        loader_dialog.setCanceledOnTouchOutside(true);
//        loader_dialog.showDialog();
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("جاري عرض الاجازات ..");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        if (ToolsUtils.isConnectingToInternet(getContext())) {

            StringRequest viewHoliday_request = new StringRequest(Request.Method.POST, AppController.VIEW_HOLIDAY_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("response", response);
                    if (response != null) {
                        try {
                            JSONObject jobj = new JSONObject(response);
                            JSONArray jArr = jobj.getJSONArray("result");

                            for (int i = 0; i < jArr.length(); i++) {
                                Log.e("TB_EMPBASICINFO_NO", jArr.getJSONObject(i).getString("TB_EMPBASICINFO_NO").toString());

                                Log.e("FULL_NAME_AR", jArr.getJSONObject(i).getString("ARNAME").toString());
                                Log.e("TB__STARTDATE", jArr.getJSONObject(i).getString("TB_EMPHOLIDAYS_STARTDATE").toString());
                                Log.e("TB_EMPHOLIDAYS_HCOUNT", "" + jArr.getJSONObject(i).getInt("TB_EMPHOLIDAYS_HCOUNT"));

                                int TB_EMPHOLIDAYS_NO = jArr.getJSONObject(i).getInt("TB_EMPHOLIDAYS_NO");
                                String TB_EMPBASICINFO_NO = jArr.getJSONObject(i).getString("TB_EMPBASICINFO_NO").toString();
                                String FULL_NAME_AR = jArr.getJSONObject(i).getString("ARNAME").toString();
                                int TB_EMPHOLIDAYS_HCOUNT = jArr.getJSONObject(i).getInt("TB_EMPHOLIDAYS_HCOUNT");
                                String TB_EMPHOLIDAYS_STARTDATE = jArr.getJSONObject(i).getString("TB_EMPHOLIDAYS_STARTDATE").toString();
                                String TB_EMPHOLIDAYS_ENDDATE = jArr.getJSONObject(i).getString("TB_EMPHOLIDAYS_ENDDATE").toString();
                                String TB_CHOLIDAY_NAME = jArr.getJSONObject(i).getString("TB_CHOLIDAY_NAME").toString();
                                String C_ASSIST_NAME_AR = jArr.getJSONObject(i).getString("C_ASSIST_NAME_AR").toString();

                                HolidayClass view_holiday_record = new HolidayClass(TB_EMPHOLIDAYS_NO, TB_EMPBASICINFO_NO, FULL_NAME_AR, TB_EMPHOLIDAYS_HCOUNT, TB_EMPHOLIDAYS_STARTDATE, TB_EMPHOLIDAYS_ENDDATE, TB_CHOLIDAY_NAME, C_ASSIST_NAME_AR);

                                viewHolidayArray.add(view_holiday_record);

                            }
                            Log.e("size", viewHolidayArray.size() + "");

//                            loader_dialog.hideDialog();
                            pDialog.dismiss();

                            viewHoliday_recyclerView.setAdapter(viewHolidayAdapter);
                            viewHolidayAdapter.notifyDataSetChanged();


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

                    params.put("from", from);
                    params.put("to", to);

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

            AppController.getInstance().addToRequestQueue(viewHoliday_request, 30000);


        } else {

            Toast.makeText(getContext(), "تعذر الاتصال بالانترنت ", Toast.LENGTH_LONG).show();
//            loader_dialog.hideDialog();
            pDialog.dismiss();

        }


    }

    /////////////////////////////////////////////////////////////////////////////////
    private void viewBackHoliday() {
        backHolidayArray.clear();
        backHolidayAdapter = new BackHolidayAdapter(getContext(), backHolidayArray);

        backHoliday_recyclerView.setAdapter(viewHolidayAdapter);
        backHolidayAdapter.notifyDataSetChanged();

//        loader_dialog.setMsg("جاري عرض الاجازات المقبولة ..");
//        loader_dialog.setCanceledOnTouchOutside(true);
//        loader_dialog.showDialog();

        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("جاري عرض الاجازات المقبولة ..");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        if (ToolsUtils.isConnectingToInternet(getContext())) {

            StringRequest backHoliday_request = new StringRequest(Request.Method.POST, AppController.BACK_HOLIDAY_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("response", response);
                    if (response != null) {
                        try {
                            JSONObject jobj = new JSONObject(response);
                            JSONArray jArr = jobj.getJSONArray("result");

                            for (int i = 0; i < jArr.length(); i++) {
                                Log.e("TB_EMPBASICINFO_NO", jArr.getJSONObject(i).getString("TB_EMPBASICINFO_NO").toString());

                                Log.e("FULL_NAME_AR", jArr.getJSONObject(i).getString("TB_EMPBASICINFO_AFNAME").toString());
                                Log.e("TB__STARTDATE", jArr.getJSONObject(i).getString("TB_EMPHOLIDAYS_STARTDATE").toString());
                                Log.e("TB_EMPHOLIDAYS_HCOUNT", "" + jArr.getJSONObject(i).getInt("TB_EMPHOLIDAYS_HCOUNT"));

                                int TB_EMPHOLIDAYS_NO = jArr.getJSONObject(i).getInt("TB_EMPHOLIDAYS_NO");
                                String TB_EMPBASICINFO_NO = jArr.getJSONObject(i).getString("TB_EMPBASICINFO_NO").toString();
                                String FULL_NAME_AR = jArr.getJSONObject(i).getString("TB_EMPBASICINFO_AFNAME").toString() + " " + jArr.getJSONObject(i).getString("TB_EMPBASICINFO_ASNAME").toString() + " " + jArr.getJSONObject(i).getString("TB_EMPBASICINFO_ALNAME").toString();
                                int TB_EMPHOLIDAYS_HCOUNT = jArr.getJSONObject(i).getInt("TB_EMPHOLIDAYS_HCOUNT");
                                String TB_EMPHOLIDAYS_STARTDATE = jArr.getJSONObject(i).getString("TB_EMPHOLIDAYS_STARTDATE").toString();
                                String TB_EMPHOLIDAYS_ENDDATE = jArr.getJSONObject(i).getString("TB_EMPHOLIDAYS_ENDDATE").toString();

                                HolidayClass back_holiday_record = new HolidayClass(TB_EMPHOLIDAYS_NO, TB_EMPBASICINFO_NO, FULL_NAME_AR, TB_EMPHOLIDAYS_HCOUNT, TB_EMPHOLIDAYS_STARTDATE, TB_EMPHOLIDAYS_ENDDATE, null, null);

                                backHolidayArray.add(back_holiday_record);

                            }
                            Log.e("size", backHolidayArray.size() + "");

//                            loader_dialog.hideDialog();

                            pDialog.dismiss();

                            backHoliday_recyclerView.setAdapter(backHolidayAdapter);
                            backHolidayAdapter.notifyDataSetChanged();


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
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("User-ID", Fragment_Login.sharedPref.getString("USER_ID", null));
                    headers.put("Authorization", Fragment_Login.sharedPref.getString("Token", null));
                    Log.e("onResponse", headers.toString());
                    return headers;
                    //return super.getHeaders();
                }
            };

            AppController.getInstance().addToRequestQueue(backHoliday_request, 30000);


        } else {

            Toast.makeText(getContext(), "تعذر الاتصال بالانترنت ", Toast.LENGTH_LONG).show();
//            loader_dialog.hideDialog();
            pDialog.dismiss();

        }


    }


}
