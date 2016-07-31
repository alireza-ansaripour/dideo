package ir.dideo.dideo.activites;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import Models.SearchAPI;
import Models.Video;
import Models.VideoResults;
import ir.dideo.dideo.R;
import ir.dideo.dideo.adapters.SearchResultAdapter;

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
        final View footer = LayoutInflater.from(view.getContext()).inflate(R.layout.search_result_load_more, null);
        listView.addFooterView(footer,null,false);
        listView.setAdapter(Results);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            boolean flag_loading = false;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0) {
                    if (!flag_loading) {
                        flag_loading = true;
                        new SearchAPI(view.getContext(), "Salam", null) {
                            @Override
                            public void onResults(VideoResults results) {
//                                Log.d("SearchR", results.nextPageTokens);
                                videos.addAll(new ArrayList<Video>(Arrays.asList(results.videos)));
                                Results.notifyDataSetChanged();
                            }

                            @Override
                            public void onFail() {
                                Log.d("SearchR", "onFail: " + "Salam");
                            }
                        };
                    }
                }
            }

        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Video video = (Video) Results.getItem(position);
                Intent intent = new Intent(getContext(),MediaPlayerActivity.class);
                intent.putExtra("title",video.title);
                Log.i("title", video.title);
                Log.i("videoKey",video.video_key);
                intent.putExtra("videoKey",video.video_key);
                startActivity(intent);
            }
        });
    }
}