package com.example.coursemanagement.student.homework;

import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coursemanagement.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentHomework extends AppCompatActivity {
    SQLiteDatabase db=null;
    MySQLiteOpenHelper myDbHlper = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_homework);
        System.out.println(222222);
         myDbHlper = new MySQLiteOpenHelper(StudentHomework.this);
        System.out.println(333333);
        db = myDbHlper.getReadableDatabase();
        System.out.println(55555);



        Cursor cursor = db.query(
                NewsContract.NewsEntry.HOMEWORK_TABLE,
                null , null , null , null , null , null);


        List<News> newsList = new ArrayList<>();
        ListView lvNewsList = findViewById(R.id.lv_news_list);
        int titleIndex = cursor.getColumnIndex(
                NewsContract.NewsEntry.HOMEWORK_TITLE);
        int authorIndex = cursor.getColumnIndex(
                NewsContract.NewsEntry.HOMEWORK_COURSE);

        while (cursor.moveToNext()) {
            News news = new News();

            String currentTitle = cursor.getString(titleIndex);
            String currentCourse = cursor.getString(authorIndex);
            news.setHomeworkTitle(currentTitle);
            news.setCourse(currentCourse);
            newsList.add(news);

        }
        NewsAdapter newsAdapter = new NewsAdapter(
                StudentHomework.this,
                R.layout.list_item,
                newsList);
        lvNewsList.setAdapter(newsAdapter);

    }



}

