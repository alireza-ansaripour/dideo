package ir.dideo.dideo;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import Models.Video;

/**
 * Created by Navid on 7/10/16.
 */
public class searchResultFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_result_freagment, parent, false);

    }
    Video[] videos = new Video[3];
    ListView listView = null;
    ArrayAdapter<Video> Results = null;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    }
}
