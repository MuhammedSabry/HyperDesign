package com.sabry.muhammed.hyperdesign;

import android.content.Context;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.sabry.muhammed.hyperdesign.MainActivity.adapter;
import static com.sabry.muhammed.hyperdesign.MainActivity.globalDataHolderArray;
import static com.sabry.muhammed.hyperdesign.MainActivity.setEmptyView;


public class NetworkUtils {

    public static void setJsonRequest(String url, Context context) {
        Cache cache;
        Network network;
        RequestQueue queue;

        //1 MB cache for the QueueRequest
        cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024);
        network = new BasicNetwork(new HurlStack());
        queue = new RequestQueue(cache, network);
        queue.start();

        JsonArrayRequest jsObjRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //setting the data in the global ArrayList
                        publishData(response);

                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error Response: ", "Error");
                        setEmptyView();
                    }
                });

        queue.add(jsObjRequest);
    }

    private static void publishData(JSONArray array) {
        DataHolder theDataHolder;

        int id, height;
        String data, url, price;

        for (int i = 0; i < array.length(); i++) {
            JSONObject mainObject;
            JSONObject imageArray;
            try {
                //main object that holds the whole item's dataa
                mainObject = array.getJSONObject(i);

                //DataHolder's information extraction from the JSONObject
                id = mainObject.getInt("id");
                data = mainObject.getString("productDescription");
                price = String.valueOf(mainObject.getDouble("price"));

                //image's JSONObject
                imageArray = mainObject.getJSONObject("image");

                url = imageArray.getString("url");
                height = imageArray.getInt("height");

            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
            theDataHolder = new DataHolder(url, data, height);
            theDataHolder.setPrice(price);
            theDataHolder.setID(id);
            int index = globalDataHolderArray.indexOf(theDataHolder);

            if (index < 0)
                globalDataHolderArray.add(id, theDataHolder);
            else
                globalDataHolderArray.set(index, theDataHolder);

        }
    }
}
