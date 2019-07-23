package android.bignerdranch.wgu_assistant;

import java.util.Date;
import java.util.UUID;

public class Course {
    private UUID mID;
    private String mTitle;
    private Date mStartDate;
    private Date mEndDate;

    private String mChosenStartDate;
    private String mChosenEndDate;

    private String courseStatus;
    private String courseNote;
    private String courseMentorName;
    private String courseMentorPhone;
    private String courseMentorEmail;
    private int mAlertSet;

    private int mTermReference;
    //
    public Course() {
        this(UUID.randomUUID());

    }
    public Course(UUID id) {
        mID = id;
        mStartDate = new Date();
        mEndDate = new Date();
        mChosenStartDate = new String();
        mChosenEndDate = new String();

        courseStatus = new String();
        courseNote = new String();
        courseMentorName = new String();
        courseMentorPhone = new String();
        courseMentorEmail = new String();
        mChosenStartDate = new String();
        //selectTermForCourse = new Integer();

    }
    public UUID getID() {
        return mID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getStartDate() {
        return mStartDate;
    }

    public void setStartDate(Date startDate) {
        mStartDate = startDate;
    }

    public Date getEndDate() {
        return mEndDate;
    }

    public void setEndDate(Date endDate) {
        mEndDate = endDate;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public String getCourseNote() {
        return courseNote;
    }

    public void setCourseNote(String courseNote) {
        this.courseNote = courseNote;
    }

    public String getCourseMentorName() {
        return courseMentorName;
    }

    public void setCourseMentorName(String courseMentorName) {
        this.courseMentorName = courseMentorName;
    }

    public String getCourseMentorPhone() {
        return courseMentorPhone;
    }

    public void setCourseMentorPhone(String courseMentorPhone) {
        this.courseMentorPhone = courseMentorPhone;
    }

    public String getCourseMentorEmail() {
        return courseMentorEmail;
    }

    public void setCourseMentorEmail(String courseMentorEmail) {
        this.courseMentorEmail = courseMentorEmail;
    }

    public String getChosenStartDate() {
        return mChosenStartDate;
    }

    public void setChosenStartDate(String chosenStartDate) {
        mChosenStartDate = chosenStartDate;
    }

    public String getChosenEndDate() {
        return mChosenEndDate;
    }

    public void setChosenEndDate(String chosenEndDate) {
        mChosenEndDate = chosenEndDate;
    }

    public int getTermReference() {
        return mTermReference;
    }

    public void setTermReference(int termReference) {
        mTermReference = termReference;
    }

    public int getAlertSet() {
        return mAlertSet;
    }

    public void setAlertSet(int alertSet) {
        mAlertSet = alertSet;
    }
}
