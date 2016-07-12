package ir.dideo.dideo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import Models.Video;

/**
 * Created by Navid on 7/10/16.
 */
public class SearchResultAdapter extends BaseAdapter {
    Object[] objects = null;
    Context context;
    Video[] items;
    public SearchResultAdapter(Context context, Video[] items) {
        this.context = context;
        this.items = items;
    }
    @Override
    public int getCount() {
        if (items != null)
            return items.length;
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
            return items[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            convertView = vi.inflate(R.layout.search_result_patern, null);
            holder = new ViewHolder();
            holder.textView = (TextView)convertView.findViewById(R.id.resultText);
            holder.imageView = (ImageView) convertView.findViewById(R.id.resultImage);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        TextView textView = (TextView)convertView.findViewById(R.id.resultText);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.resultImage);
        Video item = (Video) getItem(position);
//        String pathToFile = "http://inducesmile.com/wp-content/uploads/2015/03/mobile.jpg";
        DownloadImageWithURLTask downloadTask = new DownloadImageWithURLTask(imageView);
        downloadTask.execute(item.thumbnail);
//        downloadTask.execute(pathToFile);
        textView.setText(item.title);
        return convertView;
    }
}
class ViewHolder{
    TextView textView;
    ImageView imageView;
    WebView webView;
}
class DownloadImageWithURLTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    public DownloadImageWithURLTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String pathToFile = urls[0];
        Bitmap bitmap = null;
        try {
            InputStream in = new java.net.URL(pathToFile).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return bitmap;
    }
    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}