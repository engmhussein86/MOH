package com.moh.app;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.navigation.NavigationView;
import com.moh.app.adapters.EservicesAdapter;
import com.moh.app.adapters.HinsAdapter;
import com.moh.app.eservices.HinsFragment;
import com.moh.app.moh.R;
import com.moh.app.news.FragNews;
import com.moh.app.phone.FragPhones;
import com.moh.app.utils.AppController;
import com.moh.app.utils.ImagePicker;
import com.moh.app.utils.ToolsUtils;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import ps.gov.mtit.ssologin.SSOHelper;

public class DrawerActivity extends AppCompatActivity {

    // tags used to attach the fragments
    private static final String TAG_GRID = "grid";
    private static final String TAG_HOME = "home";
    private static final String TAG_NEWS = "news";
    private static final String TAG_PHONES = "phones";
    //private FloatingActionButton fab;

    // urls to load navigation header background image
    // and profile image
    private static final String TAG_CONTACTUS = "contactus";

    //private Fragment fragment;
    private static final String TAG_ESERVICES = "eservices";
    private static final String TAG_GRID_ESERVICES = "grid_eservices";
    private static final String TAG_HINS = "hins";
    private static final String TAG_FINANCE = "finance";
    private static final String TAG_ABOUT = "about";
    //    public static LoadingDialog loader_dialog;
    public static NavigationView navigationView;
    public static boolean download_complete = false;
    // index to identify current nav menu item
    public static int navItemIndex;
    public static String CURRENT_TAG = TAG_GRID;
    // toolbar titles respected to selected nav menu item
    public static String[] activityTitles;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    ImageLoader imageLoader;
    private DrawerLayout drawer;
    //    private LoadingDialog loaderDialog;
    //    private View navHeader;
//    private ImageView imgNavHeaderBg, imgProfile;
//    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    //////////////////////////////////////////////////////////////////
    public static boolean contains(int[] arr, int item) {
        for (int n : arr) {
            if (item == n) {
                return true;
            }
        }
        return false;
    }

    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
//    private void loadNavHeader() {
//        // name, website
//        txtName.setText("Ravi Tamada");
//        txtWebsite.setText("www.androidhive.info");
//
//        // loading header background image
//        Glide.with(this).load(urlNavHeaderBg)
//                .crossFade()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(imgNavHeaderBg);
//
//        // Loading profile image
//        Glide.with(this).load(urlProfileImg)
//                .crossFade()
//                .thumbnail(0.5f)
//                .bitmapTransform(new CircleTransform(this))
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(imgProfile);
//
//        // showing dot next to notifications label
//        navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();

//        loaderDialog = AppController.getLoadingDialog(getApplicationContext(), "");

        setContentView(R.layout.activity_drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();

        //loader_dialog = AppController.getLoadingDialog(this, "");
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        //fab = (FloatingActionButton) findViewById(R.id.fab);

        // Navigation view header
        //navHeader = navigationView.getHeaderView(0);
        //txtName = (TextView) navHeader.findViewById(R.id.name);
        //txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        //imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        //imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        // load nav menu header data
        //loadNavHeader();


        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        Log.d("load", CURRENT_TAG);
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            Log.d("load", "return nothing");
            drawer.closeDrawers();

            // show or hide the fab button
            // toggleFab();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        // toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {

        String HolidayNotify = "";
        int fragNotify = 0;

        Intent intent = this.getIntent();
        if (intent != null)
            HolidayNotify = intent.getStringExtra("HolidayNotify");


        if (HolidayNotify != null && !HolidayNotify.isEmpty() && HolidayNotify.equalsIgnoreCase("1")) {
            navItemIndex = 5;
            fragNotify = 1;
            Log.e("navItemIndex", navItemIndex + "");

        }

        Log.d("dd", "getHomeFragment" + navItemIndex);


        switch (navItemIndex) {
            case 0:
                // home
                Log.d("dd", "case 0" + navItemIndex);
                return new Fragment_ApplicationGrid();

            case 1:
                // photos
                Log.d("dd", "case 1" + navItemIndex);
                return new FragNews();


            case 2:
                //phones

                Log.d("dd", "case 2" + navItemIndex);
                return new FragPhones();
            case 3:
                // contact us
                return new ContactFragment();


            case 4:
                return new Fragment_Login();

            case 5:

                Fragment_EservicesGrid frag = new Fragment_EservicesGrid();

                Log.e("fragNotify", fragNotify + "");

                Bundle bundle = new Bundle();
                bundle.putInt("notify", fragNotify);
                frag.setArguments(getIntent().getExtras());

                return frag;


            case 9:
                return new Fragment_About();


            default:
                return new Fragment_ApplicationGrid();
        }


    }

