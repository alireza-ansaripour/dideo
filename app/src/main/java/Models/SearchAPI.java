package Models;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import org.json.JSONObject;

import java.sql.SQLException;

import db.DBHelper;

/**
 * Created by alireza on 7/4/16.
 */
public abstract class SearchAPI {
    private static VideoResults results = null;
    private Context  context;
    private String query;
    private  void  convertToVideoResult(String json){
        Log.i("json",json);
        Gson gson = new Gson();
        results = gson.fromJson(json,VideoResults.class);
        if (results.videos.length == 0){
            Log.e("result","request Failed");
            final SearchAPI api = this;
            String url = "http://mahan.ddev.ir/api/search?q="+query;

            new SearchAPI(context,query,null) {
                @Override
                public void onResults(VideoResults res) {
                    onResults(res);
                }

                @Override
                public void onFail() {

                }
            };
        }else{
            Log.d("result", results.toString());
            onResults(results);
        }

    }

    public SearchAPI(Context context, String query,String next_page_token) {
        this.query = query;
        this.context = context;
        Dao<Search,String> searches = OpenHelperManager.getHelper(context, DBHelper.class).getSearchDao();
        try {
            Search search = searches.queryForId(query);
            if(search == null)
                searches.create(new Search(query));
            else {
                search.count = search.count+1;
                searches.update(search);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String url;
        if(next_page_token == null)
            url = "http://mahan.ddev.ir/api/search?q="+Uri.encode(query,"UTF-8");
        else
            url = "http://mahan.ddev.ir/api/search?q="+Uri.encode(query,"UTF-8")+"&nextPageTokens="+next_page_token;
        Log.i("url",url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new SucessResponse(), new FailResponse());
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsObjRequest);
    }
    public abstract void onResults(VideoResults results);

    public abstract void onFail();

    class SucessResponse implements Response.Listener<JSONObject>{

        @Override
        public void onResponse(JSONObject jsonObject) {
            String result = jsonObject.toString();
            convertToVideoResult(result);
        }
    }
    class FailResponse implements Response.ErrorListener{

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            onFail();
        }
    }
}
