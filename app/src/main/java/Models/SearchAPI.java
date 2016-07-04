package Models;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by alireza on 7/4/16.
 */
public abstract class SearchAPI {
    private static VideoResults results = null;
    private  void  convertToVideoResult(String json){
        Log.i("json",json);
        Gson gson = new Gson();
        results = gson.fromJson(json,VideoResults.class);
        onResults(results);
    }

    public SearchAPI(Context context, String query,String next_page_token){
        String url = "http://mahan.ddev.ir/api/search?q="+query;
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

            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsObjRequest);
    }
    public abstract void onResults(VideoResults results);


    public static Video[] getRelatedVideo(Video video){
        //TODO:implement this method
        return null;
    }
}
