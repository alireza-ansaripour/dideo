package ir.dideo.dideo;

import android.app.Fragment;
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

        videos[0] = new Video();
        videos[0].title = "vid1";
        videos[1] = new Video();
        videos[1].title = "vid2";
        videos[2] = new Video();
        videos[2].title = "vid3";
        listView = (ListView)view.findViewById(R.id.listview);
        Results = new ArrayAdapter<Video>(getContext().getApplicationContext(), R.layout.search_result_freagment,videos);
        listView.setAdapter(Results);
    }
}
