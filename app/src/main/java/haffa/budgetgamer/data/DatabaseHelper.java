package haffa.budgetgamer.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peker on 11/24/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    /*
    <rant>
    addGame and getGame methods in this class wait for the better days when Android people decide
    to scrap Content Providers and we will be able to load and insert data from/to SQLite databases
    like civilised people
    </rant>
    */


    public static final String CONTENT_AUTHORITY = "haffa.budgetgamer";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    static final String LOG_TAG = DatabaseHelper.class.getSimpleName();
    static final String DATABASE_NAME = "gamesales.db";
    static final String TABLE_NAME = "gameDeals";
    static final int DATABASE_VERSION = 1;
    static final String ID = "id";
    static final String COLUMN_TITLE = "title";
    static final String COLUMN_DEAL_ID = "deal_id";
    static final String COLUMN_STORE_ID = "store_id";
    static final String COLUMN_GAME_ID = "game_id";
    static final String COLUMN_SALE_PRICE = "sale_price";
    static final String COLUMN_NORMAL_PRICE = "normal_price";
    static final String COLUMN_SAVINGS = "savings";
    static final String COLUMN_DEAL_RATING = "deal_rating";
    static final String COLUMN_THUMBNAIL = "thumbnail";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String SQL_CREATE_DEAL_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                ID + " INTEGER PRIMARY KEY, " +
                COLUMN_TITLE + " TEXT UNIQUE, " +
                COLUMN_DEAL_ID + " TEXT NOT NULL, " +
                COLUMN_STORE_ID + " TEXT NOT NULL, " +
                COLUMN_GAME_ID + " TEXT NOT NULL, " +
                COLUMN_SALE_PRICE + " TEXT NOT NULL, " +
                COLUMN_NORMAL_PRICE + " TEXT NOT NULL, " +
                COLUMN_SAVINGS + " TEXT NOT NULL, " +
                COLUMN_DEAL_RATING + " TEXT NOT NULL, " +
                COLUMN_THUMBNAIL + " TEXT NOT NULL " + " );";

                sqLiteDatabase.execSQL(SQL_CREATE_DEAL_TABLE);

        Log.v(LOG_TAG, "Table created successfully");

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    //methods addGame and getAllGames are implemented based on tutorial on
    // http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/

    public void addGame(Game game){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_TITLE, game.getTitle());
        contentValues.put(COLUMN_DEAL_ID, game.getDealID());
        contentValues.put(COLUMN_STORE_ID, game.getStoreID());
        contentValues.put(COLUMN_GAME_ID, game.getGameID());
        contentValues.put(COLUMN_SALE_PRICE, game.getSalePrice());
        contentValues.put(COLUMN_NORMAL_PRICE, game.getNormalPrice());
        contentValues.put(COLUMN_DEAL_RATING, game.getDealRating());
        contentValues.put(COLUMN_SAVINGS, game.getSavings());
        contentValues.put(COLUMN_THUMBNAIL, game.getThumb());

        sqLiteDatabase.insertWithOnConflict(TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        sqLiteDatabase.close();
    }

    List<Game> getAllGames(){
        List<Game> gameList = new ArrayList<Game>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase sqliteDatabase = this.getWritableDatabase();
        Cursor cursor = sqliteDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Game game = new Game();
                game.setID(Integer.parseInt(cursor.getString(0)));
                game.setTitle(cursor.getString(1));
                game.setDealID(cursor.getString(2));
                game.setStoreID(cursor.getString(3));
                game.setGameID(cursor.getString(4));
                game.setSalePrice(cursor.getString(5));
                game.setNormalPrice(cursor.getString(6));
                game.setDealRating(cursor.getString(7));
                game.setSavings(cursor.getString(8));
                game.setThumb(cursor.getString(9));
                gameList.add(game);
            }
            while (cursor.moveToNext());
        }
        return gameList;
    }
    Game getGame(int id){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, new String[] { ID, COLUMN_TITLE, COLUMN_DEAL_ID,
                COLUMN_STORE_ID, COLUMN_GAME_ID, COLUMN_SALE_PRICE, COLUMN_NORMAL_PRICE, COLUMN_DEAL_RATING,
                COLUMN_SAVINGS, COLUMN_THUMBNAIL }, ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

            Game game = new Game(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3), cursor.getString(4),
                    cursor.getString(5), cursor.getString(6), cursor.getString(7),
                    cursor.getString(8), cursor.getString(9));
        return game;
    }
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public void dropAndRecreateDatabase(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        String SQL_CREATE_DEAL_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                ID + " INTEGER PRIMARY KEY, " +
                COLUMN_TITLE + " TEXT NOT NULL, " +
                COLUMN_DEAL_ID + " TEXT NOT NULL, " +
                COLUMN_STORE_ID + " TEXT NOT NULL, " +
                COLUMN_GAME_ID + " TEXT NOT NULL, " +
                COLUMN_SALE_PRICE + " TEXT NOT NULL, " +
                COLUMN_NORMAL_PRICE + " TEXT NOT NULL, " +
                COLUMN_SAVINGS + " TEXT NOT NULL, " +
                COLUMN_DEAL_RATING + " TEXT NOT NULL, " +
                COLUMN_THUMBNAIL + " TEXT NOT NULL " + " );";

        sqLiteDatabase.execSQL(SQL_CREATE_DEAL_TABLE);
    }

}
