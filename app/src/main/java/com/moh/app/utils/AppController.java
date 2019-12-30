package com.moh.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.animation.Animation;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.moh.app.network.LruBitmapCache;

import java.lang.reflect.Field;

import androidx.multidex.MultiDexApplication;

public class AppController extends MultiDexApplication {


    public static final String TAG = AppController.class.getSimpleName();
    public static final String BASE_URL = "http://apps.moh.gov.ps/newwebemp/AndroidAPI/";
    public static final String BASE_URL1 = "http://apps.moh.gov.ps/mohmob/RESTAPI/";
    public static final String NEWS_URL = "http://www.moh.gov.ps/test/test.php";
    public static final String GET_PHONES_URL = BASE_URL + "PhoneAPI.php?type=GET_PHONES";
    public static final String GET_SITES_URL = BASE_URL + "PhoneAPI.php?type=GET_SITES";
    //    public static final String ESERVICES_URL = BASE_URL+"AndroidAuth.php";
//    public static final String HINS_URL = BASE_URL+"HinsAPI.php";
//    public static final String HINSPHOTO_URL =BASE_URL+ "HinsPhoto.php";
    public static final String VIEW_HINSPHOTO_URL = BASE_URL + "HinsPhoto.php";
    //    public static final String FINANCE_URL = BASE_URL+"FINANCEAPI.php";
//    public static final String ATTENDANCE_URL = BASE_URL+"AttendanceAPI.php";
//    public static final String HOLIDAY_URL = BASE_URL+"HolidayAPI1.php";
//    public static final String VIEW_HOLIDAY_URL = BASE_URL+"HolidayRepAPI.php";
    //public static final String WAIT_HOLIDAY_URL = BASE_URL+"WaitEmpHoliday1.php";
    //public static final String CONFIRM_HOLIDAY_URL = WAIT_HOLIDAY_URL+"?type=confirm_holiday";
//    public static final String SHIFT_URL = BASE_URL+"ShiftAPI.php";
    public static final String COMPLAINT_URL = BASE_URL + "ComplaintAPI.php";
    public static final String INSERT_TOKEN_URL = BASE_URL + "fcm/fcm_insert.php";
    public static final String DELETE_TOKEN_URL = BASE_URL + "fcm/fcm_delete.php";
    public static final String EXIST_TOKEN_URL = BASE_URL + "fcm/fcm_exist.php";
    //    public static final String BACK_HOLIDAY_URL = BASE_URL+"BackHolidaysAPI.php";
//    public static final String CONFIRM_BACK_HOLIDAY_URL = BACK_HOLIDAY_URL+"?type=confirm_back_holiday";
    public static final String PERMISSION_URL = BASE_URL + "PermissionAPI.php";
    public static final String HOSPITALS_INJURIES_URL = BASE_URL + "HOSPITALS_INJURIES_API.php";
    public static final String HOSPITALS_URL = HOSPITALS_INJURIES_URL + "?type=GET_ALL_HOSPITALS";
    //////////////////////////////////////////////////////////////////////
    public static final String OPERATION_SCHEDULE_URL = "http://apps.moh.gov.ps/cs/index.php/General_api/get_operations";
    public static final String OUTPATIENT_CLINIC_URL = "http://apps.moh.gov.ps/cs/index.php/General_api/get_visits";
    public static final String LABORDER_URL = "http://apps.moh.gov.ps/cs/index.php/General_api/get_lab_orders";
    public static final String GOV_CONNECTION_URL = BASE_URL1 + "Test_connection/GOV_CONNECTION";
    public static final String HINS_CONNECTION_URL = BASE_URL1 + "Test_connection/HINS_CONNECTION";
    public static final String WAIT_HOLIDAY_URL = BASE_URL1 + "WaitEmpHoliday";
    public static final String CONFIRM_HOLIDAY_URL = WAIT_HOLIDAY_URL + "/confirm_holiday";
    public static final String ESERVICES_URL = BASE_URL1 + "Auth1/login";
    public static final String BACK_HOLIDAY_URL = BASE_URL1 + "BackHolidaysAPI";
    public static final String CONFIRM_BACK_HOLIDAY_URL = BACK_HOLIDAY_URL + "/confirm_back_holiday";
    public static final String HOLIDAY_URL = BASE_URL1 + "HolidayAPI";
    public static final String INSERT_HOLIDAY_URL = HOLIDAY_URL + "/INSERT_HOLIDAY_ANDROID";
    public static final String VIEW_HOLIDAY_URL = HOLIDAY_URL + "/HOLIDAYS_REP";
    public static final String EMP_HOLIDAYS_COUNT_URL = HOLIDAY_URL + "/GET_EMP_HOLIDAYS_COUNT_ANDROID";
    public static final String HINS_URL = BASE_URL1 + "HinsAPI";
    public static final String UPLOAD_HINSPHOTO_DB_URL = HINS_URL + "/uploadImgDB";
    public static final String UPLOAD_HINSPHOTO_FILE_URL = HINS_URL + "/uploadImgFile";
    public static final String HINSPHOTO_URL = BASE_URL1 + "Img/get/";
    public static final String LOGOUT_URL = BASE_URL1 + "Auth1/logout";
    public static final String ATTENDANCE_URL = BASE_URL1 + "AttendanceAPI";
    public static final String FINANCE_URL = BASE_URL1 + "FinanceAPI";
    public static final String SHIFT_URL = BASE_URL1 + "ShiftAPI";
    /////////////////////////////////////////////////////////////////////////////////////////
    public static final String GET_ALL_HOSPITAL_PR = "http://www.moh.gov.ps/moh_client_api/clinic.php?type=GET_ALL_HOSPITAL_PR";
    public static final String GET_ALL_CLASS_PR_BY_HOS = "http://www.moh.gov.ps/moh_client_api/clinic.php?type=GET_ALL_CLASS_PR_BY_HOS&P_HOS_NO=";
    public static final String CLINIC_SCHEDULE_DATA = "http://www.moh.gov.ps/moh_client_api/clinic.php?type=CLINIC_SCHEDULE_DATA";
    ///////////////////////////////////////sso//////////////////////////////////////////////
    public static final String VERIFY_TOKEN_URL = "https://ssoidp.gov.ps/sso/module.php/sspoauth2/verify.php";
    public static final String REFRESH_TOKEN_URL = "https://ssoidp.gov.ps/sso/module.php/sspoauth2/token.php";
    public static ConnectivityManager connectivityManager;
    public static SharedPreferences pref;
    ///////////////////////////////////////////////////////////////////////////////////////////////////////


