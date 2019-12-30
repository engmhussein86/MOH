package com.moh.app.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.moh.app.models.NewsClass;
import com.moh.app.moh.R;
import com.moh.app.news.ContentActivity;
import com.moh.app.news.FullImgActivity;
import com.moh.app.utils.AppController;

import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by pc-3 on 7/24/2016.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    static Context context;
    ArrayList<NewsClass> News;
    int lastPosition = -1;

    public NewsAdapter(Context context, ArrayList<NewsClass> News) {
        this.context = context;
        this.News = News;
        //Log.e("TEST", "Cons");
    }

    public ArrayList<NewsClass> getNews() {
        return News;
    }

    public void setArrayList(ArrayList<NewsClass> arr) {
        this.News = arr;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Log.e("TEST", "In create");
        LayoutInflater inflater = LayoutInflater.from(context);
        View vi = inflater.inflate(R.layout.row_news, parent, false);
        //WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        //int width = windowManager.getDefaultDisplay().getWidth();
//        //int height = windowManager.getDefaultDisplay().getHeight();
        //vi.setLayoutParams(new RecyclerView.LayoutParams(width, RecyclerView.LayoutParams.WRAP_CONTENT));

        ViewHolder holder = new ViewHolder(vi);
        //Log.e("TEST", "End create");
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.post_date.setText(News.get(position).getPost_date());
        holder.post_title.setText(News.get(position).getPost_title());

        holder.post_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ContentActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("post_date", News.get(position).getPost_date());
                intent.putExtra("guid", News.get(position).getGuid());
                intent.putExtra("post_title", News.get(position).getPost_title());
                intent.putExtra("image_url", News.get(position).getImage_url());
                intent.putExtra("post_content", News.get(position).getPost_content());
                context.startActivity(intent);

            }
        });

        String img_url = News.get(position).getImage_url();
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

//        String imageName = News.get(position).getImage_url().trim();
//        imageName = imageName.replaceAll("http://www.moh.gov.ps/portal/wp-content/uploads/", "");
//
//        String encodedURL = "http://www.moh.gov.ps/portal/wp-content/uploads/" + Uri.encode(imageName);

        holder.imageLoader = AppController.getInstance().getImageLoader();
        holder.imageLoader.get(encodedURL, ImageLoader.getImageListener(holder.imageView, R.drawable.moh_default, R.drawable.moh_default));
        holder.imageView.setImageUrl(encodedURL, holder.imageLoader);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FullImgActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("image_url", News.get(position).getImage_url());
                intent.putExtra("post_title", News.get(position).getPost_title());
                context.startActivity(intent);
            }
        });
        //setAnimation(holder.cv, position);

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                sharingIntent.setType("text/plain");
                String shareBody = News.get(position).getGuid() + " " + News.get(position).getPost_content();
                String subject = News.get(position).getPost_title();
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                Intent new_intent = Intent.createChooser(sharingIntent, "Share via");
                new_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(new_intent);
            }
        });

//        holder.shareImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//                sharingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                sharingIntent.setType("text/plain");
//                String shareBody = News.get(position).getPost_content();
//                String subject =  News.get(position).getPost_title();
//                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,subject);
//                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
//                Intent new_intent = Intent.createChooser(sharingIntent, "Share via");
//                new_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(new_intent);
//
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return News.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView post_date, guid, post_title, post_content;
        NetworkImageView imageView;
        ImageLoader imageLoader;
        ImageButton shareImg;
        LinearLayout share;


        public ViewHolder(View itemView) {
            super(itemView);

            cv = (CardView) itemView.findViewById(R.id.cv);
            post_date = (TextView) itemView.findViewById(R.id.post_date);
            post_title = (TextView) itemView.findViewById(R.id.post_title);
            imageView = (NetworkImageView) itemView.findViewById(R.id.img);
            shareImg = (ImageButton) itemView.findViewById(R.id.sharebtn);
            share = (LinearLayout) itemView.findViewById(R.id.share);

            Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/" + context.getString(R.string.font_name));
            post_title.setTypeface(font);


        }
    }


}
