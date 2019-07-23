package android.bignerdranch.wgu_assistant;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.bignerdranch.wgu_assistant.TermDbSchema.AssessmentTable;

import java.util.Date;
import java.util.UUID;

public class AssessmentCursorWrapper extends CursorWrapper {
    public AssessmentCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public Assessment getAssessment() {
        String uuidString = getString(getColumnIndex(AssessmentTable.Cols.UUID));
        String title = getString(getColumnIndex(AssessmentTable.Cols.TITLE));
        String assesstype = getString(getColumnIndex(AssessmentTable.Cols.ASSESS_TYPE));
        long duedate = getLong(getColumnIndex(AssessmentTable.Cols.DUE_DATE));
        long goaldate = getLong(getColumnIndex(AssessmentTable.Cols.GOAL_DATE));
        int alertset = getInt(getColumnIndex(AssessmentTable.Cols.ALERT_SET));
        int course_reference = getInt(getColumnIndex(AssessmentTable.Cols.COL_ASSESS_COURSE_REFERENCE));

        Assessment assessment = new Assessment(UUID.fromString(uuidString));
        assessment.setTitle(title);
        assessment.setAssessType(assesstype);
        assessment.setDueDate(new Date(duedate));
        assessment.setGoalDate(new Date(goaldate));
        assessment.setAlertSet(new Integer(alertset));
        assessment.setCourseReference(new Integer(course_reference));
        //assessment.setCourseReference(course_reference);


        return assessment;
    }
}
