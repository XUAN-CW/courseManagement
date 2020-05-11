package com.example.coursemanagement.teacher.ui.home;

import android.provider.BaseColumns;

public final class TeacherNewsContract {
    private TeacherNewsContract() {}

    public static class NewsEntry implements BaseColumns {
        public static final String ASSIGNMENT_TABLE = "assignment";
        public static final String ASSIGNMENT_TITLE = "homeworkTitle";
        public static final String ASSIGNMENT_CONTENT = "homeworkContent";
        public static final String ASSIGNMENT_COURSE = "course";
        public static final String ASSIGNMENT_START_TIME = "startTime";
        public static final String ASSIGNMENT_DEADLINE = "deadline";
        public static final String ASSIGNMENT_ASSIGNMENT_NUMBER = "assignmentNumber";
        public static final String ASSIGNMENT_COURSE_NUMBER = "courseNumber";
    }
}
