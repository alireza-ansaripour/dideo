package ir.dideo.dideo;

import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.widget.VideoView;
import io.vov.vitamio.widget.MediaController;

public class WatchActivity extends AppCompatActivity {

    private String path = "http://hn8.asset.aparat.com/aparat-video/a_ko92f565ojpk67m86o99762552l32l33597o53582951-169z__a9ec5.mp4";
    private VideoView vidView;
    private EditText mEditText;
    MediaController vidControl;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        if (!LibsChecker.checkVitamioLibs(this))
            return;
        setContentView(R.layout.activity_watch);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int h = displaymetrics.heightPixels;
        int w = displaymetrics.widthPixels;
        VideoView vidView = (VideoView)findViewById(R.id.myVideo);
        String vidAddress = "http://hn8.asset.aparat.com/aparat-video/a_ko92f565ojpk67m86o99762552l32l33597o53582951-169z__a9ec5.mp4";
        vidControl = new MediaController(this);
        vidControl.setAnchorView(findViewById(R.id.content));
        vidView.setMediaController(vidControl);
        Uri vidUri = Uri.parse(vidAddress);
        vidView.setVideoURI(vidUri);
        findViewById(R.id.content).setLayoutParams(new LinearLayout.LayoutParams(w, h / 2));
        vidView.start();

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int h = displaymetrics.heightPixels;
        int w = displaymetrics.widthPixels;
        vidView = (VideoView)findViewById(R.id.myVideo);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
            // for example the width of a layout
            int width = 300;;
            findViewById(R.id.content).setLayoutParams(new LinearLayout.LayoutParams(w, h));
            vidView.setLayoutParams(new RelativeLayout.LayoutParams(w, RelativeLayout.LayoutParams.MATCH_PARENT));
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            findViewById(R.id.content).setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, h / 2));
            vidView.setLayoutParams(new RelativeLayout.LayoutParams(w, RelativeLayout.LayoutParams.MATCH_PARENT));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
}
