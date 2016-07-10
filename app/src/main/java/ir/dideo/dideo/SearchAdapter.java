package ir.dideo.dideo;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;

import java.lang.reflect.Array;

/**
 * Created by alireza on 7/10/16.
 */
public class SearchAdapter extends BaseAdapter {
    Object[] objects = null;
    Context context;
    public SearchAdapter(Context context) {
        this.context = context;
    }
    String[] item = {"ali","reza"};
    @Override
    public int getCount() {
        return item.length;
    }

    @Override
    public Object getItem(int position) {
        return item[position];
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
            holder.textView = (TextView)convertView.findViewById(R.id.text1);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        TextView textView = (TextView)convertView.findViewById(R.id.text1);
        textView.setText("ali");
        return convertView;
    }
}
class ViewHolder{
    TextView textView;
}