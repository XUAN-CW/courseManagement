package com.example.clientnetwork;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 注意事项：
 *  1.声明网络权限，在 AndroidManifest.xml 中添加：
 *    <uses-permission android:name="android.permission.INTERNET" />
 *    来源：https://www.jianshu.com/p/5eee1ef02700
 *  2.在 Android 中如何发送 HTTP 请求
 *    https://blog.csdn.net/BugGodFather/article/details/93234755
 *  3.模拟器默认把127.0.0.1和localhost当做本身了，在模拟器上可以用10.0.2.2代替127.0.0.1和localhost，
 *    另外如果是在局域网环境可以用 192.168.0.x或者192.168.1.x(根据具体配置)连接本机。
 *    https://blog.csdn.net/xulianboblog/article/details/51335361
 */

public class MainActivity extends AppCompatActivity {

    private Button btn;//点击按钮访问

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.toSever);//绑定ID
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//监听按钮
                new Thread(new Runnable() {//创建子线程
                    @Override
                    public void run() {
                        getwebinfo();//把路径选到MainActivity中
                    }
                }).start();//启动子线程
            }
        });
    }

    private void getwebinfo() {
        try {
            //1,找水源--创建URL
//            URL url = new URL("https://www.baidu.com/");//放网站
            URL url = new URL("http://10.0.2.2:8080/http://localhost:8080/server_war_exploded//server");//放网站
            //2,开水闸--openConnection
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.addRequestProperty("identity","student");

            //3，建管道--InputStream
            InputStream inputStream = httpURLConnection.getInputStream();
            //4，建蓄水池蓄水-InputStreamReader
            InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
            //5，水桶盛水--BufferedReader
            BufferedReader bufferedReader = new BufferedReader(reader);

            StringBuffer buffer = new StringBuffer();
            String temp = null;

            while ((temp = bufferedReader.readLine()) != null) {
                //取水--如果不为空就一直取
                buffer.append(temp);
            }
            bufferedReader.close();//记得关闭
            reader.close();
            inputStream.close();
            Log.e("MAIN",buffer.toString());//打印结果

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
