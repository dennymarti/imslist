package ch.bbcag.imslist.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import ch.bbcag.imslist.converters.Converters;
import ch.bbcag.imslist.dao.TaskDao;
import ch.bbcag.imslist.dao.TodoDao;
import ch.bbcag.imslist.model.Task;
import ch.bbcag.imslist.model.Todo;

@Database(entities = { Task.class, Todo.class }, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    protected AppDatabase() {
    }

    /**
     * For simplicity reasons use allowMainThreadQueries().
     * This means the main thread where the ui runs gets blocked by database calls.
     * This is not the best way to do it!
     * <p>
     * Singleton pattern -> https://www.tutorialspoint.com/design_pattern/singleton_pattern.htm
     *
     * @param context Application context
     * @return Database instance
     */
    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "IMSlist").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }
    public abstract TodoDao getTodoDao();
    public abstract TaskDao getTaskDao();
}
