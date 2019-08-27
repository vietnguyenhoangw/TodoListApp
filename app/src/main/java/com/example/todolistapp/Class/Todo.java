package com.example.todolistapp.Class;

import java.io.Serializable;

public class Todo implements Serializable {

    /*
    *  create Todo class.
    *   constructor, getter, setter...
    * */
    private int ID;
    private String todoName;
    private String todoDate;
    private String todoTime;
    private String todoDescription;


    public Todo(int ID, String todoName, String todoDate, String todoTime, String todoDescription) {
        this.ID = ID;
        this.todoName = todoName;
        this.todoDate = todoDate;
        this.todoTime = todoTime;
        this.todoDescription = todoDescription;
    }

    public Todo(String todoName, String todoDate, String todoTime, String todoDescription) {
        this.todoName = todoName;
        this.todoDate = todoDate;
        this.todoTime = todoTime;
        this.todoDescription = todoDescription;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTodoName() {
        return todoName;
    }

    public void setTodoName(String todoName) {
        this.todoName = todoName;
    }

    public String getTodoDate() {
        return todoDate;
    }

    public void setTodoDate(String todoDate) {
        this.todoDate = todoDate;
    }

    public String getTodoTime() {
        return todoTime;
    }

    public void setTodoTime(String todoTime) {
        this.todoTime = todoTime;
    }

    public String getTodoDescription() {
        return todoDescription;
    }

    public void setTodoDescription(String todoDescription) {
        this.todoDescription = todoDescription;
    }
}
