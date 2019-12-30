package com.moh.app.phone;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.moh.app.DrawerActivity;
import com.moh.app.adapters.FavAdapter;
import com.moh.app.adapters.PhonesAdapter1;
import com.moh.app.adapters.SitesAdapter;
import com.moh.app.db.PhoneDBHandler;
import com.moh.app.models.PhoneClass;
import com.moh.app.moh.R;
import com.moh.app.network.CustomRequest;
import com.moh.app.utils.AppController;
import com.moh.app.utils.ToolsUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragPhones extends Fragment {

    public static int STATE = 1;
    public static TextView size;
    Button favourite_btn, web_btn;
    SitesAdapter sitesAdapter;
    PhonesAdapter1 phonesAdapter1;
    FavAdapter favAdapter1;
    Spinner sites_spinner;
    EditText phone_key, fname;//,lname;
    ImageView phone_search_btn, name_search_btn, site_search_btn;
    ArrayList<HashMap<String, String>> fav_data, sites;
//    private LoadingDialog loader_dialog;


    ProgressDialog pDialog;
    PhoneDBHandler handler;
    LinearLayout result_layout, phoneSearch, siteSearch, nameSearch;
    LinearLayout site_layout, contact_layout, phone_layout, fav_layout, content;
    ArrayList<PhoneClass> phones1 = new ArrayList<>();
    ArrayList<PhoneClass> fav_data1 = new ArrayList<>();
    private RecyclerView recyclerView;


    public FragPhones() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phones1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("dd", "dddd");

        DrawerActivity.navItemIndex = 2;
        ((DrawerActivity) getActivity()).setToolbarTitle();
        ((DrawerActivity) getActivity()).selectNavMenu();

        sites = new ArrayList<HashMap<String, String>>();
        fav_data = new ArrayList<HashMap<String, String>>();

        sitesAdapter = new SitesAdapter(getContext(), 0, sites);
        phonesAdapter1 = new PhonesAdapter1(getContext(), phones1);
        favAdapter1 = new FavAdapter(getContext(), fav_data1);

        site_layout = (LinearLayout) view.findViewById(R.id.site_layout);
        contact_layout = (LinearLayout) view.findViewById(R.id.contact_layout);
        phone_layout = (LinearLayout) view.findViewById(R.id.phone_layout);
        fav_layout = (LinearLayout) view.findViewById(R.id.fav_layout);

        content = (LinearLayout) view.findViewById(R.id.content);

        siteSearch = (LinearLayout) view.findViewById(R.id.siteSearch);
        nameSearch = (LinearLayout) view.findViewById(R.id.nameSearch);
        phoneSearch = (LinearLayout) view.findViewById(R.id.phoneSearch);
        result_layout = (LinearLayout) view.findViewById(R.id.result_layout);

//        loader_dialog = AppController.getLoadingDialog(getContext(), "");
        handler = new PhoneDBHandler(getContext());

        sites_spinner = (Spinner) view.findViewById(R.id.sites_spinner);
        sites_spinner.setAdapter((SpinnerAdapter) sitesAdapter);


        recyclerView = (RecyclerView) view.findViewById(R.id.phones_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        phone_key = (EditText) view.findViewById(R.id.phone_key);
        phone_key.clearFocus();
        fname = (EditText) view.findViewById(R.id.fname);
        //lname = (EditText)view.findViewById(R.id.lname);


        phone_search_btn = (ImageView) view.findViewById(R.id.phone_search_btn);
        name_search_btn = (ImageView) view.findViewById(R.id.name_search_btn);
        site_search_btn = (ImageView) view.findViewById(R.id.site_search_btn);

        size = (TextView) view.findViewById(R.id.data_size);
        size.setText("0");

        content.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.purple_light));

        name_search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
                Log.e("ssss", fname.getText().toString());//+ lname.getText().toString());

                if (!fname.getText().toString().equalsIgnoreCase("")) {
                    getPhones(0, "", fname.getText().toString(), "");


                    fname.setText("");
                    //lname.setText("");
                } else {
                    Toast.makeText(getContext(), "الرجاء ادخال الاسم المراد البحث عنه", Toast.LENGTH_LONG).show();
                }

            }
        });

