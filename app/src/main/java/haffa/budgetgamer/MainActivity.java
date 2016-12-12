package haffa.budgetgamer;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.eminayar.panter.DialogType;
import com.eminayar.panter.PanterDialog;
import com.eminayar.panter.interfaces.OnSingleCallbackConfirmListener;
import com.google.firebase.crash.FirebaseCrash;

import haffa.budgetgamer.data.DataHandler;

public class MainActivity extends AppCompatActivity {
    public final String BULK_DOWNLOAD_URL = "http://www.cheapshark.com/api/1.0/deals?";
    public final String LOWEST_PRICE_URL = "http://www.cheapshark.com/api/1.0/deals?sortBy=Price";
    public final String SAVINGS_PRICE_URL = "http://www.cheapshark.com/api/1.0/deals?sortBy=Savings";
    public final String STEAM_URL = "http://www.cheapshark.com/api/1.0/deals?storeID=1";

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
            Toast.makeText(getApplicationContext(), getString(R.string.errorMsg),
                    Toast.LENGTH_LONG).show();
        }
        FirebaseCrash.report(new Exception("My first Android non-fatal error"));

        addFragments();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                performDialogOperation();
            }
        });
}
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
    public void performDialogOperation(){

        PanterDialog panterDialog = new PanterDialog(this);
                panterDialog.setHeaderBackground(R.color.colorPrimary)
                .setHeaderLogo(R.drawable.finallogo)
                .setDialogType(DialogType.SINGLECHOICE)
                .isCancelable(false)
                .items(R.array.sample_array, new OnSingleCallbackConfirmListener() {
                    @Override
                    public void onSingleCallbackConfirmed(PanterDialog dialog, int pos, String text) {
                        DataHandler dataHandler = new DataHandler();
                        switch(pos){
                            case 0:
                                dataHandler.downloadData(BULK_DOWNLOAD_URL);
                                break;
                            case 1:
                                dataHandler.downloadData(LOWEST_PRICE_URL);
                                break;
                            case 2:
                                dataHandler.downloadData(SAVINGS_PRICE_URL);
                                break;
                            case 3:
                                dataHandler.downloadData(STEAM_URL);
                                break;
                            default:
                                return;
                        }
                    }
                });
                panterDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                panterDialog.show();
    }
}
