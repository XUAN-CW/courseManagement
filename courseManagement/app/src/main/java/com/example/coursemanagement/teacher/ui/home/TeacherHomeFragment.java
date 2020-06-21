package com.example.coursemanagement.teacher.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import android.widget.PopupMenu;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.coursemanagement.Network;
import com.example.coursemanagement.R;
import com.example.coursemanagement.teacher.ui.home.ic_add_black_24dp.CreateClass;
import com.example.coursemanagement.teacher.ui.home.teacherDatabase.assignments;
import com.example.coursemanagement.teacher.ui.home.teacherDatabase.TeacherNewsAdapter;
import com.example.coursemanagement.teacher.ui.home.teacherDatabase.TeacherNewsContract;
import com.example.coursemanagement.teacher.ui.home.teacherDatabase.TeacherSQLiteOpenHelper;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Toast;


public class TeacherHomeFragment extends Fragment {

    private TeacherHomeViewModel teacherHomeViewModel;

    SQLiteDatabase db=null;
    TeacherSQLiteOpenHelper myDbHelper = null;
    List<assignments> teacherNewsList =null;
    ListView lvNewsList=null;
    TeacherNewsAdapter teacherNewsAdapter =null;
    View view;
    ListView list=null;

    PopupMenu popupMenu = null;

    ImageView teacher_assignment_plus_sign=null;
    PopupMenu teacher_add_popup_menu=null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initViews();
        new TeacherSQLiteOpenHelper(getActivity()).initDb();
        readFromDatabaseAndSetAdapter();
        list.setAdapter(teacherNewsAdapter);

        addItemClickListener();
        addPlusSignMenu();
        return view;
    }

    public void initViews(){
        teacher_assignment_plus_sign=view.findViewById(R.id.teacher_assignment_plus_sign);
        list = (ListView) view.findViewById(R.id.teacher_lv_news_list);


    }


    public void readFromDatabaseAndSetAdapter() {

        SharedPreferences spFile =
                getActivity().getSharedPreferences(getResources().getString(R.string.shared_preferences_file_name),
                        getActivity().MODE_PRIVATE);
//        while (!spFile.getBoolean(getResources().getString(R.string.isLoaded) , false)){}
        myDbHelper = new TeacherSQLiteOpenHelper(getActivity());
        db = myDbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                TeacherNewsContract.NewsEntry.ASSIGNMENT_TABLE,
                null, null, null, null, null, null);
        teacherNewsList = new ArrayList<>();
        try {
        } catch (Exception e) {

        }
        System.out.println("newsList:-----"+ teacherNewsList);

        int titleIndex = cursor.getColumnIndex(
                TeacherNewsContract.NewsEntry.ASSIGNMENT_TITLE);
        int authorIndex = cursor.getColumnIndex(
                TeacherNewsContract.NewsEntry.ASSIGNMENT_COURSE);

        while (cursor.moveToNext()) {
            assignments teacherNews = new assignments();

            String currentTitle = cursor.getString(titleIndex);
            String currentCourse = cursor.getString(authorIndex);
            teacherNews.setAssignmentTitle(currentTitle);
            teacherNews.setCourse(currentCourse);
            teacherNews.setAssignmentContent(cursor.getString(cursor.getColumnIndex(TeacherNewsContract.NewsEntry.ASSIGNMENT_CONTENT)));
            teacherNews.setAssignmentNumber(cursor.getString(cursor.getColumnIndex(TeacherNewsContract.NewsEntry.ASSIGNMENT_ASSIGNMENT_NUMBER)));
            teacherNews.setCourseNumber(cursor.getString(cursor.getColumnIndex(TeacherNewsContract.NewsEntry.ASSIGNMENT_COURSE_NUMBER)));
            teacherNews.setDeadline(cursor.getString(cursor.getColumnIndex(TeacherNewsContract.NewsEntry.ASSIGNMENT_DEADLINE)));
            teacherNews.setStartTime(cursor.getString(cursor.getColumnIndex(TeacherNewsContract.NewsEntry.ASSIGNMENT_START_TIME)));



            teacherNewsList.add(teacherNews);
//            System.out.println("currentTitle:"+currentTitle);
        }
        teacherNewsAdapter = new TeacherNewsAdapter(getActivity(), R.layout.list_item, teacherNewsList);
