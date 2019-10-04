package com.example.todolistapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistapp.Class.Todo;
import com.example.todolistapp.OnItemClickListener;
import com.example.todolistapp.R;

import java.util.ArrayList;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.VH> {

    /*
     * create OnItemClickLister through OnItemClickListener Interface
     * */
    public static OnItemClickListener listener;

    public static void setOnItemClickListener(OnItemClickListener listener) {
        TodoListAdapter.listener = listener;
    }

    /* variable */
    Context context;
    ArrayList<Todo> todoArrayList;
    int layout;

    /* Adapter constructor */
    public TodoListAdapter(Context context, ArrayList<Todo> todoArrayList, int layout) {
        this.context = context;
        this.todoArrayList = todoArrayList;
        this.layout = layout;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layout, parent, false);

        return new VH(view);
    }

    /* interactive with recycleView user interface*/
    @Override
    public void onBindViewHolder(@NonNull VH holder, final int position) {

        /* todo's object get position
         * set text for each item
         * */
        Todo todo = todoArrayList.get(position);

        holder.todoItemName.setText(todo.getTodoName());
        holder.todoItemDescription.setText(todo.getTodoDescription());
        holder.todoItemTime.setText(todo.getTodoTime());
        holder.todoItemDate.setText(todo.getTodoDate());

        if (todo.getStatus().equals("1")) {
            holder.todoAlarm.setVisibility(View.VISIBLE);
        }
        else {
            holder.todoAlarm.setVisibility(View.GONE);
        }

        /* item clickListener */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(view, position);
                }
            }
        });


    }

    /* return ArraySize for recycle view */
    @Override
    public int getItemCount() {
        return todoArrayList.size();
    }

    /* view holder to mapping recycleView's item widget*/
    public class VH extends RecyclerView.ViewHolder{

        TextView todoItemName, todoItemDescription, todoItemDate, todoItemTime;
        ImageView todoAlarm;

        public VH(@NonNull View itemView) {
            super(itemView);

            todoItemName = itemView.findViewById(R.id.todoItemName);
            todoItemDescription = itemView.findViewById(R.id.todoItemDescription);
            todoItemDate = itemView.findViewById(R.id.todoItemDate);
            todoItemTime = itemView.findViewById(R.id.todoItemTime);
            todoAlarm = itemView.findViewById(R.id.imageViewAlarm);
        }
    }
}
