package rafique.mujawar.deskera.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import rafique.mujawar.deskera.R;
import rafique.mujawar.deskera.database.DatabaseManager;
import rafique.mujawar.deskera.database.entities.TabletTabItem;
import rafique.mujawar.deskera.eventbus.BusProvider;
import rafique.mujawar.deskera.eventbus.TableItemModifiedEvent;
import rafique.mujawar.deskera.utils.DeskeraConstants;

public class TableItemDetailsActivity extends AppCompatActivity implements View.OnClickListener {

  private EditText metItem;
  private TextView mtvPrimary, mtvTitle, mtvSecondary;
  private boolean isEditing = false;
  private TabletTabItem mTabletTabItem = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_table_item_details);
    initViews();
    setData();
    initListeners();
  }

  private void initViews() {
    mtvPrimary = findViewById(R.id.tv_toolbar_primary);
    mtvSecondary = findViewById(R.id.tv_toolbar_secondary);
    mtvTitle = findViewById(R.id.toolbar_title);
    metItem = findViewById(R.id.etTableItem);
  }

  private void initListeners() {
    mtvPrimary.setOnClickListener(this);
    mtvSecondary.setOnClickListener(this);
    metItem.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        isEditing = true;
        mtvSecondary.setText(R.string.label_save);
      }

      @Override
      public void afterTextChanged(Editable s) {

      }
    });
  }

  private void setData() {
    if (getIntent().hasExtra(DeskeraConstants.ARG_TABLE_ITEM)) {
      mTabletTabItem = getIntent().getParcelableExtra(DeskeraConstants.ARG_TABLE_ITEM);
    }
    mtvSecondary.setVisibility(View.VISIBLE);
    mtvPrimary.setVisibility(View.VISIBLE);
    mtvTitle.setText(R.string.empty);
    mtvSecondary.setText(R.string.edit);
    mtvPrimary.setText(R.string.back);
    metItem.setText(mTabletTabItem.getName());
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
        finish();
        break;
      case R.id.tv_toolbar_secondary:
        onEditOrSaveClick();
        break;
    }
  }

  private void onEditOrSaveClick() {
    if (isEditing) {
      if (TextUtils.isEmpty(metItem.getText().toString().trim())) {
        metItem.setError(getString(R.string.empty_text));
      } else if (null != mTabletTabItem) {
        mTabletTabItem.setName(metItem.getText().toString().trim());
        DatabaseManager.getDatabase().getTabletTabItemDao().updateItem(mTabletTabItem);
        BusProvider.getInstance().post(new TableItemModifiedEvent());
        finish();
      }
    } else {
      metItem.requestFocus();
      metItem.setSelection(metItem.getText().length());
    }
  }
}
