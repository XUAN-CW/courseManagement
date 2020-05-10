package com.example.coursemanagement.teacher.assignment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coursemanagement.R;

import java.util.ArrayList;
import java.util.List;

public class Assignment extends AppCompatActivity {
    SQLiteDatabase db=null;
    MySQLiteOpenHelper myDbHlper = null;
    List<News> newsList=null;
    ListView lvNewsList=null;
    NewsAdapter newsAdapter=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readFromDatabaseAndShowData();

    }

    public void readFromDatabaseAndShowData(){
        setContentView(R.layout.student_homework);
        System.out.println(222222);
        myDbHlper = new MySQLiteOpenHelper(Assignment.this);
        System.out.println(333333);
        db = myDbHlper.getReadableDatabase();
        System.out.println(55555);
        Cursor cursor = db.query(
                NewsContract.NewsEntry.ASSIGNMENT_TABLE,
                null , null , null , null , null , null);


        newsList = new ArrayList<>();
        lvNewsList = findViewById(R.id.lv_news_list);
        int titleIndex = cursor.getColumnIndex(
                NewsContract.NewsEntry.ASSIGNMENT_TITLE);
        int courseIndex = cursor.getColumnIndex(
                NewsContract.NewsEntry.ASSIGNMENT_COURSE);
        System.out.println(titleIndex);
        System.out.println(courseIndex);
        while (cursor.moveToNext()) {
            News news = new News();

            String currentTitle = cursor.getString(titleIndex);
            String currentCourse = cursor.getString(courseIndex);
            news.setAssignmentTitle(currentTitle);
            news.setCourse(currentCourse);
            newsList.add(news);

        }
        newsAdapter = new NewsAdapter(
                Assignment.this,
                R.layout.list_item,
                newsList);
        lvNewsList.setAdapter(newsAdapter);
    }

}

