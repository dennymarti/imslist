package ch.bbcag.imslist.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Task",
        foreignKeys = @ForeignKey (
                entity = Todo.class,
                parentColumns = "id",
                childColumns = "todoId",
                onDelete = CASCADE
        )
)
public class Task {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    public int todoId;

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", todoId=" + todoId +
                ", aim='" + aim + '\'' +
                '}';
    }

    @ColumnInfo
    private String aim;

    public Task(int id, int todoId, String aim) {
        this.id = id;
        this.todoId = todoId;
        this.aim = aim;
    }

    public Task() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTodoId() {
        return todoId;
    }

    public void setTodoId(int todoId) {
        this.todoId = todoId;
    }

    public String getAim() {
        return aim;
    }

    public void setAim(String aim) {
        this.aim = aim;
    }
}
