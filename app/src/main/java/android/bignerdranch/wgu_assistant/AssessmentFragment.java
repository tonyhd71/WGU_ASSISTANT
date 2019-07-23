package android.bignerdranch.wgu_assistant;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import static android.content.Context.ALARM_SERVICE;

public class AssessmentFragment extends Fragment {
    public static final String EXTRA_DATE = "com.bignerdranch.android.wgu_assistant.date";
    public static final String EXTRA_DATE2 = "com.bignerdranch.android.wgu_assistant.date2";
    private static final String ARG_ASSESSMENT_ID = "assessment_id";
    private static final int DUE_DATE =0;
    private static final int GOAL_DATE =1;
    private static final String DIALOG_DATE = "DialogDate";
    private EditText mTitleField;

    private TextView mClickCoursesTxt;
    private CheckBox mSetAlert;
    private Button mSaveAssessBtn;
    private TextView mDueDateField;
    private TextView mGoalDateField;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private RadioButton radioButton2;
    private ImageButton mStartCalendar;
    private ImageButton mEndCalendar;
    private TermBaseHelper myDb;
    private String assessmentType;
    private int startMonth, startYear, startDay, endMonth, endYear, endDay;
    private String dateFormat = "MM/dd/yy";
    private SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
    private Assessment mAssessment;
    public static AssessmentFragment newInstance(UUID assessmentId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_ASSESSMENT_ID, assessmentId);
        AssessmentFragment fragment = new AssessmentFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //UUID termId = (UUID) getActivity().getIntent().getSerializableExtra(TermActivity.EXTRA_TERM_ID);
        UUID assessmentId = (UUID) getArguments().getSerializable(ARG_ASSESSMENT_ID);
        mAssessment = AssessmentLab.get(getActivity()).getAssessment(assessmentId);
        myDb = new TermBaseHelper(getContext());
    }
    @Override
    public void onPause() {
        super.onPause();
        AssessmentLab.get(getActivity()).updateAssessment(mAssessment);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.delete_assessment, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_assessment_action:
                UUID assessmentId = mAssessment.getID();
                AssessmentLab.get(getActivity()).deleteAssessment(assessmentId);
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_assessment, container, false);
        final Cursor getCoursesCursor = myDb.getCourses();
         if(getCoursesCursor.getCount() == 0) {
             Toast.makeText(getContext(), "There are no Courses to add this Assessment to", Toast.LENGTH_SHORT).show();
        } else {
             StringBuffer buffer = new StringBuffer();
             while (getCoursesCursor.moveToNext()) {
                 LinearLayout layout = (LinearLayout) v.findViewById(R.id.coursesLayOut);
                 final TextView[] myTextViews = new TextView[getCoursesCursor.getCount()]; // create an empty array;
                 // create a new textview
                 final TextView rowTextView = new TextView(getContext());
                 // set some properties of rowTextView or something
                 final String CoursesToAdd = getCoursesCursor.getString(0);

                 rowTextView.append("Course Title : "+ getCoursesCursor.getString(0)+"\n");
                 // add the textview to the linearlayout
                 layout.addView(rowTextView);
                 //mAssessment.setCourseReference(3);
                 for (int i = 0; i < myTextViews.length; i++) {
                     final int finalI = i;
                     rowTextView.setOnTouchListener(new View.OnTouchListener() {

                         @Override
                         public boolean onTouch(View v, MotionEvent event) {
                             rowTextView.setId(finalI);

                             mAssessment.setCourseReference(finalI+1);
                             Toast.makeText(getContext(), "Assessment Added to " + CoursesToAdd, Toast.LENGTH_SHORT).show();

                             mClickCoursesTxt.setText("Assessment Added to " + CoursesToAdd);

                             return false;
                         }
                     });
                 }
             }
         }

        mTitleField = (EditText) v.findViewById(R.id.inputAssessName);
        mDueDateField = (TextView) v.findViewById(R.id.inputDueDate);
        mGoalDateField = (TextView) v.findViewById(R.id.inputGoalDate);
        mSaveAssessBtn = (Button)  v.findViewById(R.id.saveAssessBtn);
        mSetAlert = (CheckBox)  v.findViewById(R.id.setAssessGoalAlert);
        mStartCalendar = (ImageButton)  v.findViewById(R.id.startDateImageButton);
        mEndCalendar = (ImageButton)  v.findViewById(R.id.endDateImageButton);
        mClickCoursesTxt = (TextView) v.findViewById(R.id.clickCoursesTxt);

        mTitleField.setText(mAssessment.getTitle());
        if (mAssessment.getAlertSet() == 1) {
            mSetAlert.setChecked(true);
        }


        radioGroup = (RadioGroup) v.findViewById(R.id.assessmentGroup);
        radioButton = (RadioButton) v.findViewById(R.id.radioButton);
        radioButton2 = (RadioButton) v.findViewById(R.id.radioButton2);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButton) {
                    //do work when radioButton1 is active
                    assessmentType = radioButton.getText().toString();
                    //mAssessment.setAssessType(radioButton.getText().toString());
                } else  if (checkedId == R.id.radioButton2) {
                    assessmentType = radioButton2.getText().toString();
                    //mAssessment.setAssessType(radioButton2.getText().toString());
                }
            }
        });
        mStartCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mAssessment.getDueDate());
                dialog.setTargetFragment(AssessmentFragment.this, DUE_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });
        mEndCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment2 dialog = DatePickerFragment2.newInstance(mAssessment.getGoalDate());
                dialog.setTargetFragment(AssessmentFragment.this, GOAL_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });
        mSaveAssessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                mAssessment.setTitle(mTitleField.getText().toString());
                mAssessment.setAssessType(assessmentType);
                if (mSetAlert.isChecked()) {
                    Calendar c = Calendar.getInstance();
                    c.setTime(mAssessment.getGoalDate());
                    startAlarm(c);
                    mAssessment.setAlertSet(1);
                }

                Intent myIntent = new Intent(getActivity(), AssessmentListActivity.class);
                startActivity(myIntent);
            }
        });
        return v;
    }
    /*private void addSomeAssessments() {
        myDb.addAssessment("MATH02-A01","?","2019-01-31","2019-01-24", 2);
        //myDb.addAssessment("ff","ccc","2019-02-28","2019-02-21", 5);
        //myDb.addAssessment("MATH01-A03","?","2019-03-31","2019-03-24",1);
       // myDb.addAssessment("MATH02-A01","?","2019-04-30","2019-04-23",2);
    }*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Date todaysDate = new Date();

        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == DUE_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mAssessment.setDueDate(date);
            mDueDateField.setText(mAssessment.getDueDate().toString());

        }
        if (requestCode == GOAL_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment2.EXTRA_DATE);
            mAssessment.setGoalDate(date);
            mGoalDateField.setText(mAssessment.getGoalDate().toString());
        }
    }
    private void startAlarm(Calendar c) {
        /*AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);*/
        String startNotificationTitle = "Assessment Start Reminder";
        String startNotificationText = mAssessment.getTitle() + " begins today.";

        Intent startNotificationIntent = new Intent(getContext(), AlarmReceiver.class);
        startNotificationIntent.putExtra("mNotificationTitle", startNotificationTitle);
        startNotificationIntent.putExtra("mNotificationContent", startNotificationText);

        PendingIntent startPendingIntent = PendingIntent.getBroadcast(getContext(), 0, startNotificationIntent, 0);

        AlarmManager startAlarmManager = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);
        startAlarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), startPendingIntent);

    }
}
