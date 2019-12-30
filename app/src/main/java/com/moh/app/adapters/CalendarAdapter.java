package com.moh.app.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.moh.app.models.ShiftClass;
import com.moh.app.moh.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by pc-3 on 2/26/2017.
 */
public class CalendarAdapter extends BaseAdapter {
    public static List<String> day_string;
    public GregorianCalendar pmonth;
    /**
     * calendar instance for previous month for getting complete view
     */
    public GregorianCalendar pmonthmaxset;
    int firstDay;
    int maxWeeknumber;
    int maxP;
    int calMaxP;
    int mnthlength;
    String itemvalue, curentDateString;
    DateFormat df;
    ArrayList<ShiftClass> ShiftArray;
    int month_p, year_p;
    private Context context;
    private java.util.Calendar month;
    private GregorianCalendar selectedDate;
    private ArrayList<String> items;

    public CalendarAdapter(Context context, int month_p, int year_p, ArrayList<ShiftClass> ShiftArray) {
        Log.e("month", month_p + "-" + year_p);
        this.ShiftArray = ShiftArray;
        this.month_p = month_p;
        this.year_p = year_p;
        CalendarAdapter.day_string = new ArrayList<String>();
        Locale.setDefault(Locale.US);
        GregorianCalendar monthCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
        month = monthCalendar;
        selectedDate = (GregorianCalendar) monthCalendar.clone();
        this.context = context;
        month.set(this.year_p, this.month_p - 1, 1);

        this.items = new ArrayList<String>();
        df = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        curentDateString = df.format(selectedDate.getTime());
        Log.e("DAY_OF_MONTH", String.valueOf(curentDateString));

        refreshDays();

    }


    public int getCount() {
        return day_string.size();
    }

    public Object getItem(int position) {
        return day_string.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new view for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        TextView dayView;
        if (convertView == null) { // if it's not recycled, initialize some
            // attributes
            LayoutInflater vi = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.row_cal_item, null);

        }


        dayView = (TextView) v.findViewById(R.id.date);
        String[] separatedTime = day_string.get(position).split("/");


        String gridvalue = separatedTime[0].replaceFirst("^0*", "");
        if ((Integer.parseInt(gridvalue) > 1) && (position < firstDay)) {
            dayView.setTextColor(Color.GRAY);
            dayView.setClickable(false);
            dayView.setFocusable(false);
        } else if ((Integer.parseInt(gridvalue) < 7) && (position > 28)) {
            dayView.setTextColor(Color.GRAY);
            dayView.setClickable(false);
            dayView.setFocusable(false);
        } else {
            // setting curent month's days in blue color.
            // dayView.setTextColor(R.color.primary_text);
            v.setBackgroundResource(R.color.gray_light);
            //v.setBackgroundColor(Color.parseColor("#FFD7D6D6"));
        }


        for (int i = 0; i < ShiftArray.size(); i++) {
            if (day_string.get(position).equals(ShiftArray.get(i).getTB_EMPSHIFT_SHIFTDATE())) {
                final int finalI = i;
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("info", ShiftArray.get(finalI).getTB_EMPSHIFT_SHIFTDATE());
                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                        // ...Irrelevant code for customizing the buttons and title

                        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View dialogView = inflater.inflate(R.layout.alert_dialog_shift, null);
                        dialogBuilder.setView(dialogView);

                        TextView EMPSHIFT_SHIFTDATE = (TextView) dialogView.findViewById(R.id.EMPSHIFT_SHIFTDATE);
                        TextView SHIFT_NAME = (TextView) dialogView.findViewById(R.id.SHIFT_NAME);
                        TextView SHIFT_START = (TextView) dialogView.findViewById(R.id.SHIFT_START);
                        TextView NOTE = (TextView) dialogView.findViewById(R.id.NOTE);
                        TextView SHIFT_TYPE_NAME = (TextView) dialogView.findViewById(R.id.SHIFT_TYPE_NAME);
                        TextView shift_color = (TextView) dialogView.findViewById(R.id.shift_color);
                        Button ok = (Button) dialogView.findViewById(R.id.ok);

                        EMPSHIFT_SHIFTDATE.setText(ShiftArray.get(finalI).getTB_EMPSHIFT_SHIFTDATE());
                        SHIFT_NAME.setText(ShiftArray.get(finalI).getTB_SHIFT_NAME());
                        SHIFT_START.setText(ShiftArray.get(finalI).getTB_SHIFT_START() + " - " + ShiftArray.get(finalI).getTB_SHIFT_END());
                        NOTE.setText(ShiftArray.get(finalI).getNOTE());
                        SHIFT_TYPE_NAME.setText(ShiftArray.get(finalI).getTB_SHIFT_TYPE_NAME());

                        shift_color.setBackgroundResource(get_shift_color(ShiftArray.get(finalI).getSHIFT_TYPE_FK()));


                        final AlertDialog alertDialog = dialogBuilder.create();
                        alertDialog.show();
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                            }
                        });

                    }
                });

                v.setBackgroundResource(get_shift_color(ShiftArray.get(i).getSHIFT_TYPE_FK()));


            }


        }


