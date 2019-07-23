package android.bignerdranch.wgu_assistant;

import java.util.Date;
import java.util.UUID;

public class Assessment {
    private UUID mID;
    private String mTitle;
    private String mAssessType;
    private int mCourseReference;
    private int mAlertSet;
    private Date mDueDate;
    private Date mGoalDate;
    public Assessment() {
        this(UUID.randomUUID());
    }
    public Assessment(UUID id) {
        mID = id;
        mTitle = new String();
        mAssessType = new String();

        mDueDate = new Date();
        mGoalDate = new Date();
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
    public String getAssessType() {
        return mAssessType;
    }

    public void setAssessType(String assessType) {
        mAssessType = assessType;
    }

    public Date getDueDate() {
        return mDueDate;
    }

    public void setDueDate(Date dueDate) {
        mDueDate = dueDate;
    }

    public Date getGoalDate() {
        return mGoalDate;
    }

    public void setGoalDate(Date goalDate) {
        mGoalDate = goalDate;
    }

    public int getCourseReference() {
        return mCourseReference;
    }

    public void setCourseReference(int courseReference) {
        mCourseReference = courseReference;
    }

    public int getAlertSet() {
        return mAlertSet;
    }

    public void setAlertSet(int alertSet) {
        mAlertSet = alertSet;
    }
}
