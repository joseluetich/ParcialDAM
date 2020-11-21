package com.dam.parcialdam;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentsListAdapter extends RecyclerView.Adapter<StudentsListAdapter.StudentsViewHolder> {

    private List<Student> students;
    Activity mainActivity;

    public StudentsListAdapter(List<Student> students, Activity activity) {
        this.students = students;
        this.mainActivity = activity;
    }

    @NonNull
    @Override
    public StudentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_row, parent, false);
        StudentsViewHolder studentsViewHolder = new StudentsViewHolder(v);

        return studentsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentsViewHolder holder, int position) {
        holder.studentInfo.setTag(position);
        String st = null;
        Student student = students.get(position);

        if(student.isHeStudied()==1) {
                st = "Estudio";
            }
        else {
            st = "No estudio";
        }
        holder.studentInfo.setText(student.getName()+" - "+st);

    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class StudentsViewHolder extends RecyclerView.ViewHolder {
        TextView studentInfo;

        public StudentsViewHolder(@NonNull View itemView) {
            super(itemView);
            studentInfo = itemView.findViewById(R.id.studentInfo);
        }
    }

}