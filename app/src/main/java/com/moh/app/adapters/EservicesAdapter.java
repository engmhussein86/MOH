package com.moh.app.adapters;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.moh.app.DrawerActivity;
import com.moh.app.Fragment_EservicesGrid;
import com.moh.app.eservices.AttendanceFragment;
import com.moh.app.eservices.FinanceFragment;
import com.moh.app.eservices.HinsFragment;
import com.moh.app.eservices.HolidayFragment;
import com.moh.app.eservices.InjuriesFragment;
import com.moh.app.eservices.LabOrderFragment;
import com.moh.app.eservices.OperationFragment;
import com.moh.app.eservices.OutPatientClinicFragment;
import com.moh.app.eservices.ShiftFragment;
import com.moh.app.eservices.TestConnectionFragment;
import com.moh.app.moh.R;
import com.moh.app.utils.ToolsUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import static com.moh.app.utils.ToolsUtils.grantAllUriPermissions;

/**
 * Created by pc-3 on 12/7/2016.
 */
public class EservicesAdapter extends BaseAdapter {

    public static final int WRITE_PERMS_REQUEST_CODE = 123;
    public static final int INSTALL_PERMS_REQUEST_CODE = 456;
    public static String dirPath;
    String[] Application_Names;
    int[] Application_Icons;
    Intent intent;
    String HolidayNotify;
    DownloadManager downloadManager;
    private Context mContext;
    private Activity mAct;


    public EservicesAdapter(Activity mAct, Context mContext, String[] Application_Names, int[] Application_Icons, String HolidayNotify) {
        this.mContext = mContext;
        this.Application_Icons = Application_Icons;
        this.Application_Names = Application_Names;
        this.mAct = mAct;
        this.HolidayNotify = HolidayNotify;

    }

    @Override
    public int getCount() {
        return Application_Icons.length;
    }

