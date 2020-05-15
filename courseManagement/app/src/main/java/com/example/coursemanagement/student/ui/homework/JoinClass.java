package com.example.coursemanagement.student.ui.homework;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coursemanagement.Login;
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
                            HttpURLConnection join_class = Network.joinClass(studentNumber,join_class_EditText.getText().toString());
                            if("OK".equals(join_class.getHeaderField("status"))){

                            }
                            Looper.prepare();
                            Toast.makeText(JoinClass.this,
                                    join_class.getHeaderField("status"), Toast.LENGTH_SHORT).show();
                            Looper.loop();
                            }catch (Exception e){

                        }
                    }
                }).start();//启动子线程


            }
        });

    }
}