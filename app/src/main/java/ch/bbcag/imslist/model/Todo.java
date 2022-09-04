package ch.bbcag.imslist.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Time;
import java.util.Date;

@Entity(tableName = "Todo")
public class Todo {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private String title;

    @ColumnInfo
    private Date deadline;

    @ColumnInfo
    private String priority;

    @ColumnInfo(name = "is_checked")
    private boolean isChecked;

    public Todo(int id, String title, Date deadline, String priority, boolean isChecked) {
        this.id = id;
        this.title = title;
        this.deadline = deadline;
        this.priority = priority;
        this.isChecked = isChecked;
    }

    public Todo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDeadline() {
        return deadline;
    }

    @Override
    public String toString() {
        return title + "\n"
                + deadline + " | 3 Task/s | " + priority;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
