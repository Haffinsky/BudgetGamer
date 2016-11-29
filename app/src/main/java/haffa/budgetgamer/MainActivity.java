package haffa.budgetgamer;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import haffa.budgetgamer.data.DataHandler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataHandler dataHandler = new DataHandler();
        try {
            dataHandler.getData();
        } catch (Exception e) {
        }
       contentProviderTest();
        addFragments();
}

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
    }
    public void addFragments(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new GameListFragment();
        fragmentTransaction.replace(R.id.grid_fragment_container, fragment);
        fragmentTransaction.commit();
    }
}
