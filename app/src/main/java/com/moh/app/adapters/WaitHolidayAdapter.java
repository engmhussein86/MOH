package com.moh.app.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.moh.app.Fragment_Login;
import com.moh.app.eservices.HolidayFragment;
import com.moh.app.models.HolidayClass;
import com.moh.app.moh.R;
import com.moh.app.utils.AppController;
import com.moh.app.utils.ToolsUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by pc-3 on 4/25/2017.
 */
public class WaitHolidayAdapter extends RecyclerView.Adapter<WaitHolidayAdapter.ViewHolder> {

    Context context;
    ArrayList<HolidayClass> waitHolidayArray;

    public WaitHolidayAdapter(Context context, ArrayList<HolidayClass> waitHolidayArray) {
        this.context = context;
        this.waitHolidayArray = waitHolidayArray;
    }

    @Override
    public WaitHolidayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View vi = inflater.inflate(R.layout.row_waitholiday_item, parent, false);

        ViewHolder holder = new ViewHolder(vi);
        //Log.e("TEST", "End create");
        return holder;
    }

    @Override
    public void onBindViewHolder(WaitHolidayAdapter.ViewHolder holder, final int position) {

        Log.e("TEST", waitHolidayArray.get(position).getTB_EMPBASICINFO_NO());
        holder.TB_EMPBASICINFO_NO.setText(waitHolidayArray.get(position).getTB_EMPBASICINFO_NO());
        holder.FULL_NAME_AR.setText(waitHolidayArray.get(position).getFULL_NAME_AR());
        holder.TB_EMPHOLIDAYS_HCOUNT.setText("" + (waitHolidayArray.get(position).getTB_EMPHOLIDAYS_HCOUNT()));
        holder.TB_EMPHOLIDAYS_STARTDATE.setText(waitHolidayArray.get(position).getTB_EMPHOLIDAYS_STARTDATE());
        holder.TB_EMPHOLIDAYS_ENDDATE.setText(waitHolidayArray.get(position).getTB_EMPHOLIDAYS_ENDDATE());
        holder.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("موافقة");
                alert.setMessage("هل أنت متأكد من الموافقة على الاجازة ");
                alert.setPositiveButton("نعم", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (ToolsUtils.isConnectingToInternet(context)) {

                            StringRequest confirm_holiday_request = new StringRequest(Request.Method.POST, AppController.CONFIRM_HOLIDAY_URL, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.e("response", response);
                                    if (response != null) {
                                        try {
                                            JSONObject jobj = new JSONObject(response);
                                            Log.e("Status", jobj.getString("result").toString());

                                            if (jobj.getString("result").toString().equalsIgnoreCase("1")) {
                                                Toast.makeText(context, "تمت الموافقة", Toast.LENGTH_LONG).show();
                                                waitHolidayArray.remove(position);
                                                notifyItemRemoved(position);
                                            } else {
                                                Toast.makeText(context, "لم تتم الموافقة", Toast.LENGTH_LONG).show();

                                            }


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

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
                                    params.put("TB_EMPHOLIDAYS_NO", "" + waitHolidayArray.get(position).getTB_EMPHOLIDAYS_NO());
                                    params.put("assist", "" + 4);
                                    params.put("TB_EMPBASICINFO_NO", "" + waitHolidayArray.get(position).getTB_EMPBASICINFO_NO());

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

                            AppController.getInstance().addToRequestQueue(confirm_holiday_request, 30000);


                        } else {

                            Toast.makeText(context, "تعذر الاتصال بالانترنت ", Toast.LENGTH_LONG).show();

                        }


                        dialog.dismiss();
                    }
                });

                alert.setNegativeButton("لا", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(context, "no", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });

                alert.show();

            }
        });

        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("رفض");
                alert.setMessage("هل أنت متأكد من رفض الاجازة ؟؟");
                alert.setPositiveButton("نعم", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (ToolsUtils.isConnectingToInternet(context)) {
                            Log.e("Mariam", AppController.CONFIRM_HOLIDAY_URL);

                            StringRequest confirm_holiday_request = new StringRequest(Request.Method.POST, AppController.CONFIRM_HOLIDAY_URL, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.e("response", response);
                                    if (response != null) {
                                        try {
                                            JSONObject jobj = new JSONObject(response);
                                            Log.e("Status", jobj.getString("result"));

                                            if (jobj.getString("result").equalsIgnoreCase("1")) {

                                                Toast.makeText(context, "تم الرفض", Toast.LENGTH_LONG).show();
                                                waitHolidayArray.remove(position);
                                                notifyItemRemoved(position);
                                            } else {
                                                Toast.makeText(context, "لم يتم الرفض", Toast.LENGTH_LONG).show();

                                            }


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

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
                                    params.put("TB_EMPHOLIDAYS_NO", "" + waitHolidayArray.get(position).getTB_EMPHOLIDAYS_NO());
                                    params.put("assist", "" + 3);
                                    params.put("TB_EMPBASICINFO_NO", "" + waitHolidayArray.get(position).getTB_EMPBASICINFO_NO());

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

                            AppController.getInstance().addToRequestQueue(confirm_holiday_request, 30000);


                        } else {

                            Toast.makeText(context, "تعذر الاتصال بالانترنت ", Toast.LENGTH_LONG).show();

                        }


                        dialog.dismiss();
                    }
                });

                alert.setNegativeButton("لا", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(context, "no", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });

                alert.show();

            }
        });

        holder.FULL_NAME_AR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Mariam", waitHolidayArray.get(position).getTB_EMPBASICINFO_ID());
                new HolidayFragment().holiday_count_dialog(context, waitHolidayArray.get(position).getTB_EMPBASICINFO_ID());
            }
        });

    }

    @Override
    public int getItemCount() {
        return waitHolidayArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView TB_EMPBASICINFO_NO, FULL_NAME_AR, TB_EMPHOLIDAYS_HCOUNT, TB_EMPHOLIDAYS_STARTDATE, TB_EMPHOLIDAYS_ENDDATE;
        LinearLayout confirm, reject;

        public ViewHolder(View itemView) {
            super(itemView);
            TB_EMPBASICINFO_NO = (TextView) itemView.findViewById(R.id.TB_EMPBASICINFO_NO);
            FULL_NAME_AR = (TextView) itemView.findViewById(R.id.FULL_NAME_AR);
            TB_EMPHOLIDAYS_HCOUNT = (TextView) itemView.findViewById(R.id.TB_EMPHOLIDAYS_HCOUNT);
            TB_EMPHOLIDAYS_STARTDATE = (TextView) itemView.findViewById(R.id.TB_EMPHOLIDAYS_STARTDATE);
            TB_EMPHOLIDAYS_ENDDATE = (TextView) itemView.findViewById(R.id.TB_EMPHOLIDAYS_ENDDATE);

            confirm = (LinearLayout) itemView.findViewById(R.id.confirm);
            reject = (LinearLayout) itemView.findViewById(R.id.reject);

        }
    }
}
