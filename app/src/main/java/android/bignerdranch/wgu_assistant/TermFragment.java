package android.bignerdranch.wgu_assistant;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.UUID;

public class TermFragment extends Fragment {
    private static final String ARG_TERM_ID = "term_id";
    private static final int START_DATE =0;
    private static final int END_DATE =1;
    private static final String DIALOG_DATE = "DialogDate";
    private EditText mTitleField;
    private TextView mStartDateField;
    private TextView mEndDateField;
    private ImageButton mStartDateImg;
    private ImageButton mEndDateImg;
    private TermBaseHelper myDb;

    private Button mSaveTermBtn;
    private Term mTerm;
    public static TermFragment newInstance(UUID termId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TERM_ID, termId);
        TermFragment fragment = new TermFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        UUID termId = (UUID) getArguments().getSerializable(ARG_TERM_ID);
        mTerm = TermLab.get(getActivity()).getTerm(termId);
        myDb = new TermBaseHelper(getContext());

    }
    @Override
    public void onPause() {
        super.onPause();
        TermLab.get(getActivity()).updateTerm(mTerm);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.delete_term, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_term_action:
                UUID termId = mTerm.getID();
                TermLab.get(getActivity()).deleteTerm(termId);
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_term, container, false);
        //
        /*SQLiteDatabase db = myDb.getWritableDatabase();
        String termuuids = "select terms.uuid, terms._id, terms.title, courses.title, courses._id, courses.term_reference from terms inner join courses on terms._id = courses.term_reference";
        Cursor res = db.rawQuery(termuuids, null);*/

        //Cursor res = myDb.getAllCoursesWithTermDetails();
            Cursor res = myDb.getCoursesForThisTerm();

        if(res.getCount() == 0) {
            Toast.makeText(getContext(), "You haven't added any Courses to this term", Toast.LENGTH_SHORT).show();
        } else {
            StringBuffer buffer = new StringBuffer();
            while (res.moveToNext()) {
                LinearLayout layout = (LinearLayout) v.findViewById(R.id.showCoursesLayout);
                final TextView[] myTextViews = new TextView[res.getCount()]; // create an empty array;
                // create a new textview
                final TextView rowTextView = new TextView(getContext());
                // set some properties of rowTextView or something
                    //rowTextView.append(res.getString(0) + "\n");

                rowTextView.append("Term Title: " + res.getString(2) + "\n");
                rowTextView.append("Courses: " + res.getString(3) + "\n");

                //Toast.makeText(getContext(), "You haven't added any Courses to this term", Toast.LENGTH_SHORT).show();
                    //rowTextView.append(mTerm.getID().toString());
                    //rowTextView.append(res.getString(14));





                // add the textview to the linearlayout
                layout.addView(rowTextView);

           }
        }

        mTitleField = (EditText) v.findViewById(R.id.inputTermName);
        mStartDateField = (TextView) v.findViewById(R.id.inputStartDate);
        mEndDateField = (TextView) v.findViewById(R.id.inputEndDate);
        mStartDateImg = (ImageButton)  v.findViewById(R.id.addStartDateImg);
        mEndDateImg = (ImageButton)  v.findViewById(R.id.addEndDateImg);

        mSaveTermBtn = (Button)  v.findViewById(R.id.saveTermBtn);
        mTitleField.setText(mTerm.getTitle());
        mStartDateImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mTerm.getStartDate());
                dialog.setTargetFragment(TermFragment.this, START_DATE);
                dialog.show(manager, DIALOG_DATE);

            }
        });
        mEndDateImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment2 dialog = DatePickerFragment2.newInstance(mTerm.getEndDate());
                dialog.setTargetFragment(TermFragment.this, END_DATE);
                dialog.show(manager, DIALOG_DATE);

            }
        });
        mSaveTermBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTerm.setTitle(mTitleField.getText().toString());
                Intent myIntent = new Intent(getActivity(), TermListActivity.class);
                startActivity(myIntent);
            }
        });
        return v;

    }
    public Cursor getCoursesForThisTerm() {
        SQLiteDatabase db = myDb.getWritableDatabase();
        String termuuids = "select terms.uuid, terms._id, terms.title, courses.title, courses._id, courses.term_reference from terms inner join courses on terms._id = courses.term_reference where terms.title= " + mTerm.getTitle();
        Cursor res = db.rawQuery(termuuids, null);
        return res;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Date todaysDate = new Date();

        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == START_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mTerm.setStartDate(date);
            mStartDateField.setText(mTerm.getStartDate().toString());



        }
        if (requestCode == END_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment2.EXTRA_DATE);
            mTerm.setEndDate(date);
            mEndDateField.setText(mTerm.getEndDate().toString());

        }
    }

}
