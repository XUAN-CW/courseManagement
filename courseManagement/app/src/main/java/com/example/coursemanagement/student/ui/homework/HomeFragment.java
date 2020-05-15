package com.example.coursemanagement.student.ui.homework;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;

import com.example.coursemanagement.Login;
import com.example.coursemanagement.R;
import com.example.coursemanagement.SignUp;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    SQLiteDatabase db = null;
    MySQLiteOpenHelper myDbHelper = null;

    List<News> newsList = null;
    ListView lvNewsList = null;
    NewsAdapter newsAdapter = null;
    View view;

    ListView list;
    ImageView student_plus_sign=null;
    PopupMenu popupMenu = null;
    PopupMenu student_add_popup_menu;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_homework, null);
        student_plus_sign=view.findViewById(R.id.student_plus_sign);
        new MySQLiteOpenHelper(getActivity()).initDb();
        String spFileName = getResources().getString(R.string.shared_preferences_file_name);

        SharedPreferences spFile = getActivity().getSharedPreferences(spFileName , getActivity().MODE_PRIVATE);
        while (!spFile.getBoolean(getResources().getString(R.string.isLoaded) , false)){}


        readFromDatabaseAndSetAdapter();
        list = (ListView) view.findViewById(R.id.lv_news_list);

        list.setAdapter(newsAdapter);
        addPlusSignMenu();
        addItemClickListener();
        return view;
    }

    public void readFromDatabaseAndSetAdapter() {
        myDbHelper = new MySQLiteOpenHelper(getActivity());
        db = myDbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                NewsContract.NewsEntry.HOMEWORK_TABLE,
                null, null, null, null, null, null);
        newsList = new ArrayList<>();
        try {
            lvNewsList = (ListView) view.findViewById(R.id.lv_news_list);
        } catch (Exception e) {

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
            news.setHomeworkContent(cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.HOMEWORK_CONTENT)));
            news.setDeadline(cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.HOMEWORK_DEADLINE)));
            news.setStartTime(cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.HOMEWORK_START_TIME)));



            newsList.add(news);

        }
        newsAdapter = new NewsAdapter(
                getActivity(),
                R.layout.list_item,
                newsList);
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
                String appName = newsList.get(position).getHomeworkTitle();
                //提示
                Toast.makeText(getActivity(), "Click:" + appName, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), StudentShowHomework.class);
                //将键值对放入 Intent 对象中
                intent.putExtra(NewsContract.NewsEntry.HOMEWORK_TITLE, newsList.get(position).getHomeworkTitle());

                intent.putExtra(NewsContract.NewsEntry.HOMEWORK_CONTENT, newsList.get(position).getHomeworkContent());

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
                String appName = newsList.get(position).getHomeworkTitle();
                Toast.makeText(getActivity(), "LongClick:" + appName, Toast.LENGTH_SHORT).show();
                popupMenu = new PopupMenu(getActivity(), view);
                //将 R.menu.student_popup_menu 菜单资源加载到popup中
                popupMenu.getMenuInflater().inflate(R.menu.student_popup_menu,popupMenu.getMenu());
                //为popupMenu选项添加监听器
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.delete:
                                newsList.remove(position);
                                //更新列表
                                list.setAdapter(newsAdapter);//显示列表, 不会使用缓存的item的视图对象
                                newsAdapter.notifyDataSetChanged();//通知更新列表, 使用所有缓存的item的视图对象
                                break;
                            default:
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
        student_plus_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("student_plus_sign");
                student_add_popup_menu = new PopupMenu(getActivity(), v);
                //将 R.menu.student_popup_menu 菜单资源加载到popup中
                student_add_popup_menu.getMenuInflater().inflate(R.menu.student_add_popup_menu,student_add_popup_menu.getMenu());
                //为popupMenu选项添加监听器
                student_add_popup_menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
//                            case R.id.delete:
//                                newsList.remove(position);
//                                //更新列表
//                                list.setAdapter(newsAdapter);//显示列表, 不会使用缓存的item的视图对象
//                                newsAdapter.notifyDataSetChanged();//通知更新列表, 使用所有缓存的item的视图对象
//                                break;
                            default:
                                Toast.makeText(getActivity(),"you clicked->" + item.getTitle(), Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
                student_add_popup_menu.show();
            }
        });
    }

}