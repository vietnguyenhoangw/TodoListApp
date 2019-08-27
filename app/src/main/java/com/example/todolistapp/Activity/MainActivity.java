package com.example.todolistapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todolistapp.Class.Todo;
import com.example.todolistapp.DataBase.DBHelper;
import com.example.todolistapp.Fragment.TodoDetailFragment;
import com.example.todolistapp.Fragment.TodoListFragment;
import com.example.todolistapp.R;

public class MainActivity extends AppCompatActivity {

    /* Variable */
    DBHelper dbHelper;  /*data base*/

    Toolbar toolbar;
    ImageView imgToolbarleft, imgToolbarRight;

    public static TextView toolBarTitle;

    /*
     * MainActivity create.
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* create database */
        dbHelper= new DBHelper(MainActivity.this);

        /* layout screen up when soft keyboard is show */
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        createWidget();
        toolbarCustom(true);
        displayFragment();
    }

    /* mapping xml widget's id with variable created on top*/
    public void createWidget() {
        toolbar= findViewById(R.id.toolbarTodo);
        toolBarTitle = toolbar.findViewById(R.id.toolbarTitle);
        imgToolbarRight = toolbar.findViewById(R.id.iv_right);
        imgToolbarleft = toolbar.findViewById(R.id.iv_left);
    }

    /* toolbar create on MainActivity, setClickListenner for back icon, add icon ... */
    public boolean toolbarCustom(boolean flag) {
        toolBarTitle.setText("Todo list");

        if (flag) {
            imgToolbarleft.setVisibility(View.GONE);
            imgToolbarRight.setVisibility(View.VISIBLE);
            imgToolbarRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, TodoCustomActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
        else {
            imgToolbarRight.setVisibility(View.GONE);
            imgToolbarleft.setVisibility(View.VISIBLE);
            imgToolbarleft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }

        return true;
    }

    /* display a fragment on MainActivity */
    public void displayFragment() {
        Fragment fragment = new TodoListFragment();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content, fragment);

        ft.commit();
    }

    /* click on item's recycle view, todolist fragment */
    public void getItemTodoFragment(Todo todo) {
        toolbarCustom(false);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, TodoDetailFragment.newInstace(todo));

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /* back button pressed custom*/
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        toolBarTitle.setText("Todo list");
        toolbarCustom(true);
    }
}
