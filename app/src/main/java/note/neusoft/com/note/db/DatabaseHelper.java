package note.neusoft.com.note.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * author：xxx
 * Creation date： by 2016/12/19 on 20:10.
 * description：
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(Context context) {
        super(context, "note.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Notebook(_id integer primary key autoincrement,Date varchar(20)," +
                "TimeId varchar(20),Color integer,Content TEXT,TitleColor integer,TextSize float)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
