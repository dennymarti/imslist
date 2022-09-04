package ch.bbcag.imslist.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.bbcag.imslist.model.Task;
import ch.bbcag.imslist.model.Todo;

@Dao
public interface TodoDao {

    Date date = new Date(2020, 10, 2);
    public static  List<Todo> getAllTest() {
        List<Todo> testTodos = new ArrayList<>();
        testTodos.add(new Todo(1, "Deutsch", date, "Hoch", false));
        testTodos.add(new Todo(2, "Siuuuu", date, "Dringend", false));
        testTodos.add(new Todo(3, "Ronaldo", date, "Hoch", false));
        testTodos.add(new Todo(4, "husajdasd", date, "Dringend", false));
        testTodos.add(new Todo(5, "Ja", date, "Gering", false));
        return testTodos;
    }

    @Query("SELECT * FROM Todo ORDER BY id DESC")
    List<Todo> getAll();

    @Query("SELECT * FROM Todo WHERE id = :id LIMIT 1")
    Todo getTodoById(int id);

    @Query("SELECT * FROM Task WHERE todoId = :todoId")
    List<Task> getTasksByTodoId(int todoId);

    @Query("SELECT count(id) FROM Todo")
    int getRows();

    @Insert
    long create(Todo todo);

    @Update
    void update(Todo todo);

    @Delete
    void delete(Todo todo);

    @Query("DELETE FROM todo")
    void deleteAll();
}
