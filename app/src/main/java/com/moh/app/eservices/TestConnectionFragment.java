package com.moh.app.eservices;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.moh.app.DrawerActivity;
import com.moh.app.Fragment_Login;
import com.moh.app.moh.R;
import com.moh.app.utils.AppController;
import com.moh.app.utils.ToolsUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class TestConnectionFragment extends Fragment {

    //    private LoadingDialog loader_dialog;
    ProgressDialog pDialog;
    TextView USER_FULL_NAME, USER_NO;
    ImageView moi_result_image, hins_result_image;


    public TestConnectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_connection, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DrawerActivity.navItemIndex = 14;
        ((DrawerActivity) getActivity()).setToolbarTitle();


//        loader_dialog = AppController.getLoadingDialog(getContext(), "");
        USER_FULL_NAME = (TextView) view.findViewById(R.id.USER_FULL_NAME);
        USER_NO = (TextView) view.findViewById(R.id.USER_NO);
        moi_result_image = (ImageView) view.findViewById(R.id.moi_result_image);
        hins_result_image = (ImageView) view.findViewById(R.id.hins_result_image);


        USER_FULL_NAME.setText(Fragment_Login.sharedPref.getString("USER_FULL_NAME", null));

        TEST_CONNECTION(0); // GOV_CONNECTION
        TEST_CONNECTION(1); // HINS_CONNECTION

    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    public void TEST_CONNECTION(int Conn_type) {
        String Conn_url = null;
        ImageView Conn_imgViw = null;

        switch (Conn_type) {


            case 0:
                Conn_url = AppController.GOV_CONNECTION_URL;
                Conn_imgViw = moi_result_image;
                break;

            case 1:
                Conn_url = AppController.HINS_CONNECTION_URL;
                Conn_imgViw = hins_result_image;
                break;


        }


//        loader_dialog.setMsg("جاري تحميل الدوام");
//        loader_dialog.showDialog();

        if (ToolsUtils.isConnectingToInternet(getContext())) {

//            loader_dialog.setMsg("جاري تحميل الدوام");
//            loader_dialog.showDialog();

            final ImageView finalConn_imgViw = Conn_imgViw;
            StringRequest testConnRequest = new StringRequest(Request.Method.POST, Conn_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("response", response);
                    if (response != null) {
                        try {
                            JSONObject jobj = new JSONObject(response);

                            Log.e("Status", jobj.getString("result").toString());

                            if (jobj.getString("result").toString().equalsIgnoreCase("1")) {
                                finalConn_imgViw.setImageResource(R.drawable.ic_check_circle_green_600_48dp);

                            } else {

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
//                    loader_dialog.hideDialog();
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

            AppController.getInstance().addToRequestQueue(testConnRequest, 30000);
        } else {

            Toast.makeText(getContext(), "تعذر الاتصال بالانترنت ", Toast.LENGTH_LONG).show();
//            loader_dialog.hideDialog();

        }

    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
