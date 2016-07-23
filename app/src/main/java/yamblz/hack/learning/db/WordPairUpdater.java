package yamblz.hack.learning.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

public class WordPairUpdater extends AsyncTask<WordPair, Void, Void> {
    private volatile Context context;

    public WordPairUpdater(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(WordPair... wordPairs) {
        WordPair wordPair = wordPairs[0];
        Helper helper = new Helper(context);
        context = null;

        try (SQLiteDatabase writableDatabase = helper.getWritableDatabase()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(Helper.LEARN_COUNT, wordPair.getLearnCount() + 1);

            writableDatabase.update(Helper.TABLE_WORDS,
                    contentValues, Helper.ID + "=" + wordPair.getId(), null);
        }
        return null;
    }
}
