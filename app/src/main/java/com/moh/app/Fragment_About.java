package com.moh.app;


import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.moh.app.moh.R;

import java.io.File;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import static com.moh.app.utils.ToolsUtils.grantAllUriPermissions;
import static com.moh.app.utils.ToolsUtils.hasWritePermissions;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_About extends Fragment {

    public static final int WRITE_PERMS_REQUEST_CODE = 123;
    TextView version, desc, by, reserved;
    String version_str;
    Button updateApp;


    public Fragment_About() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DrawerActivity.navItemIndex = 9;
        ((DrawerActivity) getActivity()).setToolbarTitle();
        ((DrawerActivity) getActivity()).selectNavMenu();

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/" + getString(R.string.font_name));

        version = (TextView) view.findViewById(R.id.version);
        desc = (TextView) view.findViewById(R.id.desc);
        by = (TextView) view.findViewById(R.id.by);
        reserved = (TextView) view.findViewById(R.id.reserved);
        updateApp = (Button) view.findViewById(R.id.updateApp);

        updateApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_app(getContext());

            }
        });

        PackageManager manager = getContext().getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(getContext().getPackageName(), 0);
            version_str = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        version.setText("الاصدار V " + version_str);

        version.setTypeface(font);
        desc.setTypeface(font);
        by.setTypeface(font);
        reserved.setTypeface(font);


    }

    //////////////////////////////////Mariam/////////////////////////////////
    private void update_app(final Context context) {
        Log.e("Mariam", "123");
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("تحديث التطبيق ");
        alert.setMessage("سيتم حذف التطبيق واعادة تثبيته مرة أخرى , هل أنت موافق؟؟ ");
        alert.setPositiveButton("نعم", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                String filename = "app-debug.apk";

                File apk = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filename);
                if (apk.exists())
                    //file.delete() - test this, I think sometimes it doesnt work
                    apk.delete();


                downloadAPK_install(filename, context);

                dialog.dismiss();
            }
        });

        alert.setNegativeButton("لا", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(context, "no", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

        alert.show();

    }
    //////////////////////////////////////////////////////////////////////////

    private void downloadAPK_install(final String fileName, final Context context) {
        DownloadManager downloadManager;
        Long downloadId = null;
        Uri uri = null;


        if (hasWritePermissions(context)) {
            Log.e("Mariam", "123");
            downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            uri = Uri.parse("http://apps.moh.gov.ps/mohmob/" + fileName);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            downloadId = downloadManager.enqueue(request);

        } else {
            Log.e("Mariam", "456");
            requestWritePerms();
        }

        //set BroadcastReceiver to install app when .apk is downloaded
        final Uri finalUri = uri;
        BroadcastReceiver onComplete = new BroadcastReceiver() {
            public void onReceive(Context ctxt, Intent intent) {
                String action = intent.getAction();
                if (action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0));
                    DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                    Cursor cursor = manager.query(query);
                    if (cursor.moveToFirst()) {
                        if (cursor.getCount() > 0) {

                            int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                            Long download_id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);

                            // status contain Download Status
                            // download_id contain current download reference id

                            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                                String file = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE));
                                Log.e("Mariam", "" + file);
                                Uri path;

                                File apk = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), file);


                                if (apk.exists()) {
                                    //File Exists
                                    Log.e("Mariam", "Exists");

                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        Log.e("Mariam", "> N");

                                        path = FileProvider.getUriForFile(getActivity(), context.getPackageName() + ".my.package.name.provider", apk);

                                    } else {
                                        Log.e("Mariam", "1");
                                        path = Uri.fromFile(apk);
                                    }

                                    Log.e("Mariam", path.toString());

                                    Intent install = new Intent(Intent.ACTION_INSTALL_PACKAGE);
                                    install.setDataAndType(path, "application/vnd.android.package-archive");
//                                    Intent install = new Intent(Intent.ACTION_VIEW);
//                                    install.setDataAndType(finalUri,manager.getMimeTypeForDownloadedFile(download_id));
                                    install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    grantAllUriPermissions(context, install, path);
                                    context.startActivity(install);
                                }
                            }
                        }

                    }
                }
            }
        };

        //register receiver for when .apk download is compete
        getActivity().registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    private void requestWritePerms() {
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(getActivity(), permissions, WRITE_PERMS_REQUEST_CODE);
        }
    }
}
