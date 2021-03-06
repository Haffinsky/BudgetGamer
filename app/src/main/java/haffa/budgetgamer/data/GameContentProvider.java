package haffa.budgetgamer.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Peker on 11/25/2016.
 */

public class GameContentProvider extends ContentProvider {

    public static final String CONTENT_AUTHORITY = "haffa.budgetgamer";
    private DatabaseHelper databaseHelper;
    public static final String PATH_GAME = "game";
    public static final int GAME = 100;
    public static final int GAME_ID = 101;
    private static final UriMatcher uriMatcher = buildUriMatcher();
    public static UriMatcher buildUriMatcher(){

        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(CONTENT_AUTHORITY, PATH_GAME, GAME);
        matcher.addURI(CONTENT_AUTHORITY, PATH_GAME + "/#", GAME_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        databaseHelper = new DatabaseHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(DatabaseHelper.TABLE_NAME);
        int uriType = uriMatcher.match(uri);
        switch(uriType){
            case GAME:
                break;
            case GAME_ID:
                queryBuilder.appendWhere(DatabaseHelper.ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        Cursor cursor = queryBuilder.query(sqLiteDatabase, strings, s,
                strings1, null, null, s1);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        int uriType = uriMatcher.match(uri);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        long id = 0;
        switch (uriType){
            case GAME:
                id = sqLiteDatabase.insert(DatabaseHelper.TABLE_NAME, null, contentValues);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(PATH_GAME + "/" + id);
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
