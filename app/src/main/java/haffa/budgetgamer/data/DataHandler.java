package haffa.budgetgamer.data;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import haffa.budgetgamer.util.RetriveMyApplicationContext;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Peker on 11/23/2016.
 */

public class DataHandler {

    public final String BULK_DOWNLOAD_URL = "http://www.cheapshark.com/api/1.0/deals?";
    public final String LOG_TAG = DataHandler.class.getSimpleName();
    OkHttpClient client = new OkHttpClient();
    String jsonResponse;


    DatabaseHelper databaseHelper = new DatabaseHelper(RetriveMyApplicationContext.getAppContext());
    public void getData() throws Exception {
        Request request = new Request.Builder()
                .url(BULK_DOWNLOAD_URL)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                    Log.v(LOG_TAG, (responseHeaders.name(i) + ": " + responseHeaders.value(i)));
                }

                jsonResponse = (response.body().string());
                Log.v(LOG_TAG, jsonResponse);
                String title;
                String dealID;
                String storeID;
                String gameID;
                String salePrice;
                String normalPrice;
                String dealRating;
                String savings;
                String thumb;
                //this method will ensure that the SQLite database isnt flooded with data
                databaseHelper.dropAndRecreateDatabase();
                try {
                    JSONArray jsonArray = new JSONArray(jsonResponse);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        title = jsonObject.getString("title");
                        dealID = jsonObject.getString("dealID");
                        storeID = jsonObject.getString("storeID");
                        gameID = jsonObject.getString("gameID");
                        salePrice = jsonObject.getString("salePrice");
                        normalPrice = jsonObject.getString("normalPrice");
                        dealRating = jsonObject.getString("dealRating");
                        savings = jsonObject.getString("savings");
                        thumb = jsonObject.getString("thumb");
                        Log.v(LOG_TAG, "cell nr " + i + " " + title + " " + dealID + " " + storeID + " "
                                + gameID + " " + salePrice + " " + normalPrice + " " + dealRating + " "
                                + savings + " " + thumb);
                        Log.v(LOG_TAG, "saving record nr " + i);
                        databaseHelper.addGame(new Game(title, dealID, storeID, gameID, salePrice,
                               normalPrice, dealRating, savings, thumb));
                    }
                } catch (JSONException e) {
                    Log.v(LOG_TAG, "Unable to parse JSON file");
                    e.printStackTrace();
                }
                List<Game> games = databaseHelper.getAllGames();

                for (Game game : games) {
                    String log = "Id: " + game.getID() + " ,Name: " + game.getTitle() + " ,Phone: " + game.getSavings();
                    // Writing Contacts to log
                    Log.v("DATABSE CONTENT", log);
                }
            }
        });
    }
}



