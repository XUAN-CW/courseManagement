package com.example.coursemanagement.teacher.ui.home.teacherDatabase;

import android.provider.BaseColumns;

public final class TeacherNewsContract {
    private TeacherNewsContract() {}

    public static class NewsEntry implements BaseColumns {
        public static final String ASSIGNMENT_TABLE = "assignment";
        public static final String ASSIGNMENT_TITLE = "title";
        public static final String ASSIGNMENT_CONTENT = "content";
        public static final String ASSIGNMENT_COURSE = "name";
        public static final String ASSIGNMENT_START_TIME = "startTime";
        public static final String ASSIGNMENT_DEADLINE = "deadline";
        public static final String ASSIGNMENT_ASSIGNMENT_NUMBER = "assignmentNumber";
        public static final String ASSIGNMENT_COURSE_NUMBER = "courseNumber";
    }
}
