package yamblz.hack.learning.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStreamReader;

import yamblz.hack.learning.R;

public class Helper extends SQLiteOpenHelper {

    private Context context;

    private static final String DATABASE_NAME = "words.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "words";
    public static final String
            ID = "_ID",
            DIRECTION = "DIRECTION",
            FIRST = "FIRST",
            SECOND = "SECOND",
            LEVEL = "LEVEL";

    public static final String TABLE_UNKNOWN = "unknownWords";

    public static final int DIRECTION_RU_EN = 1;
    public static final int DIRECTION_EN_RU = 2;

    public Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(this.getClass().getSimpleName(), "onCreate");
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                ID + " INTEGER PRIMARY KEY," +
                DIRECTION + " INTEGER," +
                FIRST + " TEXT," +
                SECOND + " TEXT, " +
                LEVEL + " INTEGER)");

        db.execSQL("CREATE TABLE " + TABLE_UNKNOWN + " (" +
                ID + " INTEGER PRIMARY KEY," +
                DIRECTION + " INTEGER," +
                FIRST + " TEXT)");

        try (JsonReader reader = new JsonReader(new InputStreamReader(
                context.getResources().openRawResource(R.raw.words)))) {
            SQLiteStatement statement = db.compileStatement(String.format(
                    "INSERT INTO %s (%s, %s, %s) " +
                            "VALUES (NULL,  ?,  ?)",
                    TABLE_UNKNOWN, ID, DIRECTION, FIRST));

            db.beginTransaction();

            reader.beginObject();
            while (reader.hasNext()) {
                String direction = reader.nextName();
                if (direction.equals("en")) {
                    statement.bindLong(1, DIRECTION_EN_RU);
                } else {
                    statement.bindLong(1, DIRECTION_RU_EN);
                }

                reader.beginArray();
                while (reader.hasNext()) {
                    statement.bindString(2, reader.nextString());
                    statement.executeInsert();
                }
                reader.endArray();
            }
            reader.endObject();

            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (IOException e) {
            Log.e(this.getClass().getSimpleName(), "init failed", e);
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        context = null;
        super.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
