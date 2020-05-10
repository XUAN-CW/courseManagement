package com.example.coursemanagement.student.homework;

import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coursemanagement.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentHomework extends AppCompatActivity  implements AdapterView.OnItemLongClickListener {
    SQLiteDatabase db=null;
    MySQLiteOpenHelper myDbHelper = null;

    List<News> newsList=null;
    ListView lvNewsList=null;
    NewsAdapter newsAdapter=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_homework);

        readFromDatabaseAndShowData();
        addItemClickListener();
    }

    public void readFromDatabaseAndShowData(){
        System.out.println(222222);
        myDbHelper = new MySQLiteOpenHelper(StudentHomework.this);
        System.out.println(333333);
        db = myDbHelper.getReadableDatabase();
        System.out.println(55555);



        Cursor cursor = db.query(
                NewsContract.NewsEntry.HOMEWORK_TABLE,
                null , null , null , null , null , null);


        newsList = new ArrayList<>();
        lvNewsList = findViewById(R.id.lv_news_list);
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
        newsAdapter = new NewsAdapter(
                StudentHomework.this,
                R.layout.list_item,
                newsList);
        lvNewsList.setAdapter(newsAdapter);

    }


    public void addItemClickListener(){
        lvNewsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * parent : ListView
             * view : 当前行的item视图对象
             * position : 当前行的下标
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //提示当前行的应用名称
                String appName = newsList.get(position).getHomeworkTitle();
                //提示
                Toast.makeText(StudentHomework.this, appName, 0).show();
            }
        });
    }



    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view,
                                   int position, long id) {
        //删除当前行
        //删除当前行的数据
//        List.remove(position);
        //更新列表
        //lv_main.setAdapter(adapter);//显示列表, 不会使用缓存的item的视图对象
//        newsAdapter.notifyDataSetChanged();//通知更新列表, 使用所有缓存的item的视图对象

        return true;
    }
}

