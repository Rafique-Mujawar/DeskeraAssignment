package rafique.mujawar.deskera.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import java.util.List;

import rafique.mujawar.deskera.R;
import rafique.mujawar.deskera.adapters.ItemAdapter;
import rafique.mujawar.deskera.database.DatabaseManager;
import rafique.mujawar.deskera.database.entities.DeskeraItem;
import rafique.mujawar.deskera.eventbus.BusProvider;
import rafique.mujawar.deskera.eventbus.FavouriteListChangedEvent;
import rafique.mujawar.deskera.listeners.IDeskeraItemListener;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseItemFragment extends Fragment implements IDeskeraItemListener {
  final String TAG = this.getClass().getSimpleName();
  protected RecyclerView mRecyclerView;
  protected TextView mTvEmptyList;
  protected ItemAdapter mAdapter;

  public BaseItemFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    BusProvider.getInstance().register(this);
    View view = inflater.inflate(R.layout.fragment_items_list, container, false);
    mTvEmptyList = view.findViewById(R.id.tv_empty);
    mRecyclerView = view.findViewById(R.id.rvItems);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
        RecyclerView.VERTICAL, false));
    mAdapter = new ItemAdapter(getContext(), this);
    mRecyclerView.addItemDecoration(
        new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    mRecyclerView.setAdapter(mAdapter);
    onViewCreateCompleted();
    return view;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    BusProvider.getInstance().unregister(this);
  }

  public void updateAdapter(List<DeskeraItem> items) {
    mAdapter.setItems(items);
    if (null == items || items.isEmpty()) {
      mTvEmptyList.setVisibility(View.VISIBLE);
      mRecyclerView.setVisibility(View.GONE);
    } else {
      mTvEmptyList.setVisibility(View.GONE);
      mRecyclerView.setVisibility(View.VISIBLE);
    }
  }

  @Subscribe
  public void onSubscribeFavouriteChanged(FavouriteListChangedEvent event) {
    if (!event.getTAG().equalsIgnoreCase(TAG)) {
      onViewCreateCompleted();
    }
  }

  @Override
  public void onDeskeraItemLiked(DeskeraItem item) {
    DatabaseManager.getDatabase().getDeskeraItemDao().updateItem(item);
    BusProvider.getInstance().post(new FavouriteListChangedEvent(TAG));
  }

  @Override
  public void onDeskeraItemUnLiked(DeskeraItem item) {
    DatabaseManager.getDatabase().getDeskeraItemDao().updateItem(item);
    BusProvider.getInstance().post(new FavouriteListChangedEvent(TAG));
  }

  public abstract void onViewCreateCompleted();
}
