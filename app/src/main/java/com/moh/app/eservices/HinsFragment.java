package com.moh.app.eservices;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.moh.app.adapters.HinsAdapter;
import com.moh.app.models.HinsUser;
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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HinsFragment extends Fragment {

    //////////////////HinsPhoto//////////////////
    public static final int PICK_IMAGE_ID = 234; // the number doesn't matter
    public static ArrayList<HinsUser> hinsUsers = new ArrayList<>();
    public static HinsAdapter hinsAdapter;
    public static Bitmap bitmap;
    public static int finish = 0;
    TextView USER_FULL_NAME, LAST_LOGIN, CARD_NO, INS_STATUS_DESC, CLINIC_CODE_DESC, INS_TYPE_DESC, START_DATE, EXP_DATE;
    RecyclerView recyclerView;
    String User_id, relation_desc, DEP_NAME;
    int IsDep, IMG_APPROVE;
    //    private LoadingDialog loader_dialog;
    ProgressDialog pDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView instruction;
    /////////////////////////////////////////////


    public HinsFragment() {
        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hins, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DrawerActivity.navItemIndex = 6;
        ((DrawerActivity) getActivity()).setToolbarTitle();

//        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sharedPreferences.edit();

        hinsUsers.clear();

        if (sharedPreferences.contains("Hins_approve")) {
            if (sharedPreferences.getBoolean("Hins_approve", false)) {
                viewHinsData();
            }
        } else {

            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
            alert.setTitle("اقرار الكتروني");
            alert.setMessage("أقر بصحة ومطابقة الصور المرفقة وعليه فانني أتحمل كامل المسؤولية القانونية أمام الجهات الرسمية بخصوص صحة الصور المرفقة الكترونيا");
            alert.setPositiveButton("موافق", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    editor.putBoolean("Hins_approve", true);
                    editor.commit();

                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    alert.setTitle("شروط رفع الصور");
                    alert.setMessage("هل تود الاطلاع على تعليمات رفع الصور؟؟ ");
                    alert.setPositiveButton("موافق", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://apps.moh.gov.ps/mohmob/hins_instruction.html"));
                            startActivity(browserIntent);
                        }
                    });

                    alert.setNegativeButton("الغاء", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Toast.makeText(context, "no", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    });

                    alert.show();

                    viewHinsData();
                }
            });

            alert.setNegativeButton("الغاء", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Toast.makeText(context, "no", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            });

            alert.show();
        }


//        loader_dialog = AppController.getLoadingDialog(getContext(), "");
        USER_FULL_NAME = (TextView) view.findViewById(R.id.USER_FULL_NAME);
        LAST_LOGIN = (TextView) view.findViewById(R.id.LAST_LOGIN);
        CARD_NO = (TextView) view.findViewById(R.id.CARD_NO);
        INS_STATUS_DESC = (TextView) view.findViewById(R.id.INS_STATUS_DESC);
        CLINIC_CODE_DESC = (TextView) view.findViewById(R.id.CLINIC_CODE_DESC);
        INS_TYPE_DESC = (TextView) view.findViewById(R.id.INS_TYPE_DESC);
        START_DATE = (TextView) view.findViewById(R.id.START_DATE);
        EXP_DATE = (TextView) view.findViewById(R.id.EXP_DATE);

        instruction = (TextView) view.findViewById(R.id.instruction);
