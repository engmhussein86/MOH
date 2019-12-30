package com.moh.app.news;


import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.moh.app.DrawerActivity;
import com.moh.app.adapters.NewsAdapter;
import com.moh.app.db.NewsDBHelper;
import com.moh.app.models.NewsClass;
import com.moh.app.moh.R;
import com.moh.app.network.CustomRequest;
import com.moh.app.utils.AppController;
import com.moh.app.utils.ToolsUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragNews extends Fragment {

    public static ArrayList<NewsClass> news;
    static String search = "";
    RecyclerView rv;
    NewsAdapter newsAdapter;
    int visibleItemCount, totalItemCount, pastVisiblesItems, page = 0;
    boolean isLoading = true;
    SearchView mSearchView;
    MenuItem searchItem;
    SwipeRefreshLayout mSwipeRefreshLayout;
    NewsDBHelper helper;
//    private LoadingDialog loader_dialog;

    ProgressDialog pDialog;

    public FragNews() {
        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e("FFF", "onCreateView");
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.clear();

        //MenuInflater inflate = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);

        searchItem = menu.findItem(R.id.action_search);
        //mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);


        mSearchView = (SearchView) searchItem.getActionView();
        mSearchView.setQueryHint("ابحث هنا ..");
        //mSearchView.setSubmitButtonEnabled(true);

        ImageView closeButton = (ImageView) mSearchView.findViewById(R.id.search_close_btn);


        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.e("search", "Search close button clicked");
                //Find EditText view
                EditText et = (EditText) mSearchView.findViewById(R.id.search_src_text);

                //Clear the text from EditText view
                et.setText(" ");
                //Clear query
                mSearchView.setQuery("", false);
                //Collapse the action view
                //mSearchView.onActionViewCollapsed();
                //Collapse the search widget
                //searchItem.collapseActionView();
                search = "";

                refreshItems();
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String query) {
                Log.e("Error", query);
                query = query.toLowerCase();
                final ArrayList<NewsClass> filteredList = new ArrayList<>();
                for (int i = 0; i < news.size(); i++) {
                    final String text = news.get(i).getPost_title().toLowerCase();
                    if (text.contains(query)) {
                        filteredList.add(news.get(i));
                    }
                }
                //rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                newsAdapter.setArrayList(filteredList);

                newsAdapter.notifyDataSetChanged();  // data set changed<br />
                return true;


            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e("T", query);
//                search="%D9%86%D8%A7%D8%B5%D8%B1";
                search = query;
                refreshItems();
                return true;
            }

        });

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                //Log.e("HI","HI");
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Write your code here
                if (mSearchView.getQuery().length() > 0) {
                    search = mSearchView.getQuery().toString();
                    //Log.e("ERROR",search);
                    refreshItems();
                    //mSearchView.setQuery("", false);
                    return false;
                } else {
                    search = "";

                    refreshItems();
                    return true;
                }


            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_refresh:
                // refresh
                mSwipeRefreshLayout.setRefreshing(true);
                refreshItems();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("FFF", "onViewCreated");

        DrawerActivity.navItemIndex = 1;
        ((DrawerActivity) getActivity()).setToolbarTitle();
        ((DrawerActivity) getActivity()).selectNavMenu();


        helper = new NewsDBHelper(getContext());

        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.nav_news);


        //creates an action bar.
        //ActionBar actionBar = getSupportActionBar();
        //sets the homebutton.
        //actionBar.setDisplayHomeAsUpEnabled(true);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        news = new ArrayList<NewsClass>();
        newsAdapter = new NewsAdapter(getActivity().getApplicationContext(), news);
        rv = (RecyclerView) view.findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
        rv.setLayoutManager(llm);

        rv.setAdapter(newsAdapter);

