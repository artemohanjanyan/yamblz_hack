package yamblz.hack.learning.ui;

/**
 * Created by vorona on 23.07.16.
 */

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yamblz.hack.learning.R;
import yamblz.hack.learning.ui.adapters.FirstRecyclerAdapter;

public class ListFragment extends Fragment  {

    private RecyclerView rv;
    private String type = "";
    private RecyclerView.LayoutManager layoutManager;

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    public String getTitle() {
        return type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        rv = (RecyclerView) rootView.findViewById(R.id.list_d);
//        Typeface face = Typeface.createFromAsset(title.getContext().getAssets(), "fonts/Elbing.otf");
//        title.setTypeface(face); //TODO FONT

        rv.setAdapter(new FirstRecyclerAdapter());
        setRecyclerViewLayoutManager(savedInstanceState);
        return rootView;
    }

    private void setRecyclerViewLayoutManager(Bundle savedInstanceState) {
        layoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);
    }

}
