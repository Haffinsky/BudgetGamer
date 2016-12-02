package haffa.budgetgamer.data;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import haffa.budgetgamer.R;

import static haffa.budgetgamer.data.DatabaseHelper.BASE_CONTENT_URI;
import static haffa.budgetgamer.data.DatabaseHelper.COLUMN_NORMAL_PRICE;
import static haffa.budgetgamer.data.DatabaseHelper.COLUMN_SALE_PRICE;
import static haffa.budgetgamer.data.DatabaseHelper.COLUMN_SAVINGS;
import static haffa.budgetgamer.data.DatabaseHelper.COLUMN_THUMBNAIL;
import static haffa.budgetgamer.data.DatabaseHelper.COLUMN_TITLE;
import static haffa.budgetgamer.data.DatabaseHelper.ID;


/**
 * Created by Peker on 12/2/2016.
 */

public class GameWidgetRemoteViewsService extends RemoteViewsService {


    String[] projection = {COLUMN_TITLE, COLUMN_NORMAL_PRICE, COLUMN_THUMBNAIL, COLUMN_SALE_PRICE, COLUMN_SAVINGS, ID};
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            private Cursor cursor = null;
            @Override
            public void onCreate() {

            }

            @Override
            public void onDataSetChanged() {
                if (cursor != null) {
                    cursor.close();
                }
                final long identityToken = Binder.clearCallingIdentity();

                ContentResolver contentResolver = getApplicationContext().getContentResolver();
                cursor =
                        contentResolver.query(BASE_CONTENT_URI,
                                projection,
                                null,
                                null,
                                null);

                Binder.restoreCallingIdentity(identityToken);
            }

            @Override
            public void onDestroy() {
                if (cursor != null) {
                    cursor.close();
                    cursor = null;
                }
            }

            @Override
            public int getCount() {
                return cursor == null ? 0 : cursor.getCount();
            }

            @Override
            public RemoteViews getViewAt(int i) {
                RemoteViews remoteViews;
                cursor.moveToPosition(i);
                remoteViews = new RemoteViews(getPackageName(),
                        R.layout.widget_tile_element);

                remoteViews.setTextViewText(R.id.widget_title_text_view, cursor.getString(0));
                remoteViews.setTextViewText(R.id.widget_price_text_view, cursor.getString(1));


                return remoteViews;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_tile_element);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}