//        loader_dialog = AppController.getLoadingDialog(getContext(), "");

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });


        // Configure the refreshing colors
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    void refreshItems() {
        Log.d("Error", "refresh");
        // Load items
        news.clear();
        newsAdapter.setArrayList(news);
        isLoading = true;
        page = 0;
        newsAdapter.notifyDataSetChanged();

        if (ToolsUtils.isConnectingToInternet(getContext())) {

            helper.deleteNews(-1);
            Log.e("Error", "deleting");

            getNews();
        } else {
            getNews_Offline();
            if (mSwipeRefreshLayout.isRefreshing())
                // Stop refresh animation
                mSwipeRefreshLayout.setRefreshing(false);

        }
        // Load complete

    }


    @Override
    public void onResume() {
        super.onResume();
        refreshItems();
    }

    private void getNews() {


        String url = AppController.NEWS_URL + "?page=" + page;
        if (search != "") url = url + "&search=" + Uri.encode(search);
        Log.e("Error", "" + url);
        HashMap<String, String> h = new HashMap<>();

//        loader_dialog.setMsg("جاري تحميل الأخبار");
//        loader_dialog.showDialog();

        if (ToolsUtils.isConnectingToInternet(getContext())) {
            final CustomRequest news_request = new CustomRequest(Request.Method.GET, url, h, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    Log.e("JSON", jsonObject.toString());
                    try {
                        //Log.d("Error",response.toString());
                        if (jsonObject != null) {
                            //JSONObject jobj = new JSONObject(response);
                            JSONArray jArr = jsonObject.getJSONArray("result");
                            Log.d("Error", String.valueOf(jArr.length()));
                            if (jArr.length() == 0) {
                                isLoading = false;
                                Toast.makeText(getActivity().getApplicationContext(), "DONE", Toast.LENGTH_SHORT).show();
                            } else {


                                for (int i = 0; i < jArr.length(); i++) {
                                    Log.d("Error", jArr.getJSONObject(i).getString("post_date").toString());
                                    NewsClass n = new NewsClass(Integer.parseInt(jArr.getJSONObject(i).getString("id")), jArr.getJSONObject(i).getString("post_date").toString(), jArr.getJSONObject(i).getString("guid").toString(), jArr.getJSONObject(i).getString("post_title").toString(), jArr.getJSONObject(i).getString("post_content").toString(), jArr.getJSONObject(i).getString("image_url").toString());
                                    news.add(n);
                                    helper.addNews(n);
                                    Log.d("Error", "sql");

                                }
                            }
                            if (mSwipeRefreshLayout.isRefreshing())
                                // Stop refresh animation
                                mSwipeRefreshLayout.setRefreshing(false);

                            //loader_dialog.hideDialog();

                            rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                    //Log.d("Error","scroll");
                                    super.onScrolled(recyclerView, dx, dy);
                                    visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                                    totalItemCount = recyclerView.getLayoutManager().getItemCount();
                                    pastVisiblesItems = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                                    //Log.e("SCROLL",visibleItemCount+ " " +pastVisiblesItems+" "+totalItemCount);
                                    if (visibleItemCount + pastVisiblesItems == totalItemCount && isLoading == true) {
                                        page = page + 1;
                                        getNews();
                                    }
                                }
                            });

                            // loader_dialog.hideDialog();

                        }
                    } catch (Exception e) {
                        Toast.makeText(getActivity().getApplicationContext(), getString(R.string.general_error), Toast.LENGTH_LONG).show();
                        Log.e("Error", "" + e.getMessage());
                        e.printStackTrace();
                    }
                    newsAdapter.notifyDataSetChanged();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
//                    Log.e("IYAD_ERROR", volleyError.getMessage());
                    if (volleyError instanceof NoConnectionError) {
                        Toast.makeText(getActivity().getApplicationContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                        //loader_dialog.hideDialog();
                        getNews_Offline();
                        if (mSwipeRefreshLayout.isRefreshing())
                            // Stop refresh animation
                            mSwipeRefreshLayout.setRefreshing(false);
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), getString(R.string.poor_internet), Toast.LENGTH_SHORT).show();
                        if (mSwipeRefreshLayout.isRefreshing())
                            // Stop refresh animation
                            mSwipeRefreshLayout.setRefreshing(false);
                    }

                    Log.e("Error", "" + volleyError.getMessage());
                    volleyError.printStackTrace();
                }
            });
            AppController.getInstance().addToRequestQueue(news_request);
        } else {
            //loader_dialog.hideDialog();
            getNews_Offline();
            if (mSwipeRefreshLayout.isRefreshing())
                // Stop refresh animation
                mSwipeRefreshLayout.setRefreshing(false);
        }


