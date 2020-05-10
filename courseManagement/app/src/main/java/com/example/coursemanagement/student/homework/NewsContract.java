package com.example.coursemanagement.student.homework;

import android.provider.BaseColumns;

public final class NewsContract {
    private NewsContract() {}

    public static class NewsEntry implements BaseColumns {
        public static final String HOMEWORK_TABLE = "homework";
        public static final String HOMEWORK_TITLE = "homeworkTitle";
        public static final String HOMEWORK_CONTENT = "homeworkContent";
        public static final String HOMEWORK_COURSE = "course";
        public static final String HOMEWORK_START_TIME = "startTime";
        public static final String HOMEWORK_DEADLINE = "deadline";
    }
}
