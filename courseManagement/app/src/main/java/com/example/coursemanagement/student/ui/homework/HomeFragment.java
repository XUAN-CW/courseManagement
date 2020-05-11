package com.example.coursemanagement.student.ui.homework;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;

import com.example.coursemanagement.R;

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

    PopupMenu popupMenu = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_homework, null);
        readFromDatabaseAndSetAdapter();

        list = (ListView) view.findViewById(R.id.lv_news_list);
        list.setAdapter(newsAdapter);

        addItemClickListener();

        return view;
    }

    public void readFromDatabaseAndSetAdapter() {
        System.out.println(222222);
        myDbHelper = new MySQLiteOpenHelper(getActivity());
        System.out.println(333333);
        db = myDbHelper.getReadableDatabase();
        System.out.println(55555);


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
}