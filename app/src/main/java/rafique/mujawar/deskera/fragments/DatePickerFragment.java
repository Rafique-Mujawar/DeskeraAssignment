package rafique.mujawar.deskera.fragments;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

import rafique.mujawar.deskera.utils.DeskeraConstants;

/**
 * @author Rafique Mujawar
 * Date 01-04-2019
 */
public class DatePickerFragment extends DialogFragment {

  public static final String TAG = DatePickerFragment.class.getName();
  private DatePickerDialog.OnDateSetListener mListener;

  public static DatePickerFragment getInstance(OnDateSetListener listener, long date) {
    DatePickerFragment datePickerFragment = new DatePickerFragment();
    if (0 != date) {
      Bundle bundle = new Bundle();
      bundle.putLong(DeskeraConstants.ARG_DATE_IN_MILLS, date);
      datePickerFragment.setArguments(bundle);
    }

    datePickerFragment.setListener(listener);
    return datePickerFragment;
  }

  public void setListener(OnDateSetListener mListener) {
    this.mListener = mListener;
  }


  @Override
  public Dialog onCreateDialog(Bundle bundle) {
    // Use the current date as the default date in the picker
    final Calendar c = Calendar.getInstance();
    if (null != getArguments() && getArguments().containsKey(DeskeraConstants.ARG_DATE_IN_MILLS)) {
      c.setTimeInMillis(getArguments().getLong(DeskeraConstants.ARG_DATE_IN_MILLS));
    }
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);

    // Create a new instance of DatePickerDialog and return it
    return new DatePickerDialog(getActivity(), mListener, year, month, day);
  }
}