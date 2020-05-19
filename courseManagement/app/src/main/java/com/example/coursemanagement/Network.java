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
            URL url = new URL("http://47.102.200.197:8080/courseManagementServer_war_exploded/server");//放网站
//            URL url = new URL("http://10.0.2.2:8080/courseManagementServer_war_exploded/server");//放网站
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

    public static HttpURLConnection getTeacherAssignment(final String jobNumber){
        HttpURLConnection httpURLConnection=null;
        httpURLConnection = communicate(new StringFlow(){
            @Override
            public void addMyRequestProperty(HttpURLConnection httpURLConnection) {
                httpURLConnection.addRequestProperty("operate","getTeacherAssignment");
                httpURLConnection.addRequestProperty("jobNumber",jobNumber);
            }
        });
        return httpURLConnection;
    }

    public static HttpURLConnection getTeacherCourse(final String jobNumber){
        HttpURLConnection httpURLConnection=null;
        httpURLConnection = communicate(new StringFlow(){
            @Override
            public void addMyRequestProperty(HttpURLConnection httpURLConnection) {
                httpURLConnection.addRequestProperty("operate","getTeacherCourse");
                httpURLConnection.addRequestProperty("jobNumber",jobNumber);
            }
        });
        return httpURLConnection;
    }


    public static HttpURLConnection assignHomework(final String courseNumbers, final String title, final String content, final String deadline){
        HttpURLConnection httpURLConnection=null;
        httpURLConnection = communicate(new StringFlow(){
            @Override
            public void addMyRequestProperty(HttpURLConnection httpURLConnection) {
                httpURLConnection.addRequestProperty("operate","assignHomework");
                httpURLConnection.addRequestProperty("courseNumbers",courseNumbers);
                httpURLConnection.addRequestProperty("title",title);
                httpURLConnection.addRequestProperty("content",content);
                httpURLConnection.addRequestProperty("deadline",deadline);
            }
        });
        return httpURLConnection;
    }

    public static HttpURLConnection updateAssignment(final String assignmentNumber, final String title, final String content, final String deadline){
        HttpURLConnection httpURLConnection=null;
        httpURLConnection = communicate(new StringFlow(){
            @Override
            public void addMyRequestProperty(HttpURLConnection httpURLConnection) {
                httpURLConnection.addRequestProperty("operate","updateAssignment");
                httpURLConnection.addRequestProperty("assignmentNumber",assignmentNumber);
                httpURLConnection.addRequestProperty("title",title);
                httpURLConnection.addRequestProperty("content",content);
                httpURLConnection.addRequestProperty("deadline",deadline);
            }
        });
        return httpURLConnection;
    }

    public static HttpURLConnection joinClass(final String studentNumber, final String courseNumber){
        HttpURLConnection httpURLConnection=null;
        httpURLConnection = communicate(new StringFlow(){
            @Override
            public void addMyRequestProperty(HttpURLConnection httpURLConnection) {
                httpURLConnection.addRequestProperty("operate","joinClass");
                httpURLConnection.addRequestProperty("studentNumber",studentNumber);
                httpURLConnection.addRequestProperty("courseNumber",courseNumber);
            }
        });
        return httpURLConnection;
    }

    public static HttpURLConnection createClass(final String courseNumber,final String name,final String jobNumber ){
        HttpURLConnection httpURLConnection=null;
        httpURLConnection = communicate(new StringFlow(){
            @Override
            public void addMyRequestProperty(HttpURLConnection httpURLConnection) {
                httpURLConnection.addRequestProperty("operate","createClass");
                httpURLConnection.addRequestProperty("courseNumber",courseNumber);
                httpURLConnection.addRequestProperty("name",name);
                httpURLConnection.addRequestProperty("jobNumber",jobNumber);
            }
        });
        return httpURLConnection;
    }

    public static HttpURLConnection deleteAssignment(final String assignmentNumber ){
        HttpURLConnection httpURLConnection=null;
        httpURLConnection = communicate(new StringFlow(){
            @Override
            public void addMyRequestProperty(HttpURLConnection httpURLConnection) {
                httpURLConnection.addRequestProperty("operate","deleteAssignment");
                httpURLConnection.addRequestProperty("assignmentNumber",assignmentNumber);
            }
        });
        return httpURLConnection;
    }

    public static HttpURLConnection countTheNumberOfHomework(final String studentNumber ){
        HttpURLConnection httpURLConnection=null;
        httpURLConnection = communicate(new StringFlow(){
            @Override
            public void addMyRequestProperty(HttpURLConnection httpURLConnection) {
                httpURLConnection.addRequestProperty("operate","countTheNumberOfHomework");
                httpURLConnection.addRequestProperty("studentNumber",studentNumber);
            }
        });
        return httpURLConnection;
    }

}
