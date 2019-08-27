package com.example.todolistapp.Fragment;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.todolistapp.Activity.MainActivity;
import com.example.todolistapp.Class.Todo;
import com.example.todolistapp.DataBase.DBHelper;
import com.example.todolistapp.OnItemClickListener;
import com.example.todolistapp.R;
import com.example.todolistapp.Adapter.TodoListAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodoListFragment extends Fragment {

    DBHelper dbHelper;

    RecyclerView rvTodoList;
    ArrayList<Todo> todoArrayList;
    TodoListAdapter todoListAdapter;
    MainActivity mainActivity;

    public static Todo getItemPositionClick;

    public TodoListFragment() {
        // Required empty public constructor
    }

    public static void setOnItemClickListener(OnItemClickListener listener) {
        TodoListAdapter.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbHelper = new DBHelper(getContext());

        /* mapping listTodo recycleview */
        rvTodoList = view.findViewById(R.id.rvTodoList);

        /*
         * create TodoArray and get data from data base
         *
         * if have data -> show all item on listView widget.
         * else dont have data -> listView widget gone, new layout with text "data empty show"
         * */
        try {
            todoArrayList = dbHelper.getTodo();
        }
        catch (Exception e) {

        }

        if (todoArrayList.isEmpty()) {
            rvTodoList.setVisibility(View.GONE);
        }


        /*
         * configuration for recyclerview adapter
         * */
        todoListAdapter = new TodoListAdapter(getContext(), todoArrayList, R.layout.todo_item);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);

        rvTodoList.setLayoutManager(linearLayoutManager);
        rvTodoList.setAdapter(todoListAdapter);

        /*
         * because recycleview dont have getItem
         * so we create ItemClickListener from Adapter through OnItemClickListener Interface
         * */
        TodoListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int posotion) {
                mainActivity.getItemTodoFragment(todoArrayList.get(posotion));
            }
        });
    }

    /* recycle view OnClickListener */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
        }
    }
}
