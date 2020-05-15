package com.example.coursemanagement.teacher.ui.home;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coursemanagement.R;
import com.example.coursemanagement.teacher.ui.Teacher;
import com.example.coursemanagement.teacher.ui.dashboard.datepicker.CustomDatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TeacherShowAssignment extends AppCompatActivity  implements View.OnClickListener{
    Button teacher_modify_assignment_commit_button =null;
    Button show_assignment_back_button=null;
    Button teacher_modify_assignment_start=null;
    private TextView mTvSelectedDate, mTvSelectedTime;
    private CustomDatePicker mDatePicker, mTimerPicker;
    AlertDialog alertDialog3;

    EditText et_title=null;
    EditText et_content=null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_show_assignment);

//        System.out.println("TeacherShowAssignment");
        initViews();
        initTitleAndContent();
        setShowAssignmentBackButtonAddListener();
        teacherModifyAssignmentButtonAddListener();
        setTeacherEditAssignmentStart();
        System.out.print("TeacherShowAssignment");
        initTimerPicker();
    }

    public  void initViews(){
        et_title=findViewById(R.id.show_assignment_title);
        et_content=findViewById(R.id.show_assignment_content);
        show_assignment_back_button=findViewById(R.id.show_assignment_back_button);
        teacher_modify_assignment_start=findViewById(R.id.teacher_modify_assignment_start);
        teacher_modify_assignment_commit_button =findViewById(R.id.teacher_modify_assignment_commit);
        mTvSelectedTime = findViewById(R.id.teacher_modify_assignment_commit);

    }

    public void initTitleAndContent(){
        Intent intent = getIntent();//获取 Intent 对象
        String title = intent.getStringExtra(TeacherNewsContract.NewsEntry.ASSIGNMENT_TITLE);//根据键找传过来的
        String content = intent.getStringExtra(TeacherNewsContract.NewsEntry.ASSIGNMENT_CONTENT);

        et_title.setText(title);
        et_content.setText(content);
    }

    private void setShowAssignmentBackButtonAddListener(){
        show_assignment_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherShowAssignment.this, Teacher.class);
                //调用 activity
                startActivity(intent);
            }
        });
    }

    public void setTeacherEditAssignmentStart(){
        teacher_modify_assignment_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teacher_modify_assignment_start.setVisibility(View.GONE);
                teacher_modify_assignment_commit_button.setVisibility(View.VISIBLE);
                et_title.setFocusableInTouchMode(true);
                et_content.setFocusableInTouchMode(true);
            }
        });

    }

    public void teacherModifyAssignmentButtonAddListener(){
        teacher_modify_assignment_commit_button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        mTimerPicker.show(mTvSelectedTime.getText().toString());
    }


    private void initTimerPicker() {
//        String beginTime = DateFormatUtils.long2Str(System.currentTimeMillis(), true);
        Date date = new Date();
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //创建Calendar实例
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);   //设置当前时间
        String beginTime=format.format(cal.getTime());
        cal.add(Calendar.YEAR, 3);  //在当前时间基础上加3年
        String endTime =format.format(cal.getTime());
//        mTvSelectedTime.setText(beginTime);
        // 通过日期字符串初始化日期，格式请用：yyyy-MM-dd HH:mm
        mTimerPicker = new CustomDatePicker(TeacherShowAssignment.this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(final long timestamp) {
                
            }
        }, beginTime, endTime);
        // 允许点击屏幕或物理返回键关闭
        mTimerPicker.setCancelable(true);
        // 显示时和分
        mTimerPicker.setCanShowPreciseTime(true);
        // 允许循环滚动
        mTimerPicker.setScrollLoop(true);
        // 允许滚动动画
        mTimerPicker.setCanShowAnim(true);
    }

}
