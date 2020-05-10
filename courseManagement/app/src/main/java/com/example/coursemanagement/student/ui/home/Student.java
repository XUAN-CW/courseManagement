package com.example.coursemanagement.student.ui.home;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.coursemanagement.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;
import java.util.List;

public class Student extends AppCompatActivity implements AdapterView.OnItemLongClickListener{
    SQLiteDatabase db=null;
    MySQLiteOpenHelper myDbHelper = null;

    List<News> newsList=null;
    ListView lvNewsList=null;
    NewsAdapter newsAdapter=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

//        readFromDatabaseAndShowData();
//        addItemClickListener();


    }


    public void readFromDatabaseAndShowData(){
        System.out.println(222222);
        myDbHelper = new MySQLiteOpenHelper(Student.this);
        System.out.println(333333);
        db = myDbHelper.getReadableDatabase();
        System.out.println(55555);



        Cursor cursor = db.query(
                NewsContract.NewsEntry.HOMEWORK_TABLE,
                null , null , null , null , null , null);


        newsList = new ArrayList<>();
        try {
            lvNewsList = findViewById(R.id.lv_news_list);
        }catch (Exception e){

        }
        System.out.println(lvNewsList);

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
                Student.this,
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
                Toast.makeText(Student.this, appName, 0).show();



                //////////////////点击移除的代码如下////////////////////
                newsList.remove(position);
                //更新列表
                lvNewsList.setAdapter(newsAdapter);//显示列表, 不会使用缓存的item的视图对象
                newsAdapter.notifyDataSetChanged();//通知更新列表, 使用所有缓存的item的视图对象
                ////////////////////////////////////
            }
        });
    }



    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view,
                                   int position, long id) {
        //删除当前行
        //删除当前行的数据

//        newsList.remove(position);
//        //更新列表
//        lvNewsList.setAdapter(newsAdapter);//显示列表, 不会使用缓存的item的视图对象
//        newsAdapter.notifyDataSetChanged();//通知更新列表, 使用所有缓存的item的视图对象

        return true;
    }
}