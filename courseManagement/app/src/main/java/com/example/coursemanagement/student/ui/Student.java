package com.example.coursemanagement.student.ui;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import com.example.coursemanagement.R;
import com.example.coursemanagement.student.ui.homework.MySQLiteOpenHelper;
import com.example.coursemanagement.student.ui.homework.News;
import com.example.coursemanagement.student.ui.homework.NewsAdapter;
import com.example.coursemanagement.student.ui.homework.StudentShowHomework;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.List;

public class Student extends AppCompatActivity{
    SQLiteDatabase db=null;
    MySQLiteOpenHelper myDbHelper = null;

    List<News> newsList=null;
    ListView lvNewsList=null;
    NewsAdapter newsAdapter=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_main);
        BottomNavigationView navView = findViewById(R.id.student_nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.student_navigation_home, R.id.student_navigation_dashboard, R.id.student_navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.student_nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        System.out.println("com.example.coursemanagement.student.ui.Teacher");
        //在这个地方初始化学生的数据库
        SQLiteDatabase db=null;
        MySQLiteOpenHelper myDbHelper = null;
        myDbHelper = new MySQLiteOpenHelper(Student.this);
        db = myDbHelper.getReadableDatabase();
        System.out.println("2-----com.example.coursemanagement.student.ui.Teacher");

        //在这个地方
        String spFileName = getResources().getString(R.string.shared_preferences_file_name);
        SharedPreferences spFile = getSharedPreferences(spFileName , MODE_PRIVATE);
        SharedPreferences.Editor editor = spFile.edit();
        editor.putBoolean(getResources().getString(R.string.isLoaded),false).apply();
    }
}