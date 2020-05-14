package com.example.coursemanagement.teacher.ui.home;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.coursemanagement.Network;
import com.example.coursemanagement.R;

import java.net.HttpURLConnection;


public class TeacherSQLiteOpenHelper extends SQLiteOpenHelper {


    public static final String ASSIGNMENT_TABLE =
            "CREATE TABLE " +
                    TeacherNewsContract.NewsEntry.ASSIGNMENT_TABLE + " (" +
                    TeacherNewsContract.NewsEntry.ASSIGNMENT_COURSE + " VARCHAR(50), " +
                    TeacherNewsContract.NewsEntry.ASSIGNMENT_TITLE + " VARCHAR(50), " +
                    TeacherNewsContract.NewsEntry.ASSIGNMENT_CONTENT + " VARCHAR(500), " +
                    TeacherNewsContract.NewsEntry.ASSIGNMENT_START_TIME + " VARCHAR(50), " +
                    TeacherNewsContract.NewsEntry.ASSIGNMENT_DEADLINE + " VARCHAR(50),"+
                    TeacherNewsContract.NewsEntry.ASSIGNMENT_ASSIGNMENT_NUMBER + " VARCHAR(50),"+
                    TeacherNewsContract.NewsEntry.ASSIGNMENT_COURSE_NUMBER + " VARCHAR(50)"+
                    " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TeacherNewsContract.NewsEntry.ASSIGNMENT_TABLE;

    public static final int DATABASE_VERSION = 1;
//    public static final String DATABASE_NAME = "news--"+new Date()+".db";
    public static final String DATABASE_NAME = "assignment.db";
    private Context mContext;

    public TeacherSQLiteOpenHelper(Context context) {
        super(context , DATABASE_NAME , null , DATABASE_VERSION);
        mContext = context;

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        System.out.println(4444444);
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        sqLiteDatabase.execSQL(ASSIGNMENT_TABLE);
        initDb();

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase ,
                          int oldVersion , int newVersion) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        sqLiteDatabase.execSQL(ASSIGNMENT_TABLE);
        onCreate(sqLiteDatabase);
    }


    public void initDb() {

        System.out.println("initDb---------------");
        new Thread(new Runnable() {//创建子线程
            @Override
            public void run() {

                String spFileName = mContext.getResources().getString(R.string.shared_preferences_file_name);
                String accountKey = mContext.getResources().getString(R.string.login_account_name);
                SharedPreferences spFile = mContext.getSharedPreferences(spFileName , Context.MODE_PRIVATE);
                String account = spFile.getString(accountKey , null);

                try {
                    HttpURLConnection homeworkFromDatabase = Network.getTeacherAssignment(account);
                    String data=homeworkFromDatabase.getHeaderField("data");
                    System.err.println("收到了\n"+data);

                    String homeworkTitle[]=null;
                    String homeworkContent[]=null;
                    String course[]=null;
                    String startTime[]=null;
                    String deadline[]=null;
                    String courseNumber[]=null;
                    String assignmentNumber[]=null;
                    String columnDivider="bbbbb";
                    String itemDivider="aaaaa";
                    String[] temp=data.split(columnDivider);

                    for(int i=0;i<temp.length;i++){
                        String[] currColumn=temp[i].split(itemDivider);
//                        System.err.println(currColumn[0]);
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
                        if(currColumn[0].equals("courseNumber")){
                            currColumn[0]="courseNumber";
                            courseNumber=currColumn;
                            System.out.println();
                        }
                        if(currColumn[0].equals("assignmentNumber")){
                            currColumn[0]="assignmentNumber";
                            assignmentNumber=currColumn;
                        }
                    }
                    int length = 0;
                    length = Math.min(homeworkTitle.length , homeworkContent.length);
                    length = Math.min(length , course.length);
                    length = Math.min(length , startTime.length);
                    length = Math.min(length , deadline.length);



                    SQLiteDatabase sqLiteDatabase=SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.coursemanagement/databases/"+DATABASE_NAME,null);

                    sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
                    sqLiteDatabase.execSQL(ASSIGNMENT_TABLE);

                    for (int i = 1; i < length; i++) {
                        ContentValues values = new ContentValues();

                        values.put(TeacherNewsContract.NewsEntry.ASSIGNMENT_COURSE_NUMBER , courseNumber[i]);
                        System.out.println(courseNumber[i]);
                        values.put(TeacherNewsContract.NewsEntry.ASSIGNMENT_ASSIGNMENT_NUMBER , assignmentNumber[i]);
                        System.out.println(assignmentNumber[i]);

                        values.put(TeacherNewsContract.NewsEntry.ASSIGNMENT_TITLE , homeworkTitle[i]);
                        System.out.println(homeworkTitle[i]);
                        values.put(TeacherNewsContract.NewsEntry.ASSIGNMENT_CONTENT , homeworkContent[i]);
                        System.out.println(homeworkContent[i]);
                        values.put(TeacherNewsContract.NewsEntry.ASSIGNMENT_COURSE , course[i]);
                        System.out.println(course[i]);
                        values.put(TeacherNewsContract.NewsEntry.ASSIGNMENT_START_TIME , startTime[i]);
                        System.out.println(startTime[i]);
                        values.put(TeacherNewsContract.NewsEntry.ASSIGNMENT_DEADLINE , deadline[i]);
                        System.out.println(deadline[i]);

                        long r = sqLiteDatabase.insert(
                                TeacherNewsContract.NewsEntry.ASSIGNMENT_TABLE,
                                null ,
                                values);
                    }
                    SharedPreferences.Editor editor = spFile.edit();
                    editor.putBoolean(mContext.getResources().getString(R.string.isLoaded), true).apply();

                }catch (Exception e){

                }}
        }).start();//启动子线程
    }




}
