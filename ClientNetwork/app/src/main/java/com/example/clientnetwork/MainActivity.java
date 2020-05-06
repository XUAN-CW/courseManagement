package com.example.clientnetwork;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

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


//        createFastjson();

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

//    public void createFastjson(){
//        // 保证创建一个新文件
//        File file = new File(jsonPath);
//        System.out.println(file.exists());
//
//        if (file.exists()) { // 如果已存在,删除旧文件
//            file.delete();
//            try {
//                file.createNewFile();
//                System.out.println("---------------");
//            } catch (IOException e) {
//                System.out.println("1111111111111111111");
//            }
//        }else {
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                System.out.println("22222222222222222");
//                e.printStackTrace();
//            }
//        }
//
//
////        System.out.println(11111);
////        JSONObject object = new JSONObject();
////        //string
////        object.put("string","string");
////        //int
////        object.put("int",2);
////        //boolean
////        object.put("boolean",true);
////        //array
////        List<Integer> integers = Arrays.asList(1,2,3);
////        object.put("list",integers);
////        //null
////        object.put("null",null);
////        System.out.println(object);
////
////
////        Writer write = null;
////        try {
////            write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
////            write.write(object.toJSONString());
////            write.close();
////        } catch (UnsupportedEncodingException e) {
//////            e.printStackTrace();
////        } catch (FileNotFoundException e) {
//////            e.printStackTrace();
////        }catch (IOException e) {
//////            e.printStackTrace();
////        }
//
//    }


    private void getwebinfo() {
        try {
            //1,找水源--创建URL
//            URL url = new URL("https://www.baidu.com/");//放网站
            URL url = new URL("http://10.0.2.2:8080/test_war_exploded/responseDemo1");//放网站
            //2,开水闸--openConnection
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            //设置属性
            httpURLConnection.addRequestProperty("identity","student");
            httpURLConnection.addRequestProperty("operate","queryAssignment");
            httpURLConnection.addRequestProperty("studentNumber","001");

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
