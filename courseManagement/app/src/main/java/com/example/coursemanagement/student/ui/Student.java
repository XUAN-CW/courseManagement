package com.example.coursemanagement.student.ui;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import com.example.coursemanagement.R;
import com.example.coursemanagement.student.ui.homework.MySQLiteOpenHelper;
import com.example.coursemanagement.student.ui.homework.News;
import com.example.coursemanagement.student.ui.homework.NewsAdapter;
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
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.student_navigation_home, R.id.student_navigation_dashboard, R.id.student_navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.student_nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }
}