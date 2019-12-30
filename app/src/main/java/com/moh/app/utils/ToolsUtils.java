package com.moh.app.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
import android.util.Patterns;
import android.view.Display;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pc-3 on 7/27/2016.
 */
public class ToolsUtils {


    public static boolean isEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isName(String name) {
        Pattern p1 = Pattern.compile("^[a-z0-9A-Z\\s]{3,30}$");
        Pattern p2 = Pattern.compile("^[\\u0600-\\u06FF۰-۹\\s]{3,30}");
        Matcher m1 = p1.matcher(name);
        Matcher m2 = p2.matcher(name);
        return m1.matches() || m2.matches();
    }

    // check the text is mobile number or not
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^[1-9][0-9]{3,14}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    //check the connectivity of Internet
    public static boolean isConnectingToInternet(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();


        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    //get screen size
    public static Point getScreenSize(Display display) {
        Point point = new Point();
        int w = display.getWidth();
        int h = display.getHeight();
        point.set(w, h);
        return point;
    }

    //change the format of display date
    public static String getNewDateFormat(String time, String format1, String format2) {
        String updatedTime = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format1, Locale.US);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            SimpleDateFormat dateFormat = new SimpleDateFormat(format2, Locale.US);
            dateFormat.setTimeZone(TimeZone.getDefault());
            updatedTime = dateFormat.format(simpleDateFormat.parse(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return updatedTime;
    }

    public static Object readObjectFromFile(Context context, String file) {
        Object object = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(context.openFileInput(file));
            object = objectInputStream.readObject();
            objectInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    public static void writeObjectToFile(Context context, String file, Object object) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(context.openFileOutput(file, Context.MODE_PRIVATE));
            oos.writeObject(object);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isFileExists(Context context, String file) {
        return context.getFileStreamPath(file).exists();
    }


    public static boolean RemoveUserFile(Context context, String file) {
        return context.getFileStreamPath(file).delete();
    }

    //convert File to Bitmap image
    public static Bitmap decodeFile(File f) {
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            final int REQUIRED_SIZE = 512;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void grantAllUriPermissions(Context context, Intent intent, Uri uri) {
        List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            context.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    public static void checkToken(final Context context, final String token, final String id) {

        if (ToolsUtils.isConnectingToInternet(context)) {

            StringRequest fcm_exist_request = new StringRequest(Request.Method.POST, AppController.EXIST_TOKEN_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("response", response);
                    if (response != null) {
                        try {

                            JSONObject jobj = new JSONObject(response);
                            Log.e("token", jobj.getJSONObject("result").getString("token").toString());
                            String exist_token = jobj.getJSONObject("result").getString("token").toString();

                            if (token != null) {
                                Log.e("current_token", token);
                                if (!exist_token.equalsIgnoreCase(token) || !exist_token.equalsIgnoreCase("0")) {
                                    //Toast.makeText(getContext(), "تم تسجيل الدخول من جهاز اخر .. الرجاء تسجيل الدخول مرة أخرى", Toast.LENGTH_LONG).show();
                                    insertToken(context, token, id);

                                }
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


                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id", id);

                    Log.e("params", params.toString());
                    return params;
                }
            };

            AppController.getInstance().addToRequestQueue(fcm_exist_request, 30000);


        } else {

            Toast.makeText(context, "تعذر الاتصال بالانترنت ", Toast.LENGTH_LONG).show();

        }


    }


    ///////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    public static void insertToken(final Context context, final String token, final String id) {

        StringRequest fcmRequest = new StringRequest(Request.Method.POST, AppController.INSERT_TOKEN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response);

                try {
                    if (response != null) {
                        final JSONObject jobj = new JSONObject(response);
                        Log.e("added", jobj.getJSONObject("result").getString("added").toString());

                        if (!jobj.getJSONObject("result").getString("added").toString().equalsIgnoreCase("1")) {

                            Toast.makeText(context, "حدثت مشكلة أثناء تسجيل الجهاز .. لن يتم وصول التنبيهات بشكل صحيح ", Toast.LENGTH_LONG).show();

                        }

                    } else {
                        Toast.makeText(context, "حدثت مشكلة أثناء تسجيل الجهاز .. لن يتم وصول التنبيهات بشكل صحيح ", Toast.LENGTH_LONG).show();
                        Log.e("response", "null");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse", "error : " + error.getMessage());


            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("TOKEN", token);
                params.put("id", id);

                Log.e("params", params.toString());
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(fcmRequest, 30000);


    }

    /////////////////////////////////////////////////////////////////////////
    public static boolean isPackageInstalled(String packagename, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packagename, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    public static void makeFolder(String dirPath) {

        File dir = new File(dirPath);

        if (!dir.exists()) {
            Log.e("exists", "not exist");

            boolean created = dir.mkdirs();


            if (created) {
                Log.e("create", "create");
            } else {
                Log.e("create", "not create");
            }


        }


    }

    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    public static boolean hasWritePermissions(Context context) {
        int res = 0;
        //string array of permissions,
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

        for (String perms : permissions) {
            res = context.checkCallingOrSelfPermission(perms);
            if (!(res == PackageManager.PERMISSION_GRANTED)) {
                return false;
            }
        }
        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    public static String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    public static String getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
    /////////////////////////////////////////////////////////////////////////////////////////////


}
