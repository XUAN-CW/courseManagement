package com.example.coursemanagement.student.ui.homework;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coursemanagement.R;
import com.example.coursemanagement.student.ui.Student;
import com.example.coursemanagement.student.ui.homework.studentDatabase.NewsContract;

public class StudentShowHomework extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_show_homework);

        Toast.makeText(StudentShowHomework.this, "StudentShowHomework", Toast.LENGTH_SHORT).show();

        System.out.println("---------------------------------------------------");
        Intent intent = getIntent();//获取 Intent 对象
        String title = intent.getStringExtra(NewsContract.NewsEntry.HOMEWORK_TITLE);//根据键找传过来的
        String content = intent.getStringExtra(NewsContract.NewsEntry.HOMEWORK_CONTENT);
        TextView show_homework_title=findViewById(R.id.show_homework_title);
        TextView show_homework_content=findViewById(R.id.show_homework_content);
        show_homework_title.setText(title);
        show_homework_content.setText(content);

        Button button=findViewById(R.id.show_homework_back_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentShowHomework.this, Student.class);
                //调用 activity
                startActivity(intent);
            }
        });
    }





}
