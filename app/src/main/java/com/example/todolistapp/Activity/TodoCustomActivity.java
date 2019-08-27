package com.example.todolistapp.Activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.todolistapp.Class.Todo;
import com.example.todolistapp.DataBase.DBHelper;
import com.example.todolistapp.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TodoCustomActivity extends AppCompatActivity {

    /* Variable */
    DBHelper dbHelper;

    ImageView imDate, imTime, imToolbarLeft, imToolbarRight;
    String dateShow, timeShow;
    EditText edtDate, edtTime, edtTodoName, edtDescription;

    int years, months, days;
    int hours, minutes;
    final Calendar calendar = Calendar.getInstance();

    /* date time format java */
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    Date currentDate = Calendar.getInstance().getTime();

    Toolbar toolbar;
    TextView toolbarTitle;


    /* this.Activity create */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_custom);

        /* database */
        dbHelper = new DBHelper(TodoCustomActivity.this);

        /* screen up, when soft keyboard on*/
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        createWidget();

        /* get current time */
        years = calendar.get(Calendar.YEAR);
        months = calendar.get(Calendar.MONTH);
        days = calendar.get(calendar.DAY_OF_MONTH);

        imDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setImDateClick();
            }
        });

        imTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setImTimeClick();
            }
        });

        imToolbarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setImToolbarLeft();
            }
        });

        imToolbarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setImToolbarRight();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setImToolbarLeft();
            }
        });

        edtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setImTimeClick();
            }
        });

        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setImDateClick();
            }
        });
    }

    /* mapping xml widget id to variable */
    public void createWidget() {
        imDate = findViewById(R.id.ivDate);
        imTime = findViewById(R.id.ivTime);
        edtDate = findViewById(R.id.edt_date);
        edtDate.setText(dateFormat.format(currentDate));
        edtDate.setFocusable(false);
        edtTime = findViewById(R.id.edt_time);
        edtTime.setText(timeFormat.format(currentDate));
        edtTime.setFocusable(false);
        edtTodoName = findViewById(R.id.edtTodoName);
        edtDescription = findViewById(R.id.edtDescription);

        /* toolbar */
        toolbar = findViewById(R.id.toolbarTodoCustom);
        toolbarTitle = toolbar.findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("Add Todo");
        imToolbarLeft = toolbar.findViewById(R.id.iv_left);
        imToolbarRight = toolbar.findViewById(R.id.iv_right);
    }

    /* setOnClickListener for every button... */
    public void setImDateClick() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(TodoCustomActivity.this,
                new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                dateShow = i2 + "/" + (i1 + 1) + "/" + i;

                edtDate.setText(dateShow);

                calendar.set(i,i1,i2);
                years = calendar.get(Calendar.YEAR);
                months = calendar.get(Calendar.MONTH);
                days = calendar.get(Calendar.DAY_OF_MONTH);
            }
        }, years, months, days);
        datePickerDialog.show();
    }

    public void setImTimeClick() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(TodoCustomActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        timeShow = i + ":" + i1;

                        edtTime.setText(timeShow);

                        hours = calendar.get(Calendar.HOUR_OF_DAY);
                        minutes = calendar.get(Calendar.MINUTE);
                    }
                }, hours, minutes, true);
        timePickerDialog.show();
    }

    public void setImToolbarLeft() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TodoCustomActivity.this);

        builder.setTitle("Close");
        builder.setIcon(R.drawable.ic_close_black_24dp);

        builder.setMessage("Close Add Todo without save?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(TodoCustomActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void setImToolbarRight() {

        String name = edtTodoName.getText().toString();
        String date = edtDate.getText().toString();
        String time = edtTime.getText().toString();
        String description = edtDescription.getText().toString();

        if (name.trim().length() <= 0) {
            edtTodoName.setError("Provide a task name.");
        }
        else{
            Todo todo = new Todo(name, date, time, description);

            if (dbHelper.insertTodo(todo) > 0) {
                Intent intent = new Intent(TodoCustomActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*back button pressed custom*/
    @Override
    public void onBackPressed() {
        setImToolbarLeft();
    }
}
