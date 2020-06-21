package com.example.coursemanagement;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

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
//            URL url = new URL("http://47.102.200.197:8080/courseManagementServer_war_exploded/server");//放网站
            URL url = new URL("http://10.0.2.2:8080/courseManagementServer_war_exploded/server");//放网站
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
                try {
                    httpURLConnection.addRequestProperty("operate","login");
                    httpURLConnection.addRequestProperty("account",URLEncoder.encode(account,"UTF-8"));
                    httpURLConnection.addRequestProperty("password",URLEncoder.encode(password,"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
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
                try {
                    httpURLConnection.addRequestProperty("account",URLEncoder.encode(account,"UTF-8"));
                    httpURLConnection.addRequestProperty("password",URLEncoder.encode(password,"UTF-8"));
                    httpURLConnection.addRequestProperty("identity",URLEncoder.encode(identity,"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

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
                try {
                    httpURLConnection.addRequestProperty("studentNumber",URLEncoder.encode(studentNumber,"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
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
                try {
                    httpURLConnection.addRequestProperty("jobNumber",URLEncoder.encode(jobNumber,"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
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
                try {
                    httpURLConnection.addRequestProperty("jobNumber",URLEncoder.encode(jobNumber,"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
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
                try {
                    httpURLConnection.addRequestProperty("courseNumbers",URLEncoder.encode(courseNumbers,"UTF-8"));
                    httpURLConnection.addRequestProperty("title",URLEncoder.encode(title,"UTF-8"));
                    httpURLConnection.addRequestProperty("content",URLEncoder.encode(content,"UTF-8"));
                    httpURLConnection.addRequestProperty("deadline",deadline);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
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
                try {
                    httpURLConnection.addRequestProperty("assignmentNumber",URLEncoder.encode(assignmentNumber,"UTF-8"));

                    httpURLConnection.addRequestProperty("title",URLEncoder.encode(title,"UTF-8"));
                    httpURLConnection.addRequestProperty("content",URLEncoder.encode(content,"UTF-8"));
                    httpURLConnection.addRequestProperty("deadline",deadline);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
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
                try {
                    httpURLConnection.addRequestProperty("studentNumber",URLEncoder.encode(studentNumber,"UTF-8"));
                    httpURLConnection.addRequestProperty("courseNumber",URLEncoder.encode(courseNumber,"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

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
                try {
                    httpURLConnection.addRequestProperty("courseNumber",URLEncoder.encode(courseNumber,"UTF-8"));
                    httpURLConnection.addRequestProperty("name",URLEncoder.encode(name,"UTF-8"));
                    httpURLConnection.addRequestProperty("jobNumber",URLEncoder.encode(jobNumber,"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
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
                try {
                    httpURLConnection.addRequestProperty("assignmentNumber",URLEncoder.encode(assignmentNumber,"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
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
                try {
                    httpURLConnection.addRequestProperty("studentNumber",URLEncoder.encode(studentNumber,"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
        return httpURLConnection;
    }

}
