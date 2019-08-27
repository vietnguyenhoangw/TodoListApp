package com.example.todolistapp.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todolistapp.Activity.MainActivity;
import com.example.todolistapp.Class.Todo;
import com.example.todolistapp.DataBase.DBHelper;
import com.example.todolistapp.R;


public class TodoDetailFragment extends Fragment {

    Button btnUpdate, btnDelete;
    TextView name, date, time, description;
    DBHelper dbHelper;

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
    }

    /* mapping */
    public void createWidget(View view) {
        name = view.findViewById(R.id.edtTodoName);
        date = view.findViewById(R.id.edt_date);
        time = view.findViewById(R.id.edt_time);
        description = view.findViewById(R.id.edtDescription);

        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnDelete = view.findViewById(R.id.btnDel);
    }

    /* get properties todo class for recycleview item widget*/
    public void getPropertiesTodoClassForWidget(Todo todo){
        name.setText(todo.getTodoName());
        date.setText(todo.getTodoDate());
        time.setText(todo.getTodoTime());
        description.setText(todo.getTodoDescription());
    }

    /* update database's data when click */
    public void updateClick(Todo todo) {
        Toast.makeText(getContext(), "update", Toast.LENGTH_SHORT).show();

        String nameUpdate = name.getText().toString();
        String dateUpdate = date.getText().toString();
        String timeUpdate = time.getText().toString();
        String descriptionUpdate = description.getText().toString();

        Todo todoUpdate = new Todo(todo.getID(),
                nameUpdate, dateUpdate, timeUpdate, descriptionUpdate);

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
}
