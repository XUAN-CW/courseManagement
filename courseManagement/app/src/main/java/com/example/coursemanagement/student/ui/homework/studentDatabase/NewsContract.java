package com.example.coursemanagement.student.ui.homework.studentDatabase;

import android.provider.BaseColumns;

public final class NewsContract {
    private NewsContract() {}

    public static class NewsEntry implements BaseColumns {
        public static final String HOMEWORK_TABLE = "homework";
        public static final String HOMEWORK_TITLE = "title";
        public static final String HOMEWORK_CONTENT = "content";
        public static final String HOMEWORK_COURSE = "name";
        public static final String HOMEWORK_START_TIME = "startTime";
        public static final String HOMEWORK_DEADLINE = "deadline";
        public static final String HOMEWORK_ASSIGNMENT_NUMBER = "assignmentNumber";
    }
}
