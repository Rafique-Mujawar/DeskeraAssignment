package rafique.mujawar.deskera.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import rafique.mujawar.deskera.R;
import rafique.mujawar.deskera.database.DatabaseManager;
import rafique.mujawar.deskera.database.entities.UserAccount;
import rafique.mujawar.deskera.utils.ActivityNavigator;
import rafique.mujawar.deskera.utils.DeskeraConstants;

public class SettingsFragment extends Fragment implements View.OnClickListener,
    CompoundButton.OnCheckedChangeListener {

  private TextView tvTemperature, tvProbationDate;
  private Switch soundSwitch, notificationSwitch;
  private UserAccount mUserAccount;

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
    if (TextUtils.isEmpty(mUserAccount.getProbationEndDate())) {
      date = mUserAccount.getProbationEndDate();
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
        //TODO: launch date picker
        break;
    }
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
        mUserAccount.setSoundOn(isChecked);
        updateDatabase();
        break;
      case R.id.switch_sound:
        mUserAccount.setNotificationOn(isChecked);
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
