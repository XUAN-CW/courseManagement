package com.example.coursemanagement.student.homework;

import android.graphics.Bitmap;

public class News {
    private String homeworkTitle;
    private String homeworkContent;
    private String course;
    private String startTime;
    private String deadline;


    public String getHomeworkTitle() {
        return homeworkTitle;
    }

    public void setHomeworkTitle(String homeworkTitle) {
        this.homeworkTitle = homeworkTitle;
    }

    public String getHomeworkContent() {
        return homeworkContent;
    }

    public void setHomeworkContent(String homeworkContent) {
        this.homeworkContent = homeworkContent;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }






































////////////////////////////////////////////////////////////////////////////////////////////////





























































    private String mTitle;
    private String mAuthor;
    private String mContent;
//    private int mImageId;

    private Bitmap bitmap;

    public String getTitle() {
        return getmTitle();
    }

    public void setTitle(String title) {
        this.setmTitle(title);
    }


    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

//    public String getmContent() {
//        return mContent;
//    }
//
//    public void setmContent(String mContent) {
//        this.mContent = mContent;
//    }

//    public int getmImageId() {
//        return mImageId;
//    }
//
//    public void setmImageId(int mImageId) {
//        this.mImageId = mImageId;
//    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}