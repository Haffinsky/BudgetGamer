package haffa.budgetgamer;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Peker on 11/28/2016.
 */

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.ViewHolder> {

    String COLUMN_TITLE = "title";
    String COLUMN_NORMAL_PRICE = "normal_price";
    String COLUMN_THUMBNAIL = "thumbnail";
    String[] projection = {COLUMN_TITLE, COLUMN_NORMAL_PRICE, COLUMN_THUMBNAIL};
    String CONTENT_AUTHORITY = "haffa.budgetgamer/game";
    Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    Context mContext;
    Cursor cursor;


    public GameListAdapter(Context context, Cursor cursor){
        mContext = context;
        this.cursor = cursor;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbView;
        public TextView titleView, priceView;

        public ViewHolder(View itemView) {
            super(itemView);
            thumbView = (ImageView) itemView.findViewById(R.id.grid_thumb_image);
            titleView = (TextView) itemView.findViewById(R.id.title_text_view);
            priceView = (TextView) itemView.findViewById(R.id.price_text_view);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.tile_element, null, true);
        return new ViewHolder(root);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ContentResolver contentResolver = mContext.getContentResolver();
        Cursor cursor =
                contentResolver.query(BASE_CONTENT_URI,
                        projection,
                        null,
                        null,
                        null);
        cursor.moveToPosition(position);
        Picasso.with(mContext).load(cursor.getString(2)).resize(160, 90).into(holder.thumbView);
        Log.v("LOGGITY", cursor.getString(2));
        holder.titleView.setText(cursor.getString(0));
        //holder.priceView.setText(cursor.getString(1));
        holder.priceView.setText(String.valueOf(position));

    }

    @Override
    public int getItemCount() {
        ContentResolver contentResolver = mContext.getContentResolver();
        Cursor cursor =
                contentResolver.query(BASE_CONTENT_URI,
                        projection,
                        null,
                        null,
                        null);
        return cursor.getCount();
    }

}
