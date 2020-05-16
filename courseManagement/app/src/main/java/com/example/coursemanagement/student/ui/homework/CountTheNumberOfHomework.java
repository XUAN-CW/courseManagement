package com.example.coursemanagement.student.ui.homework;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coursemanagement.R;
import com.example.coursemanagement.student.ui.Student;

public class CountTheNumberOfHomework extends AppCompatActivity {
    ImageView count_the_number_of_homework_back;
    TextView count_the_number_of_homework_TextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.count_the_number_of_homework);
        count_the_number_of_homework_back=findViewById(R.id.count_the_number_of_homework_back);
        count_the_number_of_homework_TextView=findViewById(R.id.count_the_number_of_homework_TextView);
        joinClassBackAddListener();
        setText();
    }

    public void joinClassBackAddListener(){
        count_the_number_of_homework_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CountTheNumberOfHomework.this, Student.class);
                //调用 activity
                startActivity(intent);
            }
        });
    }

    public void setText(){
        String itemDivider="aaaaa";
        Intent intent = getIntent();//获取 Intent 对象
        String statisticalResult = intent.getStringExtra("statisticalResult");//根据键找传过来的
        count_the_number_of_homework_TextView.setText(statisticalResult.replaceAll(itemDivider,"次\n"));
    }

}
