package com.moh.app.news;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.moh.app.moh.R;
import com.moh.app.utils.AppController;

import androidx.appcompat.app.AppCompatActivity;


public class ContentActivity extends AppCompatActivity {

    TextView guid, post_title, post_content, post_date;
    private NetworkImageView imageView;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        // get action bar
        //ActionBar actionBar = getActionBar();

        // Enabling Up / Back navigation
        //actionBar.setDisplayHomeAsUpEnabled(true);

        guid = (TextView) findViewById(R.id.guid);
        post_title = (TextView) findViewById(R.id.post_title);
        post_content = (TextView) findViewById(R.id.post_content);
        post_date = (TextView) findViewById(R.id.post_date);
        imageView = (NetworkImageView) findViewById(R.id.img);

        Typeface font_bold = Typeface.createFromAsset(getAssets(), "fonts/" + getString(R.string.font_name_bold));
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/" + getString(R.string.font_name));
        guid.setTypeface(font);
        post_title.setTypeface(font_bold);
        post_content.setTypeface(font);
        post_date.setTypeface(font);


        //guid.setText(getIntent().getExtras().getString("guid"));
        post_title.setText(getIntent().getExtras().getString("post_title"));
        post_content.setText(getIntent().getExtras().getString("post_content"));
        post_date.setText(getIntent().getExtras().getString("post_date"));

//        String imageName = getIntent().getExtras().getString("image_url");
//        imageName = imageName.replaceAll("http://www.moh.gov.ps/portal/wp-content/uploads/", "");
//
//        String encodedURL = "http://www.moh.gov.ps/portal/wp-content/uploads/" + Uri.encode(imageName);

        String img_url = getIntent().getExtras().getString("image_url");
        String encodedURL;

        Log.e("error", img_url + "nnn");

        if (!img_url.equalsIgnoreCase("null")) {
            String[] url_parts = img_url.split("/");
            Log.e("error", " " + url_parts.length);

            String imageName = url_parts[url_parts.length - 1];

            encodedURL = img_url.substring(0, img_url.lastIndexOf("/")) + "/" + Uri.encode(imageName);
        } else {
            encodedURL = "null";
        }

        imageLoader = AppController.getInstance().getImageLoader();
        imageLoader.get(encodedURL, ImageLoader.getImageListener(imageView, R.drawable.moh_default, R.drawable.moh_default));
        imageView.setImageUrl(encodedURL, imageLoader);

        guid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getIntent().getExtras().getString("guid")));
                startActivity(browserIntent);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContentActivity.this, FullImgActivity.class);
                intent.putExtra("image_url", getIntent().getExtras().getString("image_url"));
                intent.putExtra("post_title", getIntent().getExtras().getString("post_title"));
                startActivity(intent);
            }
        });


    }


}
