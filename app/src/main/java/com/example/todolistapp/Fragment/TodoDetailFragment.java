package com.example.todolistapp.Fragment;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.todolistapp.Activity.MainActivity;
import com.example.todolistapp.Activity.TodoCustomActivity;
import com.example.todolistapp.Adapter.TodoListAdapter;
import com.example.todolistapp.Class.Todo;
import com.example.todolistapp.DataBase.DBHelper;
import com.example.todolistapp.New.AlarmReceiver;
import com.example.todolistapp.R;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.ALARM_SERVICE;


public class TodoDetailFragment extends Fragment {

    Button btnUpdate, btnDelete, set;
    TextView name, date, time, description;
    DBHelper dbHelper;
    CheckBox checkBoxAlarm;
    ImageView DatePicker, TimePicker;

    String dateShow, timeShow, datepicked, timepicked, datetimeAlarm;
    int years, months, days;
    int hours, minutes;
    final Calendar calendar = Calendar.getInstance();

    AlarmManager alarmManager;
    PendingIntent pendingIntent;


    /* date time format java */
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    public TodoDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /* create a new object database */
        dbHelper = new DBHelper(getContext());

        /* soft keyboard */
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        /*
         * set Main Activity's toolbar title
         * because Detail Fragment and List Fragment use a same Toolbar.
         * so we can't set 2 toolbar with 2 functions to 2 fragment.
         *
         * this is not a good way. create 2 activity for 2 toolbar is a good idea.
         * */
        MainActivity.toolBarTitle.setText("Todo");

        /* get object passed by bundle*/
        Bundle bundle = getArguments();
        final Todo todo = (Todo) bundle.getSerializable("todo");


        /*
         * description comment on below
         * */
        createWidget(view);

        getPropertiesTodoClassForWidget(todo);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateClick(todo);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteClick(todo);
            }
        });

        /*
        *
        * Time, date picker
        * */

        // get time picked
        // convert from string to date, time and set on default datetime picker when you open.
        try {
            Date date = dateFormat.parse(datepicked);
            Date time = timeFormat.parse(timepicked);
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalTime localTime = time.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();

            days = localDate.getDayOfMonth();
            months = localDate.getMonthValue();
            years = localDate.getYear();

            hours = localTime.getHour();
            minutes = localTime.getMinute();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        DatePicker = view.findViewById(R.id.ivDate);
        DatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDatePicker();
            }
        });

        TimePicker = view.findViewById(R.id.ivTime);
        TimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTimePicker();
            }
        });
    }

    /* mapping */
    public void createWidget(View view) {
        name = view.findViewById(R.id.edtTodoName);
        date = view.findViewById(R.id.edt_date);
        time = view.findViewById(R.id.edt_time);
        description = view.findViewById(R.id.edtDescription);

        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnDelete = view.findViewById(R.id.btnDel);

        checkBoxAlarm = view.findViewById(R.id.checkboxAlarm);
    }

    /* get properties todo class for recycleview item widget*/
    public void getPropertiesTodoClassForWidget(Todo todo){
        name.setText(todo.getTodoName());
        date.setText(todo.getTodoDate());
        time.setText(todo.getTodoTime());
        description.setText(todo.getTodoDescription());

        datepicked = date.getText().toString();
        timepicked = time.getText().toString();
        datetimeAlarm = datepicked + " " + timepicked;

        if (todo.getStatus().equals("1")) {
            checkBoxAlarm.setChecked(true);
        }

//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//            Date date = sdf.parse(datetimeAlarm);
//            long millis = date.getTime();
//
//            alarmManager = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);
//            Intent intent = new Intent(getContext(), AlarmReceiver.class);
//
//            pendingIntent = PendingIntent.getBroadcast(getContext(),
//                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//            alarmManager.set(AlarmManager.RTC_WAKEUP, millis, pendingIntent);
//
//            Toast.makeText(getContext(), "" + millis, Toast.LENGTH_SHORT).show();
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
    }

    /* update database's data when click */
    public void updateClick(Todo todo) {
        Toast.makeText(getContext(), "update", Toast.LENGTH_SHORT).show();

        String nameUpdate = name.getText().toString();
        String dateUpdate = date.getText().toString();
        String timeUpdate = time.getText().toString();
        String descriptionUpdate = description.getText().toString();
        String status;

        if (checkBoxAlarm.isChecked()) {
            status = "1";
        }
        else {
            status = "0";
        }

        Todo todoUpdate = new Todo(todo.getID(),
                nameUpdate, dateUpdate, timeUpdate, descriptionUpdate, status);

        try {
            if (dbHelper.updateTodo(todoUpdate) > 0) {
                Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
            else {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e) {
            Toast.makeText(getContext(), "" + e, Toast.LENGTH_SHORT).show();
        }
    }

    /* delete data's data to database when click*/
    public void deleteClick(Todo todo) {
        try {
            if (dbHelper.deleteTodo(todo.getID()) > 0) {
                Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                MainActivity.toolBarTitle.setText("Todo list");
                getActivity().onBackPressed();
            }
            else {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e) {
            Toast.makeText(getContext(), "" + e, Toast.LENGTH_SHORT).show();
        }
    }

    public static TodoDetailFragment newInstace(Todo todo) {
        TodoDetailFragment todoDetailFragment = new TodoDetailFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("todo", todo);
        todoDetailFragment.setArguments(bundle);

        return  todoDetailFragment;
    }

    /*
    * date time picker
    * */
    public void setTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(android.widget.TimePicker timePicker, int i, int i1) {
                        timeShow = i + ":" + i1;

                        time.setText(timeShow);

                        hours = calendar.get(Calendar.HOUR_OF_DAY);
                        minutes = calendar.get(Calendar.MINUTE);
                    }
                }, hours, minutes, false);
        timePickerDialog.show();
    }

    public void setDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker datePicker, int i, int i1, int i2) {
                        dateShow = i2 + "/" + (i1 + 1) + "/" + i;

                        date.setText(dateShow);

                        calendar.set(i,i1,i2);
                        years = calendar.get(Calendar.YEAR);
                        months = calendar.get(Calendar.MONTH);
                        days = calendar.get(Calendar.DAY_OF_MONTH);
                    }
                }, years, months, days);
        datePickerDialog.show();
    }
}
