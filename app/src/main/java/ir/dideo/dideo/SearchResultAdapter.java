package ir.dideo.dideo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
        return items.length;
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
            convertView = vi.inflate(R.layout.search, null);
            holder = new ViewHolder();
            holder.textView = (TextView)convertView.findViewById(R.id.query);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        TextView textView = (TextView)convertView.findViewById(R.id.query);
        Video item = (Video) getItem(position);
        textView.setText(item.title);
        return convertView;
    }
}
class ViewHolder{
    TextView textView;
}