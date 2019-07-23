package android.bignerdranch.wgu_assistant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TermLab {
    private static TermLab sTermLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    public static TermLab get(Context context) {
        if (sTermLab == null) {
            sTermLab = new TermLab(context);
        }
        return sTermLab;
    }
    private TermLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new TermBaseHelper(mContext).getWritableDatabase();
    }
    public void addTerm(Term c) {
        ContentValues values = getContentValues(c);
        mDatabase.insert(TermDbSchema.TermTable.NAME, null, values);
    }
    public List<Term> getTerms() {
        //return mTerms;
        //return new ArrayList<>();
        List<Term> terms = new ArrayList<>();
        TermCursorWrapper cursor = queryTerms(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                terms.add(cursor.getTerm());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return terms;
    }
    public Term getTerm(UUID id) {
        TermCursorWrapper cursor = queryTerms(TermDbSchema.TermTable.Cols.UUID + " = ?", new String[] {
                id.toString()
        });
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getTerm();
        } finally {
            cursor.close();
        }
    }
    public void deleteTerm(UUID termId)
    {
        Cursor deleteTermCursor = mDatabase.rawQuery("select * from terms inner join courses on terms._id = courses.term_reference", null);
        if (deleteTermCursor.getCount() == 0) {
            String uuidString = termId.toString();

            mDatabase.delete(TermDbSchema.TermTable.NAME, TermDbSchema.TermTable.Cols.UUID + " = ?", new String[] {uuidString});
        } else {
            Toast.makeText(mContext,"This Term has a Course, therefore it cannot be deleted.", Toast.LENGTH_SHORT).show();
        }



    }
    public void updateTerm(Term term) {
        String uuidString = term.getID().toString();
        ContentValues values = getContentValues(term);
        mDatabase.update(TermDbSchema.TermTable.NAME, values, TermDbSchema.TermTable.Cols.UUID + " = ?",
                new String[] {uuidString });
    }
    private TermCursorWrapper queryTerms(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                TermDbSchema.TermTable.NAME, null, whereClause, whereArgs, null, null, null);
        return new TermCursorWrapper(cursor);
    }
    private static ContentValues getContentValues(Term term) {
        ContentValues values = new ContentValues();
        values.put(TermDbSchema.TermTable.Cols.UUID, term.getID().toString());
        values.put(TermDbSchema.TermTable.Cols.TITLE, term.getTitle());
        values.put(TermDbSchema.TermTable.Cols.START_DATE, term.getStartDate().getTime());

        values.put(TermDbSchema.TermTable.Cols.END_DATE, term.getEndDate().getTime());

        return values;
    }
}
