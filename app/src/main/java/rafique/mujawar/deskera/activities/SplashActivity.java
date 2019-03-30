package rafique.mujawar.deskera.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import rafique.mujawar.deskera.R;
import rafique.mujawar.deskera.utils.ActivityNavigator;
import rafique.mujawar.deskera.utils.DeskeraConstants;

public class SplashActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        ActivityNavigator.launchMainActivity(SplashActivity.this);
        finish();
      }
    }, DeskeraConstants.SPLASH_DURATION_MILLIS);
  }
}
