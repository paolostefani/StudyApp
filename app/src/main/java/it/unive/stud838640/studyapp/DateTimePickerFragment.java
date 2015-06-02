package it.unive.stud838640.studyapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateTimePickerFragment extends DialogFragment {
    public static final String EXTRA_DATE =
            "it.unive.stud838640.studyapp.date";
    public static final String EXTRA_DATE_OR_TIME =
            "it.unive.stud838640.studyapp.dateOrTime";

    private Date date;
    private String dateOrTime = "date";
    private String title;

    public static DateTimePickerFragment newInstance(Date date, String dateOrTime) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATE, date);
        args.putSerializable(EXTRA_DATE_OR_TIME, dateOrTime);

        DateTimePickerFragment fragment = new DateTimePickerFragment();
        fragment.setArguments(args);

        return fragment;
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() == null)
            return;
        Intent i = new Intent();
        i.putExtra(EXTRA_DATE, date);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        date = (Date) getArguments().getSerializable(EXTRA_DATE);
        dateOrTime = (String) getArguments().getSerializable(EXTRA_DATE_OR_TIME);

        Calendar calendar= Calendar.getInstance();
        calendar.setTime(date);
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        View v = null;

        if (dateOrTime.equals("date")) {
            v = getActivity().getLayoutInflater()
                    .inflate(R.layout.dialog_date, null);

            DatePicker datePicker = (DatePicker) v.findViewById(R.id.dialog_date_datePicker);
            datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    date = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();
                }
            });
            title = getString(R.string.date_label);
        }

        if (dateOrTime.equals("time")) {
            v = getActivity().getLayoutInflater()
                    .inflate(R.layout.dialog_time, null);

            TimePicker timePicker = (TimePicker) v.findViewById(R.id.dialog_time_timePicker);
            timePicker.setIs24HourView(DateFormat.is24HourFormat(getActivity()));
            timePicker.setCurrentHour(hour);
            timePicker.setCurrentMinute(minute);
            timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                @Override
                public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                    date = new GregorianCalendar(year, month, day, hourOfDay, minute).getTime();
                }
            });
            title = getString(R.string.time_label);
        }

        getArguments().putSerializable(EXTRA_DATE, date);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(title)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendResult(Activity.RESULT_OK);
                            }
                        })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }
}
