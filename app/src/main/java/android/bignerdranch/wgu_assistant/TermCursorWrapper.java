package android.bignerdranch.wgu_assistant;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

public class TermCursorWrapper extends CursorWrapper {
    public TermCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public Term getTerm() {
        String uuidString = getString(getColumnIndex(TermDbSchema.TermTable.Cols.UUID));
        String title = getString(getColumnIndex(TermDbSchema.TermTable.Cols.TITLE));
        long startdate = getLong(getColumnIndex(TermDbSchema.TermTable.Cols.START_DATE));
        long enddate = getLong(getColumnIndex(TermDbSchema.TermTable.Cols.END_DATE));

        Term term = new Term(UUID.fromString(uuidString));
        term.setTitle(title);
        term.setStartDate(new Date(startdate));
        term.setEndDate(new Date(enddate));

        return term;
    }
}
