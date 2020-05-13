package com.example.coursemanagement.teacher.ui.home;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coursemanagement.R;
import com.example.coursemanagement.student.ui.Student;
import com.example.coursemanagement.student.ui.homework.NewsContract;
import com.example.coursemanagement.teacher.ui.Teacher;

public class TeacherShowAssignment extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_show_assignment);

        System.out.println("---------------------------------------------------");
        Intent intent = getIntent();//获取 Intent 对象
        String title = intent.getStringExtra(TeacherNewsContract.NewsEntry.ASSIGNMENT_TITLE);//根据键找传过来的
        String content = intent.getStringExtra(TeacherNewsContract.NewsEntry.ASSIGNMENT_CONTENT);
        TextView show_homework_title=findViewById(R.id.show_assignment_title);
        TextView show_homework_content=findViewById(R.id.show_assignment_content);
        show_homework_title.setText(title);
        show_homework_content.setText(content);

        Button button=findViewById(R.id.show_assignment_back_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherShowAssignment.this, Teacher.class);
                //调用 activity
                startActivity(intent);
            }
        });
    }





}
