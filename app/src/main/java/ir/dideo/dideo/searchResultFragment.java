package ir.dideo.dideo;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import Models.SearchAPI;
import Models.Video;
import Models.VideoResults;

/**
 * Created by Navid on 7/10/16.
 */
public class searchResultFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_result_freagment, parent, false);
    }
    public ArrayList<Video> videos;
    ListView listView = null;
    public SearchResultAdapter Results = null;
    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        Results = new SearchResultAdapter(getContext().getApplicationContext(),videos);
        listView = (ListView) view.findViewById(R.id.SearchResultListView);
        listView.setAdapter(Results);
//        final TextView footer = new TextView(view.getContext());
//        footer.setText("Loading...");
        final View footer = LayoutInflater.from(view.getContext()).inflate(R.layout.search_result_load_more, null);
        listView.addFooterView(footer,null,false);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            boolean flag_loading = false;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0)
                {
                    if(!flag_loading)
                    {
                        flag_loading = true;
                        new SearchAPI(view.getContext(), "Salam", null) {
                            @Override
                            public void onResults(VideoResults results) {
                                Log.d("SearchR", "onResults: "+"Salam");
                                videos.addAll(new ArrayList<Video>(Arrays.asList(results.videos)));
                                Results.notifyDataSetChanged();
                            }

                            @Override
                            public void onFail() {
                                Log.d("SearchR", "onFail: "+"Salam");
                            }
                        };
                    }
                }
            }

        });
    }
}