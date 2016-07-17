package ir.dideo.dideo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.util.ArrayList;

import Models.Video;
import ir.dideo.dideo.R;

/**
 * Created by Navid on 7/10/16.
 */
public class SearchResultAdapter extends BaseAdapter {
    Object[] objects = null;
    Context context;
    ArrayList<Video> items;
    public SearchResultAdapter(Context context, ArrayList<Video> items) {
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
        Video item = (Video) getItem(position);
        UrlImageViewHelper.setUrlDrawable(imageView, item.thumbnail_hq, R.drawable.placeholder);
        textView.setText(item.title);
        return convertView;
    }
}
class ViewHolder{
    TextView textView;
    ImageView imageView;
}