package haffa.budgetgamer.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import haffa.budgetgamer.util.TinyDB;

import static haffa.budgetgamer.data.DatabaseHelper.COLUMN_DEAL_ID;
import static haffa.budgetgamer.data.DatabaseHelper.COLUMN_DEAL_RATING;
import static haffa.budgetgamer.data.DatabaseHelper.COLUMN_GAME_ID;
import static haffa.budgetgamer.data.DatabaseHelper.COLUMN_NORMAL_PRICE;
import static haffa.budgetgamer.data.DatabaseHelper.COLUMN_SALE_PRICE;
import static haffa.budgetgamer.data.DatabaseHelper.COLUMN_SAVINGS;
import static haffa.budgetgamer.data.DatabaseHelper.COLUMN_STORE_ID;
import static haffa.budgetgamer.data.DatabaseHelper.COLUMN_THUMBNAIL;
import static haffa.budgetgamer.data.DatabaseHelper.COLUMN_TITLE;
import static haffa.budgetgamer.util.RetriveMyApplicationContext.getAppContext;

/**
 * Created by Peker on 11/23/2016.
 */

public class DataHandler extends AsyncTask<String, String, String> {

    public ArrayList<String> listOfIds = new ArrayList<String>();
    String CONTENT_AUTHORITY = "haffa.budgetgamer/game";
    Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public final String LOG_TAG = DataHandler.class.getSimpleName();
    String jsonResponse;
    ContentResolver resolver = getAppContext().getContentResolver();
    ContentValues contentValues = new ContentValues();
    DatabaseHelper databaseHelper = new DatabaseHelper(getAppContext());

   @Override
   protected String doInBackground(String... params) {

       BufferedReader bufferedReader = null;
       HttpURLConnection urlConnection = null;


       try {
           URL url = new URL(params[0]);
           urlConnection = (HttpURLConnection) url.openConnection();
           urlConnection.connect();

           InputStream inputStream = urlConnection.getInputStream();
           bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
           StringBuffer buffer = new StringBuffer();
           String line = "";
           while ((line = bufferedReader.readLine()) != null) {
               buffer.append(line);
           }
           //if answer is ready at this point
           return buffer.toString();


       } catch (MalformedURLException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       } finally {
           if (urlConnection != null) {
               urlConnection.disconnect();
           }
       }
       //if there's no response
       return null;
   }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

                jsonResponse = (result);
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

                /*
                This method will ensure that the SQLite database isnt flooded with data. The implementation
                is temporary and will be in use until I figure out how to schedule timed download tasks*/

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

                        listOfIds.add(i, dealID);

                        contentValues.put(COLUMN_TITLE, title);
                        contentValues.put(COLUMN_DEAL_ID, dealID);
                        contentValues.put(COLUMN_STORE_ID, storeID);
                        contentValues.put(COLUMN_GAME_ID, gameID);
                        contentValues.put(COLUMN_SALE_PRICE, salePrice);
                        contentValues.put(COLUMN_NORMAL_PRICE, normalPrice);
                        contentValues.put(COLUMN_DEAL_RATING, dealRating);
                        contentValues.put(COLUMN_SAVINGS, savings);
                        contentValues.put(COLUMN_THUMBNAIL, thumb);

                        Log.v(LOG_TAG, "saving record nr " + i);
                        //databaseHelper.addGame(new Game(title, dealID, storeID, gameID, salePrice,
                        //       normalPrice, dealRating, savings, thumb));
                        resolver.insert(BASE_CONTENT_URI, contentValues);
                    }
                    TinyDB tinyDB = new TinyDB(getAppContext());
                    tinyDB.putListString("IDS", listOfIds);

                } catch (JSONException e) {
                    Log.v(LOG_TAG, "Unable to parse JSON file");
                    e.printStackTrace();
                }
                List<Game> games = databaseHelper.getAllGames();

                for (Game game : games) {
                    String log = "Id: " + game.getID() + " ,Name: " + game.getTitle() + " , Savings: " + game.getSavings();
                    Log.v("DATABSE CONTENT", log);
                }
            }
    public AsyncTask<String, String, String> downloadData(String url) {
           DataHandler dataHandler = new DataHandler();
            return dataHandler.execute(url);

        }
    }







