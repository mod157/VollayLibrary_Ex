package com.example.sunjae.libraytest;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by SunJae on 2017-01-19.
 */

//싱글톤 방식
public class NVolley {
    private static NVolley volley;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private LruCache<String, Bitmap> cache = new LruCache<>(10);

    private NVolley(Context context){
        requestQueue = Volley.newRequestQueue(context);
        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache(){

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    public static NVolley getInstacne(Context context){
        //생성
        if(volley == null)
            volley = new NVolley(context);
        return volley;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public ImageLoader getImageLoader(){
        return imageLoader;
    }
}
