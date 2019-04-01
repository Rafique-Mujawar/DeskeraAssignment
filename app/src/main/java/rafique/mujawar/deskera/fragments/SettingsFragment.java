package rafique.mujawar.deskera.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Calendar;

import rafique.mujawar.deskera.R;
import rafique.mujawar.deskera.database.DatabaseManager;
import rafique.mujawar.deskera.database.entities.UserAccount;
import rafique.mujawar.deskera.utils.ActivityNavigator;
import rafique.mujawar.deskera.utils.DeskeraConstants;
import rafique.mujawar.deskera.utils.DeskeraUtils;

public class SettingsFragment extends Fragment implements View.OnClickListener,
    CompoundButton.OnCheckedChangeListener, DatePickerDialog.OnDateSetListener {

  private static final String TAG = SettingsFragment.class.getName();
  private TextView tvTemperature, tvProbationDate;
  private Switch soundSwitch, notificationSwitch;
  private static UserAccount mUserAccount;

  public SettingsFragment() {
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_setting, container, false);
    initViews(view);
    updateListeners();
    loadData();
    return view;
  }

  private void initViews(View view) {
    view.findViewById(R.id.tv_view_details).setOnClickListener(this);
    notificationSwitch = view.findViewById(R.id.switch_notification);
    soundSwitch = view.findViewById(R.id.switch_sound);
    tvProbationDate = view.findViewById(R.id.tv_probation_date);
    tvTemperature = view.findViewById(R.id.tv_temperature);
  }


  private void updateListeners() {
    notificationSwitch.setOnCheckedChangeListener(this);
    soundSwitch.setOnCheckedChangeListener(this);
    tvProbationDate.setOnClickListener(this);
    tvTemperature.setOnClickListener(this);
  }

  private void loadData() {
    mUserAccount = DatabaseManager.getDatabase().getUserAccountDao().getUserAccount(0);
    tvTemperature.setText(mUserAccount.getTemperatureUnit());
    String date = getString(R.string.date_not_available);
    if (0 != mUserAccount.getProbationEndDate()) {
      date = DeskeraUtils.getDateFromMillis(mUserAccount.getProbationEndDate());
    }
    tvProbationDate.setText(date);
    soundSwitch.setChecked(mUserAccount.isSoundOn);
    notificationSwitch.setChecked(mUserAccount.isNotificationOn());
  }

  /**
   * Called when a view has been clicked.
   *
   * @param v The view that was clicked.
   */
  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.tv_view_details:
        ActivityNavigator.launchSettingDetailsActivity(getContext());
        break;
      case R.id.tv_temperature:
        ActivityNavigator.launchTemperatureUnitActivity(SettingsFragment.this, DeskeraConstants
            .RequestCodes.REQUEST_TEMPERATURE_UNIT);
        break;
      case R.id.tv_probation_date:
        DatePickerFragment datePickerFragment = DatePickerFragment.getInstance(this, mUserAccount
            .getProbationEndDate());
        datePickerFragment.show(getActivity().getSupportFragmentManager(), DatePickerFragment.TAG);
        break;
    }
  }

  @Override
  public void onDateSet(DatePicker view, int year, int month, int day) {
    // Do something with the date chosen by the user
    Log.i(TAG, "onDateSet: ");

    //TODO:: check for joining date
    final Calendar c = Calendar.getInstance();
    c.set(year, month, day);
    long newDate = c.getTimeInMillis();
    mUserAccount.setProbationEndDate(newDate);
    DatabaseManager.getDatabase().getUserAccountDao().updateUserAccount(mUserAccount);
    tvProbationDate.setText(DeskeraUtils.getDateFromMillis(newDate));
  }

  /**
   * Called when the checked state of a compound button has changed.
   *
   * @param buttonView The compound button view whose state has changed.
   * @param isChecked  The new checked state of buttonView.
   */
  @Override
  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    switch (buttonView.getId()) {
      case R.id.switch_notification:
        mUserAccount.setNotificationOn(isChecked);
        updateDatabase();
        break;
      case R.id.switch_sound:
        mUserAccount.setSoundOn(isChecked);
        updateDatabase();
        break;
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == Activity.RESULT_OK &&
        requestCode == DeskeraConstants.RequestCodes.REQUEST_TEMPERATURE_UNIT && null != data &&
        data.hasExtra(DeskeraConstants.ARG_UNIT)) {
      String newUnit = data.getStringExtra(DeskeraConstants.ARG_UNIT);
      tvTemperature.setText(newUnit);
      mUserAccount.setTemperatureUnit(newUnit);
      updateDatabase();
    }
  }

  private void updateDatabase() {
    DatabaseManager.getDatabase().getUserAccountDao().updateUserAccount(mUserAccount);
  }
}
