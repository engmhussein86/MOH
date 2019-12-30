package com.moh.app.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moh.app.models.HinsUser;
import com.moh.app.moh.R;
import com.moh.app.utils.AppController;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by pc-3 on 12/20/2016.
 */
public class HinsAdapter extends RecyclerView.Adapter<HinsAdapter.ViewHolder> {
    private static final int PICK_IMAGE_ID = 234; // the number doesn't matter
    public static int selectedPosition = 0;
    Activity context;
    ArrayList<HinsUser> hinsUsers;
    AlertDialog alertDialog;
    String image_url;

    String encodedImg;

    public HinsAdapter(Activity context, ArrayList<HinsUser> hinsUsers) {
        this.context = context;
        this.hinsUsers = hinsUsers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View vi = inflater.inflate(R.layout.row_hins_item, parent, false);

        ViewHolder holder = new ViewHolder(vi);
        //Log.e("TEST", "End create");
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.e("name", hinsUsers.get(position).getREL_TYPE_DESC());
        holder.FULL_NAME_AR.setText(hinsUsers.get(position).getFULL_NAME_AR());
        holder.ID_NO.setText(hinsUsers.get(position).getID_NO());
        holder.REL_TYPE_DESC.setText(hinsUsers.get(position).getREL_TYPE_DESC());
        holder.image.setImageResource(0);
        Drawable draw = context.getResources().getDrawable(R.drawable.ic_camera_alt_red_48dp);
        draw = resize(draw);
        holder.image.setImageDrawable(draw);
//        holder.image.setImageResource(R.drawable.ic_camera_alt_red_48dp);


        //holder.image.setVisibility(View.GONE);
        //holder.networkImage.setVisibility(View.VISIBLE);

        image_url = AppController.HINSPHOTO_URL + hinsUsers.get(position).getID_NO() + "?random=" + Math.random();
        Log.e("first", image_url);
        Picasso.get().load(image_url).into(holder.image);

//        holder.imageLoader = AppController.getInstance().getImageLoader();
//        holder.imageLoader.get(image_url, ImageLoader.getImageListener(holder.networkImage, R.drawable.ic_camera_alt_red_48dp, R.drawable.ic_camera_alt_red_48dp));
//        holder.networkImage.setImageUrl(image_url, holder.imageLoader);

//        ImageRequest img_req = new ImageRequest(AppController.VIEW_HINSPHOTO_URL, new Response.Listener<Bitmap>() {
//            @Override
//            public void onResponse(Bitmap response) {
//                holder.image.setImageBitmap(response);
//
//            }
//        }, 0, 0, null, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("ERROR", error.toString());
//
//            }
//        }){
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> params = new HashMap<String, String>();
//                params.put("Authorization", hinsUsers.get(position).getID_NO());
//                return params;
//            }
//        };
//
//        AppController.getInstance().addToRequestQueue(img_req);
        if (alertDialog != null && alertDialog.isShowing() && hinsUsers.get(position).isUploaded()) {
            alertDialog.dismiss();
        }

        if (hinsUsers.get(position).getImageFile() != null) {
            Log.e("Mariam_", hinsUsers.get(position).getImageFile());
//            holder.networkImage.setImageResource(R.drawable.ic_camera_alt_red_48dp);
//        } else {
            //holder.networkImage.setImageBitmap(BitmapFactory.decodeFile(hinsUsers.get(position).getImageFile()));
//            holder.image.setVisibility(View.VISIBLE);
//            holder.networkImage.setVisibility(View.GONE);
            holder.image.setImageBitmap(BitmapFactory.decodeFile(hinsUsers.get(position).getImageFile()));

        }

        holder.camerabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                // ...Irrelevant code for customizing the buttons and title

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogView = inflater.inflate(R.layout.alert_dialog_hinsimg, null);
                dialogBuilder.setView(dialogView);

//                final NetworkImageView view_big_img = (NetworkImageView) dialogView.findViewById(R.id.view_big_img);
                ImageView img_dialog = (ImageView) dialogView.findViewById(R.id.result);
                Button ok = (Button) dialogView.findViewById(R.id.ok);
                Button upload = (Button) dialogView.findViewById(R.id.upload);

//                if(hinsUsers.get(position).isUploaded()){
//                    upload.setVisibility(View.GONE);
//                }

                //String image_dialog_url = "http://apps.moh.gov.ps/webarch/uploaded_files/heath_insurance/" + hinsUsers.get(position).getID_NO()+"/"+ hinsUsers.get(position).getID_NO()+".jpg?random="+Math.random();

                if (hinsUsers.get(position).getIMG_APPROVE() == 1) {
                    upload.setVisibility(View.GONE);
                } else {
                    upload.setVisibility(View.VISIBLE);
                }

                String image_dialog_url = AppController.HINSPHOTO_URL + hinsUsers.get(position).getID_NO() + "?random=" + Math.random();
                Picasso.get().load(image_dialog_url).into(img_dialog);

//                view_big_img.invalidate();

                Log.e("TEST", image_dialog_url);
//                holder.imageLoader = AppController.getInstance().getImageLoader();
//                holder.imageLoader.get(image_dialog_url, ImageLoader.getImageListener(view_big_img, R.drawable.ic_camera_alt_red_48dp, R.drawable.ic_camera_alt_red_48dp));
//                view_big_img.setImageUrl(image_dialog_url, holder.imageLoader);


                final AlertDialog alertDialog = dialogBuilder.create();

                alertDialog.show();
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();

                        selectedPosition = position;
//                        Intent chooseImageIntent = ImagePicker.getPickImageIntent(context);
//                        context.startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);

                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        context.startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_ID);
                    }
                });

            }
        });


    }

    @Override
    public int getItemCount() {
        //Log.e("size from adapter", hinsUsers.size() + "");
        return hinsUsers.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    ////////////////////////////////////////////
    private Drawable resize(Drawable image) {
        Bitmap bitmap = ((BitmapDrawable) image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(bitmap,
                (int) (bitmap.getWidth() * 0.5), (int) (bitmap.getHeight() * 0.5), false);
        return new BitmapDrawable(context.getResources(), bitmapResized);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView FULL_NAME_AR, ID_NO, REL_TYPE_DESC;
        LinearLayout camerabtn;
        ImageView image;
//        ImageLoader imageLoader;
//        NetworkImageView networkImage;

        //ImageView upload;

        public ViewHolder(View itemView) {
            super(itemView);

            FULL_NAME_AR = (TextView) itemView.findViewById(R.id.FULL_NAME_AR);
            ID_NO = (TextView) itemView.findViewById(R.id.ID_NO);
            REL_TYPE_DESC = (TextView) itemView.findViewById(R.id.REL_TYPE_DESC);
            camerabtn = (LinearLayout) itemView.findViewById(R.id.camerabtn);
            image = (ImageView) itemView.findViewById(R.id.image);
//            networkImage = (NetworkImageView) itemView.findViewById(R.id.img);

            //upload = (ImageView) itemView.findViewById(R.id.upload);
        }
    }

}
