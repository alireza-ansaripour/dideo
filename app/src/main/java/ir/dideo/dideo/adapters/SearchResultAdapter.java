package ir.dideo.dideo.adapters;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Models.Video;
import ir.dideo.dideo.R;

/**
 * Created by Navid on 7/10/16.
 */
public class SearchResultAdapter extends BaseAdapter {
    Object[] objects = null;
    Context context;
    List<Video> items;
    public SearchResultAdapter(Context context, List<Video> items) {
        this.context = context;
        this.items = items;
    }
    @Override
    public int getCount() {
        if (items != null)
            return items.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
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
        TextView duration = (TextView) convertView.findViewById(R.id.duration);
        Video item = (Video) getItem(position);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displaymetrics);
        int w = displaymetrics.widthPixels-10;
        Log.d("layoutsize", w - 10 + "");

        Picasso.with(context)
                .load(item.thumbnail_hq)
                .placeholder(R.drawable.placeholder)
                .resize(w, w*4/6)
                .into(imageView);
        textView.setText(item.title);
        duration.setText(item.duration);
        return convertView;
    }
}
class ViewHolder{
    TextView textView;
    ImageView imageView;
}