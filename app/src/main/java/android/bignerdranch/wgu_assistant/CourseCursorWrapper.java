package android.bignerdranch.wgu_assistant;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.bignerdranch.wgu_assistant.TermDbSchema.CourseTable;

import java.util.Date;
import java.util.UUID;

public class CourseCursorWrapper extends CursorWrapper {
    public CourseCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public Course getCourse() {
        String uuidString = getString(getColumnIndex(CourseTable.Cols.UUID));
        String title = getString(getColumnIndex(CourseTable.Cols.TITLE));
        long startdate = getLong(getColumnIndex(CourseTable.Cols.START_DATE));
        long enddate = getLong(getColumnIndex(CourseTable.Cols.END_DATE));
        String coursestatus = getString(getColumnIndex(CourseTable.Cols.COURSE_STATUS));
        String optionalnote = getString(getColumnIndex(CourseTable.Cols.OPTIONAL_NOTE));
        String mentorname = getString(getColumnIndex(CourseTable.Cols.MENTOR_NAME));
        String mentorphone = getString(getColumnIndex(CourseTable.Cols.MENTOR_PHONE));
        String mentoremail = getString(getColumnIndex(CourseTable.Cols.MENTOR_EMAIL));
        String chosenstartdate = getString(getColumnIndex(CourseTable.Cols.CHOSEN_START_DATE));
        String chosenenddate = getString(getColumnIndex(CourseTable.Cols.CHOSEN_END_DATE));
        int alertset = getInt(getColumnIndex(CourseTable.Cols.ALERT_SET));

        int term_reference = getInt(getColumnIndex(CourseTable.Cols.COL_COURSE_TERM_REFERENCE));
        Course course = new Course(UUID.fromString(uuidString));
        course.setTitle(title);
        course.setStartDate(new Date(startdate));
        course.setEndDate(new Date(enddate));
        course.setCourseStatus(coursestatus);
        course.setCourseNote(optionalnote);
        course.setCourseMentorName(mentorname);
        course.setCourseMentorPhone(mentorphone);
        course.setCourseMentorEmail(mentoremail);
        course.setAlertSet(alertset);
        course.setTermReference(term_reference);

        return course;
    }
}
