package com.moh.app.broadcastReceiver;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.moh.app.DrawerActivity;

import java.io.File;

import androidx.core.content.FileProvider;

import static com.moh.app.utils.ToolsUtils.grantAllUriPermissions;

public class CheckDownloadComplete extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        String action = intent.getAction();
        if (action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
            DrawerActivity.download_complete = false;
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

                        if (file.equalsIgnoreCase("com.adobe.reader.apk")) {

                            File adobe = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), file);


                            if (adobe.exists()) {
                                //File Exists
                                Log.e("test1", "Exists");

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    path = FileProvider.getUriForFile(context, context.getPackageName() + ".my.package.name.provider", adobe);

                                } else {
                                    path = Uri.fromFile(adobe);
                                }
                                Intent adobe_intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
                                adobe_intent.setDataAndType(path, "application/vnd.android.package-archive");
                                adobe_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                grantAllUriPermissions(context, adobe_intent, path);
                                context.startActivity(adobe_intent);

                            }

                        }

                        if (file.equalsIgnoreCase("Obstetric_protocol.pdf")) {
                            File pdf = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), file);
                            if (pdf.exists()) {
                                //File Exists
                                Log.e("test", "Exists");

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    path = FileProvider.getUriForFile(context, context.getPackageName() + ".my.package.name.provider", pdf);

                                } else {
                                    path = Uri.fromFile(pdf);
                                }
                                Intent pdf_intent = context.getPackageManager().getLaunchIntentForPackage("com.adobe.reader");
                                intent.setDataAndType(path, "application/pdf");
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                grantAllUriPermissions(context, pdf_intent, path);

                                try {
                                    context.startActivity(pdf_intent);
                                } catch (ActivityNotFoundException e) {
                                    Toast.makeText(context, "No Application Available to View PDF", Toast.LENGTH_LONG).show();
                                }
                            }
                        }


                    }


                }
            }

            cursor.close();
        }
    }
}