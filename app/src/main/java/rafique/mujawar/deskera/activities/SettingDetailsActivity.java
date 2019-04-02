package rafique.mujawar.deskera.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import rafique.mujawar.deskera.R;
import rafique.mujawar.deskera.database.DatabaseManager;
import rafique.mujawar.deskera.database.entities.UserAccount;
import rafique.mujawar.deskera.utils.DeskeraConstants;
import rafique.mujawar.deskera.utils.DeskeraUtils;

public class SettingDetailsActivity extends AppCompatActivity implements View.OnClickListener {

  private TextView tvUserName, tvEmail, tvDoj, tvTemperature, tvSound, tvNotification,
      tvProbationEnd,
      tvDuration, tvPermanentDate, tvProbationLength, tvHobbies, tvBack, tvTitle;
  private ImageView ivProfile;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_setting_details);
    initViews();
    initListeners();
    setUpData();
  }

  private void initListeners() {
    tvBack.setVisibility(View.VISIBLE);
    tvTitle.setVisibility(View.VISIBLE);
    tvBack.setOnClickListener(this);
  }

  private void initViews() {
    tvUserName = findViewById(R.id.tvUserName);
    tvEmail = findViewById(R.id.tvEmail);
    tvDoj = findViewById(R.id.tvDoj);
    tvTemperature = findViewById(R.id.tvTemperature);
    tvSound = findViewById(R.id.tvSound);
    tvNotification = findViewById(R.id.tvNotification);
    tvProbationEnd = findViewById(R.id.tvProbationEnd);
    tvDuration = findViewById(R.id.tvDuration);
    tvPermanentDate = findViewById(R.id.tvPermanentDate);
    tvProbationLength = findViewById(R.id.tvProbationLength);
    tvHobbies = findViewById(R.id.tvHobbies);
    ivProfile = findViewById(R.id.iv_profile);
    tvBack = findViewById(R.id.tv_toolbar_primary);
    tvTitle = findViewById(R.id.toolbar_title);
  }

  private void setUpData() {
    UserAccount account = DatabaseManager.getDatabase().getUserAccountDao().getUserAccount(0);
    tvUserName.setText(account.getName());
    tvEmail.setText(account.getEmail());
    tvTemperature.setText(account.getTemperatureUnit());
    tvSound.setText(String.valueOf(account.isSoundOn));
    tvNotification.setText(String.valueOf(account.isNotificationOn));
    tvProbationEnd.setText(getDateTextIfValid(account.getProbationEndDate()));
    tvDoj.setText(getDateTextIfValid(account.getDateOfJoining()));
    String duration = getString(R.string.label_na);
    String length = getString(R.string.label_na);
    if (0 != account.getDateOfJoining() && 0 != account.getProbationEndDate()) {
      duration = DeskeraUtils.getDateDifference(account.getDateOfJoining(), account
          .getProbationEndDate());
      length = DeskeraUtils.getProbationLength(account.getDateOfJoining(),
          account.getProbationEndDate());
    }
    tvHobbies.setText(account.getHobbies());
    tvProbationLength.setText(length);
    tvDuration.setText(duration);
    if (0 != account.getProbationEndDate()) {
      tvPermanentDate.setText(
          DeskeraUtils.getDateFromMillis(
              account.getProbationEndDate() + DeskeraConstants.DAYS_TO_MILLIS));
    } else {
      tvPermanentDate.setText(R.string.date_not_available);
    }

    if (!TextUtils.isEmpty(account.getImageUri())) {
      Glide.with(this).
          load(account.getImageUri()).apply(new RequestOptions()
          .transform(new CircleCrop())).into(ivProfile);
    }
  }

  private String getDateTextIfValid(long millis) {
    String dateText = getString(R.string.date_not_available);
    if (0 != millis) {
      dateText = DeskeraUtils.getDateFromMillis(millis);
    }
    return dateText;
  }

  /**
   * Called when a view has been clicked.
   *
   * @param v The view that was clicked.
   */
  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.tv_toolbar_primary:
        onBackPressed();
        break;
    }
  }
}
