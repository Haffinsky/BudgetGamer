package haffa.budgetgamer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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
}

}
