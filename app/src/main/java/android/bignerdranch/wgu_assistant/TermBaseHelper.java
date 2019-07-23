package android.bignerdranch.wgu_assistant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TermBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "termBase.db";
    public Term mTerm;

    public TermBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TermDbSchema.TermTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                TermDbSchema.TermTable.Cols.UUID + ", " +
                TermDbSchema.TermTable.Cols.TITLE + ", " +
                TermDbSchema.TermTable.Cols.START_DATE + ", " +
                TermDbSchema.TermTable.Cols.END_DATE +
                ")"
        );
        db.execSQL("create table " + TermDbSchema.CourseTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                TermDbSchema.CourseTable.Cols.UUID + ", " +
                TermDbSchema.CourseTable.Cols.TITLE + ", " +
                TermDbSchema.CourseTable.Cols.START_DATE + ", " +
                TermDbSchema.CourseTable.Cols.END_DATE + ", " +
                TermDbSchema.CourseTable.Cols.CHOSEN_START_DATE + ", " +
                TermDbSchema.CourseTable.Cols.CHOSEN_END_DATE + ", " +

                TermDbSchema.CourseTable.Cols.COURSE_STATUS + ", " +
                TermDbSchema.CourseTable.Cols.OPTIONAL_NOTE + ", " +
                TermDbSchema.CourseTable.Cols.MENTOR_NAME + ", " +
                TermDbSchema.CourseTable.Cols.MENTOR_PHONE + ", " +
                TermDbSchema.CourseTable.Cols.MENTOR_EMAIL + ", " +
                TermDbSchema.AssessmentTable.Cols.ALERT_SET + ", " +

                TermDbSchema.CourseTable.Cols.COL_COURSE_TERM_REFERENCE + " INTEGER REFERENCES " + TermDbSchema.TermTable.NAME + "(_id) ON DELETE CASCADE ON UPDATE CASCADE" + //<<<<<<<<<< ADDED
                ")"
        );
        db.execSQL("create table " + TermDbSchema.AssessmentTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                TermDbSchema.AssessmentTable.Cols.UUID + ", " +
                TermDbSchema.AssessmentTable.Cols.TITLE + ", " +
                TermDbSchema.AssessmentTable.Cols.ASSESS_TYPE + ", " +
                TermDbSchema.AssessmentTable.Cols.DUE_DATE + ", " +
                TermDbSchema.AssessmentTable.Cols.GOAL_DATE + ", " +
                //
                TermDbSchema.AssessmentTable.Cols.ALERT_SET + ", " +

                //
                TermDbSchema.AssessmentTable.Cols.COL_ASSESS_COURSE_REFERENCE + " INTEGER REFERENCES " + TermDbSchema.CourseTable.NAME + "(_id) ON DELETE CASCADE ON UPDATE CASCADE" + //<<<<<<<<<< ADDED
                //"course_reference" + " INTEGER REFERENCES " + "courses" + "(_id) ON DELETE CASCADE ON UPDATE CASCADE" +
                ")"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(false);
    }
    public Cursor getCoursesForThisTerm() {
        SQLiteDatabase db = this.getWritableDatabase();

        String termuuids = "select terms.uuid, terms._id, terms.title, courses.title, courses._id, courses.term_reference from terms inner join courses on terms._id = courses.term_reference";
        Cursor res = db.rawQuery(termuuids, null);
        return res;
    }

    public Cursor getAssessmentsForEachCourse() {
        SQLiteDatabase db = this.getWritableDatabase();
        //terms inner join courses on coursesterm_reference = termsterms_id
        String assessForCourseQuery = "select assessments.title, courses.title from courses inner join assessments on assessments._id = courses._id";
        Cursor assessForCourse = db.rawQuery(assessForCourseQuery, null);
        return assessForCourse;
    }

    public Cursor getTerms() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select " + TermDbSchema.TermTable.Cols.TITLE + " from " + TermDbSchema.TermTable.NAME, null);
        return res;
    }
    /*public Cursor getCoursesForEachTerm() {
        SQLiteDatabase db = this.getWritableDatabase();
        String tables = "SELECT courses." + TermDbSchema.CourseTable.Cols.TITLE + " FROM " + TermDbSchema.CourseTable.NAME +
                " JOIN " + TermDbSchema.TermTable.NAME + " ON " + TermDbSchema.CourseTable.Cols.COL_COURSE_TERM_REFERENCE + " = " + TermDbSchema.TermTable.NAME + "." + "_id";
        Cursor res = db.rawQuery(tables, null);
        return res;
    }*/
    public Cursor getCoursesForEachTerm() {
        SQLiteDatabase db = this.getWritableDatabase();
        String coursesForTermQuery = "select courses." + TermDbSchema.CourseTable.Cols.TITLE + ", terms.title from " + TermDbSchema.CourseTable.NAME + " inner join " + TermDbSchema.TermTable.NAME + " on courses.term_reference = terms._id";
        Cursor res = db.rawQuery(coursesForTermQuery, null);
        return res;
    }
    public Cursor getCourses() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor getCoursesCursor = db.rawQuery("select " + TermDbSchema.CourseTable.Cols.TITLE + " from " + TermDbSchema.CourseTable.NAME, null);
        return getCoursesCursor;
    }

    public long addTerm(String title, String start_date, String end_date) {
        ContentValues cv = new ContentValues();
        cv.put(TermDbSchema.TermTable.Cols.TITLE, title);
        cv.put(TermDbSchema.TermTable.Cols.START_DATE, start_date);
        cv.put(TermDbSchema.TermTable.Cols.END_DATE, end_date);
        return this.getWritableDatabase().insert(TermDbSchema.TermTable.NAME, null, cv);
    }

    public long addCourse(
            String title,
            String start_date,
            String end_date,
            String chosen_start_date,
            String chosen_end_date,
            String status, String note,
            String mentor_name,
            String mentor_phone,
            String mentor_email,
            int term_reference) {
        ContentValues cv = new ContentValues();
        cv.put(TermDbSchema.CourseTable.Cols.TITLE, title);
        cv.put(TermDbSchema.CourseTable.Cols.START_DATE, start_date);
        cv.put(TermDbSchema.CourseTable.Cols.END_DATE, end_date);
        cv.put(TermDbSchema.CourseTable.Cols.CHOSEN_START_DATE, chosen_start_date);
        cv.put(TermDbSchema.CourseTable.Cols.CHOSEN_END_DATE, chosen_end_date);
        cv.put(TermDbSchema.CourseTable.Cols.COURSE_STATUS, status);
        cv.put(TermDbSchema.CourseTable.Cols.OPTIONAL_NOTE, note);
        cv.put(TermDbSchema.CourseTable.Cols.MENTOR_NAME, mentor_name);
        cv.put(TermDbSchema.CourseTable.Cols.MENTOR_PHONE, mentor_phone);
        cv.put(TermDbSchema.CourseTable.Cols.MENTOR_EMAIL, mentor_email);
        cv.put(TermDbSchema.CourseTable.Cols.COL_COURSE_TERM_REFERENCE, term_reference);
        return this.getWritableDatabase().insert(TermDbSchema.CourseTable.NAME, null, cv);
    }


    public Cursor getAllCoursesWithTermDetails() {
        String tables = TermDbSchema.CourseTable.NAME +
                " JOIN " + TermDbSchema.TermTable.NAME + " ON " +
                TermDbSchema.CourseTable.Cols.COL_COURSE_TERM_REFERENCE + " = " +
                TermDbSchema.TermTable.NAME + "." + "_id";
        return this.getWritableDatabase().query(tables, null, null, null, null, null, null);
    }

    public Cursor getAllAssessmentsWithCourseAndTermdetails() {
        String tables = TermDbSchema.AssessmentTable.NAME +
                " JOIN " + TermDbSchema.CourseTable.NAME + " ON " +
                TermDbSchema.AssessmentTable.Cols.COL_ASSESS_COURSE_REFERENCE + " = " +
                TermDbSchema.CourseTable.NAME + "." + "_id" +
                " JOIN " + TermDbSchema.TermTable.NAME + " ON " +
                TermDbSchema.CourseTable.Cols.COL_COURSE_TERM_REFERENCE + " = " +
                TermDbSchema.TermTable.NAME + "." + "_id";
        return this.getWritableDatabase().query(tables, null, null, null, null, null, null);
    }

    public Cursor getAllAssessmentsWithCourseDetails() {
        String tables = TermDbSchema.AssessmentTable.NAME +
                " JOIN " + TermDbSchema.CourseTable.NAME + " ON " +
                TermDbSchema.AssessmentTable.Cols.COL_ASSESS_COURSE_REFERENCE + " = " +
                TermDbSchema.CourseTable.NAME + "." + "_id";
        return this.getWritableDatabase().query(tables, null, null, null, null, null, null);
    }


    /*public void  addSomeAssessments() {
        this.addAssessment("MATH02-A01","?","2019-01-31","2019-01-24",1);
        this.addAssessment("MATH01-A02","?","2019-02-28","2019-02-21",1);
        this.addAssessment("MATH01-A03","?","2019-03-31","2019-03-24",1);
        this.addAssessment("MATH02-A01","?","2019-04-30","2019-04-23",2);
        // and so on
    }*/
    /*public long addAssessment(
            String title,
            String assessType,
            String duedate,
            String goaldate,
            long course_reference
    ) {
        ContentValues cv = new ContentValues();
        //ContentValues vls = new ContentValues();

        cv.put(TermDbSchema.AssessmentTable.Cols.TITLE, title);
        cv.put(TermDbSchema.AssessmentTable.Cols.ASSESS_TYPE,assessType);
        cv.put(TermDbSchema.AssessmentTable.Cols.DUE_DATE,duedate);
        cv.put(TermDbSchema.AssessmentTable.Cols.GOAL_DATE,goaldate);
        cv.put(TermDbSchema.AssessmentTable.Cols.COL_ASSESS_COURSE_REFERENCE,course_reference);
        return this.getWritableDatabase().insert(TermDbSchema.AssessmentTable.NAME,null,cv);
    }*/

}