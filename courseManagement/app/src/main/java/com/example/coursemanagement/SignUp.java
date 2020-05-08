package com.example.coursemanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Looper;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.net.HttpURLConnection;

public class SignUp extends AppCompatActivity {
    private Boolean bPwdSwitch = false;
    private String identity="student";
    private EditText sign_up_account;
    private EditText sign_up_pwd;
    private RadioButton sign_up_student;
    private RadioButton sign_up_teacher;
    private Button sign_up_and_login;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        initWidget();
        controlPasswordVisible();
        controlChoose();
        signUpAndLogin();
    }

    private void initWidget(){
        sign_up_student = findViewById(R.id.sign_up_student);
        sign_up_teacher = findViewById(R.id.sign_up_teacher);
        sign_up_and_login = findViewById(R.id.sign_up_and_login);
        sign_up_account = findViewById(R.id.sign_up_account);
        sign_up_pwd = findViewById(R.id.sign_up_pwd);
    }

    private void controlPasswordVisible(){
        //获取 id 为  iv_pwd_switch 的 ImageView 控件
        final ImageView ivPwdSwitch = (ImageView)findViewById(R.id.sign_up_pwd_switch);
//        //看看是不是真的获取到了 ImageView 控件
//        Toast.makeText(MainActivity.this, "here is a " + ivPwdSwitch.getId(),
//                Toast.LENGTH_SHORT).show();
        //获取 id 为  et_pwd 的 EditText 控件
        //设置鼠标监听
        ivPwdSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bPwdSwitch = !bPwdSwitch;//点击之后切换状态
                //可以看到密码
                if (bPwdSwitch) {
                    //设置图片
                    ivPwdSwitch.setImageResource(
                            R.drawable.ic_visibility_black_24dp);
                    //设置文本可见
                    sign_up_pwd.setInputType(
                            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {//不可以看到密码
                    //设置图片
                    ivPwdSwitch.setImageResource(
                            R.drawable.ic_visibility_off_black_24dp);
                    //设置文本不可见
                    sign_up_pwd.setInputType(
                            InputType.TYPE_TEXT_VARIATION_PASSWORD |
                                    InputType.TYPE_CLASS_TEXT);
                    //设置字体
                    sign_up_pwd.setTypeface(Typeface.DEFAULT);
                }
            }
        });
    }

    private void controlChoose(){


        sign_up_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sign_up_student.setChecked(true);
                sign_up_teacher.setChecked(false);
                identity="student";
            }
        });

        sign_up_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sign_up_student.setChecked(false);
                sign_up_teacher.setChecked(true);
                identity="teacher";
            }
        });
    }

    private void signUpAndLogin(){
        sign_up_and_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(sign_up_account.getText().toString().equals("")){
                    Toast.makeText(SignUp.this,
                            "请输入账号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (sign_up_pwd.getText().toString().equals("")){
                    Toast.makeText(SignUp.this,
                            "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                ///////////// 下面是 signUp 部分 //////////////////////////////

                new Thread(new Runnable() {//创建子线程
                    @Override
                    public void run() {

                        String identity= sign_up_student.isChecked()?"student":"teacher";
                        HttpURLConnection signUpResult = Network.signUp(sign_up_account.getText().toString(),
                                sign_up_pwd.getText().toString(),identity);

                        if(null!=signUpResult){
                            if (signUpResult.getHeaderField("status").equals("OK")){
                                //能够登录才保存
                                String spFileName = getResources().getString(R.string.shared_preferences_file_name);

                                String accountKey = getResources().getString(R.string.login_account_name);
                                String passwordKey = getResources().getString(R.string.login_password);
                                String identityKey = getResources().getString(R.string.identity);

                                SharedPreferences spFile = getSharedPreferences(spFileName, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = spFile.edit();
                                //账号一定会保存，账号是访问的网络的依据
                                editor.putString(accountKey,sign_up_account.getText().toString()).apply();
                                editor.putString(passwordKey,sign_up_pwd.getText().toString()).apply();
                                editor.putString(identityKey,identity).apply();

//                            Looper.prepare();
//                            Toast.makeText(SignUp.this,
//                                    "注册成功", Toast.LENGTH_SHORT).show();
//                            Looper.loop();

                                //回到登录页面
                                Intent intent = new Intent(SignUp.this, MainActivity.class);
                                //调用 activity
                                startActivity(intent);



                            } else {
                                Looper.prepare();
                                Toast.makeText(SignUp.this,
                                        signUpResult.getHeaderField("status"), Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                        }else {
                            Toast.makeText(SignUp.this,
                                    "联网失败", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }




                    }
                }).start();//启动子线程
            }
        });

    }



}
