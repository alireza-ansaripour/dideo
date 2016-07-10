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

import java.util.List;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
            };
        }
    }
}
