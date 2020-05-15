package com.example.coursemanagement.student.ui.homework;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coursemanagement.Network;
import com.example.coursemanagement.R;

import java.net.HttpURLConnection;

public class JoinClass extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_class);
        findViewById(R.id.join_class_Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String spFileName = getResources().getString(R.string.shared_preferences_file_name);
                SharedPreferences spFile = getSharedPreferences(spFileName , MODE_PRIVATE);
                final String studentNumber= spFile.getString(getResources().getString(R.string.login_account_name), null);

                new Thread(new Runnable() {//创建子线程
                    @Override
                    public void run() {
                        try {
                            EditText join_class_EditText=findViewById(R.id.join_class_EditText);
                            HttpURLConnection homeworkFromDatabase = Network.joinClass(studentNumber,join_class_EditText.getText().toString());
                            String data=homeworkFromDatabase.getHeaderField("data");
//                    System.err.println("收到了\n"+data);
                            if(null!=data){

                            }}catch (Exception e){

                        }
                    }
                }).start();//启动子线程


            }
        });

    }
}