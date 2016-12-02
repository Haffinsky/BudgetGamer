package haffa.budgetgamer;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * Created by Peker on 11/28/2016.
 */

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.ViewHolder> {

    String[] projection = {COLUMN_DEAL_ID};
    String CONTENT_AUTHORITY = "haffa.budgetgamer/game";
    Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    Context mContext;
    Cursor cursor;
    static final String COLUMN_DEAL_ID = "deal_id";



    public GameListAdapter(Context context){
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView thumbView, medalView;
        public TextView titleView, priceView, salesPriceView, savingsView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            thumbView = (ImageView) itemView.findViewById(R.id.grid_thumb_image);
            titleView = (TextView) itemView.findViewById(R.id.title_text_view);
            priceView = (TextView) itemView.findViewById(R.id.price_text_view);
            salesPriceView = (TextView) itemView.findViewById(R.id.new_price_text_view);
            medalView = (ImageView) itemView.findViewById(R.id.medal_view);
            savingsView = (TextView) itemView.findViewById(R.id.savings_text_view);
        }

        @Override
        public void onClick(View view) {
            ContentResolver contentResolver = mContext.getContentResolver();
            Cursor cursor =
                    contentResolver.query(BASE_CONTENT_URI,
                            projection,
                            null,
                            null,
                            null);
            if (cursor != null){
                cursor.moveToPosition(getPosition());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.cheapshark.com/redirect?dealID=" + cursor.getString(0)));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            } else {
            Toast.makeText(view.getContext(), "position = " + getPosition(), Toast.LENGTH_SHORT).show();
        }
        }

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.tile_element, parent, false);
        return new ViewHolder(root);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        cursor.moveToPosition(position);

        holder.titleView.setText(cursor.getString(0));
        holder.priceView.setText(cursor.getString(1) + "$");
        Picasso.with(mContext).load(cursor.getString(2)).resize(160, 80).into(holder.thumbView);
        holder.priceView.setPaintFlags(holder.priceView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.salesPriceView.setText(cursor.getString(3) + "$!");
        holder.savingsView.setText("-" + cursor.getInt(4) + "%");

        //icons downloaded from https://icons8.com
        Resources resources = mContext.getResources();
        if (position == 0){
            holder.medalView.setImageDrawable(resources.getDrawable(R.drawable.medalfirstyellow));
        } else if (position == 1) {
            holder.medalView.setImageDrawable(resources.getDrawable(R.drawable.medalsecondyellow));
        }   else if (position == 2) {
            holder.medalView.setImageDrawable(resources.getDrawable(R.drawable.medalthirdyellow));
        }   else {
            holder.medalView.setImageDrawable(resources.getDrawable(R.drawable.justmedalyellow));
        }


    }

    @Override
    public int getItemCount() {
        if(cursor == null){
            return 0;
        }
        else return cursor.getCount();
    }

    public void swapCursor(final Cursor cursor)
    {
        this.cursor = cursor;
        this.notifyDataSetChanged();
    }

}
