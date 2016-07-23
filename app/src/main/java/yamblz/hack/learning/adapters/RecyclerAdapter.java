package yamblz.hack.learning.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import yamblz.hack.learning.R;

/**
 * Adapter for RecycleView
 */
public abstract class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.TasksViewHolder>
        implements View.OnClickListener {


    class TasksViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public TasksViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.name);
//            Typeface face = Typeface.createFromAsset(title.getContext().getAssets(), "fonts/Elbing.otf");
//            title.setTypeface(face);
        }
    }
    @Override
    public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        view.setOnClickListener(this);
        return new TasksViewHolder(view);
    }

}
