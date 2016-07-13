package ir.dideo.dideo;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.CursorAdapter;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import Models.Search;
import Models.SearchAPI;
import Models.Video;
import Models.VideoResults;

public class MainActivity extends AppCompatActivity {
    AlphaAnimation inAnimation;
    AlphaAnimation outAnimation;
    FrameLayout progressBarHolder;
    boolean enableTabView = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBarHolder = (FrameLayout) findViewById(R.id.progressBarHolder);

        if(enableTabView){
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.home));
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.trending));
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.account));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
            final PagerAdapter adapter = new PagerAdapter
                    (getSupportFragmentManager(), tabLayout.getTabCount());
            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    String[] suggests = {};
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        MenuItem menuItemCompat = menu.findItem(R.id.search);
        // Associate searchable configuration with the SearchView
        final SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        searchView.setIconifiedByDefault(false);

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return false;
            }
        });

        final String[] from = new String[] {"cityName"};
        final int[] to = new int[] {R.id.query};
        final CursorAdapter arrayAdapter  = new SimpleCursorAdapter(getApplicationContext(),
                R.layout.search,
                null,
                from,
                to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        searchView.setSuggestionsAdapter(arrayAdapter);
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionClick(int position) {
                // Your code here
                searchView.setQuery(suggests[position],false);
                return true;
            }

            @Override
            public boolean onSuggestionSelect(int position) {
                // Your code here
                return true;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                populateAdapter(s,arrayAdapter);
                return false;
            }
        });

        return true;
    }
    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
            new MyTask(query).execute();
        }
    }
    private void populateAdapter(String query,CursorAdapter mAdapter) {
        final MatrixCursor c = new MatrixCursor(new String[]{ BaseColumns._ID, "cityName" });
        try {
            List<Search> searches = Arrays.asList(Search.getAllSearches(getApplicationContext()));
            Collections.sort(searches, new Comparator<Search>() {
                @Override
                public int compare(Search lhs, Search rhs) {
                    if (lhs.count < rhs.count)  return 1;
                    if (lhs.count > rhs.count)  return -1;
                    return 0;
                }
            });
            int i =0;
            suggests = new String[searches.size()];
            for(Search search : searches){
                if (search.query.contains(query)){
                    c.addRow(new Object[]{i,search.query});
                    suggests[i] = search.query;
                    i++;
                }
            }
            mAdapter.changeCursor(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private class MyTask extends AsyncTask<Void, Void, Void> {
        String query = null;
        MyTask(String query){
            this.query = query;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            inAnimation = new AlphaAnimation(0f, 1f);
            inAnimation.setDuration(200);
            progressBarHolder.setAnimation(inAnimation);
            progressBarHolder.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            outAnimation = new AlphaAnimation(1f, 0f);
            outAnimation.setDuration(200);
            progressBarHolder.setAnimation(outAnimation);
            progressBarHolder.setVisibility(View.GONE);
        }

        VideoResults searchResults;

        @Override
        protected Void doInBackground(Void... params) {

            new SearchAPI(getApplicationContext(), query, null) {
                @Override
                public void onResults(VideoResults results) {
                    searchResultFragment fragment = new searchResultFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                    fragment.videos = new ArrayList<Video>(Arrays.asList(results.videos));
                    Log.d("SearchR", "onResults: "+query);
                    searchResults = results;
                }

                @Override
                public void onFail() {
                    Log.d("SearchR", "onFail: "+query);
                    searchResults = new VideoResults();
                }
            };
            while(searchResults == null){

            }
            return null;
        }
    }
}