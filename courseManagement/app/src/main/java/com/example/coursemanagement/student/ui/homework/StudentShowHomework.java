package com.example.coursemanagement.student.ui.homework;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coursemanagement.R;

public class StudentShowHomework extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_homework);

        Toast.makeText(StudentShowHomework.this, "StudentShowHomework", Toast.LENGTH_SHORT).show();

        System.out.println("---------------------------------------------------");
        Intent intent = getIntent();//获取 Intent 对象
        String title = intent.getStringExtra(NewsContract.NewsEntry.HOMEWORK_TITLE);//根据键找传过来的
        String content = intent.getStringExtra(NewsContract.NewsEntry.HOMEWORK_CONTENT);
        TextView show_homework_title=findViewById(R.id.show_homework_title);
        TextView show_homework_content=findViewById(R.id.show_homework_content);
        show_homework_title.setText(title);
        show_homework_content.setText(content);
    }





}
