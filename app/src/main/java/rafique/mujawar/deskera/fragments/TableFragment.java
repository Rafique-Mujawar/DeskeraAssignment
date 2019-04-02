package rafique.mujawar.deskera.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import java.util.List;

import rafique.mujawar.deskera.R;
import rafique.mujawar.deskera.adapters.TableItemsAdapter;
import rafique.mujawar.deskera.database.DatabaseManager;
import rafique.mujawar.deskera.database.entities.TabletTabItem;
import rafique.mujawar.deskera.eventbus.BusProvider;
import rafique.mujawar.deskera.eventbus.ItemDeleteEvent;
import rafique.mujawar.deskera.utils.ActivityNavigator;
import rafique.mujawar.deskera.utils.DeskeraUtils;

/**
 * @author Rafique Mujawar
 * Date 02-04-2019
 */
public class TableFragment extends Fragment implements View.OnClickListener {
  private static final String TAG = TableFragment.class.getName();

  private ImageView mivSecondary;
  private TextView mtvTitle, mtvPrimary;
  private RecyclerView mrvTableItems;
  private SearchView mSearchView;

  private TableItemsAdapter mTableItemAdapter;
  private List<TabletTabItem> mTableItems;

  public TableFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_table, container, false);
    initView(view);
    setupToolabar();
    setLayoutManager();
    addListener();
    loadData();
    BusProvider.getInstance().register(this);
    return view;
  }

  private void initView(View view) {
    mrvTableItems = view.findViewById(R.id.rv_list);
    mtvPrimary = view.findViewById(R.id.tv_toolbar_primary);
    mivSecondary = view.findViewById(R.id.iv_toolbar_secondary);
    mtvTitle = view.findViewById(R.id.toolbar_title);
    mSearchView = view.findViewById(R.id.searchView);
    mSearchView.clearFocus();
  }

  private void setupToolabar() {
    mtvPrimary.setVisibility(View.VISIBLE);
    mtvPrimary.setText(R.string.edit);
    mivSecondary.setImageResource(R.drawable.ic_add);
    mtvTitle.setText(getString(R.string.table));
  }

  private void setLayoutManager() {
    mrvTableItems.setLayoutManager(new LinearLayoutManager(getContext()));
    DividerItemDecoration decoration =
        new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
    mTableItemAdapter = new TableItemsAdapter();
    mrvTableItems.addItemDecoration(decoration);
    mrvTableItems.setAdapter(mTableItemAdapter);
  }

  private void addListener() {
    mtvPrimary.setOnClickListener(this);
    mivSecondary.setOnClickListener(this);
    mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        if (null != mTableItemAdapter) {
          mTableItemAdapter.getFilter().filter(query);
        }
        return false;
      }

      @Override
      public boolean onQueryTextChange(String query) {
        if (null != mTableItemAdapter) {
          mTableItemAdapter.getFilter().filter(query);
        }
        return false;
      }
    });
  }

  private void loadData() {
    mTableItems = DatabaseManager.getDatabase().getTabletTabItemDao().getAll();
    mTableItemAdapter.setTableItemsList(mTableItems);
  }

  private void setData() {
    TabletTabItem item = new TabletTabItem();
    item.setName(DeskeraUtils.random());
    DatabaseManager.getDatabase().getTabletTabItemDao().insertItem(item);
    loadData();
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.tv_toolbar_primary:
        if (null != mTableItems && !mTableItems.isEmpty()) {
          ActivityNavigator.launchEditTableActivity(getContext());
        }
        break;
      case R.id.iv_toolbar_secondary:
        setData();
        break;
    }
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    BusProvider.getInstance().unregister(this);
  }

  @Subscribe
  @SuppressWarnings("unused")
  public void onSubscribeItemDeleted(ItemDeleteEvent event) {
    loadData();
  }
}
