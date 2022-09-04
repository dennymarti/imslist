package ch.bbcag.imslist.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ch.bbcag.imslist.model.Task;
import ch.bbcag.imslist.model.Todo;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task")
    List<Task> getAll();

    @Query("SELECT * FROM Todo WHERE id = :todoId")
    Todo getTodoByTaskId(int todoId);

    @Query("SELECT * FROM Task WHERE todoId = :todoId LIMIT 1")
    Task getTaskByTodoId(int todoId);

    @Insert
    long create(Task task);

    @Update
    void update(Task task);
}
