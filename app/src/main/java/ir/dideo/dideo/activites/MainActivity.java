package ir.dideo.dideo.activites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.shahroz.svlibrary.interfaces.onSearchListener;
import com.shahroz.svlibrary.interfaces.onSimpleSearchActionsListener;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import Models.Search;
import ir.dideo.dideo.R;
import ir.dideo.dideo.adapters.PagerAdapter;
import ir.dideo.dideo.adapters.SuggestAdapter;

public class MainActivity extends AppCompatActivity implements onSimpleSearchActionsListener, onSearchListener {

    private boolean mSearchViewAdded = false;
    private com.shahroz.svlibrary.widgets.MaterialSearchView mSearchView;
    private WindowManager mWindowManager;
    private Toolbar mToolbar;
    private MenuItem searchItem;
    private boolean searchActive = false;
    String tag = "Search view";
    SuggestAdapter suggestAdapter;
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        mSearchView = new com.shahroz.svlibrary.widgets.MaterialSearchView(this);
        ArrayList<String> suggests = new ArrayList<>();
        Search[] searches = new Search[0];
        try {
            searches = Search.getAllSearches(getApplicationContext());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Search search : searches){
            suggests.add(search.query);
        }
        suggestAdapter = new SuggestAdapter(getApplicationContext(),suggests);
        mSearchView.setSuggestions(suggests.toArray(new String[suggests.size()]));
        mSearchView.setSuggestionAdapter(suggestAdapter);
        mSearchView.setOnSearchListener(this);
        mSearchView.setSearchResultsListener(this);
        mSearchView.setHintText("Search");

        if (mToolbar != null) {
            mToolbar.post(new Runnable() {
                @Override
                public void run() {
                    if (!mSearchViewAdded && mWindowManager != null) {
                        mWindowManager.addView(mSearchView,
                                com.shahroz.svlibrary.widgets.MaterialSearchView.getSearchViewLayoutParams(MainActivity.this));
                        mSearchViewAdded = true;
                    }
                }
            });
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        searchItem = menu.findItem(R.id.search);
        searchItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mSearchView.display();
                openKeyboard();
                return true;
            }
        });
        if(searchActive)
            mSearchView.display();
        return true;

    }

    private void openKeyboard(){
        Log.d(tag,"OnKeyBoard");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSearch(String query) {

        Log.d("onSearch",query);
        Intent intent = new Intent(this,ResultActivity.class);
        intent.putExtra("query",query);
        mSearchView.hide();
        startActivity(intent);
    }

    @Override
    public void searchViewOpened() {
        try {
            Search[] searches = Search.getAllSearches(getApplicationContext());
            ArrayList<String> items = new ArrayList<>();
            for(Search search : searches)
                items.add(search.query);
            suggestAdapter.setItems(items);
            suggestAdapter.notifyDataSetChanged();
            mSearchView.onQuery("");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void searchViewClosed() {
    }

    @Override
    public void onItemClicked(String item) {
        mSearchView.setSearchQuery(item);

    }

    @Override
    public void onScroll() {

    }

    @Override
    public void error(String localizedMessage) {

    }

    @Override
    public void onCancelSearch() {
        Log.d(tag,"canceled");
        searchActive = false;
        mSearchView.hide();
    }
}