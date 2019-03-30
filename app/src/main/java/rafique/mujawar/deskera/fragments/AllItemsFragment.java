package rafique.mujawar.deskera.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;

import java.util.List;

import rafique.mujawar.deskera.database.DatabaseManager;
import rafique.mujawar.deskera.database.entities.DeskeraItem;
import rafique.mujawar.deskera.eventbus.FavouriteListChangedEvent;

/**
 * A simple {@link BaseItemFragment} subclass.
 */
public class AllItemsFragment extends BaseItemFragment {

  public AllItemsFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return super.onCreateView(inflater, container, savedInstanceState);
  }

  @Override
  public void onViewCreateCompleted() {
    loadData();
  }

  @Subscribe
  public void onSubscribeFavuriteChanged(FavouriteListChangedEvent event) {
    if (!event.getTAG().equalsIgnoreCase(TAG)) {
      loadData();
    }
  }

  private void loadData() {
    List<DeskeraItem> items = DatabaseManager.getDatabase().getDeskeraItemDao().getAll();
    updateAdapter(items);
  }
}
