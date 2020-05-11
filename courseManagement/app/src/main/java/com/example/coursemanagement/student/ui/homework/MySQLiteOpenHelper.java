package com.example.coursemanagement.student.ui.homework;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.coursemanagement.Network;
import com.example.coursemanagement.R;

import java.net.HttpURLConnection;


public class MySQLiteOpenHelper extends SQLiteOpenHelper {


    private static final String HOMEWORK_TABLE =
            "CREATE TABLE " +
                    NewsContract.NewsEntry.HOMEWORK_TABLE + " (" +
                    NewsContract.NewsEntry.HOMEWORK_COURSE + " VARCHAR(50), " +
                    NewsContract.NewsEntry.HOMEWORK_TITLE + " VARCHAR(50), " +
                    NewsContract.NewsEntry.HOMEWORK_CONTENT + " VARCHAR(500), " +
                    NewsContract.NewsEntry.HOMEWORK_START_TIME + " VARCHAR(50), " +
                    NewsContract.NewsEntry.HOMEWORK_DEADLINE + " VARCHAR(50)"+" )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + NewsContract.NewsEntry.HOMEWORK_TABLE;

    public static final int DATABASE_VERSION = 1;
//    public static final String DATABASE_NAME = "news--"+new Date()+".db";
    public static final String DATABASE_NAME = "homework.db";
    private Context mContext;

    public MySQLiteOpenHelper(Context context) {
        super(context , DATABASE_NAME , null , DATABASE_VERSION);
        mContext = context;

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        System.out.println(4444444);
        //sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        sqLiteDatabase.execSQL(HOMEWORK_TABLE);
        initDb(sqLiteDatabase);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase ,
                          int oldVersion , int newVersion) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }


    private void initDb(final SQLiteDatabase sqLiteDatabase) {
        System.out.println("initDb---------------");
        new Thread(new Runnable() {//创建子线程
            @Override
            public void run() {

                String spFileName = mContext.getResources().getString(R.string.shared_preferences_file_name);
                String accountKey = mContext.getResources().getString(R.string.login_account_name);
                SharedPreferences spFile = mContext.getSharedPreferences(spFileName , Context.MODE_PRIVATE);
                String account = spFile.getString(accountKey , null);
                account="001";

                try {
                    HttpURLConnection homeworkFromDatabase = Network.findStudentAssignment(account);
                    String data=homeworkFromDatabase.getHeaderField("data");
                    System.err.println("收到了\n"+data);
                    String homeworkTitle[]=null;
                    String homeworkContent[]=null;
                    String course[]=null;
                    String startTime[]=null;
                    String deadline[]=null;
                    String columnDivider="bbbbb";
                    String itemDivider="aaaaa";
                    String emptyResultSet="|-_-!|";
                    String[] columns=data.split(columnDivider);

                    for(int i=0;i<columns.length;i++){
                        String[] currColumn=columns[i].split(itemDivider);
                        System.out.println(currColumn[0]);
                        if(currColumn[0].equals("title")){
                            currColumn[0]="homeworkTitle";
                            homeworkTitle=currColumn;
                        }
                        if(currColumn[0].equals("content")){
                            currColumn[0]="homeworkContent";
                            homeworkContent=currColumn;
                        }
                        if(currColumn[0].equals("name")){
                            currColumn[0]="course";
                            course=currColumn;
                        }
                        if(currColumn[0].equals("startTime")){
                            currColumn[0]="startTime";
                            startTime=currColumn;
                        }
                        if(currColumn[0].equals("deadline")){
                            currColumn[0]="deadline";
                            deadline=currColumn;
                        }
                    }
                    int length = 0;
                    length = Math.min(homeworkTitle.length , homeworkContent.length);
                    length = Math.min(length , course.length);
                    length = Math.min(length , startTime.length);
                    length = Math.min(length , deadline.length);
                    for (int i = 1; i < length; i++) {
                        ContentValues values = new ContentValues();
                        values.put(NewsContract.NewsEntry.HOMEWORK_TITLE , homeworkTitle[i]);
                        System.out.println(homeworkTitle[i]);
                        values.put(NewsContract.NewsEntry.HOMEWORK_CONTENT , homeworkContent[i]);
                        System.out.println(homeworkContent[i]);
                        values.put(NewsContract.NewsEntry.HOMEWORK_COURSE , course[i]);
                        System.out.println(course[i]);
                        values.put(NewsContract.NewsEntry.HOMEWORK_START_TIME , startTime[i]);
                        System.out.println(startTime[i]);
                        values.put(NewsContract.NewsEntry.HOMEWORK_DEADLINE , deadline[i]);
                        System.out.println(deadline[i]);
                        long r = sqLiteDatabase.insert(
                                NewsContract.NewsEntry.HOMEWORK_TABLE ,
                                null ,
                                values);
                    }
                }catch (Exception e){

                }}
        }).start();//启动子线程
    }
}