//        site_search_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                clear();
//
//                HashMap<String, String> site = (HashMap) sites_spinner.getItemAtPosition(sites_spinner.getSelectedItemPosition());
//                int loc = Integer.parseInt(site.get("LOC_SID").toString());
//
//                Log.e("loc",""+loc);
//                getPhones(loc ,"","" , "");
//
//
//            }
//        });

/////////////////////////////////////////////////////////////////////////////////////
        site_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
                content.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray_light));
                getSites();

                siteSearch.setVisibility(View.VISIBLE);
                nameSearch.setVisibility(View.GONE);
                phoneSearch.setVisibility(View.GONE);

                site_search_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clear();

                        HashMap<String, String> site = (HashMap) sites_spinner.getItemAtPosition(sites_spinner.getSelectedItemPosition());
                        int loc = Integer.parseInt(site.get("LOC_SID").toString());

                        Log.e("loc", "" + loc);
                        getPhones(loc, "", "", "");

                    }
                });

            }
        });
//////////////////////////////////////////////////////////////////////////////////////////////
        contact_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
                content.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.purple_light));

                siteSearch.setVisibility(View.GONE);
                nameSearch.setVisibility(View.VISIBLE);
                phoneSearch.setVisibility(View.GONE);

                name_search_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clear();
                        Log.e("ssss", fname.getText().toString());// + lname.getText().toString());

                        if (!fname.getText().toString().equalsIgnoreCase("")) {

                            getPhones(0, "", fname.getText().toString(), "");


                            fname.setText("");
                            //lname.setText("");
                        } else {
                            Toast.makeText(getContext(), "الرجاء ادخال الاسم المراد البحث عنه", Toast.LENGTH_LONG).show();
                        }

                    }
                });

            }
        });
///////////////////////////////////////////////////////////////////////////////////////////////////
        phone_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
                content.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green_light));
                siteSearch.setVisibility(View.GONE);
                nameSearch.setVisibility(View.GONE);
                phoneSearch.setVisibility(View.VISIBLE);

                phone_search_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clear();
                        phone_key.clearFocus();
                        Log.e("phone ", phone_key.getText().toString());

                        getPhones(0, phone_key.getText().toString(), "", "");

                        phone_key.setText("");

                    }
                });


            }
        });
