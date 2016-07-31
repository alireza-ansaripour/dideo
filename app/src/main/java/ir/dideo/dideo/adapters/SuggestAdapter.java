package ir.dideo.dideo.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.shahroz.svlibrary.widgets.SuggestionAdapter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Models.Search;
import ir.dideo.dideo.R;

/**
 * Created by alireza on 7/17/16.
 */

public class SuggestAdapter extends SuggestionAdapter {
    Context context;

    public SuggestAdapter(Context context, List<String> suggestions) {
        this.context = context;
        this.suggestions = suggestions;
        this.items = suggestions;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            convertView = vi.inflate(R.layout.suggest, null);

        }
        TextView textView = (TextView)convertView.findViewById(R.id.suggest);
        ImageView delete = (ImageView)convertView.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,(String)getItem(position),Toast.LENGTH_SHORT).show();
                Search.deleteSearch(context, (String) getItem(position));
                items = new ArrayList<>();
                Search[] searches = new Search[0];
                try {
                    searches = Search.getAllSearches(context);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                for (Search search : searches)
                    items.add(search.query);
                setSuggestions(items);
                notifyDataSetInvalidated();
            }
        });
        delete.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
        String item =(String) getItem(position);
        textView.setText(item);
        return convertView;
    }
}