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

    public static final String DATABASE_NAME = "gamesales.db";
    public static final String TABLE_NAME = "gameDeals";
    public static final int DATABASE_VERSION = 1;
    public static final String ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DEAL_ID = "deal_id";
    public static final String COLUMN_STORE_ID = "store_id";
    public static final String COLUMN_GAME_ID = "game_id";
    public static final String COLUMN_SALE_PRICE = "sale_price";
    public static final String COLUMN_NORMAL_PRICE = "normal_price";
    public static final String COLUMN_SAVINGS = "savings";
    public static final String COLUMN_DEAL_RATING = "deal_rating";
    public static final String COLUMN_THUMBNAIL = "thumbnail";

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

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_DEAL_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void dropAndRecreateDatabase(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL(SQL_CREATE_DEAL_TABLE);
    }
}
