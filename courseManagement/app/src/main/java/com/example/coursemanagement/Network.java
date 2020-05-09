package com.example.coursemanagement;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public final class Network {
    //不许实例化
    private Network() {}

    interface StringFlow{
        void addMyRequestProperty(HttpURLConnection httpURLConnection);
    }

    private static HttpURLConnection communicate(StringFlow sf){
        HttpURLConnection httpURLConnection=null;
        try {
            //1,找水源--创建URL
//            URL url = new URL("https://www.baidu.com/");//放网站
            URL url = new URL("http://10.0.2.2:8080/test_war_exploded/responseDemo1");//放网站
            //2,开水闸--openConnection
            httpURLConnection = (HttpURLConnection) url.openConnection();

            sf.addMyRequestProperty(httpURLConnection);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpURLConnection;
    }

    public static HttpURLConnection login(final String account, final String password){
        HttpURLConnection httpURLConnection;
        httpURLConnection = communicate(new StringFlow(){
            @Override
            public void addMyRequestProperty(HttpURLConnection httpURLConnection) {
                httpURLConnection.addRequestProperty("operate","login");
                httpURLConnection.addRequestProperty("account",account);
                httpURLConnection.addRequestProperty("password",password);
            }
        });
        return httpURLConnection;
    }

    public static HttpURLConnection signUp(final String account, final String password, final String identity ){
        HttpURLConnection httpURLConnection=null;
        httpURLConnection = communicate(new StringFlow(){
            @Override
            public void addMyRequestProperty(HttpURLConnection httpURLConnection) {
                httpURLConnection.addRequestProperty("operate","signUp");
                httpURLConnection.addRequestProperty("account",account);
                httpURLConnection.addRequestProperty("password",password);
                httpURLConnection.addRequestProperty("identity",identity);
            }
        });
        return httpURLConnection;
    }

    public static HttpURLConnection findStudentAssignment(final String studentNumber){
        HttpURLConnection httpURLConnection=null;
        httpURLConnection = communicate(new StringFlow(){
            @Override
            public void addMyRequestProperty(HttpURLConnection httpURLConnection) {
                httpURLConnection.addRequestProperty("operate","find student assignment");
                httpURLConnection.addRequestProperty("studentNumber",studentNumber);
            }
        });
        return httpURLConnection;
    }


}
