package yamblz.hack.learning.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

import yamblz.hack.learning.network.Translation;
import yamblz.hack.learning.network.TranslationLoader;

public class WordsLoader extends AsyncTaskLoader<List<WordPair>> {
    private volatile int wordN;
    private List<WordPair> wordPairList;

    public WordsLoader(Context context, int wordN) {
        super(context);
        this.wordN = wordN;
    }

    @Override
    public List<WordPair> loadInBackground() {
        Helper helper = new Helper(getContext());

        try (SQLiteDatabase readableDatabase = helper.getReadableDatabase();
             Cursor cursor = readableDatabase.query(Helper.TABLE_UNKNOWN,
                     new String[]{Helper.DIRECTION, Helper.FIRST},
                     null, null, null, null, null)) {
            if (cursor.getCount() > 0) {
                SQLiteDatabase writableDatabase = helper.getWritableDatabase();
                SQLiteStatement statement = writableDatabase.compileStatement(String.format(
                        "INSERT INTO %s (%s,   %s, %s, %s, %s) " +
                                "VALUES (NULL,  ?,  ?,  ?,  ?)",
                        Helper.TABLE_WORDS, Helper.ID, Helper.DIRECTION,
                        Helper.FIRST, Helper.SECOND, Helper.LEARN_COUNT));

                writableDatabase.beginTransaction();

                while (cursor.moveToNext()) {
                    int direction = cursor.getInt(0);
                    String word = cursor.getString(1);
                    Translation translation = TranslationLoader.loadTranslation(direction, word);
                    if (translation != null && translation.getTranslations().size() > 1) {
                        statement.bindLong(1, direction);
                        statement.bindString(2, translation.getWord());
                        statement.bindString(3, translation.getTranslations().get(0));
                        statement.bindLong(4, 0);
                        statement.executeInsert();
                    }
                }

                writableDatabase.delete(Helper.TABLE_UNKNOWN, null, null);

                writableDatabase.setTransactionSuccessful();
                writableDatabase.endTransaction();
            }
        }

        try (SQLiteDatabase readableDatabase = helper.getReadableDatabase();
             Cursor cursor = readableDatabase.query(
                     Helper.TABLE_WORDS + " ORDER BY RANDOM() LIMIT " + wordN,
                     null, null, null, null, null, null)) {

            List<WordPair> wordPairs = new ArrayList<>();
            while (cursor.moveToNext()) {
                wordPairs.add(new WordPair(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4)));
            }
            return wordPairs;
        }
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (wordPairList == null) {
            forceLoad();
        } else {
            deliverResult(wordPairList);
        }
    }

    @Override
    public void deliverResult(List<WordPair> data) {
        wordPairList = data;
        super.deliverResult(data);
    }

    @Override
    public void onCanceled(List<WordPair> data) {
        wordPairList = data;
        super.onCanceled(data);
    }
}
