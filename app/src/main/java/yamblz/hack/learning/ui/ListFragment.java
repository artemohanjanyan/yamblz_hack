package yamblz.hack.learning.ui;

/**
 * Created by vorona on 23.07.16.
 */

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yamblz.hack.learning.R;

public class ListFragment extends Fragment  {

    public static ListFragment newInstance() {
        return new ListFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        return rootView;
    }

}
