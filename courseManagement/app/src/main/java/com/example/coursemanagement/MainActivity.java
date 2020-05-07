package com.example.coursemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.text.InputType;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Boolean bPwdSwitch = false;
    private EditText etPwd;
    private EditText etAccount;
    private CheckBox cbRememberPwd;
    private Button btLogin;
    private TextView tv_signUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readPassword();
        passwordVisible();
        loginAndSavePassword();
        signUp();
    }

    private void readPassword(){
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

    private void passwordVisible(){
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

    private void loginAndSavePassword(){
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String spFileName = getResources()
                        .getString(R.string.shared_preferences_file_name);
                System.out.println(spFileName);
                String accountKey = getResources()
                        .getString(R.string.login_account_name);
                String passwordKey = getResources()
                        .getString(R.string.login_password);
                String rememberPasswordKey = getResources()
                        .getString(R.string.login_remember_password);

                SharedPreferences spFile = getSharedPreferences(
                        spFileName,
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = spFile.edit();

                //账号一定会保存，账号是访问的网络的依据
                String account = etAccount.getText().toString();
                editor.putString(accountKey,account).apply();

                if (cbRememberPwd.isChecked()) {
                    String password = etPwd.getText().toString();
                    editor.putString(passwordKey,password).apply();
                    editor.putBoolean(rememberPasswordKey, true).apply();
                } else {
                    editor.remove(passwordKey).apply();
                    editor.remove(rememberPasswordKey).apply();
                }
            }
        });
    }

    private void signUp(){
        tv_signUp=(TextView)findViewById(R.id.tv_sign_up);
        tv_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(11111);
            }
        });
    }

}