//        instruction.setText("ssss");
        instruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://apps.moh.gov.ps/mohmob/hins_instruction.html"));
                startActivity(browserIntent);


            }
        });

        hinsAdapter = new HinsAdapter(getActivity(), hinsUsers);

        recyclerView = (RecyclerView) view.findViewById(R.id.hins_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(hinsAdapter);
        hinsAdapter.notifyDataSetChanged();


        USER_FULL_NAME.setText(Fragment_Login.sharedPref.getString("USER_FULL_NAME", null));
//        LAST_LOGIN.setText(Fragment_Login.sharedPref.getString("LAST_LOGIN", null));

        Log.e("Error", Fragment_Login.sharedPref.getString("USER_ID", null));
        Log.e("response", AppController.HINS_URL);


    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressLint("ValidFragment")
    public ArrayList<HinsUser> viewHinsData() {
        hinsUsers.clear();
//        loader_dialog.setMsg("جاري تحميل بيانات التأمين الصحي");
//        loader_dialog.setCanceledOnTouchOutside(true);
//        loader_dialog.showDialog();

        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("جاري تحميل بيانات التأمين الصحي");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();


        if (ToolsUtils.isConnectingToInternet(getContext())) {

            StringRequest HinsRequest = new StringRequest(Request.Method.POST, AppController.HINS_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("response", response);
                    if (response != null) {


                        try {

//                            Users users =  new Gson().fromJson(response, Users.class);
//                            Log.e("Error", "users"+ users.getResult().size());


                            JSONObject jobj = new JSONObject(response);
                            JSONArray jArr = jobj.getJSONArray("result");

                            Log.e("response", "" + jArr.length());
                            hinsUsers.clear();
                            if (jArr.length() > 0) {

                                CARD_NO.setText(jArr.getJSONObject(0).getString("CARD_NO").toString());
                                INS_STATUS_DESC.setText(jArr.getJSONObject(0).getString("INS_STATUS_DESC").toString());
                                CLINIC_CODE_DESC.setText(jArr.getJSONObject(0).getString("CLINIC_CODE_DESC").toString());
                                INS_TYPE_DESC.setText(jArr.getJSONObject(0).getString("INS_TYPE_DESC").toString());
                                START_DATE.setText(jArr.getJSONObject(0).getString("START_DATE").toString());
                                EXP_DATE.setText(jArr.getJSONObject(0).getString("EXP_DATE").toString() + " --- ");

                                for (int i = 0; i < jArr.length(); i++) {
                                    Log.e("DEP_ID_NO", jArr.getJSONObject(i).getString("DEP_ID_NO").toString());

                                    if (jArr.getJSONObject(i).getString("DEP_ID_NO").toString().equalsIgnoreCase("0")) {
                                        User_id = jArr.getJSONObject(i).getString("ID_NO").toString();
                                        IsDep = 1;//صاحب التأمين
                                    } else {
                                        User_id = jArr.getJSONObject(i).getString("DEP_ID_NO").toString();
                                        IsDep = 2;//مرافق
                                    }
                                    Log.e("DEP_NAME", jArr.getJSONObject(i).getString("DEP_NAME").toString());

                                    if (jArr.getJSONObject(i).getString("DEP_NAME").toString().trim().equalsIgnoreCase("-")) {
                                        DEP_NAME = jArr.getJSONObject(i).getString("FULL_NAME_AR").toString();
                                    } else {
                                        DEP_NAME = jArr.getJSONObject(i).getString("DEP_NAME").toString();
                                    }


                                    if (jArr.getJSONObject(i).getString("REL_TYPE_DESC").toString().trim().equalsIgnoreCase("-")) {
                                        relation_desc = "صاحب التأمين";
                                    } else {
                                        relation_desc = jArr.getJSONObject(i).getString("REL_TYPE_DESC").toString();
                                    }
                                    Log.e("DEP_NAME1", relation_desc);

                                    if (!jArr.getJSONObject(i).getString("IMG_APPROVE").equalsIgnoreCase("null")) {
                                        IMG_APPROVE = Integer.parseInt(jArr.getJSONObject(i).getString("IMG_APPROVE"));
                                    } else {
                                        IMG_APPROVE = 0;
                                    }


                                    HinsUser hinsUser_record = new HinsUser(User_id, DEP_NAME, relation_desc, IsDep);
                                    hinsUser_record.setIMG_APPROVE(IMG_APPROVE);
                                    hinsUsers.add(hinsUser_record);


                                }
                            }
                            Log.e("size", hinsUsers.size() + "");

//                            loader_dialog.hideDialog();

                            pDialog.dismiss();
                            recyclerView.setAdapter(hinsAdapter);
                            hinsAdapter.notifyDataSetChanged();


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

        return hinsUsers;


    }
    ////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////for Hins Photo Adapter ///////////////////////////////////

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.e("error", "jjjjj");
////        switch (requestCode) {
////            case PICK_IMAGE_ID:
////
////
////                //Log.e("TAG",data.getExtras().getString("img_name"));
////
////
////                    Log.e("error", "jjjjj");
////
////                    bitmap = ImagePicker.getImageFromResult(getActivity(), resultCode, data);
////                    Log.e("error", bitmap.toString());
////                    //HinsAdapter.ViewHolder.view_img.setImageBitmap(bitmap);
//////                    ImageView view_img = (ImageView) findViewById(R.id.view_img);
//////                    view_img.setImageBitmap(bitmap);
////                    new UploadImage(bitmap,"hello").execute();
////
////                break;
////            default:
////                super.onActivityResult(requestCode, resultCode, data);
////                break;
////        }
//        switch (requestCode) {
//            case PICK_IMAGE_ID:
//                Log.e("error", "jjjjj");
//                String bitmap = ImagePicker.getImageFromResultString(getActivity(), resultCode, data);
//                Log.e("error", bitmap);
//                hinsUsers.get(HinsAdapter.selectedPosition).setImageFile(bitmap);
//                hinsAdapter.notifyItemChanged(HinsAdapter.selectedPosition);
//                //new UploadImage(ImagePicker.decodeBitmap(getActivity(), Uri.fromFile(new File(bitmap)), 4), "hello").execute();
//                break;
//            default:
//                super.onActivityResult(requestCode, resultCode, data);
//                break;
//        }
//        //finishWithResult();
//    }


}
