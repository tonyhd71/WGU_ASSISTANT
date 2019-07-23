package android.bignerdranch.wgu_assistant;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.bignerdranch.wgu_assistant.TermDbSchema.CourseTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CourseLab {
    private static CourseLab sCourseLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CourseLab get(Context context) {
        if (sCourseLab == null) {
            sCourseLab = new CourseLab(context);
        }
        return sCourseLab;
    }
    private CourseLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new TermBaseHelper(mContext).getWritableDatabase();
        //mTerms = new ArrayList<>();
    }
    public void addCourse(Course c) {
        //mTerms.add(c);
        ContentValues values = getContentValues(c);
        mDatabase.insert(CourseTable.NAME, null, values);
    }
    public List<Course> getCourses() {
        //return mTerms;
        //return new ArrayList<>();
        List<Course> courses = new ArrayList<>();
        CourseCursorWrapper cursor = queryCourses(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                courses.add(cursor.getCourse());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return courses;
    }
    public Course getCourse(UUID id) {
        /*for (Term term : mTerms) {
            if (term.getID().equals(id)) {
                return term;
            }
        }*/
        CourseCursorWrapper cursor = queryCourses(CourseTable.Cols.UUID + " = ?", new String[] {
                id.toString()
        });
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCourse();
        } finally {
            cursor.close();
        }
    }
    public void deleteCourse(UUID courseId)
    {
        String uuidString = courseId.toString();

        mDatabase.delete(CourseTable.NAME, CourseTable.Cols.UUID + " = ?", new String[] {uuidString});
    }
    public void updateCourse(Course course) {
        String uuidString = course.getID().toString();
        ContentValues values = getContentValues(course);
        mDatabase.update(CourseTable.NAME, values, CourseTable.Cols.UUID + " = ?",
                new String[] {uuidString });
    }
    private CourseCursorWrapper queryCourses(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CourseTable.NAME, null, whereClause, whereArgs, null, null, null);
        return new CourseCursorWrapper(cursor);
    }
    private static ContentValues getContentValues(Course course) {
        ContentValues values = new ContentValues();
        values.put(CourseTable.Cols.UUID, course.getID().toString());
        values.put(CourseTable.Cols.TITLE, course.getTitle());
        values.put(CourseTable.Cols.START_DATE, course.getStartDate().getTime());
        values.put(CourseTable.Cols.END_DATE, course.getEndDate().getTime());
        values.put(CourseTable.Cols.CHOSEN_START_DATE, course.getChosenStartDate());
        values.put(CourseTable.Cols.CHOSEN_END_DATE, course.getChosenEndDate());

        values.put(CourseTable.Cols.COURSE_STATUS, course.getCourseStatus());
        values.put(CourseTable.Cols.OPTIONAL_NOTE, course.getCourseNote());
        values.put(CourseTable.Cols.MENTOR_NAME, course.getCourseMentorName());
        values.put(CourseTable.Cols.MENTOR_PHONE, course.getCourseMentorPhone());
        values.put(CourseTable.Cols.MENTOR_EMAIL, course.getCourseMentorEmail());
        values.put(CourseTable.Cols.ALERT_SET, course.getAlertSet());

        values.put(CourseTable.Cols.COL_COURSE_TERM_REFERENCE, course.getTermReference());
        //values.put(CourseTable.Cols.COL_COURSE_TERM_REFERENCE, 3);
        //values.put(CourseTable.Cols.COL_COURSE_TERM_REFERENCE, course.getTermReference());
        return values;
    }
}
