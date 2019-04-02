package rafique.mujawar.deskera.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import rafique.mujawar.deskera.R;

public class TableItemDetailsActivity extends AppCompatActivity {

  private EditText metItem;
  private TextView mtvPrimary,mtvTitle, mtvSecondary;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_table_item_details);
  }
}
