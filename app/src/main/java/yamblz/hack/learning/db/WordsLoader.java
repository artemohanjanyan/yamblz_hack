package yamblz.hack.learning.db;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

public class WordsLoader extends AsyncTaskLoader<List<WordPair>> {

    public WordsLoader(Context context) {
        super(context);
    }

    // TODO

    @Override
    public List<WordPair> loadInBackground() {
        return null;
    }
}
