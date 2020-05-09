package com.example.coursemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.text.InputType;
import android.widget.TextView;
import android.widget.Toast;

import java.net.HttpURLConnection;

public class Login extends AppCompatActivity {

    private Boolean bPwdSwitch = false;
    private EditText etPwd;
    private EditText etAccount;
    private CheckBox cbRememberPwd;
    private Button btLogin;
    private TextView tv_signUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        readAccountAndPassword();
        controlPasswordVisible();
        controlSavePasswordAndLogin();
        goToSignUpLayout();
    }

    private void readAccountAndPassword(){
        String spFileName = getResources().getString(R.string.shared_preferences_file_name);
        String accountKey = getResources().getString(R.string.login_account_name);
        String passwordKey = getResources().getString(R.string.login_password);
        String rememberPasswordKey = getResources().getString(R.string.login_remember_password);

        SharedPreferences spFile = getSharedPreferences(spFileName , MODE_PRIVATE);
        String account = spFile.getString(accountKey , null);
        String password = spFile.getString(passwordKey , null);
        boolean rememberPassword = spFile.getBoolean(rememberPasswordKey , false);

        etPwd = findViewById(R.id.et_pwd);
        etAccount = findViewById(R.id.et_account);
        cbRememberPwd = findViewById(R.id.cb_remember_pwd);
        btLogin = (Button)findViewById(R.id.login);

        if (account != null && !TextUtils.isEmpty(account)) {
            etAccount.setText(account);
        }
        if (password != null && !TextUtils.isEmpty(password)) {
            etPwd.setText(password);
        }
        cbRememberPwd.setChecked(rememberPassword);
    }

    private void controlPasswordVisible(){
        //获取 id 为  iv_pwd_switch 的 ImageView 控件
        final ImageView ivPwdSwitch = (ImageView)findViewById(R.id.iv_pwd_switch);
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
                    etPwd.setInputType(
                            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {//不可以看到密码
                    //设置图片
                    ivPwdSwitch.setImageResource(
                            R.drawable.ic_visibility_off_black_24dp);
                    //设置文本不可见
                    etPwd.setInputType(
                            InputType.TYPE_TEXT_VARIATION_PASSWORD |
                                    InputType.TYPE_CLASS_TEXT);
                    //设置字体
                    etPwd.setTypeface(Typeface.DEFAULT);
                }
            }
        });
    }

    private void controlSavePasswordAndLogin(){
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                System.out.println(etAccount.getText().toString());
//                System.out.println(etPwd.getText().toString());
                if(etAccount.getText().toString().equals("")){
                    Toast.makeText(Login.this,
                            "请输入账号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (etPwd.getText().toString().equals("")){
                    Toast.makeText(Login.this,
                            "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }


                String spFileName = getResources().getString(R.string.shared_preferences_file_name);
                String accountKey = getResources()
                        .getString(R.string.login_account_name);
                String passwordKey = getResources().getString(R.string.login_password);
                String rememberPasswordKey = getResources()
                        .getString(R.string.login_remember_password);

                SharedPreferences spFile = getSharedPreferences(spFileName, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = spFile.edit();

                //账号一定会保存，账号是访问的网络的依据
                editor.putString(accountKey,etAccount.getText().toString()).apply();
                if (cbRememberPwd.isChecked()) {
                    editor.putString(passwordKey,etPwd.getText().toString()).apply();
                    editor.putBoolean(rememberPasswordKey, true).apply();
                } else {
                    editor.remove(passwordKey).apply();
                    editor.remove(rememberPasswordKey).apply();
                }

                ///////////// 下面是login部分 //////////////////////////////

                new Thread(new Runnable() {//创建子线程
                    @Override
                    public void run() {
                        HttpURLConnection loginResult = Network.login(etAccount.getText().toString(),
                                etPwd.getText().toString());
                        if (null != loginResult) {
                            if (loginResult.getHeaderField("status").equals("OK")) {
                                String spFileName = getResources().getString(R.string.shared_preferences_file_name);
                                SharedPreferences spFile = getSharedPreferences(spFileName, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = spFile.edit();
                                //保存身份
                                editor.putString("identity", loginResult.getHeaderField("identity")).apply();
                                //设置已登录
                                editor.putBoolean(getResources().getString(R.string.logined),true).apply();
                            } else {
                            /*
                               多线程中使用 Toast
                               方法来源：https://www.cnblogs.com/liyiran/p/4635676.html
                               解决方法：
                               在 Toast 前添加：
                                   Looper.prepare();
                               在 Toast 后添加：
                                   Looper.loop();
                             */
                                Looper.prepare();
                                Toast.makeText(Login.this,
                                        loginResult.getHeaderField("status"), Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                        } else {
                            Toast.makeText(Login.this,
                                    "联网失败", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }
                }).start();//启动子线程
            }
        });
    }

    private void goToSignUpLayout(){
        tv_signUp=(TextView)findViewById(R.id.tv_sign_up);
        tv_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //这个地方的 Intent 对象有点像 request 对象
                Intent intent = new Intent(Login.this, SignUp.class);
                //调用 activity
                startActivity(intent);
            }
        });
    }

}