    public void setToolbarTitle() {
        Log.d("dd", "setToolbarTitle" + navItemIndex);
        //if(navItemIndex != 8)
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    public void selectNavMenu() {
        if (navItemIndex < 5) {
            Log.d("dd", "select" + navItemIndex);
            navigationView.getMenu().getItem(navItemIndex).setChecked(true);
        } else if (navItemIndex == 9) {//about
            navigationView.getMenu().getItem(5).setChecked(true);
        } else {//ssologin
            Log.d("dd", "select 4");
            navigationView.getMenu().getItem(4).setChecked(true);
        }
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_news:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_NEWS;
                        break;
                    case R.id.nav_phones:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_PHONES;
                        break;

                    case R.id.nav_contactus:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_CONTACTUS;
                        break;

                    case R.id.nav_eservices:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_ESERVICES;
                        break;
                    case R.id.nav_exit:
                        exitApp();
                        break;
                    case R.id.nav_about:
                        navItemIndex = 9;
                        CURRENT_TAG = TAG_ABOUT;
                        break;
                    //case R.id.nav_about_us:
                    // launch new intent instead of loading fragment
//                        Log.d("xx","ss");
//                        startActivity(new Intent(getApplicationContext(), Login.class));
//                        drawer.closeDrawers();
                    //return true;

                    default:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {

        //loader_dialog.hideDialog();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            int[] basic_navindex = {1, 2, 3, 4, 5, 9, 17, 18};

            if (navItemIndex == 0) {
                exitApp();
            } else if (contains(basic_navindex, navItemIndex)) {
                Log.d("page", "basic");
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            } else {
                Log.d("page", "eservices");
                navItemIndex = 5;
                CURRENT_TAG = TAG_GRID_ESERVICES;
                loadHomeFragment();
                return;

            }
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when home fragment is selected
        if (navItemIndex == 0) {
            getMenuInflater().inflate(R.menu.main, menu);
        }

        // when fragment is news, load the menu created for news
        if (navItemIndex == 1) {
            getMenuInflater().inflate(R.menu.activity_main_actions, menu);
        }
        return true;
    }

//    // show or hide the fab
//    private void toggleFab() {
//        if (navItemIndex == 0)
//            fab.show();
//        else
//            fab.hide();
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {

            if (ToolsUtils.isConnectingToInternet(getApplicationContext())) {

                Toast.makeText(getApplicationContext(), "جاري تسجيل الخروج", Toast.LENGTH_LONG).show();
                final String user_id = Fragment_Login.sharedPref.getString("USER_ID", null);
                final String token = Fragment_Login.sharedPref.getString("Token", null);
                //////////////////////////// delete token /////////////////////////////////////////////
                StringRequest fcmRequest = new StringRequest(Request.Method.POST, AppController.DELETE_TOKEN_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response", response);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("onErrorResponse", "error : " + error.getMessage());
                        Toast.makeText(getApplicationContext(), "حدث خلل في الاتصال  ", Toast.LENGTH_LONG).show();


                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("id", user_id);

                        Log.e("params", params.toString());
                        return params;
                    }
                };

                AppController.getInstance().addToRequestQueue(fcmRequest, 30000);

                ///////////////////////////////////////////////////////////////////////////////////////
                //////////////////////////// logoutRequest /////////////////////////////////////////////
                StringRequest logoutRequest = new StringRequest(Request.Method.POST, AppController.LOGOUT_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response", response);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("onErrorResponse", "error : " + error.getMessage());
                        Toast.makeText(getApplicationContext(), "حدث خلل في الاتصال  ", Toast.LENGTH_LONG).show();


                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("User-ID", user_id);
                        headers.put("Authorization", token);
                        Log.e("onResponse", headers.toString());
                        return headers;
                        //return super.getHeaders();
                    }
                };

                AppController.getInstance().addToRequestQueue(logoutRequest, 30000);

                ///////////////////////////////////////////////////////////////////////////////////////
                clearPref();
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();

                return true;
            } else {

                Toast.makeText(getApplicationContext(), "تعذر الاتصال بالانترنت ", Toast.LENGTH_LONG).show();

            }
        }


