package Models;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import org.json.JSONObject;

import java.sql.SQLException;

import db.DBHelper;

/**
 * Created by alireza on 7/4/16.
 */
public abstract class VideoAPI {
    private static Video result = null;
    private  void  convertToVideoResult(String json){
        Log.i("json",json);
        Gson gson = new Gson();
        result = gson.fromJson(json,Video.class);
        onResults(result);
    }

    public VideoAPI(Context context, String key) {

        String url = "http://mahan.ddev.ir/api/watch?d="+key;
        Log.i("url",url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("res", "done successfully");
                String result = response.toString();
                convertToVideoResult(result);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("videoget",error.toString());
                onFail();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsObjRequest);
    }
    public abstract void onResults(Video result);

    public abstract void onFail();

    public static Video[] getRelatedVideo(Video video){
        //TODO:implement this method
        return null;
    }
}
