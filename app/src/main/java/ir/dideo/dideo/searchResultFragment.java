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
import android.widget.ListView;
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
        TextView footer = new TextView(view.getContext());
        footer.setText("Loading...");
        listView.addFooterView(footer);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            int currentScrollState;
            int currentFirstVisibleItem;
            int currentVisibleItemCount;
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                this.currentScrollState = scrollState;
                this.isScrollCompleted();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                this.currentFirstVisibleItem = firstVisibleItem;
                this.currentVisibleItemCount = visibleItemCount;
            }

            Video[] moreSearchResults;

            private void isScrollCompleted() {
                if (this.currentVisibleItemCount > 0 && this.currentScrollState == SCROLL_STATE_IDLE) {

                    new SearchAPI(view.getContext(), "Salam", null) {
                        @Override
                        public void onResults(VideoResults results) {
                            moreSearchResults = results.videos;
                        }

                        @Override
                        public void onFail() {

                        }
                    };
                    if (moreSearchResults != null){
                        videos.addAll(new ArrayList<Video>(Arrays.asList(moreSearchResults)));
                        Results.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}