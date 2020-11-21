package com.dam.parcialdam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText nameEditText;
    RadioButton yesRadioButton, noRadioButton;
    RadioGroup studyRadioGroup;
    Button addStudentButton, seeAllButton;
    Student student;
    AppDatabaseHelper db = AppDatabaseHelper.getInstance(this);
    List<Student> students = new ArrayList<>();

    private RecyclerView studentsRecyclerView;
    private RecyclerView.Adapter studentsListAdapter;
    private RecyclerView.LayoutManager studentsListLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.nameEditText);
        yesRadioButton = findViewById(R.id.yesRadioButton);
        noRadioButton = findViewById(R.id.noRadioButton);
        studyRadioGroup = findViewById(R.id.studyRadioGroup);
        addStudentButton = findViewById(R.id.addStudentButton);
        seeAllButton = findViewById(R.id.seeAllButton);
        studentsRecyclerView = findViewById(R.id.studentsRecyclerView);


        studentsRecyclerView = findViewById(R.id.studentsRecyclerView);
        studentsRecyclerView.setHasFixedSize(true);

        studentsListLayoutManager = new LinearLayoutManager(this);
        studentsRecyclerView.setLayoutManager(studentsListLayoutManager);

        studentsListAdapter = new StudentsListAdapter(students,this);
        studentsRecyclerView.setAdapter(studentsListAdapter);

        student = new Student();

        studyRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if (id == R.id.yesRadioButton) {
                    student.setHeStudied(1);
                } else {
                    student.setHeStudied(0);
                }
            }
        });

        addStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                student.setName(nameEditText.getText().toString());
                db.addStudent(student);
            }
        });

        seeAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                students = db.getAllStudents();
                studentsListAdapter.notifyDataSetChanged();

            }
        });
    }
}