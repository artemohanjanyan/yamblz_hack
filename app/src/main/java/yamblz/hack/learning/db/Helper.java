package yamblz.hack.learning.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Helper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "words.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "words";
    public static final String
            ID = "_ID",
            DIRECTION = "DIRECTION",
            FIRST = "FIRST",
            SECOND = "SECOND",
            LEVEL = "LEVEL";

    public static final int DIRECTION_RU_EN = 1;
    public static final int DIRECTION_EN_RU = 2;

    public Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(this.getClass().getSimpleName(), "onCreate");
        db.execSQL("CREATE VIRTUAL TABLE " + TABLE_NAME + "(" +
                ID + " INTEGER PRIMARY KEY," +
                DIRECTION + " INTEGER," +
                FIRST + " TEXT," +
                SECOND + " TEXT, " +
                LEVEL + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
