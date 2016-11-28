package haffa.budgetgamer;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static haffa.budgetgamer.util.RetriveMyApplicationContext.getAppContext;


public class GameListFragment extends Fragment {

    RecyclerView recyclerView;
    GameListAdapter adapter;
    Cursor cursor;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int GAME_LOADER = 0;
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
        View rootView = inflater.inflate(R.layout.game_list_fragment, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.game_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getAppContext()));
        adapter = new GameListAdapter(getAppContext(), cursor);
        recyclerView.getRecycledViewPool().clear();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    }