//
//        if (day_string.get(position).equals ("10/03/2017")) {
//
//            v.setBackgroundColor(Color.RED);
//        }
//
//        else if (day_string.get(position).equals(curentDateString)) {
//
//            v.setBackgroundColor(Color.GREEN);
//        }


        dayView.setText(gridvalue);

        return v;
    }


    public void refreshDays() {
        // clear items
        items.clear();
        day_string.clear();
        Locale.setDefault(Locale.US);
        pmonth = (GregorianCalendar) month.clone();
        // month start day. ie; sun, mon, etc
        firstDay = month.get(GregorianCalendar.DAY_OF_WEEK);
        // finding number of weeks in current month.
        maxWeeknumber = month.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
        // allocating maximum row number for the gridview.
        mnthlength = maxWeeknumber * 7;
        maxP = getMaxP(); // previous month maximum day 31,30....
        calMaxP = maxP - (firstDay - 1);// calendar offday starting 24,25 ...
        /**
         * Calendar instance for getting a complete gridview including the three
         * month's (previous,current,next) dates.
         */
        pmonthmaxset = (GregorianCalendar) pmonth.clone();
        /**
         * setting the start date as previous month's required date.
         */
        pmonthmaxset.set(GregorianCalendar.DAY_OF_MONTH, calMaxP + 1);

        /**
         * filling calendar gridview.
         */
        for (int n = 0; n < mnthlength; n++) {

            itemvalue = df.format(pmonthmaxset.getTime());
            pmonthmaxset.add(GregorianCalendar.DATE, 1);
            day_string.add(itemvalue);

        }
    }

    private int getMaxP() {
        int maxP;
        if (month.get(GregorianCalendar.MONTH) == month
                .getActualMinimum(GregorianCalendar.MONTH)) {
            pmonth.set((month.get(GregorianCalendar.YEAR) - 1),
                    month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            pmonth.set(GregorianCalendar.MONTH,
                    month.get(GregorianCalendar.MONTH) - 1);
        }
        maxP = pmonth.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

        return maxP;
    }

    /////////////////////////////////////////////////////////////////
    public void setMonth(int month_p) {
        this.month_p = month_p;
    }

    public void setYear(int year_p) {
        this.year_p = year_p;
    }
    /////////////////////////////////////////////////////////////////

    public int get_shift_color(int shift_type) {
        int color = 0;
        switch (shift_type) {
            case 1:
                color = R.color.yellow;
                break;
            case 2:
                color = R.color.purple;
                break;
            case 3:
                color = R.color.primary_light;
                break;
            case 4:
                color = R.color.divider;
                break;
            case 5:
                color = R.color.green;
                break;
            case 6:
                color = R.color.colorAccent;
                break;
            case 7:
                color = R.color.orange;
                break;
            default:
                color = R.color.gray_light;
        }
        return color;


    }

}
