package com.moh.app.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.moh.app.Fragment_Login;
import com.moh.app.models.HolidayClass;
import com.moh.app.moh.R;
import com.moh.app.utils.AppController;
import com.moh.app.utils.ToolsUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by pc-3 on 5/21/2018.
 */

public class BackHolidayAdapter extends RecyclerView.Adapter<BackHolidayAdapter.ViewHolder> {

    Context context;
    ArrayList<HolidayClass> backHolidayArray;

    public BackHolidayAdapter(Context context, ArrayList<HolidayClass> backHolidayArray) {
        this.context = context;
        this.backHolidayArray = backHolidayArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View vi = inflater.inflate(R.layout.row_backholiday_item, parent, false);
        ViewHolder holder = new ViewHolder(vi);
        //Log.e("TEST", "End create");
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.e("TEST", backHolidayArray.get(position).getTB_EMPBASICINFO_NO());
        holder.TB_EMPBASICINFO_NO.setText(backHolidayArray.get(position).getTB_EMPBASICINFO_NO());
        holder.FULL_NAME_AR.setText(backHolidayArray.get(position).getFULL_NAME_AR());
        holder.TB_EMPHOLIDAYS_HCOUNT.setText("" + (backHolidayArray.get(position).getTB_EMPHOLIDAYS_HCOUNT()));
        holder.TB_EMPHOLIDAYS_STARTDATE.setText(backHolidayArray.get(position).getTB_EMPHOLIDAYS_STARTDATE());
        holder.TB_EMPHOLIDAYS_ENDDATE.setText(backHolidayArray.get(position).getTB_EMPHOLIDAYS_ENDDATE());
        holder.confirm_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("error", "in");
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                // ...Irrelevant code for customizing the buttons and title

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogView = inflater.inflate(R.layout.alert_dialog_backholiday, null);
                dialogBuilder.setView(dialogView);
                final AlertDialog alertDialog = dialogBuilder.create();

                Button cancel_action = (Button) dialogView.findViewById(R.id.cancel_action);
                Button back_button = (Button) dialogView.findViewById(R.id.back_button);
                final EditText return_date = (EditText) dialogView.findViewById(R.id.return_date);
                return_date.setText(backHolidayArray.get(position).getTB_EMPHOLIDAYS_ENDDATE());


                cancel_action.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();

                    }
                });

                back_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ToolsUtils.isConnectingToInternet(context)) {

                            StringRequest confirm_holiday_request = new StringRequest(Request.Method.POST, AppController.CONFIRM_BACK_HOLIDAY_URL, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.e("response", response);
                                    if (response != null) {
                                        try {
                                            JSONObject jobj = new JSONObject(response);
                                            Log.e("Status", jobj.getString("result"));

                                            if (jobj.getString("result").equalsIgnoreCase("1")) {

                                                Toast.makeText(context, jobj.getString("msg"), Toast.LENGTH_LONG).show();
                                                backHolidayArray.remove(position);
                                                notifyItemRemoved(position);
                                            } else {
                                                Toast.makeText(context, jobj.getString("msg"), Toast.LENGTH_LONG).show();

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

                                    params.put("TB_EMPHOLIDAYS_NO", "" + backHolidayArray.get(position).getTB_EMPHOLIDAYS_NO());
                                    params.put("return_date", return_date.getText().toString());
                                    params.put("EMPHOLIDAYS_STARTDATE", "" + backHolidayArray.get(position).getTB_EMPHOLIDAYS_STARTDATE());

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


                        alertDialog.dismiss();
                    }
                });


                alertDialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return backHolidayArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView TB_EMPBASICINFO_NO, FULL_NAME_AR, TB_EMPHOLIDAYS_HCOUNT, TB_EMPHOLIDAYS_STARTDATE, TB_EMPHOLIDAYS_ENDDATE;
        Button confirm_back;

        public ViewHolder(View itemView) {
            super(itemView);
            TB_EMPBASICINFO_NO = (TextView) itemView.findViewById(R.id.TB_EMPBASICINFO_NO);
            FULL_NAME_AR = (TextView) itemView.findViewById(R.id.FULL_NAME_AR);
            TB_EMPHOLIDAYS_HCOUNT = (TextView) itemView.findViewById(R.id.TB_EMPHOLIDAYS_HCOUNT);
            TB_EMPHOLIDAYS_STARTDATE = (TextView) itemView.findViewById(R.id.TB_EMPHOLIDAYS_STARTDATE);
            TB_EMPHOLIDAYS_ENDDATE = (TextView) itemView.findViewById(R.id.TB_EMPHOLIDAYS_ENDDATE);
            confirm_back = (Button) itemView.findViewById(R.id.confirm_back);
        }
    }
}
