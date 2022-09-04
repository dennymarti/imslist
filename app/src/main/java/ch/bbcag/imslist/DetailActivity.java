package ch.bbcag.imslist;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import ch.bbcag.imslist.dao.TaskDao;
import ch.bbcag.imslist.dao.TodoDao;
import ch.bbcag.imslist.database.AppDatabase;
import ch.bbcag.imslist.model.Task;
import ch.bbcag.imslist.model.Todo;

public class DetailActivity extends AppCompatActivity {

    private Todo todo;
    private Task task;

    private Integer todoId;
    private String title;
    private String priority;

    private Date date;

    private Integer year;
    private Integer month;
    private Integer day;
    private Integer hour;
    private Integer minute;
    private Integer second;

    private String task1;

    private TextInputLayout textFieldTitle;

    private TextInputLayout textFieldDate;
    private AutoCompleteTextView textViewDate;

    private TextInputLayout textFieldTime;
    private AutoCompleteTextView textViewTime;

    private TextInputLayout textFieldPriority;
    private AutoCompleteTextView dropDownPriority;

    private TextInputLayout textFieldTask1;

    private boolean valid = true;

    private TodoDao todoDao;
    private TaskDao taskDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("Aufgaben Editor");

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple_500)));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // get instance of AppDatabase for dao's
        todoDao = AppDatabase.getInstance(this).getTodoDao();
        taskDao = AppDatabase.getInstance(this).getTaskDao();

        // get Todo object with id which is given by intent from MainActivity
        getTodo();

        // set all textfields
        textFieldTitle = findViewById(R.id.textfield_title);

        textFieldDate = findViewById(R.id.textfield_date);
        textViewDate = findViewById(R.id.textview_date);

        textFieldTime = findViewById(R.id.textfield_time);
        textViewTime = findViewById(R.id.textview_time);

        textFieldPriority = findViewById(R.id.textfield_priority);
        dropDownPriority = findViewById(R.id.dropdown_priority);

        textFieldTask1 = findViewById(R.id.textfield_task1);

        // Add click event to submit button
        Button button = findViewById(R.id.submit_button);
        button.setOnClickListener(view -> submit());

        // set autofocus for inputs
        LinearLayout container = findViewById(R.id.container);
        container.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                container.clearFocus();
                hideKeyboard();
            }
        });

        // add datepicker component for deadline
        addDatePickerComponent();

        // add timepicker component for deadline
        addTimePickerComponent();

        // add dropdown component for priority
        addDropdownComponent();

        // set values in all inputs
        setValues();
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getTodo() {
        Intent intent = getIntent();
        todoId = intent.getIntExtra("todoId", 1);

        todo = todoDao.getTodoById(todoId);
        title = todo.getTitle();
        date = todo.getDeadline();

        year = date.getYear();
        month = date.getMonth();
        day = date.getDay();
        hour = date.getHours();
        minute = date.getMinutes();
        second = date.getSeconds();

        priority = todo.getPriority();

        task = taskDao.getTaskByTodoId(todoId);
        task1 = task.getAim();
    }

    private void addDatePickerComponent() {
        // add click event to show dialog
        textViewDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(DetailActivity.this, R.style.purple);
                datePickerDialog.show();
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        year = selectedYear;
                        month = selectedMonth;
                        day = selectedDay;

                        textViewDate.setText(day + "." + month + "." + year);
                    }
                });
            }
        });
    }

    private void addTimePickerComponent() {
        // add click event to show dialog
        textViewTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                MaterialTimePicker timePicker = new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).setTitleText("Zeit wählen").build();
                timePicker.show(getSupportFragmentManager(), "Material_Time_Picker");

                timePicker.addOnPositiveButtonClickListener(timePickerClick -> {
                    textViewTime.setText(timePicker.getHour() + ":" + timePicker.getMinute());

                    // set local variables to save time for deadline
                    hour = timePicker.getHour();
                    minute = timePicker.getMinute();
                    second = 0;
                });
            }
        });
    }

    private void addDropdownComponent() {
        // dropdown items
        String[] priorities = {"Hoch", "Dringend", "Niedrig"};

        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.dropdown_priority);
        ArrayAdapter<String> adapterItems = new ArrayAdapter<>(this, R.layout.dropdown_item, priorities);
        autoCompleteTextView.setText(priority, false);
        autoCompleteTextView.setAdapter(adapterItems);
    }

    private void validateFields() {
        title = textFieldTitle.getEditText().getText().toString();
        priority = dropDownPriority.getText().toString();
        task1 = textFieldTask1.getEditText().getText().toString();

        // to avoid that title isn't empty
        if (title.isEmpty()) {
            textFieldTitle.setHelperText("Titel wird benötigt");
            valid = false;
        } else if (title.length() > 40) {
            textFieldTitle.setHelperText("Titel ist zu lang");
            valid = false;
        } else {
            textFieldTitle.setHelperText(null);
            valid = true;
        }

        // to avoid that date isn't empty
        if (year == null) {
            textFieldDate.setHelperText("Datum wird benötigt");
            valid = false;
        } else {
            textFieldDate.setHelperText(null);
            valid = true;
        }

        // to avoid that time isn't empty
        if (hour == null) {
            textFieldTime.setHelperText("Uhrzeit wird benötigt");
            valid = false;
        } else {
            textFieldTime.setHelperText(null);
            valid = true;
        }

        // to avoid that priority isn't empty
        if (priority.isEmpty()) {
            textFieldPriority.setHelperText("Priorität wird benötigt");
            valid = false;
        } else {
            textFieldPriority.setHelperText(null);
            valid = true;
        }

        // to avoid that first task isn't empty
        if (task1.isEmpty()) {
            textFieldTask1.setHelperText("Task 1 wird benötigt");
            valid = false;
        } else {
            textFieldTask1.setHelperText(null);
            valid = true;
        }
    }

    private void submit() {
        validateFields();

        if (valid) {
            // set local variable date with calendar
            Calendar calendar = new Calendar.Builder().setDate(year, month, day).setTimeOfDay(hour, minute, second).setTimeZone(TimeZone.getDefault()).build();
            date = calendar.getTime();

            // if all inputs aren't empty they can saved to database
            Log.d(CreateActivity.class.getName(), "update Todo { title: " + title + ", datetime: " + date.toString() + ", priority: " + priority + ", task 1: " + task1 + " }");

            // redirect user to MainActivity
            finish();

            // inform user that todo has been successfully created
            Toast toast = Toast.makeText(this, "Todo erfolgreich aktualisiert", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, 200);
            toast.show();

            // update Todo object
            todo.setTitle(title);
            todo.setDeadline(date);
            todo.setPriority(priority);
            todo.setChecked(false);

            // save to database and get insertion id
            todoDao.update(todo);

            // to make sure that todo has been successfully updated
            Log.d(CreateActivity.class.getName(), todo.toString());

            // update one or multiple tasks with the correct reference
            task.setAim(task1);

            // save to database
            taskDao.update(task);

            // to make sure that task has been successfully updated
            Log.d(CreateActivity.class.getName(), task.toString());
        }
    }

    private void setValues() {
        textFieldTitle.getEditText().setText(title);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern("dd.MM.yyyy");
        textViewDate.setText(simpleDateFormat.format(date));

        simpleDateFormat.applyPattern("HH:mm");
        textViewTime.setText(simpleDateFormat.format(date));

        textFieldTask1.getEditText().setText(task1);
    }
}