        return super.onOptionsItemSelected(item);
    }

    //////////////////////////////////////////////////////////////////
    public void exitApp() {
        finish();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
        onDestroy();

    }

    ///////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    public void clearPref() {

        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

    }
    ///////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////for Hins Photo Adapter ///////////////////////////////////

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != Activity.RESULT_CANCELED) {
            Log.e("Mariam_error", "jjjjj onActivityResult");

            //ProgressDialog prg =  new ProgressDialog(DrawerActivity.this);

            switch (requestCode) {
                case SSOHelper.SSO_LOGIN:
                    if (resultCode == RESULT_OK) {
                        if (data != null && data.getExtras() != null) {
                            final String token = data.getStringExtra("SSOTOKEN");
                            final String refreshToken = data.getStringExtra("SSOREFRESHTOKEN");

                            Log.e("Mariam_token", token + "---" + refreshToken);

//CONTINUE YOUR WORK WITH THE TOKEN AND THE REFRESH TOKEN
// FOR THE VERIFY AND REFRESH URLS CHECK BELOW

                            StringRequest verify_token = new StringRequest(Request.Method.GET, AppController.VERIFY_TOKEN_URL, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.e("Mariam_response", response);

                                    try {
                                        if (response != null) {
                                            final JSONObject jobj = new JSONObject(response);
                                            Log.e("Mariam_status", jobj.get("user_id").toString());


                                            if (jobj.getString("message").equalsIgnoreCase("Token is valid")) {
                                                editor.putBoolean("valid_token", true);
                                            } else {
                                                editor.putBoolean("valid_token", false);
                                            }
                                            editor.commit();
                                            Log.e("Mariam_response", sharedPref.getBoolean("valid_token", false) + "1123");


                                            if (sharedPref.getBoolean("valid_token", false)) {
                                                Log.e("Mariam_response", "11");
                                                editor.putBoolean("isLogged", true);
                                                editor.putString("USER_ID", jobj.getString("user_id"));
                                                editor.putString("Token", token);
                                                editor.putString("refreshToken", refreshToken);
                                                editor.putString("USER_FULL_NAME", jobj.getString("name"));

                                                if (jobj.getString("account_type").equalsIgnoreCase("1") && jobj.getString("ministry_cd").equalsIgnoreCase("190")) {
                                                    editor.putString("isEmp", "1");
                                                } else {
                                                    editor.putString("isEmp", "0");
                                                }

                                                editor.commit();

                                                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                                                        android.R.anim.fade_out);
                                                fragmentTransaction.replace(R.id.frame, new Fragment_Login());
                                                fragmentTransaction.commitAllowingStateLoss();


                                            }

//                                            if(sharedPref.getBoolean("isLogged", false))
//                                            {
//                                                checkToken(DrawerActivity.this,token,sharedPref.getString("USER_ID", null));
//
//                                                Fragment_EservicesGrid applicationGrid = new Fragment_EservicesGrid();
//                                                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                                                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
//                                                        android.R.anim.fade_out);
//                                                fragmentTransaction.replace(R.id.frame, applicationGrid);
//                                                fragmentTransaction.commitAllowingStateLoss();
//
//                                            }
//                                            else{
//                                                Toast.makeText(DrawerActivity.this, "خطأ في اسم المستخدم أو كلمة المرور", Toast.LENGTH_LONG).show();
//                                                editor.putBoolean("isLogged", false);
//                                                editor.commit();
//                                            }


                                        } else {
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
//                                loader_dialog.hideDialog();
                                    Toast.makeText(DrawerActivity.this, "حدث خلل في الاتصال  ", Toast.LENGTH_LONG).show();

                                }
                            }) {
                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    HashMap<String, String> headers = new HashMap<String, String>();
                                    headers.put("x-sso-authorization", token);
                                    Log.e("onResponse", headers.toString());
                                    return headers;
                                    //return super.getHeaders();
                                }
                            };

                            AppController.getInstance().addToRequestQueue(verify_token, 30000);
                            //////////////////////////////////////////////////////////////////

                        } else {

                            Toast.makeText(DrawerActivity.this, "تعذر الاتصال بالانترنت ", Toast.LENGTH_LONG).show();
//                        loader_dialog.hideDialog();
//                            pDialog.dismiss();

                        }


                    }
                    break;

                case HinsFragment.PICK_IMAGE_ID:

