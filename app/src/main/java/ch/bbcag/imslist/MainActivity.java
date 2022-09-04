package ch.bbcag.imslist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ch.bbcag.imslist.adapter.TodoAdapter;
import ch.bbcag.imslist.dao.TodoDao;
import ch.bbcag.imslist.database.AppDatabase;
import ch.bbcag.imslist.model.Todo;

public class MainActivity extends AppCompatActivity {

    private TodoDao todoDao;
    private static Context context;
    private static MainActivity INSTANCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple_500)));

        addCreateActivityButton();

        todoDao = AppDatabase.getInstance(this).getTodoDao();
        context = this;
        INSTANCE = this;
    }

    @Override
    protected void onResume() {
        super.onResume();

        addTodosToClickableList();
        addHelperText();
    }

    public void resume() {
        onResume();
    }

    private void addCreateActivityButton() {
        FloatingActionButton createActivity = findViewById(R.id.create_activity);
        createActivity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateActivity.class);
                startActivity(intent);
            }
        });
    }

    // to call Toast messages on external claess
    public static Context getContext() {
        return context;
    }

    // to get main methods
    public static MainActivity getInstance() {
        return INSTANCE;
    }

    private void addHelperText() {
        TextView createActivityHelperText = findViewById(R.id.create_activity_helper_text);
        if (todoDao.getRows() == 0) {
            createActivityHelperText.setVisibility(View.VISIBLE);
        } else {
            createActivityHelperText.setVisibility(View.GONE);
        }
    }

    private void addTodosToClickableList() {
        final TodoAdapter todoAdapter = new TodoAdapter(getApplicationContext(), todoDao);

        ListView listView = findViewById(R.id.todolist);
        listView.setAdapter(todoAdapter);

        AdapterView.OnItemClickListener mListClickedHandler = new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                Todo selected = (Todo) parent.getItemAtPosition(position);
                intent.putExtra("todoId", selected.getId());
                startActivity(intent);
            }
        };

        listView.setOnItemClickListener(mListClickedHandler);
    }
}