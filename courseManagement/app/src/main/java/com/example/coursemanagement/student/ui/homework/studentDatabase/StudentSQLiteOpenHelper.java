package com.example.coursemanagement.student.ui.homework.studentDatabase;

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


public class StudentSQLiteOpenHelper extends SQLiteOpenHelper {

    SQLiteDatabase studentDatabase=null;

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
    public static final String DATABASE_NAME = NewsContract.NewsEntry.HOMEWORK_TABLE+".db";
    private Context mContext;

    public StudentSQLiteOpenHelper(Context context) {
        super(context , DATABASE_NAME , null , DATABASE_VERSION);
        mContext = context;

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        studentDatabase=sqLiteDatabase;
        studentDatabase.execSQL(SQL_DELETE_ENTRIES);
        studentDatabase.execSQL(HOMEWORK_TABLE);
        initDb();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase ,
                          int oldVersion , int newVersion) {
        onCreate(sqLiteDatabase);
    }


    public void initDb() {
        if(null==studentDatabase) {
            final SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.coursemanagement/databases/" + DATABASE_NAME, null);
            studentDatabase = sqLiteDatabase;
        }
        System.out.println("initDb---------------"+studentDatabase);
        new Thread(new Runnable() {//创建子线程
            @Override
            public void run() {

                String spFileName = mContext.getResources().getString(R.string.shared_preferences_file_name);
                String accountKey = mContext.getResources().getString(R.string.login_account_name);
                SharedPreferences spFile = mContext.getSharedPreferences(spFileName , Context.MODE_PRIVATE);
                String account = spFile.getString(accountKey , null);

                try {
                    HttpURLConnection homeworkFromDatabase = Network.findStudentAssignment(account);
                    String data=homeworkFromDatabase.getHeaderField("data");
                    data= URLDecoder.decode(data,"UTF-8");
                    System.err.println("收到了\n"+data);
                    if(null!=data){
                        Gson gson = new Gson();
                        List<Map<String, String>> listOfMaps = null;
                        listOfMaps = gson.fromJson(data, List.class);

                        studentDatabase.execSQL(SQL_DELETE_ENTRIES);
                        studentDatabase.execSQL(HOMEWORK_TABLE);

                        for (Map<String, String> temp: listOfMaps) {
                            ContentValues values = new ContentValues();
                            values.put(NewsContract.NewsEntry.HOMEWORK_TITLE , temp.get(NewsContract.NewsEntry.HOMEWORK_TITLE));
                            values.put(NewsContract.NewsEntry.HOMEWORK_CONTENT , temp.get(NewsContract.NewsEntry.HOMEWORK_CONTENT));
                            values.put(NewsContract.NewsEntry.HOMEWORK_COURSE , temp.get(NewsContract.NewsEntry.HOMEWORK_COURSE));
                            values.put(NewsContract.NewsEntry.HOMEWORK_START_TIME , temp.get(NewsContract.NewsEntry.HOMEWORK_START_TIME));
                            values.put(NewsContract.NewsEntry.HOMEWORK_DEADLINE , temp.get(NewsContract.NewsEntry.HOMEWORK_DEADLINE));
                            long r = studentDatabase.insert(
                                    NewsContract.NewsEntry.HOMEWORK_TABLE ,
                                    null ,
                                    values);
                            System.out.println(values);

                        }
                        studentDatabase.close();
                    }
                }catch (Exception e){
                } finally {
                    SharedPreferences.Editor editor = spFile.edit();
                    editor.putBoolean(mContext.getResources().getString(R.string.isLoaded), true).apply();
                }
            }
        }).start();//启动子线程
    }
}
