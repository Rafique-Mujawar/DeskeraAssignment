package rafique.mujawar.deskera.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import rafique.mujawar.deskera.R;
import rafique.mujawar.deskera.adapters.EditableTableAdapter;
import rafique.mujawar.deskera.database.DatabaseManager;
import rafique.mujawar.deskera.database.entities.TabletTabItem;
import rafique.mujawar.deskera.eventbus.BusProvider;
import rafique.mujawar.deskera.eventbus.ItemDeleteEvent;
import rafique.mujawar.deskera.listeners.AddCheckBoxCheckdListener;

/**
 * @author Rafique Mujawar
 * Date 02-04-2019
 */
public class TableEditActivity extends AppCompatActivity implements AddCheckBoxCheckdListener,
    View.OnClickListener {

  private static final String TAG = TableEditActivity.class.getSimpleName();

  private RecyclerView mrvTableItems;
  private TextView mtvPrimary, mtvTitle;
  private Button mBtnDelete;

  private EditableTableAdapter mEditTableAdapter;
  private List<TabletTabItem> mTableItemList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_table_edit);
    initView();
    setupToolabar();
    setLayoutManager();
    addListener();
    loadData();
  }

  private void initView() {
    mrvTableItems = findViewById(R.id.rv_list);
    mtvPrimary = findViewById(R.id.tv_toolbar_primary);
    mtvTitle = findViewById(R.id.toolbar_title);
    mBtnDelete = findViewById(R.id.btnDelete);
  }

  private void setupToolabar() {
    mtvPrimary.setVisibility(View.VISIBLE);
    mtvPrimary.setText(R.string.done);
    mtvTitle.setText(R.string.delete_items);
  }


  private void setLayoutManager() {
    mrvTableItems.setLayoutManager(new LinearLayoutManager(this));
    DividerItemDecoration decoration =
        new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
    mEditTableAdapter = new EditableTableAdapter(this);
    mrvTableItems.addItemDecoration(decoration);
    mrvTableItems.setAdapter(mEditTableAdapter);
  }

  private void loadData() {
    mTableItemList = DatabaseManager.getDatabase().getTabletTabItemDao().getAll();
    mEditTableAdapter.setTableDataList(mTableItemList);
  }

  private void addListener() {
    mtvPrimary.setOnClickListener(this);
    mBtnDelete.setOnClickListener(this);
  }

  @Override
  public void checkBoxChanged(boolean isChecked, int count) {
    mtvTitle.setText(getString(R.string.selected, count));
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.tv_toolbar_primary:
        finish();
        break;

      case R.id.btnDelete:
        deleteButtonClick();
        break;
    }
  }

  private void deleteButtonClick() {
    List<TabletTabItem> deleteList = mEditTableAdapter.getDeleteItemList();
    if (null != deleteList && !deleteList.isEmpty()) {
      DatabaseManager.getDatabase().getTabletTabItemDao().deleteMultiples(deleteList);
      BusProvider.getInstance().post(new ItemDeleteEvent(TAG));
      mTableItemList.removeAll(deleteList);
      mEditTableAdapter.setTableDataList(mTableItemList);
      mEditTableAdapter.getDeleteItemList().clear();
      mtvTitle.setText(R.string.delete_items);
    }
  }
}