package com.moh.app;


import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moh.app.adapters.ApplicationsAdapter;
import com.moh.app.moh.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_ApplicationGrid extends Fragment {
    // Keep all Icons in array
    public int[] Application_Icons =
            {
                    //R.drawable.ic_home_red_48dp,
                    R.drawable.ic_insert_drive_file_blue_48dp,
                    R.drawable.ic_contact_phone_black_24dp
//                    R.drawable.ic_local_hospital_blue_24dp,
//                    R.drawable.ic_local_hospital_blue_24dp
                    //R.drawable.calendar_red,
                    //R.drawable.ic_settings_phone_red_48dp,
                    //R.drawable.ic_settings_red_48dp,
                    //R.drawable.eservices_red,
                    //R.drawable.ic_android_red_48dp
            };
    GridView gv;
    TextView textView, contact;
    LinearLayout sso_login, contact_layer, hospitals;
    Activity mAct;
    Fragment frag;
    private String[] activityTitles =
            {
                    "أخبار الوزارة", "دليل الهواتف"//"المستشفيات","الرعاية الأولية"
            };


    public Fragment_ApplicationGrid() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("Test", "app");
        //activityTitles = getActivity().getResources().getStringArray(R.array.nav_item_activity_titles);
        return inflater.inflate(R.layout.fragment_application_grid, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DrawerActivity.navItemIndex = 0;
        ((DrawerActivity) getActivity()).setToolbarTitle();
        ((DrawerActivity) getActivity()).selectNavMenu();

        Typeface font_bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/" + getString(R.string.font_name_bold));

        gv = (GridView) view.findViewById(R.id.grid_view);
        textView = (TextView) view.findViewById(R.id.textView1);
        contact = (TextView) view.findViewById(R.id.contact);

        textView.setTypeface(font_bold);
        contact.setTypeface(font_bold);

        hospitals = (LinearLayout) view.findViewById(R.id.hospitals);
        hospitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HospitalFragment hos_frag = new HospitalFragment();
                frag = hos_frag;
                load_frag(frag);


            }
        });


        sso_login = (LinearLayout) view.findViewById(R.id.sso_login);
        sso_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment_Login frag_login = new Fragment_Login();
                frag = frag_login;
                load_frag(frag);


            }
        });

        contact_layer = (LinearLayout) view.findViewById(R.id.contact_layer);
        contact_layer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContactFragment frag_contact = new ContactFragment();
                frag = frag_contact;
                load_frag(frag);

            }
        });


        gv.setAdapter(new ApplicationsAdapter(getActivity(), getActivity().getApplicationContext(), activityTitles, Application_Icons));

    }

    //////////////////////////////////////
    private void load_frag(Fragment frag) {
        Log.e("Mariam", "222" + frag);
        if (frag != null) {
            Log.e("Mariam", "123" + frag);

            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.frame, frag);
            fragmentTransaction.commitAllowingStateLoss();
        }

    }
}
