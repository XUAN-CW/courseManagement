package com.example.coursemanagement.teacher.ui;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.coursemanagement.R;
import com.example.coursemanagement.teacher.ui.home.teacherDatabase.TeacherSQLiteOpenHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Teacher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        //在这个地方
        String spFileName = getResources().getString(R.string.shared_preferences_file_name);
        SharedPreferences spFile = getSharedPreferences(spFileName , MODE_PRIVATE);
        SharedPreferences.Editor editor = spFile.edit();
        editor.putBoolean(getResources().getString(R.string.isLoaded),false).apply();


        System.out.println("com.example.coursemanagement.teacher.ui.Teacher");
        //在这个地方初始化教师的数据库
        SQLiteDatabase db=null;
        TeacherSQLiteOpenHelper myDbHelper = null;
        myDbHelper = new TeacherSQLiteOpenHelper(Teacher.this);
        db = myDbHelper.getReadableDatabase();
        System.out.println(11111);

        new TeacherSQLiteOpenHelper(Teacher.this).initDb();

        while (!spFile.getBoolean(getResources().getString(R.string.isLoaded) , false)){}

    }
}