//        System.out.println("newsAdapter"+ teacherNewsAdapter);
    }

    public void addItemClickListener() {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * parent : ListView
             * view : 当前行的item视图对象
             * position : 当前行的下标
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //提示当前行的应用名称
                String appName = teacherNewsList.get(position).getAssignmentTitle();
                //提示
                Toast.makeText(getActivity(), "Click:" + appName, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), TeacherShowAssignment.class);
                //将键值对放入 Intent 对象中
                intent.putExtra(TeacherNewsContract.NewsEntry.ASSIGNMENT_TITLE, teacherNewsList.get(position).getAssignmentTitle());
                intent.putExtra(TeacherNewsContract.NewsEntry.ASSIGNMENT_CONTENT, teacherNewsList.get(position).getAssignmentContent());
                intent.putExtra(TeacherNewsContract.NewsEntry.ASSIGNMENT_ASSIGNMENT_NUMBER,teacherNewsList.get(position).getAssignmentNumber());
                Toast.makeText(getActivity(), "click:" + appName, Toast.LENGTH_SHORT).show();

                //调用 activity
                startActivity(intent);
                //////////////////点击移除的代码如下////////////////////
//                newsList.remove(position);
//                //更新列表
//                list.setAdapter(newsAdapter);//显示列表, 不会使用缓存的item的视图对象
//                newsAdapter.notifyDataSetChanged();//通知更新列表, 使用所有缓存的item的视图对象
                ////////////////////////////////////
            }
        });


        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                String appName = teacherNewsList.get(position).getAssignmentTitle();
                Toast.makeText(getActivity(), "LongClick:" + appName, Toast.LENGTH_SHORT).show();
                popupMenu = new PopupMenu(getActivity(), view);
                //将 R.menu.student_popup_menu 菜单资源加载到popup中
                popupMenu.getMenuInflater().inflate(R.menu.teacher_popup_menu,popupMenu.getMenu());
                //为popupMenu选项添加监听器
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.teacher_delete:

                                new Thread(new Runnable() {//创建子线程
                                    @Override
                                    public void run() {
                                        try {
                                            HttpURLConnection deleteAssignment = Network.deleteAssignment(teacherNewsList.get(position).getAssignmentNumber());
                                            if("OK".equals(deleteAssignment.getHeaderField("status"))){

                                            }
                                            Looper.prepare();
                                            Toast.makeText(getActivity(),
                                                    deleteAssignment.getHeaderField("status"), Toast.LENGTH_SHORT).show();
                                            Looper.loop();
                                        }catch (Exception e){

                                        }
                                    }
                                }).start();//启动子线程

                                teacherNewsList.remove(position);
                                //更新列表
                                list.setAdapter(teacherNewsAdapter);//显示列表, 不会使用缓存的item的视图对象
                                teacherNewsAdapter.notifyDataSetChanged();//通知更新列表, 使用所有缓存的item的视图对象
                                break;
                            default:
//                                newsList.get(position).getAssignmentNumber();
                                Toast.makeText(getActivity(),"you clicked->" + item.getTitle(), Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
                popupMenu.show();
                return false;
            }
        });
    }

    public void addPlusSignMenu(){
        teacher_assignment_plus_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("student_plus_sign");
                teacher_add_popup_menu = new PopupMenu(getActivity(), v);
                //将 R.menu.student_popup_menu 菜单资源加载到popup中
                teacher_add_popup_menu.getMenuInflater().inflate(R.menu.teacher_add_popup_menu,teacher_add_popup_menu.getMenu());
                //为popupMenu选项添加监听器
                teacher_add_popup_menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.create_class_item:
                                Intent intent = new Intent(getActivity(), CreateClass.class);
                                //调用 activity
                                startActivity(intent);
                                break;
                            default:
                                Toast.makeText(getActivity(),"you clicked->" + item.getTitle(), Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
                teacher_add_popup_menu.show();
            }
        });
    }


}
