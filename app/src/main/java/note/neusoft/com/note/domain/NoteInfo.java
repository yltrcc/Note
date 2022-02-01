package note.neusoft.com.note.domain;


import java.io.Serializable;

/**
 * author：xxx
 * Creation date： by 2016/12/19 on 20:23.
 * description：Diary information
 */

public class NoteInfo implements Serializable{

    private String Date;
    private  String TimeId;
    private int Color;
    private String Content;
    private int TitleColor;
    private float TextSize;

    public NoteInfo(){

    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getTitleColor() {
        return TitleColor;
    }

    public void setTitleColor(int titleColor) {
        TitleColor = titleColor;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public int getColor() {
        return Color;
    }

    public void setColor(int color) {
        Color = color;
    }

    public String getTimeId() {
        return TimeId;
    }

    public void setTimeId(String timeId) {
        TimeId = timeId;
    }

    public float getTextSize() {
        return TextSize;
    }

    public void setTextSize(float textSize) {
        TextSize = textSize;
    }
}
