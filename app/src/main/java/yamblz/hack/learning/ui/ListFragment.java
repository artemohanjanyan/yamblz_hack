package yamblz.hack.learning.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yamblz.hack.learning.R;

public class ListFragment extends Fragment {

    public static ListFragment newInstance() {
        return new ListFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_start, container, false);

        return rootView;
    }

}