//                prg.setTitle("تحميل ");
//                prg.setMessage("جاري التحميل .....");
//                prg.show();


                    Log.e("error", "jjjjj");
                    String filePath = ImagePicker.getImageFromResultString(getApplicationContext(), resultCode, data);
                    Log.e("error", filePath);
                    HinsFragment.hinsUsers.get(HinsAdapter.selectedPosition).setImageFile(filePath);
                    HinsFragment.hinsUsers.get(HinsAdapter.selectedPosition).setUploaded(true);


                    new UploadImage(BitmapFactory.decodeFile(HinsFragment.hinsUsers.get(HinsAdapter.selectedPosition).getImageFile()), HinsFragment.hinsUsers.get(HinsAdapter.selectedPosition).getID_NO()).execute();

                    //prg.dismiss();


                    break;
                default:
                    super.onActivityResult(requestCode, resultCode, data);
                    break;
            }


        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean Write_allowed = false;
        boolean Install_allowed = false;

        switch (requestCode) {
            case EservicesAdapter.WRITE_PERMS_REQUEST_CODE:

                for (int res : grantResults) {
                    // if user granted all permissions.
                    Log.e("case", "Write_allowed");
                    Write_allowed = (res == PackageManager.PERMISSION_GRANTED);
                }

                break;

            case EservicesAdapter.INSTALL_PERMS_REQUEST_CODE:

                for (int res : grantResults) {
                    // if user granted all permissions.
                    Log.e("case", "Install_allowed");
                    Log.e("res", "" + res);
                    Log.e("PackageManager", "" + PackageManager.PERMISSION_GRANTED);
                    Install_allowed = (res == PackageManager.PERMISSION_GRANTED);
                    Log.e("Install_allowed", "" + Install_allowed);
                }

                break;

            default:
                // if user not granted permissions.
                Write_allowed = false;
                Install_allowed = false;
                break;
        }

        Log.e("Write_allowed", "" + Write_allowed);
        Log.e("Install_allowed", "" + Install_allowed);

        if (Write_allowed) {
            //user granted all permissions we can perform our task.
            ToolsUtils.makeFolder(EservicesAdapter.dirPath);
        } else {
            // we will give warning to user that they haven't granted permissions.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(this, "Storage Permissions denied.", Toast.LENGTH_SHORT).show();
                }

            }
        }

        if (Install_allowed) {
            //user granted all permissions we can perform our task.
            Log.e("Install_allowed", "true");
            //install_pkg();
        } else {
            // we will give warning to user that they haven't granted permissions.
            Log.e("Install_allowed", "false");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.INSTALL_PACKAGES)) {
                    Toast.makeText(this, "Install Permissions denied.", Toast.LENGTH_SHORT).show();
                }

            }
        }

    }

    private HttpParams getHttpRequestParams() {
        HttpParams HttpRequestParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(HttpRequestParams, 1000 * 30);
        HttpConnectionParams.setSoTimeout(HttpRequestParams, 1000 * 30);

        return HttpRequestParams;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    private class UploadImage extends AsyncTask<Void, Void, Void> {
        Bitmap image;
        String name;
        ProgressDialog prg;


        public UploadImage(Bitmap image, String name) {
            Log.e("ERROR", name);
            this.image = Bitmap.createScaledBitmap(image, 128, 160, true);//resize image to make it smaller
            this.name = name;
            //prg = new ProgressDialog(DrawerActivity.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            prg = new ProgressDialog(DrawerActivity.this);
            prg.setMessage("جاري تحميل الصورة...");
            prg.setIndeterminate(false);
            prg.setCancelable(false);
            prg.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            prg.dismiss();

            if (ToolsUtils.isConnectingToInternet(getApplicationContext())) {

                StringRequest uploadImgRequest = new StringRequest(Request.Method.POST, AppController.UPLOAD_HINSPHOTO_DB_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response", response);
                        if (response != null) {
                            try {
                                JSONObject jobj = new JSONObject(response);
                                Log.e("Status", jobj.getString("result").toString());

                                if (jobj.getString("result").toString().equalsIgnoreCase("success")) {
                                    Toast.makeText(getApplicationContext(), "تم تحميل الصورة....", Toast.LENGTH_SHORT).show();

                                    HinsFragment.hinsAdapter.notifyItemChanged(HinsAdapter.selectedPosition);


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
                        Toast.makeText(getApplicationContext(), "حدث خلل في الاتصال  ", Toast.LENGTH_LONG).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        String p = "{\"id\":" + Fragment_Login.sharedPref.getString("USER_ID", null) + ",\"pid\":" + HinsFragment.hinsUsers.get(HinsAdapter.selectedPosition).getID_NO() + ",\"tp\":" + HinsFragment.hinsUsers.get(HinsAdapter.selectedPosition).getIsDep() + "}";


                        Map<String, String> params = new HashMap<String, String>();
                        params.put("p", p);
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

                AppController.getInstance().addToRequestQueue(uploadImgRequest, 30000);
            } else {

                Toast.makeText(getApplicationContext(), "تعذر الاتصال بالانترنت ", Toast.LENGTH_LONG).show();


            }

            //prg.dismiss();

//            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DrawerActivity.this);
//            // ...Irrelevant code for customizing the buttons and title
//
//            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View dialogView = inflater.inflate(R.layout.alert_dialog_hinsimg, null);
//            dialogBuilder.setView(dialogView);
//
////            NetworkImageView view_big_img = (NetworkImageView) dialogView.findViewById(R.id.view_big_img);
//            ImageView img = (ImageView)dialogView.findViewById(R.id.result);
//            Button ok = (Button) dialogView.findViewById(R.id.ok);
//            Button upload = (Button) dialogView.findViewById(R.id.upload);
//
////            String image_dialog_url = AppController.BASE_URL+ "photo/" + name+".jpg";
////
////            Log.e("TEST", image_dialog_url);
////            imageLoader = AppController.getInstance().getImageLoader();
////            imageLoader.get(image_dialog_url, ImageLoader.getImageListener(view_big_img, R.drawable.ic_camera_alt_red_48dp, R.drawable.ic_camera_alt_red_48dp));
////            view_big_img.setImageUrl(image_dialog_url, imageLoader);
//              Log.e("image",image.toString());
////              view_big_img.setVisibility(View.GONE);
//              img.setVisibility(View.VISIBLE);
//              img.setImageBitmap(Bitmap.createBitmap(image));
//
//
//
//            final AlertDialog alertDialog = dialogBuilder.create();
//            alertDialog.show();
//            ok.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    alertDialog.dismiss();
//                }
//            });
//
//            upload.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    alertDialog.dismiss();
//
//                    Intent chooseImageIntent = ImagePicker.getPickImageIntent(getApplicationContext());
//                    startActivityForResult(chooseImageIntent, HinsFragment.PICK_IMAGE_ID);
//                }
//            });

        }

        @Override
        protected Void doInBackground(Void... voids) {

            //prg.setTitle("تحميل ");
            //prg.setMessage("جاري التحميل .....");
            //prg.show();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 50, stream);

            String encodedImage = Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);
            //Log.e("encodedImage", encodedImage);

            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("image", encodedImage));
            dataToSend.add(new BasicNameValuePair("name", name));

//            Map<String, String> dataToSend = new HashMap<>();
//            dataToSend.put("image",encodedImage);
//            dataToSend.put("name",name);
//
//            Log.e("dataToSend",dataToSend.toString());

            HttpParams HttpRequestParams = getHttpRequestParams();
            HttpClient client = new DefaultHttpClient(HttpRequestParams);
            HttpPost post = new HttpPost(AppController.UPLOAD_HINSPHOTO_FILE_URL);
            //Log.e("url", AppController.HINSPHOTO_URL);

            try {
                post.setEntity(new UrlEncodedFormEntity((List<? extends NameValuePair>) dataToSend));
                client.execute(post);

            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }
    }
}
