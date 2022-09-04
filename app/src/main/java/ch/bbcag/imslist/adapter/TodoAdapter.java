package ch.bbcag.imslist.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

import ch.bbcag.imslist.MainActivity;
import ch.bbcag.imslist.R;
import ch.bbcag.imslist.dao.TodoDao;
import ch.bbcag.imslist.model.Todo;

public class TodoAdapter extends ArrayAdapter<Todo> {

    private TodoDao todoDao;

    public TodoAdapter(@NonNull Context context, TodoDao todoDao) {
        super(context, R.layout.todo_item, todoDao.getAll());
        this.todoDao = todoDao;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.todo_item, parent, false);

        RelativeLayout todoListItem = rowView.findViewById(R.id.todo_listitem);
        RelativeLayout todoMenu = rowView.findViewById(R.id.todo_menu);

        TextView todoTitle = rowView.findViewById(R.id.todo_title);
        TextView todoContent = rowView.findViewById(R.id.todo_content);

        Button todoButton = rowView.findViewById(R.id.todo_button);
        Button todoButtonFinished = rowView.findViewById(R.id.todo_finished_button);
        Button todoDeleteButton = rowView.findViewById(R.id.todo_delete_button);

        Todo todo = getItem(position);

        if (todo.isChecked()) {
            todoListItem.setBackgroundColor(Color.parseColor("#D0C8EF"));
            todoButtonFinished.setText("Undo");
        } else {
            todoButtonFinished.setText("Erledigt");
        }

        todoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                todoMenu.setVisibility(View.VISIBLE);
                todoButton.setVisibility(View.INVISIBLE);
            }
        });

        todoButtonFinished.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (todo.isChecked()) {
                    todo.setChecked(false);
                    todoListItem.setBackgroundColor(Color.WHITE);
                    todoButtonFinished.setText("Erledigen");
                } else {
                    todo.setChecked(true);
                    todoListItem.setBackgroundColor(Color.parseColor("#D0C8EF"));
                    todoButtonFinished.setText("Undo");
                }

                todoDao.update(todo);
                todoMenu.setVisibility(View.VISIBLE);
                todoButton.setVisibility(View.INVISIBLE);
            }
        });

        todoDeleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(MainActivity.getContext(), "Todo erfolgreich gelöscht", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 0, 200);
                toast.show();

                todoDao.delete(todo);

                MainActivity.getInstance().resume();
            }
        });

        todoTitle.setText(todo.getTitle());

        Date deadline = todo.getDeadline();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern("dd.MM.yyyy, HH:mm");
        todoContent.setText(simpleDateFormat.format(deadline) + " | " + todo.getPriority());

        return rowView;
    }
}
