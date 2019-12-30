package com.moh.app.eservices;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.moh.app.DrawerActivity;
import com.moh.app.Fragment_Login;
import com.moh.app.adapters.FinanceAdapter;
import com.moh.app.models.FinanceClass;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class FinanceFragment extends Fragment {

    TextView USER_FULL_NAME, USER_ID, TOTAL;
    RecyclerView recyclerView;
    ArrayList<FinanceClass> financeArray = new ArrayList<>();
    FinanceAdapter finAdapter;
    int totalAmount = 0;
    //    private LoadingDialog loader_dialog;
    ProgressDialog pDialog;


    public FinanceFragment() {
        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_finance, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DrawerActivity.navItemIndex = 7;
        ((DrawerActivity) getActivity()).setToolbarTitle();


//        loader_dialog = AppController.getLoadingDialog(getContext(), "");
        USER_FULL_NAME = (TextView) view.findViewById(R.id.USER_FULL_NAME);
        USER_ID = (TextView) view.findViewById(R.id.USER_ID);
        TOTAL = (TextView) view.findViewById(R.id.TOTAL);


        finAdapter = new FinanceAdapter(getContext(), financeArray);

        recyclerView = (RecyclerView) view.findViewById(R.id.hins_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(finAdapter);
        finAdapter.notifyDataSetChanged();

//        loader_dialog.setMsg("جاري تحميل الالتزامات المالية");
//        loader_dialog.setCanceledOnTouchOutside(true);
//        loader_dialog.showDialog();
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("جاري تحميل الالتزامات المالية");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        USER_FULL_NAME.setText(Fragment_Login.sharedPref.getString("USER_FULL_NAME", null));
        USER_ID.setText(Fragment_Login.sharedPref.getString("USER_ID", null));

        Log.e("Error", Fragment_Login.sharedPref.getString("USER_ID", null));
        Log.e("response", AppController.FINANCE_URL);

        if (ToolsUtils.isConnectingToInternet(getContext())) {

            StringRequest HinsRequest = new StringRequest(Request.Method.POST, AppController.FINANCE_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("response", response);
                    if (response != null) {
                        try {
                            JSONObject jobj = new JSONObject(response);
                            JSONArray jArr = jobj.getJSONArray("result");

                            for (int i = 0; i < jArr.length(); i++) {
                                Log.e("ENTRY_DATE", jArr.getJSONObject(i).getString("ENTRY_DATE").toString());

                                Log.e("PATIENT_ID", jArr.getJSONObject(i).getString("PATIENT_ID").toString());
                                Log.e("HOS_NAME", jArr.getJSONObject(i).getString("HOS_NAME").toString());
                                Log.e("AMOUNT", "" + jArr.getJSONObject(i).getInt("AMOUNT"));

                                String ENTRY_DATE = jArr.getJSONObject(i).getString("ENTRY_DATE").toString();
                                String PATIENT_ID = jArr.getJSONObject(i).getString("PATIENT_ID").toString();
                                String HOS_NAME = jArr.getJSONObject(i).getString("HOS_NAME").toString();
                                int AMOUNT = jArr.getJSONObject(i).getInt("AMOUNT");
                                int ADDED_COSTS1 = jArr.getJSONObject(i).getInt("ADDED_COSTS1");
                                int PAYED_AMOUNT = jArr.getJSONObject(i).getInt("PAYED_AMOUNT");

                                int remain = (AMOUNT + ADDED_COSTS1) - PAYED_AMOUNT;
                                totalAmount = totalAmount + remain;

                                FinanceClass finance_record = new FinanceClass(ENTRY_DATE, HOS_NAME, PATIENT_ID, AMOUNT, ADDED_COSTS1, PAYED_AMOUNT);
                                financeArray.add(finance_record);

                            }
                            Log.e("size", financeArray.size() + "");

//                            loader_dialog.hideDialog();
                            pDialog.dismiss();

                            recyclerView.setAdapter(finAdapter);
                            finAdapter.notifyDataSetChanged();
                            TOTAL.setText(totalAmount + "");


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

            AppController.getInstance().addToRequestQueue(HinsRequest, 30000);
        } else {

            Toast.makeText(getContext(), "تعذر الاتصال بالانترنت ", Toast.LENGTH_LONG).show();
//            loader_dialog.hideDialog();
            pDialog.dismiss();

        }

    }
}
