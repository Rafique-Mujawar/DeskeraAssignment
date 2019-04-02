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
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rafique.mujawar.deskera.R;
import rafique.mujawar.deskera.adapters.TableItemsAdapter;
import rafique.mujawar.deskera.database.DatabaseManager;
import rafique.mujawar.deskera.database.entities.TabletTabItem;
import rafique.mujawar.deskera.eventbus.BusProvider;
import rafique.mujawar.deskera.eventbus.TableItemModifiedEvent;
import rafique.mujawar.deskera.listeners.ITableItemListener;
import rafique.mujawar.deskera.utils.ActivityNavigator;
import rafique.mujawar.deskera.utils.DeskeraUtils;

/**
 * @author Rafique Mujawar
 * Date 02-04-2019
 */
public class TableFragment extends Fragment implements View.OnClickListener, ITableItemListener {
  private static final String TAG = TableFragment.class.getName();

  private TextView mtvTitle, mtvPrimary, mtvSecondary;
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
    setupToolbar();
    setLayoutManager();
    addListener();
    loadData();
    BusProvider.getInstance().register(this);
    return view;
  }

  private void initView(View view) {
    mrvTableItems = view.findViewById(R.id.rv_list);
    mtvPrimary = view.findViewById(R.id.tv_toolbar_primary);
    mtvSecondary = view.findViewById(R.id.tv_toolbar_secondary);
    mtvTitle = view.findViewById(R.id.toolbar_title);
    mSearchView = view.findViewById(R.id.searchView);
    mSearchView.clearFocus();
  }

  private void setupToolbar() {
    mtvSecondary.setVisibility(View.VISIBLE);
    mtvPrimary.setVisibility(View.VISIBLE);
    mtvPrimary.setText(R.string.edit);
    mtvSecondary.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add, 0, 0, 0);
    mtvTitle.setText(getString(R.string.table));
  }

  private void setLayoutManager() {
    mrvTableItems.setLayoutManager(new LinearLayoutManager(getContext()));
    DividerItemDecoration decoration =
        new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
    mTableItemAdapter = new TableItemsAdapter(this);
    mrvTableItems.addItemDecoration(decoration);
    mrvTableItems.setAdapter(mTableItemAdapter);
  }

  private void addListener() {
    mtvPrimary.setOnClickListener(this);
    mtvSecondary.setOnClickListener(this);
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
    Collections.sort(mTableItems, new Comparator<TabletTabItem>() {
      @Override
      public int compare(TabletTabItem item1, TabletTabItem item2) {
        return item1.getName().compareToIgnoreCase(item2.getName());
      }
    });
    mTableItemAdapter.setTableItemsList(mTableItems);
  }

  private void setData() {
    String random = DeskeraUtils.random();
    TabletTabItem item = new TabletTabItem();
    item.setName(random);
    DatabaseManager.getDatabase().getTabletTabItemDao().insertItem(item);
    loadData();
    Toast.makeText(getContext(), "New Item added: " + random, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.tv_toolbar_primary:
        if (null != mTableItems && !mTableItems.isEmpty()) {
          ActivityNavigator.launchEditTableActivity(getContext());
        }
        break;
      case R.id.tv_toolbar_secondary:
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
  public void onSubscribeItemDeleted(TableItemModifiedEvent event) {
    loadData();
  }

  /**
   * Listener method for Table item click
   *
   * @param item {@link TabletTabItem}
   */
  @Override
  public void onTableItemSelect(TabletTabItem item) {
    ActivityNavigator.launchItemDetailsActivity(getContext(), item);
  }
}
