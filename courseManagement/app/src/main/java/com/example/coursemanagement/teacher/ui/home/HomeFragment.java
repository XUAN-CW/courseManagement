package com.example.coursemanagement.teacher.ui.home;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.coursemanagement.R;

import java.util.ArrayList;
import java.util.List;

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

    private HomeViewModel homeViewModel;

    SQLiteDatabase db=null;
    MySQLiteOpenHelper myDbHelper = null;
    List<News> newsList=null;
    ListView lvNewsList=null;
    NewsAdapter newsAdapter=null;
    View view;
    ListView list=null;

    PopupMenu popupMenu = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        readFromDatabaseAndSetAdapter();
        System.out.println("teacher_lv_news_list"+view.findViewById(R.id.teacher_lv_news_list));
        list = (ListView) view.findViewById(R.id.teacher_lv_news_list);
        list.setAdapter(newsAdapter);

        addItemClickListener();

        return view;
    }


    public void readFromDatabaseAndSetAdapter() {
        myDbHelper = new MySQLiteOpenHelper(getActivity());
        db = myDbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                NewsContract.NewsEntry.ASSIGNMENT_TABLE,
                null, null, null, null, null, null);
        newsList = new ArrayList<>();
        try {
        } catch (Exception e) {

        }
        System.out.println("newsList"+newsList);

        int titleIndex = cursor.getColumnIndex(
                NewsContract.NewsEntry.ASSIGNMENT_TITLE);
        int authorIndex = cursor.getColumnIndex(
                NewsContract.NewsEntry.ASSIGNMENT_COURSE);

        while (cursor.moveToNext()) {
            News news = new News();

            String currentTitle = cursor.getString(titleIndex);
            String currentCourse = cursor.getString(authorIndex);
            news.setAssignmentTitle(currentTitle);
            news.setCourse(currentCourse);
            newsList.add(news);
        }
        newsAdapter = new NewsAdapter(getActivity(), R.layout.list_item, newsList);
        System.out.println("newsAdapter"+newsAdapter);
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
                String appName = newsList.get(position).getAssignmentTitle();
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
                String appName = newsList.get(position).getAssignmentTitle();
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
