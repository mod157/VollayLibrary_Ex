package com.example.sunjae.libraytest;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView tv_NWtext;
    Button btn_NWtext;
    NetworkImageView iv_NWImg;
    ImageLoader imageLoader;
    RequestQueue queue;
    String url = "http:// ip or domain /nammuTest/vally.php";
    String url_img = "http:// ip or domain /nammuTest/test.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue = NVolley.getInstacne(this).getRequestQueue();
        imageLoader = NVolley.getInstacne(this).getImageLoader();
        tv_NWtext = (TextView) findViewById(R.id.tv_networktext);
        btn_NWtext = (Button) findViewById(R.id.btn_networktext);
        iv_NWImg = (NetworkImageView) findViewById(R.id.iv_networkImg);
        //초기화
        iv_NWImg.setImageUrl(null,null);

        btn_NWtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //보내는방식, 주소, 보낼 데이터, 성공했을시, 실패했을시
                JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url,
                        new JSONObject(), network_OK(), network_ERROR());
                //image
                iv_NWImg.setImageUrl(url_img, imageLoader);
                //queue에 저장
                queue.add(req);
            }
        });
    }
    private Response.Listener<Bitmap> network_imgOK(){
        return new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                iv_NWImg.setImageBitmap(response);
            }
        };
    }
    private Response.ErrorListener network_imgERROR(){
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"이미지 에러", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private Response.Listener<JSONObject> network_OK(){
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String server_text = null;
                try{
                    server_text = response.getString("vally");
                }catch (JSONException e) {
                    e.printStackTrace();
                }
                tv_NWtext.setText(server_text);
            }
        };
    }

    private Response.ErrorListener network_ERROR(){
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv_NWtext.setText(error.toString());
            }
        };
    }

}
