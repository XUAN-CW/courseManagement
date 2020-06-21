package com.example.coursemanagement.teacher.ui.assignHomework;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.coursemanagement.Network;
import com.example.coursemanagement.R;
import com.example.coursemanagement.teacher.ui.assignHomework.datepicker.CustomDatePicker;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class assignHomeworkFragment extends Fragment implements View.OnClickListener{

    private TextView mTvSelectedDate, mTvSelectedTime;
    private CustomDatePicker mDatePicker, mTimerPicker;

    EditText et_title=null;
    EditText et_content=null;
    View root=null;
    AlertDialog alertDialog3;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.assign_homework, container, false);
        resetTeacherCourse();

        /*
        这个地方要小心，不然 findViewById 会失败
        错误：
            et_title=getActivity().findViewById(R.id.assign_homework_title);
        正确：
            et_title=root.findViewById(R.id.assign_homework_title);
         */

        et_title=root.findViewById(R.id.assign_homework_title);
        et_content=root.findViewById(R.id.assign_homework_content);

        initViews();
        System.out.println("------"+getActivity().findViewById(R.id.assign_homework_title));
        initTimerPicker();

        root.findViewById(R.id.assign_homework_button).setOnClickListener(this);
        mTvSelectedTime = root.findViewById(R.id.assign_homework_button);


        return root;
    }

    public void initViews(){






    }


    @Override
    public void onDestroy() {
        super.onDestroy();
//        mDatePicker.onDestroy();
    }


    private void initTimerPicker() {
//        String beginTime = DateFormatUtils.long2Str(System.currentTimeMillis(), true);
        Date date = new Date();
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //创建Calendar实例
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);   //设置当前时间
        String beginTime=format.format(cal.getTime());
        cal.add(Calendar.YEAR, 3);  //在当前时间基础上加3年
        String endTime =format.format(cal.getTime());
//        mTvSelectedTime.setText(beginTime);
        // 通过日期字符串初始化日期，格式请用：yyyy-MM-dd HH:mm
        mTimerPicker = new CustomDatePicker(getActivity(), new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(final long timestamp) {
                String spFileName = getResources().getString(R.string.shared_preferences_file_name);
                String teacherCourse = getResources().getString(R.string.teacher_course);
                SharedPreferences spFile = getActivity().getSharedPreferences(spFileName , getActivity().MODE_PRIVATE);

                List<String> tc = new ArrayList<>();
                Gson gson = new Gson();
                tc = gson.fromJson(spFile.getString(teacherCourse , null),tc.getClass());
                //方式二：使用toArray()方法
                //list.toArray(T[]  a); 将list转化为你所需要类型的数组
                final String[] items=tc.toArray(new String[tc.size()]);
                final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
                alertBuilder.setTitle("选择发布的课程");
                /**
                 *第一个参数:弹出框的消息集合，一般为字符串集合
                 * 第二个参数：默认被选中的，布尔类数组
                 * 第三个参数：勾选事件监听
                 */
                alertBuilder.setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                        if (isChecked){
                            Toast.makeText(getActivity(), "选择" + items[i], Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getActivity(), "取消选择" + items[i], Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String temp="";
                        String itemDivider="aaaaa";
                        ListView lv=alertDialog3.getListView();
                        for (int j = 0; i < items.length; i++) {
                            if (lv.getCheckedItemPositions().get(i)) {
                                temp +=lv.getAdapter().getItem(i)+ itemDivider;
                            }
                        }

                        final String title=et_title.getText().toString();
                        final String content=et_content.getText().toString();
                        System.out.println(temp+title+content+format.format(timestamp));
                        final String courseNumbers=temp;
                        new Thread(new Runnable() {//创建子线程
                            @Override
                            public void run() {
                                try {
                                    HttpURLConnection assignHomework = Network.assignHomework(courseNumbers,title,content,format.format(timestamp));
                                    if (assignHomework.getHeaderField("status").equals("OK")){

                                    }
                                    else {
                                        Looper.prepare();
                                        Toast.makeText(getActivity(),
                                                assignHomework.getHeaderField("status"), Toast.LENGTH_SHORT).show();
                                        Looper.loop();
                                    }
                                }catch (Exception e){

                                }
                            }
                        }).start();//启动子线程


                        alertDialog3.dismiss();
                    }
                });

                alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog3.dismiss();
                    }
                });
                alertDialog3 = alertBuilder.create();
                alertDialog3.show();
            }
        }, beginTime, endTime);
        // 允许点击屏幕或物理返回键关闭
        mTimerPicker.setCancelable(true);
        // 显示时和分
        mTimerPicker.setCanShowPreciseTime(true);
        // 允许循环滚动
        mTimerPicker.setScrollLoop(true);
        // 允许滚动动画
        mTimerPicker.setCanShowAnim(true);
    }

    @Override
    public void onClick(View v) {
        mTimerPicker.show(mTvSelectedTime.getText().toString());
    }


    private void resetTeacherCourse(){
        new Thread(new Runnable() {//创建子线程
            @Override
            public void run() {

                String spFileName = getActivity().getResources().getString(R.string.shared_preferences_file_name);
                String accountKey = getActivity().getResources().getString(R.string.login_account_name);
                SharedPreferences spFile = getActivity().getSharedPreferences(spFileName , Context.MODE_PRIVATE);
                String account = spFile.getString(accountKey, null);

                System.out.println("account:"+account);
                try {
                    HttpURLConnection homeworkFromDatabase = Network.getTeacherCourse(account);
                    String course=homeworkFromDatabase.getHeaderField("course");
                    System.err.println("收到了course:\n"+course);
                    SharedPreferences.Editor editor = spFile.edit();
                    editor.putString(getActivity().getResources().getString(R.string.teacher_course), course).apply();
                }catch (Exception e){

                }}
        }).start();//启动子线程
    }
}
