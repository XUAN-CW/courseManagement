package com.example.coursemanagement.teacher.ui.home.teacherDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.coursemanagement.Network;
import com.example.coursemanagement.R;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;


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

    SQLiteDatabase teacherSQLiteDatabase=null;

//    在getWritableDatabase或getReadableDatabase时，
//    android会去DB_NAME的数据库是否已经存在，如果存在了onCreate就不会被调用了，
//    还有就是如果你的版本VERSION比已经存在的新的话，onUpgrade会被调用
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        System.out.println("-----onCreate");
        teacherSQLiteDatabase=sqLiteDatabase;
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

        new Thread(new Runnable() {//创建子线程
            @Override
            public void run() {
                if (null==teacherSQLiteDatabase) {
                    SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.coursemanagement/databases/" + DATABASE_NAME, null);
                    teacherSQLiteDatabase = sqLiteDatabase;
                }
                System.out.println("initDb---------------");
                System.out.println("teacherSQLiteDatabase"+teacherSQLiteDatabase);

                String spFileName = mContext.getResources().getString(R.string.shared_preferences_file_name);
                String accountKey = mContext.getResources().getString(R.string.login_account_name);
                SharedPreferences spFile = mContext.getSharedPreferences(spFileName , Context.MODE_PRIVATE);
                String account = spFile.getString(accountKey , null);

                try {
                    HttpURLConnection homeworkFromDatabase = Network.getTeacherAssignment(account);
                    String data=homeworkFromDatabase.getHeaderField("data");
                    data= URLDecoder.decode(data,"UTF-8");
                    System.err.println("收到了：\n"+data);

                    Gson gson = new Gson();
                    List<Map<String, String>> listOfMaps = null;
                    listOfMaps = gson.fromJson(data, List.class);
//                    System.out.println(listOfMaps);

                    System.out.println();
                    teacherSQLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
                    teacherSQLiteDatabase.execSQL(ASSIGNMENT_TABLE);
                    for (Map<String, String> temp: listOfMaps) {
                        ContentValues values = new ContentValues();
                        values.put(TeacherNewsContract.NewsEntry.ASSIGNMENT_COURSE_NUMBER , temp.get(TeacherNewsContract.NewsEntry.ASSIGNMENT_COURSE_NUMBER));
                        values.put(TeacherNewsContract.NewsEntry.ASSIGNMENT_ASSIGNMENT_NUMBER , temp.get(TeacherNewsContract.NewsEntry.ASSIGNMENT_ASSIGNMENT_NUMBER));
                        values.put(TeacherNewsContract.NewsEntry.ASSIGNMENT_TITLE , temp.get(TeacherNewsContract.NewsEntry.ASSIGNMENT_TITLE));
                        values.put(TeacherNewsContract.NewsEntry.ASSIGNMENT_CONTENT , temp.get(TeacherNewsContract.NewsEntry.ASSIGNMENT_CONTENT));
                        values.put(TeacherNewsContract.NewsEntry.ASSIGNMENT_COURSE , temp.get(TeacherNewsContract.NewsEntry.ASSIGNMENT_COURSE));
                        values.put(TeacherNewsContract.NewsEntry.ASSIGNMENT_START_TIME , temp.get(TeacherNewsContract.NewsEntry.ASSIGNMENT_START_TIME));
                        values.put(TeacherNewsContract.NewsEntry.ASSIGNMENT_DEADLINE , temp.get(TeacherNewsContract.NewsEntry.ASSIGNMENT_DEADLINE));
                        long r = teacherSQLiteDatabase.insert(
                                TeacherNewsContract.NewsEntry.ASSIGNMENT_TABLE,
                                null ,
                                values);
                        System.out.println(values);
                    }
                    teacherSQLiteDatabase.close();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    SharedPreferences.Editor editor = spFile.edit();
                    editor.putBoolean(mContext.getResources().getString(R.string.isLoaded), true).apply();
                }
            }
        }).start();//启动子线程
    }
}
