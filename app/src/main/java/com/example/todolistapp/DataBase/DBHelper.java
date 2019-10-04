package com.example.todolistapp.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.todolistapp.Class.Todo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/*
 *  DataBase file, include methods inserts, update, delete...
 * */
public class DBHelper {
    String DATABASE_NAME = "todoapp-db"; /* database's name */
    private static final String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase db = null;

    Context context;

    public DBHelper(Context context) {
        this.context = context;

        processSQLite();
    }

    private void processSQLite() {
        File dbFile = context.getDatabasePath(DATABASE_NAME);
        if(!dbFile.exists()){
            try{
                CopyDatabaseFromAsset();
                Toast.makeText(context, "Copy successful !!!", Toast.LENGTH_SHORT).show();

            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    private void CopyDatabaseFromAsset() {
        try{
            InputStream databaseInputStream = context.getAssets().open(DATABASE_NAME);

            String outputStream = getPathDatabaseSystem();

            File file = new File(context.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if(!file.exists()){
                file.mkdir();
            }

            OutputStream databaseOutputStream = new FileOutputStream(outputStream);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = databaseInputStream.read(buffer)) > 0){
                databaseOutputStream.write(buffer,0,length);
            }


            databaseOutputStream.flush();
            databaseOutputStream.close();
            databaseInputStream.close();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private String getPathDatabaseSystem() {
        return context.getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }


    /* get Todo items from database */
    public ArrayList<Todo> getTodo(){
        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);

        Cursor cursor = db.rawQuery("Select * From Todo",null);

        ArrayList<Todo> arrayList = new ArrayList<>();

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String todoName = cursor.getString(1);
            String date = cursor.getString(2);
            String time = cursor.getString(3);
            String description = cursor.getString(4);
            String status = cursor.getString(5);
            arrayList.add(new Todo(id, todoName, date, time, description, status));
        }

        return arrayList;
    }

    /* insert */
    public long insertTodo(Todo todo){
        db = context.openOrCreateDatabase(DATABASE_NAME,
                context.MODE_PRIVATE,
                null);

        //"INSERT INTO Student(name,address,gender) VALUES(?,?,?)"

        ContentValues contentValues = new ContentValues();
        contentValues.put("TodoName",todo.getTodoName());
        contentValues.put("Date", todo.getTodoDate());
        contentValues.put("Time", todo.getTodoTime());
        contentValues.put("Description", todo.getTodoDescription());
        contentValues.put("Status", todo.getStatus());

        long result = db.insert("Todo",
                null,
                contentValues);

        return result;

    }

    /* update */
    public long updateTodo(Todo todo){
        db = context.openOrCreateDatabase(DATABASE_NAME,
                context.MODE_PRIVATE,
                null);

        //"UPDATE Student SET name = ? and image=? Where id = ?"

        ContentValues contentValues = new ContentValues();
        contentValues.put("TodoName",todo.getTodoName());
        contentValues.put("Date", todo.getTodoDate());
        contentValues.put("Time", todo.getTodoTime());
        contentValues.put("Description", todo.getTodoDescription());
        contentValues.put("Status", todo.getStatus());

        long result = db.update("Todo",contentValues,"ID=" + todo.getID(),null);

        return result;

    }

    /* delete */
    public long deleteTodo(int id){

        //"Delete From Student where id = ?"
        db = context.openOrCreateDatabase(DATABASE_NAME,
                context.MODE_PRIVATE,
                null);

        long result = db.delete("Todo","ID = " + id,null);

        return result;

    }
}