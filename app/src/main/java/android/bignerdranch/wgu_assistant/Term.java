package android.bignerdranch.wgu_assistant;

import java.util.Date;
import java.util.UUID;

public class Term {
    private UUID mID;
    private String mTitle;
    private Date mStartDate;
    private Date mEndDate;
    public Term() {
        this(UUID.randomUUID());
    }
    public Term(UUID id) {
        mID = id;
        mTitle = new String();
        mStartDate = new Date();
        mEndDate = new Date();
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
}
