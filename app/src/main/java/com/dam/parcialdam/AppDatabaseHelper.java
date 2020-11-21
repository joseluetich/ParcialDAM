package com.dam.parcialdam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLClientInfoException;
import java.util.ArrayList;
import java.util.List;

public class AppDatabaseHelper extends SQLiteOpenHelper {
    private static AppDatabaseHelper sInstance;

    private static final String TABLE_STUDENT = "estudiante";

    //private static final String KEY_ID = "id";
    private static final String KEY_STUDENT_NAME = "studentName";
    private static final String KEY_HESTUDIED = "study";


    public AppDatabaseHelper(Context context) {
        super(context,"course-db" , null, 1);
    }


    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_STUDENT = "CREATE TABLE " + TABLE_STUDENT + "(" +
                /*KEY_ID + " INTEGER PRIMARY KEY," +*/
                KEY_STUDENT_NAME + " TEXT," +
                KEY_HESTUDIED + " INTEGER" + ")";

        db.execSQL(CREATE_TABLE_STUDENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
            onCreate(db);
        }
    }

    public static synchronized AppDatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new AppDatabaseHelper(context);
        }
        return sInstance;
    }

    public void addStudent(Student student) {

        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            //values.put(KEY_ID, student.getId());
            values.put(KEY_STUDENT_NAME, student.getName());
            System.out.println(student.getName());
            db.insert(TABLE_STUDENT,"name",values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("DEBUG", "Error while trying to add to database");
        } finally {
            db.endTransaction();
        }
    }


    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();

        String STUDENTS_QUERY = String.format("SELECT * FROM %s", TABLE_STUDENT);

        SQLiteDatabase db = sInstance.getReadableDatabase();
        Cursor cursor = db.rawQuery(STUDENTS_QUERY, null);

        try {
            while(cursor.moveToNext()) {
                Student newStudent = new Student();
                newStudent.setName(cursor.getString(cursor.getColumnIndex(KEY_STUDENT_NAME)));
                newStudent.setHeStudied(cursor.getInt(cursor.getColumnIndex(KEY_HESTUDIED)));
                students.add(newStudent);
            }
        } catch (Exception e) {
            Log.d("DEBUG", "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return students;
    }
}