    @Override
    public Object getItem(int position) {
        return Application_Icons[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Typeface font_bold = Typeface.createFromAsset(mContext.getAssets(), "fonts/" + mContext.getString(R.string.font_name_bold));
        final Holder holder = new Holder();
        View rowView, drawerView;

        LayoutInflater inflater = LayoutInflater.from(mContext);
        rowView = inflater.inflate(R.layout.row_application_item, null);
        holder.tv = (TextView) rowView.findViewById(R.id.textView1);
        holder.img = (ImageView) rowView.findViewById(R.id.imageView1);

        holder.tv.setTypeface(font_bold);


        holder.tv.setText(Application_Names[position]);
        holder.img.setImageResource(Application_Icons[position]);

        rowView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Toast.makeText(mContext, "You Clicked "+Application_Names[position], Toast.LENGTH_LONG).show();

                DrawerActivity act = (DrawerActivity) mAct;
                FragmentTransaction fragmentTransaction = act.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);


                if (holder.tv.getText().toString().equalsIgnoreCase("بروتوكول الولادة")) {//Palestine Obstetric Protocol
                    view_protocol(mContext);

                } else {
                    Fragment frag = loadFragment(holder.tv.getText().toString());
//                        Fragment_EservicesGrid applicationGrid = new Fragment_EservicesGrid();
                    fragmentTransaction.replace(R.id.frame, frag);

                }


//                switch(position){
//
//                    case 0:
//
//                        HinsFragment mFragHins = new HinsFragment();
//                        fragmentTransaction.replace(R.id.frame, mFragHins);
//
//                        break;
//
//
//                    case 1:
//
//                        FinanceFragment mFragFin= new FinanceFragment();
//                        fragmentTransaction.replace(R.id.frame, mFragFin);
//
//                        break;
//
//                    case 2:
//
//                        OperationFragment mFragOperation = new OperationFragment();
//                        fragmentTransaction.replace(R.id.frame, mFragOperation);
//
//                        break;
//                    case 3:
//                        OutPatientClinicFragment mFragOutClinic = new OutPatientClinicFragment();
//                        fragmentTransaction.replace(R.id.frame, mFragOutClinic);
//
//                        break;
//
//                    case 4:
//
//                        AttendanceFragment mFragAttn = new AttendanceFragment();
//                        fragmentTransaction.replace(R.id.frame, mFragAttn);
//
//                        break;
//                    case 5:
//                        HolidayFragment mFragHoliday = new HolidayFragment();
//                        fragmentTransaction.replace(R.id.frame, mFragHoliday);
//
//                        break;
//                    case 6:
//                        ShiftFragment mFragShift = new ShiftFragment();
//                        fragmentTransaction.replace(R.id.frame, mFragShift);
//
//                        break;
//
//                    case 7://Palestine Obstetric Protocol
//                        view_protocol(mContext);
//
//                        break;
//
////                    case 8:
////
////                        InjuriesFragment mFragInjuries = new InjuriesFragment();
////                        fragmentTransaction.replace(R.id.frame, mFragInjuries);
////
////                        break;
////
////                    case 9:
////
////                        TestConnectionFragment mFragTestConn = new TestConnectionFragment();
////                        fragmentTransaction.replace(R.id.frame, mFragTestConn);
//
//
////                        Fragment_Login frag_login = new Fragment_Login();
////                        fragmentTransaction.replace(R.id.frame, frag_login, "A");
////                        break;
//
//                    default:
//                        Fragment frag = loadFragment(holder.tv.getText().toString());
////                        Fragment_EservicesGrid applicationGrid = new Fragment_EservicesGrid();
//                        fragmentTransaction.replace(R.id.frame, frag);
//
//
//                }

                //fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commitAllowingStateLoss();


            }
        });

        return rowView;
    }

    private Fragment loadFragment(String text) {
        Log.e("Mariam_frag", text);

        switch (text) {
            case "التأمين الصحي":
                return new HinsFragment();
            case "الالتزامات المالية":
                return new FinanceFragment();
            case "مواعيد العمليات":
                return new OperationFragment();
            case "مواعيد العيادات الخارجية":
                return new OutPatientClinicFragment();
            case "الدوام":
                return new AttendanceFragment();
            case "الاجازة الالكترونية":
                return new HolidayFragment();
            case "الورديات":
                return new ShiftFragment();
            case "فحص الاتصال":
                return new TestConnectionFragment();
            case "الاصابات":
                return new InjuriesFragment();
            case "التحاليل المخبرية":
                return new LabOrderFragment();
            default:
                return new Fragment_EservicesGrid();

        }
    }

    private void view_protocol(final Context context) {
        PackageManager pm = context.getPackageManager();
        boolean isInstalled = ToolsUtils.isPackageInstalled("com.adobe.reader", pm);

        if (!isInstalled) {
            Toast.makeText(context, "not installed", Toast.LENGTH_LONG).show();

            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setTitle("تثبيت تطبيق اضافي ");
            alert.setMessage("سيتم تثبيت تطبيق acrobat reader للاستفادة من جميع المزايا الموجودة في الملف , هل أنت موافق؟؟ ");
            alert.setPositiveButton("نعم", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String filename = "com.adobe.reader.apk";

                    File adobe = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filename);
                    if (adobe.exists()) {
                        //File Exists
                        Log.e("test", "Exists");
                    } else {
                        CopyAssets(filename, context);
                    }
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

//                    if(hasInstallPermissions()) {
//                        Log.e("hasInstallPermissions","true");
//                        install_pkg();
//                    }
//                    else
//                    {
//
//                        Log.e("hasInstallPermissions","false");
//                        requestInstallPerms();
//                    }


        } else {

            String filename = "Obstetric_protocol.pdf";
            Uri path;

            File pdf = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filename);
            if (pdf.exists()) {
                //File Exists
                Log.e("test", "Exists");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    path = FileProvider.getUriForFile(context, context.getPackageName() + ".my.package.name.provider", pdf);

                } else {
                    path = Uri.fromFile(pdf);
                }
                Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.adobe.reader");
                intent.setDataAndType(path, "application/pdf");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                grantAllUriPermissions(context, intent, path);

                try {
                    context.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(context, "No Application Available to View PDF", Toast.LENGTH_LONG).show();
                }

            } else {
                CopyAssets(filename, context);
            }

        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void CopyAssets(final String fileName, final Context context) {
        /////////////////////////////////////////////////////////////////
//        String applicationName = context.getResources().getString(R.string.app_name);
//        dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+applicationName;
//
//        Log.e("path",dirPath);
//        //hasWritePermissions = hasWritePermissions();
//        boolean hasWritePermissions = true;

        if (ToolsUtils.hasWritePermissions(context)) {
//            ToolsUtils.makeFolder(dirPath);
            downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse("http://apps.moh.gov.ps/mohmob/" + fileName);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            Long reference = downloadManager.enqueue(request);

//            while(!DrawerActivity.download_complete){
//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    Uri path1;
//                    @Override
//                    public void run() {
//                        // Do something after 5s = 5000ms
//                        Log.e("Mariam while","123");
//                        if(DrawerActivity.download_complete){
//                            File adobe = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
////        File adobe =new File("");
//
//                            if (adobe.exists()) {
//                                //File Exists
//                                Log.e("test1", "Exists");
//
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                                    path1 = FileProvider.getUriForFile(context, context.getPackageName() + ".my.package.name.provider", adobe);
//
//                                } else {
//                                    path1 = Uri.fromFile(adobe);
//                                }
//                                Intent adobe_intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
//                                adobe_intent.setDataAndType(path1, "application/vnd.android.package-archive");
//                                adobe_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                grantAllUriPermissions
//
//                                        (context, adobe_intent, path1);
//                                context.startActivity(adobe_intent);
//
//                            }
//
//                        }
//
//                    }
//                }, 10000);


        } else {
            requestWritePerms();
        }


//        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
//        Uri uri = Uri.parse("http://apps.moh.gov.ps/mohmob/Obstetric_protocol.pdf");
//        DownloadManager.Request request = new DownloadManager.Request(uri);
//        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Obstetric_protocol.pdf");
//
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//        Long reference = downloadManager.enqueue(request);


//        ToolsUtils.makeFolder(dirPath);

//        File outFile = new File(dirPath,fileName);
//
//        if(!outFile.exists()) {
//
//
//            AssetManager assetManager = context.getAssets();
//
//            InputStream in = null;
//            OutputStream out = null;
//            try {
//                in = assetManager.open(fileName);   // if files resides inside the "Files" directory itself
//
//
//                out = new FileOutputStream(outFile);
//                copyFile(in, out);
//                Toast.makeText(context, "تم حفظ الملف على ذاكرة الهاتف", Toast.LENGTH_LONG).show();
//
//
//
//            } catch (Exception e) {
//                Log.e("tag", e.getMessage());
//            } finally {
//                if (in != null) {
//                    try {
//                        in.close();
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//
//                    }
//
//                }
//                if (out != null) {
//                    try {
//                        out.close();
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//
//                    }
//
//                }
//            }
//
//        }


    }
    ////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////////////
    private void requestWritePerms() {
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(mAct, permissions, WRITE_PERMS_REQUEST_CODE);
        }
    }


    /////////////////////////////////////////////////////////////////////////////
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }
    /////////////////////////////////////////////////////////////////////////

    public class Holder {
        TextView tv;
        ImageView img;
    }
}
