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
    private String status;
    private String notificationID;


    public Todo(int ID, String todoName, String todoDate, String todoTime, String todoDescription, String status, String notificationID) {
        this.ID = ID;
        this.todoName = todoName;
        this.todoDate = todoDate;
        this.todoTime = todoTime;
        this.todoDescription = todoDescription;
        this.status = status;
        this.notificationID = notificationID;
    }

    public Todo(String todoName, String todoDate, String todoTime, String todoDescription, String status) {
        this.todoName = todoName;
        this.todoDate = todoDate;
        this.todoTime = todoTime;
        this.todoDescription = todoDescription;
        this.status = status;
    }

    public Todo(String todoName, String todoDate, String todoTime, String todoDescription, String status, String notificationID) {
        this.todoName = todoName;
        this.todoDate = todoDate;
        this.todoTime = todoTime;
        this.todoDescription = todoDescription;
        this.status = status;
        this.notificationID = notificationID;
    }

    public Todo(int ID, String todoName, String todoDate, String todoTime, String todoDescription, String status) {
        this.ID = ID;
        this.todoName = todoName;
        this.todoDate = todoDate;
        this.todoTime = todoTime;
        this.todoDescription = todoDescription;
        this.status = status;
    }

    public String getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(String notificationID) {
        this.notificationID = notificationID;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
