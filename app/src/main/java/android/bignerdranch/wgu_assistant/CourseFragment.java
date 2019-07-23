package android.bignerdranch.wgu_assistant;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatDelegate;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static android.content.Context.ALARM_SERVICE;

public class CourseFragment extends Fragment/* implements DatePickerDialog.OnDateSetListener*/ {
    private static final String ARG_COURSE_ID = "course_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int START_DATE = 0;
    private static final int END_DATE = 1;
    private TextView mListOfTerms;
    private ImageButton mStartDateImgBtn;
    private ImageButton mEndDateImgBtn;
    private EditText mTitleField;
    private TextView mStartDateField;
    private TextView mEndDateField;
    private EditText mCourseStatus;
    private EditText mOptonalNote;
    private EditText mMentorName;

    private EditText mMentorPhone;
    private EditText mMentorEmail;
    private EditText mTermSelect;
    RadioGroup radioGroup;
    RadioButton radioButton;
    private Button shareNotesBtn;
    private CheckBox setReminder;

    private NotificationManagerCompat notificationManager;
    private Button submitStatusBtn;
    //private TextView mDisplayDate;
    private Course mCourse;
    private TermBaseHelper myDb;
    //private Term mTerm;

    public static CourseFragment newInstance(UUID courseId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_COURSE_ID, courseId);
        CourseFragment fragment = new CourseFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //UUID termId = (UUID) getActivity().getIntent().getSerializableExtra(TermActivity.EXTRA_TERM_ID);
        UUID courseId = (UUID) getArguments().getSerializable(ARG_COURSE_ID);
        setHasOptionsMenu(true);
        mCourse = CourseLab.get(getActivity()).getCourse(courseId);
        myDb = new TermBaseHelper(getContext());
        notificationManager = NotificationManagerCompat.from(getContext());
        //mCourse.setTermReference(3);

    }
    @Override
    public void onPause() {
        super.onPause();
        CourseLab.get(getActivity()).updateCourse(mCourse);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.delete_course, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_course_action:
                UUID courseId = mCourse.getID();
                CourseLab.get(getActivity()).deleteCourse(courseId);
                //Toast.makeText(getActivity(), R.string.toast_delete_crime, Toast.LENGTH_SHORT).show();
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //mDisplayDate = (TextView) view.findViewById(R.id.course_date);
        View v = inflater.inflate(R.layout.fragment_course, container, false);
        //
        Calendar calendar = Calendar.getInstance();
        Cursor res = myDb.getTerms();
        if(res.getCount() == 0) {
            //sho message
            Toast.makeText(getContext(), "There are no Terms to add this Course to", Toast.LENGTH_SHORT).show();
        } else {
            StringBuffer buffer = new StringBuffer();
            while (res.moveToNext()) {
                LinearLayout layout = (LinearLayout) v.findViewById(R.id.termsLayOut);
                final TextView[] myTextViews = new TextView[res.getCount()]; // create an empty array;
                // create a new textview
                final TextView rowTextView = new TextView(getContext());
                // set some properties of rowTextView or something
                final String TermsToAdd = res.getString(0);

                rowTextView.append("Term Title : " + res.getString(0) + "\n");
                // add the textview to the linearlayout
                layout.addView(rowTextView);

                for (int i = 0; i < myTextViews.length; i++) {
                    final int finalI = i;
                    rowTextView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            rowTextView.setId(finalI);
                            mCourse.setTermReference(finalI+1);
                            Toast.makeText(getContext(), "Course Added to " + TermsToAdd, Toast.LENGTH_SHORT).show();

                            mListOfTerms.setText("Course Added to " + TermsToAdd);
                            return false;
                        }
                    });

                }

            }

        }
        Cursor assessForCourse = myDb.getAssessmentsForEachCourse();
        if(assessForCourse.getCount() == 0) {
            //sho message
            Toast.makeText(getContext(), "There are no Assessments in this Course", Toast.LENGTH_SHORT).show();
        } else {
            StringBuffer buffer = new StringBuffer();
            while(assessForCourse.moveToNext()) {
                LinearLayout layout = (LinearLayout) v.findViewById(R.id.showAssessmentsLayout);
                final TextView[] myTextViews = new TextView[assessForCourse.getCount()]; // create an empty array;
                // create a new textview
                final TextView rowTextView = new TextView(getContext());
                // set some properties of rowTextView or something
                rowTextView.append("Course Title: " + assessForCourse.getString(1) + "\n");

                rowTextView.append("Assessments: " + assessForCourse.getString(0) + "\n");

                // add the textview to the linearlayout
                layout.addView(rowTextView);
            }
        }


        //showMessage("Error", buffer.toString());

        mTitleField = (EditText) v.findViewById(R.id.courseNameInput);
            mStartDateField = (TextView) v.findViewById(R.id.inputStart);
            mEndDateField = (TextView) v.findViewById(R.id.inputEnd);
            mCourseStatus = (EditText) v.findViewById(R.id.fillerRn);
            mOptonalNote = (EditText) v.findViewById(R.id.optionalNote);
            mMentorName = (EditText) v.findViewById(R.id.inputMentorName);
            mMentorPhone = (EditText) v.findViewById(R.id.inputMentorPhone);
            mMentorEmail = (EditText) v.findViewById(R.id.inputMentorEmail);
            mStartDateImgBtn = (ImageButton) v.findViewById(R.id.startDateImageButton);
            mEndDateImgBtn = (ImageButton) v.findViewById(R.id.endDateImageButton);
            submitStatusBtn = (Button) v.findViewById(R.id.submitStatusBtn);
            shareNotesBtn = (Button) v.findViewById(R.id.shareNotesBtn);
            setReminder = (CheckBox) v.findViewById(R.id.setReminder);
            mListOfTerms = (TextView) v.findViewById(R.id.clickTermsTxt);
        submitStatusBtn.setEnabled(false);
            shareNotesBtn.setEnabled(false);

        shareNotesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Share.shareString(getActivity(), mOptonalNote.getText().toString());
                }
            });
            mStartDateImgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager manager = getFragmentManager();
                    DatePickerFragment dialog = DatePickerFragment.newInstance(mCourse.getStartDate());
                    dialog.setTargetFragment(CourseFragment.this, START_DATE);
                    dialog.show(manager, DIALOG_DATE);
                }
            });
            mEndDateImgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager manager = getFragmentManager();
                    DatePickerFragment2 dialog = DatePickerFragment2.newInstance(mCourse.getEndDate());
                    dialog.setTargetFragment(CourseFragment.this, END_DATE);
                    dialog.show(manager, DIALOG_DATE);
                }
            });
            mTitleField.setText(mCourse.getTitle());
        if (mCourse.getAlertSet() == 1) {
            setReminder.setChecked(true);
        }
            //mStartDateField.setText(mCourse.getChosenStartDate());
            //mEndDateField.setText(mCourse.getChosenEndDate());
            mCourseStatus.setText(mCourse.getCourseStatus());
            mOptonalNote.setText(mCourse.getCourseNote());
            mMentorName.setText(mCourse.getCourseMentorName());
            mMentorPhone.setText(mCourse.getCourseMentorPhone());
            mMentorEmail.setText(mCourse.getCourseMentorEmail());
            //mTermSelect.setText(mCourse.getTermSelect());
        mOptonalNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                submitStatusBtn.setEnabled(true);
                shareNotesBtn.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
            submitStatusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCourse.setTitle(mTitleField.getText().toString());
                    if (setReminder.isChecked()) {
                        Calendar c2 = Calendar.getInstance();
                        c2.setTime(mCourse.getStartDate());
                        Calendar c3 = Calendar.getInstance();
                        c3.setTime(mCourse.getEndDate());
                        startAlarm2(c2);
                        endAlarm(c3);
                        mCourse.setAlertSet(1);
                    }

                    mCourse.setCourseStatus(mCourseStatus.getText().toString());
                    //mCourse.setCourseStatus(radioButton.getText().toString());
                    mCourse.setCourseNote(mOptonalNote.getText().toString());
                    mCourse.setCourseMentorName(mMentorName.getText().toString());
                    mCourse.setCourseMentorPhone(mMentorPhone.getText().toString());
                    mCourse.setCourseMentorEmail(mMentorEmail.getText().toString());
                    Intent myIntent = new Intent(getActivity(), CourseListActivity.class);
                    startActivity(myIntent);
                }
            });
            return v;
        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Date todaysDate = new Date();
        DateFormat outputFormatter = new SimpleDateFormat("MM/dd/yyyy");
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == START_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            String startDateString = outputFormatter.format(date);
            mCourse.setStartDate(date);
            mCourse.setChosenStartDate(startDateString);
            mStartDateField.setText(mCourse.getStartDate().toString());
        }
        if (requestCode == END_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment2.EXTRA_DATE);
            String endDateString = outputFormatter.format(date);

            mCourse.setEndDate(date);
            mCourse.setChosenEndDate(endDateString);
            mEndDateField.setText(mCourse.getStartDate().toString());

        }
    }
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    private void startAlarm2(Calendar c2) {
        /*AlarmManager alarmManager2 = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent2 = new Intent(getContext(), AlarmReceiver.class);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(getContext(), 0, intent2, 0);
        alarmManager2.set(AlarmManager.RTC_WAKEUP, c2.getTimeInMillis(), pendingIntent2);*/
        String startNotificationTitle = "Course Start Reminder";
        String startNotificationText = mCourse.getTitle() + " begins today.";

        Intent startNotificationIntent = new Intent(getContext(), AlarmReceiver.class);
        startNotificationIntent.putExtra("mNotificationTitle", startNotificationTitle);
        startNotificationIntent.putExtra("mNotificationContent", startNotificationText);

        PendingIntent startPendingIntent = PendingIntent.getBroadcast(getContext(), 0, startNotificationIntent, 0);

        AlarmManager startAlarmManager = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);
        startAlarmManager.set(AlarmManager.RTC_WAKEUP, c2.getTimeInMillis(), startPendingIntent);
    }
    private void endAlarm(Calendar c3) {
        /*AlarmManager alarmManager2 = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent2 = new Intent(getContext(), AlarmReceiver.class);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(getContext(), 0, intent2, 0);
        alarmManager2.set(AlarmManager.RTC_WAKEUP, c2.getTimeInMillis(), pendingIntent2);*/
        String endNotificationTitle = "Course End Reminder";
        String endNotificationText = mCourse.getTitle() + " ends today.";

        Intent endNotificationIntent = new Intent(getContext(), AlarmReceiver.class);
        endNotificationIntent.putExtra("mNotificationTitle", endNotificationTitle);
        endNotificationIntent.putExtra("mNotificationContent", endNotificationText);

        PendingIntent endPendingIntent = PendingIntent.getBroadcast(getContext(), 1, endNotificationIntent, 0);

        AlarmManager endAlarmManager = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);
        endAlarmManager.set(AlarmManager.RTC_WAKEUP, c3.getTimeInMillis(), endPendingIntent);
    }
    //String startNotificationTitle = "Course Start Reminder";
    //String startNotificationText = mCourse.getTitle() + " begins today.";
    /*intent.putExtra("mNotificationTitle", startNotificationTitle);
        intent.putExtra("mNotificationContent", startNotificationText);*/
}
