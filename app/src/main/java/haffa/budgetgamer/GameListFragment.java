package haffa.budgetgamer;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static haffa.budgetgamer.util.RetriveMyApplicationContext.getAppContext;
public class GameListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    RecyclerView recyclerView;
    GameListAdapter adapter;
    Cursor cursor;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int GAME_LOADER = 0;
    String COLUMN_TITLE = "title";
    String COLUMN_NORMAL_PRICE = "normal_price";
    String COLUMN_THUMBNAIL = "thumbnail";
    String COLUMN_SALE_PRICE = "sale_price";
    String ID = "id";
    String COLUMN_SAVINGS = "savings";
    String[] projection = {COLUMN_TITLE, COLUMN_NORMAL_PRICE, COLUMN_THUMBNAIL, COLUMN_SALE_PRICE, COLUMN_SAVINGS};
    String CONTENT_AUTHORITY = "haffa.budgetgamer/game";
    Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GameListFragment() {
    }

    public static GameListFragment newInstance(String param1, String param2) {
        GameListFragment fragment = new GameListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getLoaderManager().initLoader(GAME_LOADER, null, this);
        View rootView = inflater.inflate(R.layout.game_list_fragment, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.game_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getAppContext()));
        adapter = new GameListAdapter(getAppContext());
        recyclerView.getRecycledViewPool().clear();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        switch (i) {
            case GAME_LOADER:
                // Returns a new CursorLoader
                return new CursorLoader(
                        getActivity(),                                     // Context
                        BASE_CONTENT_URI,  // Table to query
                        projection,                                        // Projection to return
                        null,                                              // No selection clause
                        null,                                              // No selection arguments
                        null                                               // Default sort order
                );
            default:
                // An invalid id was passed in
                return null;

    }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.v("LOAD FINISHED", "LOAD FINISHED");
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}

