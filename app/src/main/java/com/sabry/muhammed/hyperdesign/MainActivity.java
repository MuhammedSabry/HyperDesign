package com.sabry.muhammed.hyperdesign;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private final static int INITIAL_COUNT = 10, INITIAL_FROM = 0;
    private int totalProducts;

    public static final ArrayList<DataHolder> globalDataHolderArray = new ArrayList<>(10);

    private RecyclerView myRecyclerView;
    static RecyclerAdapter adapter;
    static StaggeredGridLayoutManager mGridLayoutManager;
    private static TextView emptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Products");
        myRecyclerView = findViewById(R.id.recyclerView);
        emptyTextView = findViewById(R.id.emptyView);

        totalProducts = INITIAL_COUNT;

        //building the API request URL
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme("https")
                .authority("grapesnberries.getsandbox.com")
                .appendPath("products")
                .appendQueryParameter("count", String.valueOf(INITIAL_COUNT))
                .appendQueryParameter("from", String.valueOf(INITIAL_FROM));
        String apiUri = uriBuilder.build().toString();

        //Requesting the first 10 items when the application is first lunched
        NetworkUtils.setJsonRequest(apiUri, this);

        //setting the LayoutManager to the RecyclerView
        mGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        myRecyclerView.setLayoutManager(mGridLayoutManager);


        //setting the adapter to the RecyclerView
        adapter = new RecyclerAdapter();
        myRecyclerView.setAdapter(adapter);

        //making the scrolling infinite
        myRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                               @Override
                                               public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                                   super.onScrollStateChanged(recyclerView, newState);
                                                   if (!myRecyclerView.canScrollVertically(1) && mGridLayoutManager.getItemCount() == totalProducts)
                                                       getMoreViews();
                                               }
                                           }
        );
    }

    public static void setEmptyView() {
        if (mGridLayoutManager.getItemCount() == 0)
            emptyTextView.setVisibility(View.VISIBLE);
        else
            emptyTextView.setVisibility(View.GONE);
    }

    public void getMoreViews() {
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme("https")
                .authority("grapesnberries.getsandbox.com")
                .appendPath("products")
                .appendQueryParameter("count", String.valueOf(10))
                .appendQueryParameter("from", String.valueOf(totalProducts));

        String apiUri = uriBuilder.build().toString();

        NetworkUtils.setJsonRequest(apiUri, this);
        totalProducts += 10;
    }
}