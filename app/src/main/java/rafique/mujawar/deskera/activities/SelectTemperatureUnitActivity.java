package rafique.mujawar.deskera.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import rafique.mujawar.deskera.R;
import rafique.mujawar.deskera.Temperature;
import rafique.mujawar.deskera.utils.DeskeraConstants;

public class SelectTemperatureUnitActivity extends AppCompatActivity implements
    View.OnClickListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_select_temperature_unit);
    findViewById(R.id.tv_Fareneit).setOnClickListener(this);
    findViewById(R.id.tv_celcius).setOnClickListener(this);
  }

  /**
   * Called when a view has been clicked.
   *
   * @param v The view that was clicked.
   */
  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.tv_celcius:
        publishData(Temperature.CELSIUS);
        break;
      case R.id.tv_Fareneit:
        publishData(Temperature.FAHRENHEIT);
        break;
    }
  }

  private void publishData(Temperature unit) {
    Intent intent = new Intent();
    intent.putExtra(DeskeraConstants.ARG_UNIT, unit.getValue());
    setResult(RESULT_OK, intent);
    finish();
  }
}