/////////////////////////////////////////////////////////////////////////////////////////////////
        fav_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();

                content.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.yellow_light));

                siteSearch.setVisibility(View.GONE);
                nameSearch.setVisibility(View.GONE);
                phoneSearch.setVisibility(View.GONE);

                getFavourites();
                favAdapter1.notifyDataSetChanged();


            }
        });

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void clear() {
        fav_data.clear();
        phones1.clear();
        fav_data1.clear();


        InputMethodManager inputManager =
                (InputMethodManager) getContext().
                        getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);


        phonesAdapter1.notifyDataSetChanged();
        favAdapter1.notifyDataSetChanged();


        result_layout.setVisibility(View.GONE);
        size.setText("0");

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void getSites() {
        Log.e("ERROR", "getSites");

//        loader_dialog.setMsg("جاري تحميل المرافق");
//        loader_dialog.setCanceledOnTouchOutside(true);
//        loader_dialog.showDialog();
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("جاري تحميل المرافق");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        if (ToolsUtils.isConnectingToInternet(getContext())) {


            HashMap<String, String> params = new HashMap<>();

            CustomRequest mStringRequest = new CustomRequest(Request.Method.POST, AppController.GET_SITES_URL, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("sites", response.toString());

                    try {
                        if (response != null) {
                            //JSONObject jobj = new JSONObject(response);
                            JSONArray jArr = response.getJSONArray("result");
                            Log.e("sitesLength", String.valueOf(jArr.length()));

//                            HashMap<String, String> map1 = new HashMap<>();
//
//                            map1.put("LOC_SID", "0");
//                            map1.put("LOC_TITLE_AR", "كل الجهات");
//                            sites.add(map1);


                            for (int i = 0; i < jArr.length(); i++) {
                                //Log.e("Error",jArr.getJSONObject(i).getString("LOC_SID").toString());
                                HashMap<String, String> map = new HashMap<>();

                                map.put("LOC_SID", jArr.getJSONObject(i).getString("LOC_SID"));
                                map.put("LOC_TITLE_AR", jArr.getJSONObject(i).getString("LOC_TITLE_AR"));
                                sites.add(map);

                            }
                        }

//                        loader_dialog.hideDialog();
                        pDialog.dismiss();

                        sitesAdapter.notifyDataSetChanged();
                        sites_spinner.setSelection(0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("ERROR", error.toString());
//                    loader_dialog.hideDialog();
                    pDialog.dismiss();
                    Toast.makeText(getContext(), "حدث خلل في الاتصال  ", Toast.LENGTH_LONG).show();
                }
            });

            mStringRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            AppController.getInstance().addToRequestQueue(mStringRequest);
        } else {
            Log.e("ERROR", "not connected");
            Toast.makeText(getContext(), "تعذر الاتصال بالانترنت ", Toast.LENGTH_LONG).show();
//            loader_dialog.hideDialog();
            pDialog.dismiss();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void getPhones(final int loc, final String phone, final String fname, final String lname) {
//        loader_dialog.setMsg("جاري تحميل أرقام الهواتف");
//        loader_dialog.setCanceledOnTouchOutside(true);
//        loader_dialog.showDialog();
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("جاري تحميل أرقام الهواتف");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        if (ToolsUtils.isConnectingToInternet(getContext())) {
            Log.e("mariam", AppController.GET_PHONES_URL);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, AppController.GET_PHONES_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("response", response.toString());
                            try {
                                if (response != null) {
                                    JSONObject jobj = new JSONObject(response);
                                    JSONArray jArr = jobj.getJSONArray("result");
                                    Log.e("Error", String.valueOf(jArr.length()));

                                    for (int i = 0; i < jArr.length(); i++) {
                                        PhoneClass phone_record = new PhoneClass(jArr.getJSONObject(i).getString("PHONE_SID").toString(), jArr.getJSONObject(i).getString("PHONE_NO").toString(), jArr.getJSONObject(i).getString("PHONE_USER").toString(), jArr.getJSONObject(i).getString("JOB_TITLE").toString(), false, jArr.getJSONObject(i).getString("LOC_TITLE_AR").toString());
                                        phones1.add(phone_record);


                                    }
                                }
                                Log.e("size", phones1.size() + "");

                                size.setText("" + phones1.size());
                                result_layout.setVisibility(View.VISIBLE);

//                                loader_dialog.hideDialog();

                                pDialog.dismiss();

                                recyclerView.setAdapter(phonesAdapter1);
                                phonesAdapter1.notifyDataSetChanged();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
//                            loader_dialog.hideDialog();

                            pDialog.dismiss();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("loc", "" + loc);
                    params.put("phone", phone.trim());
                    params.put("fname", fname.trim());
                    params.put("lname", lname.trim());

                    Log.e("mariam", params.toString());
                    return params;
                }

//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    HashMap<String, String> headers = new HashMap<String, String>();
//                    headers.put("Content-Type", "application/json");
//                    headers.put("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IkNTVDAwMDI1ODAwIiwic3RhdHVzIjoiYWN0aXZlIiwiaWF0IjoxNDc0MzUxOTIzLCJpc3MiOiJDU1QwMDAyNTgwMCJ9.MwoO3QDU7QiIkK4boK9IhiS4Jb4JKMdBYiNbsNA2A50");
//                    Log.e("onResponse",headers.toString());
//                    return headers;
//                    //return super.getHeaders();
//                }


            };


            AppController.getInstance().addToRequestQueue(stringRequest, 30000);

        } else {

            Toast.makeText(getContext(), "تعذر الاتصال بالانترنت ", Toast.LENGTH_LONG).show();
//            loader_dialog.hideDialog();

            pDialog.dismiss();
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void getFavourites() {

//        loader_dialog.setMsg("جاري تحميل ارقام الهواتف");
//        loader_dialog.setCanceledOnTouchOutside(true);
//        loader_dialog.showDialog();

        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("جاري تحميل ارقام الهواتف");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        fav_data1 = handler.getAllPhones();

        Log.e("size", fav_data1.size() + "");

        size.setText("" + fav_data1.size());
        result_layout.setVisibility(View.VISIBLE);

        favAdapter1 = new FavAdapter(getContext(), fav_data1);
        recyclerView.setAdapter(favAdapter1);
        favAdapter1.notifyDataSetChanged();


//        loader_dialog.hideDialog();

        pDialog.dismiss();

    }


}
