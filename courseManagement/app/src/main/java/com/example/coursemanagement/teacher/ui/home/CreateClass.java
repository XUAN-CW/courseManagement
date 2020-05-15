package com.example.coursemanagement.teacher.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coursemanagement.Network;
import com.example.coursemanagement.R;
import com.example.coursemanagement.teacher.ui.Teacher;

import java.net.HttpURLConnection;

public class CreateClass extends AppCompatActivity {
    Button teacher_create_class_Button=null;
    ImageView teacher_create_class_back=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_create_class);
        teacher_create_class_Button=findViewById(R.id.teacher_create_class_Button);
        teacher_create_class_back=findViewById(R.id.teacher_create_class_back);

        createClassButtonAddListener();
        createClassBackAddListener();
    }

    public void createClassButtonAddListener(){
        teacher_create_class_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String spFileName = getResources().getString(R.string.shared_preferences_file_name);
                SharedPreferences spFile = getSharedPreferences(spFileName , MODE_PRIVATE);
                final String jobNumber= spFile.getString(getResources().getString(R.string.login_account_name), null);

                new Thread(new Runnable() {//创建子线程
                    @Override
                    public void run() {
                        try {
                            EditText teacher_create_class_EditText=findViewById(R.id.teacher_create_class_EditText);
                            HttpURLConnection join_class = Network.joinClass(jobNumber,teacher_create_class_EditText.getText().toString());
                            if("OK".equals(join_class.getHeaderField("status"))){

                            }
                            Looper.prepare();
                            Toast.makeText(CreateClass.this,
                                    join_class.getHeaderField("status"), Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }catch (Exception e){

                        }
                    }
                }).start();//启动子线程


            }
        });
    }

    public void createClassBackAddListener(){
        teacher_create_class_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateClass.this, Teacher.class);

                //调用 activity
                startActivity(intent);
            }
        });
    }
}
