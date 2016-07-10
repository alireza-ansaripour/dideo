package ir.dideo.dideo;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

<<<<<<< HEAD
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.sql.SQLException;
import java.util.List;

import Models.Search;
=======
import java.util.List;

>>>>>>> 03b89c9a147b5340ae3c85aff08c7ec669834cf8
import Models.SearchAPI;
import Models.VideoResults;
import db.DBHelper;

public class MainActivity extends AppCompatActivity {
    TextView textView = null;
    String[] items = new String[3];
    ArrayAdapter<String> itemsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
<<<<<<< HEAD

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dao<Search,String> searches = OpenHelperManager.getHelper(getApplicationContext(), DBHelper.class).getSearchDao();
                List<Search>searchList = null;
                try {
                    searchList = searches.queryForAll();
                    Snackbar.make(view, searchList.size()+"", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

        this.textView = (TextView)findViewById(R.id.text);

        items[0] = "Alireza";
        items[1] = "Navid";
        items[2] = "Arash";
        itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(itemsAdapter);
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
        if(id == R.id.search) {
            Toast.makeText(getApplicationContext(),id+"",Toast.LENGTH_SHORT).show();
            getSupportFragmentManager().beginTransaction();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        searchView.setIconifiedByDefault(false);

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }
    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            textView.setText("Loading ...");
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
            Toast.makeText(getApplicationContext(),query,Toast.LENGTH_SHORT).show();
            new SearchAPI(getApplicationContext(), query, null) {
                @Override
                public void onResults(VideoResults results) {
//                    textView.setText(results.videos[0].toString());
                    items[2]= results.videos[0].toString();
                    itemsAdapter.notifyDataSetChanged();
                    textView.setText("Done!");
                }

                @Override
                public void onFail() {
                    
                }
            };
        }
    }
}
