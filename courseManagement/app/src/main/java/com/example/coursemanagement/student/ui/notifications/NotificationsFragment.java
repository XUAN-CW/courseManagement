package com.example.coursemanagement.student.ui.notifications;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.coursemanagement.Login;
import com.example.coursemanagement.R;


public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.student_me, container, false);

        Button button = root.findViewById(R.id.student_me_quit_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String spFileName = getResources().getString(R.string.shared_preferences_file_name);
                SharedPreferences spFile = getActivity().getSharedPreferences(spFileName, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = spFile.edit();
                //设置未登录
                editor.putBoolean(getResources().getString(R.string.logined), false).apply();

                Intent intent = new Intent(getActivity(), Login.class);
                //调用 activity
                startActivity(intent);
            }
        });
        return root;
    }
}
