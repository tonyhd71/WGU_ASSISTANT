package android.bignerdranch.wgu_assistant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.bignerdranch.wgu_assistant.TermDbSchema.AssessmentTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AssessmentLab {
    private static AssessmentLab sAssessmentLab;
    private Context mContext;
    public static int xxx;
    private SQLiteDatabase mDatabase;
    public static AssessmentLab get(Context context) {
        if (sAssessmentLab == null) {
            sAssessmentLab = new AssessmentLab(context);
        }
        return sAssessmentLab;
    }
    private AssessmentLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new TermBaseHelper(mContext).getWritableDatabase();
    }
    public void addAssessment(Assessment c) {
        ContentValues values = getContentValues(c);
        mDatabase.insert(TermDbSchema.AssessmentTable.NAME, null, values);
    }
    public List<Assessment> getAssessments() {
        //return mTerms;
        //return new ArrayList<>();
        List<Assessment> assessments = new ArrayList<>();
        AssessmentCursorWrapper cursor = queryAssessments(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                assessments.add(cursor.getAssessment());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return assessments;
    }
    public Assessment getAssessment(UUID id) {
        /*for (Term term : mTerms) {
        if (term.getID().equals(id)) {
                return term;
            }
        }*/
        AssessmentCursorWrapper cursor = queryAssessments(AssessmentTable.Cols.UUID + " = ?", new String[] {
                id.toString()
        });
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getAssessment();
        } finally {
            cursor.close();
        }
    }
    public void deleteAssessment(UUID assessmentId)
    {
        String uuidString = assessmentId.toString();

        mDatabase.delete(AssessmentTable.NAME, AssessmentTable.Cols.UUID + " = ?", new String[] {uuidString});
    }
    public void updateAssessment(Assessment assessment) {
        String uuidString = assessment.getID().toString();
        ContentValues values = getContentValues(assessment);
        mDatabase.update(AssessmentTable.NAME, values, AssessmentTable.Cols.UUID + " = ?",
                new String[] {uuidString });
    }
    private AssessmentCursorWrapper queryAssessments(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                AssessmentTable.NAME, null, whereClause, whereArgs, null, null, null);
        return new AssessmentCursorWrapper(cursor);
    }
    private static ContentValues getContentValues(Assessment assessment) {
        ContentValues values = new ContentValues();
        values.put(AssessmentTable.Cols.UUID, assessment.getID().toString());
        values.put(AssessmentTable.Cols.TITLE, assessment.getTitle());
        values.put(AssessmentTable.Cols.ASSESS_TYPE, assessment.getAssessType());

        values.put(AssessmentTable.Cols.DUE_DATE, assessment.getDueDate().getTime());
        values.put(AssessmentTable.Cols.GOAL_DATE, assessment.getGoalDate().getTime());
        values.put(AssessmentTable.Cols.ALERT_SET, assessment.getAlertSet());

        values.put(AssessmentTable.Cols.COL_ASSESS_COURSE_REFERENCE, assessment.getCourseReference());

        return values;
    }
    /*private static ContentValues getContentValues(Assessment assessment) {
        ContentValues values = new ContentValues();
        //values.put(AssessmentTable.Cols.UUID, "test" );
        values.put(AssessmentTable.Cols.TITLE, "test");
        values.put(AssessmentTable.Cols.ASSESS_TYPE, "test");

        values.put(AssessmentTable.Cols.DUE_DATE, "2019-01-01");
        values.put(AssessmentTable.Cols.GOAL_DATE, "2019-01-01");
        values.put(AssessmentTable.Cols.COL_ASSESS_COURSE_REFERENCE, 1);

        return values;
    }*/
}
