package note.neusoft.com.note.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import note.neusoft.com.note.domain.NoteInfo;

import java.util.ArrayList;

/**
 * author：xxx
 * Creation date： by 2016/12/19 on 20:18.
 * description：
 */

public class NoteDatabase {

    public DatabaseHelper helper;

    private String table="Notebook";

    private Context context;

    public NoteDatabase(Context context){
        super();
        helper=new DatabaseHelper(context);
        this.context=context;
    }

    /**
     * addTo:insert
     * @param noteInfo
     * @return
     */
    public boolean insert(NoteInfo noteInfo){
        String Date=noteInfo.getDate();
        String TimeId=noteInfo.getTimeId();
        int Color=noteInfo.getColor();
        String Content=noteInfo.getContent();
        int TitleColor=noteInfo.getTitleColor();
        float TextSize=noteInfo.getTextSize();
        SQLiteDatabase database=helper.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put("Date",Date);
        values.put("TimeId",TimeId);
        values.put("Color",Color);
        values.put("Content",Content);
        values.put("TitleColor",TitleColor);
        values.put("TextSize",TextSize);

        long id = database.insert(table, null, values);
        database.close();
        if(id==-1)
            return false;
        return true;
    }


    /**
     * delete:delete
     * @param TimeId
     * @return
     */
    public boolean delete(String TimeId){
        SQLiteDatabase database=helper.getWritableDatabase();

        int delete = database.delete(table, "TimeId=?", new String[]{TimeId});

        database.close();
        if(delete==0)
            return false;
        return true;
    }

    /**
     * inquiry：querty
     * @param TimeId
     */
    public NoteInfo querty(String TimeId){
        SQLiteDatabase database=helper.getReadableDatabase();
        Cursor cursor = database.query(table, new String[]{"Date","TimeId","Color","Content","TitleColor","TextSize"}, "TimeId=?", new String[]{TimeId}, null, null, null);
        NoteInfo noteInfo=new NoteInfo();
        while(cursor.moveToNext()){
            noteInfo.setDate(cursor.getString(0));
            noteInfo.setTimeId(cursor.getString(1));
            noteInfo.setColor(cursor.getInt(2));
            noteInfo.setContent(cursor.getString(3));
            noteInfo.setTitleColor(cursor.getInt(4));
            noteInfo.setTextSize(cursor.getFloat(5));
        }
        cursor.close();
        database.close();
        return noteInfo;
    }


    /**
     * modify：update
     * @param TimeId
     */
    public void update(String TimeId,NoteInfo noteInfo){
        SQLiteDatabase database=helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("Date",noteInfo.getDate());
        values.put("TimeId",noteInfo.getTimeId());
        values.put("Color",noteInfo.getColor());
        values.put("Content",noteInfo.getContent());
        values.put("TitleColor",noteInfo.getTitleColor());
        values.put("TextSize",noteInfo.getTextSize());

        database.update(table, values, "TimeId=?", new String[]{TimeId});
    }


    /**
     * queryAll：finAll
     * @return
     */
    public ArrayList<NoteInfo> finAll(){

        SQLiteDatabase database=helper.getReadableDatabase();
        Cursor cursor = database.query(table, new String[]{"Date", "TimeId", "Color","Content","TitleColor","TextSize"},
                null, null, null, null, null);
        ArrayList<NoteInfo> noteInfos=new ArrayList<>();
        while(cursor.moveToNext()){
            NoteInfo noteInfo=new NoteInfo();
            noteInfo.setDate(cursor.getString(0));
            noteInfo.setTimeId(cursor.getString(1));
            noteInfo.setColor(cursor.getInt(2));
            noteInfo.setContent(cursor.getString(3));
            noteInfo.setTitleColor(cursor.getInt(4));
            noteInfo.setTextSize(cursor.getFloat(5));
            noteInfos.add(noteInfo);
        }
        cursor.close();
        database.close();
        return noteInfos;

    }
}
