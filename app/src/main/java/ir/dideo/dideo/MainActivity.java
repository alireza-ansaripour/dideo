package ir.dideo.dideo;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import Models.Search;
import Models.SearchAPI;
import Models.VideoResults;

public class MainActivity extends AppCompatActivity {
    TextView textView = null;
    String[] items = new String[3];
    ArrayAdapter<String> itemsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchResultFragment fragment = new searchResultFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Toast.makeText(getApplicationContext(),"d",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(), "close", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(),suggests[position],Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onSuggestionSelect(int position) {
                // Your code here
                Toast.makeText(getApplicationContext(),suggests[position]+" selected",Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getApplicationContext(),query,Toast.LENGTH_SHORT).show();
            new SearchAPI(getApplicationContext(), query, null) {
                @Override
                public void onResults(VideoResults results) {

                }

                @Override
                public void onFail() {

                }
            };
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
}