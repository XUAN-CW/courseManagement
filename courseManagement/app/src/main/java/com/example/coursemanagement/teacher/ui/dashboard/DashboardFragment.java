package com.example.coursemanagement.teacher.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.coursemanagement.R;
import com.example.coursemanagement.teacher.ui.dashboard.datepicker.CustomDatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DashboardFragment extends Fragment implements View.OnClickListener{

    private TextView mTvSelectedDate, mTvSelectedTime;
    private CustomDatePicker mDatePicker, mTimerPicker;

    EditText et_title=null;
    EditText et_content=null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.assign_homework, container, false);


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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //创建Calendar实例
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);   //设置当前时间
        String beginTime=format.format(cal.getTime());
        cal.add(Calendar.YEAR, 3);  //在当前时间基础上加3年
        String endTime =format.format(cal.getTime());
//        mTvSelectedTime.setText(beginTime);
        // 通过日期字符串初始化日期，格式请用：yyyy-MM-dd HH:mm
        mTimerPicker = new CustomDatePicker(getActivity(), new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                System.out.println(String.valueOf(timestamp));
                System.out.println(et_content.getText());
                System.out.println(et_title.getText());
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

}
