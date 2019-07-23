package android.bignerdranch.wgu_assistant;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment2 extends DialogFragment  {
    public static final String EXTRA_DATE = "com.bignerdranch.android.wgu_assistant.date";
    //public static final String EXTRA_DATE2 = "com.bignerdranch.android.wgu_assistant.date2";

    private static final String ARG_DATE ="date";
    //private static final String ARG_DATE2 ="date2";

    private DatePicker mDatePicker;
    public static DatePickerFragment2 newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
        DatePickerFragment2 fragment = new DatePickerFragment2();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Date date = (Date) getArguments().getSerializable(ARG_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date2, null);

        mDatePicker = (DatePicker) v.findViewById(R.id.dialog_date_picker2);
        mDatePicker.init(year, month, day - 1, null);

        return new AlertDialog.Builder(getActivity()).setView(v).setTitle(R.string.date_picker_title).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int year = mDatePicker.getYear();
                int month = mDatePicker.getMonth();
                int day = mDatePicker.getDayOfMonth();
                Date date = new GregorianCalendar(year, month, day).getTime();
                sendResult(Activity.RESULT_OK, date);
            }
        }).create();
    }
    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);

    }


}
