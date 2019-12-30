package com.moh.app;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.moh.app.moh.R;

import androidx.appcompat.app.AppCompatActivity;
import io.fabric.sdk.android.Fabric;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);


//        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppController.aaa,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
//                        Log.e("onResponse","hi="+response);
//                        JSONArray person = null;
//
//                        try {
//                            person = new JSONArray(response);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
//                    }
//                }){
//            @Override
//            protected Map<String,String> getParams(){
//                Map<String,String> params = new HashMap<String, String>();
//                return params;
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json");
//                headers.put("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IkNTVDAwMDI1ODAwIiwic3RhdHVzIjoiYWN0aXZlIiwiaWF0IjoxNDc0MzUxOTIzLCJpc3MiOiJDU1QwMDAyNTgwMCJ9.MwoO3QDU7QiIkK4boK9IhiS4Jb4JKMdBYiNbsNA2A50");
//                Log.e("onResponse",headers.toString());
//                return headers;
//                //return super.getHeaders();
//            }
//        };
//        AppController.getInstance().addToRequestQueue(stringRequest, 30000);
//


        //ActionBar actionBar = getSupportActionBar();
// hide the action bar
        //actionBar.hide();

        /*RotateAnimation anim = new RotateAnimation(0f, 350f, 15f, 15f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(700);

// Start animating the image*/
        Typeface font_bold = Typeface.createFromAsset(getAssets(), "fonts/" + getString(R.string.font_name_bold));
        TextView textView = (TextView) findViewById(R.id.textView);
        TextView textView1 = (TextView) findViewById(R.id.textView1);
        textView.setTypeface(font_bold);
        textView1.setTypeface(font_bold);

        final ImageView splash = (ImageView) findViewById(R.id.splash);
        /*splash.startAnimation(anim);

// Later.. stop the animation
        splash.setAnimation(null);*/

//        ImageView image = (ImageView)findViewById(R.id.splash);
//        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_centre);
//        image.startAnimation(animation);
//

        ObjectAnimator animation = ObjectAnimator.ofFloat(splash, "rotationY", 0.0f, 360f);
        animation.setDuration(6000);
        animation.setRepeatCount(ObjectAnimator.INFINITE);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.start();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                splash.this.finish();
                //Toast.makeText(splash.this, "START ACTIVITY NEWS", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(splash.this, DrawerActivity.class);
                startActivity(i);
            }
        }, 3000);


    }
}
