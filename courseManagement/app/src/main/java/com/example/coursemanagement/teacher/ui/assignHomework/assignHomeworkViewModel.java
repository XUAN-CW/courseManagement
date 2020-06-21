package com.example.coursemanagement.teacher.ui.assignHomework;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class assignHomeworkViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public assignHomeworkViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}