    //public static final String aaa=" http://develop.nana.sa:2020/get_all_main_categories";
    public static SharedPreferences.Editor editor;
    public static boolean done = false;
    private static AppController mInstance;
    private static Animation animation;
    LruBitmapCache mLruBitmapCache;
    private ImageLoader mImageLoader;
    private RequestQueue mRequestQueue;

    public static LoadingDialog getLoadingDialog(Context con, String msg) {
        LoadingDialog LOADING_DIALOG = new LoadingDialog(con, msg);
        LOADING_DIALOG.setMsg(msg);
        LOADING_DIALOG.setContext(con);
        return LOADING_DIALOG;
    }

    public static ConfirmDialog getConfirmDialog(Context con, String title, String msg) {
        ConfirmDialog CONFIRM_DIALOG = new ConfirmDialog(con, title, msg);
        return CONFIRM_DIALOG;
    }

    public static SharedPreferences getUserPreferences(Context con) {
        SharedPreferences pref = con.getSharedPreferences("userProfile", MODE_PRIVATE);
        return pref;
    }

    public static int getTimerCount(String timerText) {
        return 0;
    }

    public static String getTimerText(int seconds) {
        return "";
    }

    public static boolean IsConnected() {
        connectivityManager = mInstance.getConnectionManager();

        NetworkInfo activeInfo = connectivityManager.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected())
            return true;
        else
            return false;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public static RequestQueue getRequestQueue() {
        if (mInstance.mRequestQueue == null) {
            //  mRequestQueue = Volley.newRequestQueue(getApplicationContext());
            mInstance.mRequestQueue = Volley.newRequestQueue(mInstance.getApplicationContext());
        }

        return mInstance.mRequestQueue;
    }

    /**
     * Using reflection to override default typeface
     * NOTICE: DO NOT FORGET TO SET TYPEFACE FOR APP THEME AS DEFAULT TYPEFACE WHICH WILL BE OVERRIDDEN
     *
     * @param context                    to work with assets
     * @param defaultFontNameToOverride  for example "monospace"
     * @param customFontFileNameInAssets file name of the font from assets
     */
    public static void overrideFont(Context context, String defaultFontNameToOverride, String customFontFileNameInAssets) {
        try {
            final Typeface customFontTypeface = Typeface.createFromAsset(context.getAssets(), customFontFileNameInAssets);

            final Field defaultFontTypefaceField = Typeface.class.getDeclaredField(defaultFontNameToOverride);
            defaultFontTypefaceField.setAccessible(true);
            defaultFontTypefaceField.set(null, customFontTypeface);
        } catch (Exception e) {

        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        pref = getSharedPreferences("userProfile", MODE_PRIVATE);

        editor = pref.edit();
        AppController.overrideFont(getApplicationContext(), "SERIF", "beinnormal.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
    }

    //inilization the ImageLoader Instance
    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            getLruBitmapCache();
            mImageLoader = new ImageLoader(this.mRequestQueue, mLruBitmapCache);
        }
        return this.mImageLoader;
    }

    //inilization the LruBitmapCache Instance for cashing images
    public LruBitmapCache getLruBitmapCache() {
        if (mLruBitmapCache == null)
            mLruBitmapCache = new LruBitmapCache();
        return this.mLruBitmapCache;
    }

    public ConnectivityManager getConnectionManager() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req, int socketTimeout) {
        //int socketTimeout = 303000;//30 seconds - change to what you want
        if (AppController.IsConnected()) {
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            req.setRetryPolicy(policy);
            req.setTag(TAG);
            getRequestQueue().add(req);
        } else {

        }
    }

    public <T> void addToRequestQueue(Request<T> req) {
        //int socketTimeout = 30000;//30 seconds - change to what you want
        if (AppController.IsConnected()) {

            getRequestQueue().add(req);
        } else {

        }
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}