//        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    Log.d("Error",response.toString());
//                    if (response != null) {
//                        JSONObject jobj = new JSONObject(response);
//                        JSONArray jArr = jobj.getJSONArray("result");
//                        Log.d("Error", String.valueOf(jArr.length()));
//                        if (jArr.length() == 0) {
//                            isLoading = false;
//                            Toast.makeText(getActivity().getApplicationContext(), "DONE", Toast.LENGTH_SHORT).show();
//                        } else {
//
//
//                            for (int i = 0; i < jArr.length(); i++) {
//                                Log.d("Error",jArr.getJSONObject(i).getString("post_date").toString());
//                                NewsClass n = new NewsClass(Integer.parseInt(jArr.getJSONObject(i).getString("id")),jArr.getJSONObject(i).getString("post_date").toString(), jArr.getJSONObject(i).getString("guid").toString(), jArr.getJSONObject(i).getString("post_title").toString(), jArr.getJSONObject(i).getString("post_content").toString(), jArr.getJSONObject(i).getString("image_url").toString());
//                                news.add(n);
//                                helper.addNews(n);
//                                Log.d("Error","sql");
//
//                            }
//                        }
//                        if(mSwipeRefreshLayout.isRefreshing())
//                            // Stop refresh animation
//                            mSwipeRefreshLayout.setRefreshing(false);
//
//                        rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
//                            @Override
//                            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                                //Log.d("Error","scroll");
//                                super.onScrolled(recyclerView, dx, dy);
//                                visibleItemCount = recyclerView.getLayoutManager().getChildCount();
//                                totalItemCount = recyclerView.getLayoutManager().getItemCount();
//                                pastVisiblesItems = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
//                                //Log.e("SCROLL",visibleItemCount+ " " +pastVisiblesItems+" "+totalItemCount);
//                                if (visibleItemCount + pastVisiblesItems == totalItemCount && isLoading == true) {
//                                    page = page + 1;
//                                    getNews();
//                                }
//                            }
//                        });
//
//                    }
//                } catch (Exception e) {
//                    Toast.makeText(getActivity().getApplicationContext(), getString(R.string.general_error), Toast.LENGTH_LONG).show();
//                    Log.e("Error", "" + e.getMessage());
//                    e.printStackTrace();
//                }
//                newsAdapter.notifyDataSetChanged();
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                if (error instanceof NoConnectionError) {
//                    Toast.makeText(getActivity().getApplicationContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
//                    getNews_Offline();
//                    if(mSwipeRefreshLayout.isRefreshing())
//                        // Stop refresh animation
//                        mSwipeRefreshLayout.setRefreshing(false);
//                } else {
//                    Toast.makeText(getActivity().getApplicationContext(), getString(R.string.poor_internet), Toast.LENGTH_SHORT).show();
//                    if(mSwipeRefreshLayout.isRefreshing())
//                        // Stop refresh animation
//                        mSwipeRefreshLayout.setRefreshing(false);
//                }
//
//                Log.e("Error", "" + error.getMessage());
//                error.printStackTrace();
//            }
//        });

//        {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                params.put("page", 1 + "");
//                params.put("l", 5 + "");
//                Log.e("Error", params.toString());
//                return params;
//            }
//        };
        //mStringRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //NetworkController.getInstance().addToRequestQueue(mStringRequest);

        //save news to sqllite

    }

    private void getNews_Offline() {

        Toast.makeText(getActivity().getApplicationContext(), "offline", Toast.LENGTH_SHORT).show();
        NewsDBHelper helper = new NewsDBHelper(getContext());
        newsAdapter.setArrayList(helper.getAllNews());

        newsAdapter.notifyDataSetChanged();  // data set changed<br />

    }
}
