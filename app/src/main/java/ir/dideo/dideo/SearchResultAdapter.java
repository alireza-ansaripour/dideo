package ir.dideo.dideo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by Navid on 7/10/16.
 */
public class SearchResultAdapter extends BaseAdapter {
    Context context;
    public SearchResultAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){

        }
        return null;
    }
}
