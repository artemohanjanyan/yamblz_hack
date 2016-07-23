package yamblz.hack.learning.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yamblz.hack.learning.R;
import yamblz.hack.learning.ui.tasks.cards.CardsFragment;

public class ListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        rootView.findViewById(R.id.list_fragment_item_cards)
                .setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.activity_main, new CardsFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        return rootView;
    }

    private static abstract class OnClickListener implements View.OnClickListener {
        public abstract void onClick(View view);
    }
}
