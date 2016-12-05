package haffa.budgetgamer;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;

import haffa.budgetgamer.data.DataHandler;

public class MainActivity extends AppCompatActivity {
    String errorMsg = "No connection detected. Please connect your device to the Internet.";
    public final String BULK_DOWNLOAD_URL = "http://www.cheapshark.com/api/1.0/deals?";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (isOnline() == true) {
            DataHandler dataHandler = new DataHandler();
            try {
                dataHandler.downloadData(BULK_DOWNLOAD_URL);
            } catch (Exception e) {
            }
        } else {
            Toast.makeText(getApplicationContext(), errorMsg,
                    Toast.LENGTH_LONG).show();
        }
        FirebaseCrash.report(new Exception("My first Android non-fatal error"));

        addFragments();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
}
    /*
    public void contentProviderTest(){
        String CONTENT_AUTHORITY = "haffa.budgetgamer/game";
        Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
        final String ID = "id";
        String COLUMN_TITLE = "title";
        ContentResolver resolver = getContentResolver();
        String[] projection = new String[]{ID, COLUMN_TITLE};
        Cursor cursor =
                resolver.query(BASE_CONTENT_URI,
                        projection,
                        null,
                        null,
                        null);
        if (cursor.moveToFirst()) {
            do {
                int ids = cursor.getInt(0);
                String title = cursor.getString(1);
                Log.v("ContentProvider", String.valueOf(ids) + " "  + title);
                // do something meaningful
            } while (cursor.moveToNext());
        }

    }*/
    public void addFragments(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new GameListFragment();
        fragmentTransaction.replace(R.id.grid_fragment_container, fragment);
        fragmentTransaction.commit();
    }
    @Override
    public void onResume(){
        super.onResume();
        FirebaseCrash.log("Activity Resumed");
    }
    public boolean isOnline() {
        //performing connectivity check
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
