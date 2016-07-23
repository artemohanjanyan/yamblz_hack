package yamblz.hack.learning.ui.adapters;

import android.view.View;


import yamblz.hack.learning.R;
import yamblz.hack.learning.ui.other.TaskSelectedListener;

public class FirstRecyclerAdapter extends RecyclerAdapter implements TaskSelectedListener {

    protected String titles[] = {"КАРТОЧКИ", "ПРОГОВАРИВАНИЕ СЛОВ", "СЛОВО НА СЛУХ", "ПОИСК ПАРЫ", "СОСТАВЛЕНИЕ СЛОВА",
    "ВЕРНО-НЕВРЕНО", "ВЫБОР ПЕРЕВОДА", "ПЕРЕВОД НА КАРТИНКЕ"};
    protected TaskSelectedListener listener;

    @Override
    public void onBindViewHolder(TasksViewHolder holder, int position) {

        holder.title.setText(titles[position]);
        holder.itemView.setTag(R.id.tag, titles[position]);
    }

    @Override
    public int getItemCount() {
        if (titles == null) return 0;
        return titles.length;
    }

    /**
     * Calls listener's method handling clicks on views
     * @param v view on which clicked
     */
    @Override
    public void onClick(View v) {
        String title = (String) v.getTag(R.id.tag);
        if (listener != null) {
            listener.onTaskSelected(title);
        }
    }

    @Override
    public void onTaskSelected(String title) {
        switch (title) {
            case "ПОИСК ПАРЫ":
                break;
        }
    }
}
