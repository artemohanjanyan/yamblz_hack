package yamblz.hack.learning.adapters;

import android.view.View;


import yamblz.hack.learning.R;
import yamblz.hack.learning.other.TaskSelectedListener;

public class FirstRecyclerAdapter extends RecyclerAdapter {

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

    @Override
    public void onClick(View v) {
        String title = (String) v.getTag(R.id.tag);
        if (listener != null) {
            listener.onTaskSelected(title);
        }
    }

    @Override
    public void setPerformerSelectedListener(TaskSelectedListener listener) {
        this.listener = listener;
    }
}
